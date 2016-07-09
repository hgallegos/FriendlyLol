package example.hans.friendlylol;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Matias on 07-07-16.
 * MANEJA UN ..:: STRING[] ::..
 */
public class DetailAdapter extends RecyclerView.Adapter<DetailAdapter.ViewHolder> {
    //private String[] mDataset;
    private String[] mDataset = {
            "Cupcake Cupcake Cupcake Cupcake Cupcake Cupcake Cupcake",
            "Donut bla bla este es un texto de prueba para ver como se comporta el cardView aaaaaaaaaaaaaaaaaaaaaaaah",
            "Eclair",
            "Froyo",
            "Gingerbread",
            "Honeycomb",
            "Icecream Sandwich",
            "Jelly Bean",
            "Kitkat",
            "Lollipop"
    }; // ARREGLO A MOSTRAR EN EL TABLAYOUT, UN NOMBRE POR CARDVIEW

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        //public TextView mTextView;
        CardView cardItem;
        TextView informacion;
        public ViewHolder(View v) {
            super(v);
            //mTextView = v;
            cardItem = (CardView) itemView.findViewById(R.id.card_view);
            informacion = (TextView) itemView.findViewById(R.id.info_texto);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public void DetailAdapter(String[] myDataset) {
        this.mDataset = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public DetailAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view - Card_detail que contiene los datos
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_detail, parent, false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(DetailAdapter.ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        //holder.mTextView.setText(mDataset[position]);
        holder.informacion.setText(mDataset[position]); //informacion es el dato que nos entrega el arreglo
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.length;
    }
}
