@file:Suppress("unused")

package aoc.y23.d2

import aoc.Puzzle

private enum class Color {
    BLUE, RED, GREEN
}

private data class Game(val id: Int, val rounds: List<Round>) {
    data class Round(val cubes: List<Pair<Int, Color>>)

    val cubes: Map<Color, Int> by lazy {
        val map = mutableMapOf(Color.BLUE to 0, Color.RED to 0, Color.GREEN to 0)

        for (round in rounds) {
            for (cube in round.cubes) {
                if (map[cube.second]!! < cube.first) {
                    map[cube.second] = cube.first
                }
            }
        }

        map
    }
}

private fun String.parseGame(): Game {
    val (id, rest1) = removePrefix("Game ").split(":", limit = 2)

    fun String.parseRound(): Game.Round {
        val cubes = split(",")
            .map { it.trim() }
            .map {
                val (number, color) = it.split(" ")
                number.toInt() to Color.valueOf(color.uppercase())
            }
        return Game.Round(cubes)
    }

    return Game(
        id = id.toInt(),
        rounds = rest1.split(";").map { it.parseRound() }
    )
}

class D2P1 : Puzzle {
    override fun solve(input: String): Any {
        return input.lineSequence()
            .map { it.parseGame() }
            .filter { it.usesLessThan(12, Color.RED) && it.usesLessThan(13, Color.GREEN) && it.usesLessThan(14, Color.BLUE) }
            .sumOf { it.id }
    }

    private fun Game.usesLessThan(limit: Int, color: Color): Boolean {
        return cubes[color]!! <= limit
    }
}

class D2P2 : Puzzle {
    override fun solve(input: String): Any {
        return input.lineSequence()
            .map { it.parseGame() }
            .map { it.cubes.values.reduce { acc, i -> acc * i } }
            .sum()
    }
}