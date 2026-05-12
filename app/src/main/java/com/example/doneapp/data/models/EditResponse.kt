package com.example.doneapp.data.models

import kotlinx.serialization.Serializable


@Serializable
data class EditResponse(
    val mensagem: String,
    val tarefas: Task
)
