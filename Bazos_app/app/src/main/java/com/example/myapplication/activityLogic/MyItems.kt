package com.example.myapplication.activityLogic

import APIclient
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.R
import com.example.myapplication.loginLogic.SessionManager
import com.example.myapplication.models.ItemList
import com.example.myapplication.models.MyItemListAdapter
import kotlinx.android.synthetic.main.activity_items.*
import kotlinx.android.synthetic.main.activity_my_items.*
import kotlinx.android.synthetic.main.row.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MyItems : AppCompatActivity() {

    lateinit var sessionManager: SessionManager
    private lateinit var apiClient: APIclient
    lateinit var ids:Array<String?>
    var IDofItem:Int = 0

    @ExperimentalMultiplatform
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_items)
        supportActionBar?.hide()

        apiClient = APIclient()
        sessionManager = SessionManager(this)

        apiClient.getApiService(this).showAllMyItems(token = "Token ${sessionManager.fetchAuthToken()}").
        enqueue(object : Callback<ItemList> {
            override fun onFailure(call: Call<ItemList>, t: Throwable) {
                //println("[HomeFragment] FAILURE. Is the server running? " + t.stackTrace)

                Log.d("FAIL ", " --------something went wrong--------" +  t)
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
                    categories[i] = (itemList.items[i].category).replace("\"", "")

                val titles = arrayOfNulls<String>(itemList!!.items!!.size)
                for (i: Int in itemList.items.indices)
                    titles[i] = (itemList.items[i].title).replace("\"", "")

                val contents = arrayOfNulls<String>(itemList!!.items!!.size)
                for (i: Int in itemList.items.indices)
                    contents[i] = (itemList.items[i].content).replace("\"", "")

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
                        this@MyItems,
                        authors,
                        categories,
                        titles,
                        contents,
                        prices,
                        photos,
                        ids
                )

                my_list_id.adapter = adapter
            }
        })

        my_list_id.setOnItemClickListener{ _, _, position, _ ->

            val idOfSelectedItem = ids!![position]
            Log.d("NUMBER: ", "" + idOfSelectedItem)

            IDofItem = idOfSelectedItem!!.toInt()

            val intent = Intent(this, ItemDetailActivity::class.java)
            intent.putExtra("id",IDofItem);
            startActivity(intent)
        }

    }

}