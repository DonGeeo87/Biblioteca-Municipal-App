package dev.dongeeo.bibliotecamunicipal.di

import android.content.Context
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dev.dongeeo.bibliotecamunicipal.BuildConfig
import dev.dongeeo.bibliotecamunicipal.data.remote.GoogleBooksApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Qualifier
import javax.inject.Singleton

/**
 * Módulo de Hilt para configurar las dependencias de red
 * 
 * Este módulo provee todas las dependencias relacionadas con networking:
 * - Retrofit: cliente HTTP para llamadas a la API
 * - OkHttp: cliente HTTP subyacente con interceptores
 * - GoogleBooksApi: interfaz para las llamadas a Google Books API
 * - Configuraciones: timeouts, logging, serialización JSON
 */
@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    
    /**
     * Provee la instancia de OkHttpClient configurada
     * 
     * CONFIGURACIÓN:
     * - Timeout de conexión: 30 segundos
     * - Timeout de lectura: 30 segundos
     * - Timeout de escritura: 30 segundos
     * - Logging interceptor solo en modo debug
     * 
     * @return OkHttpClient configurado
     */
    @Provides
    @Singleton
    fun provideOkHttpClient(@ApplicationContext context: Context): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .addInterceptor(
                HttpLoggingInterceptor().apply {
                    // Solo mostrar logs en modo debug
                    level = if (BuildConfig.DEBUG) {
                        HttpLoggingInterceptor.Level.BODY
                    } else {
                        HttpLoggingInterceptor.Level.NONE
                    }
                }
            )
            .build()
    }
    
    /**
     * Provee la instancia de Retrofit configurada
     * 
     * CONFIGURACIÓN:
     * - Base URL: Google Books API
     * - Serialización JSON con kotlinx.serialization
     * - OkHttpClient como cliente HTTP
     * 
     * @param okHttpClient Cliente HTTP configurado
     * @return Retrofit configurado
     */
    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        val json = Json {
            ignoreUnknownKeys = true
            coerceInputValues = true
        }
        
        return Retrofit.Builder()
            .baseUrl("https://www.googleapis.com/books/v1/")
            .client(okHttpClient)
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()
    }
    
    /**
     * Provee la interfaz de Google Books API
     * 
     * @param retrofit Instancia de Retrofit configurada
     * @return GoogleBooksApi para realizar llamadas a la API
     */
    @Provides
    @Singleton
    fun provideGoogleBooksApi(retrofit: Retrofit): GoogleBooksApi {
        return retrofit.create(GoogleBooksApi::class.java)
    }
    
    /**
     * Provee la API key de Google Books desde BuildConfig
     * 
     * SEGURIDAD:
     * - La API key se lee desde BuildConfig (generado desde local.properties)
     * - local.properties no se incluye en control de versiones
     * - Validación de que la API key no esté vacía
     * 
     * @return API key de Google Books
     */
    @Provides
    @Named("google_books_api_key")
    fun provideGoogleBooksApiKey(): String {
        val apiKey = BuildConfig.GOOGLE_BOOKS_API_KEY
        require(apiKey.isNotEmpty()) { 
            "Google Books API Key no está configurada. Verifica local.properties" 
        }
        return apiKey
    }
}
