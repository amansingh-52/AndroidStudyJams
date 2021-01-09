package com.aman.ottdisplay

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import okhttp3.*
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException

class Details : AppCompatActivity() {


    val list : ArrayList<DetailsDataClass> = ArrayList()

    lateinit var progressBar : ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)


        progressBar = findViewById(R.id.progressBar)

        var string : String? = intent.getStringExtra("genre")
        val type  = intent.getIntExtra("type",0)

        var sendString = ""

        if(type == 1){
            sendString = "https://ott-details.p.rapidapi.com/advancedsearch?min_imdb=6&max_imdb=8.8&genre=$string&sort=highestrated&page=1"
        }else if(type == 2){
            string = string?.trim()?.replace(" ","+")?.toLowerCase()
            sendString = "https://ott-details.p.rapidapi.com/search?title=$string&page=1"
        }

        search(sendString)

    }

    private fun search(sendString: String) {

        progressBar.visibility = View.VISIBLE

        val client = OkHttpClient()

        val request = Request.Builder()
            .url(sendString)
            .get()
            .addHeader("x-rapidapi-key", "bc7a9875aemsh1e7ce3d15f1379cp12cbbejsn241163ee7138")
            .addHeader("x-rapidapi-host", "ott-details.p.rapidapi.com")
            .build()

        client.newCall(request).enqueue(object : Callback{
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
            }

            override fun onResponse(call: Call, response: Response) {

                val jsonObject = JSONObject(response.body?.string()?:"{}")
                val jsonArray = jsonObject.getJSONArray("results")
                for(i in 0 until jsonArray.length()){
                    val jObject = jsonArray.getJSONObject(i)

                    val title = jObject.get("title").toString()

                    var imdbRating: Any
                    imdbRating = try {
                        jObject.get("imdbrating")
                    }catch (e : JSONException){
                        "0"
                    }

                    var url: Any
                    url = try {
                        val jArray = jObject.getJSONArray("imageurl")
                        jArray.get(0).toString()
                    }catch (e: JSONException){
                        "https://i2.wp.com/lifemadesimplebakes.com/wp-content/uploads/2016/01/Black-Pepper-Parmesan-Popcorn-5.jpg"
                    }

                    val item = DetailsDataClass(url,title,imdbRating.toString())
                    list.add(item)

                    runOnUiThread{
                        progressBar.visibility = View.GONE
                        val recyclerView :RecyclerView = findViewById(R.id.detailsRV)
                        recyclerView.adapter = DetailsRVAdapter(list)
                        recyclerView.hasFixedSize()
                        val linearLayoutManager = LinearLayoutManager(this@Details , LinearLayoutManager.HORIZONTAL , false)
                        recyclerView.layoutManager = linearLayoutManager
                    }


                }
            }

        })

    }
}