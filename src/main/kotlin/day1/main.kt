package day1

import readInput

fun main() {
    Main().also {
        println(it.partOne())
        println(it.partTwo())
    }
}

class Main {
    private val input = readInput(1) { it.toIntOrNull() ?: 0 }

    fun partOne() = compareWithGap(1)
    fun partTwo() = compareWithGap(3)

    private fun compareWithGap(gap: Int) = input.drop(gap).filterIndexed { idx, a -> a > input[idx] }.count()
}