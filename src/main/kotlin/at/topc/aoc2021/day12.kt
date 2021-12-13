package at.topc.aoc2021

fun main() {
    Day12().also {
        printTime("Day 12") {
            println(it.partOne()) // 3779
            println(it.partTwo()) // 96988
        }
    }
}

class Day12 {
    private val input = readInput(12) {
        it.split("-").let { parts ->
            parts[0] to parts[1]
        }
    }.fold(mapOf<String, List<String>>()) { prev, elem ->
        prev
            .plus(elem.first to (prev.getOrElse(elem.first) { listOf() }).plus(elem.second))
            .plus(elem.second to (prev.getOrElse(elem.second) { listOf() }).plus(elem.first))
    }

    private fun bfs(node: String = "start", visited: Set<String> = setOf(), canDoubleSmall: Boolean = false): List<List<String>> =
        (input[node] ?: setOf()).minus(visited).flatMap {
            if (it == "end") {
                listOf(listOf(node))
            } else if (node[0].isUpperCase()) {
                bfs(it, visited, canDoubleSmall)
            } else if (canDoubleSmall && node != "start") {
                bfs(it, visited.plus(node), canDoubleSmall).plus(
                    bfs(it, visited, false)
                )
            } else {
                bfs(it, visited.plus(node), canDoubleSmall)
            }
        }.distinct().map { l ->
            listOf(node).plus(l)
        }

    fun partOne() = bfs().size

    fun partTwo() = bfs(canDoubleSmall = true).size
}
