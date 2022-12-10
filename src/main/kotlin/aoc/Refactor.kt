package aoc

import java.nio.file.Files
import java.nio.file.Path


fun main() {
    val root = Path.of("src/main/resources/aoc")

    val files = Files.walk(root)
        .filter { it.fileName.toString().startsWith("D") }
        .toList()

    for (file in files) {
        val name = file.fileName.toString()
        val newName = name.split("_")[1]
        val day = name.removePrefix("D").split("_")[0]
        val newDir = file.resolveSibling("d$day")
        Files.createDirectories(newDir)
        val newFile = newDir.resolve(newName)

        Files.writeString(
            newFile,
            Files.readString(file)
        )
    }
}