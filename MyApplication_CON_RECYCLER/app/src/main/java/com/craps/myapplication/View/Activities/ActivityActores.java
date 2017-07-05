package com.craps.myapplication.View.Activities;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.craps.myapplication.ControllerFormato.ControllerFormato;
import com.craps.myapplication.Model.Actor;
import com.craps.myapplication.Model.Formato;
import com.craps.myapplication.R;
import com.craps.myapplication.Utils.ResultListener;
import com.craps.myapplication.View.Adapters.AdapterPagerActores;
import com.craps.myapplication.View.Adapters.AdapterPagerFormato;
import com.craps.myapplication.View.Fragments.FragmentActores;
import com.craps.myapplication.View.Fragments.FragmentDetalle;

import java.util.List;

public class ActivityActores extends AppCompatActivity implements ControllerFormato.Registrable,FragmentDetalle.Actorable,FragmentActores.Notificable {

    private RecyclerView recyclerFormatosTrabajados;

    public static final String ACTORID="ACTORID";
    public static final String ACTORNAME="ACTORNAME";
    public static final String BIOGRAFIA="BIOGRAFIA";
    public static final String LUGARNACIMIENTO="LUGARNACIMIENTO";
    public static final String FECHANACIMIENTO="FECHANACIMIENTO";
    public static final String FORMATOID="FORMATOID";
    public static final String QUEFORMATO="QUEFORMATO";

    private Integer actorId;
    private Integer formatoOrigenId;
    private String queFormato;

    private String actorName;
    private String biografia;
    private String lugarNacimiento;
    private String fechaNacimiento;

    private List<Actor> listaActores;

    ViewPager viewPagerActores;

    AdapterPagerActores adapterPagerActores;

    ControllerFormato controllerActores;

    @Override
    public void recibirActorClickeado(Actor unActor, Integer formatoId, String unFormato) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actores);
        Intent unIntent = getIntent();
        Bundle unBundle= unIntent.getExtras();
        actorId =unBundle.getInt(ACTORID);
        formatoOrigenId =unBundle.getInt(FORMATOID);
        queFormato =unBundle.getString(QUEFORMATO);
        controllerActores=new ControllerFormato(this);


        Toolbar toolbar = (Toolbar) findViewById(R.id.ABappBar);
        setSupportActionBar(toolbar);
        // MUESTRO EL BOTON DE VOLVER ATRAS.
        ActionBar ab = getSupportActionBar();

        ab.setDisplayHomeAsUpEnabled(true);
        final Drawable upArrow =getResources().getDrawable(R.drawable.ic_arrow_back_black_24dp);
        upArrow.setColorFilter(getResources().getColor(R.color.marfil), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);






        Actor unActor=new Actor();
        viewPagerActores = (ViewPager) findViewById(R.id.viewPagerDetalle);
        //LE SETEO EL ADAPTER AL VIEW PAGER, EL ADAPTER UTILIZA EL FRAGMENT MANAGER PARA CARGAR FRAGMENT Y LA LISTA DE PELICULAS PARA CREAR LOS FRAGMENTS CORRESPONDIENTES
        ControllerFormato controllerActores= new ControllerFormato(this);
        unActor.setId(actorId);
        cargarFragmentActores(unActor, formatoOrigenId, queFormato);
    }

    public void cargarFragmentActores(final Actor unActor, Integer idFormato, String elFormato ){

        if (elFormato.equals("series")){
            controllerActores.obtenerActoresSerie(new ResultListener<List<Actor>>() {
                @Override
                public void finish(List<Actor> resultado) {
                    adapterPagerActores = new AdapterPagerActores(getSupportFragmentManager());
                    viewPagerActores.setAdapter(adapterPagerActores);
                    listaActores=resultado;

                    adapterPagerActores.setListaFormatos(resultado);
                    adapterPagerActores.notifyDataSetChanged();

                    for (Actor elActor:resultado
                         ) {
                        if (elActor.getId().equals(unActor.getId())){
                            viewPagerActores.setCurrentItem(listaActores.indexOf(elActor));
                        }
                    }

                }
            }, idFormato);
        }
        else{
            controllerActores.obtenerActoresPelicula(new ResultListener<List<Actor>>() {
                @Override
                public void finish(List<Actor> resultado) {
                    adapterPagerActores = new AdapterPagerActores(getSupportFragmentManager());
                    viewPagerActores.setAdapter(adapterPagerActores);
                    listaActores=resultado;

                    adapterPagerActores.setListaFormatos(resultado);
                    adapterPagerActores.notifyDataSetChanged();

                    for (Actor elActor:resultado
                            ) {
                        if (elActor.getId().equals(unActor.getId())){
                            viewPagerActores.setCurrentItem(listaActores.indexOf(elActor));
                        }
                    }
                }
            }, idFormato);
        }

    }

    @Override
    public void recibirFormatoClickeado(Formato formato, String origen, Integer pagina, String StringABuscar, Integer drawerId) {
    }

    @Override
    public void solicitarRegistro() {
    }


    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
