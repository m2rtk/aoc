@file:Suppress("unused")

package aoc.y23.d11

import aoc.Puzzle
import aoc.Point
import kotlin.math.abs
import kotlin.math.exp
import kotlin.math.max
import kotlin.math.min

private data class SpaceImage(val rows: List<List<Char>>) {

    val galaxies: List<Point> by lazy {
        val list = mutableListOf<Point>()

        for (row in rows.indices) {
            for (col in rows[0].indices) {
                if (rows[row][col] == '#') {
                    list.add(Point(col, row))
                }
            }
        }

        list
    }

    fun expand(sizeMultiplier: Int = 2): SpaceImage {
        val expanded = mutableListOf<MutableList<Char>>()

        for (row in rows) {
            if (row.contains('#')) {
                expanded.add(row.toMutableList())
                continue
            }
            for (i in 0 until sizeMultiplier) {
                expanded.add(row.toMutableList())
            }
        }

        fun colContainsStar(index: Int): Boolean {
            return expanded.any { it[index] == '#' }
        }

        var i = 0
        while (i < expanded[0].size) {
            if (colContainsStar(i)) {
                i += 1
                continue
            }

            for (j in 0 until sizeMultiplier-1) {
                expanded.forEach { row ->
                    row.add(i, '.')
                }
            }
            i += sizeMultiplier
        }

        return SpaceImage(expanded)
    }

    fun checkExpansion(): ExpansionResult {
        val expandedRows = mutableListOf<Int>()
        val expandedCols = mutableListOf<Int>()

        for ((i, row) in rows.withIndex()) {
            if (row.contains('#')) {
                continue
            }
            expandedRows.add(i)
        }

        fun colContainsStar(index: Int): Boolean {
            return rows.any { it[index] == '#' }
        }

        for (i in 0 until rows[0].size) {
            if (colContainsStar(i)) {
                continue
            }

            expandedCols.add(i)
        }

        return ExpansionResult(expandedRows, expandedCols)
    }

    fun prettyPrint() {
        for (row in rows) {
            for (cell in row) {
                print(cell)
            }
            println()
        }
    }
}

private data class ExpansionResult(val expandedRows: List<Int>, val expandedCols: List<Int>)

private fun String.parse(): SpaceImage {
    return SpaceImage(lines().map { it.toList() })
}

private fun Point.distance(p: Point): Int {
    return abs(x - p.x) + abs(y - p.y)
}

private fun List<Point>.pairUp(): Set<Pair<Point, Point>> {
    val pairs = mutableSetOf<Pair<Point, Point>>()
    for (a in this) {
        for (b in this) {
            val ab = a to b
            val ba = b to a

            if (ab !in pairs && ba !in pairs && ab != ba) {
                pairs.add(ab)
            }
        }
    }
    return pairs
}

class D11P1 : Puzzle {
    override fun solve(input: String): Any {
        val image = input.parse()
        val expanded = image.expand()

        val pairs = expanded.galaxies.pairUp()
        println("Found ${pairs.size} pairs")

        return pairs.map { it.first.distance(it.second) }.sum()
    }
}

class D11P2 : Puzzle {
    override fun solve(input: String): Any {
        val multiplier = 1_000_000L
        val image = input.parse()
        image.prettyPrint()
        val expansion = image.checkExpansion()

        println(expansion)

        val pairs = image.galaxies.pairUp()
        println("Found ${pairs.size} pairs")

        fun distance(a: Point, b: Point): Long {
            val xMin = min(a.x, b.x)
            val xMax = max(a.x, b.x)
            val yMin = min(a.y, b.y)
            val yMax = max(a.y, b.y)

            val colRange = xMin .. xMax
            val rowRange = yMin .. yMax

            val expandedColsIncluded = expansion.expandedCols.filter { it in colRange }.size
            val expandedRowsIncluded = expansion.expandedRows.filter { it in rowRange }.size

            val result: Long = (colRange.last - colRange.first - expandedColsIncluded + expandedColsIncluded*multiplier) + (rowRange.last - rowRange.first - expandedRowsIncluded + expandedRowsIncluded*multiplier)

//            println("a=$a b=$b res=$result colRange=$colRange rowRange=$rowRange cols=$expandedColsIncluded rows=$expandedRowsIncluded")

            return result
        }

        return pairs.map { distance(it.first, it.second) }.sum()
    }
}