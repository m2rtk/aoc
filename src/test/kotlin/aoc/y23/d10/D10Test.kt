@file:Suppress("unused")

package aoc.y23.d10

import aoc.run
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

class D10Test {

    private fun p1() = D10P1()
    private fun p2() = D10P2()

    @Test
    fun input1() {
        assertThat(p1().run("input")).isEqualTo(6882)
    }

    @Test
    fun input2() {
        assertThat(p2().run("input")).isEqualTo(491)
    }

    @Test
    fun sample11() {
        assertThat(p1().run("sample1")).isEqualTo(4)
    }
    @Test
    fun sample12() {
        assertThat(p1().run("sample2")).isEqualTo(4)
    }
    @Test
    fun sample13() {
        assertThat(p1().run("sample3")).isEqualTo(8)
    }
    @Test
    fun sample14() {
        assertThat(p1().run("sample4")).isEqualTo(8)
    }

    @Test
    fun sample25() {
        assertThat(p2().run("sample5")).isEqualTo(4)
    }
    @Test
    fun sample26() {
        assertThat(p2().run("sample6")).isEqualTo(4)
    }
    @Test
    fun sample27() {
        assertThat(p2().run("sample7")).isEqualTo(8)
    }
    @Test
    fun sample28() {
        assertThat(p2().run("sample8")).isEqualTo(10)
    }
}