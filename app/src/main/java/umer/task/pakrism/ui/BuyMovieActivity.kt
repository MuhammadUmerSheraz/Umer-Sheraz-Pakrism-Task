package umer.task.pakrism.ui

import android.os.Bundle
import android.view.MenuItem
import umer.task.pakrism.R
import umer.task.pakrism.base.BaseActivty

class BuyMovieActivity : BaseActivty() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_buy_movie)
        getSupportActionBar()!!.setDisplayHomeAsUpEnabled(true)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.getItemId()) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}