package com.kimochi.note.modules.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.kimochi.note.R
import com.kimochi.note.databinding.ActDetailBinding
import com.kimochi.note.db.entity.NoteEntity
import com.kimochi.note.modules.detail.viewmodel.DetailVM
import com.kimochi.note.modules.write.WriteAct
import com.kimochi.note.utils.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class DetailAct : AppCompatActivity() {

    private val viewmodel: DetailVM by viewModel()

    private lateinit var binding: ActDetailBinding

    private var note: NoteEntity? = null

    companion object {
        fun newIntent(context: Context, note: NoteEntity? = null): Intent {
            return Intent(context, DetailAct::class.java).apply {
                putParcel(context.getString(R.string.extra_note), note)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initBinding()
        retrieveData()
        setActionBar()
        initViewModel()
    }

    private fun initBinding() {
        binding = setActBinding(R.layout.act_detail)
        binding.lifecycleOwner = this
        binding.vm = viewmodel
    }

    private fun setActionBar() {
        val toolbar = binding.toolbar
        setSupportActionBar(toolbar)
        supportActionBar?.setTitle(context().getString(R.string.detail_toolbar))
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun retrieveData() {
        note = intent.getParcel(context().getString(R.string.extra_note))
    }

    private fun initViewModel() {
        debug(note?.id.toString())
        viewmodel.getNoteById(note!!.id)
    }

    private fun showDeleteDialog(){
        val alertDialogBuilder = AlertDialog.Builder(this)
        alertDialogBuilder
            .setMessage("delete this note")
            .setCancelable(false)
            .setPositiveButton("delete") { _, _ ->
                note?.let { viewmodel.deleteById(it) }
                onBackPressed()
            }
            .setNegativeButton("cancel") { dialog, id ->
                dialog.cancel()
            }

        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.detail_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.btn_delete -> showDeleteDialog()
            R.id.btn_edit -> {
                startActivity(WriteAct.newIntent(this, note))
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
