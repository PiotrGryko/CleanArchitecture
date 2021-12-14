package piotr.example.gitclient

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

open class BaseViewModel : ViewModel() {
    fun launchNetworkOperation(block: suspend CoroutineScope.() -> Unit) = viewModelScope.launch { block() }
}