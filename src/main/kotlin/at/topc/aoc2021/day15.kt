package at.topc.aoc2021

import kotlin.math.abs

fun main() {
    Day15().also {
        printTime("Day 15") {
            println(it.partOne()) // 698
            println(it.partTwo()) // 3022
        }
    }
}

typealias Cavern = List<List<Risk>>
data class Risk(val x: Int, val y: Int, val risk: Int)

const val MAX_SCORE = 99999999

abstract class Grid {
    private val distanceMemo = mutableMapOf<Pair<Risk, Risk>, Int>()
    open fun heuristicDistance(start: Risk, finish: Risk) =
        distanceMemo.computeIfAbsent(start to finish) {
            val dx = abs(start.x - finish.x)
            val dy = abs(start.y - finish.y)
            abs(dx - dy)
        }

    abstract fun getNeighbours(position: Risk): List<Risk>

    open fun moveCost(from: Risk, to: Risk) = to.risk
}

fun aStarSearch(start: Risk, finish: Risk, grid: Grid): Int {
    val openVertices = mutableSetOf(start)
    val closedVertices = mutableSetOf<Risk>()
    val costFromStart = mutableMapOf(start to 0)
    val estimatedTotalCost = mutableMapOf(start to grid.heuristicDistance(start, finish))

    while (
        openVertices
            .minByOrNull { estimatedTotalCost.getValue(it) }
            ?.let { currentPos ->
                // Check if we have reached the finish
                if (currentPos == finish) {
                    return estimatedTotalCost.getValue(finish)
                }

                // Mark the current vertex as closed
                openVertices.remove(currentPos)
                closedVertices.add(currentPos)

                grid.getNeighbours(currentPos)
                    .minus(closedVertices)
                    .map { neighbour -> neighbour to costFromStart.getValue(currentPos) + grid.moveCost(currentPos, neighbour) }
                    .filter { (neighbour, score) -> score < costFromStart.getOrDefault(neighbour, MAX_SCORE) }
                    .forEach { (neighbour, score) ->
                        openVertices.add(neighbour)
                        costFromStart[neighbour] = score
                        estimatedTotalCost[neighbour] = score + grid.heuristicDistance(neighbour, finish)
                    }
            } != null) {
        // :)
    }

    throw IllegalArgumentException("No Path from Start $start to Finish $finish")
}

class Day15 {
    private val input = readInput(15) {
        it.map { c ->
            c.digitToIntOrNull() ?: 0
        }
    }

    private val cavern = input.mapIndexed { y, row ->
        row.mapIndexed { x, n ->
            Risk(x, y, n)
        }
    }
    private val bigCavern = (0 until (5 * input.size)).map { y ->
        (0 until (5 * input.first().size)).map { x ->
            val add = (y / input.size) + (x / input.first().size)
            val out = input[y % input.size][x % input.first().size] + add
            Risk(x, y, if (out > 9) out - 9 else out)
        }
    }

    private fun Cavern.safeGet(x: Int, y: Int) = this.getOrNull(y)?.getOrNull(x)
    private fun neighbours(c: Cavern) = object : Grid() {
        private val neighbours = c.flatten().associateWith {
            with(it) {
                listOfNotNull(
                    c.safeGet(x, y - 1), // up
                    c.safeGet(x - 1, y), // left
                    c.safeGet(x + 1, y), // right
                    c.safeGet(x, y + 1) // down
                )
            }
        }

        override fun getNeighbours(position: Risk) = neighbours[position] ?: listOf()
    }

    fun partOne() = aStarSearch(cavern.first().first(), cavern.last().last(), neighbours(cavern))

    fun partTwo() = aStarSearch(bigCavern.first().first(), bigCavern.last().last(), neighbours(bigCavern))
}
