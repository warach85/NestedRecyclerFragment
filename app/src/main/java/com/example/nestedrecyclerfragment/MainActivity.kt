package com.example.nestedrecyclerfragment

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.*
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    lateinit var navController: NavController
    lateinit var appBarConfiguration:AppBarConfiguration
    lateinit var toolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottomNav = findViewById<BottomNavigationView>(R.id.bottomNav)
        navController = findNavController(R.id.nav_host_fragment)
        bottomNav.setupWithNavController(navController)


        toolbar=findViewById<Toolbar>(R.id.my_toolbar)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setSupportActionBar(toolbar)
        setupActionBarWithNavController(navController,appBarConfiguration)


        //region - Intent from background Notification
        val intent = this.intent
        val bundle = intent?.extras

        if(bundle!=null) {
            // TODO : check if below field ok or need to check the click_action key
            if (bundle?.keySet()!!.contains("notifText")) {
                navController.navigate(R.id.action_firstFragment_to_thirdFragment,bundle)
                /*val resultActivityIntent = Intent(this, ResultActivity::class.java).apply {
                    //flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    putExtra("NotificationBundle",bundle)
                }
                startActivity(resultActivityIntent)*/
                //finish()
            }
        }
        //endregion
    }

    /*fun onFirstOptionClick(item: MenuItem) {
        NavigationUI.onNavDestinationSelected(item,findNavController(R.id.nav_host_fragment))}
    fun onSecondOptionClick(item: MenuItem){
        NavigationUI.onNavDestinationSelected(item,findNavController(R.id.nav_host_fragment))}*/

    //These 2 methods work only with Case2 i.e., 1)Default Action Bar or 2)Toolbar setup as actionbar
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menutoolbar,menu)
        return true
    }

    //Reqd for Case 2
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return item.onNavDestinationSelected(navController) || super.onOptionsItemSelected(item)
    }

    //Reqd for Case 2
    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(findNavController(R.id.nav_host_fragment),appBarConfiguration)
    }

    //region - Foreground Notification
    /*This function is called from FrbMessagingService(foreground notif), so here intent is created in that function
    ,i.e, we are responsible for putting extras in the intent and forwarding here*/
    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        /* 26/11/19 , below line was giving error as intent became null, seems this.intent refers to
        something else, so using direct intent passed from the function argument */
        //val intent = this.intent
        val bundle = intent?.extras

        if(bundle!=null) {
            // TODO : check if below field ok or need to check the click_action key
            if (bundle?.keySet()!!.contains("notifText")) {
                navController.navigate(R.id.action_firstFragment_to_thirdFragment,bundle)
            }
        }
    }
    //endregion
}
