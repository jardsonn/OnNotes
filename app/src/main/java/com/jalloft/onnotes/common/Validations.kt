package com.jalloft.onnotes.common

import android.content.Context
import com.jalloft.onnotes.R
import com.jalloft.onnotes.data.error.AuthenticationErrorCodes
import com.jalloft.onnotes.data.error.NoteResponseCodes

/**
 * Created by Jardson Costa on 25/05/2024.
 */
object Validations {


    fun isValidEmail(email: String): Boolean {
        val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$".toRegex()
        return emailRegex.matches(email)
    }

    fun isValidPassword(password: String) = password.length >= 6

    fun getAuthError(context: Context, code: Int?): String {
        return when (code) {
            AuthenticationErrorCodes.EMAIL_EMPTY -> context.getString(R.string.auth_error_email_empty)
            AuthenticationErrorCodes.PASSWORD_EMPTY -> context.getString(R.string.auth_error_password_empty)
            AuthenticationErrorCodes.INVALID_EMAIL -> context.getString(R.string.auth_error_invalid_email)
            AuthenticationErrorCodes.INVALID_PASSWORD -> context.getString(R.string.auth_error_invalid_password)
            AuthenticationErrorCodes.CREDENTIALS_INCORRECT -> context.getString(R.string.auth_error_credentials_incorrect)
            AuthenticationErrorCodes.EMAIL_ALREADY_REGISTERED -> context.getString(R.string.auth_error_email_already_registered)
            AuthenticationErrorCodes.GENERIC_ERROR -> context.getString(R.string.auth_error_generic)
            AuthenticationErrorCodes.AUTHENTICATION_EXPIRED -> context.getString(R.string.auth_error_authentication_expired)
            else -> context.getString(R.string.auth_error_generic)
        }
    }

//    fun getNoteError(context: Context, code: Int) {
//        when (code) {
//            NoteResponseCodes.MALFORMED_URL -> context.getString(R.string.note_error_message_)
//            NoteResponseCodes.NOTE_NOT_FOUND -> context.getString(R.string.note_error_message_)
//            NoteResponseCodes.NOTE_DELETED_SUCCESSFULLY -> context.getString(R.string.note_error_message_)
//            NoteResponseCodes.NOTE_UPDATED_SUCCESSFULLY -> context.getString(R.string.note_error_message_)
//        }
//    }


}