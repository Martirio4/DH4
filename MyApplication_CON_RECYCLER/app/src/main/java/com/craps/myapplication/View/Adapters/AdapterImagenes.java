package com.craps.myapplication.View.Adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.craps.myapplication.Model.Imagen;
import com.craps.myapplication.R;
import com.craps.myapplication.Utils.TMDBHelper;
import com.craps.myapplication.View.Fragments.FragmentBusqueda;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by elmar on 18/5/2017.
 */

public class AdapterImagenes extends RecyclerView.Adapter implements View.OnClickListener,View.OnLongClickListener {

    private Context context;
    private List<Imagen> listaImagenesOriginales;
    private List<Imagen> listaImagenesFavoritos;
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

    public void setListaImagenesOriginales(List<Imagen> listaImagenesOriginales) {
        this.listaImagenesOriginales = listaImagenesOriginales;
    }
    public void addListaImagenesOriginales(List<Imagen> listaImagenesOriginales) {
        this.listaImagenesOriginales.addAll(listaImagenesOriginales);
    }


    public List<Imagen> getListaImagenesOriginales(){
        return listaImagenesOriginales;
    }

    //crear vista y viewholder
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View viewCelda;
        viewCelda = layoutInflater.inflate(R.layout.detalle_celda_actores_foto_multiple, parent, false);
        FormatoViewHolder imagenesViewHolder = new FormatoViewHolder(viewCelda);


        return imagenesViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final Imagen unImagen = listaImagenesOriginales.get(position);
        FormatoViewHolder imagenViewHolder = (FormatoViewHolder) holder;
        imagenViewHolder.cargarFormato(unImagen);


    }

    @Override
    public int getItemCount() {
        return listaImagenesOriginales.size();
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


        public FormatoViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.peli_img_celda);
        }

        public void cargarFormato(Imagen unImagen) {




            Picasso.with(imageView.getContext())
                    .load(TMDBHelper.getImagePoster(TMDBHelper.IMAGE_SIZE_W185,unImagen.getRutaImagen()))
                    .placeholder(R.drawable.loading)
                    .error(R.drawable.noimagethumb)
                    .into(imageView);
        }


    }

    public interface Favoritable{
        public void recibirFormatoFavorito(Imagen unImagen);
    }
}
