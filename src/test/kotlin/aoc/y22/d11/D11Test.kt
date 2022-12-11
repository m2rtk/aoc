@file:Suppress("unused")

package aoc.y22.d11

import aoc.run
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.math.BigInteger

class D11Test {

    private val p1Solution: Any = 110264L
    private val p2Solution: Any = 23612457316L

    private fun p1() = D11P1()
    private fun p2() = D11P2()

    @Test
    fun input1() {
        assertThat(p1().run("input")).isEqualTo(p1Solution)
    }

    @Test
    fun input2() {
        assertThat(p2().run("input")).isEqualTo(p2Solution)
    }

    @Test
    fun t2() {
        val monkeys = Monkeys.parse("""
Monkey 0:
  Starting items: 79, 98
  Operation: new = old * 19
  Test: divisible by 23
    If true: throw to monkey 2
    If false: throw to monkey 3

Monkey 1:
  Starting items: 54, 65, 75, 74
  Operation: new = old + 6
  Test: divisible by 19
    If true: throw to monkey 2
    If false: throw to monkey 0

Monkey 2:
  Starting items: 79, 60, 97
  Operation: new = old * old
  Test: divisible by 13
    If true: throw to monkey 1
    If false: throw to monkey 3

Monkey 3:
  Starting items: 74
  Operation: new = old + 3
  Test: divisible by 17
    If true: throw to monkey 0
    If false: throw to monkey 1
        """.trimIndent())
        val lcm = monkeys.list.map { it.divBy }.reduce { a, b -> a * b }.let { BigInteger.valueOf(it.toLong()) }

        for (i in 0 until 20){
            monkeys.runRound { it.mod(lcm) }
        }

        monkeys.printRound()
        assertThat(monkeys.list.map { it.inspectCounter }).containsExactly(99L, 97L, 8L, 103L)
    }
}