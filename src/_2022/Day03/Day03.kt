package _2022.Day03

import readInput

fun main() {

  fun calculatePriority(item: Char): Int {
    return if (item.isLowerCase()) {
      (item.code - 96)
    } else {
      (item.code - 64 + 26)
    }
  }

  fun part1(input: List<String>): Int {
    var result = 0

    for (rucksack in input) {
      val split = rucksack.chunked(rucksack.length / 2)
      val firstCompartment = split[0].toSet()
      val secondCompartment = split[1].toSet()
      val common = firstCompartment.intersect(secondCompartment)
      common.forEach {
        result += calculatePriority(it)
      }
    }

    return result
  }

  fun part2(input: List<String>): Int {
    var result = 0
    val groups = input.map { it.toSet() }.windowed(3, 3)
    groups.forEach { group ->
      val common = group[0].intersect(group[1]).intersect(group[2])
      common.forEach {
        result += calculatePriority(it)
      }
    }
    return result
  }

  val testInput = readInput("_2022/Day03/Day03_test")
  check(part1(testInput) == 157)
  check(part2(testInput) == 70)

  val input = readInput("_2022/Day03/Day03")
  println(part1(input))
  println(part2(input))
}

