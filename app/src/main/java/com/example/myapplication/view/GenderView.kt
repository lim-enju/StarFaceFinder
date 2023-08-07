package com.example.myapplication.view

import android.content.Context
import android.graphics.BlendMode
import android.graphics.BlendModeColorFilter
import android.graphics.PorterDuff
import android.graphics.drawable.LayerDrawable
import android.os.Build
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import com.example.myapplication.R
import com.example.myapplication.databinding.ViewGenderBinding


class GenderView: LinearLayout {
    val binding: ViewGenderBinding =
        ViewGenderBinding.inflate(LayoutInflater.from(context), this, false)

    constructor(context: Context?) : super(context){
        initView()
    }
    constructor(context: Context?, attrs: AttributeSet) : super(context, attrs){
        initView()
        getAttrs(attrs)
    }

    private fun initView() { // inflate binding and add as view
        addView(binding.root)
    }

    private fun getAttrs(attrs: AttributeSet) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.GenderView)
        val genderColor: Int
        val genderDrawable: Int

        when(typedArray.getInteger(R.styleable.GenderView_gender, 0)){
            0 -> {
                binding.genderTxt.text = "남성"
                genderColor = R.color.male
                genderDrawable = R.drawable.icon_male
            }
            1 -> {
                binding.genderTxt.text = "여성"
                genderColor = R.color.female
                genderDrawable = R.drawable.icon_female
            }
            2 -> {
                binding.genderTxt.text = "어린이"
                genderColor = R.color.child
                genderDrawable = R.drawable.icon_child
            }
            else -> return
        }

        //TODO:: 왜 with일까?
        with(binding){
            val color = ContextCompat.getColor(context, genderColor)
            genderImg.setImageDrawable(ContextCompat.getDrawable(context, genderDrawable))
            genderImg.setColorFilter(color)

            val progressDrawalbe =  genderProgressbar.progressDrawable as LayerDrawable
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                progressDrawalbe.getDrawable(0).colorFilter = BlendModeColorFilter(color, BlendMode.SRC_ATOP)
                progressDrawalbe.getDrawable(1).colorFilter = BlendModeColorFilter(color, BlendMode.SRC_ATOP)
            } else {
                progressDrawalbe.getDrawable(0).setColorFilter(color, PorterDuff.Mode.SRC_IN)
                progressDrawalbe.getDrawable(1).setColorFilter(color, PorterDuff.Mode.SRC_IN)
            }
        }
    }

    fun setValue(value: Int){
        with(binding){
            genderTxt.text = "${genderTxt.text} ${value}%"
            genderProgressbar.progress = value
        }
    }
}