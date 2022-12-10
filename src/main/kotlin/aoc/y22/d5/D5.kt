@file:Suppress("unused")

package aoc.y22.d5

import aoc.Puzzle
import kotlin.text.StringBuilder

private data class Move(val amount: Int, val start: Int, val end: Int)
private data class Input(val stacks: List<MutableList<Char>>, val moves: List<Move>)

private fun parse(lines: Sequence<String>): Input {
    val sb1 = mutableListOf<String>()
    val sb2 = mutableListOf<String>()
    var target = sb1

    for (line in lines) {
        if (line.isBlank()) {
            target = sb2
            continue
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

class D5P1 : Puzzle {
    override fun solve(input: String): Any {
        val inn = parse(input.lineSequence())

        for (move in inn.moves) {
            val from = inn.stacks[move.start - 1]
            val to = inn.stacks[move.end - 1]

            for (i in 0 until move.amount) {
                to.add(from.removeLast())
            }
        }

        val result = StringBuilder()
        for (stack in inn.stacks) {
            result.append(stack.last())
        }

        return result.toString()
    }
}
class D5P2 : Puzzle {
    override fun solve(input: String): Any {
        val inn = parse(input.lineSequence())

        for (move in inn.moves) {
            val from = inn.stacks[move.start - 1]
            val to = inn.stacks[move.end - 1]

            val last = from.takeLast(move.amount)
            for (i in 0 until move.amount) {
                from.removeAt(from.size-1)
            }

            to.addAll(last)
        }

        val result = StringBuilder()
        for (stack in inn.stacks) {
            result.append(stack.last())
        }

        return result.toString()
    }
}
