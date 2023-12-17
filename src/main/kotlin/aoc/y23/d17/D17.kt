@file:Suppress("unused")

package aoc.y23.d17

import aoc.Bounds
import aoc.Point
import aoc.Puzzle
import java.util.Comparator
import java.util.PriorityQueue


private data class CityMap(val grid: List<List<Int>>) {
    val bounds = Bounds(grid)

    fun neighbours(point: Point): List<Point> {
        return point
            .surroundingHorizontalVerticalPoints(bounds)
    }

    fun get(point: Point): Int = grid[point.y][point.x]

    fun prettyString(path: List<Point> = listOf()): String {
        val sb = StringBuilder()
        val pathSet = path.toSet()

        for (row in grid.indices) {
            for (col in grid[0].indices) {
                val point = Point(col, row)

                if (point in pathSet) {
                    sb.append('.')
                } else {
                    sb.append(get(point))
                }
            }
            sb.append('\n')
        }
        return sb.toString().trim()
    }
}

private fun List<Point>.isStraight(): Boolean {
    if (this.isEmpty()) {
        return false
    }
    return all { it.x == this[0].x } || all { it.y == this[0].y }
}

private fun aStar(map: CityMap, start: Point, goal: Point, h: (Point) -> Int = { 0 }): List<Point> {
    val cameFrom = mutableMapOf<Point, Point>()

    val gScore = mutableMapOf<Point, Int>().withDefault { Int.MAX_VALUE }
    gScore[start] = 0

    val fScore = mutableMapOf<Point, Int>().withDefault { Int.MAX_VALUE }
    fScore[start] = h(start)

    val openSet = PriorityQueue<Point>(Comparator.comparingInt { fScore.getValue(it) })
    openSet.add(start)

    fun pathTo(current: Point, next: Point, limit: Int? = null): List<Point> {
        val path = mutableListOf(next)

        var c = current
        while (true) {
            path.addFirst(c)
            val prev = cameFrom[c] ?: break

            if (path.size == limit) {
                break
            }
            c = prev
        }

        return path
    }

    while (openSet.isNotEmpty()) {
        val current = openSet.poll()!!

        if (current == goal) {
            val path = mutableListOf<Point>()

            var c = current
            while (true) {
                path.addFirst(c)
                val prev = cameFrom[c] ?: break
                c = prev
            }

            return path
        }

        for (neighbor in map.neighbours(current)) {
            val threePath = pathTo(current, neighbor, limit = 4)
            if (threePath.size == 4 && threePath.isStraight()) {
                continue
            }

            val tentativeGScore = gScore.getValue(current) + map.get(current)

            if (tentativeGScore < gScore.getValue(neighbor)) {
                cameFrom[neighbor] = current
                gScore[neighbor] = tentativeGScore
                fScore[neighbor] = tentativeGScore + h(neighbor)
                if (neighbor !in openSet) {
                    openSet.add(neighbor)
                }
            }
        }
    }

    println(gScore)
    println(fScore)
    println(cameFrom)

    throw RuntimeException()
}


private fun String.parse(): CityMap = CityMap(lines().map { it.toList().map { c -> c.digitToInt() } })

class D17P1 : Puzzle {

    override fun solve(input: String): Any {
        val map = input.parse()

        println(map.prettyString())
        val path = aStar(map, Point(0,0), Point(map.bounds.width-1, map.bounds.height-1))
        println()
        println(map.prettyString(path))

        return path.sumOf { map.get(it) }
    }
}

class D17P2 : Puzzle {
    override fun solve(input: String): Any {
        return -1
    }
}