package com.bd.dana.hiltpractice.ui.home

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.pm.ApplicationInfo
import android.content.pm.PackageInfo
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bd.dana.hiltpractice.R
import com.bd.dana.hiltpractice.api.models.app_model.AppModel
import com.bd.dana.hiltpractice.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import java.util.*


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var installedAppsList: MutableList<AppModel>

    private var binding: ActivityMainBinding? = null
    private val viewModel: HomeViewModel by viewModels()

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


        with(binding?.recyclerView!!){
            layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, false)
            adapter = dataAdapter
            itemAnimator = null
        }

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
            dataAdapter.initLoad(installedAppsList)
        }, 500)

    }

    @SuppressLint("QueryPermissionsNeeded")
    private fun getInstalledApps(): List<AppModel> {
        installedAppsList.clear()
        val packs = packageManager.getInstalledPackages(0)
        for (i in packs.indices) {
            val p = packs[i]
            if (!isSystemPackage(p)) {
                val appName = p.applicationInfo.loadLabel(packageManager).toString()
                val icon = p.applicationInfo.loadIcon(packageManager)
                val packages = p.applicationInfo.packageName
                installedAppsList.add(AppModel(appName, icon, packages))
            }
        }
        installedAppsList.sortBy { it.getName().capitalized() }
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