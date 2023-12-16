package aoc

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
    fun translate(direction: Direction) = when (direction) {
        Direction.N -> translate(yd = -1)
        Direction.E -> translate(xd = 1)
        Direction.S -> translate(yd = 1)
        Direction.W -> translate(xd = -1)
    }

    fun coerceBounds(xMin: Int, xMax: Int, yMin: Int, yMax: Int) = Point(x.coerceIn(xMin, xMax), y.coerceIn(yMin, yMax))

    fun inBounds(bounds: Bounds): Point? {
        if (this in bounds) {
            return this
        }

        return null
    }

    operator fun minus(other: Point): Point = Point(x - other.x, y - other.y)

    fun surroundingPoints() = surroundingDeltas.map { this.translate(it) }
    fun surroundingPointsInBounds(xMin: Int, xMax: Int, yMin: Int, yMax: Int) = surroundingPoints()
        .filter { it.x in xMin until xMax && it.y in yMin until yMax }

    override fun toString(): String {
        return "($x, $y)"
    }
}

data class Bounds(val xRange: IntRange, val yRange: IntRange) {
    constructor(iterable: List<List<*>>) : this (iterable[0].indices, iterable.indices)
    constructor(height: Int, width: Int) : this (0 until width, 0 until height)

    operator fun contains(point: Point): Boolean {
        return point.x in xRange && point.y in yRange
    }
}

