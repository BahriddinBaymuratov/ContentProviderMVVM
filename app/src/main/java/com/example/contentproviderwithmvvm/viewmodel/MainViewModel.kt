package com.example.contentproviderwithmvvm.viewmodel

import android.content.ContentResolver
import android.content.ContentValues
import android.database.Cursor
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.contentproviderwithmvvm.MyContentProvider
import com.example.contentproviderwithmvvm.model.Student

class MainViewModel(
    private val contentResolver: ContentResolver
) : ViewModel(){

    private val _state: MutableLiveData<MainState> = MutableLiveData()
    val state: LiveData<MainState> get() = _state
    private lateinit var cursor: Cursor

    init {
        cursor = contentResolver
            .query(
                MyContentProvider.CONTENT_URI,
                arrayOf(
                    MyContentProvider.ID,
                    MyContentProvider.NAME,
                    MyContentProvider.LAST_NAME),
                null,null,
                MyContentProvider.NAME
            )!!
    }
    fun onEvent(event: MainEvent){
        when(event){
            is MainEvent.OnNext -> {
                if (cursor.moveToNext()){
                    _state.value = MainState(
                        Student(
                            cursor.getInt(0),
                            cursor.getString(1)!!,
                            cursor.getString(2)!!
                        )
                    )
                }
            }
            is MainEvent.OnPrev -> {
                if (cursor.moveToPrevious()){
                    _state.value = MainState(
                        Student(
                            cursor.getInt(0),
                            cursor.getString(1)!!,
                            cursor.getString(2)!!
                        )
                    )
                }
            }
            is MainEvent.OnDeleteStudent -> {
                contentResolver.delete(
                    MyContentProvider.CONTENT_URI,
                    "NAME = ?",
                    arrayOf(event.name)
                )
                cursor.requery()
            }
            is MainEvent.OnSaveStudent -> {
                val cn = ContentValues()
                cn.put("NAME", event.student.name)
                cn.put("LAST_NAME", event.student.lastName)
                contentResolver.insert(
                    MyContentProvider.CONTENT_URI,
                    cn
                )
                cursor.requery()
            }
            is MainEvent.OnUpdateStudent -> {
                val cn = ContentValues()
                cn.put("NAME", event.student.name)
                cn.put("LAST_NAME", event.student.lastName)
                contentResolver.update(
                    MyContentProvider.CONTENT_URI,
                    cn,
                    "NAME = ?",
                    arrayOf(event.student.name)
                )
                cursor.requery()
            }
        }
    }
}