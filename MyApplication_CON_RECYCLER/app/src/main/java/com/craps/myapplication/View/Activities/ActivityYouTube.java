package com.craps.myapplication.View.Activities;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.craps.myapplication.ControllerFormato.ControllerFormato;
import com.craps.myapplication.Model.Trailer;
import com.craps.myapplication.R;
import com.craps.myapplication.Utils.ResultListener;
import com.craps.myapplication.Utils.TMDBHelper;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerFragment;

import java.util.List;

import static com.craps.myapplication.Utils.TMDBHelper.DEVELOPER_KEY;

public class ActivityYouTube extends AppCompatActivity implements ControllerFormato.Registrable {

    public static final String DEVELOPERKEY = "devKey";
    public static final String  ID = "id";
    public static final String FORMATOAMOSTRAR = "formatoAMostrar";


    private YouTubePlayer youTubePlayerP;
    private ControllerFormato controllerTrailers;

    public Integer id1;
    public String devKey;
    public String formatoAMostrar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youtube);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        devKey = bundle.getString(DEVELOPERKEY);
        id1 = bundle.getInt(ID);


        final String formatoAMostrar = bundle.getString(FORMATOAMOSTRAR);

        final Integer id = id1;

        controllerTrailers=new ControllerFormato(this);





        YouTubePlayerFragment mYouTubeFragment = YouTubePlayerFragment.newInstance();


        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.youtubeplayercontainer, mYouTubeFragment).commit();

        mYouTubeFragment.initialize(devKey, new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean wasRestored) {
                if (!wasRestored) {
                    youTubePlayer.setPlayerStyle(YouTubePlayer.PlayerStyle.DEFAULT);
                    youTubePlayerP = youTubePlayer;

                    controllerTrailers.traerTrailers(new ResultListener<List<Trailer>>() {
                        @Override
                        public void finish(List<Trailer> resultado) {
                            youTubePlayerP.loadVideo(resultado.get(0).getClaveVideoYouTube());
                            youTubePlayerP.setFullscreen(true);
                            youTubePlayerP.play();

                        }
                    }, id, formatoAMostrar);


                }
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult errorReason) {

                String errorMessage = errorReason.toString();
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
    }

    @Override
    public void solicitarRegistro() {

    }
}
