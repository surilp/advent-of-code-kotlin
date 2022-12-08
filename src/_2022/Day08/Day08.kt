package _2022.Day08

import readInput
import kotlin.math.max

class Maximum(
  var left: Int = Int.MIN_VALUE,
  var right: Int = Int.MIN_VALUE,
  var up: Int = Int.MIN_VALUE,
  var down: Int = Int.MIN_VALUE
)

class Coordinate(val row: Int, val col: Int)

val DIRECTIONS = listOf(
  Coordinate(0, 1),
  Coordinate(0, -1),
  Coordinate(1, 0),
  Coordinate(-1, 0)
)

fun main() {

  fun createGrid(input: List<String>): List<List<Int>> {
    val grid = mutableListOf<List<Int>>()
    for (row in input) {
      grid.add(row.toList().map { it.digitToInt() })
    }
    return grid
  }

  fun initMaximumGrid(grid: List<List<Int>>): MutableList<List<Maximum>> {
    val maximumGrid = mutableListOf<List<Maximum>>()
    for (row in grid) {
      maximumGrid.add(List(row.size) { Maximum() })
    }
    return maximumGrid
  }

  fun fillMaximumGrid(grid: List<List<Int>>, maximumGrid: List<List<Maximum>>) {
    val colSize = grid[0].size
    val rowSize = grid.size

    for (row in 1 until rowSize) {
      for (col in 1 until colSize) {
        maximumGrid[row][col].left = max(maximumGrid[row][col - 1].left, grid[row][col - 1])
      }

      for (col in (colSize - 2) downTo 1) {
        maximumGrid[row][col].right = max(maximumGrid[row][col + 1].right, grid[row][col + 1])
      }
    }

    for (col in 1 until colSize) {
      for (row in 1 until rowSize) {
        maximumGrid[row][col].up = max(maximumGrid[row - 1][col].up, grid[row - 1][col])
      }

      for (row in (rowSize - 2) downTo 1) {
        maximumGrid[row][col].down = max(maximumGrid[row + 1][col].down, grid[row + 1][col])
      }
    }
  }

  fun getMaximumGrid(grid: List<List<Int>>): MutableList<List<Maximum>> {
    val maximumGrid = initMaximumGrid(grid)
    fillMaximumGrid(grid, maximumGrid)
    return maximumGrid
  }

  fun part1(input: List<String>): Int {
    val grid = createGrid(input)
    val maximumGrid = getMaximumGrid(grid)
    var result = (maximumGrid.size * 2) + (maximumGrid[0].size * 2) - 4
    for (row in 1 until grid.size - 1) {
      for (col in 1 until grid[0].size - 1) {
        if ((grid[row][col] > maximumGrid[row][col].left) ||
          (grid[row][col] > maximumGrid[row][col].right) ||
          (grid[row][col] > maximumGrid[row][col].up) ||
          (grid[row][col] > maximumGrid[row][col].down)
        ) {
          result += 1
        }
      }
    }
    return result
  }

  fun getDistance(
    grid: List<List<Int>>,
    curCoordinate: Coordinate,
    dirCoordinate: Coordinate,
    height: Int
  ): Int {
    var curRow = curCoordinate.row + dirCoordinate.row
    var curCol = curCoordinate.col + dirCoordinate.col
    var result = 0
    while ((curRow < grid.size && curCol < grid[0].size && curRow >= 0 && curCol >= 0)
    ) {
      result += 1
      if (grid[curRow][curCol] >= height) {
        break
      }
      curRow += dirCoordinate.row
      curCol += dirCoordinate.col
    }
    return result
  }

  fun part2(input: List<String>): Int {
    val grid = createGrid(input)
    val rowSize = grid.size
    val colSize = grid[0].size
    var result = Int.MIN_VALUE
    for (row in 1 until (rowSize - 1)) {
      for (col in 1 until (colSize - 1)) {
        var currentResult = 1
        DIRECTIONS.forEach {
          currentResult *= getDistance(
            grid,
            Coordinate(row, col),
            it,
            grid[row][col]
          )
        }
        result = max(result, currentResult)
      }
    }
    return result
  }

  val testInput = readInput("_2022/Day08/Day08_test")
  check(part1(testInput) == 8)
  check(part2(testInput) == 21)

  val input = readInput("_2022/Day08/Day08")
  println(part1(input))
  println(part2(input))
}