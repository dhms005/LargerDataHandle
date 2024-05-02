package com.taskdemo.largerdatahandle

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.view.ViewTreeObserver.OnScrollChangedListener
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.taskdemo.largerdatahandle.adapter.ExamAdapter
import com.taskdemo.largerdatahandle.model.ExamData
import com.taskdemo.largerdatahandle.other.ClickListiner


class ExamActivity2 : AppCompatActivity() {
    var adapter: ExamAdapter? = null
    var recyclerView: RecyclerView? = null
    var listiner: ClickListiner? = null

    lateinit var nested: NestedScrollView

    private var currentDataList: MutableList<ExamData> = mutableListOf()

    var lastIndex: Int = 0

    private var isLoadingMore = false // Flag to prevent multiple loads


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exam)
        recyclerView = findViewById<View>(
            R.id.recyclerView
        ) as RecyclerView

        nested = findViewById<View>(
            R.id.nested
        ) as NestedScrollView


        listiner = object : ClickListiner() {
            override fun click(index: Int) {
                Toast.makeText(
                    this@ExamActivity2,
                    "clicked item index is $index",
                    Toast.LENGTH_LONG
                )
                    .show()
            }
        }

        adapter = ExamAdapter(
            application, listiner!!
        )
        recyclerView!!.adapter = adapter
        recyclerView!!.layoutManager = LinearLayoutManager(this@ExamActivity2)
//        adapter!!.setData(list)

        loadData(lastIndex, lastIndex + 100)
//        addDataTimer()

//        nested.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { v: NestedScrollView, scrollX: Int, scrollY: Int, oldScrollX: Int, oldScrollY: Int ->
//            if (!isLoadingMore) {
//                if (v.getChildAt(v.getChildCount() - 1) != null && isLastItemVisible(recyclerView!!)) {
//                    loadData(); // Function to fetch more data
//                    isLoadingMore = true; // Set flag to prevent multiple loads
//                }
//            }
//
//
//        } as NestedScrollView.OnScrollChangeListener)

        nested.getViewTreeObserver().addOnScrollChangedListener(OnScrollChangedListener {
            val view = nested.getChildAt(nested.getChildCount() - 1) as View
            val diff: Int = view.bottom - (nested.getHeight() + nested
                .getScrollY())
            if (diff == 0) {
                // your pagination code
//                progressBar.setVisibility(View.VISIBLE)

                if (!isLoadingMore) {
                    isLoadingMore = true
                    loadData(lastIndex, lastIndex + 100)
//                    Handler().postDelayed({
//
//                    }, 500)
                }

            }
        })

    }

    fun addDataTimer() {

        Handler().postDelayed({
            println("world")

            Log.e("@@@", "timer")
            loadData(100, 500)

        }, 2000)
    }

    private fun loadData(startIndex: Int, endIndex: Int) {
        val newList = getData(startIndex, endIndex) // Load first 1000 items
        currentDataList.addAll(newList)
        adapter!!.setData(currentDataList)

        lastIndex = currentDataList.size

        isLoadingMore = false

    }

    private fun getData(startIndex: Int, endIndex: Int): List<ExamData> {
        val list: MutableList<ExamData> = ArrayList()
        for (i in startIndex until endIndex) {
            list.add(
                ExamData(
                    "Exam $i",
                    "Sample Date",
                    "Sample Description"
                )
            )
        }
        return list
    }


    override fun onBackPressed() {
        super.onBackPressed()
    }

    private fun isLastItemVisible(recyclerView: RecyclerView): Boolean {
        val layoutManager = recyclerView.layoutManager as LinearLayoutManager?
        val lastVisibleItemPosition = layoutManager!!.findLastVisibleItemPosition()
        return lastVisibleItemPosition == adapter!!.itemCount - 1
    }

    private fun loadData() {
        // Implement your logic to fetch more data
        // Update the adapter with new data once received
        loadData(lastIndex, lastIndex + 100)
        isLoadingMore = false // Reset flag after successful load
    }

}