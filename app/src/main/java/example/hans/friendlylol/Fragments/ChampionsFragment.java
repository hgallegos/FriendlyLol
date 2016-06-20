package example.hans.friendlylol.Fragments;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.robrua.orianna.api.core.AsyncRiotAPI;
import com.robrua.orianna.api.core.RiotAPI;
import com.robrua.orianna.type.api.Action;
import com.robrua.orianna.type.core.champion.ChampionStatus;
import com.robrua.orianna.type.core.common.Region;
import com.robrua.orianna.type.core.staticdata.Champion;
import com.robrua.orianna.type.exception.APIException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import example.hans.friendlylol.ChampionAdapter;
import example.hans.friendlylol.ChampionStatusAdapter;
import example.hans.friendlylol.DetailChampion;
import example.hans.friendlylol.MainActivity;
import example.hans.friendlylol.R;

/**
 * Created by hans6 on 19-06-2016.
 */
public class ChampionsFragment extends Fragment {

    private String version;
    private static final String API_KEY = "4821637b-1fef-4651-832f-f4177883cfa5";

    private RelativeLayout mLayout;
    private RecyclerView recyclerView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance) {
        //Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_champions, container, false);

        ((MainActivity) getActivity()).getSupportActionBar().setTitle("Campeones");

        mLayout = (RelativeLayout) view.findViewById(R.id.mainLayout);
        AsyncRiotAPI.setRegion(Region.LAS);
        AsyncRiotAPI.setAPIKey(API_KEY);

        verificarInternet(view);
        return view;
    }

    public void verificarInternet(View view){
        if(isOnline()){
            listarCampeones(view);
        }else{
            snackBarNoInternet();
        }
    }

    private void listarCampeones(View view){
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);

        if(isAdded()){
            recyclerView.setLayoutManager(new GridLayoutManager(getActivity().getApplicationContext(),5,GridLayoutManager.VERTICAL,false));
        }


        AsyncRiotAPI.getChampions(new Action<List<Champion>>() {
            @Override
            public void handle(APIException exception) {
                Log.e("Error campeones", "ni idea");
            }

            @Override
            public void perform(final List<Champion> responseData) {
                final List<Champion> champions = new ArrayList<Champion>(responseData);

                Collections.sort(champions, new Comparator<Champion>() {
                    @Override
                    public int compare(Champion c1, Champion c2) {
                        return c1.getName().compareTo(c2.getName());
                    }
                });
                version = RiotAPI.getVersions().get(0);
                if(isAdded()){
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ChampionAdapter championAdapter = new ChampionAdapter(getActivity().getApplicationContext(),champions, version);
                            recyclerView.setAdapter(championAdapter);
                            recyclerView.addOnItemTouchListener(new MainFragment.RecyclerTouchListener(getActivity().getApplicationContext(), recyclerView, new MainActivity.ClickListener() {
                                @Override
                                public void onClick(View view, int position) {
                                    Champion champion = responseData.get(position);
                                    Toast.makeText(getActivity().getApplicationContext(), champion.getName() + " is selected!", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getActivity().getApplicationContext(), DetailChampion.class);
                                    intent.putExtra("championID", champion.getID());
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
        });
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
}
