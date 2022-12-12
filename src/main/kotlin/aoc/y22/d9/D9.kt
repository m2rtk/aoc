@file:Suppress("unused")

package aoc.y22.d9

import aoc.Puzzle
import aoc.y22.Point
import kotlin.text.StringBuilder

enum class Direction {
    R, U, L, D
}

private val surroundingDeltas = listOf(
    Point(1, 1),
    Point(1, 0),
    Point(1, -1),
    Point(0, 1),
    Point(0, 0),
    Point(0, -1),
    Point(-1, 1),
    Point(-1, 0),
    Point(-1, -1),
)

data class Movee(val dir: Direction, val amount: Int) {
    companion object {
        fun parse(it: String): Movee {
            val (a, b) = it.split(" ", limit = 2)
            return Movee(Direction.valueOf(a), b.toInt())
        }
    }
}

fun Point.touches(p: Point): Boolean {
    for (delta in surroundingDeltas) {
        if (x + delta.x == p.x && y + delta.y == p.y) {
            return true
        }
    }
    return false
}

fun Point.attach(t: Point): Point {
    val h = this
    if (h.touches(t)) {
        return t
    }

    val xDiff = h.x - t.x
    val yDiff = h.y - t.y

    // if the head is ever two step directly up,down,left,right - tail follows
    return if (t.y == h.y) {
        return if (t.x > h.x) {
            Point(h.x + 1, t.y)
        } else {
            Point(h.x - 1, t.y)
        }
    } else if (t.x == h.x) {
        return if (t.y > h.y) {
            Point(t.x, h.y + 1)
        } else {
            Point(t.x, h.y - 1)
        }
    } else if (xDiff > 0 && yDiff > 0) {
        Point(t.x + 1, t.y + 1)
    } else if (xDiff > 0) {
        Point(t.x + 1, t.y - 1)
    } else if (yDiff > 0) {
        Point(t.x - 1, t.y + 1)
    } else {
        Point(t.x - 1, t.y - 1)
    }
}

class Grid(
    points: List<Pair<Char, Point>>,
    minSize: Int = 2
) {

    private val p: List<Point> = points.map { it.second }
    private val c: List<Char> = points.map { it.first }

    private val xMin = points.minOf { it.second.x }.coerceAtMost(-minSize)
    private val xMax = points.maxOf { it.second.x }.coerceAtLeast(minSize)
    private val yMin = points.minOf { it.second.y }.coerceAtMost(-minSize)
    private val yMax = points.maxOf { it.second.y }.coerceAtLeast(minSize)

    fun print() {
        val lines = mutableListOf<String>()
        for (y in yMin..yMax) {
            val line = StringBuilder()
            for (x in xMin..xMax) {
                val i = p.indexOf(Point(x, y))
                line.append(" ")
                if (i != -1) {
                    line.append(c[i])
                } else {
                    line.append(".")
                }
            }
            lines.add(line.toString())
        }

        lines.reversed().forEach { println(it) }
    }
}

data class Rope(val tailParts: Int) {

    val tailSeen: MutableSet<Point> = mutableSetOf(Point(0, 0))
    private val tail: MutableList<Point> = (0..tailParts).map { Point(0, 0) }.toMutableList()

    fun apply(move: Movee) {
        for (i in 0 until move.amount) {
            val h = tail[0]
            when (move.dir) {
                Direction.R -> tail[0] = Point(h.x + 1, h.y)
                Direction.U -> tail[0] = Point(h.x, h.y + 1)
                Direction.L -> tail[0] = Point(h.x - 1, h.y)
                Direction.D -> tail[0] = Point(h.x, h.y - 1)
            }

            for (j in 0 until tail.size - 1) {
                tail[j + 1] = tail[j].attach(tail[j + 1])
            }

            tailSeen.add(tail[tail.size - 1])
        }
    }

    fun print() {
        Grid(tail.mapIndexed { k, p -> k.toString()[0] to p } + tailSeen.map { '#' to it }).print()
    }
}

class D9P1 : Puzzle {
    override fun solve(input: String): Any {
        val rope = Rope(1)

        input.lineSequence()
            .map { Movee.parse(it) }
            .forEach { rope.apply(it) }


        return rope.tailSeen.size
    }
}

class D9P2 : Puzzle {
    override fun solve(input: String): Any {
        val rope = Rope(9)

        input.lineSequence()
            .map { Movee.parse(it) }
            .forEach { rope.apply(it) }

        return rope.tailSeen.size
    }
}