package com.pimsupa.coin.util

/**
 *  This [StateEvent] can have exactly 2 states like the [StateEvent] but the triggered state holds a value of type [T].
 */
sealed interface StateEvent<T>

/**
 * The event in its triggered state holding a value of [T]. See [triggered] to create an instance of this.
 * @param content A value that is needed on the event consumer side.
 */
data class StateEventTriggered<T>(val content: T) : StateEvent<T> {
    override fun toString(): String = "triggered($content)"
}

/**
 * The event in its consumed state not holding any value. See [consumed] to create an instance of this.
 */
data class StateEventConsumed<T>(val nothing: Unit = Unit) : StateEvent<T> {
    override fun toString(): String = "consumed"
}

/**
 * A shorter and more readable way to create an [StateEvent] in its triggered state holding a value of [T].
 * @param content A value that is needed on the event consumer side.
 */
fun <T> triggered(content: T) = StateEventTriggered(content)

/**
 * A shorter and more readable way to create an [StateEvent] in its consumed state.
 */
fun <T> consumed() = StateEventConsumed<T>()
