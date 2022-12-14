package _2022.Day13

import readInput

fun main() {
  fun getInnerListStr(packet: String, start: Int, end: Int): List<Any> {
    var start = start
    var end = end
    var startChar = packet[start]
    val newStrList = mutableListOf<Char>()
    newStrList.add(startChar)
    start += 1
    val stack = ArrayDeque<Char>()
    stack.addLast(startChar)
    while (start <= end && stack.isNotEmpty()) {
      startChar = packet[start]
      newStrList.add(startChar)
      if (startChar == '[') {
        stack.addLast(startChar)
      } else if (startChar == ']') {
        stack.removeLast()
      }
      start += 1
    }
    return listOf(newStrList.joinToString(""), start + 1)
  }

  fun strToList(packet: String, start: Int, end: Int): MutableList<Any> {
    var start = start
    var end = end

    val result = mutableListOf<Any>()
    start += 1
    end -= 1

    while (start <= end) {
      if (packet[start].digitToIntOrNull() != null) {
        start += if (packet[start].digitToIntOrNull() == 1 && (start + 1) <= end && packet[start + 1] == '0') {
          result.add(10)
          3
        } else {
          result.add(packet[start].digitToIntOrNull()!!)
          2
        }
      } else {
        val innerStrList = getInnerListStr(packet, start, end)
        start = innerStrList[1] as Int
        val newStr = innerStrList[0] as String
        val newStrResult = strToList(newStr, 0, newStr.length - 1)
        result.add(newStrResult)
      }
    }
    return result
  }

  fun getPairs(input: List<String>): MutableList<List<Any>> {
    val pairs = mutableListOf<List<Any>>()
    var i = 0
    while (i < input.size) {
      pairs.add(
        listOf(
          strToList(input[i], 0, input[i].length - 1),
          strToList(input[i + 1], 0, input[i + 1].length - 1)
        )
      )
      i += 3
    }
    return pairs
  }

  fun isPairInRightOrder(pair: List<Any>): Int {
    var left = pair[0]
    var right = pair[1]

    if (left is Int && right is List<*>) {
      left = listOf(left)
    }
    if (right is Int && left is List<*>) {
      right = listOf(right)
    }
    if (right is Int && left is Int) {
      if (left < right) {
        return 1
      } else if (left == right) {
        return 0
      } else {
        return -1
      }
    }
    if (left is List<*> && right is List<*>) {
      var idx = 0
      while (idx < left.size && idx < right.size) {
        val result = isPairInRightOrder(listOf(left[idx], right[idx]) as List<Any>)
        if (result == 1) {
          return 1
        } else if (result == -1) {
          return -1
        }
        idx += 1
      }

      if (idx >= left.size) {
        if (left.size == right.size) {
          return 0
        }
        return 1
      }
    }
    return -1
  }

  fun part1(input: List<String>): Int {
    var result = 0
    val pairs = getPairs(input)

    for (pair in pairs.withIndex()) {
      if (isPairInRightOrder(pair.value) == 1) {
        // println(pair.index + 1)
        // println(pair.value[0])
        // println(pair.value[1])
        result += (pair.index + 1)
      }
    }
    return result
  }

  fun part2(input: List<String>): Int {
    var list = mutableListOf<List<Any>>()
    list.add(strToList("[[2]]", 0, 4))
    list.add(strToList("[[6]]", 0, 4))
    input.forEach {
      if (it != "") {
        list.add(strToList(it, 0, it.length - 1))
      }
    }
    list.sortWith(Comparator { o1, o2 -> -isPairInRightOrder(listOf(o1, o2)) })

    var result = 1

    list.forEachIndexed { index, list ->
      if (listOf(listOf(2)) == list) {
        result *= (index + 1)
      }
      if (listOf(listOf(6)) == list) {
        result *= (index + 1)
      }
    }
    return result
  }

  val testInput = readInput("_2022/Day13/Day13_test")
  check(part1(testInput) == 13)
  check(part2(testInput) == 140)

  val input = readInput("_2022/Day13/Day13")
  println(part1(input))
  println(part2(input))
}


