package com.pullbears.javascript;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;
import com.pullbears.util.JSONUtil;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.webkit.WebView;

@SuppressLint("NewApi") public class JavascriptBridge {
	public interface Callback{
		public void onComplate(JSONObject response ,String cmd,Bundle params);
	}
	public interface Function {
		public Object execute(JSONObject params);
	}
	private static long seed = 0;
	private static long getSerial() {
		return ++seed;
	}
	
	class Command{
		long serial;
		String cmd;
		Bundle params;
		Callback callback;
		public Command(){
			this.serial = getSerial();
		}
		public Command(String cmd,Bundle params, Callback callback) {
			this();
			this.cmd = cmd;
			this.params = params;
			this.callback = callback;
		}
		@Override
		public String toString(){
			JSONObject json = new JSONObject();
			try{
				json.put("cmd", this.cmd);
				json.put("serial", this.serial);
				json.put("params", JSONUtil.bundleToJSON(this.params));
			}catch(JSONException e){
				e.printStackTrace();
			}
			return json.toString();
		}
		
		public void release(){
			this.serial = 0;
			this.cmd = null;
			this.params = null;
			this.callback = null;
		}
	}
	class JavascriptInterface{
		public String getCommands() {
			ArrayList<Command> temp = commandQueue;
			commandQueue = new ArrayList<Command>();
			String cmds = temp.toString();
			temp.clear();
			return cmds;
		}
		
		public void setResult(long serial,String jsonResult) {
			Command command = commandMap.remove(serial);
			if (command == null) {
				return;
			}
			JSONObject json = null;
			if (command.callback != null && jsonResult != null && !jsonResult.isEmpty()) {
				try {
					json = new JSONObject(jsonResult);
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
				command.callback.onComplate(json,command.cmd,command.params);
			}
			command.release();
		}
		@android.webkit.JavascriptInterface
		public Object require(String cmd,String params) {
			Function function = javaMethodMap.get(cmd);
			if (function != null) {
				try {
					JSONObject json = new JSONObject(params);
					return function.execute(json);
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
			}
			return null;
		}
	}
	
	private static final String API_NAMESPACE = "__JavascriptBridge__";
	private HashMap<Long, Command> commandMap;
	private ArrayList<Command> commandQueue;
	private HashMap<String, Function> javaMethodMap;
	
	public JavascriptBridge (WebView mWebView) {
		commandMap = new HashMap<Long, Command>();
		commandQueue = new ArrayList<Command>();
		javaMethodMap = new HashMap<String, Function>();
		mWebView.addJavascriptInterface(new JavascriptInterface(), API_NAMESPACE);
	}
	
	public void require(String cmd,Bundle params,Callback callback) {
		Command command = new Command(cmd, params, callback);
		commandMap.put(command.serial, command);
		commandQueue.add(command);
	}
	
	public void addJavaMethod(String method,Function function) {
		javaMethodMap.put(method, function);
	}
}
