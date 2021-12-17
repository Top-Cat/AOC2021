package at.topc.aoc2021

import kotlin.math.ceil
import kotlin.math.sqrt

fun main() {
    Day17().also {
        printTime("Day 17") {
            println(it.partOne()) // 10011
            println(it.partTwo()) // 2994
        }
    }
}

data class TargetRegion(val x1: Int, val x2: Int, val y1: Int, val y2: Int) {
    fun contains(x: Int, y: Int) = x in x1..x2 && y in y1..y2
}

class Day17 {
    private val regex = Regex("target area: x=(-?[0-9]+)\\.\\.(-?[0-9]+), y=(-?[0-9]+)\\.\\.(-?[0-9]+)")
    private val input = parseRegion(readInput(17))

    private fun parseRegion(str: String) =
        regex.matchEntire(str)?.let { match ->
            match.groupValues.drop(1).map { n ->
                n.toIntOrZero()
            }.let { arr ->
                TargetRegion(arr[0], arr[1], arr[2], arr[3])
            }
        } ?: TargetRegion(0, 0, 0, 0)

    private fun intersectsRegion(region: TargetRegion, dx: Int, dy: Int): Pair<Boolean, List<Pair<Int, Int>>> {
        var px = 0
        var py = 0
        var ddX = dx
        var ddY = dy
        val visited = mutableListOf<Pair<Int, Int>>()

        // While we're moving in X and left of the target region
        // OR
        // We're above the target region
        while ((ddX != 0 && px < region.x2) || py > region.y1) {
            px += ddX
            py += ddY
            ddX -= if (ddX > 0) 1 else if (ddX == 0) 0 else -1
            ddY -= 1

            visited.add(px to py)

            if (region.contains(px, py)) {
                return true to visited
            }
        }
        return false to visited
    }

    private fun triangle(n: Int): Int = (n * (n + 1)) / 2

    /**
     * Due to the rule where we decrease the y speed by one every tick the Y positions of the probe are the same on the way up as the way down
     * and we end up at Y=0 again. The maximum speed we can have at this point is equal to -(Y1 + 1) otherwise we will end up outside of the target region.
     * Now we know we can just calculate our maximum speed from our initial conditions we need to translate it into a maximum height.
     * Thankfully the sequence n + (n-1) + (n-2)... has already been seen this year. It's the triangle numbers again. Due to the magic of signs cancelling out
     * we can just feed y1 in and get y1 * (y1 + 1) = -(y1 + 1) * (-(y1 + 1) + 1) -> -(y1 + 1) * -y1 -> y1 * (y1 + 1)
     */
    fun partOne() = triangle(input.y1)

    private fun invTriangle(n: Int): Double = (sqrt((8 * n) + 1.0) - 1) / 2

    fun partTwo() = (ceil(invTriangle(input.x1)).toInt()..input.x2).flatMap { x ->
        (input.y1..-(input.y1 + 1)).map { y ->
            x to y
        }
    }.filter {
        intersectsRegion(input, it.first, it.second).first
    }.size
}
