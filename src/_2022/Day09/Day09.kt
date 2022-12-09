package _2022.Day09

import readInput
import kotlin.math.abs

data class Coordinate(var x: Int, var y: Int)

fun main() {
  val DIRECTIONS = mapOf(
    "L" to Coordinate(-1, 0),
    "R" to Coordinate(1, 0),
    "U" to Coordinate(0, 1),
    "D" to Coordinate(0, -1)
  )

  fun isTouching(head: Coordinate, tail: Coordinate): Boolean {
    return abs(head.x - tail.x) <= 1 && abs(head.y - tail.y) <= 1
  }

  fun part1(input: List<String>): Int {
    val head = Coordinate(0, 0)
    val tail = Coordinate(0, 0)
    val setOfTailVisitedCoordinates = mutableSetOf<Coordinate>()
    setOfTailVisitedCoordinates.add(Coordinate(0, 0))

    for (move in input) {
      val split = move.split(" ")
      val direction = DIRECTIONS[split[0]]!!
      val steps = split[1].toInt()

      repeat(steps) {
        head.x += direction.x
        head.y += direction.y

        if (!isTouching(head, tail)) {
          val tailMoveX = if (head.x - tail.x == 0) 0 else (head.x - tail.x) / abs(head.x - tail.x)
          val tailMoveY = if (head.y - tail.y == 0) 0 else (head.y - tail.y) / abs(head.y - tail.y)

          tail.x += tailMoveX
          tail.y += tailMoveY
          setOfTailVisitedCoordinates.add(tail.copy())
        }
      }
    }
    return setOfTailVisitedCoordinates.size
  }

  fun part2(input: List<String>): Int {
    val head = Coordinate(0, 0)
    val followers = List(9) { Coordinate(0, 0) }
    val setOfTailVisitedCoordinates = mutableSetOf<Coordinate>()
    setOfTailVisitedCoordinates.add(Coordinate(0, 0))

    for (move in input) {
      val split = move.split(" ")
      val direction = DIRECTIONS[split[0]]!!
      val steps = split[1].toInt()

      repeat(steps) {
        head.x += direction.x
        head.y += direction.y

        var current = head
        var beforeCurrent = followers[0]
        var i = 1
        while (i <= followers.size && !isTouching(current, beforeCurrent)) {
          val tailMoveX =
            if (current.x - beforeCurrent.x == 0) 0 else (current.x - beforeCurrent.x) / abs(current.x - beforeCurrent.x)
          val tailMoveY =
            if (current.y - beforeCurrent.y == 0) 0 else (current.y - beforeCurrent.y) / abs(current.y - beforeCurrent.y)

          beforeCurrent.x += tailMoveX
          beforeCurrent.y += tailMoveY

          if (i == followers.size) {
            setOfTailVisitedCoordinates.add(beforeCurrent.copy())
            break
          }

          current = beforeCurrent
          beforeCurrent = followers[i]
          i += 1
        }
      }
    }
    return setOfTailVisitedCoordinates.size
  }

  val testInput = readInput("_2022/Day09/Day09_test")
  check(part1(testInput) == 13)
  check(part2(testInput) == 1)

  val testInput2 = readInput("_2022/Day09/Day09_test2")
  check(part2(testInput2) == 36)

  val input = readInput("_2022/Day09/Day09")
  println(part1(input))
  println(part2(input))
}