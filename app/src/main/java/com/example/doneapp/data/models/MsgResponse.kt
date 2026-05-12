package com.example.doneapp.data.models

import kotlinx.serialization.Serializable

@Serializable
data class MsgResponse(
    val mensagem: String
)