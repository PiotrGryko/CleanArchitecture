package piotr.example.gitclient.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import piotr.example.domain.model.value.ProjectListItem
import piotr.example.gitclient.databinding.ItemProjectListBinding

class ProjectsAdapter(val onClickListener: (ProjectListItem, View) -> Unit) :
    PagingDataAdapter<ProjectListItem, ProjectsAdapter.ProjectViewHolder>(ProjectsComparator) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ProjectViewHolder(ItemProjectListBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: ProjectViewHolder, position: Int) {
        val item = getItem(position)
        item?.let {
            holder.bind(it)
            holder.itemView.setOnClickListener { onClickListener(item, holder.binding.avatar) }
        }
    }

    inner class ProjectViewHolder(val binding: ItemProjectListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ProjectListItem) {
            binding.item = item
        }
    }
}

object ProjectsComparator : DiffUtil.ItemCallback<ProjectListItem>() {
    override fun areItemsTheSame(oldItem: ProjectListItem, newItem: ProjectListItem) =
        oldItem.projectId == newItem.projectId

    override fun areContentsTheSame(oldItem: ProjectListItem, newItem: ProjectListItem) =
        oldItem == newItem

}