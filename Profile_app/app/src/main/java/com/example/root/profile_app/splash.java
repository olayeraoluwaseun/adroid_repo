package com.example.root.profile_app;


import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;


public class splash extends ActionBarActivity {
//GoogleMap googleMap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);


        Thread myBackground = new Thread(){

            public void run()
            {

                Intent intent = new Intent(getBaseContext(), user_login_reg.class);
                startActivity(intent);

                finish();
            }
        };

        myBackground.start();

//        createMapView();
//        addMarker();


    }


//    /**
//     * Initialises the mapview
//     */
//    private void createMapView(){
//        /**
//         * Catch the null pointer exception that
//         * may be thrown when initialising the map
//         */
//        try {
//            if(null == googleMap){
//                googleMap = ((MapFragment) getFragmentManager().findFragmentById(
//                        R.id.mapView)).getMap();
//
//                /**
//                 * If the map is still null after attempted initialisation,
//                 * show an error to the user
//                 */
//                if(null == googleMap) {
//                    Toast.makeText(getApplicationContext(),
//                            "Error creating map", Toast.LENGTH_SHORT).show();
//                }
//            }
//        } catch (NullPointerException exception){
//            Log.e("mapApp", exception.toString());
//        }
//    }
//
//    /**
//     * Adds a marker to the map
//     */
//    private void addMarker(){
//
//
//        /** Make sure that the map has been initialised **/
//        if(null != googleMap){
//            googleMap.addMarker(new MarkerOptions()
//                            .position(new LatLng(0, 0))
//                            .title("Marker")
//                            .draggable(true)
//            );
//        }
//    }

}
