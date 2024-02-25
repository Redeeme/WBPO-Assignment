package sk.doxxbet.doxxbetui.wpbo_assignment.ui

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.NetworkPolicy
import com.squareup.picasso.Picasso
import sk.doxxbet.doxxbetui.wpbo_assignment.databinding.UserItemBinding

class UserAdapter(val context: Context, val delegate: UserClickDelegate) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    interface UserClickDelegate {
        fun userClick(isChecked: Boolean, id: Int)
    }

    var users: List<UserModel>? = null
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = UserItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(binding)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val user = users?.get(position)
        val userHolder = holder as UserViewHolder
        if (user != null) {
            userHolder.binding.nameTextView.text = "${user.firstName} ${user.lastName}"
            try {
                Picasso.with(holder.itemView.context)
                    .load(user?.avatar)
                    .networkPolicy(NetworkPolicy.NO_CACHE)
                    .into(userHolder.binding.avatarImageView)
            } catch (e: Throwable) {
                println(e)
            }
            holder.binding.toggleButton.setOnClickListener {
                delegate.userClick(holder.binding.toggleButton.isChecked, user.id)

            }
        }
    }

    override fun getItemCount(): Int {
        return users?.size ?: 0
    }

    inner class UserViewHolder(val binding: UserItemBinding) :
        RecyclerView.ViewHolder(binding.root)

}
