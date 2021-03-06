package at.topc.aoc2021

import org.junit.Test
import kotlin.test.assertEquals

class AocTests {

    @Test
    fun day1() {
        assertEquals(1446, Day1().partOne())
        assertEquals(1486, Day1().partTwo())
    }

    @Test
    fun day2() {
        assertEquals(1648020, Day2().partOne())
        assertEquals(1759818555, Day2().partTwo())
    }

    @Test
    fun day3() {
        assertEquals(4191876, Day3().partOne())
        assertEquals(3414905, Day3().partTwo())
    }

    @Test
    fun day4() {
        assertEquals(33348, Day4().partOne())
        assertEquals(8112, Day4().partTwo())
    }

    @Test
    fun day5() {
        assertEquals(7414, Day5().partOne())
        assertEquals(19676, Day5().partTwo())
    }

    @Test
    fun day6() {
        assertEquals(379414, Day6().partOne())
        assertEquals(1705008653296, Day6().partTwo())
    }

    @Test
    fun day7() {
        assertEquals(344735, Day7().partOne())
        assertEquals(96798233, Day7().partTwo())
    }

    @Test
    fun day8() {
        assertEquals(264, Day8().partOne())
        assertEquals(1063760, Day8().partTwo())
    }

    @Test
    fun day9() {
        assertEquals(575, Day9().partOne())
        assertEquals(1019700, Day9().partTwo())
    }

    @Test
    fun day10() {
        assertEquals(415953, Day10().partOne())
        assertEquals(2292863731, Day10().partTwo())
    }

    @Test
    fun day11() {
        assertEquals(1585, Day11().partOne())
        assertEquals(382, Day11().partTwo())
    }

    @Test
    fun day12() {
        assertEquals(3779, Day12().partOne())
        assertEquals(96988, Day12().partTwo())
    }

    @Test
    fun day13() {
        assertEquals(759, Day13().partOne())
        assertEquals("""
            ⬛ ⬜ ⬜ ⬛ ⬜ ⬛ ⬛ ⬛ ⬛ ⬜ ⬜ ⬛ ⬛ ⬜ ⬜ ⬛ ⬛ ⬛ ⬜ ⬜ ⬛ ⬛ ⬛ ⬛ ⬜ ⬛ ⬜ ⬜ ⬛ ⬜ ⬛ ⬛ ⬛ ⬜ ⬜ ⬛ ⬛ ⬛ ⬜
            ⬛ ⬜ ⬜ ⬛ ⬜ ⬛ ⬜ ⬜ ⬜ ⬜ ⬛ ⬜ ⬜ ⬛ ⬜ ⬛ ⬜ ⬜ ⬛ ⬜ ⬜ ⬜ ⬜ ⬛ ⬜ ⬛ ⬜ ⬛ ⬜ ⬜ ⬛ ⬜ ⬜ ⬛ ⬜ ⬛ ⬜ ⬜ ⬛
            ⬛ ⬛ ⬛ ⬛ ⬜ ⬛ ⬛ ⬛ ⬜ ⬜ ⬛ ⬜ ⬜ ⬜ ⬜ ⬛ ⬜ ⬜ ⬛ ⬜ ⬜ ⬜ ⬛ ⬜ ⬜ ⬛ ⬛ ⬜ ⬜ ⬜ ⬛ ⬜ ⬜ ⬛ ⬜ ⬛ ⬜ ⬜ ⬛
            ⬛ ⬜ ⬜ ⬛ ⬜ ⬛ ⬜ ⬜ ⬜ ⬜ ⬛ ⬜ ⬜ ⬜ ⬜ ⬛ ⬛ ⬛ ⬜ ⬜ ⬜ ⬛ ⬜ ⬜ ⬜ ⬛ ⬜ ⬛ ⬜ ⬜ ⬛ ⬛ ⬛ ⬜ ⬜ ⬛ ⬛ ⬛ ⬜
            ⬛ ⬜ ⬜ ⬛ ⬜ ⬛ ⬜ ⬜ ⬜ ⬜ ⬛ ⬜ ⬜ ⬛ ⬜ ⬛ ⬜ ⬛ ⬜ ⬜ ⬛ ⬜ ⬜ ⬜ ⬜ ⬛ ⬜ ⬛ ⬜ ⬜ ⬛ ⬜ ⬜ ⬜ ⬜ ⬛ ⬜ ⬛ ⬜
            ⬛ ⬜ ⬜ ⬛ ⬜ ⬛ ⬛ ⬛ ⬛ ⬜ ⬜ ⬛ ⬛ ⬜ ⬜ ⬛ ⬜ ⬜ ⬛ ⬜ ⬛ ⬛ ⬛ ⬛ ⬜ ⬛ ⬜ ⬜ ⬛ ⬜ ⬛ ⬜ ⬜ ⬜ ⬜ ⬛ ⬜ ⬜ ⬛
        """.trimIndent(), Day13().partTwo())
    }

    @Test
    fun day14() {
        assertEquals(2937, Day14().partOne())
        assertEquals(3390034818249, Day14().partTwo())
    }

    @Test
    fun day15() {
        assertEquals(698, Day15().partOne())
        assertEquals(3022, Day15().partTwo())
    }

    @Test
    fun day16() {
        assertEquals(1007, Day16().partOne())
        assertEquals(834151779165, Day16().partTwo())
    }

    @Test
    fun day17() {
        assertEquals(10011, Day17().partOne())
        assertEquals(2994, Day17().partTwo())
    }

    @Test
    fun day18() {
        assertEquals(3551, Day18().partOne())
        assertEquals(4555, Day18().partTwo())
    }

    @Test
    fun day20() {
        assertEquals(5081, Day20().partOne())
        assertEquals(15088, Day20().partTwo())
    }
}