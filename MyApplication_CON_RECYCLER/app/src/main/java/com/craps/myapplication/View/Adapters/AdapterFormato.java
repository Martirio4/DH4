package com.craps.myapplication.View.Adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import com.craps.myapplication.Model.Formato;
import com.craps.myapplication.R;
import com.craps.myapplication.Utils.TMDBHelper;
import com.craps.myapplication.View.Fragments.FragmentBusqueda;
import com.squareup.picasso.Picasso;
import java.util.List;
import android.support.v4.app.FragmentManager;

/**
 * Created by elmar on 18/5/2017.
 */

public class AdapterFormato extends RecyclerView.Adapter implements View.OnClickListener,View.OnLongClickListener {

    private Context context;
    private List<Formato> listaFormatosOriginales;
    private List<Formato> listaFormatosFavoritos;
    private View.OnClickListener listener;
    private AdapterView.OnLongClickListener listenerLong;
    private Favoritable favoritable;

    public void setLongListener(View.OnLongClickListener unLongListener){
        this.listenerLong=unLongListener;
    }
    public void setListener(View.OnClickListener listener){
        this.listener=listener;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public void setListaFormatosOriginales(List<Formato> listaFormatosOriginales) {
        this.listaFormatosOriginales = listaFormatosOriginales;
    }

    public List<Formato> getListaFormatosOriginales(){
        return listaFormatosOriginales;
    }

    //crear vista y viewholder
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View viewCelda;
        FragmentActivity unaActivity= (FragmentActivity) context;
        FragmentManager fragmentManager = (FragmentManager) unaActivity.getSupportFragmentManager();
        FragmentBusqueda fragmentBusqueda= (FragmentBusqueda) fragmentManager.findFragmentByTag("FragmentBuscador");


        if (fragmentBusqueda != null && fragmentBusqueda.isVisible()) {
            viewCelda = layoutInflater.inflate(R.layout.detalle_celda_busqueda, parent, false);
        }
        else{
            viewCelda = layoutInflater.inflate(R.layout.detalle_celda, parent, false);
        }
        FormatoViewHolder peliculasViewHolder = new FormatoViewHolder(viewCelda);
        viewCelda.setOnClickListener(this);

        return peliculasViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final Formato unFormato = listaFormatosOriginales.get(position);
        FormatoViewHolder formatoViewHolder = (FormatoViewHolder) holder;
        formatoViewHolder.cargarFormato(unFormato);

        formatoViewHolder.imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                favoritable=(Favoritable)v.getContext();
                favoritable.recibirFormatoFavorito(unFormato);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listaFormatosOriginales.size();
    }

    @Override
    public void onClick(View view) {
        listener.onClick(view);
    }

    @Override
    public boolean onLongClick(View v) {
        listenerLong.onLongClick(v);
        return true;
    }

    //creo el viewholder que mantiene las referencias
    //de los elementos de la celda

    private static class FormatoViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        //private TextView textViewTitulo;
        private ImageButton imageButton;
        private RatingBar ratingBar;
        private TextView textViewTitulo;
        private TextView textViewDescripcion;

        public FormatoViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.peli_img_celda);
            imageButton=(ImageButton) itemView.findViewById(R.id.boton_favo);
            ratingBar = (RatingBar) itemView.findViewById(R.id.ratingbar);

            FragmentActivity unaActivity= (FragmentActivity) itemView.getContext();
            FragmentManager fragmentManager = (FragmentManager) unaActivity.getSupportFragmentManager();
            FragmentBusqueda fragmentBusqueda= (FragmentBusqueda) fragmentManager.findFragmentByTag("FragmentBuscador");

            Typeface roboto = Typeface.createFromAsset(itemView.getContext().getAssets(), "fonts/Roboto-Light.ttf");

            if (fragmentBusqueda != null && fragmentBusqueda.isVisible()) {
                textViewTitulo =(TextView) itemView.findViewById(R.id.peli_texto_celda);
                textViewDescripcion=(TextView) itemView.findViewById(R.id.detalleBusqueda);
                textViewTitulo.setTypeface(roboto);
                textViewDescripcion.setTypeface(roboto);
            }

        }

        public void cargarFormato(Formato unFormato) {

            ratingBar.setRating(unFormato.getVote_average()/2);
            if (textViewTitulo !=null){
                textViewDescripcion.setText(unFormato.getOverview());
                if (unFormato.getTitle()==null){
                    textViewTitulo.setText(unFormato.getName());
                }
                else{
                    textViewTitulo.setText(unFormato.getTitle());

                }

            }

            Picasso.with(imageView.getContext())
                    .load(TMDBHelper.getImagePoster(TMDBHelper.IMAGE_SIZE_W92,unFormato.getPoster_path()))
                    .placeholder(R.drawable.loading)
                    .error(R.drawable.noimagethumb)
                    .into(imageView);
        }


    }

    public interface Favoritable{
        public void recibirFormatoFavorito(Formato unFormato);
    }
}
