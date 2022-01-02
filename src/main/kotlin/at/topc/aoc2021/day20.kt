package at.topc.aoc2021

fun main() {
    Day20().also {
        printTime("Day 20") {
            println(it.partOne()) // 5081
            println(it.partTwo()) // 15088
        }
    }
}

class Day20 {
    private val input = getFile(20).readLines()
    private val algo = input.first().toCharArray()
    private val image = input.drop(2).map { it.toCharArray() }

    private fun getIndex(inImage: List<CharArray>, x: Int, y: Int, borderChar: Char) = if (inImage.getOrElse(y) {
        "".toCharArray()
    }.getOrElse(x) {
        borderChar
    } != '.') 1 else 0

    private fun getChunk(inImage: List<CharArray>, x: Int, y: Int, borderChar: Char) =
        (0..2).flatMap { y2 ->
            (0..2).map { x2 ->
                getIndex(inImage, x - 2 + x2, y - 2 + y2, borderChar)
            }
        }.mapIndexed { idx, c ->
            c shl (8 - idx)
        }.sum()

    private fun convolution(inImage: List<CharArray>, borderChar: Char = '.') =
        (0..inImage.size+1).map { y ->
            (0..inImage.first().size+1).map { x ->
                algo[getChunk(inImage, x, y, borderChar)]
            }.toCharArray()
        }

    private fun isLit(c: Char): Int = if (c == '#') 1 else 0
    private fun totalLit(inImage: List<CharArray>) = inImage.sumOf { a -> a.sumOf(::isLit) }

    fun partOne() = applyAlgo()

    fun partTwo() = applyAlgo(50)

    private fun applyAlgo(rounds: Int = 2) = (0 until (rounds / 2)).fold(image) { inp, _ ->
        convolution(
            convolution(inp),
            algo[0]
        )
    }.let {
        totalLit(it)
    }
}
