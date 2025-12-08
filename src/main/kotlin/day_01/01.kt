package day_01

import java.nio.file.Files
import java.nio.file.Path
import kotlin.math.abs
import kotlin.math.floor

const val wrap = 100

fun turnDial(pos: Int, dir: String, steps: Int): Int = when (dir) {
    "L" -> (pos - steps)
    "R" -> (pos + steps)
    else -> throw IllegalArgumentException("Invalid direction: $dir")
}

fun a(instructions: List<Pair<String, Int>>) {
    var pos = 50
    var numberOfZeros = 0

    instructions.forEach { (direction, steps) ->
        pos = turnDial(pos, direction, steps) % wrap

        if (pos == 0) {
            numberOfZeros++
        }
    }

    println(numberOfZeros)
}

/*
 If we reach 0 -> count it
 If we rush by 0 -> count it
   rush by 0 negatively:
     case previous pos 0 -> negative: floor - 1
     else -> floor
   rush by 0 positively:
     case newPos 0: floor -1
     else -> floor

 0 -> -1 --> 0
 1 -> -1 --> 1

 99 -> 100 --> 1
 55 -> 0 --> 1

 */

// https://gemini.google.com/share/aa3729241b18

fun b(instructions: List<Pair<String, Int>>) {
    var pos = 50
    var numberOfZeros = 0

    instructions.forEach { (direction, steps) ->
        val newAbsolutePos = turnDial(pos, direction, steps)
        val newPos = Math.floorMod(newAbsolutePos, wrap)

        if (newPos == 0) {
            numberOfZeros++
        }

        val numberOfWraps = when {
            newAbsolutePos < 0 && pos == 0 -> abs(floor(newAbsolutePos / wrap.toDouble()).toInt()) - 1
            newAbsolutePos > 0 && newPos == 0 -> abs(floor(newAbsolutePos / wrap.toDouble()).toInt()) - 1
            else -> abs(floor(newAbsolutePos / wrap.toDouble()).toInt())
        }

        pos = newPos
        numberOfZeros += numberOfWraps
    }

    println(numberOfZeros)
}


fun main() {
    val input = "01/1.in"
    // val input = "01/example.in"

    val instructions = Files.readAllLines(Path.of(input)).map { line ->
        line.slice(0..0) to line.slice(1..line.lastIndex).toInt()
    }

    a(instructions)
    b(instructions)
}