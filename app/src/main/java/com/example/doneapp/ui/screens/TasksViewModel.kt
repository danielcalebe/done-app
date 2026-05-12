package com.example.doneapp.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.doneapp.data.ApiService
import com.example.doneapp.data.UiState
import com.example.doneapp.data.models.EditResponse
import com.example.doneapp.data.models.MsgResponse
import com.example.doneapp.data.models.Task
import com.example.doneapp.data.models.TaskRequest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class TasksViewModel : ViewModel() {
    private val _tasks = MutableStateFlow<UiState<List<Task>>?>(null)
    val tasks: StateFlow<UiState<List<Task>>?> = _tasks.asStateFlow()

    fun getTarefas() {
        viewModelScope.launch {
            _tasks.value = UiState.Loading
            _tasks.value = ApiService.getTarefas()
        }
    }


    private val _delete = MutableStateFlow<UiState<MsgResponse>?>(null)
    val delete: StateFlow<UiState<MsgResponse>?> = _delete.asStateFlow()

    fun deleteTarefa(id: Int) {
        viewModelScope.launch {
            _delete.value = UiState.Loading
            _delete.value = ApiService.deleteTarefa(id)
            _tasks.value = ApiService.getTarefas()
        }
    }


    private val _create = MutableStateFlow<UiState<EditResponse>?>(null)
    val create: StateFlow<UiState<EditResponse>?> = _create.asStateFlow()


    fun createTarefa(
        tarefa: TaskRequest
    ) {
        viewModelScope.launch {
            _create.value = UiState.Loading
            _create.value = ApiService.createTarefa(tarefa)
            _tasks.value = ApiService.getTarefas()
        }
    }



}