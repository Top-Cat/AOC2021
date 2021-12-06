package day6

import readInput
import toIntOrZero

fun main() {
    Main().also {
        println(it.partOne()) // 379414
        println(it.partTwo()) // 1705008653296
    }
}

data class StateInfo(val map: Map<Int, Long> = mapOf(5 to 1), val counts: List<Long> = listOf()) {
    fun next(map: Map<Int, Long>) = StateInfo(map, counts.plus(map.values.sum()))
}

class Main {
    private val input = readInput(6).split(",").map { it.toIntOrZero() }

    private val mappings by lazy {
        (1..260).fold(StateInfo()) { prev, _ ->
            prev.next(
                prev.map
                    .plus(9 to prev.map.getOrDefault(0, 0))
                    .filterValues { it > 0 }
                    .map {
                        (if (it.key == 0) 6 else it.key - 1) to it.value
                    }
                    .groupBy { it.first }
                    .mapValues { it.value.sumOf { s -> s.second } }
                    .toMap()
            )
        }.counts
    }

    private fun getMappings(day: Int) = mappings.drop(day - 1).take(5).reversed()

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