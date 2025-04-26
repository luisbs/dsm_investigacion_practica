package edu.udb.dsm.investigacion_practica.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import edu.udb.dsm.investigacion_practica.R
import edu.udb.dsm.investigacion_practica.entities.Estudiante
import edu.udb.dsm.investigacion_practica.entities.EstudianteData

@Composable
fun PantallaListaEstudiantes(navController: NavHostController) {
    val estudiantes = remember { mutableStateListOf<Estudiante>() }

    // Obtener los estudiantes desde Firebase al cargar la pantalla
    LaunchedEffect(Unit) {
        estudiantes.addAll(Estudiante.listar())
    }

    ListaEstudiantes(
        estudiantes,
        onNavigate = { uid ->
            val param = uid.let { "?uid=${uid}" }
            navController.navigate("formulario_estudiante${param}")
        },
        modifier = Modifier.fillMaxSize()
    )
}

@Composable
fun ListaEstudiantes(
    estudiantes: List<Estudiante>,
    onNavigate: (uid: String?) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        Button(onClick = {
            // hacia la pantalla de agregar estudiante
            onNavigate("formulario_estudiante")
        }) {
            Text("Agregar Estudiante")
        }

        Text(
            stringResource(R.string.estudiantes_lista),
            fontSize = 20.sp,
            lineHeight = 3.em,
        )

        LazyColumn {
            items(estudiantes) { estudiante ->
                HorizontalDivider()
                ElementoEstudiante(estudiante.data, modifier = Modifier.clickable {
                    //hacia la pantalla de formulario
                    onNavigate(estudiante.uuid)
                })
            }
        }
    }
}

@Composable
fun ElementoEstudiante(
    estudiante: EstudianteData,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .padding(horizontal = 16.dp, vertical = 8.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = stringResource(
                    R.string.estudiante_identidad,
                    estudiante.carnet ?: "(sin carnet)",
                    estudiante.nombre ?: "(sin nombre)",
                ),
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = stringResource(
                    R.string.estudiante_contactos,
                    estudiante.telefono ?: "(sin telefono)",
                    estudiante.email ?: "(sin correo)",
                ),
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = stringResource(R.string.estudiante_plan, estudiante.plan ?: "(sin plan)"),
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}


