@file:Suppress("unused")

package aoc.y15

import aoc.Puzzle

private fun String.isNice(): Boolean {
    val vowels = mutableListOf<Char>()
    var doubleSeen = false

    for (i in indices) {
        if (i == length-1) {
            break
        }
        val a = this[i]
        val b = this[i + 1]
        val it = "$a$b"

        if (a in "aeiou") { vowels.add(a) }

        if (a == b) {
            doubleSeen = true
        } else if (it == "ab" || it == "cd" || it == "pq" || it == "xy") {
            return false
        }
    }

    if (this[length-1] in "aeiou") { vowels.add(this[length-1]) }

    return doubleSeen && vowels.size >= 3
}
private fun String.isNice2(): Boolean {
    return false
}

class D5P1 : Puzzle {
    override fun solve(input: String) = input.lines().count { it.isNice() }
}

class D5P2 : Puzzle {
    override fun solve(input: String) = input.lines().count { it.isNice2() }
}
