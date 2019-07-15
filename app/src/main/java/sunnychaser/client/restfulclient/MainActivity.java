package sunnychaser.client.restfulclient;

import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void showDialog(String info) {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setIcon(R.mipmap.ic_launcher);
        builder.setTitle("登录结果");
        builder.setMessage(info);
        builder.setPositiveButton("确定", null);
        builder.show();
    }

    protected class Login {
        protected String username;
        protected String password;

        protected Login(String uname, String upwd) {
            username = uname;
            password = upwd;
        }
        protected void login() {
            AsyncHttpClient client = new AsyncHttpClient();
            RequestParams pars = new RequestParams();
            pars.put("username", username);
            pars.put("password", password);
            client.post("http://192.168.1.253:8080/user/login", pars, new JsonHttpResponseHandler() {

                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    try {
                        showDialog(response.get("infomsg").toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
        });
        }
    }

    public void onlogin(View v) {
        EditText etusername = (EditText)findViewById(R.id.editText);
        EditText etpassword = (EditText)findViewById(R.id.editText2);
        final String username = etusername.getText().toString();
        final String password = etpassword.getText().toString();
        Login l = new Login(username, password);
        l.login();
    }
}
