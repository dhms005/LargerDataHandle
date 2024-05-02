package com.taskdemo.largerdatahandle

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.taskdemo.largerdatahandle.adapter.ExamAdapter
import com.taskdemo.largerdatahandle.model.ExamData
import com.taskdemo.largerdatahandle.other.ClickListiner
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ExamActivity : AppCompatActivity() {
    var adapter: ExamAdapter? = null
    var recyclerView: RecyclerView? = null
    var listiner: ClickListiner? = null

    private var currentDataList: MutableList<ExamData> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exam)
        var list: List<ExamData> = ArrayList()
        list = data
        recyclerView = findViewById<View>(
            R.id.recyclerView
        ) as RecyclerView
        listiner = object : ClickListiner() {
            override fun click(index: Int) {
                Toast.makeText(this@ExamActivity, "clicked item index is $index", Toast.LENGTH_LONG)
                    .show()
            }
        }

        adapter = ExamAdapter(
            application, listiner!!
        )
        recyclerView!!.adapter = adapter
        recyclerView!!.layoutManager = LinearLayoutManager(this@ExamActivity)
//        adapter!!.setData(list)

        loadData()
    }

    private  fun loadData() {
        val newList = getData(0, 100) // Load first 1000 items
        val diffResult = DiffUtil.calculateDiff(object : DiffUtil.Callback() {
            override fun getOldListSize(): Int = currentDataList.size

            override fun getNewListSize(): Int = newList.size

            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return currentDataList[oldItemPosition].name == newList[newItemPosition].name
            }

            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                val oldData = currentDataList[oldItemPosition]
                val newData = newList[newItemPosition]
                return oldData.name == newData.name &&
                        oldData.date == newData.date &&
                        oldData.message == newData.message
            }
        })

        currentDataList.addAll(newList)
        adapter!!.setData(currentDataList)
//        adapter!!.submitList(currentDataList)
        diffResult.dispatchUpdatesTo(adapter!!) // Efficiently update RecyclerView


            loadData2()



    }

    private suspend fun performHeavyComputation(): String {
        // Simulate heavy computation by delaying for a few seconds
        delay(2000) // Adjust the delay as per your computation needs
        return "Additional details for post ${currentDataList.size}"
    }

    private fun loadData2() {
        val newList = getData(100, 200) // Load first 1000 items
        val diffResult = DiffUtil.calculateDiff(object : DiffUtil.Callback() {
            override fun getOldListSize(): Int = currentDataList.size

            override fun getNewListSize(): Int = newList.size

            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return currentDataList[oldItemPosition].name == newList[newItemPosition].name
            }

            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                val oldData = currentDataList[oldItemPosition]
                val newData = newList[newItemPosition]
                return oldData.name == newData.name &&
                        oldData.date == newData.date &&
                        oldData.message == newData.message
            }
        })

        currentDataList.addAll(newList)
        adapter!!.setData(currentDataList)
//        adapter!!.submitList(currentDataList)
        diffResult.dispatchUpdatesTo(adapter!!) // Efficiently update RecyclerView


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

    // Sample data for RecyclerView 
    private val data: List<ExamData>
        private get() {
            val list: MutableList<ExamData> = ArrayList()

            for (i in 0..10) {
                list.add(
                    ExamData(
                        "First Exam",
                        "May 23, 2015",
                        "Best Of Luck"
                    )
                )
            }

            adapter?.notifyDataSetChanged()

            for (i in 0..10) {
                list.add(
                    ExamData(
                        "Second Exam",
                        "June 09, 2015",
                        "b of l"
                    )
                )
            }

            for (i in 0..10) {
                list.add(
                    ExamData(
                        "My Test Exam",
                        "April 27, 2017",
                        "This is testing exam .."
                    )
                )
            }



            return list
        }
}