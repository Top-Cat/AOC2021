package day4

import readInput
import toIntOrZero

fun main() {
    Main().also {
        println(it.partOne()) // 33348
        println(it.partTwo()) // 8112
    }
}

data class WinInfo(val steps: Int, val lastCalled: Int, val picked: List<Int>, val remaining: List<Int>) {
    fun answer() = remaining.sumOf { it } * lastCalled
}

class BingoCard(private val numbers: List<List<Int>>) {
    tailrec fun winFrom(calls: List<Int>, pick: List<List<Boolean>> = numbers.map { it.map { false } }, steps: Int = 0, lastCalled: Int? = null): WinInfo? =
        if (hasWon(pick)) {
            val pickedLocal = numbers.mapIndexed { ida, a -> a.mapIndexedNotNull { idb, b -> if (pick[ida][idb]) b else null } }.flatten()
            WinInfo(steps, lastCalled ?: 0, pickedLocal, numbers.flatten().minus(pickedLocal))
        } else if (calls.isEmpty()) {
            null
        } else {
            val newPick = pick.mapIndexed { ida, elem -> elem.mapIndexed { idb, e -> if (numbers[ida][idb] == calls[0]) true else e } }
            winFrom(calls.drop(1), newPick, steps + 1, calls[0])
        }

    private fun hasWon(pick: List<List<Boolean>>) =
        pick.any { it.all { b -> b } } && // Any row has all picked
        pick.first().mapIndexed { idx, b -> pick.all { it[idx] } }.any() // Any column has all picked
}

class Main {
    private val input = readInput(4).split("\n\n")
    private val calls = input.first().split(",").map { it.toIntOrZero() }
    private val sheets: List<BingoCard> = input.drop(1).map {
        it.split("\n").map { l ->
            l.split(" ").mapNotNull { n ->
                n.toIntOrNull()
            }
        }.let { card -> BingoCard(card) }
    }

    private val wins = sheets.mapNotNull { it.winFrom(calls) }

    fun partOne() = wins.minByOrNull { it.steps }?.answer()

    fun partTwo() = wins.maxByOrNull { it.steps }?.answer()
}