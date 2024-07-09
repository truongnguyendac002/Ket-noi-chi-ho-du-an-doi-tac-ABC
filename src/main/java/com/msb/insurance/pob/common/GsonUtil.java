package com.msb.insurance.pob.common;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * @author locnb2
 */
public class GsonUtil {

    private GsonUtil(){}

    private static final Gson gson = new GsonBuilder().disableHtmlEscaping().create();

    private static final Gson gsonIncludeNull = new GsonBuilder().serializeNulls().create();

    public static Gson getInstance(){
        return gson;
    }

    public static Gson getIncludeNullInstance(){
        return gsonIncludeNull;
    }

}
