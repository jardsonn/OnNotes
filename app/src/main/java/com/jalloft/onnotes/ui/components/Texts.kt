package com.jalloft.onnotes.ui.components

import android.text.style.StyleSpan
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import com.jalloft.onnotes.R

@Composable
fun AppNameText(
    modifier: Modifier = Modifier,
    fontSize: TextUnit = TextUnit.Unspecified,
    fontStyle: FontStyle? = null,
    fontWeight: FontWeight = FontWeight.Black,
    fontFamily: FontFamily? = null,
    letterSpacing: TextUnit = TextUnit.Unspecified,
    lineHeight: TextUnit = TextUnit.Unspecified,
    overflow: TextOverflow = TextOverflow.Clip,
    style: TextStyle = LocalTextStyle.current
){
    val appName = stringResource(id = R.string.app_name)
    val annotated = AnnotatedString(
        appName,
        listOf(AnnotatedString.Range(SpanStyle(color = MaterialTheme.colorScheme.primary), 0, 2))
    )
    Text(
        text = annotated,
        modifier,
        fontSize = fontSize,
        fontWeight = fontWeight,
        lineHeight = lineHeight,
        fontFamily = fontFamily,
        fontStyle = fontStyle,
        letterSpacing = letterSpacing,
        overflow = overflow,
        style = style)
}

@Composable
fun TextFooter(
    modifier: Modifier = Modifier,
    textLabel: String,
    textAction: String,
    actionClick: () -> Unit
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = textLabel,
            color = MaterialTheme.colorScheme.onBackground.copy(.5f),
            fontWeight = FontWeight.Black,
            modifier = Modifier,
            textAlign = TextAlign.Center
        )
        TextButton(onClick = actionClick) {
            Text(
                text = textAction,
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.End,
                fontWeight = FontWeight.Black,
            )
        }
    }
}
