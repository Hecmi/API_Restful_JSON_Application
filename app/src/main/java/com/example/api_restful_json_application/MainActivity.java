package com.example.api_restful_json_application;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    TextView txtUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtUsuario = (TextView)findViewById(R.id.txtUsuarios);
        request_JSON("https://dummyjson.com/users");
    }

    //Función que recibe como parámetro la URL a realizar la petición.
    private void request_JSON(String url){

        RequestQueue queue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //TRY-CATCH para validar el error que se pueda presentar al formatear el JSON.
                        try {
                            txtUsuario.setText(formatear_JSON(response));
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        txtUsuario.setText("Error en la petición realizada a: " + url);
                    }
                });

        queue.add(stringRequest);
    }

    //Función para formatear el resultado de la petición.
    private String formatear_JSON(String response) throws JSONException {
        String texto_formateado = "";

        JSONObject jObject = new JSONObject(response);
        JSONArray jArray = jObject.getJSONArray("users");

        for (int i = 0; i < jArray.length(); i++){
            JSONObject usuario = jArray.getJSONObject(i);

            texto_formateado += "\n" + usuario.get("firstName") + ", " + usuario.get("age") + ", " + usuario.get("email");
        }

        return texto_formateado;
    }
}