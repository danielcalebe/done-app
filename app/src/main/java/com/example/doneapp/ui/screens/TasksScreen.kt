package com.example.doneapp.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.doneapp.R
import com.example.doneapp.data.UiState
import com.example.doneapp.data.models.Task
import com.example.doneapp.data.models.TaskRequest
import kotlinx.coroutines.delay


@Composable
fun TasksScreen(
    showAddDialog: Boolean,
    onShowDialogChange: (Boolean) -> Unit,
    tasksViewModel: TasksViewModel
) {

    val tarefas by tasksViewModel.tasks.collectAsStateWithLifecycle()
    LaunchedEffect(Unit) {
        tasksViewModel.getTarefas()
    }

    Column(
        Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp)
    ) {
        Image(
            painter = painterResource(R.drawable.logo),
            null,
            modifier = Modifier
                .width(120.dp)
                .height(80.dp)
        )
        Text(
            "Your To-do list",
            style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Light)
        )


        tarefas?.let {
            when (it) {
                is UiState.Error -> {
                    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text(it.msg)
                    }
                }

                UiState.Loading -> {
                    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
                    }
                }

                is UiState.Success<List<Task>> -> {


                    LazyColumn(
                        Modifier
                            .fillMaxWidth()
                            .padding(top = 12.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        items(it.result) { it ->
                            var showEditDialog by remember { mutableStateOf(false) }
                            var showDeleteDialog by remember { mutableStateOf(false) }

                            TaskItem(it, { showEditDialog = it }, { showDeleteDialog = it })
                            if (showDeleteDialog) {
                                AppDialog(
                                    close = { showDeleteDialog = false },
                                    dismissButtonLabel = "Cancelar",
                                    confirmButtonLabel = "Confirmar",
                                    title = "Excluir tarefa",
                                    action = {
                                        tasksViewModel.deleteTarefa(it.id)
                                    }
                                ) {
                                    Text("Tem certeza que deseja excluir a tarefa ${it.titulo}?")
                                }
                            }




                            if (showEditDialog) {
                                AppDialog(
                                    close = { showEditDialog = false },
                                    dismissButtonLabel = "Cancelar",
                                    confirmButtonLabel = "Salvar",
                                    title = "Editar tarefa"
                                ) {
                                    var titulo by remember { mutableStateOf(it.titulo) }
                                    var descricao by remember { mutableStateOf(it.descricao) }
                                    Column() {
                                        OutlinedTextField(
                                            value = titulo,
                                            onValueChange = { titulo = it },
                                            label = { Text("Título") }
                                        )
                                        OutlinedTextField(
                                            value = descricao,
                                            onValueChange = { descricao = it },
                                            label = { Text("Descrição") }
                                        )
                                    }
                                }
                            }
                        }
                    }

                    if (showAddDialog) {


                        var titulo by remember { mutableStateOf("") }
                        var descricao by remember { mutableStateOf("") }

                        AppDialog(
                            close = { onShowDialogChange(false) },
                            dismissButtonLabel = "Cancelar",
                            confirmButtonLabel = "Salvar",
                            title = "Criar tarefa",
                            action = {
                                tasksViewModel.createTarefa(
                                    TaskRequest(
                                        titulo = titulo,
                                        descricao = descricao
                                    )
                                )
                               onShowDialogChange(false)
                            }
                        ) {

                            Column() {
                                OutlinedTextField(
                                    value = titulo,
                                    onValueChange = { titulo = it },
                                    label = { Text("Título") }
                                )
                                OutlinedTextField(
                                    value = descricao,
                                    onValueChange = { descricao = it },
                                    label = { Text("Descrição") }
                                )
                            }
                        }
                    }

                }


            }
        }
    }
}


@Composable
fun AppDialog(
    close: () -> Unit,
    dismissButtonLabel: String,
    confirmButtonLabel: String,
    title: String,
    action: () -> Unit = {},
    content: @Composable () -> Unit,

    ) {
    AlertDialog(
        shape = RoundedCornerShape(12.dp),
        containerColor = MaterialTheme.colorScheme.primaryContainer,
        titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
        onDismissRequest = { close() },
        dismissButton = {
            Button(
                onClick = {
                    close()
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    contentColor = MaterialTheme.colorScheme.primary
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(dismissButtonLabel)
            }
        },
        confirmButton = {
            Button(
                shape = RoundedCornerShape(12.dp), onClick = { action() }) {
                Text(confirmButtonLabel)
            }
        },
        title = { Text(title, fontWeight = FontWeight.Bold) },
        text = { content() }
    )
}

@Composable
fun TaskItem(
    task: Task,
    onEditDialogChange: (Boolean) -> Unit,
    onDeleteDialogChange: (Boolean) -> Unit
) {

    var expanded by remember { mutableStateOf(false) }
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    task.titulo,
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                )
                Row(
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    FilledIconButton(
                        onClick = {
                            if (task.concluida) {

                            } else {

                            }
                        },
                        colors = IconButtonDefaults.iconButtonColors(

                            containerColor = if (task.concluida) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.background,
                            contentColor = MaterialTheme.colorScheme.primaryContainer
                        ),
                        shape = RoundedCornerShape(4.dp),
                        modifier = Modifier
                            .border(
                                2.dp,
                                MaterialTheme.colorScheme.primary,
                                RoundedCornerShape(4.dp)
                            )
                            .size(28.dp)
                    ) {
                        if (task.concluida)
                            Icon(
                                Icons.Default.Check,
                                contentDescription = null
                            )
                    }


                    IconButton(
                        modifier = Modifier.size(28.dp),
                        onClick = {
                            onDeleteDialogChange(true)
                        }
                    ) {
                        Icon(
                            Icons.Default.Delete,
                            null,
                            tint = MaterialTheme.colorScheme.error
                        )
                    }

                    IconButton(
                        modifier = Modifier.size(28.dp),
                        onClick = {
                            onEditDialogChange(true)
                        }
                    ) {
                        Icon(
                            Icons.Default.Edit,
                            null,
                            tint = MaterialTheme.colorScheme.secondary
                        )
                    }

                    IconButton(
                        modifier = Modifier.size(28.dp),
                        onClick = {
                            expanded = !expanded
                        }
                    ) {
                        Icon(
                            if (!expanded) Icons.Default.KeyboardArrowDown else Icons.Default.KeyboardArrowUp,
                            null,
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }

                }
            }



            AnimatedVisibility(expanded) {
                Text(task.descricao, modifier = Modifier.padding(top = 4.dp))
            }


        }
    }

}