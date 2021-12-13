package at.topc.aoc2021

import java.io.File
import kotlin.time.measureTime

fun getFile(day: Int) = File(Day1::class.java.getResource("/$day.txt")!!.toURI())
fun readInput(day: Int) = getFile(day).readText()
fun <T> readInput(day: Int, block: (String) -> T) = getFile(day).readLines().map(block)
fun String.toIntOrZero() = this.toIntOrNull() ?: 0

operator fun Pair<Int, Int>.plus(other: Pair<Int, Int>) = first + other.first to second + other.second

inline fun <T> printTime(title: String, block: () -> T): T {
    var result: T
    measureTime {
        result = block()
    }.let {
        println("Completed $title in ${it.inWholeMilliseconds}ms")
    }
    return result
}