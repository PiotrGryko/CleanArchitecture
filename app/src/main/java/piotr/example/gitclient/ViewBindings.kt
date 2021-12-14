package piotr.example.gitclient

import android.net.Uri
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

object ViewBindings {

    @JvmStatic
    @BindingAdapter("isVisible")
    fun setIsVisible(view: View, visible: Boolean) {
        view.isVisible = visible
    }
    @JvmStatic
    @BindingAdapter("imageUri")
    fun setImageUri(view: ImageView, uri:Uri?) {
        uri?.let{
            Glide.with(view).load(it).into(view)
        }
    }
}