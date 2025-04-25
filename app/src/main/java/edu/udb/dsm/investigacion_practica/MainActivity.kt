package edu.udb.dsm.investigacion_practica

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import edu.udb.dsm.investigacion_practica.screens.PantallaFormularioEstudiante
import edu.udb.dsm.investigacion_practica.screens.PantallaListaEstudiantes
import edu.udb.dsm.investigacion_practica.ui.theme.DSMInvestigacionPracticaTheme
import androidx.navigation.NavType

import androidx.navigation.navArgument
import edu.udb.dsm.investigacion_practica.ui.nav.NavRoutes

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DSMInvestigacionPracticaTheme {
                AppNavigation()
            }
        }
    }
}

@Composable
fun AppNavigation() {
    val navController: NavHostController = rememberNavController()

    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = NavRoutes.ListaEstudiantes,
        ) {
            composable(NavRoutes.ListaEstudiantes) {
                PantallaListaEstudiantes(navController)
            }
            composable(
                route = "${NavRoutes.FormularioEstudiante}?uid={uid}",
                arguments = listOf(navArgument("uid") {
                    type = NavType.StringType
                    defaultValue = ""
                })
            ) { backStackEntry ->
                val uid = backStackEntry.arguments?.getString("uid") ?: ""
                PantallaFormularioEstudiante(navController, uid)
            }
        }
    }
}
