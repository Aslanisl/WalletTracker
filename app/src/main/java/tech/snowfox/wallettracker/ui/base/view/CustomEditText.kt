package tech.snowfox.wallettracker.ui.base.view

import android.content.Context
import android.support.annotation.AttrRes
import android.text.InputType
import android.util.AttributeSet
import android.widget.LinearLayout
import kotlinx.android.synthetic.main.custom_edit_field.view.*
import tech.snowfox.wallettracker.R

class CustomEditText
@JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, @AttrRes defStyleAttr: Int = 0)
    : LinearLayout(context, attrs, defStyleAttr) {

    init {
        inflate(context, R.layout.custom_edit_field, this)

        if (attrs != null) {
            val typedArray = context.theme.obtainStyledAttributes(
                    attrs,
                    R.styleable.CustomEditText,
                    0, 0
            )

            try {
                val hintText = typedArray.getString(R.styleable.CustomEditText_hintText)
                hintText?.let { customEditLayout.hint = it }

                val textInputStyle = typedArray.getInt(R.styleable.CustomEditText_inputEditStyle, -1)
                customEditText.inputType = when (textInputStyle) {
                    0 -> InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
                    1 -> InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                    else -> InputType.TYPE_CLASS_TEXT
                }
            } finally {
                typedArray.recycle()
            }
        }
    }

    var text: String
        get() = customEditText.text.toString()
        set(value) {
            customEditText.setText(value)
        }
}