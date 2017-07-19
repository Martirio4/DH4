package com.craps.myapplication.View.Adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.craps.myapplication.Model.Actor;

import com.craps.myapplication.R;
import com.craps.myapplication.Utils.TMDBHelper;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by elmar on 18/5/2017.
 */

public class AdapterActores extends RecyclerView.Adapter implements View.OnClickListener{

    private Context context;
    private List<Actor> listaActoresOriginales;
    private View.OnClickListener listener;
    private Favoritable favoritable;
    public void setListener(View.OnClickListener listener){
        this.listener=listener;
    }
    public void setContext(Context context) {
        this.context = context;
    }
    public void setListaActoresOriginales(List<Actor> listaActoresOriginales) {
        this.listaActoresOriginales = listaActoresOriginales;
    }

    public List<Actor> getListaActoresOriginales(){
        return listaActoresOriginales;
    }

    //crear vista y viewholder
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View viewCelda = layoutInflater.inflate(R.layout.detalle_celda_actores, parent, false);
        FormatoViewHolder actoresViewHolder = new FormatoViewHolder(viewCelda);
        viewCelda.setOnClickListener(this);
        //ACA AGREGO EL LONG CLICK?!=!=!"="?=!"
        return actoresViewHolder;
    }
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final Actor unActor = listaActoresOriginales.get(position);
        FormatoViewHolder formatoViewHolder = (FormatoViewHolder) holder;
        formatoViewHolder.cargarActor(unActor);
    }
    @Override
    public int getItemCount() {
        return listaActoresOriginales.size();
    }
    @Override
    public void onClick(View view) {
        listener.onClick(view);
    }
    //creo el viewholder que mantiene las referencias
    //de los elementos de la celda
    private static class FormatoViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        //private TextView textViewTitulo;
        private ImageButton imageButton;
        private RatingBar ratingBar;
        private TextView textViewNombreActor;
        private TextView textViewNombrePersonaje;

        public FormatoViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.peli_img_celda);
            textViewNombreActor = (TextView) itemView.findViewById(R.id.peli_texto_celda);
            textViewNombrePersonaje = (TextView) itemView.findViewById(R.id.detalleBusqueda);

            Typeface roboto = Typeface.createFromAsset(itemView.getContext().getAssets(), "fonts/Roboto-Light.ttf");
            textViewNombreActor.setTypeface(roboto);
            textViewNombrePersonaje.setTypeface(roboto);
        }
        public void cargarActor(Actor unActor) {

            textViewNombreActor.setText(unActor.getNombreActor());
            textViewNombrePersonaje.setText(unActor.getPersonaje());

            Picasso.with(imageView.getContext())
                    .load(TMDBHelper.getImagePoster(TMDBHelper.IMAGE_SIZE_W92, unActor.getFotoPerfilActor()))
                    .placeholder(R.drawable.loading)
                    .error(R.drawable.desconocidocolor)
                    .into(imageView);
        }
    }

    public interface Favoritable{
        public void recibirFormatoFavorito(Actor unActor);
    }
}
