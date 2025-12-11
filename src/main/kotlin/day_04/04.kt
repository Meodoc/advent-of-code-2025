package day_04

import java.io.File

const val PAPER = '@'
const val ACCESS_THRESHOLD = 4

val adjOffsets = listOf(
    -1 to -1,
    -1 to 0,
    -1 to 1,
    1 to -1,
    1 to 0,
    1 to 1,
    0 to -1,
    0 to 1
)

fun isAccessible(pos: Pair<Int, Int>, grid: Array<CharArray>): Boolean {
    val adjacent = adjOffsets
        .map { (offsetX, offsetY) -> offsetX + pos.first to offsetY + pos.second }
        .filter { (x, y) -> x in 0..grid[0].lastIndex && y in 0..grid.lastIndex }
        .count { (x, y) -> grid[y][x] == PAPER }

    return adjacent < ACCESS_THRESHOLD
}

fun getAccessible(grid: Array<CharArray>): List<Pair<Int, Int>> =
    grid.withIndex().flatMap { indexedRow ->
        indexedRow.value.withIndex().filter { it.value == PAPER }
            .mapNotNull { indexedColumn ->
                val pos = indexedColumn.index to indexedRow.index
                if (isAccessible(pos, grid)) pos else null
            }
    }

fun a(grid: Array<CharArray>) {
    val answer = getAccessible(grid).count()
    println("Answer a: $answer")
}

fun b(grid: Array<CharArray>) {
    var total = 0

    do {
        val accessible = getAccessible(grid)
        total += accessible.size
        accessible.forEach { (x, y) -> grid[y][x] = '.' }
    } while (accessible.isNotEmpty())

    println("Answer b: $total")
}

fun bFunctional(grid: Array<CharArray>) {
    val total = generateSequence(getAccessible(grid)) { accessible ->
        accessible.forEach { (x, y) -> grid[y][x] = '.' }
        getAccessible(grid).takeIf { it.isNotEmpty() }
    }.sumOf { it.size }

    println("Answer b: $total")
}

fun parse(input: String): Array<CharArray> =
    File(input).readLines().map { row ->
        row.toCharArray()
    }.toTypedArray()

fun main() {
    // val input = "04/example.in"
    val input = "04/4.in"

    a(parse(input)) // 1416
    b(parse(input)) // 9086
    bFunctional(parse(input))
}