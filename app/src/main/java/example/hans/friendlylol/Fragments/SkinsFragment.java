package example.hans.friendlylol.Fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.robrua.orianna.api.core.AsyncRiotAPI;
import com.robrua.orianna.api.core.RiotAPI;
import com.robrua.orianna.type.api.Action;
import com.robrua.orianna.type.core.common.Region;
import com.robrua.orianna.type.core.staticdata.Champion;
import com.robrua.orianna.type.exception.APIException;

import example.hans.friendlylol.DetalleSkinsAdapter;
import example.hans.friendlylol.R;

/**
 * Created by Matias on 11-07-16.
 */
public class SkinsFragment extends Fragment {
    private String version;
    int color;
    DetalleSkinsAdapter adapter;

    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private static final String API_KEY = "4821637b-1fef-4651-832f-f4177883cfa5";

    public SkinsFragment() {
        //Constructor
    }

    @SuppressLint("ValidFragment")
    public SkinsFragment(int color) {
        this.color = color;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        AsyncRiotAPI.setAPIKey(API_KEY);
        AsyncRiotAPI.setRegion(Region.LAS);

        // aqui se llama al layout .xml
        View view = inflater.inflate(R.layout.skins_fragment, container, false);

        final LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.skinsfrag_bg);
        linearLayout.setBackgroundColor(color);

        /* SKINS */
        recyclerView = (RecyclerView) view.findViewById(R.id.skinsfrag_scrollableview);
        recyclerView.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(getActivity().getBaseContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        Bundle bundle = this.getArguments();
        if(bundle != null){
            AsyncRiotAPI.getChampionByID(new Action<Champion>() {
                @Override
                public void handle(APIException exception) {
                    Log.e("Error", "ni idea");
                }

                @Override
                public void perform(final Champion responseData) {
                    version = RiotAPI.getVersions().get(0);
                    if(isAdded()){
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                adapter = new DetalleSkinsAdapter(getActivity().getApplicationContext(), responseData.getName(), responseData.getSkins(),version);
                                recyclerView.setAdapter(adapter);
                            }
                        });
                    }

                }
            }, bundle.getLong("ChampionID"));
        }

        return view;
    }

}

