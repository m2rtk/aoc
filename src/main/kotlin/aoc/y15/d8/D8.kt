@file:Suppress("unused")

package aoc.y15.d8

import aoc.Puzzle

private data class StringLiteral(
    val raw: String,
) {
    val codeLength = raw.length
    val memoryLength: Int by lazy {
        var memoryNeeded = 0

        var remaining = raw

        fun eat(n: Int) {
            remaining = remaining.substring(n)
        }

        while (remaining.isNotBlank()) {
            val char = remaining[0]

            if (char == '"') {
                eat(1)
            } else if (char == '\\') {
                val escaped = remaining[1]

                if (escaped == '\\') {
                    eat(2)
                    memoryNeeded += 1
                } else if (escaped == '\"') {
                    eat(2)
                    memoryNeeded += 1
                } else if (escaped == 'x') {
                    eat(4)
                    memoryNeeded += 1
                }
            } else {
                eat(1)
                memoryNeeded += 1
            }
        }

        memoryNeeded
    }
    val encoded: String by lazy {
        var encoded = ""

        for (char in raw) {
            if (char == '"') {
                encoded += """\""""
            } else if (char == '\\') {
                encoded += """\\"""
            } else {
                encoded += char
            }
        }

        encoded = "\"" + encoded + "\""
        encoded
    }

    val encodedLength = encoded.length
}

class D8P1 : Puzzle {
    override fun solve(input: String): Any {
        val strings = input.lines().map { StringLiteral(it) }
        val totalCode = strings.sumOf { it.codeLength }
        val totalMemory = strings.sumOf { it.memoryLength }
        return totalCode - totalMemory
    }
}

class D8P2 : Puzzle {
    override fun solve(input: String): Any {
        val strings = input.lines().map { StringLiteral(it) }
        val totalCode = strings.sumOf { it.codeLength }
        val totalEncodedCode = strings.sumOf { it.encodedLength }
        return totalEncodedCode - totalCode
    }
}