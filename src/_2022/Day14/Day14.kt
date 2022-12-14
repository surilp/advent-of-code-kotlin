package _2022.Day14

import readInput
import kotlin.math.max

data class Coordinate(val x: Int, val y: Int)

fun main() {
  fun getPathCoordinates(input: List<String>): List<Any> {
    val paths = mutableSetOf<Coordinate>()
    var maxDepth = 0
    for (row in input) {
      val corners =
        row.split(" -> ").map { it.split(",").map { it.toInt() } }.map { Coordinate(it[0], it[1]) }

      for (idx in 1 until corners.size) {
        val prev = corners[idx - 1]
        val current = corners[idx]
        if (prev.x == current.x) {
          val range = if (prev.y <= current.y) prev.y..current.y else prev.y downTo current.y
          for (y in range) {
            paths.add(Coordinate(prev.x, y))
            maxDepth = max(maxDepth, y)
          }
        } else {
          maxDepth = max(maxDepth, prev.y)
          val range = if (prev.x <= current.x) prev.x..current.x else prev.x downTo current.x
          for (x in range) {
            paths.add(Coordinate(x, prev.y))
          }
        }
      }
    }
    return listOf(paths, maxDepth)
  }

  fun freeFall(paths: MutableSet<Coordinate>, maxDepth: Int, entry: Coordinate): Coordinate? {
    var currentEntry = entry
    while (!paths.contains(currentEntry)) {
      if (currentEntry.y >= maxDepth) {
        return Coordinate(-1, -1)
      }
      currentEntry = Coordinate(currentEntry.x, currentEntry.y + 1)
    }
    if (paths.contains(Coordinate(currentEntry.x - 1, currentEntry.y)) && paths.contains(
        Coordinate(
          currentEntry.x + 1,
          currentEntry.y
        )
      )
    ) {
      paths.add(Coordinate(currentEntry.x, currentEntry.y - 1))
      return null
    } else if (!paths.contains(Coordinate(currentEntry.x - 1, currentEntry.y))) {
      return freeFall(paths, maxDepth, Coordinate(currentEntry.x - 1, currentEntry.y))
    } else if (!paths.contains(Coordinate(currentEntry.x + 1, currentEntry.y))) {
      return freeFall(paths, maxDepth, Coordinate(currentEntry.x + 1, currentEntry.y))
    }
    return null
  }

  fun freeFallV2(paths: MutableSet<Coordinate>, maxDepth: Int, entry: Coordinate): Coordinate? {
    var currentEntry = entry
    while (!paths.contains(currentEntry) || currentEntry.y == maxDepth) {
      if (currentEntry.y >= maxDepth) {
        paths.add(Coordinate(currentEntry.x, currentEntry.y - 1))
        return null
      }
      currentEntry = Coordinate(currentEntry.x, currentEntry.y + 1)
    }

    if (paths.contains(Coordinate(currentEntry.x - 1, currentEntry.y)) && paths.contains(
        Coordinate(
          currentEntry.x + 1,
          currentEntry.y
        )
      )
    ) {
      paths.add(Coordinate(currentEntry.x, currentEntry.y - 1))
      return null
    } else if (!paths.contains(Coordinate(currentEntry.x - 1, currentEntry.y))) {
      return freeFallV2(paths, maxDepth, Coordinate(currentEntry.x - 1, currentEntry.y))
    } else if (!paths.contains(Coordinate(currentEntry.x + 1, currentEntry.y))) {
      return freeFallV2(paths, maxDepth, Coordinate(currentEntry.x + 1, currentEntry.y))
    }
    return null
  }

  fun simulate(paths: MutableSet<Coordinate>, maxDepth: Int, entry: Coordinate): Int {
    var stop = false
    val currentCount = paths.size
    while (!stop) {
      val result = freeFall(paths, maxDepth, entry)
      if (result != null) {
        stop = true
      }
    }
    return paths.size - currentCount
  }

  fun part1(input: List<String>): Int {
    val pathInfo = getPathCoordinates(input)
    val paths = pathInfo[0] as MutableSet<Coordinate>
    val maxDepth = pathInfo[1] as Int
    return simulate(paths, maxDepth, Coordinate(500, 0))
  }

  fun part2(input: List<String>): Int {
    val pathInfo = getPathCoordinates(input)
    val paths = pathInfo[0] as MutableSet<Coordinate>
    val currentCount = paths.size
    val entry = Coordinate(500, 0)
    val maxDepth = pathInfo[1] as Int
    while (!paths.contains(entry)) {
      freeFallV2(paths, maxDepth + 2, entry)
    }
    return paths.size - currentCount
  }

  val testInput = readInput("_2022/Day14/Day14_test")
  check(part1(testInput) == 24)
  check(part2(testInput) == 93)

  val input = readInput("_2022/Day14/Day14")
  println(part1(input))
  println(part2(input))
}

