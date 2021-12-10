package Day10

import readInput

class SyntaxScoring(val input_file: String) {
    val PARENTHESES_MAP = mapOf(
        '(' to ')',
        '[' to ']',
        '{' to '}',
        '<' to '>'
    )

    val PART1_MAP = mapOf(
        ')' to 3,
        ']' to 57,
        '}' to 1197,
        '>' to 25137
    )

    val PART2_MAP = mapOf(
        '(' to 1,
        '[' to 2,
        '{' to 3,
        '<' to 4
    )

    val data: List<String>

    init {
        data = readInput(input_file)
    }

    fun getPart1Result(): Int {
        var result: Int = 0
        for (item in data) {
            val stack = ArrayDeque<Char>()
            for (paren in item) {
                if (paren in PARENTHESES_MAP.keys) {
                    stack.addLast(paren)
                } else {
                    if (!stack.isEmpty() && (PARENTHESES_MAP[stack.last()] == paren)) {
                        stack.removeLast()
                    } else {
                        result += PART1_MAP[paren]!!
                        break
                    }
                }
            }
        }
        return result
    }

    fun getPart2Result(): Long {
        val result: ArrayList<Long> = ArrayList()
        itemloop@ for (item in data) {
            val stack = ArrayDeque<Char>()
            for (paren in item) {
                if (paren in PARENTHESES_MAP.keys) {
                    stack.addLast(paren)
                } else {
                    if (!stack.isEmpty() && (PARENTHESES_MAP[stack.last()] == paren)) {
                        stack.removeLast()
                    } else {
                        continue@itemloop
                    }
                }
            }
            var currentResult: Long = 0
            while (!stack.isEmpty()) {
                val item = stack.removeLast()
                currentResult = (currentResult * 5) + PART2_MAP[item]!!
            }
            result.add(currentResult)
        }
        result.sort()
        return result[result.size / 2]
    }

}

fun main() {
    val syntaxScoring = SyntaxScoring("Day10/Day10_test")
    check(syntaxScoring.getPart1Result() == 26397)
    check(syntaxScoring.getPart2Result() == 288957L)

    val syntaxScoringMain = SyntaxScoring("Day10/Day10")
    println("part 1 - ${syntaxScoringMain.getPart1Result()}")
    println("part 2 - ${syntaxScoringMain.getPart2Result()}")

}