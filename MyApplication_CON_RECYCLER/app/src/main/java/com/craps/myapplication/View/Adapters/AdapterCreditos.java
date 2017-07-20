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

import com.craps.myapplication.Model.Credito;
import com.craps.myapplication.R;
import com.craps.myapplication.Utils.TMDBHelper;
import com.craps.myapplication.View.Fragments.FragmentBusqueda;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by elmar on 18/5/2017.
 */

public class AdapterCreditos extends RecyclerView.Adapter implements View.OnClickListener, View.OnLongClickListener {

    private Context context;
    private List<Credito> listaCreditosOriginales;
    private List<Credito> listaCreditosFavoritos;
    private View.OnClickListener listener;
    private AdapterView.OnLongClickListener listenerLong;
    private Favoritable favoritable;

    public void setLongListener(View.OnLongClickListener unLongListener) {
        this.listenerLong = unLongListener;
    }

    public void setListener(View.OnClickListener listener) {
        this.listener = listener;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public void setListaCreditosOriginales(List<Credito> listaCreditosOriginales) {
        this.listaCreditosOriginales = listaCreditosOriginales;
    }

    public void addListaCreditosOriginales(List<Credito> listaCreditosOriginales) {
        this.listaCreditosOriginales.addAll(listaCreditosOriginales);
    }


    public List<Credito> getListaCreditosOriginales() {
        return listaCreditosOriginales;
    }

    //crear vista y viewholder
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View viewCelda;
        FragmentActivity unaActivity = (FragmentActivity) context;
        FragmentManager fragmentManager = (FragmentManager) unaActivity.getSupportFragmentManager();
        FragmentBusqueda fragmentBusqueda = (FragmentBusqueda) fragmentManager.findFragmentByTag("FragmentBuscador");


        viewCelda = layoutInflater.inflate(R.layout.detalle_celda_actores, parent, false);

        FormatoViewHolder peliculasViewHolder = new FormatoViewHolder(viewCelda);
        viewCelda.setOnClickListener(this);

        return peliculasViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final Credito unCredito = listaCreditosOriginales.get(position);
        FormatoViewHolder creditoViewHolder = (FormatoViewHolder) holder;
        creditoViewHolder.cargarFormato(unCredito);


    }

    @Override
    public int getItemCount() {
        return listaCreditosOriginales.size();
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
            textViewTitulo = (TextView) itemView.findViewById(R.id.peli_texto_celda);
            textViewDescripcion = (TextView) itemView.findViewById(R.id.detalleBusqueda);
            Typeface roboto = Typeface.createFromAsset(itemView.getContext().getAssets(), "fonts/Roboto-Light.ttf");
            textViewTitulo.setTypeface(roboto);
            textViewDescripcion.setTypeface(roboto);


            FragmentActivity unaActivity = (FragmentActivity) itemView.getContext();
            FragmentManager fragmentManager = (FragmentManager) unaActivity.getSupportFragmentManager();
            FragmentBusqueda fragmentBusqueda = (FragmentBusqueda) fragmentManager.findFragmentByTag("FragmentBuscador");


            if (fragmentBusqueda != null && fragmentBusqueda.isVisible()) {
                textViewTitulo = (TextView) itemView.findViewById(R.id.peli_texto_celda);
                textViewDescripcion = (TextView) itemView.findViewById(R.id.detalleBusqueda);
                textViewTitulo.setTypeface(roboto);
                textViewDescripcion.setTypeface(roboto);
            }
        }

        public void cargarFormato(Credito unCredito) {


            if (unCredito.getTitle() == null || unCredito.getTitle().isEmpty()) {
                if (unCredito.getOriginal_name() == null) {
                } else {
                    textViewTitulo.setText(unCredito.getOriginal_name());
                }
            } else {
                if (unCredito.getOriginal_title() == null) {
                } else {
                    textViewTitulo.setText(unCredito.getOriginal_title());
                }
            }
            textViewDescripcion.setText(unCredito.getCharacter());


            Picasso.with(imageView.getContext())
                    .load(TMDBHelper.getImagePoster(TMDBHelper.IMAGE_SIZE_W92, unCredito.getPoster_path()))
                    .placeholder(R.drawable.loading)
                    .error(R.drawable.posterpelinotfound)
                    .into(imageView);
        }


    }

    public interface Favoritable {
        public void recibirFormatoFavorito(Credito unCredito);
    }
}
