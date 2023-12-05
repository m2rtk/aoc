@file:Suppress("unused")

package aoc.y23.d5

import aoc.Puzzle

data class Almanac(
    val seeds: List<LongRange>,
    val maps: List<ConversionMap>,
) {

    fun uniqueSeeds() = sequence<Long> {
        for (seedRange in seeds) {
            for (seed in seedRange) {
                yield(seed)
            }
        }
    }

    fun calculate(seed: Long): Long {
        return maps.fold(seed) { acc, map -> map.invoke(acc) }
    }
}

data class ConversionMap(val name: String, val entries: List<ConversionMapEntry>) {

    fun invoke(input: Long): Long {
        for (entry in entries) {
            val result = entry.invoke(input)
            if (result != null) {
                return result
            }
        }
        return input
    }
}

data class ConversionMapEntry(
    val destinationRangeStart: Long,
    val sourceRangeStart: Long,
    val rangeLength: Long,
) {

    val sourceRangeEnd = sourceRangeStart + rangeLength
    val destinationRangeEnd = destinationRangeStart + rangeLength

    fun invoke(input: Long): Long? {
        if (input < sourceRangeStart || input >= sourceRangeEnd) {
            return null
        }

        return input + (destinationRangeStart - sourceRangeStart)
    }

    override fun toString() = "$destinationRangeStart $sourceRangeStart $rangeLength"
}

private fun String.parseAlmanac(seedsArePairs: Boolean = false): Almanac {
    val lines = lines()
    val seeds = lines[0].removePrefix("seeds:").trim().split(Regex("\\s+")).map { it.toLong() }

    val maps = mutableListOf<ConversionMap>()

    var currentMapName: String? = null
    var currentMap = mutableListOf<ConversionMapEntry>()
    for (line in lines.subList(2, lines.size)) {
        if (line.isBlank()) {
            continue
        }

        if (line.trim().endsWith("map:")) {
            if (currentMap.isNotEmpty()) {
                maps.add(ConversionMap(currentMapName!!, currentMap))
            }

            currentMapName = line.trim().removeSuffix("map:").trim()
            currentMap = mutableListOf()
        } else {
            val (destination, source, range) = line.trim().split(Regex("\\s+"), limit = 3).map { it.toLong() }
            currentMap.add(ConversionMapEntry(destination, source, range))
        }
    }

    if (currentMap.isNotEmpty()) {
        maps.add(ConversionMap(currentMapName!!, currentMap))
    }

    if (seedsArePairs) {
        val unpackedSeeds = mutableListOf<LongRange>()

        for ((start, range) in seeds.windowed(2, step = 2)) {
            unpackedSeeds.add(start until start + range)
        }

        println("parsed")
        return Almanac(unpackedSeeds, maps)
    }

    return Almanac(seeds.map { it..it }, maps)
}

class D5P1 : Puzzle {
    override fun solve(input: String): Any {
        val almanac = input.parseAlmanac()
        return almanac.uniqueSeeds().minOf { almanac.calculate(it) }
    }
}

/**
 * Slow
 * Left the printlns in because there should be a better solution
 */
class D5P2 : Puzzle {
    override fun solve(input: String): Any {
        val almanac = input.parseAlmanac(seedsArePairs = true)

        val mins = mutableListOf<Long>()
        for (seedRange in almanac.seeds) {
            seedRange.first

            println(seedRange)

            val firstResult = almanac.calculate(seedRange.first)
            val lastResult = almanac.calculate(seedRange.last)
            val range = seedRange.last - seedRange.first
            val diff = lastResult - firstResult
            val change = range - diff

            println("first=${seedRange.first}->${firstResult} last=${seedRange.last}->${lastResult} range=$range diff=$diff change=$change")

            val min = seedRange.reduce { acc, l ->
                val result = almanac.calculate(l)
                if (acc > result) result else acc
            }
            println("min=$min")
            mins.add(min)
        }

        return mins.min()
    }
}

/*
Last output here

929142010..1396911756
first=929142010->2928341086 last=1396911756->93329629 range=467769746 diff=-2835011457 change=3302781203
min=23846357

2497466808..2707633645
first=2497466808->2306805090 last=2707633645->2583894197 range=210166837 diff=277089107 change=-66922270
min=891387023

3768123711..3801340506
first=3768123711->1787915678 last=3801340506->3762102004 range=33216795 diff=1974186326 change=-1940969531
min=1787915679

1609270159..1696240008
first=1609270159->3872583593 last=1696240008->2020245818 range=86969849 diff=-1852337775 change=1939307624
min=1609270159

199555506..578165337
first=199555506->2768526595 last=578165337->1649144059 range=378609831 diff=-1119382536 change=1497992367min=20191102

1840685500..2154695210
first=1840685500->3135505386 last=2154695210->3644752276 range=314009710 diff=509246890 change=-195237180
min=401662950

1740069852..1776938106
first=1740069852->2064075662 last=1776938106->4179571644 range=36868254 diff=2115495982 change=-2078627728
min=1740069852

2161129344..2331619448
first=2161129344->3651186410 last=2331619448->491164501 range=170490104 diff=-3160021909 change=3330512013
min=140213466

2869967743..3135423107
first=2869967743->1377440429 last=3135423107->1208448819 range=265455364 diff=-168991610 change=434446974
min=334213102

3984276455..4015467342
first=3984276455->600279879 last=4015467342->762377361 range=31190887 diff=162097482 change=-130906595
min=600279880

D5P2(src\main\resources\aoc\y23\d5\input.txt) = 20191102
 */