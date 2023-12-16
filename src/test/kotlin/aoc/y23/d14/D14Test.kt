@file:Suppress("unused")

package aoc.y23.d14

import aoc.Direction
import aoc.run
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class D14Test {

    private fun p1() = D14P1()
    private fun p2() = D14P2()

    @Test
    fun input1() {
        assertThat(p1().run("input")).isEqualTo(105623)
    }

    @Test
    fun input2() {
        assertThat(p2().run("input")).isEqualTo(98029)
    }

    private val sample = """
        O....#....
        O.OO#....#
        .....##...
        OO.#O....O
        .O.....O#.
        O.#..O.#.#
        ..O..#O..O
        .......O..
        #....###..
        #OO..#....
    """.trimIndent()

    @Test
    fun sample1() {
        assertThat(p1().solve(sample)).isEqualTo(136)
    }

    @Test
    fun sample2() {
        assertThat(p2().solve(sample)).isEqualTo(64)
    }

    @Test
    fun tiltNorth() {
        val tilted = p1().tilt(sample, Direction.N)
        assertThat(tilted).isEqualTo("""
            OOOO.#.O..
            OO..#....#
            OO..O##..O
            O..#.OO...
            ........#.
            ..#....#.#
            ..O..#.O.O
            ..O.......
            #....###..
            #....#....
        """.trimIndent())
    }
    @Test
    fun tiltSouth() {
        val tilted = p1().tilt(sample, Direction.S)
        assertThat(tilted).isEqualTo("""
            .....#....
            ....#....#
            ...O.##...
            ...#......
            O.O....O#O
            O.#..O.#.#
            O....#....
            OO....OO..
            #OO..###..
            #OO.O#...O
        """.trimIndent())
    }
    @Test
    fun tiltEast() {
        val tilted = p1().tilt(sample, Direction.E)
        assertThat(tilted).isEqualTo("""
            ....O#....
            .OOO#....#
            .....##...
            .OO#....OO
            ......OO#.
            .O#...O#.#
            ....O#..OO
            .........O
            #....###..
            #..OO#....
        """.trimIndent())
    }
    @Test
    fun tiltWest() {
        val tilted = p1().tilt(sample, Direction.W)
        assertThat(tilted).isEqualTo("""
            O....#....
            OOO.#....#
            .....##...
            OO.#OO....
            OO......#.
            O.#O...#.#
            O....#OO..
            O.........
            #....###..
            #OO..#....
        """.trimIndent())
    }

    @Test
    fun cycle() {
        val p2 = p2()

        val after1 = p2.cycle(sample)

        assertThat(after1).isEqualTo("""
            .....#....
            ....#...O#
            ...OO##...
            .OO#......
            .....OOO#.
            .O#...O#.#
            ....O#....
            ......OOOO
            #...O###..
            #..OO#....
        """.trimIndent())

        val after2 = p2.cycle(after1)

        assertThat(after2).isEqualTo("""
            .....#....
            ....#...O#
            .....##...
            ..O#......
            .....OOO#.
            .O#...O#.#
            ....O#...O
            .......OOO
            #..OO###..
            #.OOO#...O
        """.trimIndent())

        val after3 = p2.cycle(after2)

        assertThat(after3).isEqualTo("""
            .....#....
            ....#...O#
            .....##...
            ..O#......
            .....OOO#.
            .O#...O#.#
            ....O#...O
            .......OOO
            #...O###.O
            #.OOO#...O
        """.trimIndent())


    }
}