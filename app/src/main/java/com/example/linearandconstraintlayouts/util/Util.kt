package com.example.linearandconstraintlayouts.util

import kotlin.random.Random

private val syllables = arrayOf("do", "pa", "wo", "be", "tu", "fi", "ca")

fun randomName(syllablesCount: Int): String {
    return Array(syllablesCount) { syllables.random() }.joinToString("")
}

val characters = CharRange('A', 'Z').toList()

fun Random.nextString(size: Int): String = Array(size) {
    characters[nextInt(0, characters.lastIndex)]
}
    .fold("") {acc, c -> c.plus(acc) }

fun assertNull(value: Any?) {
    value?.let { assert(false) }
    assert(true)
}
