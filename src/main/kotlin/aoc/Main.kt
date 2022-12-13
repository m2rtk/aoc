package aoc


fun main() {
    val puzzles = findPuzzles(2022, 13)
    puzzles.forEach { it.runSamples() }
    puzzles.forEach { it.run() }
}
