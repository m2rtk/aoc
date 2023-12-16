@file:Suppress("unused")

package aoc.y23.d10

import aoc.Bounds
import aoc.Point
import aoc.Puzzle
import aoc.Direction
import aoc.Direction.*
import aoc.y23.d10.Pipe.*


private val charToPipe = mapOf(
    '|' to V,
    '-' to H,
    'L' to NE,
    'J' to NW,
    'F' to SE,
    '7' to SW,
    '.' to GROUND,
)


internal enum class Pipe(
    val prettyChar: Char,
    vararg directions: Direction,
) {
    V('│', N, S),
    H('─', E, W),
    NE('└', N, E),
    NW('┘', N, W),
    SW('┐', S, W),
    SE('┌', S, E),
    GROUND(' ');

    val directions = directions.toSet()

    fun otherDirection(direction: Direction): Direction {
        if (direction !in directions) {
            throw IllegalArgumentException("direction $direction not found in $this")
        }
        assert(direction in directions)
        return directions.first { it != direction }
    }
}

private fun String.parse(): Pipes {
    val charArr: List<List<Char>> = lines().map { it.toCharArray().toList() }

    fun findStart(): Point {
        for (row in charArr.indices) {
            for (col in charArr[row].indices) {
                val char = charArr[row][col]

                if (char == 'S') {
                    return Point(col, row)
                }
            }
        }
        throw IllegalArgumentException("input has no Start")
    }

    fun Point.isConnected(direction: Direction): Point? {
        val pipe = charToPipe[charArr[y][x]] ?: return null

        return if (direction in pipe.directions) {
            this
        } else {
            null
        }
    }

    val bounds = Bounds(charArr[0].indices, charArr.indices)
    val start = findStart()

    val n = start.translate(N).inBounds(bounds)?.isConnected(S)
    val e = start.translate(E).inBounds(bounds)?.isConnected(W)
    val s = start.translate(S).inBounds(bounds)?.isConnected(N)
    val w = start.translate(W).inBounds(bounds)?.isConnected(E)

    val startPipe = if (n != null && s != null) V
    else if (e != null && w != null) H
    else if (n != null && e != null) NE
    else if (n != null && w != null) NW
    else if (s != null && e != null) SE
    else if (s != null && w != null) SW
    else throw IllegalArgumentException("input has bad Start")

    val pipes: MutableList<List<Pipe>> = mutableListOf()

    for (row in charArr.indices) {
        val line = mutableListOf<Pipe>()
        for (col in charArr[row].indices) {
            val char = charArr[row][col]

            if (char == 'S') {
                line.add(startPipe)
            } else {
                line.add(charToPipe[char] ?: throw IllegalArgumentException("unknown char in input $char"))
            }
        }
        pipes.add(line)
    }

    return Pipes(pipes, start)
}

internal data class Pipes(
    val arr: List<List<Pipe>>,
    val start: Point,
) {

    val bounds = Bounds(arr[0].indices, arr.indices)

    fun withoutCrap(): Pipes {
        val points = runAround()

        val newArr = arr.mapIndexed { rowIndex, row ->
            row.mapIndexed { col, pipe ->
                if (Point(col, rowIndex) in points) {
                    pipe
                } else {
                    GROUND
                }
            }
        }

        return Pipes(newArr, start)
    }

    fun prettyPrint(additional: Map<Point, Char> = mapOf()) {
        for (row in arr.indices) {
            for (col in arr[row].indices) {
                val pipe = arr[row][col]
                val add = additional[Point(col, row)]
                if (add == null) {
                    print(pipe.prettyChar)
                } else {
                    print(add)
                }
            }
            println()
        }
    }

    fun get(point: Point) = arr[point.y][point.x]

    fun runAround(): Set<Point> {
        val points = mutableSetOf<Point>()

        var current = start
        var inDirection = get(start).directions.first()

        while (true) {
            val pipe = get(current)
            val outDirection = pipe.otherDirection(inDirection)

            current = current.translate(outDirection)
            inDirection = outDirection.opposite()
            points.add(current)

            if (current == start) {
                return points
            }
        }
    }

    val grid: Grid by lazy {
        val grid = Grid()

        fun add(point: Point, item: GridItem) {
            if (point.y >= 0 && point.x >= 0) {
                grid.add(point, item)
            }
        }

        fun add(point: Point, template: String) {
            val rows = template.split("\n")
            for ((iRow, row) in rows.withIndex()) {
                for ((iCol, char) in row.withIndex()) {
                    val p = Point(point.x * 3 + iCol, point.y * 3 + iRow)
                    val item = if (char == ' ') GridItem.GROUND else GridItem.WALL
                    add(p, item)
                }
            }
        }

        for ((row, rowArray) in arr.withIndex()) {
            for ((col, pipe) in rowArray.withIndex()) {
                val point = Point(col, row)
                when (pipe) {
                    V -> add(point, " # \n # \n # ")
                    H -> add(point, "   \n###\n   ")
                    NE -> add(point, " # \n ##\n   ")
                    NW -> add(point, " # \n## \n   ")
                    SW -> add(point, "   \n## \n # ")
                    SE -> add(point, "   \n ##\n # ")
                    GROUND -> add(point, "   \n   \n   ")
                }
            }
        }

        grid
    }
}

class D10P1 : Puzzle {
    override fun solve(input: String): Any {
        val pipes = input.parse()
        pipes.prettyPrint()
        pipes.withoutCrap().prettyPrint()
        return pipes.runAround().size / 2
    }
}

class D10P2 : Puzzle {
    override fun solve(input: String): Any {
        val pipes = input.parse().withoutCrap()
        pipes.prettyPrint()

        // grid blows up the pipe map 3-fold
        // each cell in pipes is represented by 3x3 in grid
        val grid = pipes.grid
        grid.prettyPrint()

        val fill = GridFill(grid)
        fill.fullFlood()

        fill.prettyPrint()

        val bounds = grid.bounds()
        val innerBlotch = fill.blotches()
            .filterNot { blotch -> blotch.any { it.x == 0 || it.y == 0 || it.x == bounds.xRange.last || it.y == bounds.yRange.last } }
            .single()

        println("All blotches = ${fill.blotches().size}")

        var count = 0
        for (row in 0 until pipes.arr.size) {
            for (col in 0 until pipes.arr[0].size) {
                fun check(deltaRow: Int, deltaCol: Int): Boolean {
                    val point = Point(3 * col + deltaCol, 3 * row + deltaRow)
                    return innerBlotch.contains(point)
                }

                if (
                    check(0, 0) &&
                    check(1, 0) &&
                    check(2, 0) &&
                    check(0, 1) &&
                    check(1, 1) &&
                    check(2, 1) &&
                    check(0, 2) &&
                    check(1, 2) &&
                    check(2, 2)
                ) {
                    count++
                }
            }
        }

        return count
    }
}