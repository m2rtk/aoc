package aoc.y22

data class Point(val x: Int, val y: Int) {

    companion object {
        fun parse(line: String): Point {
            val (x, y) = line.replace(Regex("\\s+"), "")
                .split(",")
                .map { it.toInt() }
            return Point(x,  y)
        }
    }

    fun translate(xd: Int = 0, yd: Int = 0) = Point(x + xd, y + yd)
    fun coerceBounds(xMin: Int, xMax: Int, yMin: Int, yMax: Int) = Point(x.coerceIn(xMin, xMax), y.coerceIn(yMin, yMax))

    override fun toString(): String {
        return "($x, $y)"
    }
}
