@file:Suppress("unused")

package aoc.y22.d8

import aoc.Puzzle

class D8P1 : Puzzle {
    override fun solve(input: String): Any {
        val lines = input.lines().map { it.toList().map { i -> i.toString().toInt() } }
        val w = lines[0].size
        val h = lines.size
        val edgeTrees = lines.size * 2 + (lines[0].size - 2) * 2

        var counter = 0
        for (x in 1..w - 2) {
            outer@ for (y in 1..h - 2) {
                val tree = lines[y][x]

                val left = (0 until x)
                    .map { lines[y][it] }
                    .all { it < tree }

                val right = (x + 1 until w)
                    .map { lines[y][it] }
                    .all { it < tree }

                val up = (0 until y)
                    .map { lines[it][x] }
                    .all { it < tree }

                val down = (y+1 until h)
                    .map { lines[it][x] }
                    .all { it < tree }

                if (left || right || up || down) {
                    counter++
                }
            }
        }

        return edgeTrees + counter
    }
}

class D8P2 : Puzzle {

    private fun List<Int>.takeUntil(target: Int): List<Int> {
        val acc = mutableListOf<Int>()
        for (i in this) {
            if (i < target) {
                acc.add(i)
            } else {
                acc.add(i)
                return acc
            }
        }
        return acc
    }

    override fun solve(input: String): Any {
        val lines = input.lines().map { it.toList().map { i -> i.toString().toInt() } }
        val w = lines[0].size
        val h = lines.size

        var max = 0
        for (x in 0 until w) {
            outer@ for (y in 0 until h) {
                val tree = lines[y][x]

                val up = (y-1 downTo  0)
                    .map { lines[it][x] }
                    .takeUntil(tree)
                    .count()

                val left = (x-1 downTo  0)
                    .map { lines[y][it] }
                    .takeUntil(tree)
                    .count()

                val down = (y+1 until h)
                    .map { lines[it][x] }
                    .takeUntil(tree)
                    .count()

                val right = (x+1 until w)
                    .map { lines[y][it] }
                    .takeUntil(tree)
                    .count()

                val sum = (left * right * up * down)

                if (sum > max) {
                    max = sum
                }
            }
        }

        return max
    }
}