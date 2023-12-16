@file:Suppress("unused")

package aoc.y23.d12

import aoc.Puzzle

internal data class RowOfSprings(
    val springs: String,
    val goodSpringGroups: List<Int>
) {

    fun possibleArrangementsSize() = possibleArrangements().size

    fun possibleArrangements(): Set<String> {
        return setOf()
    }
}

internal fun go(row: String, groups: List<Int>): Set<String> {
    val result = mutableSetOf<String>()

    fun asd() {
        val startPosition = groups.map { "#".repeat(it) }.joinToString(".")
    }

    return result
}

internal fun test(): Set<String> {
    val numbers = listOf(3,2,1)

    val rw = "?????????"
    val n1 = "###.##.#."
    val n2 = "###.##..#"
    return setOf()
}

private fun RowOfSprings.line() = Line(springs, goodSpringGroups)
private data class Line(
    private val row: String,
    private val groups: List<Int>,
) {

    private val spacesSize = row.length - groups.sum()
    private val spaces: MutableList<Int>

    init {
        spaces = mutableListOf()
        spaces.add(0)

        val spacesInBetween = groups.size - 1
        for (i in 0 until spacesInBetween) {
            spaces.add(1)
        }

        spaces.add(spacesSize - spacesInBetween)
    }

//    fun iterate(): Boolean {
//
//    }

    fun find(): Set<String> {

        fun round(spaces: List<Int>) {

        }


        return setOf()
    }

    fun render(): String {
        val result = StringBuilder()
        var useSpaces = true
        val spacesIterator = spaces.iterator()
        val groupsIterator = groups.iterator()

        while (spacesIterator.hasNext() || groupsIterator.hasNext()) {
            if (useSpaces) {
                result.append(".".repeat(spacesIterator.next()))
            } else {
                result.append("#".repeat(groupsIterator.next()))
            }
            useSpaces = !useSpaces
        }

        return result.toString()
    }
}

internal fun matches(row: String, solution: String): Boolean {
    assert(row.length == solution.length)
    for (i in row.indices) {
        val r = row[i]
        if (r == '?') {
            continue
        }

        if (r != solution[i]) {
            return false
        }
    }
    return true
}

internal fun isOk(row: String, groups: List<Int>): Boolean {
    assert(row.indexOf('?') == -1)

    var i = 0
    val groupIterator = groups.iterator()

    while (i < row.length && groupIterator.hasNext()) {
        val groupLength = groupIterator.next()
        val index = row.substring(i).indexOf("#".repeat(groupLength))

        if (index == -1) {
            return false
        }

        val groupStartIndex = i + index

        val beforeIndex = groupStartIndex-1
        val afterIndex = groupStartIndex + groupLength

        val before = if (beforeIndex == -1) {
            '.'
        } else {
            row[beforeIndex]
        }

        val after = if (afterIndex == row.length) {
            '.'
        } else {
            row[afterIndex]
        }

//        println("i=$i gs=$groupStartIndex gl=$groupLength bi=$beforeIndex$before ai=$afterIndex$after")

        if (before != '.' || after != '.') {
            return false
        }

        i = groupStartIndex + groupLength
    }

    return !groupIterator.hasNext()
}

internal fun String.parseLine(): RowOfSprings {
    val (springs, groups) = split(Regex("\\s+"), limit = 2)
    val g = groups.split(",").map { it.toInt() }
    return RowOfSprings(springs, g)
}

class D12P1 : Puzzle {
    override fun solve(input: String): Any {
        return input.lines()
            .map { it.parseLine() }
            .onEach { println(it.line().render()) }
            .map { it.possibleArrangementsSize() }
            .sum()
    }
}

class D12P2 : Puzzle {
    override fun solve(input: String): Any {
        return -1
    }
}