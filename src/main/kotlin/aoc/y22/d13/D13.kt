@file:Suppress("unused")

package aoc.y22.d13

import aoc.Puzzle
import kotlin.math.max

/*
I really wish I was using python right now

>>> import ast
>>> ast.literal_eval('[[0,0,0], [0,0,1], [1,1,0]]')
[[0, 0, 0], [0, 0, 1], [1, 1, 0]]
 */

sealed interface Item {
    fun compareTo(right: Item): Result
}

enum class Result(val ord: Int) {
    RIGHT_ORDER(1),
    WRONG_ORDER(-1),
    CONTINUE(0)
}

data class IntWrapper(val value: Int) : Item {
    override fun compareTo(right: Item): Result {
        val left = this
//        println("Compare $left vs $right")

        return when (right) {
            is IntWrapper -> if (left.value == right.value) {
                    Result.CONTINUE
                } else if (left.value < right.value) {
                    Result.RIGHT_ORDER
                } else {
                    Result.WRONG_ORDER
                }

            is ListWrapper -> ListWrapper(left).compareTo(right)
        }
    }

    override fun toString(): String {
        return value.toString()
    }
}
data class ListWrapper(val value: List<Item>) : Item {
    constructor(vararg items: Item) : this(items.toList())

    val size = value.size

    operator fun get(i: Int) = value[i]

    override fun toString(): String {
        return value.toString()
    }

    override fun compareTo(right: Item): Result {
        val left = this
//        println("Compare $left vs $right")

        when (right) {
            is IntWrapper -> return left.compareTo(ListWrapper(right))

            is ListWrapper -> {
                for (i in 0 until max(left.size, right.size)) {
                    return if (left.size == i) {
                        Result.RIGHT_ORDER
                    } else if (right.size == i) {
                        Result.WRONG_ORDER
                    } else {
                        val result = left[i].compareTo(right[i])
                        if (result == Result.CONTINUE) {
                            continue
                        } else {
                            result
                        }
                    }
                }
                return Result.CONTINUE
            }
        }
    }
}

data class Packets(val pairs: List<Pair<ListWrapper, ListWrapper>>) {
    companion object {
        fun parse(lines: String): Packets {
            val pairs = lines.split("\n\n")
                .map {
                val (left, right) = it.split("\n")
                parseLine(left) to parseLine(right)
            }

            return Packets(pairs)
        }

        fun parseLine(line: String): ListWrapper {
            return readList(line)
        }

        private fun readList(line: String): ListWrapper {
            val list = mutableListOf<Item>()
            val contents = line.substring(1, line.length - 1)

            var remaining = contents

            while (remaining.isNotEmpty()) {
                if (remaining.startsWith(",")) {
                    remaining = remaining.substring(1)
                    continue
                }

                if (remaining.startsWith("[")) {
                    val matchingBracket = findMatchingBracket(remaining)
                    val innerList = remaining.substring(0, matchingBracket + 1)
                    list.add(readList(innerList))
                    remaining = remaining.substring(innerList.length)
                } else {
                    val nextComma = remaining.indexOf(',')
                    val endIndex = if (nextComma == -1) {
                        remaining.length
                    } else {
                        nextComma
                    }

                    val numberString = remaining.substring(0, endIndex)
                    list.add(IntWrapper(numberString.toInt()))
                    remaining = remaining.substring(numberString.length)
                }
            }

            return ListWrapper(list)
        }

        private fun findMatchingBracket(line: String): Int {
            var counter = 0

            for (i in line.indices) {
                if (line[i] == '[')  {
                    counter++
                } else if (line[i] == ']') {
                    counter--
                }

                if (counter == 0) {
                    return i
                }
            }

            return -1
        }
    }

    fun add(pair: Pair<ListWrapper, ListWrapper>): Packets {
        return Packets(pairs + pair)
    }

    fun sorted(): List<ListWrapper> {
        return pairs
            .flatMap { it.toList() }
            .sortedWith{ left, right -> left.compareTo(right).ord }
            .reversed()
    }
}

class D13P1 : Puzzle {
    override fun solve(input: String): Any {
        val packets = Packets.parse(input)

        val ok = mutableListOf<Int>()

        for (i in packets.pairs.indices) {
            val (left, right) = packets.pairs[i]

            val result = left.compareTo(right)

            if (result == Result.RIGHT_ORDER) {
                ok.add(i + 1)
            }
        }

        return ok.sum()
    }
}

class D13P2 : Puzzle {
    override fun solve(input: String): Any {
        val a = Packets.parseLine("[[2]]")
        val b = Packets.parseLine("[[6]]")
        val sorted = Packets.parse(input).add(a to b).sorted()

        val ai = sorted.indexOf(a) + 1
        val bi = sorted.indexOf(b) + 1

        return ai * bi
    }
}