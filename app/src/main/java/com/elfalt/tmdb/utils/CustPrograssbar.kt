package com.elfalt.tmdb.utils

import android.content.Context
import com.kaopiz.kprogresshud.KProgressHUD


class CustPrograssbar {


    companion object {
        private var progressDialog: KProgressHUD? = null

        fun prograssCreate(context: Context?) {
            try {
                progressDialog = showProgressDialog(context!!, cancelable = false)
                progressDialog!!.show()

            } catch (error: Exception) {
                print(error)
            }
        }

        fun closePrograssBar() {
            try {
                progressDialog!!.dismiss()
            } catch (error: Exception) {
                print(error)
            }
        }



        fun showProgressDialog(context: Context, message: String = "please wait", cancelable: Boolean = false): KProgressHUD {
            return KProgressHUD.create(context)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel(message)
                .setCancellable(cancelable)
                .setMaxProgress(100) as KProgressHUD
        }

    }



}