package com.example.ageinminutes
import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private var tvSelectedDate: TextView? = null
    private var tvMinuteCalc: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvSelectedDate = findViewById(R.id.tvSelectedDate)
        tvMinuteCalc = findViewById(R.id.tvMinutesCalc)

        val btnSelectDate = findViewById<Button>(R.id.btnSelectDate)
        btnSelectDate.setOnClickListener {
            clickOnDatePicker()
        }

    }

    private fun clickOnDatePicker() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePicker = DatePickerDialog(this,
            { _, sYear, sMonth, sDayOfMonth ->

                // set text of Selected Date textview
                val selectedDate = "$sDayOfMonth/${sMonth + 1}/$sYear"
                tvSelectedDate?.text = selectedDate

                // calculate
                val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH)
                val dateSelect = sdf.parse(selectedDate)
                val currentDate = sdf.parse(sdf.format(System.currentTimeMillis()))

                // set text of In minutes textview
                dateSelect?.let {
                    currentDate?.let {
                        val minutes = currentDate.time / 60000 - dateSelect.time / 60000
                        tvMinuteCalc?.text = minutes.toString()
                    }
                }

            }, year, month, day)

        // max range of date is yesterday
        datePicker.datePicker.maxDate = System.currentTimeMillis() - 86400000
        datePicker.show()

    }
}