package aoc


fun main() {
    val puzzles = findPuzzles(2022, 14)
//    puzzles.forEach { it.runSamples() }
//    puzzles.forEach { it.run() }

    puzzles[0].solve("""
        498,4 -> 498,6 -> 496,6
        503,4 -> 502,4 -> 502,9 -> 494,9
    """.trimIndent())
}
