package _2022.Day10

import readInput

val CYCLES = mapOf(
  "noop" to 1,
  "addx" to 2
)

fun main() {
  fun printScreen(screen: List<List<String>>) {
    screen.forEach { println(it.joinToString("")) }
  }

  fun part1(input: List<String>): Int {
    val screen = List(6) { MutableList(40) { "." } }
    var x = 1
    var cycle = 0

    var result = 0

    val stack: MutableList<Int> = mutableListOf<Int>(220, 180, 140, 100, 60, 20)
    for (instruction in input) {
      val split = instruction.split(" ")
      val action = split[0]
      val numToAdd = if (action == "addx") split[1].toInt() else 0


      repeat(CYCLES[action]!!) {
        val row = cycle / 40
        val col = cycle % 40

        val calibratedCycle = cycle % 40
        if (calibratedCycle in (x - 1)..(x + 1)) {
          screen[row][col] = "#"
        }

        if (stack.isNotEmpty() && cycle == stack.last() - 1) {
          val current = stack.removeLast()
          result += (x * current)
        }
        cycle += 1
      }

      x += numToAdd
    }
    printScreen(screen)
    return result
  }

  val testInput = readInput("_2022/Day10/Day10_test")
  check(part1(testInput) == 13140)

  val input = readInput("_2022/Day10/Day10")
  println(part1(input))
}

