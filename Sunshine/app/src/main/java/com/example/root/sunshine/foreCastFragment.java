package com.example.root.sunshine;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.zip.Inflater;

/**
 * Created by root on 1/23/15.
 */
public  class foreCastFragment extends Fragment {
    private static ArrayAdapter myAdapter;
    private static ListView listView;


    public foreCastFragment() {
    }

    @Override
    public void onCreate(Bundle saveBundleInstanceBundle)
    {
        super.onCreate(saveBundleInstanceBundle);
        //add this line in order for this fragment to handle menu events
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Inflate the menu; this adds items to the action bar if it is present.
        inflater.inflate(R.menu.menu_main, menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        if (id == R.id.action_referesh)
        {
            FetchTask task = new FetchTask();
            task.execute("94043");
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);


        final String[] data =
                {
                        "Today  - sunny - 60/70 ",
                        "Tomorrow - cloudy - 70/80",
                        "Wed - sunny - 80/70",
                        "Thur - winter - 30/40",
                        "Fri - sunny - 20/30",
                        "Sat - rainy - 40/50",
                        "Sun - cloudy - 50/40"

                };

        //the list collects the data and stores in form of a list

        final List<String> myList = new ArrayList<String>(Arrays.asList(data));

        //the adapter takes the data and populate the listview

        myAdapter = new ArrayAdapter<String>(getActivity(), R.layout.list_item_sunshine, R.id.list_item_sunshine_textView,
                myList);


        //Get a reference from the listview and attach it to the adapter
        listView = (ListView) rootView.findViewById(R.id.list_item_data);

        listView.setAdapter(myAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?>adapterview, View view, int position, long l)
            {
                Toast.makeText(getActivity(), data[position], Toast.LENGTH_SHORT).show();

            }
        });


        return rootView;

    }

    public class FetchTask extends AsyncTask<String, Void, String[]>

    {
        private final String LOG_TAG  = FetchTask.class.getSimpleName();


        /* The date/time conversion code is going to be moved outside the asynctask later,
         * so for convenience we're breaking it out into its own method now.
         */
        private String getReadableDateString(long time){
            // Because the API returns a unix timestamp (measured in seconds),
            // it must be converted to milliseconds in order to be converted to valid date.
            Date date = new Date(time * 1000);
            SimpleDateFormat format = new SimpleDateFormat("E, MMM d");
            return format.format(date).toString();
        }

        /**
         * Prepare the weather high/lows for presentation.
         */
        private String formatHighLows(double high, double low) {
            // For presentation, assume the user doesn't care about tenths of a degree.
            long roundedHigh = Math.round(high);
            long roundedLow = Math.round(low);

            String highLowStr = roundedHigh + "/" + roundedLow;
            return highLowStr;
        }
        /**
         * Take the String representing the complete forecast in JSON Format and
         * pull out the data we need to construct the Strings needed for the wireframes.
         *
         * Fortunately parsing is easy:  constructor takes the JSON string and converts it
         * into an Object hierarchy for us.
         */
        private String[] getWeatherDataFromJson(String forecastJsonStr, int numDays)
                throws JSONException {

            // These are the names of the JSON objects that need to be extracted.
            final String OWM_LIST = "list";
            final String OWM_WEATHER = "weather";
            final String OWM_TEMPERATURE = "temp";
            final String OWM_MAX = "max";
            final String OWM_MIN = "min";
            final String OWM_DATETIME = "dt";
            final String OWM_DESCRIPTION = "main";

            JSONObject forecastJson = new JSONObject(forecastJsonStr);
            JSONArray weatherArray = forecastJson.getJSONArray(OWM_LIST);

            String[] resultStrs = new String[numDays];
            for(int i = 0; i < weatherArray.length(); i++) {
                // For now, using the format "Day, description, hi/low"
                String day;
                String description;
                String highAndLow;

                // Get the JSON object representing the day
                JSONObject dayForecast = weatherArray.getJSONObject(i);

                // The date/time is returned as a long.  We need to convert that
                // into something human-readable, since most people won't read "1400356800" as
                // "this saturday".
                long dateTime = dayForecast.getLong(OWM_DATETIME);
                day = getReadableDateString(dateTime);

                // description is in a child array called "weather", which is 1 element long.
                JSONObject weatherObject = dayForecast.getJSONArray(OWM_WEATHER).getJSONObject(0);
                description = weatherObject.getString(OWM_DESCRIPTION);

                // Temperatures are in a child object called "temp".  Try not to name variables
                // "temp" when working with temperature.  It confuses everybody.
                JSONObject temperatureObject = dayForecast.getJSONObject(OWM_TEMPERATURE);
                double high = temperatureObject.getDouble(OWM_MAX);
                double low = temperatureObject.getDouble(OWM_MIN);

                highAndLow = formatHighLows(high, low);
                resultStrs[i] = day + " - " + description + " - " + highAndLow;
            }

            return resultStrs;
        }

        @Override
        protected String[] doInBackground(String... params) {



            //if there's no zip code, there's nothing to look up. verify size

            if (params.length == 0)
            {
                return null;
            }

            //These two need to be declared outside the try/catch
            //so that they can be closed in the finally block.


            HttpURLConnection urlConnection = null;

            BufferedReader reader = null;

            //will contain the raw JSON response as a string
            String forecastJsonStr = null;

            String format = "json";
            String units = "metric";
            int numDays =7;


            try
            {
                //construct the url for the openWeatherMap query
                //possible parameters are available at OWM's forecast API page

                String stringUrl = "http://api.openweathermap.org/data/2.5/" +
                        "forecast/daily";
                final String FORECAST_BASE_URL =stringUrl;

                final String QUERY_PARAM =  "q";

                final String FORMAT_PARAM = "mode";

                final String UNITS_PARAM = "units";

                final String DAYS_PARAM ="cnt";

                Uri builtUri = Uri.parse(FORECAST_BASE_URL).buildUpon()
                        .appendQueryParameter(QUERY_PARAM, params[0])
                        .appendQueryParameter(FORMAT_PARAM, format)
                        .appendQueryParameter(UNITS_PARAM, units)
                        .appendQueryParameter(DAYS_PARAM, Integer.toString(numDays))
                        .build();

                URL url = new URL (builtUri.toString());

                Log.v(LOG_TAG, "Built Uri" + builtUri.toString());


                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                //Read the input stream into a String

                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();

                if (inputStream == null) {
                    //Nothing to do

                    forecastJsonStr = null;
                }

                reader = new BufferedReader(new InputStreamReader(inputStream));


                String line;

                while ((line = reader.readLine()) != null) {
                    //Since it's JSON, adding newline isnt necessary (it wont affect parsing)
                    //But it does make debugging a *lot* easier if you print out the the completed
                    //buffer for debugging

                    buffer.append(line + "\n");
                }

                if (buffer.length() == 0) {
                    //Stream was empty. No point in parsing

                    forecastJsonStr = null;
                }

                forecastJsonStr = buffer.toString();

                Log.v(LOG_TAG, "Forecast JSON String: " + forecastJsonStr);

            } catch (IOException e) {
                Log.e(LOG_TAG, "Error", e);

                //If the code didnt successfully get the weather data, there's no point
                //to parse it.

                forecastJsonStr = null;

            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }

                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e(LOG_TAG, "Error closing stream", e);
                    }
                }
            }



                try
                {
                    return getWeatherDataFromJson(forecastJsonStr, numDays);
                }
                catch (JSONException e)
                {
                    Log.e(LOG_TAG, e.getMessage(), e);
                    e.printStackTrace();
                }

            return null;

            /**
            //These two need to be declared outside the try/catch
            //so that they can be closed in the finally block.


            HttpURLConnection urlConnection = null;

            BufferedReader reader = null;

            //will contain the raw JSON response as a string

            String forecastJsonStr = null;

            try {
                //construct the URL for the OpenWeatherMap query
                //Possible parameters are available at OWM's forecast API page, at


                String stringUrl = "http://api.openweathermap.org/data/2.5/" +
                        "forecast/daily?q=94043&mode=json&units=metric&cn=7";

                URL url = new URL(stringUrl);

                //create the request to OpenWeatherMap, and open the connection

                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();


                //Read the input stream into a String

                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();

                if (inputStream == null) {
                    //Nothing to do

                    forecastJsonStr = null;
                }

                reader = new BufferedReader(new InputStreamReader(inputStream));


                String line;

                while ((line = reader.readLine()) != null) {
                    //Since it's JSON, adding newline isnt necessary (it wont affect parsing)
                    //But it does make debugging a *lot* easier if you print out the the completed
                    //buffer for debugging

                    buffer.append(line + "\n");
                }

                if (buffer.length() == 0) {
                    //Stream was empty. No point in parsing

                    forecastJsonStr = null;
                }

                forecastJsonStr = buffer.toString();

                Log.v(LOG_TAG, "Forecast JSON String: " + forecastJsonStr);

            } catch (IOException e) {
                Log.e(LOG_TAG, "Error", e);

                //If the code didnt successfully get the weather data, there's no point
                //to parse it.

                forecastJsonStr = null;

            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }

                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e(LOG_TAG, "Error closing stream", e);
                    }
                }
            }*/


        }
        @Override
        protected void onPostExecute(String [] result)
        {
            if (result !=null)
            {
                myAdapter.clear();

                for (String dayForeCastStr : result)
                {
                    myAdapter.add(dayForeCastStr);
                }
            }
        }

    }
}
