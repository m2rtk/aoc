@file:Suppress("unused")

package aoc.y15.d9

import aoc.Puzzle
import java.util.*

// https://medium.com/@jcamilorada/recursive-permutations-calculation-algorithm-in-kotlin-86233a0a2ee1
private fun permutations(input: List<String>): List<List<String>> {
    val solutions = mutableListOf<List<String>>()
    permutationsRecursive(input, 0, solutions)
    return solutions
}

private fun permutationsRecursive(input: List<String>, index: Int, answers: MutableList<List<String>>) {
    if (index == input.lastIndex) answers.add(input.toList())
    for (i in index..input.lastIndex) {
        Collections.swap(input, index, i)
        permutationsRecursive(input, index + 1, answers)
        Collections.swap(input, i, index)
    }
}

private data class RouteInfo(val start: String, val end: String, val distance: Int)

private fun String.parseDistanceInfo(): RouteInfo {
    val (start, end, distance) = split(Regex(" to | = "), limit = 3)
    return RouteInfo(start, end, distance.toInt())
}

private data class Routes(val routes: List<RouteInfo>) {

    private val distances = mutableMapOf<Pair<String, String>, Int>()

    init {
        routes
            .forEach {
                distances[it.start to it.end] = it.distance
                distances[it.end to it.start] = it.distance
            }
    }

    val allPermutationsWithDistances by lazy {
        val allCities = distances.keys.map { it.first }.toSet()
        permutations(allCities.toList())
            .map {
                val distance = it.windowed(2).fold(0) { acc, cities -> distances[cities[0] to cities[1]]!! + acc }
                it to distance
            }
    }
}

class D9P1 : Puzzle {
    override fun solve(input: String): Any {
        val routes = Routes(input.lines().map { it.parseDistanceInfo() })
        return routes.allPermutationsWithDistances
            .minBy { it.second }
            .second
    }
}

class D9P2 : Puzzle {
    override fun solve(input: String): Any {
        val routes = Routes(input.lines().map { it.parseDistanceInfo() })
        return routes.allPermutationsWithDistances
            .maxBy { it.second }
            .second
    }
}