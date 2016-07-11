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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.robrua.orianna.api.core.AsyncRiotAPI;
import com.robrua.orianna.type.api.Action;
import com.robrua.orianna.type.core.common.Region;
import com.robrua.orianna.type.core.staticdata.Champion;
import com.robrua.orianna.type.exception.APIException;

import example.hans.friendlylol.DetalleStringAdapter;
import example.hans.friendlylol.R;

/**
 * Created by Matias on 11-07-16.
 */
public class SkillsFragment extends Fragment {
    int color;
    DetalleStringAdapter adapterPassive;

    private RecyclerView recyclerViewPassive;
    private LinearLayoutManager linearLayoutManagerPassive;
    private static final String API_KEY = "4821637b-1fef-4651-832f-f4177883cfa5";

    public SkillsFragment() {
        //Constructor
    }

    @SuppressLint("ValidFragment")
    public SkillsFragment(int color) {
        this.color = color;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        AsyncRiotAPI.setAPIKey(API_KEY);
        AsyncRiotAPI.setRegion(Region.LAS);

        // aqui se llama al layout .xml
        View view = inflater.inflate(R.layout.habilidades_fragment, container, false);

        final RelativeLayout relativeLayout = (RelativeLayout) view.findViewById(R.id.skillsfrag_bg);
        relativeLayout.setBackgroundColor(color);

        /* PASSIVE */
        final TextView title = (TextView) view.findViewById(R.id.tituloPassive);
        title.setText("Pasiva : ");
        recyclerViewPassive = (RecyclerView) view.findViewById(R.id.skillsfrag_scrollableviewPassive);
        recyclerViewPassive.setHasFixedSize(true);
        linearLayoutManagerPassive = new LinearLayoutManager(getActivity().getBaseContext());
        recyclerViewPassive.setLayoutManager(linearLayoutManagerPassive);

        Bundle bundle = this.getArguments();
        if(bundle != null){
            AsyncRiotAPI.getChampionByID(new Action<Champion>() {
                @Override
                public void handle(APIException exception) {
                    Log.e("Error", "ni idea");
                }

                @Override
                public void perform(final Champion responseData) {
                    if(isAdded()){
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                adapterPassive = new DetalleStringAdapter(responseData.getPassive().toString());
                                recyclerViewPassive.setAdapter(adapterPassive);
                            }
                        });
                    }

                }
            }, bundle.getLong("ChampionID"));
        }

        return view;
    }

}
