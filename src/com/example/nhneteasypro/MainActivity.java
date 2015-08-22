package com.example.nhneteasypro;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import com.github.lzyzsd.jsbridge.BridgeHandler;
import com.github.lzyzsd.jsbridge.BridgeWebView;
import com.github.lzyzsd.jsbridge.CallBackFunction;
import com.github.lzyzsd.jsbridge.DefaultHandler;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.pullbears.util.JSONUtil;

import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.Button;

 @SuppressLint("SetJavaScriptEnabled")
public class MainActivity extends Activity implements OnClickListener{

	private final String TAG = "MainActivity";

	BridgeWebView webView;

	Button button;

	int RESULT_CODE = 0;
	
	ValueCallback<Uri> mUploadMessage;

    
    private Handler handler = new Handler();
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        webView = (BridgeWebView) findViewById(R.id.webView);

		button = (Button) findViewById(R.id.button);
		button.setOnClickListener( this);
		
		webView.setDefaultHandler(new DefaultHandler());
		webView.setWebChromeClient(new WebChromeClient() {
		
		});
		webView.getSettings().setJavaScriptEnabled(true);
		//webView.addJavascriptInterface(new JavaScriptinterface(this),”android”);
		webView.setDefaultHandler(new DefaultHandler(){
			@Override
			public void handler(String data, CallBackFunction function) {
				Log.i(TAG, "handler = submitFromWeb, data from web = " + data);
                function.onCallBack("submitFromWeb exe, response data from Java");
                handleWebviewTouchEvent(data);
			}
			
		});
		//String filePath = "file:///android_asset/template.html";
		String basePath = "file:///android_asset/template.html";
		webView.loadUrl(basePath);
        webView.callHandler("functionInJs", "smart", new CallBackFunction() {
            @Override
            public void onCallBack(String data) {
            	Log.i(TAG, "call webview method functionInJs 回调："+data);
            }
        });
        
        handler.postDelayed(excuteTest, 10000);
    }
    
    private Runnable excuteTest = new Runnable() {
		public void run() {
			//excuteDisplay();
		}
	};
    
    public void excuteDisplay() {
    	
    	String imgPath = "file:///android_asset/placeholder.jpg";
        String content = getFromAssets("texts/template.txt");
        Log.i(TAG, "content："+ content);
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("data", content);
        map.put("template", "tpl.detail.js");
        map.put("pict", "1");
        map.put("defaultimg", imgPath);
        
    	String data = new Gson().toJson(map);
        Log.i(TAG, "pre send data:："+data);
        webView.callHandler("buildHtmlHandler", data, new CallBackFunction() {
            @Override
            public void onCallBack(String data) {
            	Log.i(TAG, "call webview method buildHtmlHandler 回调："+data);
            	
            }
        });
	}

	@Override
	public void onClick(View v) {
		Button btn = (Button)v;
		switch (btn.getId()) {
		case R.id.button:
			excuteDisplay();
			break;

		default:
			break;
		}
	}
	
	public void handleWebviewTouchEvent(String data) {
		Map<String,String> map = new Gson().fromJson(data, new TypeToken<Map<String,String>>(){}.getType());
		String type = (String) map.get("type");
		String id = (String) map.get("id");
		Log.i(TAG, "handleWebviewTouchEvent" + type);
		new AlertDialog.Builder(this)   
		.setTitle(type)  
		.setMessage("标题" + id)  
		.setPositiveButton("是", null)  
		.setNegativeButton("否", null)  
		.show(); 
		
	}
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    public String getFromAssets(String fileName){ 
        try { 
             InputStreamReader inputReader = new InputStreamReader( getResources().getAssets().open(fileName) ); 
            BufferedReader bufReader = new BufferedReader(inputReader);
            String line="";
            String Result="";
            while((line = bufReader.readLine()) != null)
                Result += line;
            return Result;
        } catch (Exception e) { 
            e.printStackTrace(); 
        }
		return null;
    } 
}
