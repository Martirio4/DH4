package com.craps.myapplication.View.Fragments;


import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.craps.myapplication.ControllerFormato.ControllerFormato;
import com.craps.myapplication.Model.Actor;
import com.craps.myapplication.Model.Formato;
import com.craps.myapplication.R;
import com.craps.myapplication.Utils.ResultListener;
import com.craps.myapplication.Utils.TMDBHelper;
import com.craps.myapplication.View.Activities.ActivityPoster;
import com.craps.myapplication.View.Adapters.AdapterActores;
import com.craps.myapplication.View.Adapters.AdapterFormato;
import com.squareup.picasso.Picasso;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import java.util.ArrayList;
import java.util.List;

/**
 *
 *
 * A simple {@link Fragment} subclass.
 */
public class FragmentDetalle extends Fragment {
    public static final String TITLE="TITLE";
    public static final String NAME="NAME";
    public static final String ID="id";
    public static final String BACKDROP="BACKDROP";
    public static final String SINOPSIS="SINOPSIS";
    public static final String CALIFICACION="CALIFICACION";
    public static final String POSTERID="POSTERID";
    public static final String FIRSTAIRDATE="FIRSTAIRDATE";
    public static final String RELEASEDATE="RELEASEDATE";

    //Creo el objeto que abre el Clan del FAB
    private FloatingActionMenu menuDetalle;
    // Creo cada elemento dentro del Clan
    private FloatingActionButton addToFavorite;
    private FloatingActionButton watchTrailer;
    private FavoritableFav favoritable;

    private RecyclerView recyclerActores;
    private AdapterActores adapterActores;

    private RecyclerView recyclerSimilares;
    private AdapterFormato adapterSimilares;

    public FragmentDetalle() {
        // Required empty public constructor
    }

    protected Integer id;
    protected String nombre;
    protected String title;
    protected Float calificacion;
    protected String releaseDate;
    protected String firstAirDate;
    protected String sinopsis;
    protected String backdrop;
    protected String posterId;

    private String urlString;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.onclick_detalle, container, false);

//    RECIBO EL BUNDLE Y SACVO LOS DATOS, LOS PONGO EN LOS TEXTVIEWS
        Bundle unBundle= getArguments();
        id=unBundle.getInt(ID);
        nombre=unBundle.getString(NAME);
        title=unBundle.getString(TITLE);
        calificacion=unBundle.getFloat(CALIFICACION);
        releaseDate=unBundle.getString(RELEASEDATE);
        firstAirDate=unBundle.getString(FIRSTAIRDATE);
        sinopsis=unBundle.getString(SINOPSIS);
        backdrop=unBundle.getString(BACKDROP);
        posterId=unBundle.getString(POSTERID);





        //recycler actores
        recyclerActores=(RecyclerView)view.findViewById(R.id.recycler_actores);
        recyclerActores.setLayoutManager(new LinearLayoutManager(view.getContext(), LinearLayoutManager.VERTICAL, false));

        adapterActores= new AdapterActores();
        adapterActores.setContext(view.getContext());
        adapterActores.setListaActoresOriginales(new ArrayList());

        recyclerActores.setAdapter(adapterActores);
        urlString=TMDBHelper.getCastMovie(id, TMDBHelper.language_SPANISH);

        ControllerFormato controllerFormato= new ControllerFormato(view.getContext());
        controllerFormato.obtenerActores(new ResultListener<List<Actor>>() {
            @Override
            public void finish(List<Actor> resultado) {
              adapterActores.setListaActoresOriginales(resultado);
                adapterActores.notifyDataSetChanged();
            }
        }, urlString);




        TextView textonombre=(TextView)view.findViewById(R.id.tag_nombre2);
        TextView textoaño=(TextView)view.findViewById(R.id.tag_año2);
        TextView textosinopsis=(TextView)view.findViewById(R.id.tag_sinopsis2);
        TextView textCalificacion = (TextView) view.findViewById(R.id.textViewrating);
        Typeface roboto = Typeface.createFromAsset(getContext().getAssets(), "fonts/Roboto-Light.ttf");
        ImageButton imageButton=(ImageButton) view.findViewById(R.id.detalle_img);

        Picasso.with(imageButton.getContext())
                .load(TMDBHelper.getBackDropPoster(TMDBHelper.IMAGE_SIZE_W342,backdrop))
                .placeholder(R.drawable.loading2)
                .error(R.drawable.noimagedetalle)
                .into(imageButton);


        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ActivityPoster.class);
                Bundle bundle= new Bundle();
                bundle.putString(ActivityPoster.POSTERID, posterId);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

       /* FloatingActionButton floatingActionButton=(FloatingActionButton) view.findViewById(R.id.fab_trailer);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "Ver el trailer", Toast.LENGTH_SHORT).show();
            }
        });
        */

        if (title==null||title.isEmpty()){
            textonombre.setText(nombre);
            textoaño.setText(firstAirDate);
        }
        else{
            textonombre.setText(title);
            textoaño.setText(releaseDate);
        }
        textosinopsis.setText(sinopsis);
        textCalificacion.setText(calificacion.toString());

        textonombre.setTypeface(roboto);
        textoaño.setTypeface(roboto);
        textosinopsis.setTypeface(roboto);
        textCalificacion.setTypeface(roboto);

        menuDetalle = (FloatingActionMenu) view.findViewById(R.id.menu);
        /*
        addToFavorite = (FloatingActionButton)view.findViewById(R.id.addToFavorite);
        watchTrailer = (FloatingActionButton) view.findViewById(R.id.watchTrailer);
        */


        final FloatingActionButton addToFavorite = new FloatingActionButton(getActivity());
        final FloatingActionButton watchTrailer = new FloatingActionButton(getActivity());
        final FloatingActionButton deleteFav = new FloatingActionButton(getActivity());

        deleteFav.setButtonSize(FloatingActionButton.SIZE_MINI);
        deleteFav.setLabelText("Quitar de favoritos");
        deleteFav.setImageResource(R.drawable.ic_delete_black_24dp);

        addToFavorite.setButtonSize(FloatingActionButton.SIZE_MINI);
        addToFavorite.setLabelText(getString(R.string.add_to_favorite));
        addToFavorite.setImageResource(R.drawable.ic_favorite_black_24dp);

        watchTrailer.setButtonSize(FloatingActionButton.SIZE_MINI);
        watchTrailer.setLabelText(getString(R.string.watch_trailer));
        watchTrailer.setImageResource(R.drawable.ic_camera_roll_black_24dp);

        menuDetalle.addMenuButton(deleteFav);
        deleteFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteFav.setLabelColors(ContextCompat.getColor(getActivity(), R.color.paleta4),
                        ContextCompat.getColor(getActivity(), R.color.light_grey),
                        ContextCompat.getColor(getActivity(), R.color.white_transparent));
                deleteFav.setLabelTextColor(ContextCompat.getColor(getActivity(), R.color.black));

                favoritable= (FavoritableFav) v.getContext();

                Formato unFormato = new Formato();

                if (title==null||title.isEmpty()){
                    unFormato.setName(nombre);
                    unFormato.setFirst_air_date(firstAirDate);
                }
                else{
                    unFormato.setTitle(title);
                    unFormato.setRelease_date(releaseDate);
                }
                unFormato.setId(id);
                unFormato.setOverview(sinopsis);
                unFormato.setVote_average(calificacion);
                unFormato.setBackdrop_path(backdrop);
                unFormato.setPoster_path(posterId);


                favoritable.eliminarFormatoFavorito(unFormato);
            }
        });

        menuDetalle.addMenuButton(addToFavorite);
        addToFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addToFavorite.setLabelColors(ContextCompat.getColor(getActivity(), R.color.paleta4),
                        ContextCompat.getColor(getActivity(), R.color.light_grey),
                        ContextCompat.getColor(getActivity(), R.color.white_transparent));
                addToFavorite.setLabelTextColor(ContextCompat.getColor(getActivity(), R.color.black));

                favoritable= (FavoritableFav) v.getContext();
                Formato unFormato = new Formato();

                if (title==null||title.isEmpty()){
                    unFormato.setName(nombre);
                    unFormato.setFirst_air_date(firstAirDate);
                }
                else{
                    unFormato.setTitle(title);
                    unFormato.setRelease_date(releaseDate);
                }
                unFormato.setId(id);
                unFormato.setOverview(sinopsis);
                unFormato.setVote_average(calificacion);
                unFormato.setBackdrop_path(backdrop);
                unFormato.setPoster_path(posterId);


                favoritable.recibirFormatoFavorito(unFormato);

            }
        });

        menuDetalle.addMenuButton(watchTrailer);
        watchTrailer.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick (View v){
                watchTrailer.setLabelColors(ContextCompat.getColor(getActivity(), R.color.paleta4),
                        ContextCompat.getColor(getActivity(), R.color.light_grey),
                        ContextCompat.getColor(getActivity(), R.color.white_transparent));
                watchTrailer.setLabelTextColor(ContextCompat.getColor(getActivity(),R.color.black));


                        Toast.makeText(v.getContext(), "Ver el trailer", Toast.LENGTH_SHORT).show();
            }
        });




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

    public interface FavoritableFav{
        ///corregir aca, usar un id de pelucula
       public void recibirFormatoFavorito(Formato unFormato);
        public void eliminarFormatoFavorito(Formato unFormato);
    }

    public static FragmentDetalle fragmentDetalleCreator(Formato unFormato) {
        FragmentDetalle detalleFragment = new FragmentDetalle();
        Bundle unBundle = new Bundle();

        if (unFormato.getTitle()==null||unFormato.getTitle().isEmpty()){
            unBundle.putString(NAME, unFormato.getName());
            unBundle.putString(FIRSTAIRDATE, unFormato.getFirst_air_date());
        }
        else{
            unBundle.putString(TITLE, unFormato.getTitle());
            unBundle.putString(RELEASEDATE, unFormato.getRelease_date());
        }
        unBundle.putString(SINOPSIS, unFormato.getOverview());
        unBundle.putString(BACKDROP, unFormato.getBackdrop_path());
        unBundle.putString(POSTERID, unFormato.getPoster_path());
        unBundle.putFloat(CALIFICACION, unFormato.getVote_average());
        unBundle.putInt(ID, unFormato.getId());

        detalleFragment.setArguments(unBundle);
        return detalleFragment;
    }


}