package com.kimochi.note.di

import com.kimochi.note.modules.detail.viewmodel.DetailVM
import com.kimochi.note.modules.main.viewmodel.MainVM
import com.kimochi.note.modules.write.viewmodel.WriteVM
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {

    viewModel { MainVM() }

    viewModel { WriteVM() }

    viewModel { DetailVM() }
}