package com.meidi.util;

import com.meidi.domain.Order;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 * Created by luanpeng on 16/4/24.
 */
public class WxTemplate implements MdConstants {


    /**
     * 开团成功
     *
     * @param token
     * @param order
     * @throws IOException
     */
    public static void groupLaunch(String token, Order order) throws IOException {
        String jsonStr = "{\"touser\": \"" + order.getWxOpenid() + "\", \"template_id\": \"" + WX_GROUP_LAUNCH + "\", " +
                "\"url\": \"" + PATH + "/business/joinGroupPage/" + order.getLaunchId() + "\",\"topcolor\":\"#32b16c\"," +
                "\"data\":{\"first\": {\"value\":\"您已开团成功，立刻邀请小伙伴参团，达到规定人数，团购价立即生效！\",\"color\":\"#0A0A0A\"}," +
                "\"keyword1\": {\"value\":\"" + order.getPayAmount()/100 + "\",\"color\":\"#888\"}," +
                "\"keyword2\": {\"value\":\"" + order.getCommodityName() + "\",\"color\":\"#888\"}," +
//                "\"keyword3\": {\"value\":\"" + order.getDicCity().getName() + "\",\"color\":\"#888\"}," +
                "\"keyword4\": {\"value\":\"" + order.getOrderCode() + "\",\"color\":\"#888\"}," +
                "\"remark\": {\"value\":\"点击消息分享给小伙伴，邀请Ta们一起优惠变美吧！\",\"color\":\"#888\"}" +
                "}}";

        sendMsg(token, jsonStr);
    }

//    public static void groupLaunchTest(String token,String wxOpenid) throws IOException {
//        String jsonStr = "{\"touser\": \"" + wxOpenid + "\", \"template_id\": \"" + WX_GROUP_LAUNCH + "\", " +
//                "\"url\": \"" + PATH + "/business/joinGroupPage/" + 123 + "\",\"topcolor\":\"#32b16c\"," +
//                "\"data\":{\"first\": {\"value\":\"您好, 恭喜您开团成功！\",\"color\":\"#0A0A0A\"}," +
//                "\"keyword1\": {\"value\":\"" + 222 + "\",\"color\":\"#888\"}," +
//                "\"keyword2\": {\"value\":\"ces\",\"color\":\"#888\"}," +
////                "\"keyword3\": {\"value\":\"" + order.getDicCity().getName() + "\",\"color\":\"#888\"}," +
//                "\"keyword4\": {\"value\":\"asdergegdfg\",\"color\":\"#888\"}," +
//                "\"remark\": {\"value\":\"点击消息分享给约小伙伴，邀请和你一起购买吧，一起买，一起实惠！\",\"color\":\"#888\"}" +
//                "}}";
//
//        sendMsg(token, jsonStr);
//    }

    /**
     * 拼团成功
     *
     * @param token
     * @param order
     * @throws IOException
     */
    public static void groupLaunchOk(String token, Order order) throws IOException {
        String jsonStr = "{\"touser\": \"" + order.getWxOpenid() + "\", \"template_id\": \"" + WX_GROUP_LAUNCH + "\", " +
                "\"url\": \"" +  ""  + "\",\"topcolor\":\"#32b16c\"," +
                "\"data\":{\"first\": {\"value\":\"您好, 恭喜您的拼团已成功！\",\"color\":\"#0A0A0A\"}," +
                "\"keyword1\": {\"value\":\"" + order.getCommodityName() + "\",\"color\":\"#888\"}," +
                "\"keyword2\": {\"value\":\"" + "" + "\",\"color\":\"#888\"}," +
                "\"keyword3\": {\"value\":\"" + order.getDicCity().getName() + "\",\"color\":\"#888\"}," +
                "\"remark\": {\"value\":\"客服会尽快与您取得联系，或拨打4006056662预约咨询！\",\"color\":\"#888\"}" +
                "}}";

        sendMsg(token, jsonStr);
    }

    /**
     * 参团成功
     * @param token
     * @param order
     */
    public static void joinGroup(String token, Order order) throws IOException {
        String jsonStr = "{\"touser\": \"" + order.getWxOpenid() + "\", \"template_id\": \"" + WX_JOIN_GROUP + "\", " +
                "\"url\": \"" + PATH + "/business/joinGroupPage/" + order.getLaunchId() + "\",\"topcolor\":\"#32b16c\"," +
                "\"data\":{\"first\": {\"value\":\"您好, 恭喜您参团成功！如已达规定人数，则团购价立即生效；如未达规定人数，可分享给小伙伴，邀请参团。\",\"color\":\"#0A0A0A\"}," +
                "\"keyword1\": {\"value\":\"" + order.getCommodityName() + "\",\"color\":\"#888\"}," +
                "\"keyword2\": {\"value\":\"" + order.getCreateTime() + "\",\"color\":\"#888\"}," +
                "\"remark\": {\"value\":\"赶快分享给小伙伴，邀请Ta们一起优惠变美吧！\",\"color\":\"#888\"}" +
                "}}";

        sendMsg(token, jsonStr);
    }

    /**
     * 拼团失败
     * @param token
     * @param order
     * @throws IOException
     */
    public static void groupClose(String token, Order order) throws IOException {
        String jsonStr = "{\"touser\": \"" + order.getWxOpenid() + "\", \"template_id\": \"" + WX_GROUP_FAIL + "\", " +
                "\"url\": \"" + "" + "\",\"topcolor\":\"#32b16c\"," +
                "\"data\":{\"first\": {\"value\":\"很遗憾地通知您，因超时未达规定人数，开团失败！\",\"color\":\"#0A0A0A\"}," +
                "\"keyword1\": {\"value\":\"" + order.getPayAmount()/100 + "\",\"color\":\"#888\"}," +
                "\"keyword2\": {\"value\":\"" + order.getCommodityName() + "\",\"color\":\"#888\"}," +
                "\"keyword3\": {\"value\":\"" + "" + "\",\"color\":\"#888\"}," +
                "\"keyword4\": {\"value\":\"" + order.getOrderCode() + "\",\"color\":\"#888\"}," +
                "\"remark\": {\"value\":\"您有一个拼团因超时失败，请到聚会美商城“我的订单”中查看，重新发起拼团！\",\"color\":\"#888\"}" +
                "}}";

        sendMsg(token, jsonStr);
    }

    /**
     * 订单完成
     * @param token
     * @param order
     * @throws IOException
     */
    public static void orderComplete(String token, Order order) throws IOException {
        String jsonStr = "{\"touser\": \"" + order.getWxOpenid() + "\", \"template_id\": \"" + WX_ORDER_COMPLETE + "\", " +
                "\"url\": \"" + "" + "\",\"topcolor\":\"#32b16c\"," +
                "\"data\":{\"first\": {\"value\":\"您好，订单已完成！\",\"color\":\"#0A0A0A\"}," +
                "\"keyword1\": {\"value\":\"" + order.getOrderCode() + "\",\"color\":\"#888\"}," +
                "\"keyword2\": {\"value\":\"" + order.getCommodityName() + "\",\"color\":\"#888\"}," +
                "\"remark\": {\"value\":\"感谢您选择聚会美，如有更多需要，请联系客服！\",\"color\":\"#888\"}" +
                "}}";

        sendMsg(token, jsonStr);
    }

    public static void sendMsg(String token, String jsonStr) throws IOException{
        String url = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=" + token;
        HttpClient client = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);


        httpPost.setEntity(new StringEntity(jsonStr, "utf-8"));
        HttpResponse response = client.execute(httpPost);
        String returnStr = EntityUtils.toString(response.getEntity(), "UTF-8");
        System.out.println(returnStr);
    }
}