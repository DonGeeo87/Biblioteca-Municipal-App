package dev.dongeeo.bibliotecamunicipal.di

import android.content.Context
import coil.ImageLoader
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import javax.inject.Singleton

/**
 * Módulo de Hilt para configurar Coil (ImageLoader)
 * 
 * Este módulo configura Coil para manejar imágenes de manera óptima,
 * incluyendo políticas de caché y mejor compatibilidad con Google Books.
 */
@Module
@InstallIn(SingletonComponent::class)
object ImageModule {

    /**
     * Provee una instancia de ImageLoader configurada para la aplicación
     * 
     * CONFIGURACIÓN:
     * - Política de caché optimizada para imágenes de libros
     * - Cliente HTTP personalizado para mejor compatibilidad
     * - Manejo de errores mejorado
     * - Crossfade para animaciones suaves
     * 
     * @param context Contexto de la aplicación
     * @param okHttpClient Cliente HTTP para descargas
     * @return ImageLoader configurado
     */
    @Provides
    @Singleton
    fun provideImageLoader(
        @ApplicationContext context: Context,
        okHttpClient: OkHttpClient
    ): ImageLoader {
        return ImageLoader.Builder(context)
            .okHttpClient(
                okHttpClient.newBuilder()
                    .addInterceptor { chain ->
                        val request = chain.request().newBuilder()
                            .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36")
                            .addHeader("Referer", "https://books.google.com/")
                            .build()
                        chain.proceed(request)
                    }
                    .build()
            )
            .respectCacheHeaders(false) // Ignorar headers de caché del servidor
            .crossfade(true) // Animación suave al cargar imágenes
            .build()
    }
}
