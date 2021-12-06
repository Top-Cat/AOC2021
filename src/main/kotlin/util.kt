import day1.Main
import java.io.File

fun getFile(day: Int) = File(Main::class.java.getResource("/$day.txt")!!.toURI())
fun readInput(day: Int) = getFile(day).readText()
fun <T> readInput(day: Int, block: (String) -> T) = getFile(day).readLines().map(block)
fun String.toIntOrZero() = this.toIntOrNull() ?: 0

operator fun Pair<Int, Int>.plus(other: Pair<Int, Int>) = first + other.first to second + other.second
