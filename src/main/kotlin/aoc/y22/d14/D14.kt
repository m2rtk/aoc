@file:Suppress("unused")

package aoc.y22.d14

import aoc.Puzzle
import aoc.y22.Point
import kotlin.math.max
import kotlin.math.min

sealed interface Item {
    fun toChar(): Char
}

object Sand : Item {
    override fun toChar() = 'o'
}

object Rock : Item {
    override fun toChar() = '#'
}

data class GridItem(val point: Point, val item: Item) {
    override fun hashCode(): Int = point.hashCode()
    override fun equals(other: Any?) = (other as GridItem).point == point
}

class Grid(
    private val hasFloor: Boolean = false,
    private val minSize: Int = 2,
) {

    private val items = mutableMapOf<Point, Item>()
    private var floor: Int = 0

    fun add(point: Point, item: Item) {
        items[point] = item

        if (item is Rock && point.y >= floor - 1) {
            floor = point.y + 2
        }
    }

    val sandCount: Int
        get() = items.values.count { it == Sand }

    fun get(x: Int, y: Int): Item? {
        return if (hasFloor && y == floor) {
            Rock
        } else {
            items[Point(x, y)]
        }
    }

    fun drop(): Boolean {
        var x = 500
        var y = 0

        fun moveDown(): Boolean {
            val next = get(x, y + 1)

            return if (next == null) {
                y += 1
                true
            } else {
                false
            }
        }

        fun moveLeft(): Boolean {
            val next = get(x - 1, y + 1)

            return if (next == null) {
                y += 1
                x -= 1
                true
            } else {
                false
            }
        }

        fun moveRight(): Boolean {
            val next = get(x + 1, y + 1)

            return if (next == null) {
                y += 1
                x += 1
                true
            } else {
                false
            }
        }

        fun fellOff(): Boolean {
            return y > floor
        }

        fun blocksInput(): Boolean {
            return x == 500 && y == 0
        }

        while (true) {
            val moved = moveDown() || moveLeft() || moveRight()

            if (blocksInput() || fellOff()) {
                return false
            }

            if (!moved) {
                add(Point(x, y), Sand)
                return true
            }
        }
    }

    private val p: Set<Point>
        get() = items.keys

    private val xMin: Int = 0
    private val xMax: Int
        get() = p.maxOf { it.x }.coerceAtLeast(minSize)
    private val yMin: Int = 0
    private val yMax: Int
        get() = p.maxOf { it.y }.coerceAtLeast(minSize)

    fun print() {
        val lines = mutableListOf<String>()
        for (y in yMin..yMax) {
            val line = StringBuilder()
            for (x in xMin..xMax) {
                if (x == 500 && y == 0) {
                    line.append('+')
                    continue
                }
                val i = items[Point(x, y)]
                if (i != null) {
                    line.append(i.toChar())
                } else {
                    line.append(".")
                }
            }
            lines.add(line.toString())
        }

        lines.forEach { println(it) }
    }
}

fun parse(input: String, hasFloor: Boolean = false): Grid {
    val grid = Grid(hasFloor)
    for (line in input.lines()) {
        val pieces = line.split(" -> ")
            .map { it.split(",") }
            .map { Point(it[0].toInt(), it[1].toInt()) }

        for ((start, end) in pieces.windowed(2)) {
            val xMin = min(start.x, end.x)
            val xMax = max(start.x, end.x)
            val yMin = min(start.y, end.y)
            val yMax = max(start.y, end.y)

            for (x in xMin..xMax) {
                for (y in yMin..yMax) {
                    grid.add(Point(x, y), Rock)
                }
            }
        }
    }

    return grid
}

class D14P1 : Puzzle {
    override fun solve(input: String): Any {
        val grid = parse(input)

        while (true) {
            val didNotFallOff = grid.drop()
            val fellOff = !didNotFallOff
            if (fellOff) {
                break
            }
        }

        return grid.sandCount
    }
}

class D14P2 : Puzzle {
    override fun solve(input: String): Any {
        val grid = parse(input, true)

        while (true) {
            val stuckOnInput = !grid.drop()
            if (stuckOnInput) {
                break
            }
        }

        return grid.sandCount + 1
    }
}