package at.topc.aoc2021

import kotlin.time.ExperimentalTime

@ExperimentalTime
fun main() {
    Day10().also {
        printTime("Day 10") {
            println(it.partOne()) // 415953
            println(it.partTwo()) // 2292863731
        }
    }
}

class CorruptLineException(msg: String, actualChar: Char) : Exception(msg) {
    val score = charScore[actualChar] ?: 0

    companion object {
        private val charScore = mapOf(
            ')' to 3,
            ']' to 57,
            '}' to 1197,
            '>' to 25137
        )
    }
}

@ExperimentalTime
class Day10 {
    private val input = readInput(10) { it }
    private val validChars = mapOf(
        '(' to ')',
        '[' to ']',
        '<' to '>',
        '{' to '}'
    )

    private fun parseSection(openingChar: List<Char>, remaining: String): List<Char> =
        if (remaining.isEmpty()) {
            openingChar.reversed().mapNotNull { validChars[it] }
        } else if (remaining.first() == validChars[openingChar.last()]) {
            if (openingChar.size > 1) {
                parseSection(openingChar.dropLast(1), remaining.drop(1))
            } else {
                parseSection(listOf(remaining[1]), remaining.drop(2))
            }
        } else if (validChars.containsKey(remaining.first())) {
            parseSection(openingChar.plus(remaining.first()), remaining.drop(1))
        } else {
            throw CorruptLineException("Expected ${validChars[openingChar.last()]} but ${remaining.first()} found instead.", remaining.first())
        }

    fun partOne() =
        input.sumOf {
            try {
                parseSection(listOf(it.first()), it.drop(1))
                0 // Ignore incomplete lines
            } catch (e: CorruptLineException) {
                e.score
            }
        }

    fun partTwo() =
        mapOf(
            ')' to 1,
            ']' to 2,
            '}' to 3,
            '>' to 4
        ).withDefault { 0 }.let { charScore ->
            input.mapNotNull {
                try {
                    parseSection(listOf(it.first()), it.drop(1))
                        .fold(0L) { prev, elem ->
                            prev * 5 + charScore.getValue(elem)
                        }
                } catch (e: CorruptLineException) {
                    null // Ignore corrupted lines
                }
            }.sorted().let {
                it[it.size / 2] // Get median element
            }
        }
}
