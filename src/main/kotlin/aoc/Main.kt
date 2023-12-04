package aoc


fun main(args: Array<String>) {
    if (args.size > 1 && args[0] == "run") {
        val target = args[1].split("-").map { it.toInt() }

        val year = target[0]
        val day = target[1]

        println("https://adventofcode.com/$year/day/$day")

        val puzzles: List<Puzzle> = if (target.size == 3) {
            listOf(findPuzzle(year, day, target[2]) ?: throw IllegalArgumentException(args[1]))
        } else {
            findPuzzles(year, day)
        }

        if (args.size > 2) {
            puzzles.forEach { it.run(args[2]) }
        } else {
            puzzles.forEach { it.run() }
        }
    }
}


