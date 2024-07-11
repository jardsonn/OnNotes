package com.jalloft.onnotes.common

import android.icu.text.RelativeDateTimeFormatter
import android.icu.util.ULocale
import androidx.compose.ui.graphics.Color
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDateTime
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.concurrent.TimeUnit
import kotlin.math.abs

/**
 * Created by Jardson Costa on 30/05/2024.
 */
object NoteUiCommon {

    fun getMaxLinesForContent(content: String): Int {
        return when (content.length) {
            in 0..50 -> 2
            in 51..100 -> 4
            in 101..200 -> 6
            else -> 8
        }
    }

    fun generateColorFromText(text: String): Color {
        val hash = abs(text.hashCode())
        val red = 200 + (hash % 55)
        val green = 200 + ((hash / 2) % 55)
        val blue = 200 + ((hash / 3) % 55)
        return Color(red, green, blue)
    }
    

//    fun formatRelativeTime(dateTimeString: String?): String {
//        val formatoEntrada = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSS", Locale.getDefault())
//        val formatoSaida = SimpleDateFormat("dd 'de' MMM 'de' yyyy", Locale.getDefault())
//
//        val dataFormatada = dateTimeString?.let { formatoEntrada.parse(it) } ?: return "--:--"
//        return formatoSaida.format(dataFormatada)
//    }


    fun formatRelativeTime(dateString: String?): String {
        val formatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSS", Locale.getDefault())

        val date: Date = dateString?.let { formatter.parse(it) } ?: return "Data inválida"

        val now = Date()

        val diffInMillis = now.time - date.time

        val diffInSeconds = TimeUnit.MILLISECONDS.toSeconds(diffInMillis)
        val diffInMinutes = TimeUnit.MILLISECONDS.toMinutes(diffInMillis)
        val diffInHours = TimeUnit.MILLISECONDS.toHours(diffInMillis)
        val diffInDays = TimeUnit.MILLISECONDS.toDays(diffInMillis)

        return when {
            diffInSeconds < 60 -> "Há poucos segundos"
            diffInMinutes < 60 -> "Há $diffInMinutes minutos"
            diffInHours < 24 -> "Há $diffInHours horas"
            diffInDays < 30 -> "Há $diffInDays dias"
            else -> "Há muito tempo"
        }
    }

}