@file:Suppress("unused")

package aoc.y23.d15

import aoc.Puzzle

private fun calculateHash(input: String): Int {
    var value = 0

    for (char in input) {
        value += char.code
        value *= 17
        value %= 256
    }

    return value
}

class D15P1 : Puzzle {
    override fun solve(input: String): Any {
        return input.split(",").sumOf { calculateHash(it) }
    }
}

class D15P2 : Puzzle {

    private data class Lens(val label: String, val length: Int)

    override fun solve(input: String): Any {
        val hashmap = mutableMapOf<Int, MutableList<Lens>>()

        for (thing in input.split(",")) {
            if (thing.endsWith("-")) {
                val label = thing.removeSuffix("-")
                val hash = calculateHash(label)
                val list = hashmap[hash] ?: continue

                val existing = list.indexOfFirst { it.label == label }
                if (existing != -1) {
                    list.removeAt(existing)
                }
            } else {
                val (label, value) = thing.split("=", limit = 2)
                val hash = calculateHash(label)
                val lens = Lens(label, value.toInt())

                val list = hashmap[hash]

                if (list == null) {
                    hashmap[hash] = mutableListOf(lens)
                    continue
                }

                val existing = list.indexOfFirst { it.label == label }
                if (existing == -1) {
                    list.add(lens)
                } else {
                    list[existing] = lens
                }
            }
        }

        return hashmap.entries.fold(0) { acc, (key, ints) ->
            acc + ints.foldIndexed(0) { index, a, i ->
                (key + 1) * (index + 1) * i.length + a
            }
        }
    }
}