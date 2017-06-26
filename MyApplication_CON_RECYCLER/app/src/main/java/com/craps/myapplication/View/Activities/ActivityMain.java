package com.craps.myapplication.View.Activities;




import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.craps.myapplication.ControllerFormato.ControllerFormato;
import com.craps.myapplication.Model.Formato;
import com.craps.myapplication.R;
import com.craps.myapplication.Utils.HTTPConnectionManager;
import com.craps.myapplication.Utils.TMDBHelper;
import com.craps.myapplication.View.Adapters.AdapterFormato;
import com.craps.myapplication.View.Adapters.AdapterPagerMaestro;
import com.craps.myapplication.View.Fragments.FragmentAboutUs;
import com.craps.myapplication.View.Fragments.FragmentBusqueda;
import com.craps.myapplication.View.Fragments.FragmentFavoritos;
import com.craps.myapplication.View.Fragments.FragmentMain;
import com.craps.myapplication.View.Fragments.FragmentSinConexion;

import java.util.List;

public class ActivityMain extends AppCompatActivity implements ControllerFormato.Registrable ,FragmentMain.Notificable,FragmentSinConexion.Notificable, FragmentFavoritos.Notificable{

    private FloatingActionButton floatingActionButton;
    private List<String> listaFragmentsMaestros;
    private String queMostrar = "peliculas";
    private TabLayout tabLayout;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private EditText editBusqueda;
    private ControllerFormato controllerFormato;
    //isLoading Paginacion
    private Boolean isLoading;

    //PABLO 1/4A (VER LAYOUT HEADER Y ACTIVITY MAIN)
    public static final String USUARIO = "usuario";
    public static final String TEXTOLOGIN = "textoLogin";
    public static final String IMAGENUSUARIO = "imagenUsuario";
    public static final String LOGIN = "login";
    private String textoLogin = "LOGIN";
    private Integer imagenUsuario = R.drawable.icono;

    //Variables de incio
    public static String usuario = null;
    public static Boolean login = false;
    public static String idiomaDeLaSesion =TMDBHelper.language_SPANISH;


    //PABLO 1/4C




    //Getter para los filtros iniciales
    public void setQueMostrar(String queMostrar) {
        this.queMostrar = queMostrar;
    }
    public String getQueMostrar() {
        return queMostrar;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        drawerLayout = (DrawerLayout) findViewById(R.id.elDrawer);
        navigationView = (NavigationView) findViewById(R.id.naview);

        // TRAIGO USUARIO.
        Intent unIntent = getIntent();
        Bundle unBundle = unIntent.getExtras();


        if(unBundle!=null) {
            if(unBundle.getString(USUARIO)!=null) {
                usuario = unBundle.getString(USUARIO);
                //usuario.setImagen(R.drawable.user);
                //imagenUsuario = usuario.getImagen();
                login=true;
                textoLogin = "LOGOUT";
            }
        }

        Typeface roboto = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Light.ttf");

        View headerLayout = navigationView.getHeaderView(0);
        Button botonLogin = (Button) headerLayout.findViewById(R.id.botonNavView);
        botonLogin.setText(textoLogin);
        TextView textView = (TextView) headerLayout.findViewById(R.id.textoMailUsuario);
        textView.setText(usuario);
        textView.setTypeface(roboto);




        //LE DIGO LA ACTION BAR QUE USE EL LAYOUT CUSTOM
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.action_bar_layout_busqueda);
        editBusqueda = (EditText) findViewById(R.id.edit_action_buscador);

        // CASTEO EDITTEXT

        editBusqueda.setTypeface(roboto);

        //CASTEO EL FAB Y AGREGO EL LISTENER
        floatingActionButton = (FloatingActionButton) findViewById(R.id.fab_action_buscador);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String editString=editBusqueda.getText().toString().toLowerCase();
                editString = editString.replaceAll(" ", "%20");

                if (HTTPConnectionManager.isNetworkingOnline(v.getContext())) {
                    if (editString == null || editString.isEmpty()) {
                    } else {
                        pedirListaBuscada(editString, null);
                    }

                } else {
                        cargarFragmentSinConexion();
                }
                hideSoftKeyboard();
            }
        });

        // LISTENER PARA EL NAVIGATION MENU
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                if (HTTPConnectionManager.isNetworkingOnline(navigationView.getContext())) {
                    if (item.getItemId()==R.id.aboutus){
                        cargarAboutUs();
                    }
                    else{
                        pedirListaBuscada(null,item.getItemId());
                    }

                }
                else {
                    cargarFragmentSinConexion();
                }
                drawerLayout.closeDrawers();
                return true;
            }
        });


        //SI HAY INET CORRE NORMAL EL VIEWPAGER, SINO CARGA UN FRAGMENT "SIN CONEXION"

        //BUSCO EL VIEW PAGER DE PELICULAS
        controllerFormato = new ControllerFormato(this);
        final ViewPager viewPager = (ViewPager) findViewById(R.id.viewPagerFragmentMaestro);

        //CASTEO LAS TAB
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }
        });

        //LISTA DE LOS FORMATOS A EXHIBIR
        listaFragmentsMaestros = controllerFormato.recibirListaFormatos();
        //LE SETEO EL ADAPTER AL VIEW PAGER, EL ADAPTER UTILIZA EL FRAGMENT MANAGER PARA CARGAR FRAGMENT Y LA LISTA DE PELICULAS PARA CREAR LOS FRAGMENTS CORRESPONDIENTES
        AdapterPagerMaestro adapterPagerMaestro = new AdapterPagerMaestro(getSupportFragmentManager(), listaFragmentsMaestros,this);
        adapterPagerMaestro.setContext(this);
        viewPager.setAdapter(adapterPagerMaestro);

    }


    //METODO PUBLICO ABRIR DETALLE CUANDO HAGO CLICK EN UNA PELI
    public void clickFormato(Formato formato, String origen, Integer numeroPagina) {
        Intent unIntent = new Intent(this, ActivitySegunda.class);
        Bundle unBundle = new Bundle();
        unBundle.putString(ActivitySegunda.ORIGEN, origen);
        unBundle.putInt(ActivitySegunda.IDFORMATO, formato.getId());
        unBundle.putInt(ActivitySegunda.PAGINA, numeroPagina);
        if (formato.getTitle()==null||formato.getTitle().isEmpty()){
            unBundle.putString(ActivitySegunda.TIPOFORMATO, "series");
        }
        else{
            unBundle.putString(ActivitySegunda.TIPOFORMATO, "peliculas");
        }
        unIntent.putExtras(unBundle);
        startActivity(unIntent);
    }
    public void cargarAboutUs() {
        FragmentAboutUs aboutusFragment = new FragmentAboutUs();
        android.support.v4.app.FragmentManager fragmentManager= getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.contenedor_fragment_maestro,aboutusFragment );
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
    public void pedirListaBuscada(String queBuscar, Integer drawerId){
        //CARGO EL FRAGMENT
        FragmentBusqueda fragment_busqueda = new FragmentBusqueda();
        Bundle otroBundle = new Bundle();
        otroBundle.putString(FragmentBusqueda.QUEBUSCO, queBuscar);
        otroBundle.putInt(FragmentBusqueda.DRAWERID, drawerId);


        //LE CARGO EL BUNDLE
        fragment_busqueda.setArguments(otroBundle);
        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.contenedor_buscador, fragment_busqueda,"FragmentBuscador");
        fragmentTransaction.addToBackStack(null);
        fragmentManager.popBackStackImmediate();
        fragmentTransaction.commit();
    }

    private void hideSoftKeyboard(){
        if(getCurrentFocus()!=null && getCurrentFocus() instanceof EditText){
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(editBusqueda.getWindowToken(), 0);
        }
    }
    /*
    public void cargarOpcionMenu(Integer id) {
        FragmentBusqueda fragmentBusqueda=new FragmentBusqueda();
        Bundle bundle= new Bundle();
        bundle.putInt(FragmentBusqueda.IDDRAWER, id);
        fragmentBusqueda.setArguments(bundle);

    }
*/
    // PABLO 4/4A
    public void botonNavViewApretado (View view) {

        if (login == true) {
            usuario = null;
            //usuario.setImagen(R.drawable.icono);
            //imagenUsuario = usuario.getImagen();
            login = false;
            textoLogin = "LOGIN";
        }
        finish();
        Intent unIntent = new Intent(this, ActivityLogin.class);
        startActivity(unIntent);
    }

    public void cargarFragmentSinConexion() {
        FragmentSinConexion fragmentSinConexion = new FragmentSinConexion();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.contenedor_buscador, fragmentSinConexion);
        fragmentTransaction.commit();
    }
/*
    @Override
    public void recibirFormatoFavorito(Formato unFormato) {
        controllerFormato= new ControllerFormato(this);
        controllerFormato.agregarFavorito(unFormato, usuario);
    }
    */




    @Override
    public void recibirFormatoClickeado(Formato formato,String origen, Integer pagina) {
        clickFormato(formato,origen,pagina);


    }



    /*
    @Override
    public void recibirFavoritoClickeado(Formato formato) {
    }
    */
    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(navigationView)) {
            drawerLayout.closeDrawers();
        }
        else {
            super.onBackPressed();
        }
    }


    /*@Override
    public void recibirFormatoClickeado(Formato formato, String url) {
        clickFormato(formato, url);

    }*/

    @Override
    public void recibirFormatoClickeado(Formato formato, String url) {

    }

    @Override
    public void recibirFavoritoClickeado(Formato formato) {

    }
    @Override
    public void solicitarRegistro() {
        Intent unIntent = new Intent(this, ActivityRegister.class);
        startActivity(unIntent);
    }

// GENERO EL METODO PARA CARGAR EL FRAGMENT LOGIN LUEGO DEL CLICK.


    // GENERO EL METODO PARA CARGAR EL FRAGMENT LOGOUT LUEGO DEL CLICK.


    // GENERO EL METODO PARA CARGAR EL FRAGMENT REGISTER 1 LUEGO DEL CLICK.
}
