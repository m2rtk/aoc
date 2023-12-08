@file:Suppress("unused")

package aoc.y23.d7

import aoc.Puzzle

internal enum class HandType(val value: Int) {
    FIVE_OF_A_KIND(7),
    FOUR_OF_A_KIND(6),
    FULL_HOUSE(5),
    THREE_OF_A_KIND(4),
    TWO_PAIR(3),
    ONE_PAIR(2),
    HIGH_CARD(1),
}


private fun CharArray.normalize(): List<Int> {
    if (this.isEmpty()) {
        return listOf()
    }
    val sorted = this.sorted()
    val newArray = mutableListOf<Int>()

    var timesSeen = 1
    var char: Char = sorted[0]
    for (c in sorted.subList(1, sorted.size)) {
        if (c == char) {
            timesSeen++
        } else {
            newArray.add(timesSeen)
            char = c
            timesSeen = 1
        }
    }

    newArray.add(timesSeen)

    return newArray
}

private fun determineHandType(normalizedHand: List<Int>): HandType {
    if (normalizedHand.size == 1) {
        return HandType.FIVE_OF_A_KIND
    }

    if (normalizedHand.contains(4)) {
        return HandType.FOUR_OF_A_KIND
    }

    if (normalizedHand.contains(3) && normalizedHand.contains(2)) {
        return HandType.FULL_HOUSE
    }

    if (normalizedHand.contains(3)) {
        return HandType.THREE_OF_A_KIND
    }

    if (normalizedHand.count { it == 2 } == 2) {
        return HandType.TWO_PAIR
    }

    if (normalizedHand.contains(2)) {
        return HandType.ONE_PAIR
    }

    return HandType.HIGH_CARD
}

internal data class Hand(val raw: String) {

    val type: HandType by lazy {
        val arr = raw.toCharArray().normalize()
        determineHandType(arr)//.also { println("$raw -> $arr -> $it") }
    }

    val typeWithWildcard: HandType by lazy {
        val initArr: List<Int> = raw.toCharArray().filter { it != 'J' }.toCharArray().normalize()
        val wildcards = 5 - initArr.sum()

        if (wildcards == 0) {
            return@lazy type
        }

        val arr = if (initArr.isNotEmpty()) {
            val highest = initArr.indexOf(initArr.max())
            val mutable = initArr.toMutableList()
            mutable[highest] += wildcards
            mutable
        } else {
            listOf(5)
        }

        determineHandType(arr)//.also { println("$raw -> $initArr -> $arr -> $it") }
    }
}

internal data class HandAndBid(val hand: Hand, val bid: Int)

private fun String.parseHand(): HandAndBid {
    val (hand, bid) = split(Regex("\\s+"), limit = 2)
    return HandAndBid(Hand(hand), bid.toInt())
}

private val typeComparator = Comparator<Hand> { a, b -> a.type.value - b.type.value }
private val wildcardTypeComparator = Comparator<Hand> { a, b -> a.typeWithWildcard.value - b.typeWithWildcard.value }

private fun cardComparator(map: Map<Char, Int>) = Comparator<Hand> { a, b ->
    for (i in 0 until 5) {
        val diff = map[a.raw[i]]!! - map[b.raw[i]]!!

        if (diff != 0) {
            return@Comparator diff
        }
    }
    return@Comparator 0
}

private fun List<Hand>.calculateTotalWinnings(
    handToBid: Map<Hand, Int>
): Long = foldIndexed(0) { index, acc, hand ->
    val bid = handToBid[hand]!!
    val rank = index + 1
    val winning = rank * bid

//    println("${hand.raw} ${rank.toString().padStart(4)} * ${bid.toString().padStart(5)} = ${winning.toString().padStart(10)} acc=${acc+winning}")

    acc + winning
}

private fun String.parse(): Map<Hand, Int> = lines().map { it.parseHand() }.associate { it.hand to it.bid }

class D7P1 : Puzzle {

    private val labelToValue =
        mapOf(
            '2' to 0,
            '3' to 1,
            '4' to 2,
            '5' to 3,
            '6' to 4,
            '7' to 5,
            '8' to 6,
            '9' to 7,
            'T' to 8,
            'J' to 9,
            'Q' to 10,
            'K' to 11,
            'A' to 12
        )

    override fun solve(input: String): Any {
        val handToBid = input.parse()
        return handToBid.keys
            .sortedWith(typeComparator.then(cardComparator(labelToValue)))
            .calculateTotalWinnings(handToBid)
    }
}

class D7P2 : Puzzle {
    private val labelToValuePart2 =
        mapOf(
            'J' to -1,
            '2' to 0,
            '3' to 1,
            '4' to 2,
            '5' to 3,
            '6' to 4,
            '7' to 5,
            '8' to 6,
            '9' to 7,
            'T' to 8,
            'Q' to 10,
            'K' to 11,
            'A' to 12
        )

    override fun solve(input: String): Any {
        val handToBid = input.parse()
        return handToBid.keys
            .sortedWith(wildcardTypeComparator.then(cardComparator(labelToValuePart2)))
            .calculateTotalWinnings(handToBid)
    }
}