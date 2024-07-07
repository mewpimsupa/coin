package com.pimsupa.coin.util

import android.content.Context

sealed class UiText {
    data class DynamicString(val text: String) : UiText()
    data class StringResource(val resId: Int, val formatArgs: Array<out Any> = arrayOf()) : UiText() {
        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (other !is StringResource) return false

            return resId == other.resId && formatArgs.contentEquals(other.formatArgs)
        }

        override fun hashCode(): Int {
            var result = resId
            result = 31 * result + formatArgs.contentHashCode()
            return result
        }

        override fun toString(): String {
            return "StringResource(resId=$resId, formatArgs=${formatArgs.contentToString()})"
        }
    }

    fun asString(context: Context? = null): String {
        return when (this) {
            is DynamicString -> text
            is StringResource -> context?.getString(resId, *formatArgs) ?: ""
        }
    }
}
