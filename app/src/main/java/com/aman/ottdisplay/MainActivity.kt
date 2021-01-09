package com.aman.ottdisplay

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import okhttp3.*
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException

class MainActivity : AppCompatActivity(), GenreAdapter.OnItemClickListener{

    val list: ArrayList<GenreDataClass> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val client = OkHttpClient()

        val request = Request.Builder()
            .url("https://ott-details.p.rapidapi.com/getParams?param=genre")
            .get()
            .addHeader("x-rapidapi-key", "bc7a9875aemsh1e7ce3d15f1379cp12cbbejsn241163ee7138")
            .addHeader("x-rapidapi-host", "ott-details.p.rapidapi.com")
            .build()

        client.newCall(request).enqueue(object : Callback{
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
            }

            override fun onResponse(call: Call, response: Response) {
                var string: String = response.body?.string().toString()
                string = "{\"info\":$string}"
                val jsonObject = JSONObject(string)
                val jsonArray = jsonObject.getJSONArray("info")
                for(i in 0 until jsonArray.length()){
                    list.add(GenreDataClass(jsonArray[i].toString()))
                }

                runOnUiThread {
                    val recyclerView : RecyclerView = findViewById(R.id.genreRV)
                    recyclerView.adapter = GenreAdapter(list,this@MainActivity)
                    recyclerView.hasFixedSize()
                    recyclerView.layoutManager = GridLayoutManager(this@MainActivity,5,GridLayoutManager.HORIZONTAL,false)
                }


            }

        })

        val button : Button = findViewById(R.id.button)
        button.setOnClickListener {

            val editText : EditText = findViewById(R.id.searchEditText)
            val string : String = editText.text.toString()
            val intent = Intent(this,Details::class.java)
            intent.putExtra("genre",string)
            intent.putExtra("type",2)
            startActivity(intent)
        }

    }

    override fun onItemClick(string: String) {
        val intent = Intent(this,Details::class.java)
        intent.putExtra("genre",string)
        intent.putExtra("type",1)
        startActivity(intent)
    }
}