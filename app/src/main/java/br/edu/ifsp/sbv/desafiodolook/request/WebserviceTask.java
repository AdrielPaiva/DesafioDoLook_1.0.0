package br.edu.ifsp.sbv.desafiodolook.request;

/**
 * Created by Adriel on 11/20/2017.
 */

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import br.edu.ifsp.sbv.desafiodolook.MainActivity;

public class WebserviceTask extends AsyncTask<Object, Void, String> {
    private HttpURLConnection con;
    private URL url;
    private OutputStream os;
    private BufferedReader in;
    private JSONObject data = new JSONObject();

    private ProgressDialog load;
    private Context context;
    public AsyncResponse delegate = null;

    public WebserviceTask(Context context, AsyncResponse delegate) {
        this.context = context;
        this.delegate = delegate;
    }

    @Override
    protected void onPreExecute(){
        super.onPreExecute();
        load = ProgressDialog.show(context, "Por favor Aguarde ...", "Obtendo dados ...");
    }

    @Override
    protected String doInBackground(Object... params) {
        try {
            url = new URL((String) params[0]);
            data = (JSONObject) params[1];
            con = (HttpURLConnection) url.openConnection();

            con.setReadTimeout(10000);
            con.setConnectTimeout(15000);
            con.setDoOutput(true);
            con.setDoInput(true);
            con.setRequestMethod("POST");
            con.addRequestProperty("Accept", "application/json");
            con.setRequestProperty("Content-Type", "application/json;charset=utf-8");
            con.setRequestProperty("X-Requested-With", "XMLHttpRequest");
            con.setFixedLengthStreamingMode(data.toString().getBytes().length);

            con.connect();

            os = new BufferedOutputStream(con.getOutputStream());

            os.write(data.toString().getBytes());
            os.flush();
            os.close();

            in = new BufferedReader(new InputStreamReader(con.getInputStream()));

            String inputLine;

            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine + "\n");
            }

            in.close();

            MainActivity.dataResult = null;
            MainActivity.dataResult = new JSONObject(response.toString());
            return MainActivity.dataResult.get("return").toString();
            //return new JSONObject(response.toString()).get("status").toString();
        } catch (Exception ex) {
            Log.i("Webservice", "Erro " + ex.getMessage());
        }
        finally {
            con.disconnect();
        }

        return null;
    }

    @Override
    protected void onPostExecute(String result){
        super.onPostExecute(result);
        load.dismiss();
        delegate.processFinish(this.context, result);
    }

    public interface AsyncResponse {
        void processFinish(Context context, String result);
    }
}