package com.hilt.todo.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import com.hilt.todo.R
import com.hilt.todo.data.db.entity.Todo
import com.hilt.todo.databinding.ActivityMainBinding
import com.hilt.todo.utils.setActBinding
import com.hilt.todo.view.adapter.MainAdapter
import com.hilt.todo.view.viewmodel.MainVM
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), CoroutineScope {

    private val job: Job = Job()
    private val viewModel: MainVM by viewModels()

    private lateinit var binding: ActivityMainBinding
    private lateinit var mainAdapter: MainAdapter

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initBinding()
        initFlow()
    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }

    private fun initBinding() {
        binding = setActBinding(R.layout.activity_main)
        binding.vm = viewModel
        binding.lifecycleOwner = this
    }

    private fun initFlow() {
        launch {
            viewModel.getAllTodo().collect {
                setMainAdapter(it)
                Log.d("", it.size.toString())
            }
        }
    }

    private fun setMainAdapter(listTodo: List<Todo>) {
        mainAdapter = MainAdapter(listTodo)
        binding.rv.adapter = mainAdapter
    }
}