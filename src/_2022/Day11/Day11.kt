package _2022.Day11

import readInput

class Monkey(
  private var items: MutableList<Long>,
  operationString: String,
  testString: String,
  testTrueString: String,
  testFalseString: String
) {

  private val operation: List<String> = operationString.split(" ")
  val divisibleBy: Int = testString.split(" ").last().toInt()
  private val trueTransfer: Int = testTrueString.split(" ").last().toInt()
  private val falseTransfer: Int = testFalseString.split(" ").last().toInt()

  var numberOfItemsInspected: Int = 0

  companion object {
    val monkeyMap = mutableMapOf<Int, Monkey>()
  }

  private fun addItem(item: Long) {
    items.add(item)
  }

  fun calculate(value: Long, mod: Int?, worryLevelDivide: Int?): Long {
    val operationSign = operation[1]
    val operand2 = operation[2].toLongOrNull() ?: value

    val ans: Long = if (operationSign == "+") {
      (value + operand2)
    } else {
      (value * operand2)
    }
    return if (worryLevelDivide != null) {
      ans / worryLevelDivide
    } else {
      ans % mod!!
    }
  }

  fun process(mod: Int? = null, worryLevelDivide: Int? = null) {
    items.forEach {
      numberOfItemsInspected += 1

      val ans = calculate(it, mod, worryLevelDivide)

      if ((ans % divisibleBy) == 0L) {
        monkeyMap[trueTransfer]!!.addItem(ans)
      } else {
        monkeyMap[falseTransfer]!!.addItem(ans)
      }

    }
    items = mutableListOf()
  }
}

fun main() {

  fun initialize(input: List<String>): MutableMap<Int, Monkey> {
    var i = 0
    while (i < input.size) {
      val monkeyNum = input[i].split(":").first().split(" ").last().toInt()
      val monkeyItems =
        input[i + 1].split(": ").last().split(", ").map { it.toLong() }.toMutableList()
      val operation = input[i + 2].split("= ").last()
      val test = input[i + 3].split(": ").last()
      val trueTest = input[i + 4].split(": ").last()
      val falseTest = input[i + 5].split(": ").last()

      val monkey = Monkey(monkeyItems, operation, test, trueTest, falseTest)
      Monkey.monkeyMap[monkeyNum] = monkey

      i += 7
    }

    return Monkey.monkeyMap
  }

  fun part1(input: List<String>): Int {
    val monkeyMap = initialize(input)

    repeat(20) {
      monkeyMap.keys.sorted().forEach {
        monkeyMap[it]?.process(worryLevelDivide = 3)
      }
    }
    val numOfInspectionByMonkey = monkeyMap.values.map {
      it.numberOfItemsInspected
    }.sorted()

    return numOfInspectionByMonkey[numOfInspectionByMonkey.size - 1] *
      numOfInspectionByMonkey[numOfInspectionByMonkey.size - 2]
  }

  fun part2(input: List<String>): Long {
    val monkeyMap = initialize(input)
    var mod = 1
    monkeyMap.values.forEach {
      mod *= it.divisibleBy
    }


    repeat(10000) { round ->
      monkeyMap.keys.sorted().forEach {
        monkeyMap[it]?.process(mod = mod)
      }
    }
    val numOfInspectionByMonkey = monkeyMap.values.map {
      it.numberOfItemsInspected
    }.sorted()

    return numOfInspectionByMonkey[numOfInspectionByMonkey.size - 1].toLong() *
      numOfInspectionByMonkey[numOfInspectionByMonkey.size - 2].toLong()
  }

  val testInput = readInput("_2022/Day11/Day11_test")
  check(part1(testInput) == 10605)
  check(part2(testInput) == 2713310158)

  val input = readInput("_2022/Day11/Day11")
  println(part1(input))
  println(part2(input))
}

