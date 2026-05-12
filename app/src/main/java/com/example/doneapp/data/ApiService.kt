package com.example.doneapp.data

import android.util.Log
import com.example.doneapp.data.models.EditResponse
import com.example.doneapp.data.models.ErrorResponse
import com.example.doneapp.data.models.MsgResponse
import com.example.doneapp.data.models.Task
import com.example.doneapp.data.models.TaskRequest
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

object ApiService {

    val client = HttpClient(Android) {
        install(ContentNegotiation) {
            json(
                Json {
                    ignoreUnknownKeys = true
                }
            )
        }
    }

    private const val BASE_URL = "http://10.0.2.2:5000"
    suspend fun getTarefas(): UiState<List<Task>> {
        return try {
            val response = UiState.Success(client.get("$BASE_URL/tarefas").body<List<Task>>())
            Log.d("getTarefas", response.toString())
            response
        } catch (e: Exception) {
            Log.e("getTarefasErr", e.toString())
            UiState.Error(e, "Erro desconhecido")
        }
    }
    suspend fun deleteTarefa(id: Int): UiState<MsgResponse>{
        return try {
           val response = client.delete("$BASE_URL/tarefas/$id").body<MsgResponse>()
            Log.d("deleteTarefa", response.toString())
            UiState.Success(response)
        } catch (e: Exception){
            val msgErr = client.delete("$BASE_URL/tarefas/$id").body<ErrorResponse>().error
            Log.e("deleteTarefaErr", e.toString()+msgErr)
            UiState.Error(e, msgErr)
        }
    }

    suspend fun createTarefa(tarefa: TaskRequest): UiState<EditResponse>{
        return try {
            val response = client.post("$BASE_URL/tarefas"){
                contentType(ContentType.Application.Json)
                setBody(tarefa)
            }.body<EditResponse>()
            Log.d("createTarefa", response.toString())
            UiState.Success(response)
        } catch (e: Exception){
            Log.e("createTarefaErr", e.toString())
            UiState.Error(e, "Erro desconhecido")
        }
    }
}