package com.example.myapplication.activityLogic

import APIclient
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import com.example.myapplication.R
import com.example.myapplication.loginLogic.SessionManager
import com.example.myapplication.models.LoginResponse

import kotlinx.android.synthetic.main.activity_login.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity() {

    lateinit var sessionManager: SessionManager
    private lateinit var apiClient: APIclient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        supportActionBar?.hide()


        login_button.setOnClickListener {

            val username = username_text.text.toString().trim()
            val password = password_text.text.toString().trim()

            if(username.isEmpty()){
                username_text.error = "Username required"
                username_text.requestFocus()
                return@setOnClickListener
            }

            if(password.isEmpty()){
                password_text.error = "Password required"
                password_text.requestFocus()
                return@setOnClickListener
            }

            Log.d("NAME", " " + username)
            Log.d("NAME", " " + password)
            apiClient = APIclient()
            sessionManager = SessionManager(this)

            apiClient.getApiService(this).login(username, password)
                .enqueue(object : Callback<LoginResponse> {
                    override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                        Log.d("ERROR", "Error WTF")
                    }

                    override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {

                        val loginResponse = response.body()

                        if (loginResponse != null) {
                            sessionManager.saveAuthToken(loginResponse.token)

                        val intent = Intent(applicationContext, HomeScreen::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        startActivity(intent)

                        }
                        else{
                            Toast.makeText(applicationContext,"Wrong username or password",Toast.LENGTH_LONG).show()
                        }
                    }
                })
        }

        register_button.setOnClickListener {

            val intent = Intent(applicationContext, Register::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
    }
}
