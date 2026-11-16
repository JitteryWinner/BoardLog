package com.example.boardlog.datos

/** Reglas de registro de puntos de cada juego (configuración simplificada de BoardLog). */
data class ReglaPuntuacion(
    val unidad: String,
    val paso: Int,
    val mayorGana: Boolean,
    val descripcion: String,
    val maximo: Int? = null
)

object ReglasPuntuacion {
    fun obtener(idJuego: String): ReglaPuntuacion = when (idJuego) {
        "catan" -> ReglaPuntuacion(
            "puntos de victoria",
            1,
            true,
            "Gana quien alcance más puntos de victoria."
        )

        "uno" -> ReglaPuntuacion(
            "puntos de penalización",
            5,
            false,
            "Registra penalizaciones de cada ronda; gana quien acumule menos puntos."
        )

        "risk" -> ReglaPuntuacion(
            "territorios",
            1,
            true,
            "Registra territorios controlados; gana quien controle más."
        )

        "clue" -> ReglaPuntuacion(
            "aciertos",
            1,
            true,
            "Registra acusaciones acertadas; gana quien tenga más."
        )

        "monopoly" -> ReglaPuntuacion(
            "dinero ($)",
            100,
            true,
            "Registra el patrimonio final estimado; gana quien acumule más."
        )

        "ajedrez" -> ReglaPuntuacion(
            "puntos",
            1,
            true,
            "Elige ganador (1 punto) o tablas (0 a 0).",
            maximo = 1
        )

        else -> ReglaPuntuacion("puntos", 1, true, "Gana quien obtenga más puntos.")
    }

    fun ordenar(partida: EntidadPartida) = if (obtener(partida.idJuego).mayorGana)
        partida.puntos().toList().sortedByDescending { it.second }
    else partida.puntos().toList().sortedBy { it.second }
}
