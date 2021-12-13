package at.topc.aoc2021

import java.util.concurrent.atomic.AtomicBoolean
import java.util.concurrent.atomic.AtomicInteger
import kotlin.time.ExperimentalTime

@ExperimentalTime
fun main() {
    Day11().also {
        printTime("Day 11") {
            println(it.partOne()) // 1585
            println(it.partTwo()) // 382
        }
    }
}

@ExperimentalTime
data class Octopus(val day11: Day11, val x: Int, val y: Int, val level: AtomicInteger, val hasFlashed: AtomicBoolean = AtomicBoolean(false)) {
    fun clear() = hasFlashed.getAndSet(false).also {
        if (level.get() > 9) {
            level.set(0)
        }
    }

    private fun getNeighbours() = (0..2).flatMap { xd ->
        (0..2).mapNotNull { yd ->
            if (xd == 1 && yd == 1) {
                null // Ignore ourselves
            } else {
                day11.octopi.getOrNull(y - 1 + yd)?.getOrNull(x - 1 + xd)
            }
        }
    }

    fun flash(): Int =
        if (level.get() > 9 && hasFlashed.compareAndSet(false, true)) {
            getNeighbours().sumOf { it.inc(true) } + 1
        } else {
            0
        }

    fun inc(andFlash: Boolean = false) = level.incrementAndGet().let {
        if (andFlash) {
            flash()
        } else {
            0
        }
    }
}

@ExperimentalTime
class Day11 {
    private val input = readInput(11) {
        it.map { c ->
            c.digitToIntOrNull() ?: 0
        }
    }
    val octopi = input.mapIndexed { y, row ->
        row.mapIndexed { x, n ->
            Octopus(this, x, y, AtomicInteger(0))
        }
    }
    private val flattenedOctopi = octopi.flatten()

    private fun setup() {
        input.forEachIndexed { y, row ->
            row.forEachIndexed { x, n ->
                octopi.getOrNull(y)?.getOrNull(x)?.level?.set(n)
            }
        }
    }

    fun partOne() =
        setup().let {
            (1..100).sumOf { _ ->
                flattenedOctopi
                    .onEach { it.inc() }                    // Increment energy level
                    .map { it to it.flash() }               // Flash if energy level great enough (include in function)
                    .sumOf { it.first.clear(); it.second }  // Clear hasFlashed flags and set energy of flashed octopi to 0
            }
        }

    fun partTwo() =
        setup().let {
            var i = 1
            while (!flattenedOctopi
                    .onEach { it.inc() }
                    .onEach { it.flash() }
                    .map { it.clear() }.all { it }) {
                i++
            }

            i
        }
}
