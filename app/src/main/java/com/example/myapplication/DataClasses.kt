package com.example.myapplication


abstract class BaseClass<T> {
    abstract val current: Any
    abstract val options: List<Any>
    abstract val default: Any
    abstract val name: Any
    abstract val sectionId: Any
    abstract val title: String
    abstract val key: String
    internal val className: String? = this.javaClass.simpleName

    abstract fun copy(): BaseClass<T>
}

data class Booleans(
    override val current: Boolean,
    override val options: List<Boolean> = emptyList(),
    override val default: Boolean,
    override val name: String,
    override val sectionId: String,
    override val title: String = keyTitleGen(name).first,
    override val key: String = keyTitleGen(name).second
) : BaseClass<Boolean>() {
    override fun copy(): BaseClass<Boolean> = this
}

data class Strings(
    override val current: String,
    override val options: List<String> = emptyList(),
    override val default: String,
    override val name: String,
    override val sectionId: String,
    override val title: String = keyTitleGen(name).first,
    override val key: String = keyTitleGen(name).second
) : BaseClass<String>() {
    override fun copy(): BaseClass<String> = this
}

private fun keyTitleGen(valName: String): Pair<String, String> {
    // Split by camel case
    val words = valName.split("(?<!^)(?=[A-Z])".toRegex())

    // Create the two strings
    val firstString = words.joinToString(" ") { it.capitalize() }
    val secondString = words.joinToString("_") { it.toLowerCase() }

    return Pair(firstString, secondString)
}