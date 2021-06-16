/*
package com.sunyard.test;

import com.alibaba.fastjson.JSONObject;
import com.bfd.facade.MerchantServer;
import com.bfd.util.HttpConnectionManager4;
import com.sunyard.bairong.bean.bairong.BRInfoCheckResult;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

public class 百融 {
    private static volatile String tokenid;
    private static MerchantServer ms = new MerchantServer();
    //	static String strategy_id="STR0025269";
    static String conf_id = "DTV0000001";
    HttpConnectionManager4 ht = new HttpConnectionManager4();

    //	String path = " https://sandbox-api.100credit.cn/bankServer2/user/login.action"; // 登录接口
//	String hxQuePath = " https://sandbox-api.100credit.cn/strategyApi/v1/hxQuery"; // 贷前风险策略
//	String info_veriPath = " https://sandbox-api.100credit.cn/infoverify/v1/info_verify"; //// 信息验证策略
    public static void main(String[] args) {
        String login_result = null;
        try {
            login_result = getTokenId("jsyhStr", "jsyhStr", "3003724");
        } catch (Exception e) {
            throw new RuntimeException("登录异常请重新登录或者联系客服");
        }
        System.out.println(login_result);
        JSONObject json = JSONObject.parseObject(login_result);
        String tokenid = json.getString("tokenid");// 有效期一个小时，一个小时没有调用接口就会过期。需要重新登录。
        String code = json.getString("code");
        System.out.println(code + "-------------------");
        if (!StringUtils.isEmpty(tokenid) && !"".equals(tokenid) && "00".equals(code)) {
            if (StringUtils.isEmpty(tokenid) || "".equals(tokenid) || "100007".equals(tokenid)) {
                throw new RuntimeException("tokenId已经过时请重新登录");
            } else {
                try {
//					String hxquery = new Bairogn().getHxquery(tokenid, strategy_id,"");
                    String hxquery = new 百融().getHxquery(tokenid, null, conf_id);

                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            }
        }
    }

    public String getHxquery(String tokenid, String strategy_id, String conf_id) throws Exception {

        Map<String, Object> map = new HashMap<>();
        JSONObject jso = new JSONObject();
        JSONObject reqData = new JSONObject();
//		jso.put("apiName", "SandboxstrategyApi"); //测试策略贷前的
//		jso.put("apiName", "strategyApi");//测试策略贷前的正式环境
        jso.put("apiName", "SandboxverApi");//  数据验证
        jso.put("apiCode", "3003724");
        jso.put("tokenid", tokenid);
//		reqData.put("strategy_id", strategy_id);
        reqData.put("conf_id", conf_id);
//		reqData.put("id", "140502198811102244");
//		reqData.put("cell", "13986671110");// cell/mail为字符串
//		reqData.put("bank_id", "6221507950007100045");
//		reqData.put("name", "王亮");
        reqData.put("id", "140502198811102233");
        reqData.put("cell", "13986679999");
//		reqData.put("bank_id", "6221507950007100045");
        reqData.put("name", "王亮");
//		reqData.put("biz_workfor","");
        jso.put("reqData", reqData);
        String post = null;
        try {
            post = ms.getApiData(jso.toString(), "3003724");
        } catch (Exception e) {
            throw new Exception("访问受限，请联系客服");
        }
        System.out.println(post);
        BRInfoCheckResult parseObject = JSONObject.parseObject(post, BRInfoCheckResult.class);
        System.out.println(parseObject.toString());
        System.out.println(parseObject.getBankThree().getResMsg());
        return post;

    }

    private static String getTokenId(String username, String password, String apiCode) throws Exception {
        MerchantServer ms = new MerchantServer();
        return ms.login(username, password, "LoginApi", apiCode);
    }


//	public static String getBrData(String jsonData, String apicode) {
//		String res = "";
//		try {
//			res = ms.getApiData(jsonData, apicode);
//			if (!StringUtils.isEmpty(res)) {
//				JSONObject json = JSONObject.parseObject(res);
//				if (json.containsKey("code")) {
//					if (json.getString("code").equals("100007")) {
//						tokenid = null;
//					}
//				}
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return res;
//	}
}
*/
