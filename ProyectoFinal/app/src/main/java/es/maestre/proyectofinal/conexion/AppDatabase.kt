package es.maestre.proyectofinal.conexion

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import es.maestre.proyectofinal.model.Cita

// Defino mi base de datos, le digo qué tablas tiene y la versión.
@Database(entities = [Cita::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    // Función para acceder a las operaciones de la tabla de Citas.
    abstract fun citaDAO(): CitaDAO

    // Uso un companion object para que la base de datos sea accesible desde toda la app.
    companion object {
        // Aquí guardo la única instancia de mi base de datos para no crearla varias veces.
        @Volatile
        private var INSTANCE: AppDatabase? = null

        // Esta es la función que uso para llamar la base de datos desde cualquier sitio.
        fun getDatabase(context: Context): AppDatabase {
            // Si la base de datos ya existe, la devuelvo. Si no, la creo.
            return INSTANCE ?: synchronized(this) {
                // Este bloque se asegura de que, si varios hilos intentan crear la base de datos
                // a la vez, solo uno lo consiga, evitando errores.
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "citas.db"
                ).build()
                // Guardo la base de datos recién creada en mi INSTANCE.
                INSTANCE = instance
                // La devuelvo.
                instance
            }
        }
    }
}
