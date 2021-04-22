package com.example.myapplication.activityLogic

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.R
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.spinner
import kotlinx.android.synthetic.main.activity_new_item.*

//import retrofit2.Call
//import retrofit2.Callback
//import retrofit2.Response

class HomeScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()

//        val token = intent.getSerializableExtra("token")

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
                    Toast.makeText(this@HomeScreen, getString(R.string.selected_item) + " " + "" + cat_list[position], Toast.LENGTH_SHORT ).show()
                }
                override fun onNothingSelected(p0: AdapterView<*>?) {
                }
            }
        }



        my_profile_button.setOnClickListener{
            val intent = Intent(this, MyProfile::class.java)
            startActivity(intent)
        }

        new_item_button.setOnClickListener{
            val intent = Intent(this, NewItem::class.java)
            startActivity(intent)
        }
        search_button.setOnClickListener{


            var search_word: String = search_bar.text.toString()
            val intent = Intent(this, Items::class.java)
            intent.putExtra("search_word", search_word);
            startActivity(intent)
        }

        category_button.setOnClickListener{

            var search_category: String = cat_list[pos].toString()
            val intent = Intent(this, Items::class.java)
            intent.putExtra("category_word", search_category);
            startActivity(intent)

        }

    }
}