package com.rizki.submissionakhir

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.rizki.submissionakhir.databinding.ActivityMainBinding
import com.rizki.submissionakhir.networking.ConfigApi
import com.rizki.submissionakhir.response.GithubUser
import com.rizki.submissionakhir.response.ResponseGithub
import com.rizki.submissionakhir.view.UserDetailActivity
import com.rizki.submissionakhir.view.UsersAdapter
import com.rizki.submissionakhir.view.favorite.UserFavoriteActivity
import com.rizki.submissionakhir.view.setting.SettingAppActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_option,menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu?.findItem(R.id.cari)?.actionView as SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = resources.getString(R.string.hint_search)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                Toast.makeText(this@MainActivity,"Mencari " + query, Toast.LENGTH_SHORT).show()
                searchView.clearFocus()
                cariUserGithub(query?:"")
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.favorituser -> {
                startActivity(Intent(this,UserFavoriteActivity::class.java))
                true
            }
            R.id.settingapp -> {
                startActivity(Intent(this,SettingAppActivity::class.java))
                true
            }
            else -> true
        }
    }

    private fun cariUserGithub(username : String){
        showLoading(true)
        val client = ConfigApi.getServiceApi().cariUser(username)
        client.enqueue(object : Callback<ResponseGithub>{
            override fun onResponse(
                call: Call<ResponseGithub>,
                response: Response<ResponseGithub>
            ) {
                showLoading(false)
                val responseBody = response.body()
                val listUser = ArrayList<GithubUser>()
                if (responseBody != null){
                    for (user in responseBody.listUserGithub){
                        listUser.add(user)
                    }
                    binding.rvListUser.layoutManager = LinearLayoutManager(this@MainActivity)
                    val adapterUser = UsersAdapter(listUser)
                    binding.rvListUser.adapter = adapterUser
                    adapterUser.setOnItemClickCallback(object: UsersAdapter.OnItemClickCallback{
                        override fun onItemClicked(listUser: GithubUser) {
                            val intent = Intent(this@MainActivity, UserDetailActivity::class.java)
                            intent.putExtra(UserDetailActivity.USERNAME_EXTRA, listUser.Username)
                            Toast.makeText(this@MainActivity, "Kamu Memilih " + listUser.Username, Toast.LENGTH_SHORT).show()
                            startActivity(intent)
                        }
                    })
                }else{
                    Toast.makeText(this@MainActivity,"User Tidak ditemukan", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ResponseGithub>, t: Throwable) {
                showLoading(false)
                Toast.makeText(this@MainActivity,"Terjadi Sesuatu :/n ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }



    private fun showLoading(isLoading : Boolean){
        if (isLoading){
            binding.progressBar.visibility = View.VISIBLE
        }else{
            binding.progressBar.visibility = View.GONE
        }
    }

}
