package com.mydomain.countries



import android.annotation.SuppressLint
import android.os.*
import android.os.StrictMode.ThreadPolicy
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.MaterialToolbar
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject


class MainActivity : AppCompatActivity() {
    var progressBar: ProgressBar?=null
    var rvRecycler: RecyclerView?=null
    var urlConnection: UrlConnection?=null
    private var utils:Utils?=null
    var arrayCountries: JSONArray?=null
    private var data : StringBuilder?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        StrictMode.setThreadPolicy(ThreadPolicy.Builder().permitNetwork().build())

        rvRecycler = findViewById(R.id.rvRecycler)
        progressBar = findViewById(R.id.progressBar);
        urlConnection= UrlConnection(this)
        utils= Utils(this)

        data = StringBuilder()
        progressBar!!.visibility=View.GONE

        val  topAppBar: MaterialToolbar? = findViewById(R.id.topAppBar)

        topAppBar!!.setOnMenuItemClickListener { menuItem: MenuItem ->
            when (menuItem.itemId) {
                R.id.xls -> {
                    exportTo("xls")
                    return@setOnMenuItemClickListener true
                }
                R.id.csv -> {
                    exportTo("csv")
                    return@setOnMenuItemClickListener true
                }
                R.id.xml -> {
                    exportTo("xml")
                    return@setOnMenuItemClickListener true
                }
                else -> false
            }
        }


        object : Thread() {
            override fun run() {
                val msg: Message = Message.obtain()
                msg.what = 1
                try {
                    arrayCountries= JSONArray(urlConnection!!.allCountries())
                    Log.e("Result", arrayCountries.toString())
                    val b = Bundle()
                    msg.data = b
                } catch (e1: Exception) {
                    e1.printStackTrace()
                }
                countriesHandler.sendMessage(msg)
            }
        }.start()



    }


    @SuppressLint("HandlerLeak")
    private val countriesHandler: Handler = object : Handler() {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            progressBar!!.visibility=View.GONE

            val countriesAdapter = CountriesAdapter(this@MainActivity, arrayCountries!!)
            rvRecycler!!.adapter = countriesAdapter
            rvRecycler!!.layoutManager = LinearLayoutManager(this@MainActivity)


        }
    }



    private fun exportTo(target:String){

        data!!.append("Flag,Name,Common,Region,Subregion,NativeName,Capital,Timezone,Population,Area")
        for (i in 0 until arrayCountries!!.length()) {
            try {
                val objectCountry = JSONObject(arrayCountries!![i].toString())
                //flag
                val objectFlag= JSONObject(objectCountry.getString("flags"))
                val flag= objectFlag.getString("png")
                //Name
                val objectName= JSONObject(objectCountry.getString("name"))
                val name=objectName.getString("common")
                //Region | subregion
                val region=objectCountry.getString("region")
                val subregion= objectCountry.getString("subregion")
                //Native name
                val objectNativeName= JSONObject(objectName.getString("nativeName"))
                val engObject= JSONObject(objectNativeName.getString("eng"))
                val nativeName=engObject.getString("official")
                //Capital
                val arrayCapitals=JSONArray(objectCountry.getString("capital"))
                val capital= arrayCapitals.get(0)
                //Timezone
                val arrayTimeZone= JSONArray(objectCountry.getString("timezones"))
                val timezone= arrayTimeZone.get(0)
                //Population
                val population=objectCountry.getString("population")
                //area
                val area=objectCountry.getString("area")

                data!!.append("\n$flag,$name,$region,$subregion,$nativeName,$capital,$timezone,$population,$area");



            } catch (e: JSONException) {
                e.printStackTrace()
            }
        }

        when(target){
            "csv"->{
                utils!!.createCSV(data)
            }
            "xls"->{

            }
            "xlm"->{

            }
        }


    }









}