package com.simple.instagram

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.security.crypto.EncryptedFile
import androidx.security.crypto.MasterKey
import kotlinx.android.synthetic.main.activity_file.*
import kotlinx.android.synthetic.main.activity_file.ed_data
import kotlinx.android.synthetic.main.activity_shared_pref.*
import java.io.*

class FileActivity : AppCompatActivity() {

    private lateinit var normalFile: File
    private lateinit var secretFile: File
    private lateinit var encryptedFile: EncryptedFile

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_file)

        /**
         * recommended way for general purposes
         */
        val masterKey = MasterKey.Builder(this, MasterKey.DEFAULT_MASTER_KEY_ALIAS).
        setKeyScheme(MasterKey.KeyScheme.AES256_GCM).build()

        normalFile = File(filesDir, "normal")

        secretFile = File(filesDir, "secret")

        encryptedFile = EncryptedFile.Builder(
            applicationContext,
            secretFile,
            masterKey,
            EncryptedFile.FileEncryptionScheme.AES256_GCM_HKDF_4KB
        )
            .setKeysetAlias("file_key") //optional
            .setKeysetPrefName("secret_file_shared_prefs")  //optional
            .build()

        initViews()
    }

    private fun initViews() {
        btn_read_file.setOnClickListener {
            printFileContent(normalFile)
        }

        btn_show_content.setOnClickListener {
            printFileContent(secretFile)
        }

        btn_read_file_encrypted.setOnClickListener {
            encryptedFile.openFileInput().use { inputStream ->
                tv_result.text = String(inputStream.readBytes(), Charsets.UTF_8)
            }
        }

        btn_save_file.setOnClickListener { saveFile() }

        btn_save_file_encrypted.setOnClickListener { saveFileEncrypted() }
    }

    private fun printFileContent(file: File) {
        if (file.exists()) {
            val strBuilder = StringBuilder()
            BufferedReader(FileReader(file)).readLines().forEach {
                strBuilder.append(it)
            }
            tv_result.text = strBuilder.toString()
        } else
            Toast.makeText(this, getString(R.string.file_not_found), Toast.LENGTH_SHORT).show()
    }

    private fun saveFile() {
        if (!normalFile.exists())
            try {
                normalFile.createNewFile()
            } catch (e: IOException) {
                e.printStackTrace()
            }

        try {
            OutputStreamWriter(FileOutputStream(normalFile)).let {
                it.apply {
                    write(ed_data.text.toString())
                    close()
                }
            }
            Toast.makeText(this, getString(R.string.file_saved), Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun saveFileEncrypted() {
        secretFile.delete()

        encryptedFile.openFileOutput().use { outputStream ->
            outputStream.write(ed_data.text.toString().toByteArray())

            Toast.makeText(this, getString(R.string.file_saved), Toast.LENGTH_SHORT).show()
        }
    }
}