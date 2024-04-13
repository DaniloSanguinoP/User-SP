package com.example.usersp

import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.usersp.databinding.ActivityMainBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputEditText

class MainActivity : AppCompatActivity(), OnClickListener {

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

        val preferences = getPreferences(Context.MODE_PRIVATE)
        val isFirsTime = preferences.getBoolean(getString(R.string.sp_first_time), true)

        Log.i("SP", "${getString(R.string.sp_first_time)} = $isFirsTime")

        if (isFirsTime) {
            val dialogView = layoutInflater.inflate(R.layout.dialog_register, null)

            /*MaterialAlertDialogBuilder(this)
                .setTitle(R.string.dialog_title)
                .setView(dialogView)
                .setCancelable(false)
                .setPositiveButton(R.string.dialog_confirm) { _, _ ->
                    val username =
                        dialogView.findViewById<TextInputEditText>(R.id.etUsername).text.toString()
                    with(preferences.edit()) {
                        putBoolean(getString(R.string.sp_first_time), false)
                        putString(getString(R.string.sp_username), username)
                            .apply()
                    }
                    Toast.makeText(this, R.string.register_success, Toast.LENGTH_SHORT).show()
                }
                .show()*/

            val dialog = MaterialAlertDialogBuilder(this)
                .setTitle(R.string.dialog_title)
                .setView(dialogView)
                .setCancelable(false)
                .setPositiveButton(R.string.dialog_confirm) { _, _ -> }
                .create()

            dialog.show()

            dialog.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener {
                val username =
                    dialogView.findViewById<TextInputEditText>(R.id.etUsername).text.toString()

                if (username.isBlank()) {
                    Toast.makeText(this, R.string.register_invalid, Toast.LENGTH_SHORT).show()
                } else {
                    with(preferences.edit()) {
                        putBoolean(getString(R.string.sp_first_time), false)
                        putString(getString(R.string.sp_username), username)
                            .apply()
                    }
                    Toast.makeText(this, R.string.register_success, Toast.LENGTH_SHORT).show()
                    dialog.dismiss()
                }
            }

        } else  {
            val username = preferences.getString(getString(R.string.sp_username), getString(R.string.hint_username))
            Toast.makeText(this, "Bienvenido $username", Toast.LENGTH_SHORT).show()
        }

        userAdapter = UserAdapter(getUsers(), this)
        linearLayoutManager = LinearLayoutManager(this)

        binding.recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = linearLayoutManager
            adapter = userAdapter
        }

        val swipeHelper =
            ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {

                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ): Boolean = false

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    userAdapter.remove(viewHolder.adapterPosition)
                }

            })

        swipeHelper.attachToRecyclerView(binding.recyclerView)

    }

    private fun getUsers(): MutableList<User> {
        val users = mutableListOf<User>()

        val danilo = User(1,"Danilo", "Andrés","https://i.pinimg.com/736x/2d/9e/a4/2d9ea4bb5770d4cbbcdcb2bb706333a6.jpg")
        val samanta = User(2,"Samanta", "Mesa","https://upload.wikimedia.org/wikipedia/commons/b/b2/Samanta_villar.jpg")
        val diego = User(3,"Diego", "Gómez","https://upload.wikimedia.org/wikipedia/commons/thumb/2/2c/Maradona-Mundial_86_con_la_copa.JPG/800px-Maradona-Mundial_86_con_la_copa.JPG")
        val emma = User(4,"Emma", "Cruz","https://es.web.img3.acsta.net/pictures/19/10/17/03/44/5612828.jpg")

        users.add(danilo)
        users.add(samanta)
        users.add(diego)
        users.add(emma)
        users.add(danilo)
        users.add(samanta)
        users.add(diego)
        users.add(emma)

        return  users
    }

    override fun onCLick(user: User, position: Int) {
        Toast.makeText(this, "$position: ${user.getFullName()}", Toast.LENGTH_SHORT).show()
    }
}