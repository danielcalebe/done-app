package com.example.doneapp.data.models

import kotlinx.serialization.Serializable

@Serializable
data class TaskRequest(
    val titulo: String,
    val descricao: String
)