fun main() {
    fun part1(input: List<String>): Int {
        if (input.size <= 1) {
            return 0
        }
        var result = 0

        for (idx in 1 until input.size) {
            if (input[idx].toInt() > input[idx-1].toInt()) {
                result++
            }
        }
        return result

    }

    fun part2(input: List<String>): Int {
        val processedInput = input.map { it.toInt() }.windowed(3)

        if (processedInput.size <= 1) {
            return 0
        }

        var result = 0

        for (idx in 1 until processedInput.size) {
            if (processedInput[idx].sum() > processedInput[idx-1].sum()) {
                result++
            }
        }
        return result
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01_test")
    check(part1(testInput) == 7)
    check(part2(testInput) == 5)

    val input = readInput("Day01")
    println(part1(input))
    println(part2(input))
}
