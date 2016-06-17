package example.hans.friendlylol;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.robrua.orianna.type.core.champion.ChampionStatus;

import java.util.List;


/**
 * Created by hans6 on 15-06-2016.
 */
public class ChampionStatusAdapter extends RecyclerView.Adapter<ChampionStatusAdapter.ChampionViewHolder>{
    private Context context;
    private List<ChampionStatus> champions;
    private String version;


    public ChampionStatusAdapter(Context context, List<ChampionStatus> champions, String version){
        this.context = context;
        this.champions = champions;
        this.version = version;
    }

    @Override
    public ChampionStatusAdapter.ChampionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_campeones_status,parent,false);

        return new ChampionViewHolder(itemView);
    }



    @Override
    public void onBindViewHolder(final ChampionStatusAdapter.ChampionViewHolder holder, int position) {
        final ChampionStatus champion = champions.get(position);
       /* AsyncRiotAPI.getVersions(new Action<List<String>>() {
            @Override
            public void handle(APIException exception) {
                Log.e("Error Version","no se puede obtener");
            }

            @Override
            public void perform(List<String> responseData) {
                String url = "http://ddragon.leagueoflegends.com/cdn/"+responseData.get(0).toString()+"/img/champion/"+champion.getImage().getFull();
                Glide.with(context.getApplicationContext())
                        .load(url)
                        .into(holder.imageCampeon);
                holder.labelNombre.setText(champion.getName().toString());
                holder.labelTitle.setText(champion.getTitle().toString());
            }
        });
        //holder.labelNombre.setText(champion.getName());
        //holder.labelTitle.setText(champion.getTitle());*/
        String url = "http://ddragon.leagueoflegends.com/cdn/"+version+"/img/champion/"+champion.getChampion().getImage().getFull();
        Glide.with(context.getApplicationContext())
                .load(url)
                .into(holder.imageCampeon);
        holder.nombre.setText(champion.getChampion().getName());

    }

    @Override
    public int getItemCount() {
        return champions.size();
    }




    public static class ChampionViewHolder extends RecyclerView.ViewHolder {
        LinearLayout championLayout;
        ImageView imageCampeon;
        TextView nombre;


        public ChampionViewHolder(View v) {
            super(v);
            championLayout = (LinearLayout) v.findViewById(R.id.container_holder);
            imageCampeon = (ImageView) v.findViewById(R.id.image_campeon);
            nombre = (TextView) v.findViewById(R.id.label_nombre_free);
        }
    }







}

