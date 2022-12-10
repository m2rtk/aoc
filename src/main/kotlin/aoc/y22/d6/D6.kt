@file:Suppress("unused")

package aoc.y22.d6

import aoc.Puzzle
import java.util.LinkedList

private data class Buffer(val delegate: LinkedList<Char>, val limit: Int) {
    fun add(a: Char) {
        delegate.addFirst(a)

        if (delegate.size > limit) {
            delegate.removeLast()
        }
    }

    fun allEntriesUnique(): Boolean {
        val set = delegate.toSet()
        return set.size == limit
    }
}

class D6P1 : Puzzle {
    override fun solve(input: String): Any {
        for (line in input.lines()) {
            val buffer = Buffer(LinkedList(), 4)

            for (i in line.indices) {
                buffer.add(line[i])
                if (buffer.allEntriesUnique()) {
                    return i + 1
                }
            }
        }

        throw RuntimeException()
    }
}
class D6P2 : Puzzle {
    override fun solve(input: String): Any {
        for (line in input.lines()) {
            val buffer = Buffer(LinkedList(), 14)

            for (i in line.indices) {
                buffer.add(line[i])
                if (buffer.allEntriesUnique()) {
                    return i + 1
                }
            }
        }

        throw RuntimeException()
    }
}