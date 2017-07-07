package com.craps.myapplication.View.Fragments;


import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.craps.myapplication.ControllerFormato.ControllerFormato;
import com.craps.myapplication.Model.Actor;
import com.craps.myapplication.Model.Credito;
import com.craps.myapplication.Model.Formato;
import com.craps.myapplication.Model.Imagen;
import com.craps.myapplication.R;
import com.craps.myapplication.Utils.ResultListener;
import com.craps.myapplication.Utils.TMDBHelper;
import com.craps.myapplication.View.Adapters.AdapterCreditos;
import com.craps.myapplication.View.Adapters.AdapterFormato;
import com.craps.myapplication.View.Adapters.AdapterImagenes;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentActores extends Fragment {

    public static final String ACTORID="ACTORID";


    private RecyclerView recyclerCreditos;
    private AdapterCreditos adapterCreditos;
    RecyclerView recyclerImagenes;
    AdapterImagenes adapterImagenes;
    private LinearLayoutManager layoutManagerDetalle;
    private ControllerFormato controllerFragmentActores;
    public FragmentActores() {
    }
    private Boolean isLoading = false;
    private Integer actorid;
    private Integer formatoOrigenId;
    private String nombreActor;
    private String fechaNacimientoActor;
    private String biografiaActor;
    private String fotoActor;
    private Float popularidadActor;
    private TextView textonombre;
    private TextView textoaño;
    private TextView textosinopsis;
    private TextView textCalificacion;
    private ImageButton imageButton;
    private Notificable notificable;


    //DECLARO INTERFAZ
    public interface Notificable {
        public void recibirFormatoClickeado(Formato formato, String origen, Integer pagina, String StringABuscar, Integer drawerId);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_actores, container, false);

        imageButton=(ImageButton) view.findViewById(R.id.detalle_img);
        textonombre=(TextView)view.findViewById(R.id.tag_nombre2);
        textoaño=(TextView)view.findViewById(R.id.tag_año2);
        textosinopsis=(TextView)view.findViewById(R.id.tag_sinopsis2);
        textCalificacion = (TextView) view.findViewById(R.id.textViewrating);

        controllerFragmentActores= new ControllerFormato(view.getContext());

        //RECIBO EL BUNDLE Y SACVO LOS DATOS, LOS PONGO EN LOS TEXTVIEWS
        Bundle unBundle= getArguments();
        if (unBundle==null || unBundle.isEmpty()){
            actorid=270;
        }
        else{
            actorid=unBundle.getInt(ACTORID);
        }

        recyclerImagenes = (RecyclerView) view.findViewById(R.id.recyclerFotoActor);
        recyclerImagenes.setLayoutManager(new LinearLayoutManager(view.getContext(), LinearLayoutManager.HORIZONTAL,false));
        adapterImagenes = new AdapterImagenes();
        adapterImagenes.setContext(view.getContext());
        recyclerImagenes.setAdapter(adapterImagenes);
        adapterImagenes.setListaImagenesOriginales(new ArrayList<Imagen>());
        controllerFragmentActores.traerImagenesAdicionales(new ResultListener<List<Imagen>>() {
            @Override
            public void finish(List<Imagen> resultado) {
                adapterImagenes.setListaImagenesOriginales(resultado);
                adapterImagenes.notifyDataSetChanged();
            }
        },actorid);




        //Datos

        controllerFragmentActores.traerDetallesPersona(new ResultListener<Actor>() {
            @Override
            public void finish(Actor resultado) {
                nombreActor=resultado.getNombreActor();
                fechaNacimientoActor=resultado.getCumpleaños();
                biografiaActor=resultado.getBiografia();
                popularidadActor=resultado.getPopularidad();
                fotoActor=resultado.getFotoPerfilActor();
                cargarDatosActor();
                cargarDatosCreditos();
            }
        },actorid);

        Typeface roboto = Typeface.createFromAsset(getContext().getAssets(), "fonts/Roboto-Light.ttf");

        textonombre.setTypeface(roboto);
        textoaño.setTypeface(roboto);
        textosinopsis.setTypeface(roboto);


        //RECYCLER CREDITOS
        recyclerCreditos =(RecyclerView)view.findViewById(R.id.recycler_participacion);
        recyclerCreditos.setLayoutManager(new LinearLayoutManager(view.getContext(), LinearLayoutManager.HORIZONTAL, false));
        adapterCreditos= new AdapterCreditos();
        adapterCreditos.setContext(view.getContext());
        adapterCreditos.setListaCreditosOriginales(new ArrayList<Credito>());
        recyclerCreditos.setAdapter(adapterCreditos);



        //listener clickeo peliculas creditos
        View.OnClickListener listenerActore= new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer posicion = recyclerCreditos.getChildAdapterPosition(v);
                List <Credito> listaActoresOriginales = adapterCreditos.getListaCreditosOriginales();
                Credito creditoClickeado = listaActoresOriginales.get(posicion);
                Formato unFormato= new Formato();
                unFormato.setId(creditoClickeado.getId());
                if (creditoClickeado.getTitle()==null || creditoClickeado.getTitle().isEmpty()){
                    unFormato.setTipoFormato("series");
                }
                else{
                    unFormato.setTipoFormato("peliculas");
                }
                notificable.recibirFormatoClickeado(unFormato,"actores",1,"nulo",0);
            }
        };
        adapterCreditos.setListener(listenerActore);






        /*ImageButton imagebuttonFAV= (ImageButton) view.findViewById(R.id.boton_favo_grande);
        imagebuttonFAV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                favoritable= (FavoritableFav) v.getContext();

                favoritable.recibirFormatoFavorito(nombre);

            }
        });
        */
        return view;
    }

    public void cargarDatosActor(){
        textonombre.setText(nombreActor);
        textoaño.setVisibility(View.VISIBLE);
        textoaño.setText(fechaNacimientoActor);
        textosinopsis.setVisibility(View.VISIBLE);
        textosinopsis.setText(biografiaActor);
        Picasso.with(imageButton.getContext())
                .load(TMDBHelper.getBackDropPoster(TMDBHelper.IMAGE_SIZE_W342,fotoActor))
                .placeholder(R.drawable.loading2)
                .error(R.drawable.noimagedetalle)
                .into(imageButton);

    }

    public void cargarDatosCreditos(){
        controllerFragmentActores.traerCreditosPersona(new ResultListener<List<Credito>>() {
            @Override
            public void finish(List<Credito> resultado) {
                adapterCreditos.setListaCreditosOriginales(resultado);
                adapterCreditos.notifyDataSetChanged();
            }
        }, actorid);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.notificable=(Notificable)context;
    }
    public static FragmentActores fragmentActoresCreator(Integer unActorid){
        FragmentActores fragmentActores = new FragmentActores();
        Bundle unBundle= new Bundle();
        unBundle.putInt(ACTORID, unActorid);
        fragmentActores.setArguments(unBundle);
        return fragmentActores;
    }

}