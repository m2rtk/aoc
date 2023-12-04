@file:Suppress("unused")

package aoc.y23.d4

import aoc.Puzzle
import kotlin.math.pow

data class Card(val id: Int, val winning: Set<Int>, val numbers: List<Int>) {
    val matches = numbers.count { winning.contains(it) }
}

fun String.parseCard(): Card {
    val (id, rest) = this.removePrefix("Card").split(":", limit = 2)
    val (winningPart, numberPart) = rest.trim().split("|", limit = 2)

    return Card(
        id = id.trim().toInt(),
        winning = winningPart.trim().split(Regex("\\s+")).map { it.toInt() }.toSet(),
        numbers = numberPart.trim().split(Regex("\\s+")).map { it.toInt() }.toList()
    )
}

class D4P1 : Puzzle {
    override fun solve(input: String): Any {
        return input.lines().map { it.parseCard() }
            .filter { it.matches > 0 }
            .sumOf { 2.0.pow(it.matches - 1).toInt() }
    }
}

data class Cards(var count: Int, val card: Card)

class D4P2 : Puzzle {
    override fun solve(input: String): Any {
        val cards = input.lines().map { it.parseCard() }.map { Cards(1, it) }

        for (i in cards.indices) {
            val card = cards[i]

            for (j in i+1 .. i+card.card.matches) {
                if (j < cards.size) {
                    cards[j].count += card.count
                }
            }
        }

        return cards.sumOf { it.count }
    }
}