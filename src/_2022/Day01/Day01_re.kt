package _2022.Day01

import java.io.File

fun main() {
  fun parseInput(input: String) = input.split("\n\n").map { elf ->
    elf.lines().map { it.toInt() }
  }

  fun part1(input: String): Int {
    val data = parseInput(input)
    return data.maxOf { it.sum() }
  }

  fun part2(input: String): Int {
    val data = parseInput(input)
    return data
      .map { it.sum() }
      .sortedDescending()
      .take(3)
      .sum()
  }

  val testInput = File("src/_2022/Day01/Day01_test.txt").readText()
  check(part1(testInput) == 24000)
  check(part2(testInput) == 45000)

  val input = File("src/_2022/Day01/Day01.txt").readText()
  println(part1(input))
  println(part2(input))
}

