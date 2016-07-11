package example.hans.friendlylol.Fragments;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.robrua.orianna.api.core.AsyncRiotAPI;
import com.robrua.orianna.api.core.RiotAPI;
import com.robrua.orianna.type.api.Action;
import com.robrua.orianna.type.core.champion.ChampionStatus;
import com.robrua.orianna.type.core.common.Region;
import com.robrua.orianna.type.core.staticdata.Champion;
import com.robrua.orianna.type.core.summoner.Summoner;
import com.robrua.orianna.type.exception.APIException;
import com.squareup.otto.Bus;
import com.squareup.otto.Produce;
import com.squareup.otto.ThreadEnforcer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import example.hans.friendlylol.BaseApplication;
import example.hans.friendlylol.ChampionStatusAdapter;
import example.hans.friendlylol.DetailChampion;
import example.hans.friendlylol.MainActivity;
import example.hans.friendlylol.R;

/**
 * Created by hans6 on 20-06-2016.
 */
public class SummonerFragment extends Fragment {
    private String version;
    private static final String API_KEY = "4821637b-1fef-4651-832f-f4177883cfa5";

    private CoordinatorLayout mLayout;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private TextView summonerResult;
    private EditText searchText;
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance) {
        //Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_summoner, container, false);

        ((MainActivity) getActivity()).getSupportActionBar().setTitle("Invocadores");

        setHasOptionsMenu(true);
        view.setVisibility(View.GONE);

        //mLayout = (CoordinatorLayout) view.findViewById(R.id.mainLayout);


        AsyncRiotAPI.setRegion(Region.LAS);
        AsyncRiotAPI.setAPIKey(API_KEY);

        /*searchText = (EditText) view.findViewById(R.id.search_edit_text);
        searchText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_LEFT = 0;
                final int DRAWABLE_TOP = 1;
                final int DRAWABLE_RIGHT = 2;
                final int DRAWABLE_BOTTOM = 3;

                if(event.getAction()  == MotionEvent.ACTION_UP){
                    if(event.getRawX() >= (searchText.getRight() - searchText.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())){
                        //Toast.makeText(getActivity().getApplicationContext(), searchText.getText().toString(), Toast.LENGTH_SHORT).show();
                        verificarInternet(view);
                        return true;
                    }

                }
                return false;
            }
        });

        searchText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId == EditorInfo.IME_ACTION_SEARCH){
                    //Toast.makeText(getActivity().getApplicationContext(), searchText.getText().toString(), Toast.LENGTH_SHORT).show();
                    verificarInternet(view);
                    return true;
                }
                return false;
            }
        });*/


        return view;
    }


    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getActivity().getSupportFragmentManager());
        ResumenSummonerFragment resumenSummonerFragment = new ResumenSummonerFragment();
        adapter.addFragment(resumenSummonerFragment, "Resumen");
        adapter.addFragment(new HistoryPlayFragment(), "Historial de partidas");
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();
        private String Summoner;

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){

        //Implementar ActionBar Search dentro de un fragmento
        MenuItem item = menu.add("Buscar Invocador");
        item.setIcon(R.drawable.ic_search_white_24dp);
        item.setShowAsAction(MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW|MenuItem.SHOW_AS_ACTION_ALWAYS);
        SearchView searchView = new SearchView(getActivity());

        //modificar el texto dentro del componente edittext
        //int id = searchView.getContext().getResources().getIdentifier("android:id/search_src_text", null, null);
        TextView textView = (TextView) searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);
        textView.setHint("Buscar invocador");
        textView.setTextColor(getResources().getColor(R.color.md_white_1000));
        textView.setHintTextColor(getResources().getColor(R.color.md_blue_grey_300));

        // implementing the listener
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                if (s.length() < 1) {
                    Toast.makeText(getActivity(),
                            "Ingresa un nombre de Invocador",
                            Toast.LENGTH_LONG).show();
                    return true;
                } else {
                    searchSummoner(s);
                    //doSearch(s);
                    return false;
                }
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return true;
            }
        });
        item.setActionView(searchView);

    }

    public void verificarInternet(View view){
        if(isOnline()){
            return;
        }else{
            snackBarNoInternet();
        }
    }

    //Busca invocador
    private void searchSummoner(String search){
        //summonerResult = (TextView) view.findViewById(R.id.summoner_results);
        view.setVisibility(View.VISIBLE);
        viewPager = (ViewPager) view.findViewById(R.id.viewpager);
        setupViewPager(viewPager);
        tabLayout = (TabLayout) view.findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);
        BaseApplication.getEventBus().post(search);

        /*AsyncRiotAPI.getSummonerByName(new Action<Summoner>() {
            @Override
            public void handle(APIException exception) {

            }

            @Override
            public void perform(final Summoner responseData) {
                if(isAdded()){
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getContext(),""+responseData.getName(),Toast.LENGTH_LONG).show();
                            //summonerResult.setText(responseData.getName());
                        }
                    });
                }

            }
        }, search);*/
    }
    /*
    private void listarCampeones(View view){
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);

        if(isAdded()){
            recyclerView.setLayoutManager(new GridLayoutManager(getActivity().getApplicationContext(),5,GridLayoutManager.VERTICAL,false));
        }


        AsyncRiotAPI.getChampionStatuses(new Action<Map<Champion, ChampionStatus>>() {
            @Override
            public void handle(APIException exception) {
                Log.e("Error campeones", "ni idea");
            }

            @Override
            public void perform(final Map<Champion, ChampionStatus> responseData) {


                Log.e("a ver", ""+responseData.size());
                version = RiotAPI.getVersions().get(0);
                if(isAdded()){
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ChampionStatusAdapter championAdapterStatus =
                                    new ChampionStatusAdapter(getActivity().getApplicationContext(),new ArrayList<ChampionStatus>(responseData.values()), version);
                            recyclerView.setAdapter(championAdapterStatus);
                            recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity().getApplicationContext(), recyclerView, new MainActivity.ClickListener() {
                                @Override
                                public void onClick(View view, int position) {
                                    ChampionStatus championStatus = new ArrayList<>(responseData.values()).get(position);
                                    Toast.makeText(getActivity().getApplicationContext(), championStatus.getChampion().getName() + " is selected!", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getActivity().getApplicationContext(), DetailChampion.class);
                                    intent.putExtra("championID", championStatus.getChampionID());
                                    startActivity(intent);
                                }

                                @Override
                                public void onLongClick(View view, int position) {

                                }
                            }));
                        }
                    });
                }

            }
        },true);
    }

    private void snackBarPermiso(){
        Snackbar snackbar = Snackbar
                .make(mLayout, R.string.no_permission, Snackbar.LENGTH_LONG)
                .setAction(R.string.try_permission, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        openSettings();
                    }
                });
        snackbar.show();
    }

    private void snackBarInfoPermiso(){
        Snackbar snackbar = Snackbar
                .make(mLayout, R.string.permission_internet, Snackbar.LENGTH_LONG);
        snackbar.show();
    }
    */

    private void snackBarNoInternet(){
        Snackbar snackbar = Snackbar
                .make(mLayout, R.string.no_internet, Snackbar.LENGTH_INDEFINITE)
                .setAction(R.string.try_again, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        verificarInternet(view);
                    }
                });
        snackbar.show();
    }

    private void openSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.parse("package:"+getActivity().getPackageName()));
        startActivity(intent);
    }

    public boolean isOnline() {
        ConnectivityManager connMgr = (ConnectivityManager)
                getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }

    @Produce
    public String produceEvent() {
        return "Starting up";
    }
    /*
    public interface ClickListener {
        void onClick(View view, int position);

        void onLongClick(View view, int position);
    }

    public static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

        private GestureDetector gestureDetector;
        private MainActivity.ClickListener clickListener;

        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final MainActivity.ClickListener clickListener) {
            this.clickListener = clickListener;
            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                    if (child != null && clickListener != null) {
                        clickListener.onLongClick(child, recyclerView.getChildAdapterPosition(child));
                    }
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {

            View child = rv.findChildViewUnder(e.getX(), e.getY());
            if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)) {
                clickListener.onClick(child, rv.getChildAdapterPosition(child));
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }
    }*/
}

