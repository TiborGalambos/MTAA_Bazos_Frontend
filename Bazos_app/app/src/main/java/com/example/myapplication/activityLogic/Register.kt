package com.example.myapplication.activityLogic

import APIclient
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.myapplication.R
import com.example.myapplication.loginLogic.SessionManager
import com.example.myapplication.models.LoginResponse
import com.example.myapplication.models.RegisterResponse

import kotlinx.android.synthetic.main.activity_register.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Register : AppCompatActivity() {

    private lateinit var apiClient: APIclient

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        supportActionBar?.hide()


        register_button.setOnClickListener {

            val username = username_text.text.toString()
            val password = password_text.text.toString()
            val email = email_text.text.toString()

            if (username.isEmpty()) {
                username_text.error = "Username required"
                username_text.requestFocus()
                return@setOnClickListener
            }

            if (password.isEmpty()) {
                password_text.error = "Password required"
                password_text.requestFocus()
                return@setOnClickListener
            }

            if (email.isEmpty()) {
                email_text.error = "Email required"
                email_text.requestFocus()
                return@setOnClickListener
            }

            apiClient = APIclient()

            apiClient.getApiService(this).register(username, email, password)
                .enqueue(object : Callback<RegisterResponse> {
                    override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                        Log.d("ERROR", "Error WTF")
                    }
                    override fun onResponse(call: Call<RegisterResponse>, response: Response<RegisterResponse>) {
                        val registerResponse = response.body()

                        if (response.code() == 200) {
                            val intent = Intent(applicationContext, MainActivity::class.java)
                            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                            startActivity(intent)
                            Toast.makeText(applicationContext,"Successful registration", Toast.LENGTH_LONG).show()
                        }
                        else{
                            Log.d("username ", "" + username)
                            Log.d("email ", "" + email)
                            Log.d("password ", "" + password)
                            Toast.makeText(applicationContext,"Username taken", Toast.LENGTH_LONG).show()
                            Log.d("RESPONSE", " " + response.toString())
                        }
                    }
                })


        }
    }
}