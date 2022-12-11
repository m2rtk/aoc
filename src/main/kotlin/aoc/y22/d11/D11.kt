@file:Suppress("unused")

package aoc.y22.d11

import aoc.Puzzle
import java.math.BigInteger


fun BigInteger.isDivisibleBy(other: Int): Boolean {
    return this.mod(other.toBigInteger()) == BigInteger.ZERO
}

fun Long.isDivisibleBy(other: Int): Boolean {
    return this % other == 0L
}

class Monkeys(val list: List<Monkey>) {

    private var roundCounter = 0
    val monkeyBusinessLevel: Long
        get() = list
            .map { it.inspectCounter }
            .sorted()
            .takeLast(2)
            .reduce { acc, i -> acc * i }

    fun printRound() {
        println("== After round ${roundCounter} ==")
        list.forEachIndexed { index, monkey ->
            println("Monkey $index inspected items ${monkey.inspectCounter} times.")
        }
    }

    fun runRound(worryLevelReduction: (BigInteger) -> BigInteger) {
        roundCounter++
        for (i in list.indices) {
            val monkey = list[i]

            val items = monkey.items.listIterator()
            while (items.hasNext()) {
                val item = items.next()

                val worryLevelAfterInspection = monkey.inspect(item)
                val worryLevelAfterReduction = worryLevelReduction.invoke(worryLevelAfterInspection)
                val newMonkey = monkey.test(worryLevelAfterReduction)

                list[newMonkey].catch(worryLevelAfterReduction)
                items.remove()
            }
        }
    }

    companion object {
        fun parse(input: String): Monkeys {
            return input.split("\n\n")
                .map { it.lines() }
                .map { it.subList(1, it.size) }
                .map { Monkey.parse(it) }
                .let { Monkeys(it) }
        }
    }
}

class Monkey(
    startingItems: List<BigInteger>,
    private val operation: (BigInteger) -> BigInteger,
    private val test: (BigInteger) -> Int,
    val divBy: Int
) {
    val items = startingItems.toMutableList()

    var inspectCounter: Long = 0
        private set

    fun inspect(itemWorryLevel: BigInteger): BigInteger {
        inspectCounter++
        return operation.invoke(itemWorryLevel)
    }

    fun test(itemWorryLevel: BigInteger) = test.invoke(itemWorryLevel)
    fun catch(itemWorryLevel: BigInteger) {
        items.add(itemWorryLevel)
    }

    companion object {
        private fun parseOperation(s: String): (BigInteger) -> BigInteger {
            val (_, operator, right) = s.split(" ")

            return {
                val r = if (right == "old") {
                    it
                } else {
                    right.toBigInteger()
                }

                if (operator == "*") {
                    it * r
                } else {
                    it + r
                }
            }
        }


        private fun parseTest(lines: List<String>): (BigInteger) -> Int {
            val operand = lines[2].split("divisible by ")[1].toInt()
            val ifTrue = lines[3].split("monkey ")[1].toInt()
            val ifFalse = lines[4].split("monkey ")[1].toInt()
            return {
                if (it.isDivisibleBy(operand)) {
                    ifTrue
                } else {
                    ifFalse
                }
            }
        }

        fun parse(lines: List<String>): Monkey {
            val divBy = lines[2].split("divisible by ")[1].toInt()
            val ifTrue = lines[3].split("monkey ")[1].toInt()
            val ifFalse = lines[4].split("monkey ")[1].toInt()
            val test: (BigInteger) -> Int = {
                if (it.isDivisibleBy(divBy)) {
                    ifTrue
                } else {
                    ifFalse
                }
            }
            return Monkey(
                lines[0].split("items: ")[1].split(", ").map { it.toBigInteger() },
                lines[1].split(" new = ")[1].let { parseOperation(it) },
                parseTest(lines),
                divBy
            )
        }

    }
}


class D11P1 : Puzzle {
    override fun solve(input: String): Any {
        val monkeys = Monkeys.parse(input)

        val three = BigInteger("3")
        for (i in 0 until 20) {
            monkeys.runRound { it / three }
        }

        return monkeys.monkeyBusinessLevel
    }
}

class D11P2 : Puzzle {
    override fun solve(input: String): Any {
        val monkeys = Monkeys.parse(input)

        val lcm = monkeys.list.map { it.divBy }.reduce { a, b -> a * b }.let { BigInteger.valueOf(it.toLong()) }

        for (i in 0 until 10_000) {
            monkeys.runRound { it.mod(lcm) }
        }

        return monkeys.monkeyBusinessLevel
    }
}