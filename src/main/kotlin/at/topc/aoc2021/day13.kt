package at.topc.aoc2021

import kotlin.math.max

fun main() {
    Day13().also {
        printTime("Day 13") {
            println(it.partOne()) // 759
            println(it.partTwo()) // HECRZKPR
        }
    }
}

data class Fold(val inX: Boolean, val pos: Int)

class Day13 {
    private val input = readInput(13) {
        if (it.contains(",")) {
            it.split(",").let { parts ->
                parts[0].toIntOrZero() to parts[1].toIntOrZero()
            }
        } else if (it.isNotBlank()) {
            it.removePrefix("fold along ").split("=").let { parts ->
                Fold(parts[0] == "x", parts[1].toIntOrZero())
            }
        } else {
            null
        }
    }
    private val folds = input.mapNotNull { it as? Fold }
    private val grid = input.mapNotNull { it as? Pair<Int, Int> }

    private fun fold(grid: List<Pair<Int, Int>>, fold: Fold) = grid.map {
            (if (fold.inX && it.first > fold.pos) (fold.pos * 2) - it.first else it.first) to
            (if (!fold.inX && it.second > fold.pos) (fold.pos * 2) - it.second else it.second)
        }.distinct()

    private fun printGrid(grid: List<Pair<Int, Int>>) =
        grid.associateWith { "⬛" }.let { lookup ->
            grid.fold(0 to 0) { prev, elem ->
                max(prev.first, elem.first) to max(prev.second, elem.second)
            }.let { gridSize ->
                (0..gridSize.second).joinToString("\n") { y ->
                    (0..gridSize.first).joinToString(" ") { x ->
                        lookup.getOrDefault(x to y, "⬜")
                    }
                }
            }
        }

    fun partOne() = fold(grid, folds.first()).size

    fun partTwo() = folds.fold(grid) { prev, fold ->
        fold(prev, fold)
    }.let { printGrid(it) }
}
