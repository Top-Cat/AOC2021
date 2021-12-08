package at.topc.aoc2021

import kotlin.time.ExperimentalTime

@ExperimentalTime
fun main() {
    Day8().also {
        printTime("Day 8") {
            println(it.partOne()) // 264
            println(it.partTwo()) // 1063760
        }
    }
}

typealias Line = Pair<List<String>, List<String>>
class Display(private val segments: List<Segment> = (1..7).map { Segment() }) {
    fun handleClue(it: String) {
        val solvedChars = segments.filter { it.done() }.map { it.char() }
        val withoutSolved = it.filter { c -> !solvedChars.contains(c) }

        when (it.length) {
            2 -> listOf(2, 5) // 1
            3 -> listOf(0, 2, 5) // 7
            4 -> listOf(1, 2, 3, 5) // 4
            5 -> listOf(0, 3, 6) // 2, 3, 5
            6 -> listOf(0, 1, 5, 6) // 6, 9, 0
            // 7 -> 8
            else -> listOf()
        }.forEach { idx ->
            if (segments[idx].handleClue(withoutSolved)) {
                onCompleted(segments[idx])
            }
        }
    }

    private fun onCompleted(segment: Segment) {
        segments.minus(segment).forEach {
            if (!it.done()) {
                it.remove(segment.char())
                if (it.done()) {
                    onCompleted(it)
                }
            }
        }
    }

    fun done() = segments.all { it.done() }

    private val numberLookup = mapOf(
        listOf(true, true, true, false, true, true, true) to 0,
        listOf(false, false, true, false, false, true, false) to 1,
        listOf(true, false, true, true, true, false, true) to 2,
        listOf(true, false, true, true, false, true, true) to 3,
        listOf(false, true, true, true, false, true, false) to 4,
        listOf(true, true, false, true, false, true, true) to 5,
        listOf(true, true, false, true, true, true, true) to 6,
        listOf(true, false, true, false, false, true, false) to 7,
        listOf(true, true, true, true, true, true, true) to 8,
        listOf(true, true, true, true, false, true, true) to 9
    )

    fun numberFor(it: String) = numberLookup[(1..7).map { idx ->
            it.contains(segments[idx - 1].char())
        }] ?: -1
}
class Segment(private val possible: MutableList<Char> = ('a'..'g').toMutableList()) {
    fun char() = if (possible.size == 1) possible[0] else 'z'

    fun handleClue(it: String): Boolean {
        if (!done()) {
            possible.removeIf { c -> !it.contains(c) }
            return done()
        }
        return false
    }

    fun done() = possible.size <= 1

    fun remove(solvedChars: Char) {
        possible.remove(solvedChars)
    }
}

@ExperimentalTime
class Day8 {
    private val input = readInput(8) {
        it.split(" | ").map { s ->
            s.split(" ")
        }.let { l -> Line(l[0], l[1]) }
    }
    private val uniqueSegments = mapOf(
        2 to 1,
        4 to 4,
        3 to 7,
        7 to 8
    )

    private fun isUniqueSegment(s: String) = if (uniqueSegments.containsKey(s.length)) 1 else 0

    fun partOne() = input.sumOf {
            it.second.sumOf(::isUniqueSegment)
        }

    fun partTwo() = input.sumOf { line ->
            // Handle unique numbers first
            val display = Display()

            while (!display.done()) {
                line.first.forEach { c ->
                    display.handleClue(c)
                }
            }

            line.second.map {
                display.numberFor(it)
            }.joinToString("").toInt()
        }
}
