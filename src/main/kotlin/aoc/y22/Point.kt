package aoc.y22

data class Point(val x: Int, val y: Int) {

    companion object {
        fun parse(line: String): Point {
            val (x, y) = line.replace(Regex("\\s+"), "")
                .split(",")
                .map { it.toInt() }
            return Point(x,  y)
        }

        val surroundingDeltas = listOf(
            Point(1, 1),
            Point(1, 0),
            Point(1, -1),
            Point(0, 1),
            Point(0, 0),
            Point(0, -1),
            Point(-1, 1),
            Point(-1, 0),
            Point(-1, -1),
        )
    }

    fun translate(xd: Int = 0, yd: Int = 0) = Point(x + xd, y + yd)
    fun translate(other: Point) = translate(xd = other.x, yd = other.y)
    fun coerceBounds(xMin: Int, xMax: Int, yMin: Int, yMax: Int) = Point(x.coerceIn(xMin, xMax), y.coerceIn(yMin, yMax))

    fun surroundingPoints() = surroundingDeltas.map { this.translate(it) }
    fun surroundingPointsInBounds(xMin: Int, xMax: Int, yMin: Int, yMax: Int) = surroundingPoints()
        .filter { it.x in xMin until xMax && it.y in yMin until yMax }

    override fun toString(): String {
        return "($x, $y)"
    }
}
