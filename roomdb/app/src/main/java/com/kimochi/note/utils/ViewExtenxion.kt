package com.kimochi.note.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.kimochi.note.BuildConfig

fun <T : ViewDataBinding?> AppCompatActivity.setActBinding(layoutId: Int): T{
    return DataBindingUtil.setContentView<T>(this, layoutId)
}

fun <T : ViewDataBinding?> Fragment.setFragBinding(layoutId: Int, container: ViewGroup?): T{
    return DataBindingUtil.inflate<T>(layoutInflater, layoutId, container, false)
}

fun <T : ViewDataBinding?> View.setLayoutBinding(layoutId: Int, container: ViewGroup?): T{
    return DataBindingUtil.inflate<T>(
        LayoutInflater.from(context),
        layoutId, container, false)
}

fun <T> Context.openActivity(it: Class<T>, extras: Bundle.() -> Unit = {}) {
    var intent = Intent(this, it)
    intent.putExtras(Bundle().apply(extras))
    startActivity(intent)
}

fun AppCompatActivity.context(): Context {
    return this
}

fun debug(message: String){
    if (BuildConfig.DEBUG) Log.d("Result", message)
}

fun Activity.toast(message: String){
    Toast.makeText(this, message , Toast.LENGTH_SHORT).show()
}

fun Intent.putParcel(key: String = "parcel_key", parcel: Parcelable?) {
    val bundle = Bundle()
    bundle.putParcelable(key, parcel)
    this.putExtra(key, bundle)
}

fun <T : Parcelable> Intent.getParcel(key: String = "parcel_key"): T? {
    return this.getBundleExtra(key)?.getParcelable(key)
}

fun Any?.isNull() : Boolean{
    return this == null
}