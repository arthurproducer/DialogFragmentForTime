package br.com.fiap.dialogfragmentfortime

import android.content.SharedPreferences
import android.icu.text.SimpleDateFormat
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.google.android.material.snackbar.Snackbar

import kotlinx.android.synthetic.main.activity_main.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class MainActivity : AppCompatActivity() {

    private var daysToReview = Date()
    private var dateStart: String? = ""
    private var dateEnd: String? = ""
    private var maxDausToReview = 0 ///VERIFICAR SEMPRE ESTÁ COMEÇANDO DO 4
    private var actualDate = Date()
    private val fourMonthInSeconds = 122 * 24 * 60.0 * 60.0
    //private lateinit var date : String
    private var show = false
    private lateinit var pref: SharedPreferences

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        pref = getSharedPreferences("config", 0)

        fab.setOnClickListener { view ->

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val current = LocalDateTime.now()
            //val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy. HH:mm:ss")
            val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy.")
            //var period = Period.ofMonths(3)

            dateStart = pref.getString("start", null)
            dateEnd = pref.getString("end", null)
            maxDausToReview = pref.getInt("count",0)

            Toast.makeText(this, "Valor inicial: $dateStart Valor final: $dateEnd", Toast.LENGTH_LONG).show()

            if (dateStart.equals(null)) {

                maxDausToReview = 4
                val lastday = current.plusMonths(3)
                dateEnd= lastday.format(formatter)

                dateStart = current.format(formatter)


                val editor = pref.edit()
                editor.putString("date", current.format(formatter))
                editor.putString("start", dateStart)
                editor.putString("end", dateEnd)
                editor.putInt("count", maxDausToReview)
                editor.apply()


                Snackbar.make(
                    view,
                    "Primeiro Login no App com A: $current S: $dateStart E:  $dateEnd Contador: $maxDausToReview",
                    Snackbar.LENGTH_INDEFINITE
                )
                    .setAction("Action", null).show()

                Log.d("Data Atual FIRST",current.format(formatter))
                Log.d("Data Final FIRST",dateEnd)
                Log.d("Data Count FIRST",maxDausToReview.toString())
                Log.d("Data Inicial FIRST",dateStart)

            }else  if (maxDausToReview > 0) {
                Log.d("Data Atual AntesIF", current.format(formatter))
                Log.d("Data Final AntesIF", dateEnd)
                Log.d("Data Count AntesIF", maxDausToReview.toString())
                Log.d("Data Inicial AntesIF", dateStart)


                if (current.format(formatter).equals(dateEnd)) {

                    maxDausToReview--
                    NoteDialogFragment().show(supportFragmentManager, "plus")

                    dateStart = dateEnd

                    val lastday = current.plusMonths(3)
                    dateEnd= lastday.format(formatter)

                }

                val editor = pref.edit()
                editor.putString("date", current.format(formatter))
                editor.putString("start", dateStart)
                editor.putString("end", dateEnd)
                editor.putInt("count", maxDausToReview)
                editor.apply()

                Snackbar.make(
                    view,
                    "Depois de 3 meses A: ${current.format(formatter)} S: $dateStart  E:  $dateEnd Contador: $maxDausToReview",
                    Snackbar.LENGTH_INDEFINITE
                ) .setAction("Action", null).show()

                Log.d("Data Atual DepoisIF",current.format(formatter))
                Log.d("Data Final DepoisIF",dateEnd)
                Log.d("Data Count DepoisIF",maxDausToReview.toString())
                Log.d("Data Inicial DepoisIF",dateStart)

            }

        } else {

            val calendar = Calendar.getInstance()
            //val formatter = SimpleDateFormat("MMM dd yyyy HH:mma")
            val formatter = SimpleDateFormat("MMM dd")


            val dataAtual = formatter.format(actualDate)

            dateStart = pref.getString("start", null)
            dateEnd = pref.getString("end", null)
            maxDausToReview = pref.getInt("count",0)

            Toast.makeText(this, "  Contador: $maxDausToReview Valor inicial: $dateStart Valor final: $dateEnd", Toast.LENGTH_LONG).show()

            if (dateStart.equals(null)) { //
                maxDausToReview = 4 ///Declarar o valor do contador aqui
                dateStart = formatter.format(actualDate)
                calendar.setTime(actualDate)
                calendar.add(Calendar.MONTH, 3)

                dateEnd = formatter.format(calendar.timeInMillis)


                val editor = pref.edit()
                editor.putString("date", dataAtual)
                editor.putString("start", dateStart)
                editor.putString("end", dateEnd)
                editor.putInt("count", maxDausToReview)

                editor.apply()

                Snackbar.make(
                    view,
                    "Primeiro Login no App com A: $dataAtual S: $dateStart E:  $dateEnd Contador: $maxDausToReview",
                    Snackbar.LENGTH_INDEFINITE
                ).setAction("Action", null).show()


                Log.d("Data Atual FIRST",dataAtual)
                Log.d("Data Final FIRST",dateEnd)
                Log.d("Data Count FIRST",maxDausToReview.toString())
                Log.d("Data Inicial FIRST",dateStart)

            } else if (maxDausToReview > 0) {
                val formatter = SimpleDateFormat("MMM dd")
                val dataAtual = formatter.format(actualDate)

                Log.d("Data Atual AntesIF",dataAtual)
                Log.d("Data Final AntesIF",dateEnd)
                Log.d("Data Count AntesIF",maxDausToReview.toString())
                Log.d("Data Inicial AntesIF",dateStart)

                if (dataAtual.equals(dateEnd)) {

                    //count = pref.getInt("count", 0)
                    maxDausToReview--
                    NoteDialogFragment().show(supportFragmentManager, "plus")

                    dateStart = dateEnd

                    calendar.setTime(actualDate)
                    calendar.add(Calendar.MONTH, 3)

                    dateEnd = formatter.format(calendar.timeInMillis)

                    val editor = pref.edit()
                    editor.putString("date", dataAtual)
                    editor.putString("start", dateStart)
                    editor.putString("end", dateEnd)
                    editor.putInt("count", maxDausToReview)
                    editor.apply()

                    Snackbar.make(
                        view,
                        "Depois de 3 meses A: $dataAtual S: $dateStart  E:  $dateEnd Contador:$maxDausToReview",
                        Snackbar.LENGTH_INDEFINITE
                    )
                        .setAction("Action", null).show()

                    Log.d("Data Atual DepoisIF",dataAtual)
                    Log.d("Data Final DepoisIF",dateEnd)
                    Log.d("Data Count DepoisIF",maxDausToReview.toString())
                    Log.d("Data Inicial DepoisIF",dateStart)

                }
            }
        }

            //if (pref.getString("date", null).equals(null) || pref.getString("date", null).equals("")) {
                //editor.putString("date", answer)
            //}
        }


    //val formatter = DateTimeFormatter.BASIC_ISO_DATE
    //val formatted = daysToReview.format(formatter)


    //fab.setOnClickListener { view ->
//
//        if (dateStart.equals("Oct 01 2019")) {
//            NoteDialogFragment().show(supportFragmentManager, "plus")
//        }


            //NoteDialogFragment().show(supportFragmentManager, "plus")
            //date passa a ter o valor de new, e new recebe date.plus3month

            //Toast.makeText(this, "Replace with your own action $dateStart for $dateEnd $show", Toast.LENGTH_LONG).show()


//            Snackbar.make(view, "CAMINHO NATURAL $dateStart  and  $dateEnd", Snackbar.LENGTH_LONG)
//                .setAction("Action", null).show()

        }


    override fun onResume() {
        super.onResume()
/*
        pref = getSharedPreferences("config",0)


        val date = pref.getString("date",null)
        val new = pref.getString("new",null)
        val show = pref.getBoolean("show",false)

        Toast.makeText(this,"Replace with your own action $date for $new $show",Toast.LENGTH_LONG).show()
*/
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }

        fun checkIfDateIsGreaterThanFourMonth(){
            // Data do dia de hoje é salva no momento em que o login é feito e também é salva uma data do último login
            // É iniciado uma contagem de dias até o próximo mês?**
            // É iniciado um contador para saber quantos meses faltam
            // Todo dia é validado se o dia salvo é o mesmo do dia atual ****
            // OU verifica quantos dias se passaram desde o seu login inicial*(data que a notificação foi feita), caso tenha passado mais de 3 meses é feito a notificação
            // compara a data que a notificação foi feita com a data atual(para descobrir se passou 3 meses)
            // é salvo a nova data da notificação
            // e o contador desce mais um (salvo no preferences)
        }


    }
}
