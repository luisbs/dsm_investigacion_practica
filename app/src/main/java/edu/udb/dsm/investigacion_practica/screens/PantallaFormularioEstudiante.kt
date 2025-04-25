package edu.udb.dsm.investigacion_practica.screens

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import com.google.firebase.database.FirebaseDatabase
import edu.udb.dsm.investigacion_practica.entities.Estudiante


import androidx.navigation.NavHostController

@Composable
fun PantallaFormularioEstudiante(navController: NavHostController, uid: String?) {
    val nombre = remember { mutableStateOf(TextFieldValue()) }
    val carnet = remember { mutableStateOf(TextFieldValue()) }
    val plan = remember { mutableStateOf(TextFieldValue()) }
    val email = remember { mutableStateOf(TextFieldValue()) }
    val telefono = remember { mutableStateOf(TextFieldValue()) }

    // si parametro uid no esta vacio
    if (!uid.isNullOrEmpty()) {
        val dbRef = FirebaseDatabase.getInstance().getReference("estudiantes").child(uid)
        dbRef.get().addOnSuccessListener { snapshot ->
            val estudiante = snapshot.getValue(Estudiante::class.java)
            estudiante?.let {
                nombre.value = TextFieldValue(it.nombre ?: "")
                carnet.value = TextFieldValue(it.carnet ?: "")
                plan.value = TextFieldValue(it.plan ?: "")
                email.value = TextFieldValue(it.email ?: "")
                telefono.value = TextFieldValue(it.telefono ?: "")
            }
        }
    }

    Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
        Text("Formulario de Estudiante")

        // Campos de entrada
        TextField(value = nombre.value, onValueChange = { nombre.value = it }, label = { Text("Nombre") })
        TextField(value = carnet.value, onValueChange = { carnet.value = it }, label = { Text("Carnet") })
        TextField(value = plan.value, onValueChange = { plan.value = it }, label = { Text("Plan") })
        TextField(value = email.value, onValueChange = { email.value = it }, label = { Text("Email") })
        TextField(value = telefono.value, onValueChange = { telefono.value = it }, label = { Text("Teléfono") })

        Button(onClick = {
            // Crear o actualizar el estudiante
            val estudiante = Estudiante(
                nombre = nombre.value.text,
                carnet = carnet.value.text,
                plan = plan.value.text,
                email = email.value.text,
                telefono = telefono.value.text
            )
            if (uid.isNullOrEmpty()) {
                // Crear nuevo estudiante
                FirebaseDatabase.getInstance().getReference("estudiantes").push().setValue(estudiante)
                    .addOnSuccessListener {
                       // Toast.makeText(this, "Estudiante creado", Toast.LENGTH_SHORT).show()
                        navController.popBackStack()
                    }
            } else {
                // Actualizar estudiante
                FirebaseDatabase.getInstance().getReference("estudiantes").child(uid).setValue(estudiante)
                    .addOnSuccessListener {
                        //Toast.makeText(context, "Estudiante actualizado", Toast.LENGTH_SHORT).show()
                        navController.popBackStack()
                    }
            }
        }) {
            Text("Guardar")
        }
    }
}
