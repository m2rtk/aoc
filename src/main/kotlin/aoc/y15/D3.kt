@file:Suppress("unused")

package aoc.y15

import aoc.Puzzle

private data class Point(val x: Int, val y: Int) {
    fun translate(xDelta: Int, yDelta: Int): Point {
        return Point(x + xDelta, y + yDelta)
    }
}

class D3P1 : Puzzle {
    override fun solve(input: String): Any {
        val seen = mutableSetOf<Point>()

        var current = Point(0, 0)

        seen.add(current)

        for (c in input) {
            current = when (c) {
                '>' -> current.translate(1, 0)
                '<' -> current.translate(-1, 0)
                'v' -> current.translate(0, 1)
                '^' -> current.translate(0, -1)
                else -> throw RuntimeException()
            }
            seen.add(current)
        }

        return seen.size
    }
}

class D3P2 : Puzzle {
    override fun solve(input: String): Any {
        val seen = mutableSetOf<Point>()

        val santas = mutableListOf(Point(0, 0), Point(0, 0))

        seen.add(Point(0, 0))

        for (i in input.indices) {
            val santaIndex = i % 2

            santas[santaIndex] = when (input[i]) {
                '>' -> santas[santaIndex].translate(1, 0)
                '<' -> santas[santaIndex].translate(-1, 0)
                'v' -> santas[santaIndex].translate(0, 1)
                '^' -> santas[santaIndex].translate(0, -1)
                else -> throw RuntimeException()
            }
            seen.add(santas[santaIndex])
        }

        return seen.size
    }
}