package aoc


fun main(args: Array<String>) {
    val input = if (args.size > 1 && args[0] == "run") {
        args[1]
    } else {
        print("year-day-part?>")
        readln()
    }

    val target = input.split("-").map { it.toInt() }
    val year = target[0]
    val day = target[1]
    val part = if (target.size == 3) target[2] else null

    println("https://adventofcode.com/$year/day/$day")

    val puzzles: List<Puzzle> = if (part != null) {
        listOf(findPuzzle(year, day, part) ?: throw IllegalArgumentException(input))
    } else {
        findPuzzles(year, day)
    }

    puzzles.forEach { it.run() }
}


