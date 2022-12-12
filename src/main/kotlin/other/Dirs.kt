package other

import kotlin.math.abs
import kotlin.math.min

data class Dir(val count: Int, val target: Int) {
    companion object {
        fun parse(line: String): Dir {
            val (count, target) = line.split(" ").map { it.toInt() }
            return Dir(count, target)
        }
    }
}

fun calc(dirs: List<Dir>): Int {
    var pos = 1
    var time = 0

    for (i in dirs.indices) {
        val dir = dirs[i]

        val normalTarget = dir.target
        val reversedTarget = 1 + dir.count - dir.target
        val normalTime = abs(dir.target - pos)
        val reversedTime = pos + reversedTarget

    }

    return time
}


fun main() {
    val input1 = """
        2
        5 3
        2 1
    """.trimIndent()
     val input2 = """
        2
        5 4
        2 1
    """.trimIndent()

    fun parse(i: String) = i.lines().drop(1).map { Dir.parse(it) }
    fun solve(i: String) = parse(i).let { calc(it) }.also { println(it) }

    solve(input1)
    solve(input2)
}