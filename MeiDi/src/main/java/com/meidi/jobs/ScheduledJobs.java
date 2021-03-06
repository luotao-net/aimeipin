package com.meidi.jobs;

import com.meidi.domain.*;
import com.meidi.repository.*;
import com.meidi.util.MdCommon;
import com.meidi.util.MdConstants;
import com.meidi.util.WxTemplate;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Component
public class ScheduledJobs {
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    @Resource
    private CommodityRepository commodityRepository;
    @Resource
    private OrderRepository orderRepository;
    @Resource
    private WxTicketRepository wxTicketRepository;
    @Resource
    private GroupLaunchRepository groupLaunchRepository;
    @Resource
    private GroupLaunchUserRepository groupLaunchUserRepository;
    @Resource
    private UserRepository userRepository;

    /**
     * 每天00:05检查上架商品到期
     * 检查已经上架的项目状态
     * 如果项目时间结束则下架
     */
    @Scheduled(cron = "0 5 0 * * *")
    public void checkCommodityEnded() {
        try {
            String today = dateFormat.format(new Date());
            List<Commodity> endedCommodity = commodityRepository.findByStateAndEndDateBefore(1, today);

            for (Commodity commodity : endedCommodity) {
                // find all unpaid order (state=1) and close it
                List<Order> orders = orderRepository.findByStateAndCommodityId(1, commodity.getId());
                for (Order order : orders) {
                    order.setState(8);
                    orderRepository.save(order);
                }
                // mark commodity as down
                commodity.markAsDown();
                commodity.setWeight(0); // 重置权重为0
                commodityRepository.save(commodity);
            }
        } catch (Exception e) {
            System.out.println("[ScheduledJobs] Check commodity ended failed.");
            e.printStackTrace();
        }
    }

    /**
     * 每5分钟检查一次拼团状态
     * 检查所有进行中的拼团, 如果拼团时间结束则改变状态
     */
    @Scheduled(fixedDelay = 300 * 1000)
    public void checkGroupLaunchEnded() {
        try {
            String token = wxTicketRepository.findByAppid(MdConstants.WX_APP_ID).getToken();
            // 查询状态仍为拼团中,实际已经超过结束时间的拼团
            Date now = new Date();
            List<GroupLaunch> groupLaunches = groupLaunchRepository.findByStateAndEndTimeIsBefore(0, now);

            for (GroupLaunch groupLaunch : groupLaunches) {
                List<Order> orders = orderRepository.findByLaunchId(groupLaunch.getId());

                // 取消所有未支付订单
                for (Order order : orders) {
                    if (order.getState() == 1) { // 未支付
                        order.setState(8);
                        orderRepository.save(order);
                    }
                }

                List<GroupLaunchUser> groupLaunchUserList = groupLaunchUserRepository.findByLaunchId(groupLaunch.getId());
                if (groupLaunchUserList.size() >= groupLaunch.getPeopleNumber()) {
                    //人数达标, 该拼团成功
                    groupLaunch.setState(1);//拼团结束 & 拼团成功
                } else {
                    groupLaunch.setState(3);//拼团失败
                    for (Order order : orders) {
                        if (order.getState() == 2) { // 拼团失败,退款所有已支付订单
                            order.setState(5);// 改成取消中待退款
                            orderRepository.save(order);
                            WxTemplate.groupClose(token, order);
                        }
                    }
                }
                groupLaunchRepository.save(groupLaunch);
            }
        } catch (Exception e) {
            System.out.println("[ScheduledJobs] Check GroupLaunch ended failed.");
            e.printStackTrace();
        }
    }

    /**
     * 每小时更新一次WX ticket
     */
    @Scheduled(fixedRate = 3600 * 1000)
    public void updateWxTicket() {
        Iterable<WxTicket> appList = wxTicketRepository.findAll();

        for (WxTicket wxTicket: appList) {
            wxTicket.updateTokenTicket();
            wxTicketRepository.save(wxTicket);
        }
    }

    /**
     * 每天01:15 更新公众号关注用户,将是否关注置为是
     */
    @Scheduled(cron = "0 15 1 * * *")
    public void updateSubscribeUser() {
        //reset subscribe
        Iterable<User> allUsers=userRepository.findAll();
        for (User user : allUsers) {
            user.setIsSubscribe(false);
            userRepository.save(user);
        }

        String token = wxTicketRepository.findByAppid(MdConstants.WX_APP_ID).getToken();
        String next_openid = "";
        Integer total = 0;
        Integer count = 10000;
        String url;

        HttpClient client = HttpClients.createDefault();
        HttpGet httpGet;
        HttpResponse httpResponse;

        while (count >= 10000) {
            url = "https://api.weixin.qq.com/cgi-bin/user/get?access_token=" + token + "&next_openid=" + next_openid;
            try {
                httpGet = new HttpGet();
                httpGet.setURI(new URI(url));
                httpResponse = client.execute(httpGet);
                String returnStr = EntityUtils.toString(httpResponse.getEntity());
                JSONObject obj = JSONObject.fromObject(returnStr);
                next_openid = obj.getString("next_openid");
                total = obj.getInt("total");
                count = obj.getInt("count");
                JSONArray openidArr = obj.getJSONObject("data").getJSONArray("openid");

                for (Object openid : openidArr) {
                    User u = userRepository.findByWxOpenid(openid.toString());
                    if (!MdCommon.isEmpty(u)) {
                        u.setIsSubscribe(true);
                        userRepository.save(u);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("[ScheduledJobs] update subscribe list failed.");
                break;
            }
        }
        System.out.println("[ScheduledJobs] "+total.toString()+" subscribe user updated.");
    }
}
