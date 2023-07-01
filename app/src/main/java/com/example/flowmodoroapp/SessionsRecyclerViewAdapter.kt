package com.example.flowmodoroapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.flowmodoroapp.data.Session
import com.example.flowmodoroapp.databinding.SessionItemBinding

class SessionsRecyclerViewAdapter(private val sessionList: ArrayList<Session>) :
    RecyclerView.Adapter<SessionsRecyclerViewAdapter.MyViewHolder>() {


      inner class MyViewHolder(val binding: SessionItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(session: Session) {
            binding.ivDate.text = session.date
            binding.ivTaskName.text = session.taskName
            binding.ivMinutes.text = session.minutes
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding:SessionItemBinding = SessionItemBinding.inflate(layoutInflater, parent, false)
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int = sessionList.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val session = sessionList[position]
        holder.bind(session)
    }

    fun setList(sessions:List<Session>){
        sessionList.clear()
        sessionList.addAll(sessions)
    }
}