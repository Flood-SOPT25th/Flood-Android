package com.flood_android.ui.signup

import android.content.Context
import android.util.AttributeSet
import android.widget.NumberPicker


class CustomNumberpicker : NumberPicker {

    private val SELECTOR_WHEEL_ITEM_COUNT = 10


    override fun getMinValue(): Int {
        return super.getMinValue()
    }

    override fun setMinValue(minValue: Int) {
        super.setMinValue(0)
    }

    override fun setMaxValue(maxValue: Int) {
        super.setMaxValue(maxValue)
    }

    override fun getMaxValue(): Int {
        return super.getMaxValue()
    }

    override fun getDisplayedValues(): Array<String> {
        return super.getDisplayedValues()
    }

    override fun setDisplayedValues(displayedValues: Array<String>) {
        super.setDisplayedValues(displayedValues)
    }

    override fun setFormatter(formatter: Formatter) {
        super.setFormatter(formatter)
    }

    override fun setWrapSelectorWheel(wrapSelectorWheel: Boolean) {
        super.setWrapSelectorWheel(wrapSelectorWheel)
    }

    override fun setDescendantFocusability(focusability: Int) {
        super.setDescendantFocusability(focusability)
    }

    constructor(context: Context?) : super(context) {}
    constructor(context: Context?, attrs: AttributeSet?) : super(
        context,
        attrs
    ) {
    }

    constructor(
        context: Context?,
        attrs: AttributeSet?,
        defStyleAttr: Int
    ) : super(context, attrs, defStyleAttr) {
    }

    constructor(
        context: Context?,
        attrs: AttributeSet?,
        defStyleAttr: Int,
        defStyleRes: Int
    ) : super(context, attrs, defStyleAttr, defStyleRes) {
    }

    companion object {
        private const val SELECTOR_WHEEL_ITEM_COUNT = 10
    }
}