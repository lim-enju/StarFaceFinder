package com.example.myapplication.view

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class SpacingItemDecorator(
    private val padding: Int,
    vararg spacingTypes: SpacingType
): RecyclerView.ItemDecoration() {
    private val spacingTypeSet: Set<SpacingType> = spacingTypes.toSet()

    enum class SpacingType{
        TOP, BOTTOM, LEFT, RIGHT
    }

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        if (SpacingType.TOP in spacingTypeSet) {
            outRect.top = padding
        }

        if (SpacingType.BOTTOM in spacingTypeSet) {
            outRect.bottom = padding
        }

        if (SpacingType.LEFT in spacingTypeSet) {
            outRect.left = padding
        }

        if (SpacingType.RIGHT in spacingTypeSet) {
            outRect.right = padding
        }
    }
}