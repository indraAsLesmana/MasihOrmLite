package com.tutor93.tugasfrensky.util;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ClickableSpan;
import android.view.View;

import com.tutor93.tugasfrensky.R;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by frensky on 1/8/16.
 */
public class StringUtil {

    public static String checkNullString(String string) {

        if(string == null){
            return "";
        }
        else{
            return string;
        }
    }

    public static String capitalizeString(String string) {
        char[] chars = string.toCharArray();

        if (string == null) {
            return "";
        }

        boolean found = false;
        for (int i = 0; i < chars.length; i++) {
            if (!found && Character.isLetter(chars[i])) {
                chars[i] = Character.toUpperCase(chars[i]);
                found = true;
            } else if (Character.isWhitespace(chars[i]) || chars[i] == '.' || chars[i] == '\'') { // You can add other chars here
                found = false;
            }
        }
        return String.valueOf(chars);
    }

    public static String capitalizeAllString(String string) {
        char[] chars = string.toUpperCase().toCharArray();

        return String.valueOf(chars);
    }

    public static String capitalizeFirstString(String string) {
        try {
            char[] chars = string.toCharArray();

            for (int i = 0; i < chars.length; i++) {
                if (i == 0) {
                    chars[i] = Character.toUpperCase(chars[i]);
                }
            }
            return String.valueOf(chars);
        } catch (Exception e) {
            return "";
        }
    }


    public static String capitalizeStringStyle(String string) {
        char[] chars = string.toLowerCase().toCharArray();
        boolean found = false;
        boolean found2 = false;
        for (int i = 0; i < chars.length; i++) {
            if (!found) {
                if (chars[i] == '<') {
                    found2 = true;
                }

                if (found2 == true) {
                    if (chars[i] == '>') {
                        found2 = false;
                        if (i + 1 < chars.length) {
                            chars[i + 1] = Character.toUpperCase(chars[i + 1]);
                        }
                        found = true;
                    }
                } else {
                    chars[i] = Character.toUpperCase(chars[i]);
                    found = true;
                }


            } else if (Character.isWhitespace(chars[i]) || chars[i] == '.' || chars[i] == '\'') { // You can add other chars here
                found = false;
            }
        }
        return String.valueOf(chars);
    }

    public static String capitalizeHTML(String string) {
        char[] chars = string.toLowerCase().toCharArray();
        boolean found = false;
        for (int i = 0; i < chars.length; i++) {
            if (!found && Character.isLetter(chars[i])) {
                chars[i] = Character.toUpperCase(chars[i]);
                found = true;
            } else if (Character.isWhitespace(chars[i]) || chars[i] == '.' || chars[i] == '\'') { // You can add other chars here
                found = false;
            }
        }
        return String.valueOf(chars);

		/*char[] chars = string.toUpperCase().toCharArray();

		  String result = String.valueOf(chars);

		  String [] resultArray = result.split(" ");
		  String finalResult = "";
		  for(int i = 0;i< resultArray.length;i++){
			  String n = resultArray[i];
			//condition_title.setText(Html.fromHtml("<b>S</b><small>akit </small><b>L</b><small>ambung</small>"));
			  if(n.length()>0){
				  String u = "<b>"+n.charAt(0)+"</b>"+"<small><small>"+n.substring(1)+"</small></small>";
				  finalResult += u+" ";
			  }

		  }

		  return finalResult;*/
    }

    public static boolean isEmailFormatCorrect(String email){
        Pattern p = Pattern.compile(".+@.+\\.[a-z]+");
        Matcher m = p.matcher(email);

        if(!m.matches()){
            return false;
        }
        return true;
    }

    public static boolean isAlphaNumeric(String s){
        String pattern= "^[a-zA-Z0-9]*$";
        if(s.matches(pattern)){
            return true;
        }
        return false;
    }

    public final static boolean isValidEmail(CharSequence target) {
        if (target == null) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }

    public static SpannableString convertSpannable(String source, final Context activity){

        SpannableString ss = new SpannableString(source);

        String contents = source;

        if(contents == null){
            contents = "";
        }

        if(contents.isEmpty()){
            contents = "---";
        }

        contents.replace("<br>","\n");

        String[]enterArray = contents.split("\n");

        ArrayList<String> contentArray = new ArrayList<>();

        for(String element : enterArray){
            String[] space = element.split(" ");
            for(String spaceText:space){
                contentArray.add(spaceText);
            }
        }

        for(String theText:contentArray){
            String checkHttp = theText.toLowerCase();
            if(checkHttp.contains("http")){
                int firstIndex = contents.toString().indexOf(theText);
                int secondIndex = firstIndex + theText.length();
                if(firstIndex>=0){

                    final String sites = theText;
                    ClickableSpan clickableSpan = new ClickableSpan() {
                        @Override
                        public void onClick(View textView) {
                            try{
                                //in case of Http atau hTTp or htTP etc etc
                                String firstString = sites.substring(0,4);
                                String otherString = sites.substring(4,sites.length());

                                firstString = firstString.toLowerCase();
                                //String uri =firstString+otherString;
                                String url = firstString+otherString;
                                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
                                    Bundle finishBundle = ActivityOptions.makeCustomAnimation(activity, R.anim.slide_in_left, R.anim.slide_out_left).toBundle();
                                    intent.putExtra(com.tutor93.tugasfrensky.util.Constants.EXTRA_CUSTOM_TABS_EXIT_ANIMATION_BUNDLE, finishBundle);
                                    Bundle extras = new Bundle();
                                    extras.putBinder(com.tutor93.tugasfrensky.util.Constants.EXTRA_CUSTOM_TABS_SESSION, null /* Set to null for no session */);
                                    extras.putInt(com.tutor93.tugasfrensky.util.Constants.EXTRA_CUSTOM_TABS_TOOLBAR_COLOR, activity.getResources().getColor(R.color.news_purple));
                                    intent.putExtras(extras);
                                }
                                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                activity.startActivity(intent);
                                //activity.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
                                //Intent i = new Intent(Intent.ACTION_VIEW);
                                //i.setData(Uri.parse(uri));
                                //activity.startActivity(i);
                            }
                            catch(Exception e){
                                e.printStackTrace();
                            }

                        }
                    };
                    ss.setSpan(clickableSpan,firstIndex, secondIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                }
            }
        }

        return ss;
    }
}
