package dev.dongeeo.bibliotecamunicipal.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import dev.dongeeo.bibliotecamunicipal.domain.model.Book
import dev.dongeeo.bibliotecamunicipal.ui.theme.BibliotecaMunicipalTheme

/**
 * Componente para mostrar un libro en la lista de resultados
 * 
 * CARACTERÍSTICAS:
 * - Imagen del libro usando Coil para carga asíncrona
 * - Título, autor y información adicional
 * - Indicador de disponibilidad
 * - Diseño responsive con Material 3
 * - Animaciones sutiles con Card elevation
 * 
 * DISEÑO:
 * - Layout horizontal con imagen a la izquierda y texto a la derecha
 * - Imagen con placeholder y manejo de errores
 * - Texto con overflow ellipsis para títulos largos
 * - Indicador de disponibilidad con ícono y color
 */
@Composable
fun BookListItem(
    book: Book,
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Imagen del libro con fallbacks
            Box(
                modifier = Modifier
                    .size(100.dp) // Aumentado de 80dp a 100dp para mejor visibilidad
                    .fillMaxHeight()
            ) {
                // DEBUG: Log para ver qué URL estamos recibiendo
                android.util.Log.d("BookListItem", "Book: ${book.title}")
                android.util.Log.d("BookListItem", "  Image URL: ${book.imageUrl}")
                android.util.Log.d("BookListItem", "  Thumbnail URL: ${book.thumbnailUrl}")
                android.util.Log.d("BookListItem", "  Small Thumbnail URL: ${book.smallThumbnailUrl}")
                
                // Si no hay URL, mostrar placeholder directamente
                if (book.imageUrl.isNullOrBlank()) {
                    Box(
                        modifier = Modifier
                            .size(100.dp) // Actualizado para coincidir con el tamaño principal
                            .fillMaxHeight()
                            .background(
                                MaterialTheme.colorScheme.surfaceVariant,
                                RoundedCornerShape(8.dp)
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Info,
                            contentDescription = "Sin imagen",
                            modifier = Modifier.size(32.dp),
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                } else {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(book.imageUrl)
                            .crossfade(true)
                            .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36")
                            .addHeader("Referer", "https://books.google.com/")
                            .build(),
                        contentDescription = "Portada de ${book.title}",
                        modifier = Modifier
                            .size(100.dp) // Actualizado para coincidir con el tamaño principal
                            .fillMaxHeight(),
                        contentScale = ContentScale.Crop,
                        onError = { result ->
                            android.util.Log.e("BookListItem", "Error loading image for ${book.title}")
                        },
                        onSuccess = { result ->
                            android.util.Log.d("BookListItem", "Successfully loaded image for ${book.title}")
                        }
                    )
                }
            }
            
            // Información del libro
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    // Título del libro
                    Text(
                        text = book.title,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    
                    
                    // Subtítulo si existe
                    if (!book.subtitle.isNullOrBlank()) {
                        Text(
                            text = book.subtitle,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                    
                    // Autor(es)
                    Text(
                        text = book.authorsString,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.primary,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    
                    // Información adicional
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        // Año de publicación
                        book.publishedYear?.let { year ->
                            Text(
                                text = year.toString(),
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                        
                        // Número de páginas
                        book.pageCount?.let { pages ->
                            Text(
                                text = "${pages} páginas",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
                
                // Indicador de disponibilidad
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.CheckCircle,
                        contentDescription = "Disponible",
                        modifier = Modifier.size(16.dp),
                        tint = if (book.isAvailable) {
                            Color(0xFF4CAF50) // Verde para disponible
                        } else {
                            Color(0xFFF44336) // Rojo para no disponible
                        }
                    )
                    Text(
                        text = book.availabilityStatus,
                        style = MaterialTheme.typography.bodySmall,
                        color = if (book.isAvailable) {
                            Color(0xFF4CAF50)
                        } else {
                            Color(0xFFF44336)
                        }
                    )
                }
            }
        }
    }
}

/**
 * Preview del componente BookListItem
 */
@Preview(showBackground = true)
@Composable
fun BookListItemPreview() {
    BibliotecaMunicipalTheme {
        BookListItem(
            book = Book(
                id = "1",
                title = "Harry Potter y la Piedra Filosofal",
                subtitle = "Libro 1 de la serie",
                authors = listOf("J.K. Rowling"),
                publisher = "Bloomsbury",
                publishedDate = "1997-06-26",
                description = "Un niño huérfano descubre que es un mago...",
                pageCount = 223,
                categories = listOf("Fantasía", "Aventura"),
                language = "es",
                thumbnailUrl = "https://books.google.com/books/content?id=example&printsec=frontcover&img=1",
                smallThumbnailUrl = "https://books.google.com/books/content?id=example&printsec=frontcover&img=1&zoom=1",
                previewLink = "https://books.google.com/books?id=example",
                infoLink = "https://books.google.com/books?id=example",
                isAvailable = true,
                availabilityStatus = "Disponible"
            )
        )
    }
}

/**
 * Preview del componente BookListItem sin imagen
 */
@Preview(showBackground = true)
@Composable
fun BookListItemNoImagePreview() {
    BibliotecaMunicipalTheme {
        BookListItem(
            book = Book(
                id = "2",
                title = "El Quijote",
                subtitle = null,
                authors = listOf("Miguel de Cervantes"),
                publisher = "Editorial Castalia",
                publishedDate = "1605",
                description = "Las aventuras de Don Quijote...",
                pageCount = 863,
                categories = listOf("Literatura clásica"),
                language = "es",
                thumbnailUrl = null, // Sin imagen
                smallThumbnailUrl = null,
                previewLink = null,
                infoLink = null,
                isAvailable = false,
                availabilityStatus = "Prestado"
            )
        )
    }
}
