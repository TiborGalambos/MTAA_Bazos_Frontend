package com.example.myapplication.activityLogic

import APIclient
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.myapplication.R
import com.example.myapplication.loginLogic.SessionManager
import com.example.myapplication.models.ItemList
import com.example.myapplication.models.MyItemListAdapter
import com.example.myapplication.models.User
import kotlinx.android.synthetic.main.activity_items.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_my_profile.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyProfile : AppCompatActivity() {

    lateinit var sessionManager: SessionManager
    private lateinit var apiClient: APIclient
    lateinit var ids:Array<String?>

    @ExperimentalMultiplatform
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_profile)
        supportActionBar?.hide()

        apiClient = APIclient()
        sessionManager = SessionManager(this)

        Log.d("Token", "" + sessionManager.fetchAuthToken())

        apiClient.getApiService(this).userInfo(token = "Token ${sessionManager.fetchAuthToken()}")
            .enqueue(object : Callback<User> {
                override fun onFailure(call: Call<User>, t: Throwable) {
                    // Error fetching posts
                }
                override fun onResponse(call: Call<User>, response: Response<User>) {
                    val r = response.body()

                    username_text_to_write.text = r?.username
                    email_text_to_write.text = r?.email
                }
            })


        logout.setOnClickListener{
            intent = Intent(this, MainActivity::class.java)
            apiClient.getApiService(this).logOut(token = "Token ${sessionManager.fetchAuthToken()}")
                .enqueue(object : Callback<User> {
                    override fun onFailure(call: Call<User>, t: Throwable) {
                        // Error fetching posts
                    }
                    override fun onResponse(call: Call<User>, response: Response<User>) {
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        startActivity(intent)
                    }
                })

        }

        show_my_items.setOnClickListener{
            val intent = Intent(this, MyItems::class.java)
            startActivity(intent)
        }
    }
}