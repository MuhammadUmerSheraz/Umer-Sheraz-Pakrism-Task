package com.elfalt.tmdb.base

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.elfalt.tmdb.ui.MovieViewModel
import com.elfalt.tmdb.utils.CustPrograssbar
import org.koin.androidx.viewmodel.ext.android.viewModel

abstract class BaseActivty : AppCompatActivity() {

    private val viewModel: MovieViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.isLoading.observe(this, {
            if (it) {
                CustPrograssbar.prograssCreate(this)
            } else {
                CustPrograssbar.closePrograssBar()
            }
        }
        )
        viewModel.isError.observe(this, {
            Toast.makeText(this, it, Toast.LENGTH_LONG).show()
        }
        )
    }

}