package com.github.microkibaco.honey

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.graphics.Rect
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class HoneyActivity : Activity() {

    private lateinit var honeyAdapter: HoneyAdapter

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_honey)

        val brands = parseBrands(this).BeeBrands
        val recyclerView: RecyclerView = findViewById(R.id.recycler_view_key)
        val imageView: ImageView = findViewById(R.id.image_view_key)
        val product: TextView = findViewById(R.id.product)
        val price: TextView = findViewById(R.id.price)
        val detail: TextView = findViewById(R.id.detail)
        val order: TextView = findViewById(R.id.order)
        val nestedScrollView = findViewById<NestedScrollView>(R.id.nestedScrollView)
        val editText = findViewById<EditText>(R.id.right_edit_text)

        nestedScrollView.viewTreeObserver.addOnGlobalLayoutListener {
            val r = Rect()
            nestedScrollView.getWindowVisibleDisplayFrame(r)
            val screenHeight = nestedScrollView.rootView.height
            val keypadHeight = screenHeight - r.bottom

            if (keypadHeight > screenHeight * 0.15) { // 键盘高度超过屏幕的15%时
                val editTextPosition = IntArray(2)
                editText.getLocationOnScreen(editTextPosition)
                val distanceToTop = editTextPosition[1] - r.top

                // 如果EditText距离顶部不足50dp，进行滚动
                if (distanceToTop < 50.dpToPx(this)) {
                    nestedScrollView.post {
                        nestedScrollView.smoothScrollBy(0, 50.dpToPx(this) - distanceToTop)
                    }
                }
            }
        }

        order.setOnClickListener {
            Toast.makeText(this,"order success",Toast.LENGTH_SHORT).show()
        }

        // 设置默认值为1，并监听文本变化
        editText.setText("1")
        editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                val text = s.toString()
                if (text.isEmpty() || text.toInt() < 1) {
                    editText.setText("1")
                    editText.setSelection(editText.text.length)
                }
                updateTotalPrice(editText.text.toString().toInt(), price.text.toString().replace("Product Price: $",""))
            }
        })

        honeyAdapter = HoneyAdapter(brands) { brand ->
            Glide.with(this)
                .load(brand.ImageURL)
                .into(imageView)
            product.text = "Product: " + brand.Brand
            price.text = "Product Price: $" + brand.Price
            detail.text = brand.Description

            updateTotalPrice(editText.text.toString().toInt(), brand.Price)
        }

        recyclerView.apply {
            layoutManager = GridLayoutManager(this@HoneyActivity, 5)
            adapter = honeyAdapter
            addItemDecoration(
                GridSpacingItemDecoration(5, 16, true)
            )
        }

        // 默认选中第一个品牌，并加载图片
        honeyAdapter.onItemClicked(brands[0])
    }

    @SuppressLint("SetTextI18n")
    private fun updateTotalPrice(quantity: Int, priceText: String) {
        val price = priceText.toDoubleOrNull() ?: 0.0
        val total = quantity * price
        val totalPrice: TextView = findViewById(R.id.totalPrice)
        totalPrice.text = "Total Price: $" + String.format("%.2f", total)
    }

    private fun Int.dpToPx(context: Context): Int {
        return (this * context.resources.displayMetrics.density).toInt()
    }
}
