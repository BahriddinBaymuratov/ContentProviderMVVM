package com.example.contentproviderwithmvvm

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.contentproviderwithmvvm.databinding.ActivityMainBinding
import com.example.contentproviderwithmvvm.model.Student
import com.example.contentproviderwithmvvm.viewmodel.MainEvent
import com.example.contentproviderwithmvvm.viewmodel.MainViewModel
import com.example.contentproviderwithmvvm.viewmodel.MainViewModelFactory

class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private lateinit var viewModelFactory: MainViewModelFactory
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        viewModelFactory = MainViewModelFactory(contentResolver)
        viewModel = ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java]

        binding.btnInsert.setOnClickListener {
            val name  = binding.editName.text.toString().trim()
            val lastName = binding.editMean.text.toString().trim()
            viewModel.onEvent(MainEvent.OnSaveStudent(Student(name = name, lastName = lastName)))
            Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show()
            clear()
        }
        viewModel.state.observe(this){
            observeStudent(it.student)
        }
        binding.btnNext.setOnClickListener {
            viewModel.onEvent(MainEvent.OnNext)
        }
        binding.btnPre.setOnClickListener {
            viewModel.onEvent(MainEvent.OnPrev)
        }
        binding.btnClear.setOnClickListener {
            clear()
        }
        binding.btnUpdate.setOnClickListener {
            val name = binding.editName.text.toString().trim()
            val lastName = binding.editMean.text.toString().trim()
            viewModel.onEvent(MainEvent.OnUpdateStudent(Student(name = name,lastName = lastName)))
            Toast.makeText(this, "Updated", Toast.LENGTH_SHORT).show()
        }
        binding.btnDelete.setOnClickListener {
            val name = binding.editName.text.toString().trim()
            viewModel.onEvent(MainEvent.OnDeleteStudent(name))
            clear()
            Toast.makeText(this, "Deleted", Toast.LENGTH_SHORT).show()
        }
    }
    private fun clear(){
        binding.editMean.text?.clear()
        binding.editName.text?.clear()
    }
    private fun observeStudent(student: Student){
        binding.editName.setText(student.name)
        binding.editMean.setText(student.lastName)
    }
}