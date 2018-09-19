package yts.mnf.torrent.Fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import fr.castorflex.android.circularprogressbar.CircularProgressBar;
import fr.castorflex.android.circularprogressbar.CircularProgressDrawable;
import yts.mnf.torrent.Adapter.RecycleAdapter;
import yts.mnf.torrent.AppController;
import yts.mnf.torrent.GridSpacingItemDecoration;
import yts.mnf.torrent.Models.ListModel;
import yts.mnf.torrent.Models.Movie;
import yts.mnf.torrent.R;
import yts.mnf.torrent.Tools.Config;
import yts.mnf.torrent.Tools.PreferensHandler;
import yts.mnf.torrent.Tools.Url;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link YifyMovieFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link YifyMovieFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class YifyMovieFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public YifyMovieFragment() {
        // Required empty public constructor
    }



    Gson gson = new Gson();
    ListModel listMode;

    @BindView(R.id.main_activty_loading)
    CircularProgressBar progressBar;

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    @BindView(R.id.swiperefresh)
    SwipeRefreshLayout refreshSwipe;

    @BindView(R.id.container_error)
    RelativeLayout containerError;


    @BindView(R.id.tag_msg)
    TextView tvErrorMsg;

    @BindView(R.id.content_main)
    RelativeLayout rootViewYifyFrag;


    private RecycleAdapter adapter;
    private List<Movie> mModels;
    Context c;

    int totalPages=1;
    int page =1;

    int pastVisiblesItems, visibleItemCount, totalItemCount;
    boolean loading =true;
    PreferensHandler pref;




    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment YifyMovieFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static YifyMovieFragment newInstance(String param1, String param2) {
        YifyMovieFragment fragment = new YifyMovieFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_yify_movie, container, false);
        ButterKnife.bind(this,v);
        c = getContext();
        pref = new PreferensHandler(c);
        mModels = new ArrayList<>();
        final GridLayoutManager mLayoutManager = new GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(1, Config.dpToPx(1,getContext()), true));
        //recyclerView.addItemDecoration(new MarginDecoration(this));
        recyclerView.setHasFixedSize(true);

        // recyclerView.setItemAnimator(new DefaultItemAnimator());

            setUpDrakTheme(pref.getThemeDark());

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                Log.e("TAGl", "inside onScolled totpages = " + totalPages);
                visibleItemCount = mLayoutManager.getChildCount();
                totalItemCount = mLayoutManager.getItemCount();
                pastVisiblesItems = mLayoutManager.findFirstVisibleItemPosition();
                if (loading) {
                    if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                        loading = false;
                        Log.e("TAGl", "inside loading tot & page = " + totalPages + " " + page);
                        if (page < totalPages) {
                            Log.e("TAGl", "inside page<=totalPage");
                            Log.e("TAGl", "last inside tot & page = " + totalPages + " " + page);
                            page++;
                            //  paramsModel.add(new ParamsModel("page", page + ""));
                            makeNetwoekRequest(Url.ListUrl+"?page="+page,false);
                            //  RequestData(Url + "?page=" + page);
                            //  showProgg();
                        }

                    }
                }

            }
        });


        adapter = new RecycleAdapter(c, mModels,this.getFragmentManager());
        recyclerView.setAdapter(adapter);



        makeNetwoekRequest(Url.ListUrl,false);


        refreshSwipe.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        Log.i("TAG", "onRefresh called from SwipeRefreshLayout");

                        // This method performs the actual data-refresh operation.
                        // The method calls setRefreshing(false) when it's finished.
                        makeNetwoekRequest(Url.ListUrl,true);
                        refreshSwipe.setRefreshing(true);

                    }
                }
        );


        return v;
    }


    @Override
    public void onResume() {
        super.onResume();
        if(pref!=null)
            setUpDrakTheme(pref.getThemeDark());

    }

    public void startLoading(){
        containerError.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.VISIBLE);
        ((CircularProgressDrawable)progressBar.getIndeterminateDrawable()).start();
    }
    public void stopLoading(){
        ((CircularProgressDrawable)progressBar.getIndeterminateDrawable()).progressiveStop();
        progressBar.setVisibility(View.INVISIBLE);
    }



    public void makeNetwoekRequest(String url, final boolean swipeRefresh){
        Log.e("yyyy", "network request url "+ url);

        // RequestQueue queue = Volley.newRequestQueue(this);
        if(!swipeRefresh)
            startLoading();

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                url, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("TAG", response.toString());
                        Log.e("yyyy", "network request response "+ response);

                        stopLoading();
                        containerError.setVisibility(View.INVISIBLE);
                        recyclerView.setVisibility(View.VISIBLE);
                        listMode = gson.fromJson(response.toString(),ListModel.class);
                        Log.e("tag","response "+listMode.getStatus());
                        loading = true;

                        mModels = listMode.getData().getMovies();
                        int movieCount = listMode.getData().getMovieCount();
                        int movieLimit = listMode.getData().getLimit();
                        totalPages = movieCount/movieLimit;
                        if(movieCount%movieLimit != 0){
                            totalPages++;
                        }
                        Log.e("MainActivity","movieCount = "+movieCount+" movieLimit = "+movieLimit+" totalPage = "+totalPages);
                        if(mModels!=null) {
                            if (swipeRefresh) {
                                refreshSwipe.setRefreshing(false);

                                adapter.replaceItems(mModels);
                            } else {
                                adapter.addItems(mModels);
                            }
                            recyclerView.setVisibility(View.VISIBLE);
                        }


                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("TAG", "Error: " + error.getMessage());
                Log.e("yyyy", "network request Error "+error);

                // hide the progress dialog
                loading = true;
                stopLoading();
                refreshSwipe.setRefreshing(false);

                tvErrorMsg.setText("Network Issue, Please try again !! \n Pull Down to Refresh");
                recyclerView.setVisibility(View.INVISIBLE);
                containerError.setVisibility(View.VISIBLE);

            }
        });
        jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(60000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        AppController.getInstance().addToRequestQueue(jsonObjReq,"movie_list");
        AppController.getInstance().getRequestQueue().getCache().invalidate(url, true);
        AppController.getInstance().getRequestQueue().getCache().clear();
    }

    public void setUpDrakTheme(boolean key){
        if(key)
        rootViewYifyFrag.setBackgroundColor(getResources().getColor(R.color.blue_grey900));
        else
            rootViewYifyFrag.setBackgroundColor(getResources().getColor(R.color.white));
    }



    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
