package _2022.Day12

import readInput
import java.util.PriorityQueue

data class Location(val row: Int, val col: Int)

internal class PairComparator : Comparator<Pair<Int, Location>> {
  override fun compare(o1: Pair<Int, Location>, o2: Pair<Int, Location>): Int {
    return if (o1.first == o2.first) {
      0
    } else if (o1.first > o2.first) {
      1
    } else {
      -1
    }
  }
}

val DIRECTIONS = listOf(
  Location(0, 1),
  Location(0, -1),
  Location(1, 0),
  Location(-1, 0)
)

fun main() {

  fun createGrid(input: List<String>): MutableList<MutableList<String>> {
    val grid = mutableListOf<MutableList<String>>()
    input.forEach { row ->
      grid.add(row.split("").filter { it != "" }.toMutableList())
    }
    return grid
  }

  fun findStartEnd(grid: List<MutableList<String>>): MutableMap<String, Location> {
    val result = mutableMapOf<String, Location>()
    grid.forEachIndexed { row, strings ->
      strings.forEachIndexed { col, s ->
        if (s == "S") {
          result[s] = Location(row, col)
          grid[row][col] = "a"
        } else if (s == "E") {
          result[s] = Location(row, col)
          grid[row][col] = "z"
        }
      }
    }
    return result
  }

  fun getDistanceMap(rowSize: Int, colSize: Int): MutableMap<Location, Int> {
    val distanceMap = mutableMapOf<Location, Int>()
    for (row in 0 until rowSize) {
      for (col in 0 until colSize) {
        distanceMap[Location(row, col)] = Int.MAX_VALUE
      }
    }
    return distanceMap
  }

  fun getAdjMap(): MutableMap<String, String> {
    val adjMap = mutableMapOf<String, String>()
    adjMap["S"] = "b"
    for (let in 'a'..'z') {
      adjMap[let.toString()] = (let + 1).toString()
    }
    adjMap["z"] = "E"
    return adjMap
  }

  fun printVisited(distanceMap: MutableMap<Location, Int>, grid: List<List<String>>) {
    val result = mutableListOf<MutableList<String>>()
    for (row in grid) {
      result.add(List(row.size) { "." }.toMutableList())
    }
    distanceMap.forEach { location, distance ->
      if (distance < Int.MAX_VALUE) {
        result[location.row][location.col] = grid[location.row][location.col]
      }
    }
    for (row in result.withIndex()) {
      println("${row.index} ${row.value.joinToString("")}")
    }
  }

  fun common(
    grid: MutableList<MutableList<String>>,
    startEndMap: MutableMap<String, Location>
  ): MutableMap<Location, Int> {
    val priorityQueue = PriorityQueue<Pair<Int, Location>>(PairComparator())
    val adjMap = getAdjMap()

    val rowSize = grid.size
    val colSize = grid[0].size
    val distanceMap = getDistanceMap(rowSize, colSize)

    priorityQueue.add(Pair(0, startEndMap["S"]!!))

    while (priorityQueue.isNotEmpty()) {
      val current = priorityQueue.remove()

      val distance = current.first
      val currentRow = current.second.row
      val currentCol = current.second.col

      val target = adjMap[grid[currentRow][currentCol]]

      target?.let {
        for (direction in DIRECTIONS) {
          val newRow = currentRow + direction.row
          val newCol = currentCol + direction.col

          if (newRow in 0 until rowSize &&
            newCol in 0 until colSize &&
            (grid[newRow][newCol] == target ||
              grid[newRow][newCol] <= grid[currentRow][currentCol])
          ) {
            val newDistance = distance + 1
            val newLocation = Location(newRow, newCol)
            if (newDistance < distanceMap[newLocation]!!) {
              distanceMap[newLocation] = newDistance
              priorityQueue.add(Pair(newDistance, newLocation))
            }
          }
        }
      }
    }
    // printVisited(distanceMap, grid)
    return distanceMap
  }

  fun part1(input: List<String>): Int {
    val grid = createGrid(input)
    val startEndMap = findStartEnd(grid)
    val distanceMap = common(grid, startEndMap)
    return distanceMap[startEndMap["E"]!!]!!
  }

  fun part2(input: List<String>): Int {
    val grid = createGrid(input)
    val startEndMap = findStartEnd(grid)

    var result = Int.MAX_VALUE

    grid.forEachIndexed { row, strings ->
      strings.forEachIndexed { col, s ->
        if (s == "a") {

          grid[row][col] = "S"
          startEndMap["S"] = Location(row, col)
          val distanceMap = common(grid, startEndMap)
          if (distanceMap[startEndMap["E"]!!]!! < result) {
            result = distanceMap[startEndMap["E"]!!]!!
          }
        }
      }
    }

    return result
  }

  val testInput = readInput("_2022/Day12/Day12_test")
  check(part1(testInput) == 31)
  check(part2(testInput) == 29)

  val input = readInput("_2022/Day12/Day12")
  println(part1(input))
  println(part2(input))
}