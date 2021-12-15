package at.topc.aoc2021

fun main() {
    Day14().also {
        printTime("Day 14") {
            println(it.partOne()) // 2937
            println(it.partTwo()) // 3390034818249
        }
    }
}

class Day14 {
    private val input = readInput(14) {
        if (it.contains("->")) {
            it.split(" -> ").let { parts ->
                parts[0] to parts[1][0]
            }
        } else if (it.isNotBlank()) {
            it.toCharArray()
        } else {
            null
        }
    }
    private val template = input.first() as CharArray
    private val rules = input.mapNotNull { it as? Pair<String, Char> }.toMap()
    private val rulesPairs = rules.mapValues {
        listOf(
            it.key[0] + it.value.toString(),
            it.value + it.key[1].toString()
        )
    }
    private val pairs = (0 until template.size - 1).map {
        template[it] + template[it + 1].toString()
    }.groupBy { it }.mapValues { it.value.size.toLong() }

    /**
     * Fastest method, don't keep track of entire string, just number of pairs within it
     * Can handle 1_000_000 steps (which hits recursion limits on the method below)
     */
    private fun generatePolymerFast(n: Int) = (0 until n)
        .fold(pairs) { prev, _ ->
            prev
                .flatMap { o ->
                    (rulesPairs[o.key] ?: listOf()).map {
                        it to o.value
                    }
                }
                .groupBy { it.first }
                .mapValues { it.value.sumOf { i -> i.second } }
        }
        .map { it.key[0] to it.value }
        .groupBy { it.first }
        .mapValues {
            it.value.sumOf { o -> o.second } + if (it.key == template.last()) 1 else 0
        }
        .toList()                                                           // Convert to a list so we can sort it
        .sortedBy { it.second }                                             // Sort by frequency
        .let {
            it.last().second - it.first().second                            // Get difference between highest and lowest frequency (sort is ascending)
        }

    /**
     * Originally used to do part one, but too slow for part two but maybe easier to understand
     */
    private fun generatePolymer(n: Int) = (0 until n)
        .fold(template) { prev, _ ->
            (1 until prev.size*2).mapNotNull { idx ->                       // For each iteration loop through double the input length (the new length)
                if (idx % 2 == 0) {                                         // For even indexes insert a new character from the rules
                    rules[prev[idx / 2 - 1] + prev[idx / 2].toString()]
                } else {                                                    // For odd indexes keep the old character
                    prev[idx / 2]
                }
            }.toCharArray()
        }
        .groupBy { it }                                                     // Group string by character
        .mapValues { it.value.size }                                        // Count how many of each
        .toList()                                                           // Convert to a list so we can sort it
        .sortedBy { it.second }                                             // Sort by frequency
        .let {
            it.last().second - it.first().second                            // Get difference between highest and lowest frequency (sort is ascending)
        }

    private fun generatePolymerRec(n: Int) = generate(n)
        .toList()                                       // Convert to a list so we can sort it
        .sortedBy { it.second }                         // Sort by frequency
        .let {
            it.last().second - it.first().second        // Get difference between highest and lowest frequency (sort is ascending)
        }

    private fun generate(n: Int) =
        generateRecursive(n, template).also {
            // To prevent double counting generateRecursive never counts the last character
            // (it's normally also the first character of the next segment)
            // Here we just increment the count for the old character by one to fix that
            it.merge(template.last(), 1) { old, one -> old + one }
        }

    /**
     * The below method is still painfully slow without caching results
     * Here we store the total number of each character found when running the method n times on an input
     */
    private val memoised = mutableMapOf<Pair<Int, String>, MutableMap<Char, Long>>()

    private fun generateRecursive(n: Int, c: CharArray): MutableMap<Char, Long> =
        memoised.getOrPut(n to String(c)) {                         // Use cache if present instead of running code below, otherwise populate cache
            mutableMapOf<Char, Long>().also { mm ->                     // Initialise a map
                (1 until c.size*2 - 1).forEach { idx ->                 // Loop through all but the last character
                    if (n <= 1) {                                       // When on a leaf node count the characters that exist or would be generated by rules
                        when (idx % 2) {
                            0 -> mm.merge(rules[c[idx / 2 - 1] + c[idx / 2].toString()] ?: ' ', 1) { old, one -> old + one }
                            1 -> mm.merge(c[idx / 2], 1) { old, one -> old + one }
                        }
                    } else if (idx % 2 == 0) {                          // Otherwise, recursively call method and merge results
                        generateRecursive(n - 1, charArrayOf(c[idx / 2 - 1], rules[c[idx / 2 - 1] + c[idx / 2].toString()] ?: ' ', c[idx / 2]))
                            .forEach { (t, u) ->
                                mm.merge(t, u) { old, v -> (old + v) }
                            }
                    }
                }
            }
        }

    fun partOne() = generatePolymerFast(10)

    fun partTwo() = generatePolymerFast(40)
}
