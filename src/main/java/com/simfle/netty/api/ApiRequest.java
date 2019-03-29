package com.simfle.netty.api;

import com.google.gson.JsonObject;

public interface ApiRequest {

    public void requestParamValidation();

    public void service();

    public void executeService();

    public JsonObject getApiResult();

}
