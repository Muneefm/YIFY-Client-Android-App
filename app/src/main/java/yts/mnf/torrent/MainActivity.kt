package yts.mnf.torrent

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.transition.Slide
import android.util.Log
import android.view.View
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.view.Window
import android.widget.RelativeLayout
import android.widget.TextView

import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.VolleyLog
import com.android.volley.toolbox.JsonObjectRequest
import com.google.gson.Gson

import org.json.JSONObject

import java.util.ArrayList

import butterknife.BindView
import butterknife.ButterKnife
import fr.castorflex.android.circularprogressbar.CircularProgressBar
import fr.castorflex.android.circularprogressbar.CircularProgressDrawable
import yts.mnf.torrent.Activity.DetailsActivity
import yts.mnf.torrent.Activity.SearchActivity
import yts.mnf.torrent.Tools.Config
import yts.mnf.torrent.Tools.Url
import yts.mnf.torrent.Adapter.RecycleAdapter
import yts.mnf.torrent.Models.ListModel
import yts.mnf.torrent.Models.Movie

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    internal var gson = Gson()
    internal var listMode: ListModel? = null

    @BindView(R.id.main_activty_loading)
    internal var progressBar: CircularProgressBar? = null

    @BindView(R.id.recycler_view)
    internal var recyclerView: RecyclerView? = null

    @BindView(R.id.swiperefresh)
    internal var refreshSwipe: SwipeRefreshLayout? = null

    @BindView(R.id.container_error)
    internal var containerError: RelativeLayout? = null


    @BindView(R.id.tag_msg)
    internal var tvErrorMsg: TextView? = null


    private var adapter: RecycleAdapter? = null
    private var mModels: List<Movie>? = null
    internal var c: Context? = null

    internal var totalPages = 1
    internal var page = 1

    internal var pastVisiblesItems: Int = 0
    internal var visibleItemCount: Int = 0
    internal var totalItemCount: Int = 0
    internal var loading = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.requestFeature(Window.FEATURE_CONTENT_TRANSITIONS)
        // set an enter transition
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.enterTransition = Slide()
            window.exitTransition = Slide()

        }
        // set an exit transition

        setContentView(R.layout.activity_main)
        ButterKnife.bind(this)
        c = this
        val toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)




        if (intent.extras != null) {
            Log.e("TAG", "notification intent not null")
            if (intent.extras.containsKey("url")) {
                Log.e("TAG", "notification intent not url = " + intent.extras.getString("url")!!)
                AppController().startBrowser(intent.extras.getString("url"), c)
            }
        } else {
            Log.e("TAG", "notification intent is null")
        }

        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        val drawer = findViewById(R.id.drawer_layout) as DrawerLayout
        val toggle = ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer.setDrawerListener(toggle)
        toggle.syncState()

        val navigationView = findViewById(R.id.nav_view) as NavigationView
        navigationView.setNavigationItemSelectedListener(this)
        val act = Intent(this@MainActivity, DetailsActivity::class.java)
        //startActivity(act);


        mModels = ArrayList<Movie>()
        val mLayoutManager = GridLayoutManager(this, 2)
        recyclerView!!.layoutManager = mLayoutManager
        recyclerView!!.addItemDecoration(GridSpacingItemDecoration(1, Config.dpToPx(1, applicationContext), true))
        //recyclerView.addItemDecoration(new MarginDecoration(this));
        recyclerView!!.setHasFixedSize(true)

        // recyclerView.setItemAnimator(new DefaultItemAnimator());

        recyclerView!!.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                Log.e("TAGl", "inside onScolled totpages = " + totalPages)
                visibleItemCount = mLayoutManager.childCount
                totalItemCount = mLayoutManager.itemCount
                pastVisiblesItems = mLayoutManager.findFirstVisibleItemPosition()
                if (loading) {
                    if (visibleItemCount + pastVisiblesItems >= totalItemCount) {
                        loading = false
                        Log.e("TAGl", "inside loading tot & page = $totalPages $page")
                        if (page < totalPages) {
                            Log.e("TAGl", "inside page<=totalPage")
                            Log.e("TAGl", "last inside tot & page = $totalPages $page")
                            page++
                            //  paramsModel.add(new ParamsModel("page", page + ""));
                            makeNetwoekRequest(Url.ListUrl + "?page=" + page, false)
                            //  RequestData(Url + "?page=" + page);
                            //  showProgg();
                        }

                    }
                }

            }
        })


        adapter = RecycleAdapter(c, mModels, supportFragmentManager)
        recyclerView!!.adapter = adapter



        makeNetwoekRequest(Url.ListUrl, false)


        refreshSwipe!!.setOnRefreshListener {
            Log.i("TAG", "onRefresh called from SwipeRefreshLayout")

            // This method performs the actual data-refresh operation.
            // The method calls setRefreshing(false) when it's finished.
            makeNetwoekRequest(Url.ListUrl, true)
            refreshSwipe!!.isRefreshing = true
        }

    }


    fun startLoading() {
        containerError!!.visibility = View.INVISIBLE

        progressBar!!.visibility = View.VISIBLE
        (progressBar!!.indeterminateDrawable as CircularProgressDrawable).start()
    }

    fun stopLoading() {
        (progressBar!!.indeterminateDrawable as CircularProgressDrawable).progressiveStop()
        progressBar!!.visibility = View.INVISIBLE
    }


    fun makeNetwoekRequest(url: String, swipeRefresh: Boolean) {
        // RequestQueue queue = Volley.newRequestQueue(this);
        if (!swipeRefresh)
            startLoading()

        val jsonObjReq = JsonObjectRequest(Request.Method.GET,
                url, null,
                Response.Listener<JSONObject> { response ->
                    Log.e("TAG", response.toString())
                    stopLoading()
                    containerError!!.visibility = View.INVISIBLE
                    recyclerView!!.visibility = View.VISIBLE
                    listMode = gson.fromJson(response.toString(), ListModel::class.java)
                    Log.e("tag", "response " + listMode?.status)
                    loading = true

                    mModels = listMode?.data?.movies
                    val movieCount = listMode?.data?.movieCount!!
                    val movieLimit = listMode?.data?.limit!!

                    totalPages = movieCount / movieLimit
                    if (movieCount % movieLimit != 0) {
                        totalPages++
                    }
                    Log.e("MainActivity", "movieCount = $movieCount movieLimit = $movieLimit totalPage = $totalPages")
                    if (mModels != null) {
                        if (swipeRefresh) {
                            refreshSwipe!!.isRefreshing = false

                            adapter!!.replaceItems(mModels)
                        } else {
                            adapter!!.addItems(mModels)
                        }
                        recyclerView!!.visibility = View.VISIBLE
                    }
                }, Response.ErrorListener { error ->
            VolleyLog.d("TAG", "Error: " + error.message)
            // hide the progress dialog
            loading = true
            stopLoading()
            refreshSwipe!!.isRefreshing = false

            tvErrorMsg!!.text = "Network Issue, Please try again !!"
            recyclerView!!.visibility = View.INVISIBLE
            containerError!!.visibility = View.VISIBLE
        })
        AppController.getInstance().addToRequestQueue(jsonObjReq, "movie_list")
        AppController.getInstance().requestQueue.cache.invalidate(url, true)

    }


    override fun onBackPressed() {
        val drawer = findViewById(R.id.drawer_layout) as DrawerLayout
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId


        if (id == R.id.action_search) {
            val search = Intent(this@MainActivity, SearchActivity::class.java)
            startActivity(search)
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        val id = item.itemId

        if (id == R.id.home) {
            val home = Intent(this@MainActivity, MainActivity::class.java)
            startActivity(home)
            finish()
            // Handle the camera action
        } else if (id == R.id.search) {
            val search = Intent(this@MainActivity, SearchActivity::class.java)
            startActivity(search)
        }

        val drawer = findViewById(R.id.drawer_layout) as DrawerLayout
        drawer.closeDrawer(GravityCompat.START)
        return true
    }
}
