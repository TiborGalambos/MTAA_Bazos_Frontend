package com.example.myapplication.activityLogic

import APIclient
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.R
import com.example.myapplication.loginLogic.SessionManager
import com.example.myapplication.models.ItemList
import com.example.myapplication.models.MyItemListAdapter
import kotlinx.android.synthetic.main.activity_items.*
import kotlinx.android.synthetic.main.row.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.create
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class Items : AppCompatActivity() {

    lateinit var sessionManager: SessionManager
    private lateinit var apiClient: APIclient
    lateinit var ids:Array<String?>

    @ExperimentalMultiplatform
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_items)
        supportActionBar?.hide()

        var category_word:String? = ""

        var search_text:String? = ""

        search_text = intent.getStringExtra("search_word")
        category_word = intent!!.getStringExtra("category_word")

        apiClient = APIclient()
        sessionManager = SessionManager(this)

        if(search_text == "") {

            Log.d("1 ALL ITEMS: ", "this is the text: " + search_text)
            apiClient.getApiService(this).showAllItems(token = "Token ${sessionManager.fetchAuthToken()}").enqueue(object : Callback<ItemList> {
                override fun onFailure(call: Call<ItemList>, t: Throwable) {
                    //println("[HomeFragment] FAILURE. Is the server running? " + t.stackTrace)
                }

                // @SuppressLint("SetTextI18n")
                override fun onResponse(call: Call<ItemList>, response: Response<ItemList>) {
                    //println("[HomeFragment] SUCCESS. Token ${sessionManager.fetchAuthToken()}. Response: " + response.toString())

                    val itemList = response.body()
                    Log.d("SIZE", " " + itemList?.items?.size)
                    //itemList?.items?.size

                    val authors = arrayOfNulls<String>(itemList!!.items!!.size)
                    for (i: Int in itemList!!.items!!.indices)
                        authors[i] = (itemList!!.items[i]!!.author_name).replace("\"", "")

                    val categories = arrayOfNulls<String>(itemList!!.items!!.size)
                    for (i: Int in itemList.items.indices)
                        categories[i] = (itemList.items[i].category).toString().replace("\"", "")

                    val titles = arrayOfNulls<String>(itemList!!.items!!.size)
                    for (i: Int in itemList.items.indices)
                        titles[i] = ((itemList.items[i].title).replace("\"", ""))

                    val contents = arrayOfNulls<String>(itemList!!.items!!.size)
                    for (i: Int in itemList.items.indices)
                        contents[i] = (itemList.items[i].content.toString()).replace("\"", "")

                    val prices = arrayOfNulls<String>(itemList!!.items!!.size)
                    for (i: Int in itemList.items.indices)
                        prices[i] = (itemList.items[i].price).toString()

                    val photos = arrayOfNulls<String>(itemList!!.items!!.size)
                    for (i: Int in itemList.items.indices)
                        photos[i] = itemList.items[i].photo

                    ids = arrayOfNulls<String>(itemList!!.items!!.size)
                    for (i: Int in itemList.items.indices)
                        ids[i] = (itemList.items[i].id).toString()


                    val adapter = MyItemListAdapter(
                            this@Items,
                            authors,
                            categories,
                            titles,
                            contents,
                            prices,
                            photos,
                            ids
                    )

                    list_id.adapter = adapter
                }
            })
        }
        else if(category_word != "" && search_text == null)
        {
            val text = "\"" + category_word + "\""
            val body: RequestBody = create("text/plain".toMediaTypeOrNull(), text!!)

            Log.d("SEARCHING BY CATEGORY: ", "this is the text: " + category_word)
            apiClient.getApiService(this).searchThisItemByCategory(token = "Token ${sessionManager.fetchAuthToken()}", body).enqueue(object : Callback<ItemList> {
                override fun onFailure(call: Call<ItemList>, t: Throwable) {
                    //println("[HomeFragment] FAILURE. Is the server running? " + t.stackTrace)
                }

                // @SuppressLint("SetTextI18n")
                override fun onResponse(call: Call<ItemList>, response: Response<ItemList>) {
                    //println("[HomeFragment] SUCCESS. Token ${sessionManager.fetchAuthToken()}. Response: " + response.toString())

                    val itemList = response.body()
                    Log.d("SIZE", " " + itemList?.items?.size)
                    //itemList?.items?.size


                    val authors = arrayOfNulls<String>(itemList!!.items!!.size)
                    for (i: Int in itemList!!.items!!.indices)
                        authors[i] = (itemList!!.items[i]!!.author_name).replace("\"", "")

                    val categories = arrayOfNulls<String>(itemList!!.items!!.size)
                    for (i: Int in itemList.items.indices)
                        categories[i] = (itemList.items[i].category).toString().replace("\"", "")

                    val titles = arrayOfNulls<String>(itemList!!.items!!.size)
                    for (i: Int in itemList.items.indices)
                        titles[i] = ((itemList.items[i].title).replace("\"", ""))

                    val contents = arrayOfNulls<String>(itemList!!.items!!.size)
                    for (i: Int in itemList.items.indices)
                        contents[i] = (itemList.items[i].content.toString()).replace("\"", "")

                    val prices = arrayOfNulls<String>(itemList!!.items!!.size)
                    for (i: Int in itemList.items.indices)
                        prices[i] = (itemList.items[i].price).toString()

                    val photos = arrayOfNulls<String>(itemList!!.items!!.size)
                    for (i: Int in itemList.items.indices)
                        photos[i] = itemList.items[i].photo

                    ids = arrayOfNulls<String>(itemList!!.items!!.size)
                    for (i: Int in itemList.items.indices)
                        ids[i] = (itemList.items[i].id).toString()


                    val adapter = MyItemListAdapter(
                            this@Items,
                            authors,
                            categories,
                            titles,
                            contents,
                            prices,
                            photos,
                            ids
                    )

                    list_id.adapter = adapter
                }
            })
        }
        else if(search_text != null)
        {
            Log.d("3 SEARCHING BY TEXT: ", "this is the text: " + search_text)
            Log.d("3 NOT SEARCH BY CAT: ", "this is the text: " + category_word)
            val text = search_text
            val body: RequestBody = create("text/plain".toMediaTypeOrNull(), text!!)


            apiClient.getApiService(this).searchThisItemByKeyword(token = "Token ${sessionManager.fetchAuthToken()}", body).enqueue(object : Callback<ItemList> {
                override fun onFailure(call: Call<ItemList>, t: Throwable) {
                    //println("[HomeFragment] FAILURE. Is the server running? " + t.stackTrace)
                }

                // @SuppressLint("SetTextI18n")
                override fun onResponse(call: Call<ItemList>, response: Response<ItemList>) {
                    //println("[HomeFragment] SUCCESS. Token ${sessionManager.fetchAuthToken()}. Response: " + response.toString())

                    val itemList = response.body()
                    Log.d("SIZE", " " + itemList?.items?.size)
                    //itemList?.items?.size


                    val authors = arrayOfNulls<String>(itemList!!.items!!.size)
                    for (i: Int in itemList!!.items!!.indices)
                        authors[i] = (itemList!!.items[i]!!.author_name).replace("\"", "")

                    val categories = arrayOfNulls<String>(itemList!!.items!!.size)
                    for (i: Int in itemList.items.indices)
                        categories[i] = (itemList.items[i].category).toString().replace("\"", "")

                    val titles = arrayOfNulls<String>(itemList!!.items!!.size)
                    for (i: Int in itemList.items.indices)
                        titles[i] = ((itemList.items[i].title).replace("\"", ""))

                    val contents = arrayOfNulls<String>(itemList!!.items!!.size)
                    for (i: Int in itemList.items.indices)
                        contents[i] = (itemList.items[i].content.toString()).replace("\"", "")

                    val prices = arrayOfNulls<String>(itemList!!.items!!.size)
                    for (i: Int in itemList.items.indices)
                        prices[i] = (itemList.items[i].price).toString()

                    val photos = arrayOfNulls<String>(itemList!!.items!!.size)
                    for (i: Int in itemList.items.indices)
                        photos[i] = itemList.items[i].photo

                    ids = arrayOfNulls<String>(itemList!!.items!!.size)
                    for (i: Int in itemList.items.indices)
                        ids[i] = (itemList.items[i].id).toString()


                    val adapter = MyItemListAdapter(
                            this@Items,
                            authors,
                            categories,
                            titles,
                            contents,
                            prices,
                            photos,
                            ids
                    )

                    list_id.adapter = adapter
                }
            })
        }


//        list_id.setOnItemClickListener{ _, _, position, _ ->
//            val idOfSelectedItem = ids!![position]
//            Log.d("NUMBER: ", "" + idOfSelectedItem)
//
//        }
    }
}