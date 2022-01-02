package at.topc.aoc2021

import java.util.Stack
import java.util.concurrent.atomic.AtomicInteger
import kotlin.math.ceil

fun main() {
    Day18().also {
        printTime("Day 18") {
            println(it.partOne()) // 3551
            println(it.partTwo()) // 4555
        }
    }
}

data class MutablePair(
    var first: Any,
    var second: Any
) {
    private fun copyHalf(a: Any): Any = if (a is AtomicInteger) AtomicInteger(a.get()) else if (a is MutablePair) a.deepCopy() else AtomicInteger(0)
    fun deepCopy() = MutablePair(copyHalf(first), copyHalf(second))

    override fun toString() = "[${first},${second}]"
}

infix fun Int.mto(that: Int) = MutablePair(AtomicInteger(this), AtomicInteger(that))
infix fun MutablePair.mto(that: MutablePair) = MutablePair(this, that)

enum class Result {
    REMOVE, FOUND, NONE
}

class Day18 {
    private val input = readInput(18) {
        val stack = Stack<MutablePair>()
        var left = true

        (0 mto 0).also { head ->
            it.forEach { c ->
                when (c) {
                    '[' -> {
                        if (stack.empty()) {
                            stack.push(head)
                        } else {
                            val toPush = 0 mto 0
                            if (left) {
                                stack.peek().first = toPush
                            } else {
                                stack.peek().second = toPush
                            }
                            stack.push(toPush)
                        }
                        left = true
                    }
                    ',' -> left = false
                    ']' -> stack.pop()
                    else -> (if (left) {
                        stack.peek().first
                    } else {
                        stack.peek().second
                    } as AtomicInteger).let { ai ->
                        ai.set((ai.get() * 10) + c.digitToInt())
                    }
                }
            }
        }
    }

    private fun addNumbers(first: MutablePair, second: MutablePair):MutablePair {
        val start = (first mto second).deepCopy()
        processRec(start)

        return start
    }

    private fun leftmost(pair: MutablePair): AtomicInteger? = pair.first.let { f ->
        when (f) {
            is AtomicInteger -> f
            is MutablePair -> leftmost(f)
            else -> null
        }
    }

    private fun rightmost(pair: MutablePair): AtomicInteger? = pair.second.let { f ->
        when (f) {
            is AtomicInteger -> f
            is MutablePair -> rightmost(f)
            else -> null
        }
    }

    private fun magnitude(pair: MutablePair): Int =
        pair.first.let { a ->
            when (a) {
                is MutablePair -> magnitude(a)
                is AtomicInteger -> a.get()
                else -> 0
            } * 3
        } +
        pair.second.let { b ->
            when (b) {
                is MutablePair -> magnitude(b)
                is AtomicInteger -> b.get()
                else -> 0
            } * 2
        }

    private fun tryExplode(pair: MutablePair, left: AtomicInteger? = null, right: AtomicInteger? = null, level: Int = 0): Result {
        if (level >= 4 && pair.first is AtomicInteger && pair.second is AtomicInteger) {
            left?.addAndGet((pair.first as AtomicInteger).get())
            right?.addAndGet((pair.second as AtomicInteger).get())

            return Result.REMOVE
        }

        if (pair.first is MutablePair) {
            val res = tryExplode(
                pair.first as MutablePair,
                left,
                if (pair.second is AtomicInteger) pair.second as AtomicInteger else leftmost(pair.second as MutablePair),
                level + 1
            )

            if (res == Result.REMOVE) {
                pair.first = AtomicInteger(0)
            }

            if (res != Result.NONE) {
                return Result.FOUND
            }
        }

        if (pair.second is MutablePair) {
            val res = tryExplode(
                pair.second as MutablePair,
                if (pair.first is AtomicInteger) pair.first as AtomicInteger else rightmost(pair.first as MutablePair),
                right,
                level + 1
            )

            if (res == Result.REMOVE) {
                pair.second = AtomicInteger(0)
            }

            if (res != Result.NONE) {
                return Result.FOUND
            }
        }

        return Result.NONE
    }

    private fun trySplit(pair: MutablePair): Boolean {
        if (pair.first is AtomicInteger && (pair.first as AtomicInteger).get() >= 10) {
            val num = (pair.first as AtomicInteger).get()
            pair.first = (num / 2) mto ceil(num / 2.0).toInt()

            return true
        } else if (pair.first is MutablePair && trySplit(pair.first as MutablePair)) {
            return true
        } else if (pair.second is AtomicInteger && (pair.second as AtomicInteger).get() >= 10) {
            val num = (pair.second as AtomicInteger).get()
            pair.second = (num / 2) mto ceil(num / 2.0).toInt()

            return true
        } else if (pair.second is MutablePair && trySplit(pair.second as MutablePair)) {
            return true
        }

        return false
    }

    private fun processRec(pair: MutablePair) {
        while (tryExplode(pair) != Result.NONE || trySplit(pair)) {
            // Nothing
        }
    }

    fun partOne() =
        input.reduce { a, b ->
            addNumbers(a, b)
        }.let { magnitude(it) }

    fun partTwo() =
        input.maxOf { a ->
            input.minus(a).maxOf { b ->
                magnitude(addNumbers(a, b))
            }
        }
}
