package aoc

private class Input

fun lines(filename: String): Sequence<String> = Input::class.java
    .getResourceAsStream(filename)!!
    .bufferedReader()
    .lineSequence()
