package _2022.Day01

import readInput

fun main() {
  fun part1(input: List<String>): Int {
    var result = 0
    val listOfCalories = input.joinToString(",").split(",,")
    for (calories in listOfCalories) {
      val total = calories.split(",").map { it.toInt() }.sum()
      if (total > result) {
        result = total
      }
    }
    return result
  }

  fun part2(input: List<String>): Int {
    var first = 0
    var second = 0
    var third = 0

    val listOfCalories = input.joinToString(",").split(",,")
    for (calories in listOfCalories) {
      val total = calories.split(",").map { it.toInt() }.sum()
      if (total > first) {
        third = second
        second = first
        first = total
      } else if (total > second) {
        third = second
        second = total
      } else if (total > third) {
        third = total
      }
    }
    return first + second + third
  }

  val testInput = readInput("_2022/Day01/Day01_test")
  check(part1(testInput) == 24000)
  check(part2(testInput) == 45000)


  val input = readInput("_2022/Day01/Day01")
  println(part1(input))
  println(part2(input))
}