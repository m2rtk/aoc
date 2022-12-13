@file:Suppress("unused")

package aoc.y22.d13

import aoc.run
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class D13Test {

    private val sample = """
        [1,1,3,1,1]
        [1,1,5,1,1]

        [[1],[2,3,4]]
        [[1],4]

        [9]
        [[8,7,6]]

        [[4,4],4,4]
        [[4,4],4,4,4]

        [7,7,7,7]
        [7,7,7]

        []
        [3]

        [[[]]]
        [[]]

        [1,[2,[3,[4,[5,6,7]]]],8,9]
        [1,[2,[3,[4,[5,6,0]]]],8,9]
    """.trimIndent()

    private val p1Solution: Any = 6072
    private val p2Solution: Any = 22184

    private fun p1() = D13P1()
    private fun p2() = D13P2()

    @Test
    fun input1() {
        assertThat(p1().run("input")).isEqualTo(p1Solution)
    }

    @Test
    fun input2() {
        assertThat(p2().run("input")).isEqualTo(p2Solution)
    }

    @Test
    fun parse1() {
        assertThat(Packets.parseLine("[1,1,3,1,1]")).isEqualTo(
            ListWrapper(
                IntWrapper(1),
                IntWrapper(1),
                IntWrapper(3),
                IntWrapper(1),
                IntWrapper(1),
            )
        )
    }

    @Test
    fun parse2() {
        assertThat(Packets.parseLine("[[4,4],4,4]")).isEqualTo(
            ListWrapper(
                ListWrapper(
                    IntWrapper(4),
                    IntWrapper(4),
                ),
                IntWrapper(4),
                IntWrapper(4),
            )
        )
    }

    @Test
    fun sample1() {
        assertThat(p1().solve(sample)).isEqualTo(13)
    }
    @Test
    fun sample2() {
        assertThat(p2().solve(sample)).isEqualTo(140)
    }
}