package example.hans.friendlylol.Fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.robrua.orianna.api.core.AsyncRiotAPI;
import com.robrua.orianna.api.core.RiotAPI;
import com.robrua.orianna.api.core.StaticDataAPI;
import com.robrua.orianna.type.api.Action;
import com.robrua.orianna.type.core.common.Region;
import com.robrua.orianna.type.core.common.Season;
import com.robrua.orianna.type.core.staticdata.Champion;
import com.robrua.orianna.type.core.stats.ChampionStats;
import com.robrua.orianna.type.core.stats.PlayerStatsSummary;
import com.robrua.orianna.type.core.stats.PlayerStatsSummaryType;
import com.robrua.orianna.type.core.summoner.Summoner;
import com.robrua.orianna.type.exception.APIException;
import com.squareup.otto.Bus;
import com.squareup.otto.Produce;
import com.squareup.otto.Subscribe;
import com.squareup.otto.ThreadEnforcer;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import example.hans.friendlylol.BaseApplication;
import example.hans.friendlylol.MainActivity;
import example.hans.friendlylol.R;

/**
 * Created by hans6 on 10-07-2016.
 */
public class ResumenSummonerFragment extends Fragment {

    private View view;
    private ImageView summonerImage;
    private TextView summoneName, summonerLevel, kda, torreText, subditosText;

    private static final String API_KEY = "4821637b-1fef-4651-832f-f4177883cfa5";
    public static Bus bus = new Bus(ThreadEnforcer.ANY);

    public ResumenSummonerFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BaseApplication.getEventBus().register(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_resumen_summoner, container, false);
        summoneName = (TextView) view.findViewById(R.id.summoner_name);
        summonerImage = (ImageView) view.findViewById(R.id.image_summoner);
        summonerLevel = (TextView) view.findViewById(R.id.summoner_level);
        kda = (TextView) view.findViewById(R.id.kdaSuma);
        torreText = (TextView) view.findViewById(R.id.torretas);
        subditosText = (TextView) view.findViewById(R.id.subditos);

        AsyncRiotAPI.setAPIKey(API_KEY);
        AsyncRiotAPI.setRegion(Region.LAS);
        return view;
    }

    @Subscribe
    public void getMessage(String s) {
        AsyncRiotAPI.getSummonerByName(new Action<Summoner>() {
            @Override
            public void handle(APIException exception) {

            }

            @Override
            public void perform(final Summoner responseData) {
                ArrayList<PlayerStatsSummary> statsSummaries = new ArrayList<PlayerStatsSummary>(responseData.getStats(Season.SEASON2016).values());
                PlayerStatsSummary statsRankedSolo = responseData.getStats(Season.SEASON2016).get(PlayerStatsSummaryType.RankedSolo5x5);
                final long kills, deaths, assists, games, torres, subditos;
                if(statsRankedSolo != null){
                    Log.d("Stats",  ""+responseData.getID());
                    games = statsRankedSolo.getLosses()+statsRankedSolo.getWins();
                    kills = (statsRankedSolo.getAggregatedStats().getTotalKills()/games);
                    List<ChampionStats> champions = new ArrayList<ChampionStats>(responseData.getRankedStats(Season.SEASON2016).values());
                    deaths = (champions.get(0).getStats().getTotalDeaths()/games);
                    torres = statsRankedSolo.getAggregatedStats().getTotalTurretsKilled();
                    subditos = statsRankedSolo.getAggregatedStats().getTotalMinionKills();
                    assists = statsRankedSolo.getAggregatedStats().getTotalAssists()/games;
                }else{
                    PlayerStatsSummary statsUnranked = responseData.getStats(Season.SEASON2016).get(PlayerStatsSummaryType.Unranked);
                    Log.d("Stats",  ""+responseData.getID());
                    games = statsUnranked.getLosses()+statsUnranked.getWins();
                    kills = statsUnranked.getAggregatedStats().getTotalKills()/games;
                    //List<ChampionStats> champions = new ArrayList<ChampionStats>(responseData.getRankedStats(Season.SEASON2016).values());
                    deaths = statsUnranked.getAggregatedStats().getTotalDeaths()/games;
                    assists = statsUnranked.getAggregatedStats().getTotalAssists()/games;
                    torres=statsUnranked.getAggregatedStats().getTotalTurretsKilled();
                    subditos = statsUnranked.getAggregatedStats().getTotalMinionKills()/games;
                }

                final String latestVersion = RiotAPI.getVersions().get(0);
                final String url = "http://ddragon.leagueoflegends.com/cdn/"+latestVersion+"/img/profileicon/"+responseData.getProfileIconID()+".png";
                if(isAdded()){
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Glide.with(getActivity())
                                    .load(url)
                                    .into(summonerImage);
                            summoneName.setText(responseData.getName());
                            summonerLevel.setText("Latino América Sur - Nivel "+responseData.getLevel());
                            kda.setText(kills+"/"+deaths+"/"+assists);
                            torreText.setText(""+torres);
                            subditosText.setText(""+subditos);


                        }
                    });
                }

            }
        },s);
    }


}
