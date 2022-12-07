package _2022.Day04

import readInput

fun main() {

  fun getPairs(data:String): List<Int> {
    val split = data.split(",")
    val (pair1Low, pair1High) = split[0].split("-").map { it.toInt() }
    val (pair2Low, pair2High) = split[1].split("-").map { it.toInt() }
    return listOf(pair1Low, pair1High, pair2Low, pair2High)
  }

  fun part1(input: List<String>): Int {
    var result = 0
    for (sectionRange in input) {
      val (pair1Low, pair1High, pair2Low, pair2High) = getPairs(sectionRange)
      if ((pair1Low >= pair2Low && pair1High <= pair2High) ||
        (pair2Low >= pair1Low && pair2High <= pair1High)) {
        result += 1
      }
    }
    return result
  }

  fun part2(input: List<String>): Int {
    var result = 0
    for (sectionRange in input) {
      val (pair1Low, pair1High, pair2Low, pair2High) = getPairs(sectionRange)
      if (pair1Low in pair2Low..pair2High || pair2Low in pair1Low .. pair1High) {
        result += 1
      }
    }
    return result
  }

  val testInput = readInput("_2022/Day04/Day04_test")
  check(part1(testInput) == 2)
  check(part2(testInput) == 4)

  val input = readInput("_2022/Day04/Day04")
  println(part1(input))
  println(part2(input))
}