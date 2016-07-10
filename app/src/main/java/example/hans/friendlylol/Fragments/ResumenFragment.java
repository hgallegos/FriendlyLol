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
import android.widget.FrameLayout;

import com.robrua.orianna.api.core.AsyncRiotAPI;
import com.robrua.orianna.type.api.Action;
import com.robrua.orianna.type.core.common.Region;
import com.robrua.orianna.type.core.staticdata.Champion;
import com.robrua.orianna.type.exception.APIException;

import java.net.ResponseCache;

import example.hans.friendlylol.DetailAdapter;
import example.hans.friendlylol.DetalleAdapter;
import example.hans.friendlylol.R;

/**
 * Created by Matias on 07-07-16.
 */

public class ResumenFragment extends Fragment {
    int color;
    DetalleAdapter adapter;

    //private String version;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private static final String API_KEY = "4821637b-1fef-4651-832f-f4177883cfa5";

    public ResumenFragment() {
        //Constructor
    }

    @SuppressLint("ValidFragment")
    public ResumenFragment(int color) {
        this.color = color;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //String strtext = this.getArguments().getString("nombreCampeon");
        AsyncRiotAPI.setAPIKey(API_KEY);
        AsyncRiotAPI.setRegion(Region.LAS);

        View view = inflater.inflate(R.layout.dummy_fragment, container, false);

        final FrameLayout frameLayout = (FrameLayout) view.findViewById(R.id.dummyfrag_bg);
        frameLayout.setBackgroundColor(color);

        recyclerView = (RecyclerView) view.findViewById(R.id.dummyfrag_scrollableview);
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
                                recyclerView.setAdapter(adapter);
                            }
                        });
                    }

                }
            }, bundle.getLong("ChampionID"));
        }

        //list<String> listaDeDatos = new ArrayList<String>();
        //for (int i = 0; i < VersionModel.data.length; i++) {
        //    listaDeDatos.add(VersionModel.data[i]);
        //}

        //List<String> datosCampeon = new ArrayList<>();
        //adapter = new DetailAdapter(datosCampeon);

        // Dentro de ..:: DetailAdapter ::.. hay un array[] fijo que ingresa datos para mostrar (no se utiliza List<String>)
        //adapter = new DetailAdapter();
        //recyclerView.setAdapter(adapter);

        return view;
    }
}

