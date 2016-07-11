package example.hans.friendlylol;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.robrua.orianna.type.core.staticdata.Skin;

import java.util.List;

/**
 * Created by Matias on 11-07-16.
 */
public class DetalleSkinsAdapter extends RecyclerView.Adapter<DetalleSkinsAdapter.ViewHolder> {
    private String campeon;
    private String nombreSinSignos;
    private String nombreSinEspaciosSignos;
    private List<Skin> skins;
    private String version;
    private Context context;


    public DetalleSkinsAdapter(Context context, String campeon, List<Skin> skins, String version) {
        //Constructor
        this.context = context;
        this.campeon = campeon;
        this.skins = skins;
        this.version = version;

        this.nombreSinSignos = campeon.replaceAll("\\W","");
        this.nombreSinEspaciosSignos = nombreSinSignos.replaceAll("\\s","");
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView nom;
        ImageView img;
        public ViewHolder(View v) {
            super(v);
            img = (ImageView) itemView.findViewById(R.id.skin);
            nom = (TextView) itemView.findViewById(R.id.nombre);
        }
    }

    @Override
    public DetalleSkinsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_skins_detail, parent, false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(DetalleSkinsAdapter.ViewHolder holder, int position) {
        String url = "http://ddragon.leagueoflegends.com/cdn/img/champion/splash/"+nombreSinEspaciosSignos+"_"+skins.get(position).getNum()+".jpg";
        Glide.with(context.getApplicationContext())
                .load(url)
                .into(holder.img);
        holder.nom.setText(skins.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return skins.size();
    }
}
