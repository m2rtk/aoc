@file:Suppress("unused")

package aoc.y22.d15

import aoc.Puzzle
import aoc.Point
import kotlin.math.abs

data class SensorAndBeacon(val sensor: Point, val beacon: Point) {
    companion object {
        fun parse(line: String): SensorAndBeacon {
            val (a, b) = line
                .filter { it in "0123456789,:-" }
                .split(":")
                .map { Point.parse(it) }
            return SensorAndBeacon(a, b)
        }
    }

    val distance = abs(sensor.x - beacon.x) + abs(sensor.y - beacon.y)
}

sealed interface Item {
    fun toChar(): Char
}

object Beacon : Item {
    override fun toChar() = 'B'
}

object Sensor : Item {
    override fun toChar() = 'S'
}

object Cover : Item {
    override fun toChar() = '#'
}

operator fun IntRange.minus(other: IntRange): List<IntRange> {
    if (this.first >= other.first && this.last <= other.last) { // other covers this completely
        return listOf()
    }

    if (this.first >= other.first && this.last >= other.last && other.last in this) { // other covers start
        return listOf(IntRange(other.last + 1, last))
    }

    if (this.first <= other.first && this.last <= other.last && other.first in this) { // other covers end
        return listOf(IntRange(first, other.first - 1))
    }

    if (this.first < other.first && this.last > other.last) { // other is in the middle
        return listOf(IntRange(first, other.first - 1), IntRange(other.last + 1, last))
    }

    return listOf(this)
}

fun List<IntRange>.findNotCoveredRange(min: Int = 0, max: Int = 4_000_000): List<IntRange> {
    var left = mutableListOf(IntRange(min, max))

    for (range in this.sortedBy { it.first }) {
        val new = mutableListOf<IntRange>()

        for (l in left) {
            val result = l - range
            new.addAll(result)
        }

        if (new.isEmpty()) {
            return new
        }

        left = new
    }

    return left
}

fun List<IntRange>.sum(): Int {
    val s = mutableSetOf<Int>()

    for (range in this) {
        for (i in range) {
            s.add(i)
        }
    }

    return s.size
}

class Grid(
    private val minSize: Int = 2,
) {

    private val items = mutableMapOf<Point, Item>()
    private val pairs = mutableListOf<SensorAndBeacon>()

    fun add(sb: SensorAndBeacon) {
        items[sb.beacon] = Beacon
        items[sb.sensor] = Sensor
        pairs.add(sb)
    }

    fun addCoverage() {
        pairs.forEach { cover(it) }
    }

    fun coveringRangesOf(y: Int): List<IntRange> {
        return pairs.mapNotNull { it.coveringRangeOf(y) }
    }

    fun find(min: Int, max: Int): Point {
        fun findNotCoveredPoint(y: Int, ranges: List<IntRange>): Point? {
            val found = ranges.findNotCoveredRange(min, max)

            if (found.isEmpty()) {
                return null
            }

            return Point(found[0].last, y)
        }

        for (y in min..max) {
            val ranges = coveringRangesOf(y)
            val point = findNotCoveredPoint(y, ranges)

            if (point != null) {
                return point
            }
        }

        throw NoSuchElementException()
    }

    /**
     * for making pretty picture
     */
    private fun cover(sb: SensorAndBeacon) {
        val yMin = sb.sensor.y - sb.distance
        val yMax = sb.sensor.y + sb.distance

        for (y in yMin..yMax) {
            val size = sb.distance - abs(sb.sensor.y - y)
            val xMin = sb.sensor.x - size
            val xMax = sb.sensor.x + size
            for (x in xMin..xMax) {
                val point = Point(x, y)

                if (point !in items) {
                    items[point] = Cover
                }
            }
        }
    }

    private fun SensorAndBeacon.coveringRangeOf(targetY: Int): IntRange? {
        val yMin = sensor.y - distance
        val yMax = sensor.y + distance

        if (targetY !in IntRange(yMin, yMax)) {
            return null
        }

        val size = distance - abs(sensor.y - targetY)
        val xMin = sensor.x - size
        val xMax = sensor.x + size

        return IntRange(xMin, xMax)
    }

    fun get(x: Int, y: Int): Item? = items[Point(x, y)]

    private val p: Set<Point>
        get() = items.keys

    private val xMin: Int
        get() = p.minOf { it.x }.coerceAtMost(-minSize)
    private val xMax: Int
        get() = p.maxOf { it.x }.coerceAtLeast(minSize)
    private val yMin: Int
        get() = p.minOf { it.y }.coerceAtMost(-minSize)
    private val yMax: Int
        get() = p.maxOf { it.y }.coerceAtLeast(minSize)

    fun print(min: Int? = null, max: Int? = null) {
        val lines = mutableListOf<String>()

        val yMin = kotlin.math.max(yMin, min ?: yMin)
        val xMin = kotlin.math.max(xMin, min ?: xMin)
        val yMax = kotlin.math.min(yMax, max ?: yMax)
        val xMax = kotlin.math.min(xMax, max ?: xMax)

        for (y in yMin..yMax) {
            val line = StringBuilder()
            line.append(y.toString().padStart(3)).append(" ")
            for (x in xMin..xMax) {
                val i = items[Point(x, y)]
                if (i != null) {
                    line.append(i.toChar())
                } else {
                    line.append(".")
                }
            }
            lines.add(line.toString())
        }

        println("    $yMin")
        lines.forEach { println(it) }
    }
}

class D15P1 : Puzzle {
    override fun solve(input: String): Any {
        val grid = Grid()
        input.lines()
            .map { SensorAndBeacon.parse(it) }
            .forEach { grid.add(it) }

        val y = 2_000_000
        return grid.coveringRangesOf(y).sum() - 1
    }
}

class D15P2 : Puzzle {
    override fun solve(input: String): Any {
        val grid = Grid()
        input.lines().map { SensorAndBeacon.parse(it) }.forEach { grid.add(it) }
        val point = grid.find(0, 4_000_000)
        return point.x.toLong() * 4_000_000L + point.y.toLong()
    }
}