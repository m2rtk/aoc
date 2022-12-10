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

private fun downloadInputFile(year: Int, day: Int): Path {
    val yy = year.toString().takeLast(2)
    val dd = day.toString()
    val filePath = Path("src/main/resources/aoc/y${yy}/d${dd}/input.txt")

    if (Files.exists(filePath)) {
        return filePath
    }

    Files.createDirectories(filePath.parent)

    val cookie = Files.readString(Path.of("session.cookie"))

    val url = URL("https://adventofcode.com/$year/day/$day/input")

    val connection = url.openConnection() as HttpURLConnection
    connection.requestMethod = "GET"
    connection.setRequestProperty("Cookie", "session=$cookie")
    connection.inputStream.use {
        val s = it.readAllBytes().decodeToString().removeSuffix("\n")
        Files.writeString(filePath, s)
    }

    println("Downloaded input from $url to $filePath")
    return filePath
}

private fun Puzzle.input(namePart: String): List<Path> {
    return Files.walk(filesDir())
        .filter { it.fileName.toString().contains(namePart) }
        .toList()
}

private fun createFiles(year: Int, day: Int) {
    val yy = year.toString().takeLast(2)
    val dd = day.toString()

    val file = Path.of("src/main/kotlin/aoc/y${yy}/d${dd}/D${dd}.kt")
    if (Files.notExists(file)) {
        val puzzleFileContents = Files.readString(Path.of("src/main/resources/file.template"))
            .replace("{YEAR}", yy)
            .replace("{DAY}", dd)
        Files.createDirectories(file.parent)
        Files.writeString(file, puzzleFileContents)
        println("Created new file $file")
    }

    val testFile = Path.of("src/test/kotlin/aoc/y${yy}/d${dd}/D${dd}Test.kt")
    if (Files.notExists(testFile)) {
        val testFileContents = Files.readString(Path.of("src/main/resources/test.template"))
            .replace("{YEAR}", yy)
            .replace("{DAY}", dd)
        Files.createDirectories(testFile.parent)
        Files.writeString(testFile, testFileContents)
        println("Created new file $testFile")
    }
}

internal fun findPuzzle(year: Int, day: Int, part: Int): Puzzle? {
    val className = "aoc.y${year.toString().takeLast(2)}.d${day}.D${day}P${part}"
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

    createFiles(year, day)
    downloadInputFile(year, day)

    return puzzles
}

fun Puzzle.run(inputNamePart: String = "input"): Any? {
    val files = input(inputNamePart)

    if (files.size == 1) {
        return run(files[0])
    }

    for (file in files) {
        run(file)
    }

    return null
}

fun Puzzle.runSamples() = run("sample")

fun Puzzle.runRaw(input: String): Any? {
    return try {
        val result = solve(input)
        println("${javaClass.simpleName}(${input}) = $result")
        result
    } catch (e: Exception) {
        println("${javaClass.simpleName}(${input}) = ERROR ${e.message}")
        e.printStackTrace(System.out)
        null
    }
}

fun Puzzle.run(file: Path): Any? {
    return try {
        val result = solve(Files.readString(file))
        println("${javaClass.simpleName}(${file}) = $result")
        result
    } catch (e: Exception) {
        println("${javaClass.simpleName}(${file}) = ERROR ${e.message}")
        e.printStackTrace(System.out)
        null
    }
}
