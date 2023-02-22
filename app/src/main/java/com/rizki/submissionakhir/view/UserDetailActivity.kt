package com.rizki.submissionakhir.view

import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.StringRes
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayoutMediator
import com.rizki.submissionakhir.R
import com.rizki.submissionakhir.database.DBContract.UserFavColumns.Companion.AVATAR_URL
import com.rizki.submissionakhir.database.DBContract.UserFavColumns.Companion.USERNAME
import com.rizki.submissionakhir.database.DBContract.UserFavColumns.Companion._ID
import com.rizki.submissionakhir.database.UserFavHelper
import com.rizki.submissionakhir.databinding.ActivityUserDetailBinding
import com.rizki.submissionakhir.networking.ConfigApi
import com.rizki.submissionakhir.response.GithubUser
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserDetailActivity : AppCompatActivity() {

    private lateinit var binding : ActivityUserDetailBinding
    private lateinit var userFavHelper: UserFavHelper
    private lateinit var users: GithubUser

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = getString(R.string.details_users)

        val username = intent.getStringExtra(USERNAME_EXTRA)
        val id = intent.getIntExtra(ID_EXTRA,0)
        getDataUser(username?:"null")

        val sectionPageAdapter = SectionPageAdapter(this)
        binding.viewpager.adapter = sectionPageAdapter
        sectionPageAdapter.Username = username?:"null"
        TabLayoutMediator(binding.tab, binding.viewpager){ tab, position ->
            tab.text = resources.getString(TITTLE_TAB[position])
        }.attach()

        userFavHelper = UserFavHelper.getInstance(applicationContext)
        userFavHelper.open()

        simpanFavoriteUser(id)
    }


    private fun getDataUser(username: String){
        showLoading(true)
        val client = ConfigApi.getServiceApi().getUserDetail(username)
        client.enqueue(object : Callback<GithubUser>{
            override fun onResponse(call: Call<GithubUser>, response: Response<GithubUser>) {
                showLoading(false)
                val responseBody = response.body()
                if (responseBody != null){
                    users = responseBody
                    setDataUser()
                }
            }

            override fun onFailure(call: Call<GithubUser>, t: Throwable) {
                showLoading(false)
                Toast.makeText(this@UserDetailActivity, "Something Wrong : /n ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun setDataUser(){
        with(binding){
            tvUsernameGit.text = users.Username ?: "null"
            tvFullnameGit.text = users.Fullname ?: "null"
            tvRepositoryGit.text = getString(R.string.repository, users?.Repository ?: "null")
            tvCompanyGit.text = users.Company ?: "null"
            tvLocationGit.text = users.Location ?: "null"

            Glide.with(this@UserDetailActivity).load(users.Avatar).into(imgUser)
        }
    }



    private fun simpanFavoriteUser(id : Int){
        val cursor = userFavHelper.queryById(id.toString())
        var statusFavorite = cursor.count != 0

        setBtnFavorite(statusFavorite)
        if (!statusFavorite){
            binding.btnFavorite.setOnClickListener {
                statusFavorite = !statusFavorite
                val contentValue = ContentValues()
                contentValue.put(_ID,users.Id)
                contentValue.put(USERNAME,users.Username)
                contentValue.put(AVATAR_URL,users.Avatar)
                val status = userFavHelper.tambah(contentValue)
                if (status>0){
                    Toast.makeText(this, "User tersimpan difavorit", Toast.LENGTH_SHORT).show()
                }else{
                    Toast.makeText(this, "User sudah tersimpan difavorit", Toast.LENGTH_SHORT).show()
                }
                setBtnFavorite(statusFavorite)
            }
        }else{
            binding.btnFavorite.setOnClickListener {
                statusFavorite = !statusFavorite
                val status = userFavHelper.hapusById(id)
                if (status>0){
                    Toast.makeText(this, "User dihapus dari favorit", Toast.LENGTH_SHORT).show()
                }else{
                    Toast.makeText(this, "User Sudah dihapus dari favorit", Toast.LENGTH_SHORT).show()
                }
                setBtnFavorite(statusFavorite)
            }
        }
    }

    private fun setBtnFavorite(status : Boolean){
        if (status){
            binding.btnFavorite.setImageResource(R.drawable.ic_favorite)
        }else{
            binding.btnFavorite.setImageResource(R.drawable.ic_favorite_border)
        }
    }

    private fun showLoading(isLoading : Boolean){
        if (isLoading){
            binding.progressBar.visibility = View.VISIBLE
        }else{
            binding.progressBar.visibility = View.GONE
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    companion object{
        const val ID_EXTRA = "extra_id"
        const val USERNAME_EXTRA = "extra_username"


        @StringRes
        private val TITTLE_TAB = intArrayOf(
            R.string.tabs_follower,
            R.string.tabs_following
        )
    }

}