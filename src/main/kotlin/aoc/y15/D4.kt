@file:Suppress("unused")

package aoc.y15

import aoc.Puzzle
import java.math.BigInteger
import java.security.MessageDigest

private val md = MessageDigest.getInstance("MD5")
private fun md5(input: String): String {
    return BigInteger(1, md.digest(input.toByteArray())).toString(16).padStart(32, '0')
}

private fun goHard(input: String, neededZeroes: Int): Int {
    val needed = "0".repeat(neededZeroes)
    var i = 1
    while (true) {
        if (md5(input + i.toString()).startsWith(needed)) {
            return i
        }
        i++
    }
}

class D4P1 : Puzzle {
    override fun solve(input: String) = goHard(input, 5)
}

class D4P2 : Puzzle {
    override fun solve(input: String) = goHard(input, 6)
}