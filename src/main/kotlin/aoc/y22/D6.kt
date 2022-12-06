package aoc.y22

import aoc.lines
import java.util.LinkedList

private class D6 {
    data class Buffer(val delegate: LinkedList<Char>, val limit: Int) {
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

    fun t1(lines: Sequence<String>) {
        for (line in lines) {
            val buffer = Buffer(LinkedList(), 4)

            for (i in line.indices) {
                buffer.add(line[i])
                if (buffer.allEntriesUnique()) {
                    println(i+1)
                    break
                }
            }
        }
    }

    fun t2(lines: Sequence<String>) {
        for (line in lines) {
            val buffer = Buffer(LinkedList(), 14)

            for (i in line.indices) {
                buffer.add(line[i])
                if (buffer.allEntriesUnique()) {
                    println(i+1)
                    break
                }
            }
        }
    }
}

fun main() {
    fun sample() = lines("/aoc/y22/D6_sample.txt")
    fun input() = lines("/aoc/y22/D6_input.txt")

    val d = D6()

    d.t1(sample())
    d.t1(input())
    d.t2(sample())
    d.t2(input())
}