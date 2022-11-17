package com.example.my_simple_mvvm.views.changeColor

import com.example.my_simple_mvvm.model.colors.NamedColor

/**
 * Represents list item for the color; it may be selected or not
 */
data class NamedColorListItem(
    val namedColor: NamedColor,
    val selected: Boolean
) {
}