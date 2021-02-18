package com.hilt.todo.utils

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

fun <T : ViewDataBinding?> AppCompatActivity.setActBinding(layoutId: Int): T{
    return DataBindingUtil.setContentView<T>(this, layoutId)
}

fun <T : ViewDataBinding?> View.setLayoutBinding(layoutId: Int, container: ViewGroup?): T{
    return DataBindingUtil.inflate<T>(
        LayoutInflater.from(context),
        layoutId, container, false)
}