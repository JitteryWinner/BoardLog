package com.example.boardlog.navegacion

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.boardlog.datos.RepositorioBoardLog
import com.example.boardlog.funcionalidades.confirmacion.PantallaConfirmacion
import com.example.boardlog.funcionalidades.detallepartida.PantallaDetallePartida
import com.example.boardlog.funcionalidades.estadisticas.PantallaEstadisticas
import com.example.boardlog.funcionalidades.historial.PantallaHistorial
import com.example.boardlog.funcionalidades.informacionjuego.PantallaInformacionJuego
import com.example.boardlog.funcionalidades.inicio.PantallaInicio
import com.example.boardlog.funcionalidades.juegos.PantallaJuegos
import com.example.boardlog.funcionalidades.jugadores.PantallaJugadores
import com.example.boardlog.funcionalidades.partidaencurso.PantallaPartidaEnCurso
import com.example.boardlog.funcionalidades.perfiljugador.PantallaPerfilJugador
import com.example.boardlog.funcionalidades.presentacion.PantallaPresentacion
import com.example.boardlog.funcionalidades.resultados.PantallaResultados
import com.example.boardlog.funcionalidades.seleccionjuego.PantallaSeleccionJuego
import com.example.boardlog.funcionalidades.seleccionjugadores.PantallaSeleccionJugadores
import com.example.boardlog.funcionalidades.sesion.PantallaSesion

@Composable
fun NavegacionBoardLog(repositorio: RepositorioBoardLog) {
    val controladorNavegacion = rememberNavController()
    val sesionLista by repositorio.sesionLista.collectAsStateWithLifecycle()
    val cuenta by repositorio.cuentaActiva.collectAsStateWithLifecycle()
    if (!sesionLista) {
        androidx.compose.material3.CircularProgressIndicator(); return
    }

    val abrir: (Destino) -> Unit = { destino -> controladorNavegacion.abrirDestino(destino) }
    val volver: () -> Unit = { controladorNavegacion.popBackStack() }
    NavHost(
        navController = controladorNavegacion,
        startDestination = Destino.Presentacion,
        // Cambio directo de pantallas, sin efecto de desvanecimiento ni animaciones al retroceder.
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None },
        popEnterTransition = { EnterTransition.None },
        popExitTransition = { ExitTransition.None }
    ) {
        composable<Destino.Presentacion> {
            PantallaPresentacion(irA = {
                controladorNavegacion.navigate(if (cuenta == null) Destino.Sesion else Destino.Inicio) {
                    popUpTo<Destino.Presentacion> { inclusive = true }
                    launchSingleTop = true
                }
            })
        }
        composable<Destino.Sesion> {
            PantallaSesion(repositorio) {
                controladorNavegacion.navigate(Destino.Inicio) {
                    popUpTo<Destino.Sesion> {
                        inclusive = true
                    }
                }
            }
        }
        composable<Destino.Inicio> {
            PantallaInicio(repositorio, abrir, alSalir = {
                controladorNavegacion.navigate(Destino.Sesion) {
                    popUpTo<Destino.Inicio> {
                        inclusive = true
                    }
                }
            })
        }
        composable<Destino.Juegos> { PantallaJuegos(abrir) }
        composable<Destino.InformacionJuego> { entry ->
            PantallaInformacionJuego(
                repositorio,
                entry.toRoute<Destino.InformacionJuego>().idJuego,
                abrir,
                volver
            )
        }
        composable<Destino.Jugadores> { PantallaJugadores(repositorio, abrir) }
        composable<Destino.PerfilJugador> { entry ->
            PantallaPerfilJugador(
                repositorio,
                entry.toRoute<Destino.PerfilJugador>().idJugador,
                abrir,
                volver
            )
        }
        composable<Destino.SeleccionJuego> { PantallaSeleccionJuego(repositorio, abrir, volver) }
        composable<Destino.SeleccionJugadores> { entry ->
            PantallaSeleccionJugadores(
                repositorio,
                entry.toRoute<Destino.SeleccionJugadores>().idJuego,
                abrir,
                volver
            )
        }
        composable<Destino.ConfirmacionPartida> { entry ->
            PantallaConfirmacion(
                repositorio,
                entry.toRoute<Destino.ConfirmacionPartida>().idJuego,
                abrir,
                volver
            )
        }
        composable<Destino.PartidaEnCurso> { entry ->
            PantallaPartidaEnCurso(
                repositorio,
                entry.toRoute<Destino.PartidaEnCurso>().idPartida,
                abrir,
                volver
            )
        }
        composable<Destino.Resultados> { entry ->
            PantallaResultados(
                repositorio,
                entry.toRoute<Destino.Resultados>().idPartida,
                abrir
            )
        }
        composable<Destino.Historial> { PantallaHistorial(repositorio, abrir) }
        composable<Destino.DetallePartida> { entry ->
            PantallaDetallePartida(
                repositorio,
                entry.toRoute<Destino.DetallePartida>().idPartida,
                abrir,
                volver
            )
        }
        composable<Destino.Estadisticas> { PantallaEstadisticas(repositorio, abrir) }
    }
}

/** Nunca construir strings de rutas: siempre se pasa una instancia @Serializable. */
private fun NavHostController.abrirDestino(destino: Destino) {
    when (destino) {
        Destino.Sesion -> navigate(Destino.Sesion) { launchSingleTop = true }
        Destino.Presentacion -> navigate(Destino.Presentacion) { launchSingleTop = true }
        Destino.Inicio -> navigate(Destino.Inicio) {
            popUpTo<Destino.Inicio> {
                inclusive = false
            }; launchSingleTop = true
        }

        Destino.Juegos -> navigate(Destino.Juegos) {
            popUpTo<Destino.Inicio> {
                inclusive = false
            }; launchSingleTop = true
        }

        is Destino.InformacionJuego -> navigate(destino)
        Destino.Jugadores -> navigate(Destino.Jugadores) { launchSingleTop = true }
        is Destino.PerfilJugador -> navigate(destino)
        Destino.SeleccionJuego -> navigate(Destino.SeleccionJuego) { launchSingleTop = true }
        is Destino.SeleccionJugadores -> navigate(destino)
        is Destino.ConfirmacionPartida -> navigate(destino)
        is Destino.PartidaEnCurso -> navigate(destino)
        is Destino.Resultados -> navigate(destino)
        Destino.Historial -> navigate(Destino.Historial) {
            popUpTo<Destino.Inicio> {
                inclusive = false
            }; launchSingleTop = true
        }

        is Destino.DetallePartida -> navigate(destino)
        Destino.Estadisticas -> navigate(Destino.Estadisticas) {
            popUpTo<Destino.Inicio> {
                inclusive = false
            }; launchSingleTop = true
        }

        else -> Unit // Seguridad para nuevas variantes de Destino en el futuro.
    }
}
