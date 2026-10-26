package com.example.boardlog

import android.app.Application
import com.example.boardlog.datos.RepositorioBoardLog

class AplicacionBoardLog : Application() {
    val repositorio: RepositorioBoardLog by lazy { RepositorioBoardLog(this) }
}
