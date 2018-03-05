package uk.ac.ucl.cege.cegeg077.zcwcjbu.geojsontest1;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;


public class SendCheckboxToServer extends Activity implements View.OnClickListener
{

    TextView mTextview;
    private WebView wv;
    String answer;
    public String c1;
    public String c2;
    public String c3;
    public String question;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.webpage);

        ((Button) findViewById(R.id.button)).setOnClickListener(this);
        //intent extras fetched with values pertaining to marker point
        Bundle extras = getIntent().getExtras();
        question = getIntent().getStringExtra("q");
        c1 = (String) extras.get("c1");
        c2 = (String) extras.get("c2");
        c3 = (String) extras.get("c3");
        answer = (String) extras.get("a");
        // wv = (WebView) findViewById(R.id.textView1);
        //wv.loadUrl(url);
        //setting text for questions
        mTextview = (TextView) findViewById(R.id.textView1);

        mTextview.setText(question);
        CheckBox oneCheckBox = (CheckBox) findViewById(R.id.one);
        oneCheckBox.setText(c1);
        CheckBox twoCheckBox = (CheckBox) findViewById(R.id.two);
        twoCheckBox.setText(c2);
        CheckBox threeCheckBox = (CheckBox) findViewById(R.id.three);
        threeCheckBox.setText(c3);

    }

    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.button:
                //button to post user inputted data
                submitDataPost();
                break;
        }
    }
    private void submitDataPost() {
        // extract the data from the form into an array of NameValuePair objects
        //NameValuePair nameValuePairs[] = getFormDetails();

        String data="name=claire&surname=ellul";

        // create an asynchronous operation that will take these values
        // and send them to the server
        SendHttpRequestTask sfd = new SendHttpRequestTask();
        try {
            EditText et = (EditText)findViewById(R.id.firstName);
            String theFirstName = (String) et.getText().toString();

            // get the surname
            EditText et2 = (EditText)findViewById(R.id.surname);
            String theSurname = (String) et2.getText().toString();


            String urlParameters =
                    "firstname=" + URLEncoder.encode(theFirstName, "UTF-8") +
                            "&surname=" + URLEncoder.encode(theSurname, "UTF-8");
            // also add the checkboxes - are they checked or not
            urlParameters += "&"+getCheckBoxes();
            Log.i("result",urlParameters);

            sfd.execute(urlParameters);
        }
        catch             (UnsupportedEncodingException e){}

    }
    private String getCheckBoxes() {
        String result;
        Bundle extras = getIntent().getExtras();
        question = getIntent().getStringExtra("q");
        c1 = (String) extras.get("c1");
        c2 = (String) extras.get("c2");
        c3 = (String) extras.get("c3");
        answer = (String) extras.get("a");
        // wv = (WebView) findViewById(R.id.textView1);
        //wv.loadUrl(url);
        mTextview = (TextView) findViewById(R.id.textView1);

        mTextview.setText(question);
        CheckBox oneCheckBox = (CheckBox) findViewById(R.id.one);
        oneCheckBox.setText(c1);
        //listener checks which checkboxes are checked
        result = "=" + oneCheckBox.isChecked();
        CheckBox twoCheckBox = (CheckBox) findViewById(R.id.two);
        twoCheckBox.setText(c2);

        result += "&" + "=" + twoCheckBox.isChecked();

        CheckBox threeCheckBox = (CheckBox) findViewById(R.id.three);
        threeCheckBox.setText(c3);

        result += "&" + "=" + threeCheckBox.isChecked();
        Log.d("url", c1);
        return result;
    }

    private class SendHttpRequestTask extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected String doInBackground(String... params) {
            URL url;
            String urlParams = params[0];
            String targetURL = "http://developer.cege.ucl.ac.uk:30522/teaching/user36/process_form.php";

            HttpURLConnection connection = null;
            try {
                //Create connection
                url = new URL(targetURL);
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Content-Type",
                        "application/x-www-form-urlencoded");

                connection.setRequestProperty("Content-Length", "" +
                        Integer.toString(urlParams.getBytes().length));
                connection.setRequestProperty("Content-Language", "en-US");

                connection.setUseCaches(false);
                connection.setDoInput(true);
                connection.setDoOutput(true);

                //Send request
                DataOutputStream wr = new DataOutputStream(
                        connection.getOutputStream());
                wr.writeBytes(urlParams);
                wr.flush();
                wr.close();

                //Get Response
                InputStream is = connection.getInputStream();
                BufferedReader rd = new BufferedReader(new InputStreamReader(is));
                String line;
                StringBuffer response = new StringBuffer();
                while ((line = rd.readLine()) != null) {
                    response.append(line);
                    response.append('\r');
                }
                rd.close();
                connection.disconnect();
                return response.toString();

            } catch (Exception e) {

                e.printStackTrace();
                return null;

            } finally {

                if (connection != null) {
                    connection.disconnect();
                }
            }
        }

        //set text to return answer to user upon successful submission
        @Override
        protected void onPostExecute(String response) {
            TextView xTextview = (TextView) findViewById(R.id.answer);
            xTextview.setText(answer);
            System.out.print("yassssss");
        }
    }
}