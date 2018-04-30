package com.example.bepis05.recyclerview;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.StringTokenizer;

import javax.net.ssl.HttpsURLConnection;


public class MainActivity extends AppCompatActivity {

    public static List<Person> persons;
    public static RecyclerView rv;
    public static RVAdapter adapter;
    public static LinearLayoutManager linearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rv = (RecyclerView)findViewById(R.id.rv);
        persons = new ArrayList<>();
        rv.setHasFixedSize(true);

        linearLayoutManager = new LinearLayoutManager(this);
        rv.setLayoutManager(linearLayoutManager);

        new cargar_datos().execute(4);

        adapter = new RVAdapter(persons,this);
        rv.setAdapter(adapter);
    }
    public class cargar_datos extends AsyncTask<Integer,Integer,Void> {

        ProgressDialog pd;

        @Override
        protected void onPreExecute() {
            pd = new ProgressDialog(MainActivity.this);
            pd.setMessage("Descargando ...");
            pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            pd.setCancelable(false);
            pd.setMax(100);
            pd.show();
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            pd.setProgress(values[0]);
        }

        @Override
            protected Void doInBackground(Integer... integers) {

            String response = null;
            String surlApi = "https://randomuser.me/api";
                try {
                    int t = integers[0];
                  /*  for (int i = 0; i < integers[0]; i++) {
                        publishProgress(i * 100 / t);
                        String url = "https://picsum.photos/150/150/?random";
                        InputStream iStr = null;
                        iStr = new java.net.URL(url).openStream();
                        Bitmap bmp = BitmapFactory.decodeStream(iStr);
                        Person persona = new Person("Persona " + i, "edad " + i, bmp);
                        persons.add(persona);
                    }*/

                    for (int i = 0; i < integers[0]; i++) {
                        publishProgress((i+1) * 100 / t);
                        URL urlapi = new URL(surlApi);
                        HttpURLConnection conn = (HttpURLConnection) urlapi.openConnection();
                        conn.setRequestMethod("GET");
                        InputStream in = new BufferedInputStream(conn.getInputStream());
                        response = convertStreamToString(in);

                        Log.d("Response from url: ", response);
                            if (response != null) {
                                JSONObject jsonObject = new JSONObject(response);
                                JSONArray results = jsonObject.getJSONArray("results");
                                JSONObject resultsObject = results.getJSONObject(0);
                                JSONObject nameObject = resultsObject.getJSONObject("name");

                                //nombre
                                String nombre = nameObject.getString("first") + " " + nameObject.get("last");

                                StringTokenizer tokens = new StringTokenizer(nombre," ");
                                String Name = tokens.nextToken();
                                String Last = tokens.nextToken();
                                String nameFull = Name.substring(0,1).toUpperCase() + Name.substring(1) + " " +
                                        Last.substring(0,1).toUpperCase() + Last.substring(1);

                                //Imagen
                                JSONObject photoObject = resultsObject.getJSONObject("picture");
                                String url = photoObject.getString("large");
                                InputStream iStr = null;
                                iStr = new java.net.URL(url).openStream();
                                Bitmap bmp = BitmapFactory.decodeStream(iStr);

                                //Edad
                                Calendar cal = Calendar.getInstance();
                                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss", Locale.ENGLISH);
                                cal.setTime(sdf.parse(resultsObject.getString("dob")));// all done
                                Calendar today = Calendar.getInstance();
                                int age = today.get(Calendar.YEAR) - cal.get(Calendar.YEAR);
                                if (today.get(Calendar.DAY_OF_YEAR) < cal.get(Calendar.DAY_OF_YEAR)){
                                    age--;
                                }
                                Integer ageInt = new Integer(age);
                                String ageS = ageInt.toString();

                                //Objeto person
                                Person persona = new Person(nameFull, "Edad : " + ageS, bmp);
                                persons.add(persona);
                            }
                            else {
                                Log.d("Error","error en el json");
                            }
                    }


                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (ParseException e) {
                    e.printStackTrace();
                }

            return null;
            }

        private String convertStreamToString(InputStream in) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            StringBuilder sb = new StringBuilder();

            String line ;
            try{
                while ((line = reader.readLine())!=null){
                    sb.append(line).append('\n');
                }
            }catch (IOException e){
                e.printStackTrace();
            }finally {
                try{
                    in.close();
                }catch (IOException er){
                    er.printStackTrace();
                }
            }

            return sb.toString();
        }

        @Override
            protected void onPostExecute(Void aVoid) {
                adapter.notifyDataSetChanged();
                pd.dismiss();
            }

    }
    //params, progress,result

}
