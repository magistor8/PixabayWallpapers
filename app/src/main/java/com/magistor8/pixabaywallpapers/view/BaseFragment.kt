package com.magistor8.pixabaywallpapers.view

import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.magistor8.pixabaywallpapers.R
import retrofit2.HttpException
import java.net.SocketTimeoutException

abstract class BaseFragment : Fragment() {

    protected fun renderError(throwable: Throwable) {
        when (throwable) {
            is HttpException -> {
                when (throwable.response()?.code()) {
                    401 -> showAlert(getString(R.string.DialogAlertTitleError), getString(R.string.AuthorizationError))
                    400 -> showAlert(getString(R.string.DialogAlertTitleError), getString(R.string.BadDataFormat))
                    500 -> showAlert(getString(R.string.DialogAlertTitleError),getString(R.string.BadRequest))
                    else -> showAlert(getString(R.string.DialogAlertTitleError),getString(R.string.Error))
                }
            }
            is SocketTimeoutException -> {
                showAlert(getString(R.string.DialogAlertTitleError),getString(R.string.ServerNotResponce))
            }
            is IllegalAccessException -> {
                showAlert(getString(R.string.DialogAlertTitleError),getString(R.string.NetworkError))
            }
            else -> {
                showAlert(getString(R.string.DialogAlertTitleError),getString(R.string.Error))
            }
        }
    }

    protected open fun showAlert(title: String, alert: String) {
        val builder = AlertDialog.Builder(requireContext())
        builder.setMessage(alert)
        builder.setTitle(title)
        builder.setCancelable(false)
        builder.setPositiveButton(
            R.string.ok
        ) { dialog, _ ->
            dialog.dismiss()
        }
        builder.create().show()
    }

}