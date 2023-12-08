@file:Suppress("unused")

package aoc.y23.d6

import aoc.Puzzle

data class Race(val time: Int, val distance: Int) {

    val waysToBeat: Int by lazy {
        (0..time).count { canBeat(it) }
    }

    private fun canBeat(timeToHold: Int): Boolean {
        val timeLeft = time - timeToHold
        return timeLeft * timeToHold > distance
    }
}

private fun String.parseRaces(): List<Race> {
    val lines = lines()
    val times = lines[0].removePrefix("Time:").trim().split(Regex("\\s+")).map { it.toInt() }
    val distances = lines[1].removePrefix("Distance:").trim().split(Regex("\\s+")).map { it.toInt() }
    return times.zip(distances) { t, d -> Race(t, d)}
}

data class LongRace(val time: Long, val distance: Long) {

    val waysToBeat: Int by lazy {
        (0..time).count { canBeat(it) }
    }

    private fun canBeat(timeToHold: Long): Boolean {
        val timeLeft = time - timeToHold
        return timeLeft * timeToHold > distance
    }
}

private fun String.parseRace(): LongRace {
    val lines = lines()
    val time = lines[0].replace(Regex("\\s+"), "").removePrefix("Time:").toLong()
    val distance = lines[1].replace(Regex("\\s+"), "").removePrefix("Distance:").toLong()
    return LongRace(time, distance)
}

class D6P1 : Puzzle {
    override fun solve(input: String): Any {
        return input.parseRaces().fold(1) { acc, race -> race.waysToBeat * acc }
    }
}

class D6P2 : Puzzle {
    override fun solve(input: String): Any {
        return input.parseRace().waysToBeat
    }
}