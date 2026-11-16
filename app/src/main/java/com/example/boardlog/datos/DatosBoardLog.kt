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
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
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
        JuegoMesa(
            "catan",
            "Catan",
            R.drawable.catan,
            3,
            4,
            "Comercia, construye y expande tus asentamientos."
        ),
        JuegoMesa(
            "uno",
            "UNO",
            R.drawable.uno,
            2,
            10,
            "Deshazte de tus cartas antes que los demás."
        ),
        JuegoMesa(
            "risk",
            "Risk",
            R.drawable.risk,
            2,
            6,
            "Conquista territorios y domina el mundo."
        ),
        JuegoMesa(
            "clue",
            "Clue",
            R.drawable.clue,
            3,
            6,
            "Investiga pistas hasta descubrir al culpable."
        ),
        JuegoMesa(
            "monopoly",
            "Monopoly",
            R.drawable.monopoly,
            2,
            6,
            "Compra propiedades, negocia y administra tu dinero."
        ),
        JuegoMesa(
            "ajedrez",
            "Ajedrez",
            R.drawable.ajedrez,
            2,
            2,
            "Planea tu estrategia para conseguir jaque mate."
        )
    )

    fun obtener(id: String) = juegos.find { it.id == id }
}

@Entity(tableName = "players")
data class EntidadJugador(
    @PrimaryKey val id: String,
    @ColumnInfo(name = "name") val nombre: String,
    @ColumnInfo(name = "ownerId", defaultValue = "'legacy'") val idPropietario: String = "legacy",
    @ColumnInfo(name = "archived", defaultValue = "0") val archivado: Boolean = false
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
    @ColumnInfo(name = "finishedAt") val fechaFinalizacion: Long? = null,
    @ColumnInfo(name = "ownerId", defaultValue = "'legacy'") val idPropietario: String = "legacy"
) {
    fun ids(): List<String> = idsJugadores.split(",").filter { it.isNotBlank() }
    fun puntos(): Map<String, Int> = puntuaciones.split(",").mapNotNull { item ->
        val bits = item.split(":")
        if (bits.size == 2) bits[1].toIntOrNull()?.let { bits[0] to it } else null
    }.toMap()

    fun idGanador(): String? {
        if (estadoPartida != ESTADO_FINALIZADO) return null
        val ranking = ReglasPuntuacion.ordenar(this)
        if (ranking.size < 2 || ranking[0].second == ranking[1].second) return null
        return ranking.first().first
    }
}

/** Una fila por ronda y jugador. Permite reconstruir el historial de puntuaciones. */
@Entity(tableName = "score_rounds")
data class EntidadRonda(
    @PrimaryKey val id: String,
    @ColumnInfo(name = "matchId") val idPartida: String,
    @ColumnInfo(name = "playerId") val idJugador: String,
    @ColumnInfo(name = "roundNumber") val numeroRonda: Int,
    @ColumnInfo(name = "points") val puntos: Int,
    @ColumnInfo(name = "createdAt") val fecha: Long
)

@Entity(tableName = "accounts")
data class EntidadCuenta(
    @PrimaryKey val id: String,
    @ColumnInfo(name = "username") val usuario: String,
    @ColumnInfo(name = "pinHash") val hashPin: String,
    @ColumnInfo(name = "salt") val sal: String
)

@Dao
interface DaoBoardLog {
    @Query("SELECT * FROM accounts WHERE username = :usuario LIMIT 1")
    suspend fun buscarCuenta(usuario: String): EntidadCuenta?

    @Query("SELECT * FROM accounts WHERE id = :id LIMIT 1")
    suspend fun buscarCuentaId(id: String): EntidadCuenta?

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun guardarCuenta(cuenta: EntidadCuenta)

    @Query("SELECT * FROM players ORDER BY name")
    fun observarJugadores(): Flow<List<EntidadJugador>>

    @Query("SELECT * FROM matches ORDER BY createdAt DESC")
    fun observarPartidas(): Flow<List<EntidadPartida>>

    @Query("SELECT * FROM matches WHERE id = :id LIMIT 1")
    suspend fun buscarPartida(id: String): EntidadPartida?

    @Query("SELECT * FROM score_rounds ORDER BY roundNumber ASC, createdAt ASC")
    fun observarRondas(): Flow<List<EntidadRonda>>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun guardarRondas(rondas: List<EntidadRonda>)

    @Query("UPDATE players SET name = :nombre WHERE id = :id AND ownerId = :cuenta")
    suspend fun renombrarJugador(id: String, cuenta: String, nombre: String): Int

    @Query("UPDATE players SET archived = 1 WHERE id = :id AND ownerId = :cuenta")
    suspend fun archivarJugador(id: String, cuenta: String): Int

    @Query("SELECT MAX(roundNumber) FROM score_rounds WHERE matchId = :idPartida")
    suspend fun ultimaRonda(idPartida: String): Int?

    @Query("SELECT COUNT(*) FROM players")
    suspend fun contarJugadores(): Int

    @Query("SELECT COUNT(*) FROM matches")
    suspend fun contarPartidas(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun guardarJugador(jugador: EntidadJugador)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun guardarPartida(partida: EntidadPartida)
}

@Database(
    entities = [EntidadJugador::class, EntidadPartida::class, EntidadCuenta::class, EntidadRonda::class],
    version = 3,
    exportSchema = false
)
abstract class BaseDatosBoardLog : RoomDatabase() {
    abstract fun dao(): DaoBoardLog
}


/** Conserva las partidas y jugadores existentes en una cuenta local "Anterior". */
val MIGRACION_1_2 = object : Migration(1, 2) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL("ALTER TABLE players ADD COLUMN ownerId TEXT NOT NULL DEFAULT 'legacy'")
        db.execSQL("ALTER TABLE matches ADD COLUMN ownerId TEXT NOT NULL DEFAULT 'legacy'")
        db.execSQL("CREATE TABLE IF NOT EXISTS accounts (id TEXT NOT NULL PRIMARY KEY, username TEXT NOT NULL, pinHash TEXT NOT NULL, salt TEXT NOT NULL)")
    }
}

/** Migración no destructiva: conserva todas las partidas y perfiles existentes. */
val MIGRACION_2_3 = object : Migration(2, 3) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL("ALTER TABLE players ADD COLUMN archived INTEGER NOT NULL DEFAULT 0")
        db.execSQL("CREATE TABLE IF NOT EXISTS score_rounds (id TEXT NOT NULL PRIMARY KEY, matchId TEXT NOT NULL, playerId TEXT NOT NULL, roundNumber INTEGER NOT NULL, points INTEGER NOT NULL, createdAt INTEGER NOT NULL)")
    }
}
