package example.hans.friendlylol;

import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Matias on 10-07-16.
 */
public class DetalleStringAdapter extends RecyclerView.Adapter<DetalleStringAdapter.ViewHolder> {
    private String info;

    public DetalleStringAdapter(String info) {
        //Constructor
        this.info = info;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView informacion;
        public ViewHolder(View v) {
            super(v);
            informacion = (TextView) itemView.findViewById(R.id.info_texto);
        }
    }

    @Override
    public DetalleStringAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_detail, parent, false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(DetalleStringAdapter.ViewHolder holder, int position) {
        holder.informacion.setText(Html.fromHtml(info));
        // la info se entrega como formato html, por lo que tengo que convertirla
    }

    @Override
    public int getItemCount() {
        return 1;
    }
}

/*

    public DetalleStringAdapter(String info) {
        this.info = info;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView informacion;
        public ViewHolder(View v) {
            super(v);
            informacion = (TextView) itemView.findViewById(R.id.info_texto);
        }
    }

    // Create new views (invoked by the layout manager)
    @Override
    public DetalleStringAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // crea una new view - Card_detail que contiene los datos
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_detail, parent, false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(DetalleStringAdapter.ViewHolder holder, int position) {
        holder.informacion.setText(info); //informacion es el dato que nos entrega el arreglo
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return info.length();
    }

*/

