package at.topc.aoc2021

import kotlin.math.abs
import kotlin.math.min
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

    fun partOne() = minByDescent()

    fun partTwo() = minByDescent(::triangle)

    private fun fx(guess: Int, block: (Int) -> Int = { it }) = input.sumOf { n -> block(abs(n - guess)) }

    private tailrec fun minByDescent(block: (Int) -> Int = { it }, min: Int = 1, max: Int = maxInput): Int {
        val avg = (min + max) / 2
        // Check slope at point
        val a = fx(avg, block)
        val b = fx(avg - 1, block)

        return if (max - min < 2) {
            min(a, b)
        } else if (a < b) {
            minByDescent(block, avg, max)
        } else {
            minByDescent(block, min, avg)
        }
    }

    private fun minFuel(block: (Int) -> Int = { it }) = (1..maxInput).minOf {
        input.sumOf { n -> block(abs(n - it)) }
    }
}