package aoc


fun main() {
    val puzzles = findPuzzles(2022, 8)
    puzzles.forEach { it.runSamples() }
    puzzles.forEach { it.run() }

//    for (i in 1..7) {
//        val puzzles = findPuzzles(2022, i)
//        puzzles.forEach { it.run() }
//    }
}
