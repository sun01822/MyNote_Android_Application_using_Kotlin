package com.example.mynote

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.mynote.databinding.ActivityDetailScreenBinding

class DetailScreen : AppCompatActivity() {
    private lateinit var binding: ActivityDetailScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val id = intent.getIntExtra("id", 0)
        val title = intent.getStringExtra("title")
        val description = intent.getStringExtra("description")
        val time = intent.getStringExtra("time")

        binding.sampleTitle.text = title.toString()
        binding.sampleDescription.text = description.toString()
        binding.sampleTime.text = time.toString()

        binding.goBack.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        binding.editTextView.setOnClickListener {
            val intent = Intent(this, InputActivity::class.java)
            intent.putExtra("code", "y")
            intent.putExtra("id", id)
            intent.putExtra("title", title)
            intent.putExtra("description", description)
            startActivity(intent)
            finish()
        }
    }
}