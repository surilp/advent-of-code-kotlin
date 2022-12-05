package _2021.Day02

import readInput

fun main() {

    fun part1(directions: List<String>): Int {
        var depth: Int = 0
        var horizontalPostion: Int = 0

        directions.forEach {
            val direction: List<String> = it.split(" ")
            if (direction[0] == "forward") {
                horizontalPostion += direction[1].toInt()
            } else if (direction[0] == "down") {
                depth += direction[1].toInt()
            } else if (direction[0] == "up") {
                depth -= direction[1].toInt()
            }
        }

        return depth * horizontalPostion
    }

    fun part2(directions: List<String>): Int {
        var depth: Int = 0
        var horizontalPostion: Int = 0
        var aim: Int = 0

        directions.forEach {
            val direction: List<String> = it.split(" ")
            if (direction[0] == "forward") {
                horizontalPostion += direction[1].toInt()
                depth += aim * direction[1].toInt()
            } else if (direction[0] == "down") {
                aim += direction[1].toInt()
            } else if (direction[0] == "up") {
                aim -= direction[1].toInt()
            }
        }

        return depth * horizontalPostion
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("_2021/Day02/Day02_test")
    check(part1(testInput) == 150)
    check(part2(testInput) == 900)

    val input = readInput("_2021/Day02/Day02")
    println(part1(input))
    println(part2(input))

}