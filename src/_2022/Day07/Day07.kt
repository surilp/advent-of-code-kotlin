package _2022.Day07

import readInput

class Directory(private val parent: Directory? = null) {
  private val listOfFiles: MutableList<File> = mutableListOf()
  private val mapOfDirectories: MutableMap<String, Directory> = mutableMapOf()
  private var directorySize = 0

  fun addFile(fileName: String, fileSize: Int) {
    listOfFiles.add(File(fileName, fileSize))
  }

  fun addDirectory(directoryName: String) {
    if (!mapOfDirectories.containsKey(directoryName)) {
      mapOfDirectories[directoryName] = Directory(this)
    }
  }

  fun changeDirectory(dirName: String): Directory {
    return mapOfDirectories[dirName]!!
  }

  fun goToParentDirectory(): Directory? {
    return parent
  }

  fun addToDirectorySize(size: Int) {
    directorySize += size
  }

  fun directorySize(): Int {
    return directorySize
  }

  fun getChildDirectories(): MutableCollection<Directory> {
    return mapOfDirectories.values
  }
}

class File(val name: String, val size: Int)

fun main() {

  fun createFileSystem(commands: List<String>): Directory {
    val root = Directory()
    var current = root
    var curIdx = 1

    while (curIdx < commands.size) {
      val command = commands[curIdx]
      when {
        command.startsWith("$ ls") -> {
          curIdx += 1
          while (curIdx < commands.size && commands[curIdx][0] != '$') {
            val dirContent = commands[curIdx].split(" ")

            if (dirContent[0] == "dir") {
              current.addDirectory(dirContent.last())
            } else {
              val fileSize = dirContent.first().toInt()
              current.addFile(dirContent.last(), fileSize)
              var currentDir: Directory? = current
              while (currentDir != null) {
                currentDir.addToDirectorySize(dirContent.first().toInt())
                currentDir = currentDir.goToParentDirectory()
              }
            }
            curIdx += 1
          }
        }

        command.startsWith("$ cd /") -> {
          current = root
          curIdx += 1
        }

        command.startsWith("$ cd ..") -> {
          current = current.goToParentDirectory()!!
          curIdx += 1
        }

        command.startsWith("$ cd") -> {
          val directory = command.split(" ").last()
          current = current.changeDirectory(directory)
          curIdx += 1
        }
      }
    }
    return root
  }

  fun dfs(root: Directory, result: MutableList<Int>, limit: Int) {
    if (root.directorySize() <= limit) {
      result.add(root.directorySize())
    }
    root.getChildDirectories().forEach {
      dfs(it, result, limit)
    }
  }

  fun listOfDirectorySize(root: Directory, result: MutableList<Int>) {
    result.add(root.directorySize())
    root.getChildDirectories().forEach {
      listOfDirectorySize(it, result)
    }
  }

  fun part1(input: List<String>): Int {
    val root = createFileSystem(input)
    val candidate = mutableListOf<Int>()
    dfs(root, candidate, 100000)
    return candidate.sum()
  }

  fun binarySearch(listOfDirectorySize: List<Int>, target: Int): Int {
    var left = 0
    var right = listOfDirectorySize.size
    var result = listOfDirectorySize[0]
    while (left <= right) {
      val mid = (left + right) / 2
      if (listOfDirectorySize[mid] >= target) {
        result = listOfDirectorySize[mid]
        right = mid - 1
      } else {
        left = mid + 1
      }
    }
    return result
  }

  fun part2(input: List<String>): Int {
    val root = createFileSystem(input)
    val neededSpace = 30000000 - (70000000 - root.directorySize())

    val listOfDirectorySize = mutableListOf<Int>()
    listOfDirectorySize(root, listOfDirectorySize)
    listOfDirectorySize.sort()
    return binarySearch(listOfDirectorySize, neededSpace)
  }

  val testInput = readInput("_2022/Day07/Day07_test")
  check(part1(testInput) == 95437)
  check(part2(testInput) == 24933642)

  val input = readInput("_2022/Day07/Day07")
  println(part1(input))
  println(part2(input))
}