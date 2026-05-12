package com.example.doneapp.data.models

import kotlinx.serialization.Serializable


@Serializable
data class Task(
    val concluida: Boolean,
    val descricao: String,
    val id: Int,
    val titulo: String
)