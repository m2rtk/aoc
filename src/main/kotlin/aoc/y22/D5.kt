package aoc.y22

import aoc.lines

private class D5 {
    private data class Move(val amount: Int, val start: Int, val end: Int)
    private data class Input(val stacks: List<MutableList<Char>>, val moves: List<Move>)

    private fun parse(lines: Sequence<String>): Input {
        val sb1 = mutableListOf<String>()
        val sb2 = mutableListOf<String>()
        var target = sb1

        for (line in lines) {
            if (line.isBlank()) {
                target = sb2
                continue;
            }

            target.add(line)
        }

        val stacks = mutableListOf<MutableList<Char>>()
        val stackCount = sb1.first().length / 4 + 1
        for (i in 0 until stackCount) {
            val stack = mutableListOf<Char>()
            val c = i * 4 + 1
            for (line in sb1.reversed().drop(1)) {
                val x = line[c]
                if (x == ' ') {
                    break
                }
                stack.add(line[c])
            }

            stacks.add(stack)
        }

        val moves = sb2
            .map { it.removePrefix("move ").replace("from ", "").replace("to ", "") }
            .map { it.split(" ", limit = 3) }
            .map { (a,b,c) -> Move(a.toInt(),b.toInt(),c.toInt()) }

        return Input(stacks, moves)
    }


    fun t1(lines: Sequence<String>) {
        val input = parse(lines)

        for (move in input.moves) {
            val from = input.stacks[move.start - 1]
            val to = input.stacks[move.end - 1]

            for (i in 0 until move.amount) {
                to.add(from.removeLast())
            }
        }

        for (stack in input.stacks) {
            print(stack.last())
        }
        println()
    }

    fun t2(lines: Sequence<String>) {
        val input = parse(lines)

        for (move in input.moves) {
            val from = input.stacks[move.start - 1]
            val to = input.stacks[move.end - 1]

            val last = from.takeLast(move.amount)
            for (i in 0 until move.amount) {
                from.removeAt(from.size-1)
            }

            to.addAll(last)
        }

        for (stack in input.stacks) {
            print(stack.last())
        }
        println()
    }
}

fun main() {
    fun sample() = lines("/aoc/y22/D5_sample.txt")
    fun input() = lines("/aoc/y22/D5_input.txt")

    val d = D5()

    d.t1(sample())
    d.t1(input())
    d.t2(sample())
    d.t2(input())
}