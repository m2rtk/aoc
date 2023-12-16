@file:Suppress("unused")

package aoc.y23.d14

import aoc.Direction
import aoc.Direction.*
import aoc.Puzzle

private data class Platform(val grid: List<List<Char>>) {

    fun tilt(direction: Direction): Platform {
        val grid: List<MutableList<Char>> = grid.map { it.toMutableList() }
        when (direction) {
            N -> for (col in grid[0].indices) {
                var openRow = -1
                for (row in grid.indices) {
                    val item = grid[row][col]

                    when (item) {
                        'O' -> {
                            if (openRow != -1) {
                                grid[openRow][col] = 'O'
                                grid[row][col] = '.'

                                if (grid[openRow + 1][col] == '.') {
                                    openRow += 1
                                }
                            }
                        }

                        '.' -> {
                            if (openRow == -1) {
                                openRow = row
                            }
                        }

                        '#' -> {
                            openRow = -1
                        }
                    }
                }
            }
            S -> for (col in grid[0].indices) {
                var openRow = -1
                for (row in grid.indices.reversed()) {
                    val item = grid[row][col]

                    when (item) {
                        'O' -> {
                            if (openRow != -1) {
                                grid[openRow][col] = 'O'
                                grid[row][col] = '.'

                                if (grid[openRow - 1][col] == '.') {
                                    openRow -= 1
                                }
                            }
                        }

                        '.' -> {
                            if (openRow == -1) {
                                openRow = row
                            }
                        }

                        '#' -> {
                            openRow = -1
                        }
                    }
                }
            }

            E -> for (row in grid.indices) {
                var openCol = -1
                for (col in grid[row].indices.reversed()) {
                    val item = grid[row][col]

                    when (item) {
                        'O' -> {
                            if (openCol != -1) {
                                grid[row][openCol] = 'O'
                                grid[row][col] = '.'

                                if (grid[row][openCol - 1] == '.') {
                                    openCol -= 1
                                }
                            }
                        }

                        '.' -> {
                            if (openCol == -1) {
                                openCol = col
                            }
                        }

                        '#' -> {
                            openCol = -1
                        }
                    }
                }
            }
            W -> for (row in grid.indices) {
                var openCol = -1
                for (col in grid[row].indices) {
                    val item = grid[row][col]

                    when (item) {
                        'O' -> {
                            if (openCol != -1) {
                                grid[row][openCol] = 'O'
                                grid[row][col] = '.'

                                if (grid[row][openCol + 1] == '.') {
                                    openCol += 1
                                }
                            }
                        }

                        '.' -> {
                            if (openCol == -1) {
                                openCol = col
                            }
                        }

                        '#' -> {
                            openCol = -1
                        }
                    }
                }
            }
        }

        return Platform(grid)
    }

    val totalLoad: Int by lazy {
        val height = grid.size
        grid.foldIndexed(0) { index, acc, row ->
            val rowsToSouthEdge = height - index
            val rocksOnRow = row.count { it == 'O' }

            acc + rowsToSouthEdge * rocksOnRow
        }
    }

    fun prettyString(): String {
        val sb = StringBuilder()

        for (row in grid) {
            for (col in row) {
                sb.append(col)
            }
            sb.append('\n')
        }

        return sb.toString().trim()
    }
}

private fun String.parse(): Platform = Platform(lines().map { it.toMutableList() }.toMutableList())

class D14P1 : Puzzle {
    fun tilt(input: String, direction: Direction): String {
        return input.parse().tilt(direction).prettyString()
    }

    override fun solve(input: String): Any {
        return input.parse().tilt(N).totalLoad
    }
}

class D14P2 : Puzzle {

    private fun Platform.cycle() = tilt(N).tilt(W).tilt(S).tilt(E)

    fun cycle(input: String) = input.parse().cycle().prettyString()

    override fun solve(input: String): Any {
        val platform = input.parse()
        val times = 1_000_000_000

        val platforms = mutableListOf(platform)

        for (i in 0 until times){
            val new = platforms.last.cycle()

            if (new in platforms) {
                val loopingPlatforms = platforms.subList(platforms.indexOf(new), platforms.size)
                return loopingPlatforms[(times - i - 1) % loopingPlatforms.size].totalLoad
            } else {
                platforms.add(new)
            }
        }

        return platforms.last.totalLoad
    }
}
