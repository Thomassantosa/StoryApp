package com.example.storyappsubmission.customview

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText

class CustomEmailText : AppCompatEditText {

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }

    private fun init() {

        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                // Do nothing
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (android.util.Patterns.EMAIL_ADDRESS.matcher(s.toString()).matches()) {
                    // Email is valid, clear the error
                    setError(null, null)
                } else {
                    // Email is not valid, show the error icon and message
                    setError("Invalid email address", null)
                }
            }

            override fun afterTextChanged(p0: Editable?) {
                // Do nothing
            }
        })
    }
}