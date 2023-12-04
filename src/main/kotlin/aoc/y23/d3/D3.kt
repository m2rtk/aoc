@file:Suppress("unused")

package aoc.y23.d3

import aoc.Puzzle
import aoc.y22.Point

private data class EngineSchematic(
    val lines: List<String>
) {
    val width = lines[0].length
    val height = lines.size

    fun get(col: Int, row: Int): EnginePart {
        val char = lines[col][row]

        if (char.isDigit()) {
            return Number(char.digitToInt())
        } else if (char == '.') {
            return Nothing
        } else {
            return Symbol(char)
        }
    }

    fun hasSurroundingSymbol(col: Int, row: Int): Boolean {
        val point = Point(col, row)

        for (surroundingPoint in point.surroundingPointsInBounds(0, width, 0, height)) {
            if (get(surroundingPoint.x, surroundingPoint.y) is Symbol) {
                return true
            }
        }

        return false
    }

    fun surroundingGears(col: Int, row: Int): List<Point> {
        val point = Point(col, row)
        val gears = mutableListOf<Point>()

        for (surroundingPoint in point.surroundingPointsInBounds(0, width, 0, height)) {
            val b = get(surroundingPoint.x, surroundingPoint.y)
            if (b is Symbol && b.symbol == '*') {
                gears.add(surroundingPoint)
            }
        }

        return gears
    }
}

private sealed interface EnginePart

private data class Number(val int: Int) : EnginePart
private object Nothing : EnginePart
private data class Symbol(val symbol: Char) : EnginePart

class D3P1 : Puzzle {
    override fun solve(input: String): Any {
        val engine = EngineSchematic(input.lines())

        val partNumbers = mutableListOf<Int>()

        var numberString: String? = null
        var includeNumber = false

        for (col in 0 until   engine.width) {
            for (row in 0 until engine.height) {
                val part = engine.get(col, row)

                if (part is Number) {
                    if (numberString == null) {
                        numberString = part.int.toString()
                    } else {
                        numberString += part.int.toString()
                    }

                    includeNumber = includeNumber || engine.hasSurroundingSymbol(col, row)
                } else {
                    if (numberString != null && includeNumber) {
                        partNumbers.add(numberString.toInt())
                    }

                    numberString = null
                    includeNumber = false
                }
            }
        }

        return partNumbers.sum()
    }
}

class D3P2 : Puzzle {
    override fun solve(input: String): Any {
        val engine = EngineSchematic(input.lines())

        val gears = mutableMapOf<Point, MutableList<Int>>()

        var numberString: String? = null
        val gearsForNumber: MutableSet<Point> = mutableSetOf()

        for (col in 0 until   engine.width) {
            for (row in 0 until engine.height) {
                val part = engine.get(col, row)

                if (part is Number) {
                    if (numberString == null) {
                        numberString = part.int.toString()
                    } else {
                        numberString += part.int.toString()
                    }

                    gearsForNumber.addAll(engine.surroundingGears(col, row))
                } else {
                    if (numberString != null) {
                        val number = numberString.toInt()
                        for (gear in gearsForNumber) {
                            if (gear in gears) {
                                gears[gear]!!.add(number)
                            } else {
                                gears[gear] = mutableListOf(number)
                            }
                        }
                    }

                    numberString = null
                    gearsForNumber.clear()
                }
            }
        }

        return gears
            .filter { it.value.size == 2 }
            .map { it.value[0] * it.value[1] }
            .sum()
    }
}