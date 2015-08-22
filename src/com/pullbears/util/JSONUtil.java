/**
 * @author azrael
 * @date 2013-2-6
 */
package com.pullbears.util;

import android.os.Bundle;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Set;

/**
 * @author azrael
 *
 */
public class JSONUtil {
    
    public static JSONObject bundleToJSON(Bundle bundle) throws JSONException{
        JSONObject json = new JSONObject();
        if(bundle == null || bundle.isEmpty()){
            return json;
        }
        Set<String> keySet = bundle.keySet();
        for (String key : keySet) {
            Object object = bundle.get(key);
            if (object instanceof String || object instanceof Boolean || object instanceof Integer
                    || object instanceof Long || object instanceof Double){
                json.put(key, object);
            }
        }
        return json;
    }
    
   public static String bundleToJSONString(Bundle bundle) throws JSONException{
       JSONObject json = bundleToJSON(bundle);
       return json.toString();
   }
    
   public static Bundle jsonTobundle(Object obj) {
	   Bundle bundle = new Bundle();
	   bundle.putString("jsonString",obj.toString());
	   return bundle;
   }
}
