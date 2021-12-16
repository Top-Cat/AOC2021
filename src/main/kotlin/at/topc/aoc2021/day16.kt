package at.topc.aoc2021

import kotlin.math.pow
import kotlin.math.roundToInt

fun main() {
    Day16().also {
        printTime("Day 16") {
            println(it.partOne()) // 1007
            println(it.partTwo()) // 834151779165
        }
    }
}

// All the mutability of this solution is contained in this class that works its way through the input list to return bit segments
class PacketReader(private val input: MutableList<Int>) {
    var p = 0

    private fun ones(n: Int) = (2.0.pow(n.toDouble()) - 1).roundToInt()

    /**
     * Read n bits from the input buffer, if the bits are available in the head of the list just shift them over and return them
     * Otherwise we need to move down the list and add segments together
     */
    fun readBits(n: Int): Int {
        val bitsAvailable = 8 - p
        return if (n < bitsAvailable) {
            p += n
            (input[0] shr (bitsAvailable - n)) and ones(n)
        } else {
            ((input[0] and ones(bitsAvailable)) shl (n - bitsAvailable)).let {
                p = 0
                input.removeAt(0)

                it + readBits(n - bitsAvailable)
            }
        }
    }
}

abstract class Packet(protected val ver: Int, protected val type: Int) {
    abstract fun bits(): Int
    abstract fun value(): Long
    abstract fun sum(): Int

    companion object {
        const val HEADER_SIZE = 6
    }
}

class LiteralPacket(reader: PacketReader, ver: Int, type: Int) : Packet(ver, type) {
    private val value: Long
    private val bits: Int

    override fun bits() = bits + HEADER_SIZE
    override fun value() = value
    override fun sum() = ver

    init {
        val (_value, _bits) = deRecursive(reader)
        value = _value
        bits = _bits
    }

    private fun deRecursive(reader: PacketReader, total: Long = 0, n: Int = 0): Pair<Long, Int> =
        (reader.readBits(1) == 0).let { isEnd ->
            (total + reader.readBits(4)).let { value ->
                if (isEnd) {
                    value to n + 5
                } else {
                    deRecursive(reader, value shl 4, n + 5)
                }
            }
        }
}

class OperatorPacket(reader: PacketReader, ver: Int, type: Int) : Packet(ver, type) {
    private val subPackets: List<Packet>
    private val bits: Int

    override fun bits() = bits + HEADER_SIZE

    override fun sum() = ver + subPackets.sumOf { it.sum() }

    override fun value() = when (type) {
        0 -> subPackets.sumOf { it.value() }
        1 -> subPackets.fold(1L) { prev, elem -> prev * elem.value() }
        2 -> subPackets.minOf { it.value() }
        3 -> subPackets.maxOf { it.value() }
        5 -> if (subPackets[0].value() > subPackets[1].value()) 1 else 0
        6 -> if (subPackets[0].value() < subPackets[1].value()) 1 else 0
        7 -> if (subPackets[0].value() == subPackets[1].value()) 1 else 0
        else -> 0
    }

    init {
        val i = reader.readBits(1)
        subPackets = if (i == 0) {
            val maxLen = reader.readBits(15)
            deRecursive(reader, maxLen)
        } else {
            val n = reader.readBits(11)
            (1..n).map {
                parsePacket(reader)
            }
        }
        bits = subPackets.sumOf { it.bits() } + 1 + if (i == 0) 15 else 11
    }

    private fun deRecursive(reader: PacketReader, maxLen: Int): List<Packet> =
        if (maxLen > 0) {
            parsePacket(reader).let { packet ->
                listOf(packet) + deRecursive(reader, maxLen - packet.bits())
            }
        } else {
            listOf()
        }
}

fun parsePacket(reader: PacketReader) =
    reader.readBits(3).let { version ->
        when (val type = reader.readBits(3)) {
            4 -> LiteralPacket(reader, version, type)
            else -> OperatorPacket(reader, version, type)
        }
    }

class Day16 {
    private val input = readInput(16)

    private fun parsePacket(hex: String): Packet =
        hex.indices.step(2).map { x ->
            hex.slice(x..x+1).toInt(16)
        }
            .toMutableList()
            .let { input ->
                PacketReader(input)
            }
            .let { reader ->
                parsePacket(reader)
            }

    fun partOne() = parsePacket(input).sum()

    fun partTwo() = parsePacket(input).value()
}
