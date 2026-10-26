package com.example.boardlog.datos

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder

/** Una consulta HTTP real, opcional; la app mantiene las fichas locales sin conexión. */
object InformacionRemotaJuego {
    suspend fun buscarDescripcion(nombreJuego: String): String? = withContext(Dispatchers.IO) {
        runCatching {
            val busqueda = URLEncoder.encode("$nombreJuego juego de mesa", "UTF-8")
            val url = URL("https://www.wikidata.org/w/api.php?action=wbsearchentities&search=$busqueda&language=es&format=json&limit=5")
            val conn = (url.openConnection() as HttpURLConnection).apply {
                requestMethod = "GET"
                connectTimeout = 5000
                readTimeout = 5000
                setRequestProperty("User-Agent", "BoardLog/1.0 (Proyecto educativo Android)")
            }
            try {
                if (conn.responseCode !in 200..299) return@runCatching null
                val json = JSONObject(conn.inputStream.bufferedReader().use { it.readText() })
                val resultados = json.optJSONArray("search") ?: return@runCatching null
                (0 until resultados.length()).mapNotNull { i ->
                    resultados.optJSONObject(i)?.optString("description")?.takeIf { it.isNotBlank() }
                }.firstOrNull()
            } finally { conn.disconnect() }
        }.getOrNull()
    }
}
