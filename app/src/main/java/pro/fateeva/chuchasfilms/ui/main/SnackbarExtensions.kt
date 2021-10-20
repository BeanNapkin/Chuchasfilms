package pro.fateeva.chuchasfilms.ui.main

import android.view.View
import androidx.annotation.StringRes
import com.google.android.material.snackbar.Snackbar
import pro.fateeva.chuchasfilms.R

object SnackbarExtensions {
    fun View.showSnackbar(
        @StringRes message: Int,
        @StringRes buttonText: Int,
        action: (View) -> Unit
    ) =
        Snackbar
            .make(this, context.getString(message), Snackbar.LENGTH_INDEFINITE)
            .setAction(context.getString(buttonText), action)
            .show()

    fun View.showSnackbar(
        @StringRes message: Int
    ) =
        Snackbar
            .make(this, context.getString(message), Snackbar.LENGTH_INDEFINITE)
            .show()

}
