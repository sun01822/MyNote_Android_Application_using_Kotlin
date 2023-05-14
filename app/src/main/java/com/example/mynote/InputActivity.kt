package com.example.mynote


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.mynote.database.AppDatabase
import com.example.mynote.databinding.ActivityInputBinding
import com.example.mynote.models.NoteModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class InputActivity : AppCompatActivity() {
    private lateinit var binding: ActivityInputBinding
    private lateinit var appDB: AppDatabase
    private var code: String = ""
    private var id:Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInputBinding.inflate(layoutInflater)
        setContentView(binding.root)
        this.supportActionBar?.displayOptions = androidx.appcompat.app.ActionBar.DISPLAY_SHOW_CUSTOM
        supportActionBar?.setCustomView(R.layout.center_title)
        getInitData()
        appDB = AppDatabase.initDatabase(this)

        binding.saveTextView.setOnClickListener {
                val title = binding.titleET.text.toString()
                val description = binding.descriptionET.text.toString()
                val formatter = SimpleDateFormat("yyyy-MM-dd hh:mm:ss a")
                val time = formatter.format(Date())
                val note = NoteModel(null, title, description, time)
                if(title.isEmpty()){
                    binding.titleET.setBackgroundResource(R.drawable.empty_background)
                    Toast.makeText(this, "Empty Title", Toast.LENGTH_SHORT).show()
                }
                else if(description.isEmpty()){
                    binding.titleET.setBackgroundResource(R.drawable.custom_edittext)
                    binding.descriptionET.setBackgroundResource(R.drawable.empty_background)
                    Toast.makeText(this, "Empty Description", Toast.LENGTH_SHORT).show()
                }
                else {
                    if(code=="y"){
                        GlobalScope.launch {
                            appDB.noteDao().updateNote(note.title.toString(), note.description.toString(), note.time.toString(), id)
                        }
                        Toast.makeText(this, "Note added successfully", Toast.LENGTH_SHORT).show()
                    }
                    else{
                        GlobalScope.launch {
                            appDB.noteDao().insertNote(note)
                        }
                        Toast.makeText(this, "Note added successfully", Toast.LENGTH_SHORT).show()
                    }
                    startActivity(Intent(this,MainActivity::class.java))
                    finish()
                }
        }
    }
    private fun getInitData(){
        code = intent.getStringExtra("code").toString()
        val title = intent.getStringExtra("title")
        val description = intent.getStringExtra("description")
        id = intent.getIntExtra("id",0)
        if(code == "y"){
            binding.saveTextView.text = "Update"
            binding.titleET.setText(title)
            binding.descriptionET.setText(description)
        }
    }
}