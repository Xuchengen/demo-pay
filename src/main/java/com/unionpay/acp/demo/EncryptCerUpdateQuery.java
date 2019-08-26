package com.unionpay.acp.demo;

import java.util.HashMap;
import java.util.Map;

import com.unionpay.acp.sdk.AcpService;
import com.unionpay.acp.sdk.LogUtil;
import com.unionpay.acp.sdk.SDKConfig;

/**
 * 
 * 银联加密公钥更新查询(只适用于使用RSA证书加密的方式<即signMethod=01>，其他signMethod=11，12密钥加密用不到此交易)
 * 商户定期（1天1次）向银联全渠道系统发起获取加密公钥信息交易.
 * 本交易成功后会自动替换配置文件acp_sdk.properties文件中acpsdk.encryptCert.path指定的文件，可用不用手工替换
 * 声明：以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己需要，按照技术文档编写。该代码仅供参考，不提供编码，性能，规范性等方面的保障<br>
 */
public class EncryptCerUpdateQuery {

	public static void main(String[] args) {
		//加载classpath下的acp_sdk.properties文件内容
		SDKConfig.getConfig().loadPropertiesFromSrc();
		
		Map<String, String> contentData = new HashMap<String, String>();
		contentData.put("version", DemoBase.version);                  		     //版本号
		contentData.put("encoding", DemoBase.encoding);            		 //字符集编码 可以使用UTF-8,GBK两种方式
		contentData.put("signMethod", SDKConfig.getConfig().getSignMethod());    //签名方法  01:RSA证书方式  11：支持散列方式验证SHA-256 12：支持散列方式验证SM3
		contentData.put("txnType", "95");                              			 //交易类型 95-银联加密公钥更新查询
		contentData.put("txnSubType", "00");                           			 //交易子类型  默认00
		contentData.put("bizType", "000000");                          			 //业务类型  默认
		contentData.put("channelType", "07");                          			 //渠道类型
		
		contentData.put("certType", "01");							   			 //01：敏感信息加密公钥(只有01可用)
		contentData.put("merId", "777290058110048");                   			 //商户号码（商户号码777290058110097仅做为测试调通交易使用，该商户号配置了需要对敏感信息加密）测试时请改成自己申请的商户号，【自己注册的测试777开头的商户号不支持代收产品】
		contentData.put("accessType", "0");                            			 //接入类型，商户接入固定填0，不需修改	
		contentData.put("orderId", DemoBase.getOrderId());             			 //商户订单号，8-40位数字字母，不能含“-”或“_”，可以自行定制规则	
		contentData.put("txnTime", DemoBase.getCurrentTime());         		     //订单发送时间，格式为yyyyMMddHHmmss，必须取当前时间，否则会报txnTime无效                         //账号类型
		
		Map<String, String> reqData = AcpService.sign(contentData,DemoBase.encoding);			   //报文中certId,signature的值是在signData方法中获取并自动赋值的，只要证书配置正确即可。
		String requestBackUrl = SDKConfig.getConfig().getBackRequestUrl();				 			   //交易请求url从配置文件读取对应属性文件acp_sdk.properties中的 acpsdk.backTransUrl
		Map<String, String> rspData = AcpService.post(reqData,requestBackUrl,DemoBase.encoding);  //发送请求报文并接受同步应答（默认连接超时时间30秒，读取返回结果超时时间30秒）;这里调用signData之后，调用submitUrl之前不能对submitFromData中的键值对做任何修改，如果修改会导致验签不通过

		if(!rspData.isEmpty()){
			if(AcpService.validate(rspData, DemoBase.encoding)){
				LogUtil.writeLog("验证签名成功");
				String respCode = rspData.get("respCode") ;
				if(("00").equals(respCode)){
					int resultCode = AcpService.updateEncryptCert(rspData,"UTF-8");
					if (resultCode == 1) {
						LogUtil.writeLog("加密公钥更新成功");
					} else if (resultCode == 0) {
						LogUtil.writeLog("加密公钥无更新");
					} else {
						LogUtil.writeLog("加密公钥更新失败");
					}
					
				}else{
					//其他应答码为失败请排查原因
					//TODO
				}
			}else{
				LogUtil.writeErrorLog("验证签名失败");
				//TODO 检查验证签名失败的原因
			}
		}else{
			//未返回正确的http状态
			LogUtil.writeErrorLog("未获取到返回报文或返回http状态码非200");
		}
	}
}
