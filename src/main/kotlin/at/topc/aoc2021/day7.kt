package at.topc.aoc2021

import kotlin.math.abs
import kotlin.time.ExperimentalTime

@ExperimentalTime
fun main() {
    Day7().also {
        printTime("Day 7") {
            println(it.partOne()) // 344735
            println(it.partTwo()) // 96798233
        }
    }
}

@ExperimentalTime
class Day7 {
    private val input = readInput(7).split(",").map { it.toIntOrZero() }
    private val maxInput by lazy { input.maxOf { it } }

    private fun triangle(n: Int): Int = (n * (n + 1)) / 2

    fun partOne() = minFuel()

    fun partTwo() = minFuel(::triangle)

    private fun minFuel(block: (Int) -> Int = { it }) = (1..maxInput).minOf {
        input.sumOf { n -> block(abs(n - it)) }
    }
}