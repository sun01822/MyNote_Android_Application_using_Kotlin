package com.example.mynote

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mynote.database.AppDatabase
import com.example.mynote.databinding.ActivityMainBinding
import com.example.mynote.models.NoteModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var appDB: AppDatabase
    lateinit var dataList: ArrayList<NoteModel>
    private lateinit var adapter:CustomAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        appDB = AppDatabase.initDatabase(this)
        showAllNotes()

        binding.floatingButton.setOnClickListener {
            startActivity(Intent(this, InputActivity::class.java))
            finish()
        }
    }
    private fun showAllNotes() {
        dataList = arrayListOf()
        GlobalScope.launch {
            appDB.noteDao().getAllNotes().forEach {
                    singleNote -> dataList.add(singleNote)
            }
            if(dataList.isEmpty()){
                binding.emptyLayout.visibility = View.VISIBLE
                binding.visibleLayout.visibility = View.GONE
            }
            else{
                binding.emptyLayout.visibility = View.GONE
                binding.visibleLayout.visibility = View.VISIBLE
                initRecyclerView()
            }
        }
    }
    private fun initRecyclerView(){
        adapter = CustomAdapter(dataList)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter

        adapter.setOnClickListener(object : CustomAdapter.OnClickListener{
            override fun onClick(position: Int, note: NoteModel) {
                val intent = Intent(this@MainActivity, DetailScreen::class.java)
                intent.putExtra("id", note.id)
                intent.putExtra("title", note.title)
                intent.putExtra("description", note.description)
                intent.putExtra("time", note.time)
                startActivity(intent)
                finish()
            }
            override fun onDelete(position: Int, note: NoteModel) {
                GlobalScope.launch {
                    appDB.noteDao().deleteNote(note)
                }
                dataList.removeAt(position)
                adapter.notifyItemRemoved(position)
                Toast.makeText(this@MainActivity, "Note deleted successfully", Toast.LENGTH_SHORT).show()
                if(dataList.isEmpty()){
                    binding.emptyLayout.visibility = View.VISIBLE
                    binding.visibleLayout.visibility = View.GONE
                }
            }
        })
    }
}