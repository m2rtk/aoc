package aoc


fun main() {
    val puzzles = findPuzzles(2022, 12)
    puzzles.forEach { it.runSamples() }
    puzzles.forEach { it.run() }
}
