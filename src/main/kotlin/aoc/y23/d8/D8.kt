@file:Suppress("unused")

package aoc.y23.d8

import aoc.Puzzle
import java.math.BigInteger

private enum class Instruction {
    LEFT, RIGHT
}

private data class MapDocument(
    val instructions: List<Instruction>,
    val nodes: Map<String, Pair<String, String>>,
) {
    fun getInstruction(step: Long): Instruction {
        val index = (step % instructions.size).toInt()
        return instructions[index]
    }
}

private fun lcm(a: BigInteger, b: BigInteger): BigInteger {
    val larger = if (a > b) a else b
    val maxLcm = a * b
    var lcm = larger
    while (lcm <= maxLcm) {
        if (lcm.mod(a) == BigInteger.ZERO && lcm.mod(b) == BigInteger.ZERO) {
            return lcm
        }
        lcm += larger
    }
    return maxLcm
}

// https://www.baeldung.com/kotlin/lcm
private fun lcm(nums: List<Number>): BigInteger {
    return nums
        .map { BigInteger(it.toString()) }
        .reduce { a, b -> lcm(a, b) }
}

private fun String.parse(): MapDocument {
    val lines = lines()
    val instructions = lines.first().trim().toCharArray().map {
        if (it == 'L') Instruction.LEFT
        else Instruction.RIGHT
    }
    val nodes = lines.subList(2, lines.size)
        .map { it.split(" = ", limit = 2) }
        .map { it[0] to it[1].removePrefix("(").removeSuffix(")").split(", ") }
        .associate { it.first to (it.second[0] to it.second[1]) }

    return MapDocument(instructions, nodes)
}

class D8P1 : Puzzle {
    override fun solve(input: String): Any {
        val map = input.parse()

        var location = "AAA"
        var stepsTaken: Long = 0

        while (location != "ZZZ") {
            val instruction = map.getInstruction(stepsTaken)
            val options = map.nodes[location]!!

            location = when (instruction) {
                Instruction.LEFT -> options.first
                Instruction.RIGHT -> options.second
            }

            stepsTaken++
        }

        return stepsTaken
    }
}

class D8P2 : Puzzle {
    override fun solve(input: String): Any {
        val map = input.parse()

        val locations: MutableList<String> = map.nodes.keys.filter { it.endsWith("A") }.toMutableList()
        val repeating = locations.map { location ->
            var steps = 0L
            var current: String = location

            while (!current.endsWith("Z")) {
                val instruction = map.getInstruction(steps)
                val options = map.nodes[current]!!

                current = when(instruction) {
                    Instruction.LEFT -> options.first
                    Instruction.RIGHT -> options.second
                }
                steps++
            }

            steps
        }

        return lcm(repeating)

        // leaving this part here because it is how I stumbled into a solution

//        val lastPrints: MutableList<Int> = locations.map { 0 }.toMutableList()
//        var stepsTaken: Long = 0
//        var lastPrint: Long = 0
//
//        while (!locations.all { it.endsWith("Z") }) {
//            val (i, instruction) = map.getInstruction(stepsTaken)
//
//            for (index in locations.indices) {
//                val location = locations[index]
//                val options = map.nodes[location]!!
//
//                locations[index] = when (instruction) {
//                    Instruction.LEFT -> options.first
//                    Instruction.RIGHT -> options.second
//                }
//            }
//
//            stepsTaken ++
//
//            val states = locations.mapIndexed { index, loc ->
//                if (loc.endsWith("Z")) {
//                    val diff = stepsTaken - lastPrints[index]
//                    lastPrints[index] = stepsTaken.toInt()
//                    "$loc-${diff.toString().padEnd(5, '-')}"
//                } else {
//                    "---------"
//                }
//            }
//                .joinToString(" ")
//
//            if (states.contains("Z")) {
//                println("${stepsTaken.toString().padStart(10)} ${i.toString().padStart(3)} $states")
//            }
//
//            if (stepsTaken > 100000) {
//                break
//            }
//        }
//
//        return stepsTaken
    }
}
