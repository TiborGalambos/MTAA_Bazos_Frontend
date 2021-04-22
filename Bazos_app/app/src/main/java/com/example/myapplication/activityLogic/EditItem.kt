package com.example.myapplication.activityLogic

import APIclient
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.myapplication.R
import com.example.myapplication.Utils.getFileName
import com.example.myapplication.loginLogic.SessionManager
import com.example.myapplication.models.ItemResponse
import kotlinx.android.synthetic.main.activity_my_profile.*
import kotlinx.android.synthetic.main.activity_new_item.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

class EditItem : AppCompatActivity() {

    lateinit var sessionManager: SessionManager
    private lateinit var apiClient: APIclient

    //@ExperimentalMultiplatform
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_item)
        supportActionBar?.hide()

        val idOfItem:Int? = intent.getIntExtra("id", 0)

        var category_name:String = "Electronics"

        apiClient = APIclient()
        sessionManager = SessionManager(this)

        apiClient.getApiService(this).getThisItem(item_id_url = idOfItem!! ,token = "Token ${sessionManager.fetchAuthToken()}",)
            .enqueue(object : Callback<ItemResponse> {
                override fun onFailure(call: Call<ItemResponse>, t: Throwable) {
                    Log.d("ERROR", "Response: " + t.message)
                    Toast.makeText(applicationContext, "FAIL", Toast.LENGTH_SHORT).show()
                }

                override fun onResponse(call: Call<ItemResponse>, response: Response<ItemResponse>) {
                    Toast.makeText(applicationContext, "SUCCESS", Toast.LENGTH_SHORT).show()
                    val r = response.body()

                    title_text.setText(r?.title!!.toString().replace("\"", ""))
                    content_text.setText(r?.content!!.replace("\"", ""))
                    address_text.setText(r?.address!!.replace("\"", ""))
                    price_text.setText((r?.price!!.toString()))
                    category_name = r?.category!!.replace("\"", "")
//                    username_text_to_write.text = r?.username
//                    email_text_to_write.text = r?.email
                }
            })



        post_button.setOnClickListener{

            val title = title_text.text.toString().replace("\"", "")
            val content = content_text.text.toString().replace("\"", "")
            val address = address_text.text.toString().replace("\"", "")
            val price = price_text.text.toString().replace("\"", "")

            if (title.isEmpty()) {
                title_text.error = "Title required"
                title_text.requestFocus()
                return@setOnClickListener
            }

            if (content.isEmpty()) {
                content_text.error = "Content required"
                content_text.requestFocus()
                return@setOnClickListener
            }

            if (address.isEmpty()) {
                address_text.error = "Address required"
                address_text.requestFocus()
                return@setOnClickListener
            }

            if (price.isEmpty()) {
                price_text.error = "Price required"
                price_text.requestFocus()
                return@setOnClickListener
            }

            intent = Intent(this, added_item::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK


            apiClient.getApiService(this).editThisItem(
                title = title,
                content = content,
                address = address,
                price = price.toInt(),
                category = category_name,
                item_id_url = idOfItem!!,
                token = "Token ${sessionManager.fetchAuthToken()}")
                .enqueue(object : Callback<ItemResponse> {
                    override fun onFailure(call: Call<ItemResponse>, t: Throwable) {
                        Log.d("ERROR", "Response: " + t.message)
                        Toast.makeText(applicationContext, "FAIL", Toast.LENGTH_SHORT).show()
                    }

                    override fun onResponse(call: Call<ItemResponse>, response: Response<ItemResponse>) {
                        Toast.makeText(applicationContext, "SUCCESS", Toast.LENGTH_SHORT).show()
                        startActivity(intent)
                    }
                })

        }

    }
}