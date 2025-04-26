package edu.udb.dsm.investigacion_practica.screens

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavHostController
import edu.udb.dsm.investigacion_practica.entities.Estudiante
import edu.udb.dsm.investigacion_practica.entities.EstudianteData
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth

@Composable
fun PantallaFormularioEstudiante(navController: NavHostController, uid: String?) {
    val nombre = remember { mutableStateOf(TextFieldValue()) }
    val carnet = remember { mutableStateOf(TextFieldValue()) }
    val plan = remember { mutableStateOf(TextFieldValue()) }
    val email = remember { mutableStateOf(TextFieldValue()) }
    val telefono = remember { mutableStateOf(TextFieldValue()) }

    // Cargar datos si hay UID
    LaunchedEffect(Unit) {
        if (uid.isNullOrEmpty()) return@LaunchedEffect
        Estudiante.obtener(uid)?.data?.let {
            nombre.value = TextFieldValue(it.nombre ?: "")
            carnet.value = TextFieldValue(it.carnet ?: "")
            plan.value = TextFieldValue(it.plan ?: "")
            email.value = TextFieldValue(it.email ?: "")
            telefono.value = TextFieldValue(it.telefono ?: "")
        }
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = if (uid.isNullOrEmpty()) "Agregar Estudiante" else "Editar Estudiante",
                style = MaterialTheme.typography.titleLarge,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(16.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                ),
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    CampoTexto(label = "Nombre", valor = nombre.value) { nombre.value = it }
                    CampoTexto(label = "Carnet", valor = carnet.value) { carnet.value = it }
                    CampoTexto(label = "Plan", valor = plan.value) { plan.value = it }
                    CampoTexto(label = "Email", valor = email.value) { email.value = it }
                    CampoTexto(label = "Teléfono", valor = telefono.value) { telefono.value = it }

                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = {
                            val data = EstudianteData(
                                nombre = nombre.value.text,
                                carnet = carnet.value.text,
                                plan = plan.value.text,
                                email = email.value.text,
                                telefono = telefono.value.text
                            )
                            Estudiante(data, uid).guardar()
                            navController.popBackStack()
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Guardar")
                    }
                }
            }
        }
    }
}

@Composable
fun CampoTexto(label: String, valor: TextFieldValue, onValueChange: (TextFieldValue) -> Unit) {
    OutlinedTextField(
        value = valor,
        onValueChange = onValueChange,
        label = { Text(label) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = MaterialTheme.colorScheme.surface,
            unfocusedContainerColor = MaterialTheme.colorScheme.surface,
            disabledContainerColor = MaterialTheme.colorScheme.surface,
            focusedIndicatorColor = MaterialTheme.colorScheme.primary,
            unfocusedIndicatorColor = MaterialTheme.colorScheme.outline,
            focusedLabelColor = MaterialTheme.colorScheme.primary,
            unfocusedLabelColor = MaterialTheme.colorScheme.onSurfaceVariant,
            cursorColor = MaterialTheme.colorScheme.primary
        )
    )
}


