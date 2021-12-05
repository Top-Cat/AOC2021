package day5

import readInput
import toIntOrZero

fun main() {
    Main().also {
        println(it.partOne()) // 7414
        println(it.partTwo()) // 19676
    }
}

typealias LineSegment = Pair<Point, Point>
typealias Point = Pair<Int, Int>
operator fun Point.compareTo(other: Point) = if (first == other.first) second - other.second else first - other.first

class Main {
    private val input: List<LineSegment> = readInput(5) { str ->
        str.split(" -> ").map { c ->
            c.split(",").map { n ->
                n.toIntOrZero()
            }.let {
                Point(it[0], it[1])
            }
        }.let {
            if (it[0] > it[1]) LineSegment(it[1], it[0]) else LineSegment(it[0], it[1])
        }
    }

    fun partOne() = findDuplicates(false)

    fun partTwo() = findDuplicates(true)

    private fun allPoints(segment: LineSegment, includeDiagonal: Boolean) = with(segment) {
        if (first.first == second.first) {
            (first.second..second.second).map { first.first to it }
        } else if (first.second == second.second) {
            (first.first..second.first).map { it to first.second }
        } else if (!includeDiagonal) {
            listOf()
        } else {
            val sign = if (first.second > second.second) -1 else 1
            (first.first..second.first).map { it to first.second + sign * (it - first.first) }
        }
    }

    private fun findDuplicates(includeDiagonal: Boolean) = mutableMapOf<Point, Int>().also { map ->
        input.forEach { elem ->
            allPoints(elem, includeDiagonal).forEach {
                map.merge(it, 1) { old, one -> old + one }
            }
        }
    }.filter { it.value > 1 }.count()

}