package com.example.myapplication.activityLogic

import APIclient
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.myapplication.R
import com.example.myapplication.loginLogic.SessionManager
import com.example.myapplication.models.DeleteResponse
import com.example.myapplication.models.ItemResponse
import kotlinx.android.synthetic.main.activity_item_detail.*
import kotlinx.android.synthetic.main.activity_new_item.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ItemDetailActivity : AppCompatActivity() {

    lateinit var sessionManager: SessionManager
    private lateinit var apiClient: APIclient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_detail)
        supportActionBar?.hide()

        apiClient = APIclient()
        sessionManager = SessionManager(this)

        val idOfItem:Int? = intent.getIntExtra("id", 0)

        Log.d("DETAIL ACTIVITY: ", "" + idOfItem)

        edit.setOnClickListener {
            val intent = Intent(this, EditItem::class.java)
            intent.putExtra("id",idOfItem);
            startActivity(intent)
        }

        delete.setOnClickListener {

            intent = Intent(this, HomeScreen::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

            apiClient.getApiService(this).deleteThisItem(item_id_url = idOfItem!! ,token = "Token ${sessionManager.fetchAuthToken()}")
                .enqueue(object : Callback<DeleteResponse> {
                    override fun onFailure(call: Call<DeleteResponse>, t: Throwable) {
                        Log.d("ERROR", "Response: " + t.message)
                        Toast.makeText(applicationContext, "FAIL", Toast.LENGTH_SHORT).show()
                    }

                    override fun onResponse(call: Call<DeleteResponse>, response: Response<DeleteResponse>) {
                        Toast.makeText(applicationContext, "Your item was deleted!", Toast.LENGTH_SHORT).show()
                        startActivity(intent)

                    }
                })

        }

    }
}