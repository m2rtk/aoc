@file:Suppress("unused")

package aoc.y15.d6

import aoc.run
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class D6Test {

    private val p1Solution: Any = 543903
    private val p2Solution: Any = 14687245

    private fun p1() = D6P1()
    private fun p2() = D6P2()

    @Test
    fun input1() {
        assertThat(p1().run("input")).isEqualTo(p1Solution)
    }

    @Test
    fun input2() {
        assertThat(p2().run("input")).isEqualTo(p2Solution)
    }
}