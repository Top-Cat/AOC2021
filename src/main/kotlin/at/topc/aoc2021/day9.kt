package at.topc.aoc2021

import kotlin.time.ExperimentalTime

@ExperimentalTime
fun main() {
    Day9().also {
        printTime("Day 9") {
            println(it.partOne()) // 575
            println(it.partTwo()) // 1019700
        }
    }
}

typealias CaveFloor = List<List<Lowpoint>>
data class Lowpoint(val x: Int, val y: Int, val height: Int)

@ExperimentalTime
class Day9 {
    private val input = readInput(9) {
        it.map { n ->
            n.digitToIntOrNull() ?: 0
        }
    }
    private val caveFloor = input.mapIndexed { y, row ->
        row.mapIndexed { x, n ->
            Lowpoint(x, y, n)
        }
    }
    private val flattenedFloor = caveFloor.flatten()

    private fun CaveFloor.safeGet(x: Int, y: Int) = this.getOrNull(y)?.getOrNull(x)
    private fun Lowpoint.getNeighbours() = listOfNotNull(
        caveFloor.safeGet(x, y - 1), // up
        caveFloor.safeGet(x - 1, y), // left
        caveFloor.safeGet(x + 1, y), // right
        caveFloor.safeGet(x, y + 1) // down
    )
    private fun Lowpoint.isLowpoint() = getNeighbours().all { it.height > height }
    private fun Lowpoint.lowestNeighbour() = getNeighbours().minByOrNull { it.height } ?: this
    private fun Lowpoint.flowsTo(): Lowpoint =
        lowestNeighbour().let { n ->
            if (n.height < height) {
                n.flowsTo()
            } else {
                this
            }
        }

    fun partOne() = flattenedFloor.filter { it.isLowpoint() }.sumOf { it.height + 1 }

    fun partTwo() = flattenedFloor
        .filter { it.height != 9 }
        .map { it.flowsTo() }
        .groupBy { it }
        .mapValues { it.value.size }
        .toList()
        .sortedByDescending { it.second }
        .take(3)
        .fold(1) { prev, elem ->
            prev * elem.second
        }
}