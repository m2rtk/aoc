@file:Suppress("unused")

package aoc.y15

import aoc.Puzzle

private fun String.isNice11(): Boolean {
    val vowels = mutableListOf<Char>()

    for (c in this) {
        if (c in "aeiou") {
            vowels.add(c)
            if (vowels.size == 3) {
                return true
            }
        }
    }

    return false
}

private fun String.isNice12(): Boolean {
    for (it in windowedSequence(2)) {
        if (it[0] == it[1]) {
            return true
        }
    }

    return false
}

private fun String.isNice13(): Boolean {
    for (it in windowedSequence(2)) {
        if (it == "ab" || it == "cd" || it == "pq" || it == "xy") {
            return false
        }
    }

    return true
}

private fun String.isNice1(): Boolean = isNice11() && isNice12() && isNice13()

private fun String.isNice21(): Boolean {
    val seenPairs = mutableMapOf<String, Int>()

    for (i in 0 until length - 1) {
        val a = this[i]
        val b = this[i + 1]
        val pair = "$a$b"

        val existing = seenPairs[pair]

        if (existing != null && existing + 1 != i) {
            return true
        } else if (existing == null) {
            seenPairs[pair] = i
        }
    }

    return false
}

private fun String.isNice22(): Boolean {
    for (it in this.windowedSequence(3)) {
        if (it[0] == it[2]) {
            return true
        }
    }
    return false
}

private fun String.isNice2(): Boolean = isNice21() && isNice22()

class D5P1 : Puzzle {
    override fun solve(input: String) = input.lines().count { it.isNice1() }
}

class D5P2 : Puzzle {
    override fun solve(input: String) = input.lines().count { it.isNice2() }
}
