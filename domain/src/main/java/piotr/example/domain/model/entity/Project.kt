package piotr.example.domain.model.entity

import android.net.Uri
import java.util.*

data class Project(
    val id: Long,
    val name: String,
    val fullName: String?,
    val private: Boolean,
    val createdAt: Date?,
    val owner: ProjectOwner
)

data class ProjectOwner(
    val id: Long,
    val login: String,
    val avatarUrl: String?,
    val url: String
)


