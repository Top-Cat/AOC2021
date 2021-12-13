package at.topc.aoc2021

fun main() {
    // Only part outside timer is reading the input
    Day6().also {
        printTime("Day 6") {
            println(it.partOne()) // 379414
            println(it.partTwo()) // 1705008653296
        }
    }
}

data class StateInfo(val map: Map<Int, Long> = mapOf(5 to 1), val counts: List<Long> = listOf()) {
    fun next(map: Map<Int, Long>) = StateInfo(map, counts.plus(map.values.sum()))
}

class Day6 {
    private val input = readInput(6).split(",").map { it.toIntOrZero() }

    private val mappings by lazy {
        printTime("Mappings") {
            (1..260).fold(StateInfo()) { prev, _ ->
                prev.next(
                    prev.map
                        .mapKeys { it.key - 1 }
                        .plus(8 to (prev.map[0] ?: 0))
                        .plus(6 to (prev.map[0] ?: 0) + (prev.map[7] ?: 0))
                        .minus(-1)
                        .filterValues { it > 0 }
                )
            }.counts
        }
    }

    private val mappingsFast by lazy {
        printTime("Mappings") {
            (1..1000).fold(StateInfo(mapOf(6 to 1))) { prev, day ->
                prev.next(
                    prev.map.plus((7 + day) % 9 to (prev.map[(7 + day) % 9] ?: 0) + (prev.map[day % 9] ?: 0))
                )
            }.counts
        }
    }

    private fun getMappings(day: Int) = mappingsFast.drop(day - 1).take(5).reversed()

    fun partOne() = getMappings(80).let { m ->
        input.sumOf {
            m[it - 1]
        }
    }

    fun partTwo() = getMappings(256).let { m ->
        input.sumOf {
            m[it - 1]
        }
    }
}