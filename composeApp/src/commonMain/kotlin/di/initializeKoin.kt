package di

import org.koin.core.KoinApplication
import org.koin.core.context.startKoin

fun initializeKoin(config: (KoinApplication.() -> Unit)? = null) {
    startKoin {
        config?.invoke(this)
        modules(dataModule, domainModule, appModule)
    }
}
