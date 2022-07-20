package com.example.dice4g.ui.profiles

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.dice4g.R
import com.example.dice4g.databinding.ItemProfileBinding
import com.example.dice4g.infra.models.Profile
import com.google.android.material.dialog.MaterialAlertDialogBuilder

interface ProfileListener {
    fun deleteProfile(profileId: String)

    fun useProfile(profileId: String)
}

class ProfileAdapter(
    val profileListener: ProfileListener,
    var profiles: List<Profile>
) : RecyclerView.Adapter<ProfileAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemProfileBinding) :
        RecyclerView.ViewHolder(binding.root) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemProfileBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = profiles[position]

        holder.binding.buttonUseProfile.setOnClickListener {
            profileListener.useProfile(item.id)
        }

        holder.binding.root.setOnLongClickListener onLongClickFun@{
            val context = it!!.context
            MaterialAlertDialogBuilder(context)
                .setMessage(it.context.getString(R.string.discard_profile))
                .setNegativeButton(context.getString(R.string.cancel)) { dialog, which ->
                    dialog.cancel()
                }
                .setPositiveButton(context.getString(R.string.remove)) { dialog, which ->
                    profileListener.deleteProfile(item.id)
                    dialog.dismiss()
                }
                .show()

            return@onLongClickFun true
        }
        holder.binding.textView.text = item.Name;
        holder.binding.root.setOnHoverListener { v, event -> true }
    }

    override fun getItemCount(): Int {
        return profiles.count()
    }

}