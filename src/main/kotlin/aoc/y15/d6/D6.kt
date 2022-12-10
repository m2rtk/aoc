@file:Suppress("unused")

package aoc.y15.d6

import aoc.Puzzle
import kotlin.math.abs
import kotlin.math.max

class Grid {

    private val arr = (0 until 1000).map { IntArray(1000) }

    private fun iterate(ax: Int, ay: Int, bx: Int, by: Int, function: (Int) -> Int) {
        for (x in ax..bx) {
            for (y in ay..by) {
                arr[x][y] = function(arr[x][y])
            }
        }
    }

    val sum: Int
        get() = arr.sumOf { it.sum() }

    fun apply1(command: Command) {
        when (command.action) {
            "on" -> iterate(command.ax, command.ay, command.bx, command.by) { 1 }
            "off" -> iterate(command.ax, command.ay, command.bx, command.by) { 0 }
            "toggle" -> iterate(command.ax, command.ay, command.bx, command.by) { abs(it - 1) }
        }
    }

    fun apply2(command: Command) {
        when (command.action) {
            "on" -> iterate(command.ax, command.ay, command.bx, command.by) { it + 1 }
            "off" -> iterate(command.ax, command.ay, command.bx, command.by) { max(it - 1, 0) }
            "toggle" -> iterate(command.ax, command.ay, command.bx, command.by) { it + 2 }
        }
    }
}

data class Command(val action: String, val ax: Int, val ay: Int, val bx: Int, val by: Int) {
    companion object {
        fun parse(line: String): Command {
            val l = line.removePrefix("turn ")

            val (action, coords1, _, coords2) = l.split(" ", limit = 4)
            val (ax, ay) = coords1.split(",").map { it.toInt() }
            val (bx, by) = coords2.split(",").map { it.toInt() }
            return Command(action, ax, ay, bx, by)
        }
    }
}

class D6P1 : Puzzle {
    override fun solve(input: String): Any {
        val grid = Grid()

        input.lineSequence()
            .map { Command.parse(it) }
            .forEach { grid.apply1(it) }

        return grid.sum
    }
}

class D6P2 : Puzzle {
    override fun solve(input: String): Any {
        val grid = Grid()

        input.lineSequence()
            .map { Command.parse(it) }
            .forEach { grid.apply2(it) }

        return grid.sum
    }
}