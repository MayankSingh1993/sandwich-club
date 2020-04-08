package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) {
        Sandwich sandwichPojoObj =null;
        try {
            //root object
           JSONObject sandwichRootObj = new JSONObject(json);
           //name
           JSONObject name = sandwichRootObj.optJSONObject("name");
           //Extract  mainName string from name
            String mainName = name.optString("mainName");


            // List for storing another names of sandwich
            List<String> alsoKnownAs = new ArrayList<>();
            // Extract alsoKnownAs Array from name
            JSONArray alsoKnownAsArray = name.optJSONArray("alsoKnownAs");

           for(int i=0;i<alsoKnownAsArray.length();i++){
               String anotherName = alsoKnownAsArray.optString(i);
               alsoKnownAs.add(anotherName);

           }
           //Extract Place of origin
            String placeOfOrigin = sandwichRootObj.optString("placeOfOrigin");
            //Extract Description
            String description = sandwichRootObj.optString("description");

            //Extract image url
            String imageurl = sandwichRootObj.optString("image");

            // List for storing ingredients of sandwich
            List<String> ingredients = new ArrayList<>();
            // Extract ingredients Array from name
            JSONArray ingredientsArray = sandwichRootObj.optJSONArray("ingredients");
            for(int i=0;i<ingredientsArray.length();i++){
                String ingredient = ingredientsArray.optString(i);
                ingredients.add(ingredient);
            }
            sandwichPojoObj = new Sandwich(mainName,alsoKnownAs,placeOfOrigin,description,imageurl,ingredients);


        } catch (Exception e) {
            e.printStackTrace();
        }
        return sandwichPojoObj;
    }
}
