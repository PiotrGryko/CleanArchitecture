package piotr.example.data.api

import android.net.Uri
import android.util.Log
import piotr.example.domain.model.entity.Project
import piotr.example.domain.model.entity.ProjectOwner
import java.util.*

data class ProjectsResponse(val items: List<RemoteProject>)

data class RemoteProject(
    val id: Long,
    val name: String,
    val fullName: String?,
    val private: Boolean,
    val createdAt: Date?,
    val owner: RemoteOwner
)

data class RemoteOwner(
    val id: Long,
    val login: String,
    val avatarUrl: String,
    val url: String
)


fun RemoteOwner.toProjectOwner() = ProjectOwner(
    this.id,
    this.login,
    this.avatarUrl,
    this.url
)
fun RemoteProject.toProject() = Project(
    this.id,
    this.name,
    this.fullName,
    this.private,
    this.createdAt,
    this.owner.toProjectOwner()

)

