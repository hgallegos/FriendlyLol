package example.hans.friendlylol;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Matias on 08-07-16.
 * MANEJA UN ..:: LIST<STRING> ::..
 */

public class DetalleAdapter extends RecyclerView.Adapter<DetalleAdapter.ViewHolder> {
    private List<String> listaDeDatos = new ArrayList<>();

    public DetalleAdapter(List<String> listaDeDatos) {
        this.listaDeDatos = listaDeDatos;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        CardView cardItem;
        TextView informacion;
        public ViewHolder(View v) {
            super(v);
            cardItem = (CardView) itemView.findViewById(R.id.card_view);
            informacion = (TextView) itemView.findViewById(R.id.info_texto);
        }
    }

    // Create new views (invoked by the layout manager)
    @Override
    public DetalleAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // crea una new view - Card_detail que contiene los datos
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_detail, parent, false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(DetalleAdapter.ViewHolder holder, int position) {
        holder.informacion.setText(listaDeDatos.get(position)); //informacion es el dato que nos entrega el arreglo
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return listaDeDatos.size();
    }
}
