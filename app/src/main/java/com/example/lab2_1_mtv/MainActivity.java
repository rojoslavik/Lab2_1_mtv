package com.example.lab2_1_mtv;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    EditText osoite;
    TextView htmlTextBox;
    Button go;
    String osoiteTeksti;
    String htmlString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        osoite = findViewById(R.id.editText);
        go = findViewById(R.id.goButton);
        htmlTextBox = findViewById(R.id.htmlTextBox);

        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AsyncTaskRunner runner = new AsyncTaskRunner();
                htmlTextBox.setText(htmlString);
                runner.execute(htmlString);

            }
        });


    }



    private class AsyncTaskRunner extends AsyncTask<String, URL, String> {


        @Override
        protected String doInBackground(String... params) {
            try {
                osoiteTeksti = osoite.getText().toString();
                URL url = new URL(osoiteTeksti);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                InputStream in = new BufferedInputStream(connection.getInputStream());
                htmlString = convertStreamToString(in);

            }

                catch(Exception e){
                    e.printStackTrace();
                }
                return htmlString;
            }

        protected void onPostExecute(String htmlString) {
            htmlTextBox.setText(htmlString);

        }
        }


    private String convertStreamToString(InputStream in) {
        java.util.Scanner s = new java.util.Scanner(in).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }

}

