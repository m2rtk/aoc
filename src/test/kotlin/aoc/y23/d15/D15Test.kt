@file:Suppress("unused")

package aoc.y23.d15

import aoc.run
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class D15Test {

    private fun p1() = D15P1()
    private fun p2() = D15P2()

    @Test
    fun input1() {
        assertThat(p1().run("input")).isEqualTo(510792)
    }

    @Test
    fun input2() {
        assertThat(p2().run("input")).isEqualTo(269410)
    }

    @Test
    fun sample1() {
        assertThat(p1().solve("rn=1,cm-,qp=3,cm=2,qp-,pc=4,ot=9,ab=5,pc-,pc=6,ot=7")).isEqualTo(1320)
    }

    @Test
    fun sample2() {
        assertThat(p2().solve("rn=1,cm-,qp=3,cm=2,qp-,pc=4,ot=9,ab=5,pc-,pc=6,ot=7")).isEqualTo(145)
    }
}