package com.hilt.todo.view.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hilt.todo.R
import com.hilt.todo.data.db.entity.Todo
import com.hilt.todo.databinding.ItemTodoBinding
import com.hilt.todo.utils.setLayoutBinding

class MainAdapter (
    private val listTodo: List<Todo>
): RecyclerView.Adapter<MainAdapter.MainVH>() {

    private lateinit var binding: ItemTodoBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainVH {
        binding = parent.setLayoutBinding(R.layout.item_todo, parent)
        return MainVH(binding)
    }

    override fun getItemCount(): Int = listTodo.size

    override fun onBindViewHolder(holder: MainVH, position: Int) {
        holder.bind(listTodo[position])
    }

    inner class MainVH(binding: ItemTodoBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind (item: Todo) {
            binding.todo = item
        }
    }
}