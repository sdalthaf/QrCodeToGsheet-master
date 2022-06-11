package alabs.qsg;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity {

    String scannedData;
    private String status;
    private Date date;
    private boolean inScan = false;
    private boolean outScan = false;
    private String sdate;
    private String stime;



    Button scanBtn;
    Button clearBtn;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       final Activity activity =this;
        scanBtn = (Button)findViewById(R.id.scan_btn);

        scanBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clear(view);
                IntentIntegrator integrator = new IntentIntegrator(activity);
                integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
                integrator.setPrompt("Scan");
                integrator.setBeepEnabled(false);
                integrator.setCameraId(0);
                integrator.setBarcodeImageEnabled(false);
                integrator.initiateScan();
            }
        });

    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();
        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.radio_pirates:
                if (checked)
                    inScan = true;
                    outScan = false;
                    break;
            case R.id.radio_ninjas:
                if (checked)
                    outScan  =true;
                    inScan = false;
                    break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode,resultCode,data);
        if(result!=null) {
            scannedData = result.getContents();
            if (scannedData != null) {
                // Here we need to handle scanned data...
               new SendRequest().execute();


            }else {
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }



    public class SendRequest extends AsyncTask<String, Void, String> {


        protected void onPreExecute(){}

        protected String doInBackground(String... arg0) {

            try{
                //Enter script URL Here
                URL url = new URL("https://script.google.com/macros/s/AKfycbwyogmSWwld0MFrT0zY2ycrCgL2GyYbA_2fFkvw-wiZq_5pxr01WGl-pGN5B_XwPMMd/exec");

                JSONObject postDataParams = new JSONObject();

                //Passing scanned code as parameter
               postDataParams.put("sdata",scannedData);

               //checking time
                date = new Date() ;

                SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm") ;
                SimpleDateFormat dateF = new SimpleDateFormat("dd-MM-yyyy") ;
                stime = dateFormat.format(date);
                sdate = dateF.format(date);
                if(inScan) {
                    if(dateFormat.parse(dateFormat.format(date)).after(dateFormat.parse("08:00")) && dateFormat.parse(dateFormat.format(date)).before(dateFormat.parse("08:45")))
                    {
                        status = "On Time";
                        postDataParams.put("sdate",sdate);
                        postDataParams.put("stime",dateFormat.format(date));
                        postDataParams.put("status","On Time");
                        postDataParams.put("remarks","Keep It Up!!");


                    }else if(dateFormat.parse(dateFormat.format(date)).after(dateFormat.parse("08:45")) && dateFormat.parse(dateFormat.format(date)).before(dateFormat.parse("16:40"))){
                        status = "Late Comer";
                        postDataParams.put("sdate",sdate);
                        postDataParams.put("stime",dateFormat.format(date));
                        postDataParams.put("status","Late Comer");
                        postDataParams.put("remarks","Please be on time");
                    } else if(dateFormat.parse(dateFormat.format(date)).equals(dateFormat.parse("08:00"))){
                        status = "On Time";
                        postDataParams.put("sdate",sdate);
                        postDataParams.put("stime",dateFormat.format(date));
                        postDataParams.put("status","On Time");
                        postDataParams.put("remarks","Keep It Up!!");
                    } else {
                        status = "Invalid Scan";
                        postDataParams.put("sdate",sdate);
                        postDataParams.put("stime",dateFormat.format(date));
                        postDataParams.put("status","Invalid Scan");
                        postDataParams.put("remarks","In Scan starts at 8 AM and closes before 4.40 pm");
                    }
                }else if(outScan) {
                    if(dateFormat.parse(dateFormat.format(date)).after(dateFormat.parse("16:40")) && dateFormat.parse(dateFormat.format(date)).before(dateFormat.parse("23:59")))
                    {
                        status = "Leaving On Time";
                        postDataParams.put("sdate",sdate);
                        postDataParams.put("stime",dateFormat.format(date));
                        postDataParams.put("status","Leaving On Time");
                        postDataParams.put("remarks","Keep It Up!!");


                    }else if(dateFormat.parse(dateFormat.format(date)).after(dateFormat.parse("08:45")) && dateFormat.parse(dateFormat.format(date)).before(dateFormat.parse("16:40"))){
                        status = "Early Going";
                        postDataParams.put("sdate",sdate);
                        postDataParams.put("stime",dateFormat.format(date));
                        postDataParams.put("status","Early Going");
                        postDataParams.put("remarks","Please leave on time college closes at 4.40 pm");
                    } else if(dateFormat.parse(dateFormat.format(date)).equals(dateFormat.parse("16:40"))){
                        status = "Leaving On Time";
                        postDataParams.put("sdate",sdate);
                        postDataParams.put("stime",dateFormat.format(date));
                        postDataParams.put("status","Leaving On Time");
                        postDataParams.put("remarks","Keep It Up!!");
                    } else {
                        status = "Invalid Scan";
                        postDataParams.put("sdate",sdate);
                        postDataParams.put("stime",dateFormat.format(date));
                        postDataParams.put("status","Invalid Scan");
                        postDataParams.put("remarks","In Scan starts at 8.45 AM and closes before 11.59 pm");
                    }
                }else{
                    status = "Invalid Scan";
                    scannedData = "";
                    sdate = "";
                    stime = "";
                }

                Log.e("params",postDataParams.toString());

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(15000 /* milliseconds */);
                conn.setConnectTimeout(15000 /* milliseconds */);
                conn.setRequestMethod("GET");
                conn.setDoInput(true);
                conn.setDoOutput(true);

                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
                writer.write(getPostDataString(postDataParams));

                writer.flush();
                writer.close();
                os.close();

                int responseCode=conn.getResponseCode();
                Log.e("responseCode", String.valueOf(responseCode));

                if (responseCode == HttpsURLConnection.HTTP_OK) {

                    BufferedReader in=new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    StringBuffer sb = new StringBuffer("");
                    String line="";

                    while((line = in.readLine()) != null) {

                        sb.append(line);
                        break;
                    }

                    in.close();
                    //return sb.toString();
                    return "Success!";
                }
                else {
                    return new String("false : "+responseCode);
                }
            }
            catch(Exception e){
                return new String("Exception: " + e.getMessage());
            }
        }

        @Override
        protected void onPostExecute(String result) {
            Toast.makeText(getApplicationContext(), result,
                    Toast.LENGTH_LONG).show();

            report();

        }
    }

    public String getPostDataString(JSONObject params) throws Exception {

        StringBuilder result = new StringBuilder();
        boolean first = true;

        Iterator<String> itr = params.keys();

        while(itr.hasNext()){

            String key= itr.next();
            Object value = params.get(key);

            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(key, "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(value.toString(), "UTF-8"));

        }
        return result.toString();
    }

    @SuppressLint("ResourceType")
    public void report() {

        RelativeLayout relativeLayout = (RelativeLayout)findViewById(R.id.relativeLayout);

        TextView statusTextView = new TextView(this);
        statusTextView.setId(123);
        statusTextView.setText("Roll No: " + scannedData + "\nDate: " + sdate +"\nTime: "+stime+"\nStatus: " + status);
        statusTextView.setTextColor(Color.BLACK);
        statusTextView.setPadding(50,500,50, 170);
        relativeLayout.addView(statusTextView);
    }

    public void clear(View view) {
        RelativeLayout relativeLayout = (RelativeLayout)findViewById(R.id.relativeLayout);
        TextView statusTextView = (TextView) relativeLayout.findViewById(123);
        relativeLayout.removeView(statusTextView);
    }
}









