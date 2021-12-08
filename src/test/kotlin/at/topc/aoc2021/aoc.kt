package at.topc.aoc2021

import org.junit.Test
import kotlin.test.assertEquals
import kotlin.time.ExperimentalTime

@ExperimentalTime
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

}