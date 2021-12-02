import day1.Main

fun readInput(day: Int) = Main::class.java.getResource("/$day.txt")?.readText() ?: ""
fun <T> readInput(day: Int, block: (String) -> T) = readInput(day).split("\n").map(block)

operator fun Pair<Int, Int>.plus(other: Pair<Int, Int>) = first + other.first to second + other.second