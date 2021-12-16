package piotr.example.domain.model.value

import android.net.Uri
import piotr.example.domain.model.entity.Project

data class ProjectListItem(
    val projectId: Long,
    val name: String,
    val ownerLogin: String,
    val avatar: Uri?
) {
    val isAvatarVisible = avatar != null
}

fun Project.toProjectListItem() = ProjectListItem(
    this.id,
    this.name,
    this.owner.login,
    when (this.owner.avatarUrl) {
        null -> null
        else -> Uri.parse(this.owner.avatarUrl)
    }
)