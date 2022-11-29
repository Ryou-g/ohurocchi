import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.ohurocchi.User
import com.example.ohurocchi.databinding.TaskListItemBinding
import com.google.android.gms.tasks.Task
import java.text.SimpleDateFormat
import java.util.*

class TaskAdapter : ListAdapter<User, TaskViewHolder>(diffUtilItemCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view = TaskListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TaskViewHolder(view)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class TaskViewHolder(
    private val binding: TaskListItemBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(task: User) {
        binding.titleTextView.text = task.title
        binding.dateTextView.text = task.createdAt
            //SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.JAPANESE).format(task.createdAt)
    }
}

private val diffUtilItemCallback = object : DiffUtil.ItemCallback<User>() {
    override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
        return oldItem == newItem
    }

    override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
        return oldItem.id == newItem.id
    }
}