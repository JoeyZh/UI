package com.joey.protocol;

/**
 * Created by Administrator on 2016/7/22.
 */
public interface ResponseHandler {
    /**
     * 成功
     */
    public void onSuccess();

    /**
     * 加载中
     */
    public void onLoading();

    /**
     * 出错
     */
    public void onError();
}
