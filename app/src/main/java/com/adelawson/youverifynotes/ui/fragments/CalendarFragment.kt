package com.adelawson.youverifynotes.ui.fragments

import android.content.Context
import android.os.Bundle
import android.util.DisplayMetrics
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.adelawson.youverifynotes.R
import com.adelawson.youverifynotes.data.localSource.Task
import com.adelawson.youverifynotes.data.localSource.TaskViewModel
import com.adelawson.youverifynotes.databinding.CalendarDayBinding
import com.adelawson.youverifynotes.databinding.FragmentCalendarBinding
import com.adelawson.youverifynotes.ui.adapters.CalendarTaskParentAdapter
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.kizitonwose.calendarview.model.CalendarDay
import com.kizitonwose.calendarview.ui.DayBinder
import com.kizitonwose.calendarview.ui.ViewContainer
import com.kizitonwose.calendarview.utils.Size
import java.text.SimpleDateFormat
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList


class CalendarFragment : Fragment() {
    private lateinit var binding: FragmentCalendarBinding
    private val viewModel by viewModels<TaskViewModel>()
    private lateinit var taskList: List<Task>
    private var selectedDate= LocalDate.now()
    private val today = LocalDate.now()
    private lateinit var dates: List<String>

    private val dateFormatter = DateTimeFormatter.ofPattern("dd")
    private val dayFormatter = DateTimeFormatter.ofPattern("EEE")
    private val monthFormatter = DateTimeFormatter.ofPattern("MMM")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCalendarBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showNav()

        val displayMetrics = DisplayMetrics()
        val windowManager = requireContext().getSystemService(Context.WINDOW_SERVICE) as WindowManager
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        binding.calendarView.apply {
            val dayWidth = displayMetrics.widthPixels / 5
            val dayHeight = (dayWidth * 1.25).toInt()
            daySize = Size(dayWidth, dayHeight)
        }
        class DayViewContainer(view: View) : ViewContainer(view) {
            val bind = CalendarDayBinding.bind(view)
            lateinit var day: CalendarDay

            init {
                view.setOnClickListener {
                    val firstDay = binding.calendarView.findFirstVisibleDay()
                    val lastDay = binding.calendarView.findLastVisibleDay()


                    if (firstDay == day) {
                        binding.calendarView.smoothScrollToDate(day.date.minusDays(2))
                    } else if (lastDay == day) {
                        binding.calendarView.smoothScrollToDate(day.date.minusDays(2))
                    }

                    if (selectedDate != day.date) {
                        val oldDate = selectedDate
                        selectedDate = day.date
                        binding.calendarView.notifyDateChanged(day.date)
                        oldDate?.let { binding.calendarView.notifyDateChanged(it) }
                    }
                }
            }

            fun bind(day: CalendarDay) {
                this.day = day
                bind.dateTxv.text = dateFormatter.format(day.date)
                bind.dayTxv.text = dayFormatter.format(day.date)
                bind.monthTxv.text = monthFormatter.format(day.date)
                setColor(bind.dateTxv,day)
                setColor(bind.dayTxv,day)
                setColor(bind.monthTxv,day)
                bind.dateContainer.background = AppCompatResources.getDrawable(
                    requireContext(),
                    (if (day.date == selectedDate) R.drawable.date_selector else R.color.white)
                )
            }
        }

        binding.calendarView.dayBinder = object : DayBinder<DayViewContainer> {
            override fun create(view: View) = DayViewContainer(view)
            override fun bind(container: DayViewContainer, day: CalendarDay) = container.bind(day)
        }

        val currentMonth = YearMonth.now()
        binding.calendarView.setup(currentMonth, currentMonth.plusMonths(3), DayOfWeek.values().random())
        binding.calendarView.scrollToDate(LocalDate.now())





        val d1: Date = Date.from(currentMonth.atDay(1).atStartOfDay(ZoneId.systemDefault()).toInstant())
        val d2 = Date.from(currentMonth.plusMonths(3).atDay(31).atStartOfDay(ZoneId.systemDefault()).toInstant())
        dates = getDates(d1,d2)

        val calendarRecycler = binding.calendarRcv
        val taskAdapter = CalendarTaskParentAdapter()
        calendarRecycler.adapter = taskAdapter
        calendarRecycler.layoutManager = LinearLayoutManager(requireContext())
        viewModel.readTasks.observe(viewLifecycleOwner, Observer{task->
            taskList = task
            taskAdapter.setData(dates, task)

        })

        val cal = Calendar.getInstance()
        val sdf = SimpleDateFormat("EEEE d, LLLL", Locale.getDefault())
        val curDate = sdf.format(cal.time).toString()
        val pos = dates.indexOf(curDate)
        calendarRecycler.scrollToPosition(pos)




    }



    private fun setColor(txv: TextView, day: CalendarDay){
        txv.setTextColor(if (day.date == selectedDate) resources.getColor(R.color.white) else resources.getColor(
            R.color.black
        ))
    }
    fun showNav(){
        val bottomNav = activity?.findViewById<BottomAppBar>(R.id.bottomAppBar)
        bottomNav?.visibility = View.VISIBLE
        val bottomFab = activity?.findViewById<FloatingActionButton>(R.id.new_note_fab)
        bottomFab?.visibility = View.VISIBLE

    }
    fun getDates(date1:Date, date2:Date):ArrayList<String>{
        val dates = ArrayList<String>()
        val cal1 = Calendar.getInstance()
        cal1.time = date1
        val cal2 = Calendar.getInstance()
        cal2.time = date2
        while (!cal1.after(cal2))
        {
            val sdf = SimpleDateFormat("EEEE d, LLLL", Locale.getDefault())
            dates.add(sdf.format(cal1.time).toString())
            cal1.add(Calendar.DATE, 1)
        }
        return dates
    }

}