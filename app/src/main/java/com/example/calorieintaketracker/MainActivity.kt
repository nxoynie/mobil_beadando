package com.example.calorieintaketracker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.calorieintaketracker.network.WebContentService
import com.jayway.jsonpath.Configuration
import com.jayway.jsonpath.JsonPath
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("https://jokes.one/api/joke/")
            .addConverterFactory(ScalarsConverterFactory.create())
            .build()


        val service: WebContentService = retrofit.create(WebContentService::class.java)

        service.getJoke().enqueue(object : Callback<ResponseBody?> {
            override fun onResponse(call: Call<ResponseBody?>, response: Response<ResponseBody?>) {
                response.body()?.let {
                    val json = it.string()
                    val query = ".contents.jokes[0].joke"
                    val config: Configuration = Configuration.builder().build()

                    val ip = JsonPath.parse(json, config).read(query, List::class.java)
                    val message: String
                    val rac: String

                    if (ip == null ) {
                        message = "What do you call an elephant that doesn't matter? An irrelephant"
                    } else {
                        message = ip[0].toString()
                    }

                    Toast.makeText(applicationContext, "${message}", Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<ResponseBody?>, t: Throwable) {
                Toast.makeText(applicationContext, "What do you call an elephant that doesn't matter? An irrelephant", Toast.LENGTH_SHORT).show()
            }
        })
    }
}