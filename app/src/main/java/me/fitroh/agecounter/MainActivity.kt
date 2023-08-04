package me.fitroh.agecounter

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    // access modifier private yang mengizinkan pemberian nilai null
    private var tvSelectedDate: TextView? = null
    private var tvAgeInMinutes: TextView? = null


    // override merupakan implementasi ulang fungsi kelas induk
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnDatePicker: Button = findViewById(R.id.btnDatePicker) // deklarasi variable
        tvSelectedDate = findViewById(R.id.tvSelectedDate)
        tvAgeInMinutes = findViewById(R.id.tvAgeInMinutes)


        btnDatePicker.setOnClickListener {
            clickDatePicker() // memanggil fungsi
        }
    }

    private fun clickDatePicker() {
        // Mendapatkan instance dari Calendar untuk mendapatkan tanggal saat ini
        val myCalendar = Calendar.getInstance()
        val year = myCalendar.get(Calendar.YEAR)
        val month = myCalendar.get(Calendar.MONTH)
        val day = myCalendar.get(Calendar.DAY_OF_MONTH)

        // Membuat instance DatePickerDialog dengan listener untuk menangani pilihan tanggal
        val dpd = DatePickerDialog(this, { _, selectedYear, selectedMonth, selectedDayOfMonth ->
            // Menampilkan Toast dengan tanggal yang dipilih oleh pengguna
            Toast.makeText(
                this,
                "Year was $selectedYear, month ${selectedMonth + 1}, day $selectedDayOfMonth",
                Toast.LENGTH_LONG
            ).show()

            // Menggabungkan tanggal yang dipilih dalam format dd/MM/yyyy dan menampilkan di TextView
            val selectedDate = "$selectedDayOfMonth/${selectedMonth + 1}/$selectedYear"
            tvSelectedDate?.text = selectedDate
            val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH)
            val theDate = sdf.parse(selectedDate)

            // Jika berhasil parsing tanggal, maka lakukan perhitungan selisih waktu
            theDate?.let {
                // Menghitung tanggal yang dipilih dalam menit
                val selectedDateInMinutes = theDate.time / 60000
                // mendapatkan tanggal saat ini
                val currentDate = sdf.parse(sdf.format(System.currentTimeMillis()))
                currentDate?.let {
                    val currentDateInMinutes = currentDate.time / 60000
                    val differentInMinutes = currentDateInMinutes - selectedDateInMinutes
                    tvAgeInMinutes?.text = differentInMinutes.toString()
                }
            }
        }, year, month, day)

        dpd.datePicker.maxDate = System.currentTimeMillis() - 86400000
        dpd.show()
    }

}