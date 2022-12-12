@file:Suppress("unused")

package aoc.y22.d12

import aoc.Puzzle
import aoc.y22.Point
import java.util.LinkedList


data class HeightMap(val grid: List<List<Char>>) {
    companion object {
        fun parse(input: String): HeightMap {
            return HeightMap(input.lines().map { it.toList() })
        }
    }

    val start = find('S')
    val end = find('E')

    fun possibleNextPositions(p: Point): List<Point> {
        val current = if (start == p) {
            'a'
        } else {
            grid[p.y][p.x]
        }
        return listOf(p.up(), p.down(), p.left(), p.right())
            .filter { it != p }
            .filter {
                val target = grid[it.y][it.x]

                if (target == 'E')  {
                    current + 1 == 'z' || current >= 'z'
                } else {
                    current + 1 == target || current >= target
                }
            }
    }

    fun sameLevelSurroundingPositions(p: Point): List<Point> {
        val current = if (start == p) {
            'a'
        } else {
            grid[p.y][p.x]
        }
        return listOf(p.up(), p.down(), p.left(), p.right())
            .filter { it != p }
            .filter {
                val target = grid[it.y][it.x]
                current == target
            }
    }

    private fun Point.up() = gridTranslate(0, -1)
    private fun Point.down() = gridTranslate(0, 1)
    private fun Point.left() = gridTranslate( -1, 0)
    private fun Point.right() = gridTranslate( 1, 0)
    private fun Point.gridTranslate(xd: Int, yd: Int): Point {
        return this.translate(xd, yd).coerceBounds(
            xMin = 0,
            xMax = grid[0].size - 1,
            yMin = 0,
            yMax = grid.size - 1
        )
    }

    private fun find(char: Char): Point {
        for (y in grid.indices) {
            val line = grid[y]
            val x = line.indexOf(char)
            if (x != -1) {
                return Point(x, y)
            }
        }

        throw RuntimeException()
    }
}

fun collect(map: HeightMap): Set<Point> {
    val q = LinkedList<Point>()
    val start = map.start
    val seen = mutableSetOf<Point>()

    q.add(start)

    while (q.isNotEmpty()) {
        val pos = q.pop()

        for (nextPos in map.sameLevelSurroundingPositions(pos)) {
            if (nextPos !in seen) {
                seen.add(nextPos)
                q.add(nextPos)
            }
        }
    }

    return seen
}

fun go(map: HeightMap, start: Point = map.start): Int {
    val q = LinkedList<Point>()
    val dist = mutableMapOf<Point, Int>()

    dist[start] = 0
    q.add(start)

    while (q.isNotEmpty()) {
        val pos = q.pop()
        val currentDistance = dist[pos]!!

        for (nextPos in map.possibleNextPositions(pos)) {
            val knownDist = dist[nextPos]
            if (knownDist == null) {
                dist[nextPos] = currentDistance + 1
                q.add(nextPos)
            } else if (knownDist > currentDistance + 1) {
                dist[nextPos] = currentDistance + 1
            }
        }
    }

    return dist[map.end]!!
}

class D12P1 : Puzzle {
    override fun solve(input: String): Any {
        val map = HeightMap.parse(input)
        return go(map)
    }
}

class D12P2 : Puzzle {
    override fun solve(input: String): Any {
        val map = HeightMap.parse(input)
        return collect(map).minOfOrNull { go(map, start = it) }!!
    }
}