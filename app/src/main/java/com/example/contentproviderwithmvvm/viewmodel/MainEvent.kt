package com.example.contentproviderwithmvvm.viewmodel

import com.example.contentproviderwithmvvm.model.Student

sealed class MainEvent {
    object OnNext : MainEvent()
    object OnPrev : MainEvent()
    data class OnSaveStudent(val student: Student) : MainEvent()
    data class OnDeleteStudent(val name: String) : MainEvent()
    data class OnUpdateStudent(val student: Student) : MainEvent()
}