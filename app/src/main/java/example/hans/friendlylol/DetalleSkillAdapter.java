package example.hans.friendlylol;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.robrua.orianna.type.core.staticdata.ChampionSpell;

import java.util.List;

/**
 * Created by Matias on 11-07-16.
 */
public class DetalleSkillAdapter extends RecyclerView.Adapter<DetalleSkillAdapter.ViewHolder> {
    private List<ChampionSpell> habilidades;
    private String version;
    private Context context;


    public DetalleSkillAdapter(Context context, List<ChampionSpell> habilidades, String version) {
        //Constructor
        this.context = context;
        this.habilidades = habilidades;
        this.version = version;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView nom;
        TextView descrip;
        ImageView img;
        public ViewHolder(View v) {
            super(v);
            img = (ImageView) itemView.findViewById(R.id.icon);
            nom = (TextView) itemView.findViewById(R.id.nombre);
            descrip = (TextView) itemView.findViewById(R.id.descripcion);
        }
    }

    @Override
    public DetalleSkillAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_skill_detail, parent, false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(DetalleSkillAdapter.ViewHolder holder, int position) {
        String url = "http://ddragon.leagueoflegends.com/cdn/"+version+"/img/spell/"+habilidades.get(position).getImage().getFull();
        Glide.with(context.getApplicationContext())
                .load(url)
                .into(holder.img);
        holder.nom.setText(habilidades.get(position).getName());
        holder.descrip.setText(Html.fromHtml(habilidades.get(position).getDescription())); // gran texto y la info se entrega como formato html, por lo que tengo que convertirla
    }

    @Override
    public int getItemCount() {
        return habilidades.size();
    }
}
