package com.bd.dana.hiltpractice.ui.home

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.Dialog
import android.app.PendingIntent
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.content.pm.ApplicationInfo
import android.content.pm.PackageInfo
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bd.dana.hiltpractice.R
import com.bd.dana.hiltpractice.api.models.app_model.AppModel
import com.bd.dana.hiltpractice.api.models.app_model.AppTable
import com.bd.dana.hiltpractice.broadcast_receiver.OpenAppBroadcast
import com.bd.dana.hiltpractice.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import java.util.*


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var installedAppsList: MutableList<AppTable>

    private var binding: ActivityMainBinding? = null
    private val viewModel: HomeViewModel by viewModels()

    private var selectedTimeStamp: Long = 0L

    private val dataAdapter: AppAdapter = AppAdapter()


    @SuppressLint("QueryPermissionsNeeded", "LogNotTimber")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        /*val mainIntent = Intent(Intent.ACTION_MAIN, null)
        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER)
        val pkgAppsList: List<ResolveInfo> = this.packageManager.queryIntentActivities(mainIntent, 0)*/

        getInstalledAppsAndBind()
        initClickListener()


        with(binding?.recyclerView!!){
            layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, false)
            adapter = dataAdapter
            itemAnimator = null
        }

    }

    @SuppressLint("LogNotTimber")
    private fun initAlarm(selectedTimeStamp: Long) {
        Toast.makeText(this, "called", Toast.LENGTH_LONG).show()
        val intent = Intent(this, OpenAppBroadcast::class.java).apply {
            putExtra("package", "com.bd.deliverytiger.app")
        }
        val pendingIntent = PendingIntent.getBroadcast(this, 50000444, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        val alarmManager: AlarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager

        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, selectedTimeStamp, AlarmManager.INTERVAL_DAY, pendingIntent)
        //Timber.d("reminderDebug createReminder for $selectedTimeStamp")
        Log.d("timeStampOpenApp", "$selectedTimeStamp")
    }

    private fun initClickListener() {
        dataAdapter.invokeApp = { App ->
            timePicker()
        }
    }

    private fun timePicker() {
        val calender = Calendar.getInstance()
        TimePickerDialog(this, { _, hourOfDay, minute ->
            calender.apply {
                timeInMillis = System.currentTimeMillis()
                set(Calendar.HOUR_OF_DAY, hourOfDay)
                set(Calendar.MINUTE, minute)
            }
            selectedTimeStamp = calender.timeInMillis
            //selectedHour24 = "$hourOfDay:$minute"
            initAlarm(selectedTimeStamp)
        }, calender.get(Calendar.HOUR_OF_DAY), calender.get(Calendar.MINUTE), false)
            .show()
    }

    private fun getInstalledAppsAndBind() {
        val loadingDialog = Dialog(this)
        loadingDialog.setContentView(R.layout.loading)
        loadingDialog.window!!.setLayout(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        loadingDialog.setCancelable(false)
        installedAppsList = mutableListOf()
        loadingDialog.show()
        Handler(Looper.getMainLooper()).postDelayed({
            getInstalledApps()
            loadingDialog.dismiss()
            findViewById<TextView>(R.id.totalInstalledApp).text = "Total installed Apps: ${installedAppsList.size}"
            viewModel.insertAllInstalledApp(installedAppsList)
            viewModel.getAllInstalledApps().observe(this, androidx.lifecycle.Observer {
                dataAdapter.initLoad(it)
            })
        }, 500)

    }

    @SuppressLint("QueryPermissionsNeeded")
    private fun getInstalledApps(): List<AppTable> {
        installedAppsList.clear()
        val packs = packageManager.getInstalledPackages(0)
        for (i in packs.indices) {
            val p = packs[i]
            if (!isSystemPackage(p)) {
                val appName = p.applicationInfo.loadLabel(packageManager).toString()
                val icon = p.applicationInfo.loadIcon(packageManager)
                val packages = p.applicationInfo.packageName
                installedAppsList.add(AppTable(appName = appName, packageName =  packages))
            }
        }
        installedAppsList.sortBy { it.appName?.capitalized() }
        return installedAppsList
    }

    private fun String.capitalized(): String {
        return this.replaceFirstChar {
            if (it.isLowerCase())
                it.titlecase(Locale.getDefault())
            else it.toString()
        }
    }
    private fun isSystemPackage(pkgInfo: PackageInfo): Boolean {
        return pkgInfo.applicationInfo.flags and ApplicationInfo.FLAG_SYSTEM != 0
    }

}