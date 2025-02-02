package com.medvedev.jobsearch.app.presentation

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import com.medvedev.jobsearch.R
import com.medvedev.jobsearch.databinding.CustomBottomNavBinding

class CustomBottomNavigationView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 1
) : FrameLayout(context, attributeSet, defStyleAttr) {

    private val binding = CustomBottomNavBinding.inflate(LayoutInflater.from(context), this, true)

    private var selectedIndex = 0

    private val items by lazy {
        listOf(
            binding.itemSearch,
            binding.itemFavorite,
            binding.itemResponses,
            binding.itemMessages,
            binding.itemProfile
        )
    }

    private lateinit var onNavItemClickListener: (Int) -> Unit

    init {
        updateUi()
        initClickListeners()
    }

    fun setOnNavItemClickListener(listener: (Int) -> Unit) {
        onNavItemClickListener = listener
    }

    private fun initClickListeners() = items.forEachIndexed { index, item ->
        item.setOnClickListener {
            onItemClick(index)
        }
    }

    private fun onItemClick(index: Int) {
        selectedIndex = index
        updateUi()
        onNavItemClickListener.invoke(selectedIndex)
    }

    private fun updateUi() {
        items.forEachIndexed { index, textView ->
            if (index == selectedIndex) {
                textView.setTextColor(ContextCompat.getColor(context, R.color.blue))

                val drawables = textView.compoundDrawablesRelative
                val drawableTop = drawables[1]
                drawableTop?.let { drawable ->
                    val wrapperDrawable = DrawableCompat.wrap(drawable).mutate()
                    DrawableCompat.setTint(
                        wrapperDrawable,
                        ContextCompat.getColor(context, R.color.blue)
                    )
                    textView.setCompoundDrawablesRelative(
                        drawables[0],
                        wrapperDrawable,
                        drawables[2],
                        drawables[3]
                    )
                }
            } else {
                textView.setTextColor(ContextCompat.getColor(context, R.color.grey_4))

                val drawables = textView.compoundDrawablesRelative
                val drawableTop = drawables[1]
                drawableTop?.let { drawable ->
                    val wrapperDrawable = DrawableCompat.wrap(drawable).mutate()
                    DrawableCompat.setTint(
                        wrapperDrawable,
                        ContextCompat.getColor(context, R.color.grey_4)
                    )
                    textView.setCompoundDrawablesRelative(
                        drawables[0],
                        wrapperDrawable,
                        drawables[2],
                        drawables[3]
                    )
                }
            }
        }
    }
}