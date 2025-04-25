package edu.udb.dsm.investigacion_practica.screens

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import com.google.firebase.database.FirebaseDatabase
import edu.udb.dsm.investigacion_practica.R
import edu.udb.dsm.investigacion_practica.entities.Estudiante

@Composable
fun PantallaListaEstudiantes(navController: NavHostController) {
    val estudiantes = remember { mutableStateListOf<Estudiante>() }

    // Obtener los estudiantes desde Firebase al cargar la pantalla
    LaunchedEffect(Unit) {
        val dbRef = FirebaseDatabase.getInstance().getReference("estudiantes")
        dbRef.get().addOnSuccessListener { snapshot ->
            if (snapshot.exists()) {
                estudiantes.clear()
                snapshot.children.forEach {
                    val estudiante = it.getValue(Estudiante::class.java)
                    estudiante?.let { estudiantes.add(it) }
                }
            }
        }.addOnFailureListener {
            // Error en la carga
            //Toast.makeText(this, "Error al cargar estudiantes", Toast.LENGTH_SHORT).show()
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        LazyColumn {
            items(estudiantes) { estudiante ->
                ElementoEstudiante(estudiante, onClick = {
                    //hacia la pantalla de formulario
                    navController.navigate("formulario_estudiante?uid=${estudiante.carnet}")
                })
            }
        }

        Button(onClick = {
            // hacia la pantalla de agregar estudiante
            navController.navigate("formulario_estudiante")
        }) {
            Text("Agregar Estudiante")
        }
    }

}
@Composable
fun ElementoEstudiante(
    estudiante: Estudiante,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Column(modifier = modifier.clickable(onClick = onClick)) {
        Text(text = stringResource(R.string.estudiante_nombre, estudiante.nombre ?: "(nombre)"))
        Text(text = stringResource(R.string.estudiante_carnet, estudiante.carnet ?: "(carnet)"))
    }
}


