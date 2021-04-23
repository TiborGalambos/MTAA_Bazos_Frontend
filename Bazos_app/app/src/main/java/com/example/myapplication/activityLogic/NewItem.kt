package com.example.myapplication.activityLogic

import APIclient
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.R
import com.example.myapplication.Utils.*
import com.example.myapplication.loginLogic.SessionManager
import com.example.myapplication.models.ItemResponse
import kotlinx.android.synthetic.main.activity_my_profile.*
import kotlinx.android.synthetic.main.activity_new_item.*
import kotlinx.android.synthetic.main.activity_register.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.MultipartBody.Part.Companion.createFormData
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.create
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream


class NewItem : AppCompatActivity() {

    lateinit var sessionManager: SessionManager
    private lateinit var apiClient: APIclient

    lateinit var file_path: Uri
    var code: Int = 0

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_item)
        supportActionBar?.hide()

        apiClient = APIclient()
        sessionManager = SessionManager(this)

        val cat_list = resources.getStringArray(R.array.Categories)
        var pos:Int = 1
        if (spinner != null) {
            val adapter = ArrayAdapter(
                    this,
                    android.R.layout.simple_spinner_item, cat_list
            )
            spinner.adapter = adapter
            spinner.onItemSelectedListener = object :
                AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                        parent: AdapterView<*>,
                        view: View, position: Int, id: Long
                )   {
                    pos = position
                    Toast.makeText(this@NewItem, getString(R.string.selected_item) + " " + "" + cat_list[position], Toast.LENGTH_SHORT ).show()
                    }
                    override fun onNothingSelected(p0: AdapterView<*>?) {
                    }
                }
        }

        photo_button.setOnClickListener{

            var intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, 123)

        }

        post_button.setOnClickListener{
//            Toast.makeText(applicationContext,cat_list[pos], Toast.LENGTH_SHORT).show()
//
            val category = cat_list[pos].toString().replace("(^\\(|\\)$)", "")
            val title = title_text.text.toString().replace("(^\\(|\\)$)", "")
            val content = content_text.text.toString().trim().replace("(^\\(|\\)$)", "")
            val address = address_text.text.toString().trim().replace("(^\\(|\\)$)", "")
            val price = price_text.text.toString().trim().replace("(^\\(|\\)$)", "")

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
            if (content.length < 30) {
                content_text.error = "Content too short (min 30 chars)"
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

            if (code == 0) {
                photo_button.error = "Photo required"
                photo_button.requestFocus()
                return@setOnClickListener
            }

            intent = Intent(this, added_item::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK


            val parcelFileDescriptor = contentResolver.openFileDescriptor(file_path, "r", null) ?: return@setOnClickListener
            val file = File(cacheDir, contentResolver.getFileName(file_path))
            val inputStream = FileInputStream(parcelFileDescriptor.fileDescriptor)
            val outputStream = FileOutputStream(file)
            inputStream.copyTo(outputStream)

            val requestFile: RequestBody = create("multipart/form-data".toMediaTypeOrNull(), file)
            val body: MultipartBody.Part = createFormData("photo", file.name, requestFile)


            apiClient.getApiService(this).postItem(token = "Token ${sessionManager.fetchAuthToken()}",
                    category = category.toString(),
                    title = title,
                    content = content,
                    price = price.toInt(),
                    address = address,
                    body)
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 123) {
            code = requestCode

           file_path = data!!.getData()!!
            Log.d("tag", "Message" + (data!!.getData()))

            photo_file.setImageURI(data!!.getData()!!)
            photo_button.setText("Photo Uploaded")
        }
    }
}
