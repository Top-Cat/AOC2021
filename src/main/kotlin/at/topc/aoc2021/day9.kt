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

typealias CaveFloor = List<List<Int>>
data class Lowpoint(val x: Int, val y: Int, val height: Int)

@ExperimentalTime
class Day9 {
    private val input = readInput(9) {
        it.map { n ->
            n.digitToIntOrNull() ?: 0
        }
    }

    private fun CaveFloor.safeGet(x: Int, y: Int) = this.getOrNull(y)?.getOrNull(x)?.let { Lowpoint(x, y, it) }
    private fun Lowpoint.getNeighbours() = listOfNotNull(
        input.safeGet(x, y - 1), // up
        input.safeGet(x - 1, y), // left
        input.safeGet(x + 1, y), // right
        input.safeGet(x, y + 1) // down
    )
    private fun Lowpoint.isLowpoint() = getNeighbours().all { it.height > height }
    private fun Lowpoint.lowestNeighbour() = getNeighbours().minByOrNull { it.height } ?: this

    fun partOne() =
        input.flatMapIndexed { y, row ->
            row.mapIndexedNotNull { x, n ->
                Lowpoint(x, y, n).let {
                    if (it.isLowpoint()) {
                        it
                    } else {
                        null
                    }
                }
            }
        }.sumOf { it.height + 1 }

    private fun Lowpoint.flowsTo(): Lowpoint =
        lowestNeighbour().let { n ->
            if (n.height < height) {
                n.flowsTo()
            } else {
                this
            }
        }

    fun partTwo() = input
        .flatMapIndexed { y, row ->
            row.mapIndexedNotNull { x, n ->
                if (n == 9) {
                    null
                } else {
                    Lowpoint(x, y, n).flowsTo()
                }
            }
        }
        .groupBy { it }
        .mapValues { it.value.size }
        .toList()
        .sortedByDescending { it.second }
        .take(3)
        .fold(1) { prev, elem ->
            prev * elem.second
        }
}
