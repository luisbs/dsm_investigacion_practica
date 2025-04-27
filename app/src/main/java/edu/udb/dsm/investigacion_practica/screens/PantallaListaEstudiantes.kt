package edu.udb.dsm.investigacion_practica.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.*
import androidx.navigation.NavHostController
import edu.udb.dsm.investigacion_practica.R
import edu.udb.dsm.investigacion_practica.entities.Estudiante
import edu.udb.dsm.investigacion_practica.entities.EstudianteData
import edu.udb.dsm.investigacion_practica.ui.nav.NavRoutes

@Composable
fun PantallaListaEstudiantes(navController: NavHostController) {
    val estudiantes = remember { mutableStateListOf<Estudiante>() }

    LaunchedEffect(Unit) {
        estudiantes.addAll(Estudiante.listar())
    }

    ListaEstudiantes(
        estudiantes,
        onNavigate = { navController.navigate("${NavRoutes.FormularioEstudiante}?uid=${it}") },
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
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "Imagen de cabecera",
            modifier = Modifier
                .size(180.dp)
                .padding(top = 16.dp),
            contentScale = ContentScale.Crop
        )

        Text(
            text = "Estudiantes Actuales",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 16.dp)
        )

        Text(
            text = "Escuela de Ingeniería",
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Button(
            onClick = { onNavigate(null) },
            modifier = Modifier.padding(bottom = 16.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Agregar"
            )
            Text(text = "Agregar Estudiante", modifier = Modifier.padding(start = 8.dp))
        }

        LazyColumn {
            items(estudiantes) { estudiante ->
                HorizontalDivider()
                ElementoEstudiante(
                    estudiante.data,
                    modifier = Modifier.clickable { onNavigate(estudiante.uuid) }
                )
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
                text = "${estudiante.nombre ?: ""} (${estudiante.carnet ?: "sin carnet"})",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = stringResource(
                    R.string.estudiante_contactos,
                    estudiante.telefono ?: "(sin telefono)",
                    estudiante.email ?: "(sin correo)"
                ),
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = stringResource(
                    R.string.estudiante_plan,
                    estudiante.plan ?: "(sin plan)"
                ),
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}
