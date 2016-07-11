package example.hans.friendlylol.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.robrua.orianna.api.core.AsyncRiotAPI;
import com.robrua.orianna.type.api.Action;
import com.robrua.orianna.type.core.common.Region;
import com.robrua.orianna.type.core.summoner.Summoner;
import com.robrua.orianna.type.exception.APIException;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;
import com.squareup.otto.ThreadEnforcer;

import example.hans.friendlylol.BaseApplication;
import example.hans.friendlylol.R;

/**
 * Created by hans6 on 10-07-2016.
 */
public class HistoryPlayFragment extends Fragment {
    private View view;
    private TextView textView;

    private static final String API_KEY = "4821637b-1fef-4651-832f-f4177883cfa5";
    public static Bus bus = new Bus(ThreadEnforcer.ANY);

    public HistoryPlayFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //BaseApplication.getEventBus().register(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_history_play, container, false);
        textView = (TextView) view.findViewById(R.id.summoner_name);

        AsyncRiotAPI.setAPIKey(API_KEY);
        AsyncRiotAPI.setRegion(Region.LAS);
        return view;
    }
    /*
    @Subscribe
    public void getMessage(String s) {
        AsyncRiotAPI.getSummonerByName(new Action<Summoner>() {
            @Override
            public void handle(APIException exception) {

            }

            @Override
            public void perform(final Summoner responseData) {
                if(isAdded()){
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            textView.setText("Historial de: "+responseData.getName());
                        }
                    });
                }
            }
        },s);
    }*/


}

