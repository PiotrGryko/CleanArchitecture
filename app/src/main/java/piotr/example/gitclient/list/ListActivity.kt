package piotr.example.gitclient.list

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import piotr.example.domain.model.value.ProjectListItem
import piotr.example.gitclient.R
import piotr.example.gitclient.databinding.ActivityListBinding
import piotr.example.gitclient.details.DetailsActivity

@AndroidEntryPoint
class ListActivity : AppCompatActivity() {

    private val viewModel: ListActivityViewModel by viewModels()
    private val adapter = ProjectsAdapter(::onListItemClicked)
    private lateinit var binding: ActivityListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_list)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter
        binding.swipeRefreshLayout.setOnRefreshListener { adapter.refresh() }
        setSupportActionBar(binding.toolbar)


        viewModel.projects.observe(this) {
            adapter.submitData(this.lifecycle, it)
        }
        adapter.addLoadStateListener {
            when {
                it.refresh is LoadState.NotLoading -> binding.swipeRefreshLayout.isRefreshing = false
                it.append is LoadState.Error -> Toast.makeText(this, "error", Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.options, menu);
        return super.onCreateOptionsMenu(menu)
    }

    private fun onListItemClicked(projectListItem: ProjectListItem, sharedView: View) {
        DetailsActivity.launch(this, sharedView, projectListItem.projectId)
    }
}