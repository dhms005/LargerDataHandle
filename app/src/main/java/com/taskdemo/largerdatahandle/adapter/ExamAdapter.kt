package com.taskdemo.largerdatahandle.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.taskdemo.largerdatahandle.other.ClickListiner
import com.taskdemo.largerdatahandle.databinding.ExamCardBinding
import com.taskdemo.largerdatahandle.model.ExamData
import kotlinx.coroutines.*

class ExamAdapter(
    context: Context, listiner: ClickListiner
) : RecyclerView.Adapter<ExamAdapter.ExamViewHolder>() {
    val list = ArrayList<ExamData>()
    var context: Context
    var listiner: ClickListiner
//    private val courses = ArrayList<ExamData>()

    init {
        this.context = context
        this.listiner = listiner
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ExamViewHolder {
        val examBinding =
            ExamCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ExamViewHolder(examBinding)
    }

    inner class ExamViewHolder(var viewBinding: ExamCardBinding) :
        RecyclerView.ViewHolder(viewBinding.root) {

        fun bind(data: ExamData) {
            viewBinding.examName.text = data.name
            viewBinding.examDate.text = data.date
            viewBinding.examMessage.text = data.message

            // If not cached, compute details asynchronously
//            data?.let { computeDetails(it) }

        }

        private fun computeDetails(data: ExamData) {
            // Perform heavy computation asynchronously
            CoroutineScope(Dispatchers.Default).launch {
                val startTime = System.currentTimeMillis()

                val details = performHeavyComputation(data)

                val endTime = System.currentTimeMillis()
                val duration = endTime - startTime
                Log.d("HeavyComputation", "Time taken for item ${data.name}: $duration ms")

                // Cache the computed details
//                detailsCache[post.id] = "$details & Time taken is:  $duration ms"

                // Update UI in the main thread
                withContext(Dispatchers.Main) {
                    viewBinding.examName.text = data.name
                    viewBinding.examDate.text = data.date
                    viewBinding.examMessage.text = data.message
                    Log.d("HeavyComputation_Detail", details)
                }
            }
        }

        private suspend fun performHeavyComputation(data: ExamData): String {
            // Simulate heavy computation by delaying for a few seconds
            delay(200) // Adjust the delay as per your computation needs
            return "Additional details for post ${data.name}"
        }
    }

    override fun onBindViewHolder(holder: ExamViewHolder, position: Int) {
        val data = list[position]
        holder.bind(data)
    }


//    override fun onBindViewHolder(
//        viewHolder: ExamViewHolder,
//        position: Int
//    ) {
//        viewHolder.bind(data)
////        val taskModel = list[position]
////        val index = viewHolder.adapterPosition
//        viewHolder.viewBinding.examName.text = list[position].name
//        viewHolder.viewBinding.examDate.text = list[position].date
//        viewHolder.viewBinding.examMessage.text = list[position].message
//        viewHolder.viewBinding.view.setOnClickListener { listiner.click(position) }
//    }

    fun setData(course: List<ExamData>) {
        list.clear()
        list.addAll(course)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onAttachedToRecyclerView(
        recyclerView: RecyclerView
    ) {
        super.onAttachedToRecyclerView(recyclerView)
    }
}