package example.hans.friendlylol;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.robrua.orianna.api.core.AsyncRiotAPI;
import com.robrua.orianna.type.api.Action;
import com.robrua.orianna.type.core.staticdata.Champion;
import com.robrua.orianna.type.exception.APIException;

import java.util.List;


/**
 * Created by hans6 on 15-06-2016.
 */
public class ChampionAdapter extends RecyclerView.Adapter<ChampionAdapter.ChampionViewHolder>{
    private Context context;
    private List<Champion> champions;
    private String version;


    public ChampionAdapter(Context context, List<Champion> champions, String version){
        this.context = context;
        this.champions = champions;
        this.version = version;
    }

    @Override
    public ChampionAdapter.ChampionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_campeones,parent,false);

        return new ChampionViewHolder(itemView);
    }



    @Override
    public void onBindViewHolder(final ChampionAdapter.ChampionViewHolder holder, int position) {
        final Champion champion = champions.get(position);
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
        String url = "http://ddragon.leagueoflegends.com/cdn/"+version+"/img/champion/"+champion.getImage().getFull();
        Glide.with(context.getApplicationContext())
                .load(url)
                .into(holder.imageCampeon);
        holder.labelNombre.setText(champion.getName().toString());
        char[] charArray = champion.getTitle().toCharArray();
        charArray[0] = Character.toUpperCase(charArray[0]);
        holder.labelTitle.setText(new String(charArray));

    }

    @Override
    public int getItemCount() {
        return champions.size();
    }




    public static class ChampionViewHolder extends RecyclerView.ViewHolder {
        LinearLayout championLayout;
        TextView labelNombre;
        TextView labelTitle;
        ImageView imageCampeon;


        public ChampionViewHolder(View v) {
            super(v);
            championLayout = (LinearLayout) v.findViewById(R.id.container_holder);
            labelNombre = (TextView) v.findViewById(R.id.label_nombre);
            labelTitle = (TextView) v.findViewById(R.id.label_title);
            imageCampeon = (ImageView) v.findViewById(R.id.image_campeon);
        }
    }







}

