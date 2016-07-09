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

import java.util.ArrayList;
import java.util.List;

import example.hans.friendlylol.DetalleAdapter;
import example.hans.friendlylol.R;

/**
 * Created by Matias on 08-07-16.
 */
public class ResumenFragment extends Fragment {
    int color;
    long idCampeon;
    private List<String> listaDeDatos = new ArrayList<>();
    DetalleAdapter adapter;

    private static final String API_KEY = "4821637b-1fef-4651-832f-f4177883cfa5";
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;

    public ResumenFragment() {
        //Constructor
    }

    @SuppressLint("ValidFragment")
    public ResumenFragment(int color, long idCampeon) {
        this.color = color;
        this.idCampeon = idCampeon;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dummy_fragment, container, false);

        AsyncRiotAPI.setAPIKey(API_KEY);
        AsyncRiotAPI.setRegion(Region.LAS);

        final FrameLayout frameLayout = (FrameLayout) view.findViewById(R.id.dummyfrag_bg);
        frameLayout.setBackgroundColor(color);

        recyclerView = (RecyclerView) view.findViewById(R.id.dummyfrag_scrollableview);
        recyclerView.setHasFixedSize(true);

        linearLayoutManager = new LinearLayoutManager(getActivity().getBaseContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        //final List<String> listaDeDatos = new List<String>;
        AsyncRiotAPI.getChampionByID(new Action<Champion>() {
            @Override
            public void handle(APIException exception) {
                Log.e("Error campeones", "ni idea");
            }

            @Override
            public void perform(Champion responseData) {
                listaDeDatos = (ArrayList)responseData.getAllyTips();
//                for (int i = 0; i < responseData.getAllyTips().size(); i++) {
//                    listaDeDatos.add(responseData.getAllyTips().get(i).toString());
//                }
            }
        }, idCampeon);

        // PRUEBA CON LIST<STRING> ... AQUI SE GUARDA EN LA LISTA LOS DATOS DEl ARREGLO QUE SE ENCUENTRA EN LA CLASE VersionModel
        //List<String> listaDeDatos = new List<String>;
        //for (int i = 0; i < VersionModel.data.length; i++) {
        //    listaDeDatos.add(VersionModel.data[i]);
        //}


        adapter = new DetalleAdapter(listaDeDatos);
        recyclerView.setAdapter(adapter);
        return view;
    }
}

