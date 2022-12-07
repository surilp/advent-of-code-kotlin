package _2022.Day05

import readInput

fun main() {

  fun getCrateToIndexMap(dataDividerIdx: Int, input: List<String>): MutableMap<Int, Int> {
    val crateToIdx = mutableMapOf<Int, Int>()
    for (crate in input[dataDividerIdx - 1].withIndex()) {
      val crateToInt = crate.value.digitToIntOrNull()
      crateToInt?.let {
        crateToIdx[crateToInt] = crate.index
      }
    }
    return crateToIdx
  }

  fun getCrateStack(
    dataDividerIdx: Int,
    input: List<String>
  ): MutableMap<Int, ArrayDeque<Char>> {
    val crateToIdx = getCrateToIndexMap(dataDividerIdx, input)
    val crateStack = mutableMapOf<Int, ArrayDeque<Char>>()
    for (row in (dataDividerIdx - 2) downTo 0) {
      for (item in crateToIdx) {
        if (item.value >= input[row].length) {
          break
        }
        val crate = input[row][item.value]
        if (crate.toString().trim() != "") {
          if (item.key in crateStack) {
            crateStack[item.key]?.add(crate)
          } else {
            crateStack[item.key] = ArrayDeque()
            crateStack[item.key]?.add(crate)
          }
        }
      }
    }
    return crateStack
  }

  fun getMoves(input: List<String>, rowIdx: Int): List<Int> {
    val rowValue = input[rowIdx].split(" ")
    val numOfCrate = rowValue[1].toInt()
    val fromStack = rowValue[3].toInt()
    val toStack = rowValue[5].toInt()
    return listOf(numOfCrate, fromStack, toStack)
  }

  fun getFinalResult(crateStack: MutableMap<Int, ArrayDeque<Char>>): String {
    return crateStack.map {
      it.value.last()
    }.joinToString("")
  }

  fun part1(input: List<String>): String {
    val dataDividerIdx = input.indexOf("")
    val crateStack = getCrateStack(dataDividerIdx, input)

    for (rowIdx in (dataDividerIdx + 1) until input.size) {
      val (numOfCrate, fromStack, toStack) = getMoves(input, rowIdx)

      repeat(numOfCrate) {
        if (crateStack[fromStack]!!.isNotEmpty()) {
          val poppedCrate = crateStack[fromStack]?.removeLast()!!
          crateStack[toStack]?.add(poppedCrate)
        }
      }
    }
    return getFinalResult(crateStack)
  }

  fun part2(input: List<String>): String {
    val dataDividerIdx = input.indexOf("")
    val crateStack = getCrateStack(dataDividerIdx, input)

    for (rowIdx in (dataDividerIdx + 1) until input.size) {
      val (numOfCrate, fromStack, toStack) = getMoves(input, rowIdx)

      val tempQueue = ArrayDeque<Char>()
      repeat(numOfCrate) {
        if (crateStack[fromStack]!!.isNotEmpty()) {
          val poppedCrate = crateStack[fromStack]?.removeLast()!!
          tempQueue.addFirst(poppedCrate)
        }
      }
      tempQueue.forEach {
        crateStack[toStack]?.add(it)
      }
    }
    return getFinalResult(crateStack)
  }

  val testInput = readInput("_2022/Day05/Day05_test")
  check(part1(testInput) == "CMZ")
  check(part2(testInput) == "MCD")

  val input = readInput("_2022/Day05/Day05")
  println(part1(input))
  println(part2(input))
}