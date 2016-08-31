package newsarig.com.example.erez.newsarig;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;
import java.util.StringTokenizer;

/**
 * Created by erez on 31/08/2016.
 */
public class MSGParser
{
    public static MSGData parse(String msg)
    {

        StringTokenizer tokens  = new StringTokenizer(msg, "|");
        String typeStr = tokens.nextToken();
        MSGData msgData = new MSGData(typeStr);
        if(! tokens.hasMoreTokens())
        {
            return msgData;
        }
        String action = tokens.nextToken();
        msgData.setAction(action);
        if(! tokens.hasMoreTokens())
        {
            return msgData;
        }
        String jsonStr = tokens.nextToken();


        JSONObject jsonObject;
        try {
            jsonObject = new JSONObject(jsonStr);
        } catch (JSONException e) {
            Log.d("MSGParser", "json parse error");
            return null;
        }
        String[] keys = getNames(jsonObject);

        for (String key : keys)
        {
            try {
                String value = String.valueOf(jsonObject.get(key));
                msgData.addParam(new Param(key, Integer.valueOf(value)));

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        return msgData;

    }

    public static String[] getNames(JSONObject jo) {
        int length = jo.length();
        if (length == 0) {
            return null;
        }
        Iterator<String> iterator = jo.keys();
        String[] names = new String[length];
        int i = 0;
        while (iterator.hasNext()) {
            names[i] = iterator.next();
            i += 1;
        }
        return names;
    }
}
