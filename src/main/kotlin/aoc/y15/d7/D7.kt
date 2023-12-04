@file:Suppress("unused")

package aoc.y15.d7

import aoc.Puzzle

sealed interface Signal {
    fun ready(): Boolean
    fun get(): UShort
    fun set(uShort: UShort)
    fun reset()
}

sealed interface Wire {
    val id: String
    val signal: Signal
}

sealed interface Gate {
    fun run()

    fun canRun(): Boolean
}

interface UnaryGate : Gate {
    val input1: Signal
    val output: Signal

    override fun canRun(): Boolean = input1.ready()
}

interface BinaryGate : Gate {
    val input1: Signal
    val input2: Signal
    val output: Signal
    override fun canRun(): Boolean = input1.ready() && input2.ready()
}

data class SignalGate(override val input1: Signal, override val output: Signal) : UnaryGate {
    override fun run() {
        output.set(input1.get())
    }

    override fun toString(): String = "$input1 -> $output"
}

data class NotGate(override val input1: Signal, override val output: Signal) : UnaryGate {
    override fun run() {
        output.set(input1.get().inv())
    }

    override fun toString(): String = "NOT $input1 -> $output"
}

data class AndGate(override val input1: Signal, override val input2: Signal, override val output: Signal) : BinaryGate {
    override fun run() {
        output.set(input1.get() and input2.get())
    }

    override fun toString(): String = "$input1 AND $input2 -> $output"
}

data class OrGate(override val input1: Signal, override val input2: Signal, override val output: Signal) : BinaryGate {
    override fun run() {
        output.set(input1.get() or input2.get())
    }

    override fun toString(): String = "$input1 OR $input2 -> $output"
}

data class LeftShiftGate(override val input1: Signal, override val input2: Signal, override val output: Signal) : BinaryGate {
    override fun run() {
        output.set((input1.get().toInt() shl input2.get().toInt()).toUShort())
    }

    override fun toString(): String = "$input1 LSHIFT $input2 -> $output"
}

data class RightShiftGate(override val input1: Signal, override val input2: Signal, override val output: Signal) : BinaryGate {
    override fun run() {
        output.set((input1.get().toInt() shr input2.get().toInt()).toUShort())
    }

    override fun toString(): String = "$input1 RSHIFT $input2 -> $output"
}

data class Circuit(
    val gates: List<Gate>,
    val wires: Map<String, WireSignal>,
) {
    fun run() {
        val runnableGates = gates.toMutableList()

        while (runnableGates.isNotEmpty()) {
            val iterator = runnableGates.iterator()
            var actionsTaken = false
            while (iterator.hasNext()) {
                val gate = iterator.next()

                if (gate.canRun()) {
                    println("Running $gate")
                    gate.run()
                    iterator.remove()

                    actionsTaken = true
                }
            }

            if (!actionsTaken) {
                wires.values.toList().sortedBy { it.id }.forEach { println(it) }
                runnableGates.forEach { println(it) }
                throw IllegalArgumentException("Did nothing")
            }
        }
    }

    fun get(key: String): UShort {
        return wires[key]?.get()!!
    }

    /**
     * This impl is a bit shit.
     */
    fun set(key: String, value: UShort) {
        wires[key]?.forceSet(value)
    }

    fun reset() {
        wires.values.forEach { it.reset() }
    }
}

class WireSignal(val id: String) : Signal {
    private var value: UShort? = null

    override fun ready(): Boolean {
        return value != null
    }

    override fun get(): UShort {
        return value!!
    }

    override fun set(uShort: UShort) {
        if (value != null) {
            return
        }

        value = uShort
    }

    fun forceSet(uShort: UShort) {
        value = uShort
    }

    override fun reset() {
        value = null
    }

    override fun toString(): String {
        return "$id:$value"
    }
}

class StaticSignal(val value: UShort) : Signal {
    override fun ready(): Boolean = true
    override fun get(): UShort = value
    override fun set(uShort: UShort) {}
    override fun reset() {}

    override fun toString(): String {
        return "$value"
    }
}

fun parseCircuit(lines: List<String>): Circuit {

    val wires = mutableMapOf<String, WireSignal>()

    fun getSignal(key: String): Signal {
        if (key[0].isDigit()) {
            return StaticSignal(key.toUShort())
        }
        return wires.computeIfAbsent(key) { WireSignal(key) }
    }

    fun String.parseGate(): Gate {
        try {
            val (left, outputWireId) = this.split(" -> ", limit = 2)

            val output = getSignal(outputWireId)

            val leftPieces = left.split(" ")

            if (leftPieces.size == 1) {
                val input = getSignal(leftPieces[0])
                return SignalGate(input, output)
            }

            if (leftPieces.size == 2 && leftPieces[0] == "NOT") {
                val input = getSignal(leftPieces[1])
                return NotGate(input, output)
            }

            if (leftPieces.size == 3) {
                val a = leftPieces[0]
                val b = leftPieces[2]
                return when (leftPieces[1]) {
                    "AND" -> AndGate(getSignal(a), getSignal(b), output)
                    "OR" -> OrGate(getSignal(a), getSignal(b), output)
                    "LSHIFT" -> LeftShiftGate(getSignal(a), getSignal(b), output)
                    "RSHIFT" -> RightShiftGate(getSignal(a), getSignal(b), output)
                    else -> throw IllegalArgumentException(this)
                }
            }

            throw IllegalArgumentException(this)
        } catch (e: Exception) {
            throw IllegalArgumentException("Failed parsing $this: ${e.message}", e)
        }
    }

    return Circuit(lines.map { it.parseGate() }, wires)
}


class D7P1 : Puzzle {
    override fun solve(input: String): Any {
        val circuit = parseCircuit(input.lines())
        circuit.run()
        return circuit.get("a")
    }
}

class D7P2 : Puzzle {
    override fun solve(input: String): Any {
        val circuit = parseCircuit(input.lines())
        circuit.run()

        val a = circuit.get("a")
        circuit.reset()
        circuit.set("b", a)
        circuit.run()
        return circuit.get("a")
    }
}