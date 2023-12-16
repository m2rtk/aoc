package aoc


enum class Direction {
    N, E, S, W;

    fun opposite(): Direction = when (this) {
        N -> S
        E -> W
        S -> N
        W -> E
    }
}

