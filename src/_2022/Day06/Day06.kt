package _2022.Day06

import readInput

fun main() {

  fun findFirstMarkerAfter(buffer: String, numOfUniqueChar: Int): Int {
    val helperMap = mutableMapOf<Char, Int>()
    var start = 0
    var end = 0
    val size = buffer.length

    while (end < size) {
      val curChar = buffer[end]
      if (curChar in helperMap && helperMap[curChar]!! in start..end) {
        start = helperMap[curChar]!! + 1
      }
      helperMap[curChar] = end
      if (end - start + 1 == numOfUniqueChar) {
        return end + 1
      }
      end += 1
    }
    return -1
  }

  fun part1(input: List<String>): List<Int> {
    val result = mutableListOf<Int>()
    for (buffer in input) {
      result.add(findFirstMarkerAfter(buffer, 4))
    }
    return result
  }

  fun part2(input: List<String>): List<Int> {
    val result = mutableListOf<Int>()
    for (buffer in input) {
      result.add(findFirstMarkerAfter(buffer, 14))
    }
    return result
  }

  val testInput = readInput("_2022/Day06/Day06_test")
  check(part1(testInput) == listOf(7, 5, 6, 10, 11))
  check(part2(testInput) == listOf(19, 23, 23, 29, 26))

  val input = readInput("_2022/Day06/Day06")
  println(part1(input))
  println(part2(input))
}