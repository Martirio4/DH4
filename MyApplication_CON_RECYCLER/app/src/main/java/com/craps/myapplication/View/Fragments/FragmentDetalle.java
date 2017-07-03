package com.craps.myapplication.View.Fragments;


import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
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
import com.craps.myapplication.View.Activities.ActivityMain;
import com.craps.myapplication.View.Activities.ActivityPoster;
import com.craps.myapplication.View.Adapters.AdapterActores;
import com.craps.myapplication.View.Adapters.AdapterFormato;
import com.facebook.AccessToken;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareButton;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;
import com.squareup.picasso.Picasso;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.tweetcomposer.ComposerActivity;

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
    private FloatingActionButton deleteFav;
    private FloatingActionButton watchTrailer;
    private FloatingActionButton compartir;

    private FavoritableFav favoritable;

    private RecyclerView recyclerActores;
    private AdapterActores adapterActores;

    private RecyclerView recyclerSimilares;
    private AdapterFormato adapterSimilares;
    private LinearLayoutManager layoutManagerDetalle;

    private ControllerFormato controllerFragmentDetalle;
    private Integer cantActores;

    public FragmentDetalle() {
        // Required empty public constructor
    }
    private Notificable notificable;
    private Actorable actorable;

    private Boolean isLoading=false;

    protected Integer id;
    protected String nombre;
    protected String title;
    protected Float calificacion;
    protected String releaseDate;
    protected String firstAirDate;
    protected String sinopsis;
    protected String backdrop;
    protected String posterId;
    private String formatoAMostrar;
    private YouTubePlayer youTubePlayerP;
    private Boolean existeEnFavoritos;
    private String formatoAMostrarCompartir;
    private String nombreAMostrarCompartir;
    private ShareButton compartirFB;





    //DECLARO INTERFAZ
    public interface Notificable {
        public void recibirFormatoClickeado(Formato formato,String origen, Integer pagina, String StringABuscar, Integer drawerId);
    }
    public interface Actorable{
        public void recibirActorClickeado(Actor unActor, Integer idFormato, String queFormato);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Twitter.initialize(this.getActivity());
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.onclick_detalle, container, false);

        YouTubePlayerSupportFragment mYouTubeFragment = YouTubePlayerSupportFragment.newInstance();


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


        if (title==null ||title.isEmpty()){
            formatoAMostrar="series";
            formatoAMostrarCompartir ="serie";
            nombreAMostrarCompartir =nombre;
        }
        else
        {
            formatoAMostrar="peliculas";
            formatoAMostrarCompartir ="pelicula";
            nombreAMostrarCompartir =title;
        }

        //RECYCLER ACTORES
        recyclerActores=(RecyclerView)view.findViewById(R.id.recycler_actores);
        recyclerActores.setLayoutManager(new LinearLayoutManager(view.getContext(), LinearLayoutManager.HORIZONTAL, false));

        //SETEAR EL ADAPTER
        adapterActores= new AdapterActores();
        adapterActores.setContext(view.getContext());
        adapterActores.setListaActoresOriginales(new ArrayList<Actor>());
        recyclerActores.setAdapter(adapterActores);

        //LLAMAR AL CONTROLLER Y PEDIR DATOS
        controllerFragmentDetalle= new ControllerFormato(view.getContext());
        if (formatoAMostrar.equals("series")){
            controllerFragmentDetalle.obtenerActoresSerie(new ResultListener<List<Actor>>() {
                @Override
                public void finish(List<Actor> resultado) {
                    cantActores=0;
                    if (resultado.size()<cantActores){
                        cantActores=resultado.size();
                    }
                    else{
                        cantActores=5;
                    }
                    List<Actor> listaFiltrada=new ArrayList<Actor>();
                    cantActores=0;
                    if (resultado.size()<5){
                        cantActores=resultado.size();
                    }
                    else{
                        cantActores=5;
                    }
                    for (int i=0;i<cantActores;i++){
                        listaFiltrada.add(resultado.get(i));
                    }
                    adapterActores.setListaActoresOriginales(listaFiltrada);
                    adapterActores.notifyDataSetChanged();
                }
            }, id);
        }
        else{
            controllerFragmentDetalle.obtenerActoresPelicula(new ResultListener<List<Actor>>() {
                @Override
                public void finish(List<Actor> resultado) {
                    cantActores=0;
                    if (resultado.size()<cantActores){
                        cantActores=resultado.size();
                    }
                    else{
                        cantActores=5;
                    }
                    List<Actor> listaFiltrada=new ArrayList<Actor>();
                    cantActores=0;
                    if (resultado.size()<5){
                        cantActores=resultado.size();
                    }
                    else{
                        cantActores=5;
                    }
                    for (int i=0;i<cantActores;i++){
                        listaFiltrada.add(resultado.get(i));
                    }
                    adapterActores.setListaActoresOriginales(listaFiltrada);
                    adapterActores.notifyDataSetChanged();
                }
            }, id);
        }

        //LISTENER CLIC ACTORES
        View.OnClickListener listenerActore= new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer posicion = recyclerActores.getChildAdapterPosition(v);
                List < Actor > listaActoresOriginales = adapterActores.getListaActoresOriginales();
                Actor actorClickeado = listaActoresOriginales.get(posicion);
                actorable.recibirActorClickeado(actorClickeado, id, formatoAMostrar);
            }
        };
        adapterActores.setListener(listenerActore);

        //RECYCLER FORMATO SIMILARES
        recyclerSimilares=(RecyclerView) view.findViewById(R.id.recycler_Similares);
        layoutManagerDetalle= new LinearLayoutManager(view.getContext(),LinearLayoutManager.HORIZONTAL,false);
        recyclerSimilares.setLayoutManager(layoutManagerDetalle);

        //SETEAR ADAPTER FORMATOS SIMILARES
        adapterSimilares= new AdapterFormato();
        adapterSimilares.setContext(view.getContext());
        adapterSimilares.setListaFormatosOriginales(new ArrayList<Formato>());
        recyclerSimilares.setAdapter(adapterSimilares);
        pedirPaginaSimilares();

        //AGREGO LISTENER DE CLICKEO DE PELICULAS
        View.OnClickListener listenerDetalle = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //ESTO SE UTILIZA PARA OBTENER LA POSITION DE LO QUE FUE CLICKEADO.
                Integer posicion = recyclerSimilares.getChildAdapterPosition(view);
                Integer numeroPagina= (int) Math.ceil((posicion+1)/20.0);
                List < Formato > listaPeliculasOriginales = adapterSimilares.getListaFormatosOriginales();
                Formato formatoClickeado = listaPeliculasOriginales.get(posicion);
                Integer pagina=controllerFragmentDetalle.getNumeroPagina();
                notificable.recibirFormatoClickeado(formatoClickeado, "self",pagina,"nulo",0);
            }
        };
       adapterSimilares.setListener(listenerDetalle);

        //SCROLL LISTENER FORMATOS SIMILARES
        recyclerSimilares.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                Integer ultimaPosicionVisible=layoutManagerDetalle.findLastVisibleItemPosition();
                Integer cantidadItems=layoutManagerDetalle.getItemCount();
                if (!isLoading){
                    if (ultimaPosicionVisible>= cantidadItems-2){
                        pedirPaginaSimilares();
                    }
                }
            }
        });

        //CON LOS DATOS CASTEO LOS OBJETOS Y PONGO VALORES

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

        if (formatoAMostrar.equals("series")){
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

        //CODIGO YOUTUBE
                /*
               android.support.v4.app.FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
                transaction.replace(R.id.youtubeplayercontainer,mYouTubeFragment).commit();
                mYouTubeFragment.initialize(DEVELOPER_KEY, new YouTubePlayer.OnInitializedListener() {
                    @Override
                    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean wasRestored) {
                        if(!wasRestored){
                            youTubePlayer.setPlayerStyle(YouTubePlayer.PlayerStyle.DEFAULT);
                            youTubePlayerP=youTubePlayer;

                            controllerFragmentDetalle.traerTrailers(new ResultListener<List<Trailer>>() {
                                @Override
                                public void finish(List<Trailer> resultado) {
                                    youTubePlayerP.loadVideo(resultado.get(0).getClaveVideoYouTube());
                                    youTubePlayerP.play();

                                }
                            },id, formatoAMostrar);


                        }
                    }

                    @Override
                    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult errorReason) {

                        String errorMessage = errorReason.toString();
                        Toast.makeText(getActivity(), errorMessage, Toast.LENGTH_LONG).show();
                        Log.d("errorMessage:", errorMessage);


        //                if(errorReason.isUserRecoverableError()){
        //            errorReason.getErrorDialog(ActivitySegunda.thi,RECOVERY_DIALOG_REQUEST).show();
        //        }else{
        //            String errorMessage = String.format(
        //                    "There was an error initializing the Youtube Player (%1$s)",
        //                    errorReason.toString());
        //            Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show();

                    }

            });
            */


        //ARMO LOS FLOATING ACTION BUTTON
        menuDetalle = (FloatingActionMenu) view.findViewById(R.id.menu);
        compartirFB = (ShareButton) view.findViewById(R.id.botonFB) ;

        ShareLinkContent content = new ShareLinkContent.Builder()
            .setContentUrl(Uri.parse(TMDBHelper.getBackDropPoster(TMDBHelper.IMAGE_SIZE_W342,posterId)))
                .setQuote("Les recomiendo esta "+ formatoAMostrarCompartir +" que encontre en ReelShot: "+"\n"+ nombreAMostrarCompartir )
            .build();

        //Asigno el content al share button
        compartirFB.setShareContent(content);




        /*
        addToFavorite = (FloatingActionButton)view.findViewById(R.id.addToFavorite);
        watchTrailer = (FloatingActionButton) view.findViewById(R.id.watchTrailer);
        */

        agregarCompartir();
        controllerFragmentDetalle.existeUnFavoritoDeterminado(new ResultListener<Boolean>() {
            @Override
            public void finish(Boolean resultado) {
                existeEnFavoritos=resultado;
            }
        }, id);

        if(!ActivityMain.login || !existeEnFavoritos){
            fabAgregarFavoritoTrailer();
        }
        else{
            fabQuitarFavoritoTrailer();
        }


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

    public void pedirPaginaSimilares(){
        if (controllerFragmentDetalle.isPageAvailable()) {
            isLoading = true;
            if (formatoAMostrar.equals("peliculas")) {
                controllerFragmentDetalle.obtenerPeliculasRelacionadas(new ResultListener<List<Formato>>() {
                    @Override
                    public void finish(List<Formato> resultado) {
                        adapterSimilares.addListaFormatosOriginales(resultado);
                        adapterSimilares.notifyDataSetChanged();
                        isLoading = false;
                    }
                },id);
            } else {
                controllerFragmentDetalle.obtenerSeriesRelacionadas(new ResultListener<List<Formato>>() {
                    @Override
                    public void finish(List<Formato> resultado) {
                        adapterSimilares.addListaFormatosOriginales(resultado);
                        adapterSimilares.notifyDataSetChanged();
                        isLoading = false;
                    }
                },id);
            }
        }
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

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.notificable=(Notificable)context;
        this.actorable=(Actorable)context;
    }


    public void fabAgregarFavoritoTrailer(){
        addToFavorite = new FloatingActionButton(getActivity());

        addToFavorite.setButtonSize(FloatingActionButton.SIZE_MINI);
        addToFavorite.setLabelText(getString(R.string.add_to_favorite));
        addToFavorite.setImageResource(R.drawable.ic_favorite_black_24dp);
        menuDetalle.addMenuButton(addToFavorite);

        addToFavorite.setLabelColors(ContextCompat.getColor(getActivity(), R.color.color5),
                ContextCompat.getColor(getActivity(), R.color.light_grey),
                ContextCompat.getColor(getActivity(), R.color.white_transparent));
        addToFavorite.setLabelTextColor(ContextCompat.getColor(getActivity(), R.color.black));

        addToFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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

        watchTrailer = new FloatingActionButton(getActivity());
        watchTrailer.setButtonSize(FloatingActionButton.SIZE_MINI);
        watchTrailer.setLabelText(getString(R.string.watch_trailer));
        watchTrailer.setImageResource(R.drawable.ic_camera_roll_black_24dp);
        menuDetalle.addMenuButton(watchTrailer);

        watchTrailer.setLabelColors(ContextCompat.getColor(getActivity(), R.color.color5),
                ContextCompat.getColor(getActivity(), R.color.light_grey),
                ContextCompat.getColor(getActivity(), R.color.white_transparent));
        watchTrailer.setLabelTextColor(ContextCompat.getColor(getActivity(), R.color.black));

        watchTrailer.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick (View v){
                watchTrailer.setLabelColors(ContextCompat.getColor(getActivity(), R.color.color5),
                        ContextCompat.getColor(getActivity(), R.color.light_grey),
                        ContextCompat.getColor(getActivity(), R.color.white_transparent));
                watchTrailer.setLabelTextColor(ContextCompat.getColor(getActivity(),R.color.black));


                Toast.makeText(v.getContext(), "Ver el trailer", Toast.LENGTH_SHORT).show();
            }
        });



    }

    public void fabQuitarFavoritoTrailer(){
        deleteFav = new FloatingActionButton(getActivity());
        deleteFav.setButtonSize(FloatingActionButton.SIZE_MINI);
        deleteFav.setLabelText(getString(R.string.remove_favorite));
        deleteFav.setImageResource(R.drawable.ic_delete_black_24dp);
        menuDetalle.addMenuButton(deleteFav);

        deleteFav.setLabelColors(ContextCompat.getColor(getActivity(), R.color.color5),
                ContextCompat.getColor(getActivity(), R.color.light_grey),
                ContextCompat.getColor(getActivity(), R.color.white_transparent));
        deleteFav.setLabelTextColor(ContextCompat.getColor(getActivity(), R.color.black));

        deleteFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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


        watchTrailer = new FloatingActionButton(getActivity());
        watchTrailer.setButtonSize(FloatingActionButton.SIZE_MINI);
        watchTrailer.setLabelText(getString(R.string.watch_trailer));
        watchTrailer.setImageResource(R.drawable.ic_camera_roll_black_24dp);
        menuDetalle.addMenuButton(watchTrailer);

        watchTrailer.setLabelColors(ContextCompat.getColor(getActivity(), R.color.color5),
                ContextCompat.getColor(getActivity(), R.color.light_grey),
                ContextCompat.getColor(getActivity(), R.color.white_transparent));
        watchTrailer.setLabelTextColor(ContextCompat.getColor(getActivity(), R.color.black));

        watchTrailer.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick (View v){
                watchTrailer.setLabelColors(ContextCompat.getColor(getActivity(), R.color.color5),
                        ContextCompat.getColor(getActivity(), R.color.light_grey),
                        ContextCompat.getColor(getActivity(), R.color.white_transparent));
                watchTrailer.setLabelTextColor(ContextCompat.getColor(getActivity(),R.color.black));


                Toast.makeText(v.getContext(), "Ver el trailer", Toast.LENGTH_SHORT).show();
            }
        });


    }

    public void agregarCompartir() {

        if (estaLogueadoAFacebook()) {

            compartir = new FloatingActionButton(getActivity());

            compartir.setButtonSize(FloatingActionButton.SIZE_MINI);
            compartir.setLabelText(getString(R.string.share_Facebook));
            compartir.setImageResource(R.drawable.ic_share_black_24dp);
            menuDetalle.addMenuButton(compartir);

            compartir.setLabelColors(ContextCompat.getColor(getActivity(), R.color.color5),
                    ContextCompat.getColor(getActivity(), R.color.light_grey),
                    ContextCompat.getColor(getActivity(), R.color.white_transparent));
            compartir.setLabelTextColor(ContextCompat.getColor(getActivity(), R.color.black));

            compartir.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    compartirFB.performClick();
                }
            });


        }
        if (estaLogueadoATwitter()){

            compartir = new FloatingActionButton(getActivity());

            compartir.setButtonSize(FloatingActionButton.SIZE_MINI);
            compartir.setLabelText(getString(R.string.share_Twitter));
            compartir.setImageResource(R.drawable.ic_share_black_24dp);
            menuDetalle.addMenuButton(compartir);

            compartir.setLabelColors(ContextCompat.getColor(getActivity(), R.color.color5),
                    ContextCompat.getColor(getActivity(), R.color.light_grey),
                    ContextCompat.getColor(getActivity(), R.color.white_transparent));
            compartir.setLabelTextColor(ContextCompat.getColor(getActivity(), R.color.black));

            compartir.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    composeTweet(v);
                }
            });
        }
    }



    public void composeTweet(View view){
        final TwitterSession session = TwitterCore.getInstance().getSessionManager().getActiveSession();
                final Intent intent = new ComposerActivity.Builder(this.getActivity())
                .session(session)
                .text("Les recomiendo esta "+ formatoAMostrarCompartir +" que encontre en ReelShot: "+"\n"+"\n"+nombreAMostrarCompartir +"\n"+"\n")
                .hashtags("#Maraton #ReelShot")
                .createIntent();
        startActivity(intent);
    }

    public Boolean estaLogueadoAFacebook() {
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        return accessToken != null;
    }

    public Boolean estaLogueadoATwitter() {
        TwitterSession session = TwitterCore.getInstance().getSessionManager().getActiveSession();
        return  (session != null);
    }









}