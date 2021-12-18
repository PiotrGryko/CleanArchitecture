package piotr.example.gitclient.details

import android.net.Uri
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.map
import dagger.hilt.android.lifecycle.HiltViewModel
import piotr.example.domain.Constants
import piotr.example.domain.model.entity.Project
import piotr.example.domain.repository.ProjectsRepository
import piotr.example.gitclient.BaseViewModel
import java.text.SimpleDateFormat
import javax.inject.Inject

@HiltViewModel
class DetailsActivityViewModel @Inject constructor(
    private val projectsRepository: ProjectsRepository,
    savedStateHandle: SavedStateHandle
) : BaseViewModel() {
    private val id = savedStateHandle.get<Long>(Constants.KEY_PROJECT_ID) ?: error("id not passed")
     val project = MutableLiveData<Project>()

    val projectName = project.map { it.name }
    val ownerName = project.map { it.owner.login }
    val avatar = project.map {
        when (it.owner.avatarUrl) {
            null -> Uri.EMPTY
            else -> Uri.parse(it.owner.avatarUrl)
        }
    }
    val creationDate = project.map { it.createdAt?.toString() }
    val private = project.map { it.private.toString() }


    init {
        launchNetworkOperation {
            project.postValue(projectsRepository.getProjectFromDatabase(id))
        }
    }


}