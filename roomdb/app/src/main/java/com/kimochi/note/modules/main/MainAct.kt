package com.kimochi.note.modules.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager.VERTICAL
import com.kimochi.note.R
import com.kimochi.note.databinding.ActMainBinding
import com.kimochi.note.db.entity.NoteEntity
import com.kimochi.note.modules.detail.DetailAct
import com.kimochi.note.modules.main.adapter.NoteListAdapter
import com.kimochi.note.modules.main.viewmodel.MainVM
import com.kimochi.note.modules.write.WriteAct
import com.kimochi.note.utils.context
import com.kimochi.note.utils.debug
import com.kimochi.note.utils.openActivity
import com.kimochi.note.utils.setActBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainAct : AppCompatActivity() {

    private val viewmodel : MainVM by viewModel()

    private lateinit var binding : ActMainBinding

    private lateinit var noteListAdapter: NoteListAdapter

    private val listNote = mutableListOf<NoteEntity>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initBinding()
        initView()
        retrieveLiveData()
        initNoteListAdapter()
    }

    private fun initBinding(){
        binding = setActBinding(R.layout.act_main)
        binding.lifecycleOwner = this
    }

    private fun initView(){
        binding.fab.setOnClickListener {
            openActivity(WriteAct::class.java)
        }
    }

    private fun retrieveLiveData(){
        viewmodel.getNotes().observe(this, Observer {
            listNote.clear()
            listNote.addAll(it)
            noteListAdapter.notifyDataSetChanged()
        })
    }

    private fun initNoteListAdapter(){
        noteListAdapter = NoteListAdapter(listNote){
            startActivity(DetailAct.newIntent(this, it))
        }
        binding.rv.apply {
            adapter = noteListAdapter
            layoutManager = LinearLayoutManager(context(), VERTICAL, false)
        }
    }
}
