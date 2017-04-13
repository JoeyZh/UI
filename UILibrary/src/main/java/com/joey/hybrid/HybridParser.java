package com.joey.hybrid;

/**
 * Created by Administrator on 2016/11/18.
 */
public class HybridParser {
    public static final String GOTO_DETAIL_URL = "m=default&c=goods&a=index&id=";

    public static String parseGoodsId(String url) {
        String values[] = url.split(GOTO_DETAIL_URL);
        if (null == values || values.length != 2)
            return null;
        String ids[] = values[1].split("&");
//        KLog.a("goods_id = " + ids[0]);
        return ids[0];
    }

}
