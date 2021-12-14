package piotr.example.gitclient.details

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import piotr.example.gitclient.R
import piotr.example.gitclient.databinding.ActivityDetailsBinding
import androidx.core.view.ViewCompat

import androidx.core.app.ActivityOptionsCompat
import dagger.hilt.android.AndroidEntryPoint
import piotr.example.domain.Constants
import piotr.example.gitclient.list.ListActivityViewModel


@AndroidEntryPoint
class DetailsActivity : AppCompatActivity() {

    companion object {
        fun launch(caller: Activity,view: View, projectId:Long) {
            val intent = Intent(caller, DetailsActivity::class.java)
            intent.putExtra(Constants.KEY_PROJECT_ID,projectId)
            val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                caller,
                view,
                ViewCompat.getTransitionName(view)!!
            )
            caller.startActivity(intent, options.toBundle())
        }
    }

    lateinit var binding: ActivityDetailsBinding
    private val viewModel: DetailsActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_details)
        binding.lifecycleOwner=this
        binding.viewModel=viewModel


    }
}