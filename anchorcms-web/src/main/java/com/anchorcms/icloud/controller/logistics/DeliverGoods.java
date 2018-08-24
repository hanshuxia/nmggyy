package com.anchorcms.icloud.controller.logistics;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.anchorcms.icloud.controller.logistics.MD5Util;

/**
 * @Description: 卖家发货
 * @Date 20170628
 * @author zhouyq
 *
 */
public class DeliverGoods {

    /**
     * @Author zhouyq
     * @Date 20170628
     * @Desc 卖家发货/创建物流订单
     */
    public String createLogisticsOrder(String creParam) throws Exception {

        // 返回创建订单结果
        String createResultMsg = null;

        // 参数数组
        String[] creLogisticsParaArray = creParam.split(",");

        // 创建订单
        String logistics_interface = "{\"eccompanyid\":\"HTYW\",\"logisticproviderid\":\"ZT\",\"txlogisticid\":\""+creLogisticsParaArray[0]+"\",\"ordertype\":1,\"servicetype\":\"11\",\"sender\":{\"name\":\""+creLogisticsParaArray[1]+"\",\"postcode\":\""+creLogisticsParaArray[2]+" \",\"mobile\":\""+creLogisticsParaArray[3]+"\",\"prov\":\""+creLogisticsParaArray[4]+"\",\"city\":\""+creLogisticsParaArray[5]+"\",\"area\":\""+creLogisticsParaArray[6]+"\",\"address\":\""+creLogisticsParaArray[7]+"\"},\"receiver\":{\"name\":\""+creLogisticsParaArray[8]+"\",\"postcode\":\""+creLogisticsParaArray[9]+"\",\"mobile\":\""+creLogisticsParaArray[10]+"\",\"prov\":\""+creLogisticsParaArray[11]+"\",\"city\":\""+creLogisticsParaArray[12]+"\",\"area\":\""+creLogisticsParaArray[13]+"\",\"address\":\""+creLogisticsParaArray[14]+"\"},\"createordertime\":\""+creLogisticsParaArray[15]+"\",\"goodsvalue\":\"0\",\"paytype\":2,\"itemsvalue\":0,\"items\":[{\"itemname\":\"航联货物\",\"number\":\"1\",\"itemvalue\":\"0\"}],\"number\":1,\"note\":\"轻搬轻放，请勿倒置\"}";
        // 签名秘钥key
        String key = "92C2DB04ACA6346B";

        // 编码格式
        String charset = "UTF-8";

        // 消息类型
        String msg_type = "ORDERCREATE";// 创建订单

        // 电商或物流商标识
        String eccompanyid = "HTYW";

        // 订单接收地址
//        String url = "http://msdcn.wicp.net:11143/cre_oms/order.action";
//        String url = "http://220.194.2.197:278220/cre_oms/orderG.action";
        String url = "http://220.195.2.197:22220/cre_oms/order.action";

        // 生成签名
        String data_digest = new String(Base64.encodeBase64(MD5Util.code32(logistics_interface + key, charset).getBytes(charset)));

        // HTTP协议的客户端对象HttpClient
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(url);

        // post参数
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        nvps.add(new BasicNameValuePair("logistics_interface", logistics_interface));
        nvps.add(new BasicNameValuePair("data_digest", data_digest));
        nvps.add(new BasicNameValuePair("msg_type", msg_type));
        nvps.add(new BasicNameValuePair("eccompanyid", eccompanyid));

        // 将参数传入post方法中
        httpPost.setEntity(new UrlEncodedFormEntity(nvps, charset));
        HttpResponse response = httpclient.execute(httpPost);
        HttpEntity entity = response.getEntity();
        if (entity != null) {
            createResultMsg = EntityUtils.toString(entity);
            JSONObject object = JSONObject.fromObject(createResultMsg);
            JSONArray resultArray =  object.getJSONArray("responseitems");
            createResultMsg = (String) resultArray.getJSONObject(0).get("success");
        }
        return createResultMsg;
    }

    /**
     * @Author zhouyq
     * @Date 20170630
     * @Desc 查询物流订单状态
     */
    public String selLogisticsOrder(String selParam) throws Exception {

        // 查询订单结果
        String selResultMsg = null;

        // 编码格式
        String charset = "UTF-8";

        // 订单接收地址
        String url = "http://220.195.2.197:8080/WlzbServer/ZzxxOutServlet?orderno=" + selParam;

        // HTTP协议的客户端对象HttpClient
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(url);

        // post参数
//        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
//        nvps.add(new BasicNameValuePair("logistics_interface", logistics_interface));

        // 将参数传入post方法中
//        httpPost.setEntity(new UrlEncodedFormEntity(nvps, charset));
        HttpResponse response = httpclient.execute(httpPost);
        HttpEntity entity = response.getEntity();
        if (entity != null) {
            selResultMsg = EntityUtils.toString(entity);
            JSONArray jsonArrayObj = JSONArray.fromObject(selResultMsg);
            selResultMsg = String.valueOf(jsonArrayObj);
        }
        return selResultMsg;
    }
}
