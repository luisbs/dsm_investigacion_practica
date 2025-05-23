package edu.udb.dsm.investigacion_practica.screens

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.*
import androidx.navigation.NavHostController
import edu.udb.dsm.investigacion_practica.entities.Estudiante
import edu.udb.dsm.investigacion_practica.entities.EstudianteData

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaFormularioEstudiante(navController: NavHostController, uid: String?) {
    val context = LocalContext.current

    val nombre = remember { mutableStateOf(TextFieldValue()) }
    val carnet = remember { mutableStateOf(TextFieldValue()) }
    val plan = remember { mutableStateOf("") }
    val email = remember { mutableStateOf(TextFieldValue()) }
    val telefono = remember { mutableStateOf(TextFieldValue()) }

    val planOptions = listOf("2017", "2023")
    var expanded by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        if (uid.isNullOrBlank()) return@LaunchedEffect
        Estudiante.obtener(uid)?.data?.let {
            nombre.value = TextFieldValue(it.nombre ?: "")
            carnet.value = TextFieldValue(it.carnet ?: "")
            plan.value = it.plan ?: ""
            email.value = TextFieldValue(it.email ?: "")
            telefono.value = TextFieldValue(it.telefono ?: "")
        }
    }

    Surface(
        modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
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
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Bold
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

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                    ) {
                        ExposedDropdownMenuBox(expanded = expanded,
                            onExpandedChange = { expanded = !expanded }) {
                            OutlinedTextField(value = plan.value,
                                onValueChange = { },
                                readOnly = true,
                                label = { Text("Plan") },
                                trailingIcon = {
                                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                                },
                                modifier = Modifier
                                    .menuAnchor()
                                    .fillMaxWidth(),
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

                            ExposedDropdownMenu(expanded = expanded,
                                onDismissRequest = { expanded = false }) {
                                planOptions.forEach { selectionOption ->
                                    DropdownMenuItem(text = { Text(selectionOption) }, onClick = {
                                        plan.value = selectionOption
                                        expanded = false
                                    })
                                }
                            }
                        }
                    }

                    CampoTexto(label = "Email", valor = email.value) { email.value = it }
                    CampoTexto(label = "Teléfono", valor = telefono.value) { telefono.value = it }

                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = {
                            if (nombre.value.text.isBlank()) {
                                Toast.makeText(context, "❌ El nombre no puede estar vacío", Toast.LENGTH_SHORT).show()
                                return@Button
                            }
                            if (carnet.value.text.isBlank()||
                                carnet.value.text.length != 8) {
                                Toast.makeText(context, "❌ El carnet no puede estar vacío y deben ser 8 caracteres", Toast.LENGTH_SHORT).show()
                                return@Button
                            }
                            if (plan.value.isBlank()) {
                                Toast.makeText(context, "❌ Debes seleccionar un plan", Toast.LENGTH_SHORT).show()
                                return@Button
                            }
                            if (email.value.text.isBlank() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email.value.text).matches()) {
                                Toast.makeText(context, "❌ Email inválido", Toast.LENGTH_SHORT).show()
                                return@Button
                            }
                            if (telefono.value.text.isBlank() ||
                                telefono.value.text.length != 8 ||
                                !telefono.value.text.all { it.isDigit() }) {

                                Toast.makeText(context, "❌ Teléfono inválido deben ser 8 digitos", Toast.LENGTH_SHORT).show()
                                return@Button
                            }



                            val data = EstudianteData(
                                nombre = nombre.value.text,
                                carnet = carnet.value.text,
                                plan = plan.value,
                                email = email.value.text,
                                telefono = telefono.value.text
                            )
                            Estudiante(data, uid).guardar()
                            navController.popBackStack()
                        }, modifier = Modifier.fillMaxWidth(), colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary,
                            contentColor = Color.White
                        )
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Check,
                            contentDescription = "Guardar",
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Guardar")
                    }

                    //si esta editando, muestra el boton de eliminar verficando "uid"
                    // Codigo para borrar cuando se esta editando


                    if (!uid.isNullOrEmpty()) {
                        Spacer(modifier = Modifier.height(8.dp))

                        Button(
                            onClick = {  Estudiante.eliminar(uid).addOnSuccessListener {
                                navController.popBackStack()
                            }
                                      },
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFFB00020),
                                contentColor = Color.White
                            )
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Close,
                                contentDescription = "Borrar",
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Borrar")
                        }
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
