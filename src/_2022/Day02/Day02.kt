package _2022.Day02

import readInput

open class Parent {
  open val point = 0
}

object Rock : Parent(), Comparable<Any> {

  override val point = 1
  override fun compareTo(other: Any): Int {
    if (other is Rock) {
      return 0
    }
    if (other is Scissors) {
      return 1
    }
    return -1
  }
}

object Paper : Parent(), Comparable<Any> {
  override val point = 2

  override fun compareTo(other: Any): Int {
    if (other is Paper) {
      return 0
    }
    if (other is Rock) {
      return 1
    }
    return -1
  }
}

object Scissors : Parent(), Comparable<Any> {
  override val point = 3

  override fun compareTo(other: Any): Int {
    if (other is Scissors) {
      return 0
    }
    if (other is Paper) {
      return 1
    }
    return -1
  }
}

val decryptHelperMap = hashMapOf(
  "A" to Rock,
  "B" to Paper,
  "C" to Scissors,
  "X" to Rock,
  "Y" to Paper,
  "Z" to Scissors
)

val whatToDoMap = hashMapOf(
  "X" to -1,
  "Y" to 0,
  "Z" to 1
)

fun main() {
  fun part1(input: List<String>): Int {
    var result = 0
    for (row in input) {
      val split = row.split(" ")
      val player1: Comparable<Any> = (decryptHelperMap[split[0]] as Comparable<Any>?)!!
      val player2: Comparable<Any> = (decryptHelperMap[split[1]] as Comparable<Any>?)!!
      val compareToValue = player2.compareTo(player1)

      if (compareToValue == 0) {
        result += 3
      } else if (compareToValue == 1) {
        result += 6
      }
      result += (player2 as Parent).point
    }
    return result
  }

  fun part2(input: List<String>): Int {
    var result = 0
    for (row in input) {
      val split = row.split(" ")
      val player1: Comparable<Any> = (decryptHelperMap[split[0]] as Comparable<Any>?)!!
      val whatToDo = whatToDoMap[split[1]]!!
      if (whatToDo == 0) {
        result += 3
      } else if (whatToDo == 1) {
        result += 6
      }
      for (player2 in listOf(Rock, Scissors, Paper)) {
        if (player2.compareTo(player1) == whatToDo) {
          result += player2.point
          break
        }
      }
    }
    return result
  }

  val testInput = readInput("_2022/Day02/Day02_test")
  check(part1(testInput) == 15)
  check(part2(testInput) == 12)

  val input = readInput("_2022/Day02/Day02")
  println(part1(input))
  println(part2(input))
}