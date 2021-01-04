package com.kimochi.note.modules.write

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.kimochi.note.R
import com.kimochi.note.databinding.ActWriteBinding
import com.kimochi.note.db.entity.NoteEntity
import com.kimochi.note.modules.write.viewmodel.WriteVM
import com.kimochi.note.utils.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class WriteAct : AppCompatActivity() {

    private val viewmodel : WriteVM by viewModel()

    private lateinit var binding : ActWriteBinding

    private var note: NoteEntity? = null

    companion object {
        fun newIntent(context: Context, note: NoteEntity? = null): Intent {
            return Intent(context, WriteAct::class.java).apply {
                putParcel(context.getString(R.string.extra_edit), note)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        note = intent.getParcel(context().getString(R.string.extra_edit))

        initBinding()
        initView()
    }

    private fun initBinding(){
        binding = setActBinding(R.layout.act_write)
        binding.lifecycleOwner = this
        binding.vm = viewmodel
    }

    private fun initView(){
        if(!note.isNull()){
            viewmodel.id.postValue(note?.id)
            viewmodel.title.postValue(note?.title)
            viewmodel.desc.postValue(note?.description)
            binding.btnSave.setOnClickListener {
                viewmodel.updateNote()
                onBackPressed()
            }
        }else{
            binding.btnSave.setOnClickListener {
                viewmodel.saveNote()
                onBackPressed()
            }
        }
    }

}
