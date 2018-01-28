package com.example.admin.androidadvance;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Admin on 29/01/2018.
 */

public class ParserData {
    private String dulieu;
    private String id;

    public ParserData(String dulieu,String id) {
        this.dulieu = dulieu;
        this.id = id;
    }

        public GithubData GetNamePackage(){
            GithubData data = null;
            try {
                JSONArray mJsonArray = new JSONArray(dulieu);

                for(int i = 0 ; i<mJsonArray.length();i++){


                    JSONObject mJsonObject = mJsonArray.getJSONObject(i);
                    if(mJsonObject.getString("id").compareTo(id)==0){
                        data = new GithubData(mJsonObject.getString("id"),mJsonObject.getString("name"),mJsonObject.getString("full_name"),mJsonObject.getString("owner"),mJsonObject.getString("private"),mJsonObject.getString("html_url"));

                    }

                }




            } catch (JSONException e) {
            e.printStackTrace();
        }


        return data;

    }
}
