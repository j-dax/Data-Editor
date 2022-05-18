package com.jdax.data.window

data class DataWindow constructor(val content: List<List<String>>) {
    constructor(arrayContent: Array<Array<String>>) : this(arrayContent.map { it.asList() })
}