package umer.task.pakrism.ui

import android.os.Bundle
import android.view.MenuItem
import android.widget.Filter
import kotlinx.android.synthetic.main.activity_buy_movie.*
import kotlinx.android.synthetic.main.seat_item.view.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import umer.task.pakrism.AppConstants
import umer.task.pakrism.R
import umer.task.pakrism.adapters.GenericListAdapter
import umer.task.pakrism.base.BaseActivty
import umer.task.pakrism.model.Seat

class BuyMovieActivity : BaseActivty() {
    private val movieViewModel: MovieViewModel by viewModel()

    val selectedSeatList: ArrayList<Seat> = ArrayList()
    val seat_unit=100
    var totalBill=0
    var movieName=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_buy_movie)
        movieName = intent.getStringExtra(AppConstants.name)?:""
        movie_name_tv.text=movieName
        val seatList: ArrayList<Seat> = ArrayList()

        val seatPattern = arrayOf("A", "B", "C","D","E")
        seatPattern.forEach {
            for (i in 1..6) {
                seatList.add(Seat("${it}$i"))
            }
        }



        getSupportActionBar()!!.setDisplayHomeAsUpEnabled(true)
        populateMovieRecycler(seatList)

        buy.setOnClickListener {
            if(totalBill==0){
                movieViewModel.isError.value="Bill must be greater than 0"
            }else{
                movieViewModel.isError.value="Ticket purchased successfully for $movieName"
            }
        }
    }

    private fun populateMovieRecycler(list: List<Seat>) {


        seats_rcv.adapter = object : GenericListAdapter<Seat>(
            R.layout.seat_item,
            bind = { element, holder, itemCount, position ->
                holder.view.run {
                    element.run {
                        seat_number.text = name
                        if (isSelect) {
                            setBackgroundResource(R.drawable.background_item_choose_seat)
                        } else {
                            setBackgroundResource(R.drawable.background_item_un_choose_seat)
                        }
                        setOnClickListener {
                            element.isSelect = !isSelect
                            if( element.isSelect){
                                selectedSeatList.add(element)
                            }else{
                                selectedSeatList.remove(element)
                            }
                            seats_rcv.adapter!!.notifyItemChanged(position)
                            selectedSeatsDate()
                        }
                    }
                }
            }
        ) {
            override fun getFilter(): Filter {
                TODO("Not yet implemented")
            }

        }.apply {
            submitList(list)
        }

    }

    private fun selectedSeatsDate() {
        var result=""
        selectedSeatList.forEach {
            result+=it.name+" , "
        }
        result = result.dropLast(3)
        if(result.isEmpty()){
            selected_seat_tv.text="N/A"
        }else{
            selected_seat_tv.text=result
        }
        totalBill=selectedSeatList.size*seat_unit
        total_bill_tv.text="Rs $totalBill"

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