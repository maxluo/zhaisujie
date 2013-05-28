package com.ag.zhaisujie;

import java.util.Iterator;
import java.util.Map;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
/**
 *    HttpUtil.java
 *     <p>
 *     调用webservice
 *     Copyright: Copyright(c) 2013 
 *     @author Gavin_Feng
 *     </p>
 * 
 */
public class HttpUtil {
	public static String URL_WEBSERVICE_ENDPOINT="http://test.yunjiazheng.com/api/index.php/app/mobile/ws?=1";
	public static String URL_WEBSERVICE_NameSpace="urn:ApiControllerwsdl";
	public static String URL_WEBSERVICE_GET_CODE="sendMessage";
	public static String URL_WEBSERVICE_LOGIN="login";
	public static String URL_WEBSERVICE_LOGOUT="logout";
	public static String URL_WEBSERVICE_SET_ORDER="setOrder";
	public static String URL_WEBSERVICE_GET_ORDER_DETAIL="getOrderDetail";
	public static String URL_WEBSERVICE_CANCEL_ORDER="cancelOrder";
	public static String URL_WEBSERVICE_PAY_ORDER="payOrder";
	public static String URL_WEBSERVICE_CONFIRM_ORDER="confirmOrder";
	
	private static boolean DEBUG = true;
	
	public static String getCode(String phone){

		String result="";
		 // 指定WebService的命名空间和调用的方法名  
        SoapObject request = new SoapObject(URL_WEBSERVICE_NameSpace, URL_WEBSERVICE_GET_CODE); 
        request.addProperty("mobile", phone);  
        // 生成调用WebService方法的SOAP请求信息,并指定SOAP的版本  
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);  
        envelope.bodyOut = request;  
        envelope.setOutputSoapObject(request);
        HttpTransportSE transport = new HttpTransportSE(URL_WEBSERVICE_ENDPOINT);  
        transport.debug=true;
        try {  
            // 调用WebService  
            transport.call(URL_WEBSERVICE_NameSpace+"#"+URL_WEBSERVICE_GET_CODE, envelope);  
            if (envelope.getResponse() != null) {  
            	result=envelope.getResponse().toString();  
            } 
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        return result;
	}
	
	public static String login(String phone,String pwd){

		String result="";
		 // 指定WebService的命名空间和调用的方法名  
        SoapObject request = new SoapObject(URL_WEBSERVICE_NameSpace, URL_WEBSERVICE_LOGIN); 
        request.addProperty("mobile", phone);  
        request.addProperty("passCode", pwd);  
        // 生成调用WebService方法的SOAP请求信息,并指定SOAP的版本  
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);  
        envelope.bodyOut = request;  
        envelope.setOutputSoapObject(request);
        HttpTransportSE transport = new HttpTransportSE(URL_WEBSERVICE_ENDPOINT);  
        transport.debug=true;
        try {  
            // 调用WebService  
            transport.call(URL_WEBSERVICE_NameSpace+"#"+URL_WEBSERVICE_LOGIN, envelope);  
            if (envelope.getResponse() != null) {  
            	result=envelope.getResponse().toString();  
            } 
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        return result;
	}
	
	public static String logout(String userId, String userPhone) {
	   String result="";
		 // 指定WebService的命名空间和调用的方法名  
       SoapObject request = new SoapObject(URL_WEBSERVICE_NameSpace, URL_WEBSERVICE_LOGOUT); 
       request.addProperty("uid", userId);  
       request.addProperty("username", userPhone);  
       // 生成调用WebService方法的SOAP请求信息,并指定SOAP的版本  
       SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);  
       envelope.bodyOut = request;  
       envelope.setOutputSoapObject(request);
       HttpTransportSE transport = new HttpTransportSE(URL_WEBSERVICE_ENDPOINT);  
       transport.debug=true;
       try {  
           // 调用WebService  
           transport.call(URL_WEBSERVICE_NameSpace+"#"+URL_WEBSERVICE_LOGOUT, envelope);  
           if (envelope.getResponse() != null) {  
           	result=envelope.getResponse().toString();  
           } 
       } catch (Exception e) {  
           e.printStackTrace();  
       }  
       return result;
	}
	
	/**
	 * 调动服务器webservice接口
	 * @param methodName 接口方法名称
	 * @param params 接口参数
	 * @return
	 * @throws Exception
	 */
	public static Object getInfoFromServer(String methodName, Map<String, Object> params) throws Exception {
	  Object result = null;
		 // 指定WebService的命名空间和调用的方法名  
      SoapObject request = new SoapObject(URL_WEBSERVICE_NameSpace, methodName); 
      
      for(Iterator<Map.Entry<String, Object>> it=params.entrySet().iterator(); it.hasNext();) {
    	  Map.Entry<String, Object> m = it.next();
    	  request.addProperty(m.getKey(), m.getValue());  
      }
     
      // 生成调用WebService方法的SOAP请求信息,并指定SOAP的版本  
      SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);  
      envelope.bodyOut = request;  
      envelope.setOutputSoapObject(request);
      HttpTransportSE transport = new HttpTransportSE(URL_WEBSERVICE_ENDPOINT);
      transport.debug = DEBUG;
		// 调用WebService
      transport.call(URL_WEBSERVICE_NameSpace + "#" + methodName, envelope);
      if (envelope.getResponse() != null) {
    	 result = envelope.getResponse();
      }
      return result;
	}
	
}
