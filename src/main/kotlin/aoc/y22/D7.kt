package aoc.y22

import aoc.lines

private class D7  {

    sealed interface Item {
        fun print(indent: Int = 0)
        fun size(): Int
    }

    data class File(val name: String, val size: Int) : Item {

        override fun size(): Int = size

        override fun print(indent: Int) {
            println(" ".repeat(indent) + "- $name (file, size=$size)")
        }
    }
    data class Dir(val name: String, val parent: Dir? = null, val items: MutableList<Item> = mutableListOf()) : Item {

        override fun size() = items.sumOf { it.size() }

        fun allDirs(): List<Dir> {
            val dirs = mutableListOf(this)

            items.filterIsInstance<Dir>().forEach { dirs.addAll(it.allDirs()) }

            return dirs
        }

        override fun print(indent: Int) {
            println(" ".repeat(indent) + "- $name (dir)")
            for (item in items) {
                item.print(indent + 2)
            }
        }
    }

    fun build(sequence: Sequence<String>): Dir {
        val lines = sequence.toList()
        var pointer = 1

        assert(lines[0] == "$ cd /")

        val root = Dir("/")
        var currentDir = root

        while (pointer < lines.size) {
            val line = lines[pointer++]

            if (line == "$ ls") {
                while (true) {
                    if (pointer == lines.size) {
                        break
                    }
                    if (lines[pointer].startsWith("$")) {
                        break
                    } else {
                        val (type, name) = lines[pointer].split(" ", limit = 2)
                        if (type == "dir") {
                            currentDir.items.add(Dir(name, parent = currentDir))
                        } else {
                            currentDir.items.add(File(name, size = type.toInt()))
                        }
                        pointer++
                    }
                }
            } else if (line.startsWith("$ cd")) {
                val target = line.split(" ", limit = 3)[2]

                currentDir = if (target == ".." && currentDir.parent != null) {
                    currentDir.parent!!
                } else if (target == "/") {
                    root
                } else {
                    currentDir.items.filterIsInstance<Dir>().find { it.name == target } ?: throw RuntimeException("No such dir $target in ${currentDir.name}")
                }
            }
        }

        return root
    }

    fun t1(sequence: Sequence<String>) {
        val root = build(sequence)

        root.allDirs()
            .filter { it.size() <= 100000 }
            .sumOf { it.size() }
            .also { println(it) }
    }

    fun t2(sequence: Sequence<String>) {
        val root = build(sequence)

        val totalSpace = 70_000_000
        val usedSpace = root.size()
        val unusedSpace = totalSpace - usedSpace
        val targetUnusedSpace = 30_000_000
        val missingSpace = targetUnusedSpace - unusedSpace

        root.allDirs()
            .filter { it.size() >= missingSpace }
            .minBy { it.size() }
            .also { println(it.size()) }
    }
}

fun main() {
    fun sample() = lines("/aoc/y22/D7_sample.txt")
    fun input() = lines("/aoc/y22/D7_input.txt")

    val d = D7()

    d.t1(sample())
    d.t1(input())
    d.t2(sample())
    d.t2(input())
}