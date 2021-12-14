package piotr.example.data.db

import android.net.Uri
import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import piotr.example.data.api.RemoteOwner
import piotr.example.data.api.RemoteProject
import piotr.example.domain.model.entity.Project
import piotr.example.domain.model.entity.ProjectOwner
import java.util.*

@Entity(tableName = "project")
data class ProjectEntity(
    @PrimaryKey
    val id: Long,
    @Embedded(prefix = "owner_")
    val owner:OwnerEntity,
    val name: String,
    val fullName: String?,
    val isPrivate: Boolean,
    val createdAt: Date?
)

data class OwnerEntity(
    val id: Long,
    val login: String,
    val avatarUrl: String?,
    val url: String
)

fun OwnerEntity.toProjectOwner() = ProjectOwner(
    this.id,
    this.login,
    this.avatarUrl,
    this.url
)

fun ProjectEntity.toProject() = Project(
    this.id,
    this.name,
    this.fullName,
    this.isPrivate,
    this.createdAt,
    this.owner.toProjectOwner()
)


fun ProjectOwner.toOwnerEntity() = OwnerEntity(
    this.id,
    this.login,
    this.avatarUrl,
    this.url
)

fun Project.toProjectEntity() = ProjectEntity(
    this.id,
    this.owner.toOwnerEntity(),
    this.name,
    this.fullName,
    this.private,
    this.createdAt
)
