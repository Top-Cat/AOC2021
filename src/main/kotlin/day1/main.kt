package day1

fun main() {
    Main().also {
        println(it.partOne())
        println(it.partTwo())
    }
}

class Main {
    private val text = this::class.java.getResource("/1.txt")?.readText() ?: ""
    private val input = text.split("\n").map { it.toIntOrNull() ?: 0 }

    fun partOne() = compareWithGap(1)
    fun partTwo() = compareWithGap(3)

    private fun compareWithGap(gap: Int) = input.drop(gap).filterIndexed { idx, a -> a > input[idx] }.count()
}