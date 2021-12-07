package at.topc.aoc2021

fun main() {
    Day2().also {
        println(it.partOne()) // 1648020
        println(it.partTwo()) // 1759818555
    }
}

enum class Direction(val part1: Position, val part2: Position) {
    FORWARD(
        Position(1, 0, 0),
        Position(1, 1, 0)
    ),
    DOWN(
        Position(0, 1, 0),
        Position(0, 0, 1)
    ),
    UP(
        Position(0, -1, 0),
        Position(0, 0, -1)
    );
}
data class Movement(val direction: Direction, val distance: Int)
data class Position(val horizontal: Int = 0, val depth: Int = 0, val aim: Int = 0) {
    operator fun times(other: Pair<Int, Int>) = other.let { (distance, otherAim) ->
        Position(horizontal * distance, depth * distance * otherAim, aim * distance)
    }

    operator fun plus(other: Position) = Position(horizontal + other.horizontal, depth + other.depth, aim + other.aim)
}

class Day2 {
    private val input = readInput(2) { str ->
        str.split(" ").let {
            Movement(Direction.valueOf(it[0].uppercase()), it[1].toIntOrNull() ?: 0)
        }
    }

    fun partOne() = moveSub(Position(aim = 1)) {
        it.direction.part1
    }

    fun partTwo() = moveSub(Position()) {
        it.direction.part2
    }

    private fun moveSub(initial: Position, block: (Movement) -> Position) = input.fold(initial) { prev, elem ->
        prev + (block(elem) * (elem.distance to prev.aim))
    }.let {
        it.horizontal * it.depth
    }
}