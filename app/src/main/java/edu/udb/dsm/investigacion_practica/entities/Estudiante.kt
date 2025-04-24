package edu.udb.dsm.investigacion_practica.entities

import com.google.android.gms.tasks.Task
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

data class Estudiante(
    var nombre: String? = null,
    var carnet: String? = null,
    var plan: String? = null,
    var email: String? = null,
    var telefono: String? = null,
) {
    companion object {
        fun ref(): DatabaseReference {
            return FirebaseDatabase.getInstance().getReference("estudiantes")
        }

        /** Recuperar un Estudiante usando su UID */
        fun get(uid: String): Task<DataSnapshot> {
            return ref().child(uid).get()
        }

        /** Eliminar un Estudiante usando su UID */
        fun remove(uid: String): Task<Void> {
            return ref().child(uid).removeValue()
        }
    }

    /**
     * Insertar al Estudiante en la base de datos.
     * Si un UID es proveido los datos del Estudiante seran actualizados.
     */
    fun store(uid: String? = null): Task<Void> {
        return if (uid !== null) ref().child(uid).setValue(this)
        else ref().push().setValue(this)
    }
}