package com.example.gasapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.gasapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), OnDataPass {
    lateinit var binding: ActivityMainBinding
    private val dbHelper = DBHelper(this)
    var logged = false
    lateinit var loggedUser: User
    var lastClicked = 3

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // odkomentowac przy pierwszym uruchamianiu !!!
        // dbHelper.createTables()
        // addSampleData()

        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.mainFragmentContainer, LoginRegisterFragment())
        fragmentTransaction.commit()

        binding.button1.setOnClickListener() {
            lastClicked = 1
            if (!logged) {
                loginOrRegister()
            } else {
                val bundle = Bundle()
                bundle.putString("name", loggedUser.name)

                val fragment = AddEntryFragment()
                fragment.arguments = bundle

                val fragmentManager = supportFragmentManager
                val fragmentTransaction = fragmentManager.beginTransaction()
                fragmentTransaction.replace(R.id.mainFragmentContainer, fragment)
                fragmentTransaction.commit()
            }
        }

        binding.button2.setOnClickListener() {
            replaceFragment(MapsFragment())
        }

        binding.button3.setOnClickListener() {
            replaceFragment(UsersFragment())
        }

        binding.button4.setOnClickListener() {
            lastClicked = 4
            if (!logged) {
                loginOrRegister()
            } else {
                val bundle = Bundle()
                bundle.putString("name", loggedUser.name)

                val fragment = ProfileFragment()
                fragment.arguments = bundle

                val fragmentManager = supportFragmentManager
                val fragmentTransaction = fragmentManager.beginTransaction()
                fragmentTransaction.replace(R.id.mainFragmentContainer, fragment)
                fragmentTransaction.commit()
            }
        }
    }

    override fun onDataPass(data: String) {
        if (data == "login") {
            val fragmentManager = supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.mainFragmentContainer, LoginFragment())
            fragmentTransaction.commit()
        } else if (data == "register") {
            val fragmentManager = supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.mainFragmentContainer, RegisterFragment())
            fragmentTransaction.commit()
        } else if (data == "loginRegister") {
            val fragmentManager = supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.mainFragmentContainer, LoginRegisterFragment())
            fragmentTransaction.commit()
        } else if (data == "4") {
            val bundle = Bundle()
            bundle.putString("name", loggedUser.name)
            val fragment = ProfileFragment()
            fragment.arguments = bundle

            val fragmentManager = supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.mainFragmentContainer, fragment)
            fragmentTransaction.commit()
        } else {
            loggedUser = dbHelper.getUserByName(data)!!
            logged = true

            val bundle = Bundle()
            bundle.putString("name", loggedUser.name)

            var fragment: Fragment = if (lastClicked == 4) {
                ProfileFragment()
            } else {
                AddEntryFragment()
            }

            fragment.arguments = bundle

            val fragmentManager = supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.mainFragmentContainer, fragment)
            fragmentTransaction.commit()
        }
    }

    private fun loginOrRegister() {
        val fragment = LoginRegisterFragment()

        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.mainFragmentContainer, fragment)
        fragmentTransaction.commit()
    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.mainFragmentContainer, fragment)
        fragmentTransaction.commit()
    }

    private fun addSampleData() {
        dbHelper.addUser(
            User(
                1,
                "Adam",
                "Uszeta",
                "tmp"
            )
        )

        dbHelper.addUser(
            User(
                2,
                "Franek",
                "Kimono",
                "tmp"
            )
        )

        dbHelper.addUser(
            User(
                3,
                "Dobry",
                "Ziomek",
                "tmp"
            )
        )

        dbHelper.addEntry(
            Entry(
                1,
                1,
                "gas",
                32.52,
                14.00,
                ""
            )
        )

        dbHelper.addEntry(
            Entry(
                2,
                1,
                "gas",
                14.52,
                2.00,
                ""
            )
        )

        dbHelper.addEntry(
            Entry(
                3,
                2,
                "gas",
                53.52,
                35.00,
                ""
            )
        )

        dbHelper.addEntry(
            Entry(
                4,
                3,
                "gas",
                12.52,
                14.24,
                ""
            )
        )
    }
}