@file:Suppress("unused")

package aoc.y22.d15

import aoc.run
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.Arguments.arguments
import org.junit.jupiter.params.provider.MethodSource
import org.junit.jupiter.params.provider.ValueSource

class D15Test {

    private val p1Solution: Any = 5511201
    private val p2Solution: Any = 11318723411840L

    private fun p1() = D15P1()
    private fun p2() = D15P2()

    @Test
    fun input1() {
        assertThat(p1().run("input")).isEqualTo(p1Solution)
    }

    @Test
    fun input2() {
        assertThat(p2().run("input")).isEqualTo(p2Solution)
    }


    companion object {
        @JvmStatic
        fun coverCases() = listOf<Arguments>(
            arguments(
                listOf(IntRange(0, 10)),
                false,
            ),
            arguments(
                listOf(-1..5, 8..16, 12..16, 6..10, 0..0, 12..28, 17..17),
                true,
            ),
            arguments(
                listOf(IntRange(0, 0)),
                false,
            ),
            arguments(
                listOf(IntRange(0, 1), IntRange(0, 10)),
                false,
            ),
            arguments(
                listOf(IntRange(0, 10), IntRange(12, 20)),
                false,
            ),
            arguments(
                listOf(IntRange(0, 20)),
                true,
            ),
            arguments(
                listOf(IntRange(-100, 20)),
                true,
            ),
            arguments(
                listOf(IntRange(0, 100)),
                true,
            ),
            arguments(
                listOf(IntRange(0, 10), IntRange(10, 20)),
                true,
            ),
            arguments(
                listOf(IntRange(-100, 100), IntRange(10, 20)),
                true,
            ),
            arguments(
                listOf(IntRange(-100, 8), IntRange(10, 20)),
                false,
            ),
        )
    }

    @ParameterizedTest
    @ValueSource(
        strings = [
            "6..20 - 0..0 = 6..20",
            "1..5 - 8..16 = 1..5",
            "5..6 - 6..6 = 5..5",
            "5..6 - 5..6 = empty",
            "5..6 - 5..5 = 6..6",
            "5..6 - 6..8 = 5..5",
            "5..5 - 6..6 = 5..5",
            "5..5 - 5..6 = empty",
            "6..6 - 5..6 = empty",
            "1..1 - 1..1 = empty",
            "1..1 - 2..2 = 1..1",
            "1..10 - 2..2 = 1..1,3..10",
            "1..20 - 1..10 = 11..20",
            "1..20 - -10..10 = 11..20",
            "0..40 - 0..20 = 21..40",
            "0..40 - 21..40 = 0..20",
            "0..40 - 0..40 = empty",
            "5..20 - 0..40 = empty",
            "0..100 - 0..50 = 51..100",
            "51..100 - 0..50 = 51..100",
            "51..100 - 51..51 = 52..100",
            "52..100 - 51..60 = 61..100",
            "61..100 - 80..100 = 61..79",
            "61..79 - 70..70 = 61..69,71..79",
            "5..7 - 6..6 = 5..5,7..7",
        ]
    )
    fun minusTest(line: String) {
        val (a, b, r) = line.split(Regex(" [=-] "))
        assertThat(a.toIntRange() - b.toIntRange()).isEqualTo(r.toIntRangeList())
    }


    @ParameterizedTest
    @MethodSource("coverCases")
    fun coverTest(input: List<IntRange>, expected: Boolean) {
        assertThat(input.findNotCoveredRange(0, 20).isEmpty()).isEqualTo(expected)
    }

    private fun String.toIntRange(): IntRange {
        val (min, max) = split("..").map { it.toInt() }
        return IntRange(min, max)
    }

    private fun String.toIntRangeList(): List<IntRange> {
        return if (this == "empty") {
            listOf()
        } else {
            this.split(",").map { it.toIntRange() }
        }
    }
}