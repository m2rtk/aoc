@file:Suppress("unused")

package aoc.y15

import aoc.Puzzle

private data class Box (val string: String) {
    val sides = string.split("x").map { num -> num.toInt() }.sorted()
    val l = sides[0]
    val w = sides[1]
    val h = sides[2]

    fun surface(): Int = 2*l*w + 2*w*h + 2*h*l
    fun required(): Int = surface() + sequenceOf(l*w, w*h, h*l).min()
    fun ribbonWrap(): Int = 2*l + 2*w
    fun ribbonBow(): Int = l*w*h
}

class D2P1 : Puzzle {
    override fun solve(input: String): String {
        return input
            .lines()
            .map { Box(it) }
            .sumOf { it.required() }
            .toString()
    }
}

class D2P2 : Puzzle {
    override fun solve(input: String): String {
        return input
            .lines()
            .map { Box(it) }
            .sumOf { it.ribbonWrap() + it.ribbonBow() }
            .toString()
    }
}