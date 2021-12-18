package piotr.example.gitclient.list

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import piotr.example.domain.model.value.ProjectListItem
import piotr.example.domain.model.value.ProjectsSortType
import piotr.example.gitclient.R
import piotr.example.gitclient.databinding.ActivityListBinding
import piotr.example.gitclient.details.DetailsActivity

@AndroidEntryPoint
class ListActivity : AppCompatActivity(), SearchView.OnQueryTextListener {

    private val viewModel: ListActivityViewModel by viewModels()
    private val adapter = ProjectsAdapter(::onListItemClicked)
    private lateinit var binding: ActivityListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_list)
        binding.lifecycleOwner=this
        binding.viewModel=viewModel

        setSupportActionBar(binding.toolbar)

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter
        binding.swipeRefreshLayout.setOnRefreshListener { adapter.refresh() }
        binding.searchView.setOnQueryTextListener(this)

        viewModel.projects.observe(this) {
            adapter.submitData(this.lifecycle, it)
        }
        adapter.addLoadStateListener {
            when {
                it.refresh is LoadState.NotLoading ->{
                    binding.swipeRefreshLayout.isRefreshing = false
                    viewModel.setPlaceholderVisibility(adapter.itemCount==0)
                }
                it.append is LoadState.Error -> Toast.makeText(this, "error", Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        return true
    }
    override fun onQueryTextSubmit(query: String?): Boolean {
        viewModel.setQuery(query)
        return true
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.options, menu);
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.issues -> viewModel.setSortType(ProjectsSortType.ISSUES)
            R.id.forks -> viewModel.setSortType(ProjectsSortType.FORKS)
            R.id.stars -> viewModel.setSortType(ProjectsSortType.STARS)
        }
        return super.onOptionsItemSelected(item)
    }


    private fun onListItemClicked(projectListItem: ProjectListItem, sharedView: View) {
        DetailsActivity.launch(this, sharedView, projectListItem.projectId)
    }
}