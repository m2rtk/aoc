package aoc

import java.lang.Exception
import java.net.HttpURLConnection
import java.net.URL
import java.nio.file.Files
import java.nio.file.Path
import kotlin.io.path.Path

interface Puzzle {
    fun solve(input: String): Any
}


private fun Puzzle.filesDir(): Path = Path("src/main/resources/" + javaClass.packageName.replace('.', '/') + '/')

private fun Puzzle.loadInput(): Path {
    val day = javaClass.simpleName.split("P")[0].removePrefix("D").toInt()
    val year = "20" + javaClass.name.toString().split(".", limit = 3)[1].removePrefix("y")
    val filePath = filesDir().resolve("D${day}_input.txt")
    val cookie = Files.readString(Path.of("session.cookie"))

    val url = URL("https://adventofcode.com/$year/day/$day/input")

    val connection = url.openConnection() as HttpURLConnection
    connection.requestMethod = "GET"
    connection.setRequestProperty("Cookie", "session=$cookie")
    connection.inputStream.use {
        it.transferTo(Files.newOutputStream(filePath))
    }

    println("Downloaded input from $url to $filePath")
    return filePath
}

private fun Puzzle.input(namePart: String): List<Path> {
    val dayName = javaClass.simpleName.split("P", limit = 2)[0]
    val dir = filesDir()

    val dayFiles = Files.walk(dir)
        .filter { it.fileName.toString().startsWith(dayName) }
        .toList()

    val files = dayFiles.filter { it.fileName.toString().contains(namePart) }

    return files.ifEmpty {
        if (namePart == "input") {
            listOf(loadInput())
        } else {
            println("Could not find any matching files for $namePart in $dir")
            listOf()
        }
    }
}

private fun createPuzzleFile(year: Int, day: Int) {
    val yy = year.toString().takeLast(2)
    val contents = Files.readString(Path.of("src/main/resources/Template.ktt"))
        .replace("{YEAR}", yy)
        .replace("{DAY}", day.toString())

    val file = Path.of("src/main/kotlin/aoc/y${yy}/D${day}.kt")
    Files.writeString(file, contents)
    println("Created new file $file")
}

internal fun findPuzzle(year: Int, day: Int, part: Int): Puzzle? {
    val className = "aoc.y${year.toString().takeLast(2)}.D${day}P${part}"
    return try {
        val clazz = Class.forName(className)
        if (Puzzle::class.java.isAssignableFrom(clazz)) {
            clazz.getConstructor().newInstance() as Puzzle
        } else {
            println("$className is not a Puzzle")
            null
        }
    } catch (e: ClassNotFoundException) {
        println("No solution for year $year day $day part $part found from $className")
        null
    }
}

internal fun findPuzzles(year: Int, day: Int): List<Puzzle> {
    val puzzles = mutableListOf<Puzzle>()
    findPuzzle(year, day, 1)?.let { puzzles.add(it) }
    findPuzzle(year, day, 2)?.let { puzzles.add(it) }

    if (puzzles.isEmpty()) {
        createPuzzleFile(year, day)
    }
    return puzzles
}

fun Puzzle.run(inputNamePart: String = "input") {
    val files = input(inputNamePart)
    for (file in files) {
        run(file)
    }
}

fun Puzzle.runSamples() = run("sample")

fun Puzzle.run(file: Path) {
    try {
        println("${javaClass.simpleName}(${file}) = ${solve(Files.readString(file))}")
    } catch (e: Exception) {
        println("${javaClass.simpleName}(${file}) = ERROR")
        e.printStackTrace()
    }
}
