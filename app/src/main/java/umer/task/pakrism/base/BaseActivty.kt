package umer.task.pakrism.base

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import umer.task.pakrism.ui.MovieViewModel
import umer.task.pakrism.utils.CustPrograssbar
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
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }
        )
    }

}