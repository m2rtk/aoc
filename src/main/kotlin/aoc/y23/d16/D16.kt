@file:Suppress("unused")

package aoc.y23.d16

import aoc.Bounds
import aoc.Direction
import aoc.Direction.*
import aoc.Point
import aoc.Puzzle

private data class Light(val point: Point, val direction: Direction) {
    fun translate(newDirection: Direction) = Light(point.translate(newDirection), newDirection)
}

private data class Grid(val rows: List<List<Char>>) {

    private val bounds = Bounds(rows)
    val seen: MutableSet<Light> = mutableSetOf()

    fun fire(start: Light) {
        val stack = ArrayDeque<Light>()
        stack.add(start)

        while (stack.isNotEmpty()) {
            val light = stack.removeLast()

            if (light.point !in bounds) {
                continue
            }

            if (light in seen) {
                continue
            }

            seen.add(light)

            fun fire(direction: Direction) {
                stack.add(light.translate(direction))
            }

            when (rows[light.point.y][light.point.x]) {
                '.' -> fire(light.direction)
                '/' -> when (light.direction) {
                    N -> fire(E)
                    E -> fire(N)
                    S -> fire(W)
                    W -> fire(S)
                }

                '\\' -> when (light.direction) {
                    N -> fire(W)
                    E -> fire(S)
                    S -> fire(E)
                    W -> fire(N)
                }

                '-' -> when (light.direction) {
                    E, W -> fire(light.direction)
                    N, S -> {
                        fire(E)
                        fire(W)
                    }
                }

                '|' -> when (light.direction) {
                    N, S -> fire(light.direction)
                    E, W -> {
                        fire(N)
                        fire(S)
                    }
                }
            }
        }
    }

    fun energized(): Int = seen.map { it.point }.distinct().size
}

class D16P1 : Puzzle {
    override fun solve(input: String): Any {
        val grid = Grid(input.lines().map { it.toList() })
        grid.fire(Light(Point(0, 0), E))
        return grid.energized()
    }
}

class D16P2 : Puzzle {
    override fun solve(input: String): Any {
        val rows = input.lines().map { it.toList() }
        val h = rows.size
        val w = rows.size

        val results = mutableListOf<Int>()

        fun go(x: Int, y: Int, direction: Direction) {
            val point = Point(x, y)
            val grid = Grid(rows)
            grid.fire(Light(point, direction))
            val result = grid.energized()
            results.add(result)
        }

        for (row in rows.indices) {
            go(0, row, E)
            go(w-1, row, W)
        }

        for (col in rows[0].indices) {
            go(col, 0, S)
            go(col, h-1, N)
        }

        return results.max()
    }
}