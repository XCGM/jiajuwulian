package com.xcgmym.jiajuwulian;

import org.json.JSONObject;
import org.json.JSONException;

public class Test
{
	public Test()
	{
	}

	public static void main(String[] arg)
	{
		String string = "{\"zt\":1,\"info\":{\"appid\":\"wx545c8271b1900e40\",\"attach\":[],\"bank_type\":\"CCB_CREDIT\",\"cash_fee\":\"1\",\"fee_type\":\"CNY\",\"is_subscribe\":\"Y\",\"mch_id\":\"1484697352\",\"nonce_str\":\"3Cqah5eJoNxljOhr\",\"openid\":\"oi-Jy1VAYN3aVRIk8si9CdrOYXL4\",\"out_trade_no\":\"148469735220180327170138\",\"result_code\":\"SUCCESS\",\"return_code\":\"SUCCESS\",\"return_msg\":\"OK\",\"sign\":\"A8D7BDB71EFA9602A6CE9C0E4BCCDCF5\",\"sub_mch_id\":\"75560575\",\"time_end\":\"20180327170138\",\"total_fee\":\"1\",\"trade_state\":\"SUCCESS\",\"trade_state_desc\":\"\u652f\u4ed8\u6210\u529f\",\"trade_type\":\"MICROPAY\",\"transaction_id\":\"4200000053201803276624500800\"}}";
		JSONObject json = null;
		try
		{
			json = new JSONObject(string);
			System.out.println("get zt:"+json.get("zt"));
		}catch(JSONException jsone)
		{
		}
	}
}
