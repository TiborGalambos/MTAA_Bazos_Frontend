package com.example.myapplication.models

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.myapplication.R
import com.example.myapplication.loginLogic.Constants
import com.squareup.picasso.Picasso

class ItemListAdapter(private val context: Context,
                      private val authors: Array<String?>,
                      private val categories: Array<String?>,
                      private val titles: Array<String?>,
                      private val contents: Array<String?>,
                      private val prices: Array<String?>,
                      private val photos: Array<String?>,
                      private val ids: Array<String?>) : BaseAdapter() {

    private lateinit var author: TextView
    private lateinit var category: TextView
    private lateinit var title: TextView
    private lateinit var content: TextView
    private lateinit var price: TextView
    private lateinit var photo: ImageView

    override fun getCount(): Int {
        return authors.size
    }
    override fun getItem(position: Int): Any {
        return position
    }
    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    @SuppressLint("ViewHolder")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View? {
        val thisView = LayoutInflater.from(context).inflate(R.layout.row, parent, false)

        author = thisView.findViewById(R.id.author_r)
        category = thisView.findViewById(R.id.category_r)
        title = thisView.findViewById(R.id.title_r)
        content = thisView.findViewById(R.id.content_r)
        price = thisView.findViewById(R.id.price_r)
        photo = thisView.findViewById(R.id.photo_r)

        author.text = authors[position]?.replace("(^\\(|\\)$)", "")
        category.text = categories[position]?.replace("(^\\(|\\)$)", "")
        title.text = titles[position]?.replace("(^\\(|\\)$)", "")
        content.text = contents[position]?.replace("(^\\(|\\)$)", "")
        price.text = prices[position]
        val path = Constants.BASE_URL_FOR_PHOTO.plus(photos[position])
        Picasso.with(context).load(path).into(photo)

        return thisView
    }
}