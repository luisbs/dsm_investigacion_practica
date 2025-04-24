package edu.udb.dsm.investigacion_practica

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ListItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import edu.udb.dsm.investigacion_practica.entities.Estudiante
import edu.udb.dsm.investigacion_practica.ui.theme.DSMInvestigacionPracticaTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DSMInvestigacionPracticaTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android", modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }

//        Recycler
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!", modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    DSMInvestigacionPracticaTheme {
        Greeting("Android")
    }
}

@Composable
fun ListaEstudiantes(
    estudiantes: Array<Estudiante>,
    modifier: Modifier = Modifier,
) {
    LazyColumn(modifier = modifier) {
        itemsIndexed(estudiantes) { index, estudiante ->
            ElementoEstudiante(estudiante)
            // separador de decoracion
            if (index != estudiantes.size - 1) HorizontalDivider()
        }
    }
}

@Composable
fun ElementoEstudiante(
    estudiante: Estudiante,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        Text(text = stringResource(R.string.estudiante_nombre, estudiante.nombre ?: "(nombre)"))
        Text(text = stringResource(R.string.estudiante_carnet, estudiante.carnet ?: "(carnet)"))
    }
}

@Preview
@Composable
fun ListaEstudiantesPreview() {
    DSMInvestigacionPracticaTheme {
        ListaEstudiantes(arrayOf(
            Estudiante("Alfredo Abelardo", "AA000000"),
            Estudiante("Bryan Bermudez", "BB0123456"),
            Estudiante("Carlos Castro", "CC987654"),
        ))
    }
}