package com.example.boardlog.datos

import androidx.annotation.DrawableRes
import androidx.room.ColumnInfo
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.RoomDatabase
import com.example.boardlog.R
import kotlinx.coroutines.flow.Flow

const val ESTADO_EN_CURSO = "in_progress" // Valor persistido compatible con Room
const val ESTADO_FINALIZADO = "finished" // Valor persistido compatible con Room

/** Catálogo falso: no exige credenciales ni red para usar la app. */
data class JuegoMesa(
    val id: String,
    val nombre: String,
    @DrawableRes val imagen: Int,
    val minimoJugadores: Int,
    val maximoJugadores: Int,
    val descripcion: String
)

object CatalogoJuegos {
    val juegos = listOf(
        JuegoMesa("catan", "Catan", R.drawable.catan, 3, 4, "Comercia, construye y expande tus asentamientos."),
        JuegoMesa("uno", "UNO", R.drawable.uno, 2, 10, "Deshazte de tus cartas antes que los demás."),
        JuegoMesa("risk", "Risk", R.drawable.risk, 2, 6, "Conquista territorios y domina el mundo."),
        JuegoMesa("clue", "Clue", R.drawable.clue, 3, 6, "Investiga pistas hasta descubrir al culpable."),
        JuegoMesa("monopoly", "Monopoly", R.drawable.monopoly, 2, 6, "Compra propiedades, negocia y administra tu dinero."),
        JuegoMesa("ajedrez", "Ajedrez", R.drawable.ajedrez, 2, 2, "Planea tu estrategia para conseguir jaque mate.")
    )
    fun obtener(id: String) = juegos.find { it.id == id }
}

@Entity(tableName = "players")
data class EntidadJugador(
    @PrimaryKey val id: String,
    @ColumnInfo(name = "name") val nombre: String
)

@Entity(tableName = "matches")
data class EntidadPartida(
    @PrimaryKey val id: String,
    @ColumnInfo(name = "gameId") val idJuego: String,
    // Solo IDs, nunca serializar objetos completos en la navegación.
    @ColumnInfo(name = "playerIds") val idsJugadores: String,
    @ColumnInfo(name = "scores") val puntuaciones: String,
    @ColumnInfo(name = "status") val estadoPartida: String, // in_progress | finished
    @ColumnInfo(name = "createdAt") val fechaCreacion: Long,
    @ColumnInfo(name = "finishedAt") val fechaFinalizacion: Long? = null
) {
    fun ids(): List<String> = idsJugadores.split(",").filter { it.isNotBlank() }
    fun puntos(): Map<String, Int> = puntuaciones.split(",").mapNotNull { item ->
        val bits = item.split(":")
        if (bits.size == 2) bits[1].toIntOrNull()?.let { bits[0] to it } else null
    }.toMap()
    fun idGanador(): String? = if (estadoPartida == ESTADO_FINALIZADO) puntos().maxByOrNull { it.value }?.key else null
}

@Dao
interface DaoBoardLog {
    @Query("SELECT * FROM players ORDER BY name")
    fun observarJugadores(): Flow<List<EntidadJugador>>

    @Query("SELECT * FROM matches ORDER BY createdAt DESC")
    fun observarPartidas(): Flow<List<EntidadPartida>>

    @Query("SELECT * FROM matches WHERE id = :id LIMIT 1")
    suspend fun buscarPartida(id: String): EntidadPartida?

    @Query("SELECT COUNT(*) FROM players")
    suspend fun contarJugadores(): Int

    @Query("SELECT COUNT(*) FROM matches")
    suspend fun contarPartidas(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun guardarJugador(jugador: EntidadJugador)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun guardarPartida(partida: EntidadPartida)
}

@Database(entities = [EntidadJugador::class, EntidadPartida::class], version = 1, exportSchema = false)
abstract class BaseDatosBoardLog : RoomDatabase() {
    abstract fun dao(): DaoBoardLog
}
