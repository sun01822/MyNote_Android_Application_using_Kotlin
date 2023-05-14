package com.example.mynote

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mynote.models.NoteModel

class CustomAdapter(private val dataList: ArrayList<NoteModel>):RecyclerView.Adapter<CustomAdapter.CustomViewHolder>() {
    private var mOnClickListener: OnClickListener? = null
    private var mDeleteListener: OnClickListener? = null
    class CustomViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.sample_title)
        val description: TextView = itemView.findViewById(R.id.sample_description)
        val time: TextView = itemView.findViewById(R.id.sample_time)
        val deleteTextView: TextView = itemView.findViewById(R.id.delete_textView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val item = LayoutInflater.from(parent.context).inflate(R.layout.custom_layout, parent, false)
        return CustomViewHolder(item)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        var singleData = dataList[position]
        holder.title.text = singleData.title
        val temp = dataList[position].description
        val shortString = temp?.let { Math.min(it.length, 60) }?.let { temp.substring(0, it) }
        holder.description.text = "$shortString ..."
        holder.time.text = singleData.time

        holder.itemView.setOnClickListener {
            if(mOnClickListener != null) {
                mOnClickListener!!.onClick(position, singleData)
            }
        }
        holder.deleteTextView.setOnClickListener{
            if(mDeleteListener != null) {
                mDeleteListener!!.onDelete(position, singleData)
            }
        }
    }

    fun setOnClickListener(onClickListener: OnClickListener){
        this.mOnClickListener = onClickListener
        this.mDeleteListener = onClickListener
    }

    interface OnClickListener {
        fun onClick(position: Int, note: NoteModel)
        fun onDelete(position: Int, note: NoteModel)
    }
}