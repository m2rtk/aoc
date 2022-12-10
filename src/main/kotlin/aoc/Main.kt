package aoc


fun main() {
    val puzzles = findPuzzles(2022, 10)
    puzzles.forEach { it.runSamples() }
    puzzles.forEach { it.run() }
//    findPuzzle(2022, 9, 2)?.run("sample2")

//    for (i in 1..7) {
//        val puzzles = findPuzzles(2022, i)
//        puzzles.forEach { it.run() }
//    }
}
