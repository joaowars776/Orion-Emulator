package com.orionemu.server.utilities;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


public class JsonUtil {
    private static final Gson gson = new GsonBuilder().create(); //fattar nt mycke, kan googla. hahahaahhaha

    public static Gson getInstance() {
        return gson;
    }
}
