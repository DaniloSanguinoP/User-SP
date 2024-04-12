package com.example.usersp

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.usersp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var userAdapter: UserAdapter
    private lateinit var linearLayoutManager: RecyclerView.LayoutManager

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        userAdapter = UserAdapter(getUsers())
        linearLayoutManager = LinearLayoutManager(this)

        binding.recyclearView.apply {
            layoutManager = linearLayoutManager
            adapter = userAdapter
        }
    }

    private fun getUsers(): MutableList<User> {
        val users = mutableListOf<User>()

        val danilo = User(1,"Danilo", "Andrés","https://i.pinimg.com/736x/2d/9e/a4/2d9ea4bb5770d4cbbcdcb2bb706333a6.jpg")
        val samanta = User(2,"Samanta", "Meza","https://upload.wikimedia.org/wikipedia/commons/b/b2/Samanta_villar.jpg")
        val diego = User(3,"Diego", "Gómez","https://upload.wikimedia.org/wikipedia/commons/thumb/2/2c/Maradona-Mundial_86_con_la_copa.JPG/800px-Maradona-Mundial_86_con_la_copa.JPG")
        val emma = User(4,"Emma", "Cruz","https://es.web.img3.acsta.net/pictures/19/10/17/03/44/5612828.jpg")

        users.add(danilo)
        users.add(samanta)
        users.add(diego)
        users.add(emma)

        return  users
    }
}