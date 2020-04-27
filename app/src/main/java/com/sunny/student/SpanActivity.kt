package com.sunny.student

import android.os.Bundle
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.CharacterStyle
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.HtmlCompat
import kotlinx.android.synthetic.main.activity_span.*

class SpanActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_span)
        val string = "<font color=\"#FF6C00\" size=\"20\">谷歌</font> " + "哈哈哈" +
                "<font color=\"#FF6C00\" size=\"20\">谷歌</font>"
        val char = HtmlCompat.fromHtml(string, HtmlCompat.FROM_HTML_MODE_COMPACT)
        val urls = char.getSpans(0, string.length, CharacterStyle::class.java)
        textView5.movementMethod = LinkMovementMethod.getInstance()

        val span = SpannableStringBuilder(char)
        urls?.forEach {
            val clickSpan = object : ClickableSpan(){
                private var mColor = 0xFF6C00
                fun setColor(color : Int) {
                    mColor = color
                }
                override fun onClick(widget: View) {
                    Log.d("span", this.toString())
                }

                override fun updateDrawState(ds: TextPaint) {
                    super.updateDrawState(ds)
                    ds.color = mColor
                    ds.isUnderlineText = false
                }

            }
            if (it is ForegroundColorSpan) {
                clickSpan.setColor(it.foregroundColor)
            }
            span.setSpan(clickSpan, char.getSpanStart(it), char.getSpanEnd(it), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        }
        textView5.text = span
        textView5.highlightColor = 0x00000000

    }
}
