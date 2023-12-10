package aoc.y23.d10

import aoc.Bounds
import aoc.Point
import java.util.*

enum class GridItem {
    GROUND, WALL
}

class Grid {

    val points: MutableMap<Point, GridItem> = mutableMapOf()

    fun add(point: Point, item: GridItem) {
        points[point] = item
    }

    fun get(point: Point): GridItem? {
        return points[point]
    }

    fun bounds() = Bounds(
        0..points.maxBy { it.key.x }.key.x,
        0..points.maxBy { it.key.y }.key.y,
    )

    fun prettyPrint(additional: Map<Point, Char> = mapOf()) {
        fun get(row: Int, col: Int): Char {
            val add = additional.get(Point(col, row))

            if (add != null) {
                return add
            }

            val item = points[Point(col, row)] ?: GridItem.GROUND
            return when (item) {
                GridItem.GROUND -> ' '
                GridItem.WALL -> '#'
            }
        }

        val maxX = points.maxBy { it.key.x }.key.x
        val maxY = points.maxBy { it.key.y }.key.y

        for (row in 0..maxY) {
            for (col in 0..maxX) {
                print(get(row, col))
                print(get(row, col))
            }
            println()
        }
    }
}

class GridFill(private val grid: Grid) {

    private val bounds = grid.bounds()
    private val blotches = mutableListOf<Set<Point>>()

    fun fullFlood() {
        for (point in grid.points.keys) {
            flood(point)
        }
    }

    fun blotches(): List<Set<Point>> = blotches

    private fun flood(start: Point) {
        if (grid.get(start) == GridItem.WALL) {
            return
        }

        if (blotches.any { start in it }) {
            return
        }

        println("Flooding from $start")

        val seen = mutableSetOf<Point>()
        val stack = LinkedList<Point>()
        stack.add(start)

        while (stack.isNotEmpty()) {
            val n = stack.pop()

            if (n in seen || n !in bounds || grid.get(n) == GridItem.WALL) {
                continue
            }

            seen.add(n)

            stack.push(n.translate(xd =  1))
            stack.push(n.translate(xd = -1))
            stack.push(n.translate(yd =  1))
            stack.push(n.translate(yd = -1))
        }

        blotches.add(seen)
    }

    fun prettyPrint() {
        for (row in bounds.yRange) {
            for (col in bounds.xRange) {
                val point = Point(col, row)

                var printed = false
                for ((i, blotch) in blotches.withIndex()) {
                    if (point in blotch) {
                        print(i.toString().first())
                        print(i.toString().first())
                        printed = true
                        break
                    }
                }

                if (!printed) {
                    val item = grid.get(point) ?: GridItem.GROUND
                    val char = if (item == GridItem.GROUND) ' ' else '.'
                    print(char)
                    print(char)
                }
            }
            println()
        }
    }
}
