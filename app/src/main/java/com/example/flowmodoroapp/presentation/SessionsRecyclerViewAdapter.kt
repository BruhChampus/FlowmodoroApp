package com.example.flowmodoroapp.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.flowmodoroapp.utils.ClickListener
import com.example.flowmodoroapp.data.Session
import com.example.flowmodoroapp.databinding.SessionItemBinding

class SessionsRecyclerViewAdapter(private val sessionList: ArrayList<Session>, private val clickListener: ClickListener) :
    RecyclerView.Adapter<SessionsRecyclerViewAdapter.MyViewHolder>() {


      inner class MyViewHolder(private val binding: SessionItemBinding,  private val clickListener: ClickListener) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(session: Session) {
            binding.ivDate.text = session.date
            binding.ivTaskName.text = session.taskName
            binding.ivMinutes.text = session.minutes
            binding.ivTaskName.setOnClickListener {
              clickListener.onClick(session)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding:SessionItemBinding = SessionItemBinding.inflate(layoutInflater, parent, false)
        return MyViewHolder(binding,clickListener)
    }

    override fun getItemCount(): Int = sessionList.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val session = sessionList[position]
        holder.bind(session)
    }
}