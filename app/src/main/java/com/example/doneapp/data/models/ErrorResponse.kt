package com.example.doneapp.data.models

import kotlinx.serialization.Serializable

@Serializable
data class ErrorResponse(
    val error: String
)