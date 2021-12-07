package at.topc.aoc2021

fun main() {
    Day3().also {
        println(it.partOne()) // 4191876
        println(it.partTwo()) // 3414905
    }
}

typealias Binary = List<Int>
fun Binary.toNumber() = mapIndexed { idx, elem -> if (elem == 0) 0 else 1 shl (size - idx - 1) }.sumOf { it }
fun Int.inverse(size: Int) = ((1 shl size) - 1) - this

class Day3 {
    private val input: List<Binary> = readInput(3) { str ->
        str.map { it.digitToInt() }
    }

    private val gamma = mostCommon(input)

    private tailrec fun filterByPosition(arr: List<Binary>, idx: Int = 0, mostCommon: Boolean = true): Binary =
        if (arr.size == 1) {
            arr.first()
        } else {
            val common = mostCommon(arr)
            filterByPosition(arr.filter { (it[idx] == common[idx]) xor !mostCommon }, idx + 1, mostCommon)
        }

    private fun mostCommon(arr: List<Binary>) = arr.reduce { prev, elem ->
        prev.zip(elem).map {
            it.first + it.second
        }
    }.map { if (it >= arr.size / 2.0) 1 else 0 }

    fun partOne() = gamma.toNumber().let { gamma ->
        gamma * gamma.inverse(input[0].size)
    }

    fun partTwo() =
        filterByPosition(input).toNumber() * // oxygenGenerator
        filterByPosition(input, mostCommon = false).toNumber() // co2Scrubber
}