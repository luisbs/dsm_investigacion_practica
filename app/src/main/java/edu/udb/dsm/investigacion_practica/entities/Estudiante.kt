package edu.udb.dsm.investigacion_practica.entities

import com.google.android.gms.tasks.Task
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.tasks.await

data class EstudianteData(
    var nombre: String? = null,
    var carnet: String? = null,
    var email: String? = null,
    var telefono: String? = null,
    var plan: String? = null,
)

data class Estudiante(
    var data: EstudianteData,
    var uuid: String? = null,
) {
    companion object {
        fun ref(): DatabaseReference {
            return FirebaseDatabase.getInstance().getReference("estudiantes")
        }

        suspend fun listar(): List<Estudiante> {
            val snapshot = ref().get().await()
            if (!snapshot.hasChildren()) return listOf()

            val estudiantes = mutableListOf<Estudiante>()
            snapshot.children.forEach { child ->
                child.getValue(EstudianteData::class.java)?.let {
                    estudiantes.add(Estudiante(it, child.key))
                }
            }
            return estudiantes
        }

        /** Recuperar un Estudiante usando su UID */
        suspend fun obtener(uid: String): Estudiante? {
            val data = ref().child(uid).get().await().getValue(EstudianteData::class.java)
            return data?.let { Estudiante(it, uid) }
        }

        /** Eliminar un Estudiante usando su UID */
        fun eliminar(uid: String): Task<Void> {
            return ref().child(uid).removeValue()
        }
    }

    /**
     * Insertar al Estudiante en la base de datos.
     * Si un UID es proveido los datos del Estudiante seran actualizados.
     */
    fun guardar(newUuid: String? = null) {
        if (newUuid !== null) ref().child(newUuid).setValue(data)
        else if (uuid !== null) ref().child(uuid!!).setValue(data)
        else ref().push().setValue(data)
    }
}