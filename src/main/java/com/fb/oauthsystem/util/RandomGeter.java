package com.fb.oauthsystem.util;

import java.util.Random;

public class RandomGeter {
    public static final String NUMBERLIST = "0123456789";
    public static final String LETTERLIST = "AaBbCcDdEeFfGgHhIiJjKkLlMmNnOoPpQqRrSsTtUuVvWwXxYyZz";

    public static String getRandomNumber(int place){
        return getRandom(place, NUMBERLIST);
    }

    public static String getRandomLetter(int place){
        return getRandom(place, LETTERLIST);
    }

    public static String getRandomLN(int place){
        return getRandom(place, NUMBERLIST+LETTERLIST);
    }

    private static String getRandom(int place, String parents){
        Random random = new Random();
        char[] c = new char[place];
        char[] parentList = parents.toCharArray();
        for(int i = 0; i < c.length; ++i){
            c[i] = parentList[random.nextInt(parentList.length)];
        }
        return new String(c);
    }

}
