@file:Suppress("unused")

package aoc.y22.d10

import aoc.Puzzle

sealed interface Command {
    companion object {
        fun parse(line: String): Command {
            if (line == "noop") {
                return NoOp
            }

            val value = line.split(" ")[1].toInt()
            return AddX(value)
        }
    }
}

data class AddX(val value: Int) : Command {
    override fun toString() = "addx $value"
}

object NoOp : Command {
    override fun toString() = "noop"
}

class Computer(
    private val commands: List<Command>,
    private val debug: Boolean = false
) {

    private var commandIndex = 0

    private var x = 1
    private val sprite: List<Int>
        get() = listOf(
            (x - 1).coerceIn(0, 39),
            x.coerceIn(0, 39),
            (x + 1).coerceIn(0, 39)
        )

    private var executingCommandStartCycle = 0
    private var executingCommand: Command? = null

    private var cycle: Int = 0
    private val history: MutableList<Int> = mutableListOf(1)

    private val screenRows: MutableList<String> = mutableListOf("")
    val screenString: String
        get() = screenRows.joinToString("\n")

    fun signalStrengthDuring(targetCycle: Int): Int {
        return history[targetCycle] * targetCycle
    }

    fun run() {
        printSprite()
        print()
        while (true) {
            runCycle()

            if (executingCommand == null && commandIndex == commands.size) {
                return
            }
        }
    }

    private fun runCycle() {
        cycle++
        val c = cycle.toString().padStart(2)
        // before
        if (executingCommand == null) {
            executingCommand = commands[commandIndex++]
            executingCommandStartCycle = cycle
            print("Start cycle  ${c}: begin executing $executingCommand")
        }

        // during
        history.add(x)

        if (screenRows[screenRows.size - 1].length == 40) {
            screenRows.add("")
        }

        val column = screenRows[screenRows.size - 1].length
        print("During cycle $c: CRT draws pixel in position $column")
        val pixel = if (column in sprite) {
            '#'
        } else {
            '.'
        }
        screenRows[screenRows.size - 1] = screenRows[screenRows.size - 1] + pixel
        print("Current CRT row: ${screenRows[screenRows.size - 1]}")

        // after
        val command = executingCommand
        if (command is NoOp) {
            executingCommand = null
        } else if (command is AddX && cycle - 1 == executingCommandStartCycle) {
            x += command.value
            executingCommand = null
            print("End of cycle $c: finish executing $command (Register X is now $x)")
            printSprite()
        }

        print()
    }

    private fun printSprite() {
        val s = (0 until 40).map { '.' }.toMutableList()
        sprite.forEach { s[it] = '#' }
        print("Sprite position: ${s.joinToString("")}")
    }

    private fun print(s: String = "") {
        if (debug) {
            println(s)
        }
    }
}

class D10P1 : Puzzle {
    override fun solve(input: String): Any {
        val computer = Computer(input.lines().map { Command.parse(it) }, false)
        computer.run()

        fun n(i: Int) = computer.signalStrengthDuring(i)

        return listOf(n(20), n(60), n(100), n(140), n(180), n(220)).sum()
    }
}

class D10P2 : Puzzle {
    override fun solve(input: String): Any {
        val computer = Computer(input.lines().map { Command.parse(it) }, false)
        computer.run()
        return "\n" + computer.screenString
    }
}