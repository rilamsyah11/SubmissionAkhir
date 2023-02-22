package com.rizki.submissionakhir.view.favorite

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.rizki.submissionakhir.R
import com.rizki.submissionakhir.database.UserFavHelper
import com.rizki.submissionakhir.databinding.ActivityUserFavoriteBinding
import com.rizki.submissionakhir.helper.UserFavMappingHelper
import com.rizki.submissionakhir.response.GithubUser
import com.rizki.submissionakhir.view.UserDetailActivity
import com.rizki.submissionakhir.view.UsersAdapter

class UserFavoriteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUserFavoriteBinding
    private lateinit var userFavHelper: UserFavHelper
    private lateinit var usersAdapter: UsersAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = getString(R.string.favorite_users)

        userFavHelper = UserFavHelper.getInstance(applicationContext)
        userFavHelper.open()

        binding.rvUserFavorite.layoutManager = LinearLayoutManager(this@UserFavoriteActivity)

        val cursor = userFavHelper.queryAll()
        usersAdapter = UsersAdapter(UserFavMappingHelper.mapCursorToArrayListUser(cursor))
        if (cursor.count != 0){
            binding.rvUserFavorite.adapter = usersAdapter
        }else{
            Toast.makeText(this, "List User Favorit Kosong Harap Cari dan Tambahkan User ke Favorite Terlebih Dahulu", Toast.LENGTH_SHORT).show()
        }
        usersAdapter.setOnItemClickCallback(object : UsersAdapter.OnItemClickCallback{
            override fun onItemClicked(listUser: GithubUser) {
                val intent = Intent(this@UserFavoriteActivity, UserDetailActivity::class.java)
                intent.putExtra(UserDetailActivity.ID_EXTRA, listUser.Id)
                intent.putExtra(UserDetailActivity.USERNAME_EXTRA, listUser.Username)
                startActivity(intent)
            }
        })
        userFavHelper.close()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}