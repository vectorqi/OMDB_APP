package com.vector.omdbapp.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.vector.omdbapp.R
import com.vector.omdbapp.data.model.Movie
import com.vector.omdbapp.viewmodel.MovieViewModel

/**
 * MovieItem.kt
 *
 * Composable representing a single movie item in the list.
 * Displays poster, title, year, and a toggleable label with a control button.
 * Uses Coil for image loading with placeholders.
 */

@Composable
fun MovieItem(movie: Movie, viewModel: MovieViewModel) {
    val context = LocalContext.current
    val isFavorite = viewModel.favorStates[movie.imdbID] == true

    // Create a custom image loader with disk and memory caching enabled
    val customImageLoader = ImageLoader.Builder(context)
        .crossfade(true)
        .diskCachePolicy(CachePolicy.ENABLED)
        .memoryCachePolicy(CachePolicy.ENABLED)
        .build()
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Top
    ) {
        // Provide a placeholder image if the poster is loading or fails
        AsyncImage(
            model = ImageRequest.Builder(context)
                .data(movie.posterUrl)
                .placeholder(R.drawable.loading)
                .error(R.drawable.error)
                .size(128, 192)

                .build(),
            imageLoader = customImageLoader,
            contentDescription = movie.title,
            modifier = Modifier.size(80.dp)
        )

        Spacer(modifier = Modifier.width(8.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = movie.title,
                style = MaterialTheme.typography.bodyLarge,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = context.getString(R.string.year_label) + movie.year,
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
        IconButton(onClick = { viewModel.toggleFavorite(movie.imdbID) }) {
            Icon(
                imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                contentDescription = if (isFavorite) context.getString(R.string.icon_unfavor)else context.getString(R.string.icon_favor),
                tint = if (isFavorite) MaterialTheme.colorScheme.primary else Color.Gray
            )
        }
    }
}