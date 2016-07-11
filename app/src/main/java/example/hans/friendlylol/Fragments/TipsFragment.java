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

import example.hans.friendlylol.DetalleAdapter;
import example.hans.friendlylol.R;

/**
 * Created by Matias on 09-07-16.
 */
public class TipsFragment extends Fragment {
    int color;
    DetalleAdapter adapter;
    DetalleAdapter adapter2;

    //private String version;
    private RecyclerView recyclerViewTips;
    private LinearLayoutManager linearLayoutManagerTips;
    private RecyclerView recyclerViewTipsCounter;
    private LinearLayoutManager linearLayoutManagerTipsCounter;
    private static final String API_KEY = "4821637b-1fef-4651-832f-f4177883cfa5";

    public TipsFragment() {
        //Constructor
    }

    @SuppressLint("ValidFragment")
    public TipsFragment(int color) {
        this.color = color;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        AsyncRiotAPI.setAPIKey(API_KEY);
        AsyncRiotAPI.setRegion(Region.LAS);

        View view = inflater.inflate(R.layout.tips_fragment, container, false);

        final RelativeLayout relativeLayout = (RelativeLayout) view.findViewById(R.id.tipsfrag_bg);
        relativeLayout.setBackgroundColor(color);

        /* CONSEJO */
        final TextView title = (TextView) view.findViewById(R.id.titulo);
        title.setText("Consejos :");

        recyclerViewTips = (RecyclerView) view.findViewById(R.id.tipsfrag_scrollableview);
        recyclerViewTips.setHasFixedSize(true);

        linearLayoutManagerTips = new LinearLayoutManager(getActivity().getBaseContext());
        recyclerViewTips.setLayoutManager(linearLayoutManagerTips);


        /* CONSEJO SOBRE COOUTER */
        final TextView title2 = (TextView) view.findViewById(R.id.titulo2);
        title2.setText("Consejos sobre Counter : ");

        recyclerViewTipsCounter = (RecyclerView) view.findViewById(R.id.tipsfrag_scrollableview2);
        recyclerViewTipsCounter.setHasFixedSize(true);

        linearLayoutManagerTipsCounter = new LinearLayoutManager(getActivity().getBaseContext());
        recyclerViewTipsCounter.setLayoutManager(linearLayoutManagerTipsCounter);

        Bundle bundle = this.getArguments();
        if(bundle != null){
            AsyncRiotAPI.getChampionByID(new Action<Champion>() {
                @Override
                public void handle(APIException exception) {
                    Log.e("Error", "ni idea");
                }

                @Override
                public void perform(final Champion responseData) {
                    //algunas veces de daba error, no siempre pero esto lo solucionaba, no me acuerdo porque xD
                    if(isAdded()){
                        /*Lo de interfaz debe ir en el thread de la interfaz
                        con el metodo runOnUiThread se logra
                        el getActivity() se usa solo en los fragment, si llamas al metodo
                        run... en una acitvity normal no es necesario*/
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                adapter = new DetalleAdapter(responseData.getAllyTips());
                                recyclerViewTips.setAdapter(adapter);
                                // .enemyTips -> tips a saber contra los enemigos
                                adapter2 = new DetalleAdapter(responseData.getEnemyTips());
                                recyclerViewTipsCounter.setAdapter(adapter2);
                            }
                        });
                    }

                }
            }, bundle.getLong("ChampionID"));
        }

        return view;
    }

}

/*              SEGUNDO RECYCLE VIEW

        final TextView title2 = (TextView) view.findViewById(R.id.titulo2);
        title2.setText("Consejos sobre Counter : ");

        recyclerViewTipsCounter = (RecyclerView) view.findViewById(R.id.tipsfrag_scrollableview2);
        recyclerViewTipsCounter.setHasFixedSize(true);

        linearLayoutManagerTipsCounter = new LinearLayoutManager(getActivity().getBaseContext());
        recyclerViewTipsCounter.setLayoutManager(linearLayoutManagerTipsCounter);

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
                                adapter2 = new DetalleAdapter(responseData.getEnemyTips());
                                recyclerViewTipsCounter.setAdapter(adapter2);
                            }
                        });
                    }

                }
            }, bundle.getLong("ChampionID"));
        }

*/


//list<String> listaDeDatos = new ArrayList<String>();
//for (int i = 0; i < VersionModel.data.length; i++) {
//    listaDeDatos.add(VersionModel.data[i]);
//}

//List<String> datosCampeon = new ArrayList<>();
//adapter = new DetailAdapter(datosCampeon);

// Dentro de ..:: DetailAdapter ::.. hay un array[] fijo que ingresa datos para mostrar (no se utiliza List<String>)
//adapter = new DetailAdapter();
//recyclerView.setAdapter(adapter);