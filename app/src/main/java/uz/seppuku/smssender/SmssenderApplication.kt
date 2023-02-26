package uz.seppuku.smssender

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import uz.seppuku.smssender.di.appModule

class SmssenderApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            // Reference Android context
            androidContext(this@SmssenderApplication)
            modules(appModule)
        }
    }
}