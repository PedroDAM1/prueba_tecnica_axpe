package com.pedropelayo.prueba_tecnica.data.utils

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class Dispatcher (val dispatcher : AppDispatchers)

enum class AppDispatchers{
    IO
}