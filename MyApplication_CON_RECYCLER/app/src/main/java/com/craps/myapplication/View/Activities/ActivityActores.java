package com.craps.myapplication.View.Activities;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.craps.myapplication.ControllerFormato.ControllerFormato;
import com.craps.myapplication.Model.Actor;
import com.craps.myapplication.Model.Formato;
import com.craps.myapplication.R;
import com.craps.myapplication.View.Fragments.FragmentActores;
import com.craps.myapplication.View.Fragments.FragmentDetalle;


public class ActivityActores extends AppCompatActivity implements ControllerFormato.Registrable,FragmentDetalle.Actorable, FragmentActores.Actorable, FragmentActores.Notificable {

    RecyclerView recyclerFormatosTrabajados;

    public static final String ACTORID="ACTORID";
    public static final String ACTORNAME="ACTORNAME";
    public static final String BIOGRAFIA="BIOGRAFIA";
    public static final String LUGARNACIMIENTO="LUGARNACIMIENTO";
    public static final String FECHANACIMIENTO="FECHANACIMIENTO";

    private Integer actorId;
    private String actorName;
    private String biografia;
    private String lugarNacimiento;
    private String fechaNacimiento;

    @Override
    public void recibirActorClickeado(Actor unActor) {

        cargarFragmentActores(unActor);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actores);
        Intent unIntent = getIntent();
        Bundle unBundle= unIntent.getExtras();
        actorId =unBundle.getInt(ACTORID);



        Actor unActor=new Actor();
        unActor.setId(actorId);


        cargarFragmentActores(unActor);



    }

    public void cargarFragmentActores(Actor unActor ){
        FragmentActores fragmentActores = new FragmentActores();
        Bundle bundle = new Bundle();
        bundle.putInt(ActivityActores.ACTORID, unActor.getId());
        fragmentActores.setArguments(bundle);
        FragmentManager fragmentManager= getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.detalle_contenedor_fragment, fragmentActores);
        fragmentTransaction.commit();
    }

    @Override
    public void recibirFormatoClickeado(Formato formato, String origen, Integer pagina, String StringABuscar, Integer drawerId) {

    }

    @Override
    public void solicitarRegistro() {

    }
}
