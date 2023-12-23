package com.example.myapplication.view

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import com.example.myapplication.databinding.ViewSearchBinding

class SearchView: ConstraintLayout {
    private val binding: ViewSearchBinding = ViewSearchBinding.inflate(LayoutInflater.from(context), this, false)
    constructor(context: Context) : super(context){
        initView()
    }
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs){
        initView()
    }

    private fun initView() { // inflate binding and add as view
        addView(binding.root)
    }

    private fun getAttrs(attrs: AttributeSet?){

    }

    fun getText() = binding.searchEditText.text.toString()

    fun setText(text: String?){
        binding.searchEditText.setText(text)
    }

    fun addTextChangedListener(textWatcher: TextWatcher){
        binding.searchEditText.addTextChangedListener(textWatcher)
    }

    object SearchViewReverseBinding {
        @JvmStatic
        @BindingAdapter("content")
        fun setInputViewContent(view: SearchView, content: String?){
            val old = view.getText()
            if(old != content) {
                view.setText(content)
            }
        }

        @JvmStatic
        @BindingAdapter("contentAttrChanged")
        fun setInputViewInverseBindingListener(view: SearchView, listener: InverseBindingListener?) {
            val watcher = object : TextWatcher {
                override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {

                }

                override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {

                }

                override fun afterTextChanged(editable: Editable) {
                    listener?.onChange()
                }
            }

            view.addTextChangedListener(watcher)
        }

        @JvmStatic
        @InverseBindingAdapter(attribute = "content", event = "contentAttrChanged")
        fun getContent(view: SearchView): String {
            return view.getText()
        }
    }
}

