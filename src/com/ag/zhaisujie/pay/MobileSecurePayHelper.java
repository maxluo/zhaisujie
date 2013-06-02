package com.ag.zhaisujie.pay;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.ArrayList;
import java.util.List;

import javax.crypto.Cipher;

import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.util.Base64;

import com.ag.zhaisujie.activity.OrderSendActivity;

public class MobileSecurePayHelper {
	Context mContext = null;
    Handler mHandler = null;
    String mUrl = null;
    String mPath = null;
 
    public static String Stream2String(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line = null;
        try {
            while ((line = reader.readLine()) != null)
                sb.append(line);
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }
 
    public static JSONObject string2JSON(String str, String split) {
        JSONObject json = new JSONObject();
        try {
            String[] arrStr = str.split(split);
            for (int i = 0; i < arrStr.length; i++) {
                String[] arrKeyValue = arrStr[i].split("=");
                json.put(arrKeyValue[0],
                        arrStr[i].substring(arrKeyValue[0].length() + 1));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return json;
    }
 
    public static String SendAndWaitResponse(String strReqData, String strUrl) {
        String strResponse = null;
        ArrayList<BasicNameValuePair> pairs = new ArrayList<BasicNameValuePair>();
        pairs.add(new BasicNameValuePair("requestData", strReqData));
 
        HttpURLConnection conn = null;
        UrlEncodedFormEntity p_entity;
        try {
            p_entity = new UrlEncodedFormEntity(pairs, "UTF-8");
            URL url = new URL(strUrl);
            conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(30 * 1000);
            conn.setReadTimeout(30 * 1000);
            conn.setDoOutput(true);
            conn.addRequestProperty("Content-type",
                    "application/x-www-form-urlencoded;charset=UTF-8");
            conn.connect();
 
            OutputStream os = conn.getOutputStream();
            p_entity.writeTo(os);
            os.flush();
            InputStream content = conn.getInputStream();
            strResponse = Stream2String(content);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }
        return strResponse;
    }
 
    public static boolean urlDownloadToFile(Context context, String strurl,
            String path) {
        boolean bRet = false;
        try {
            URL url = new URL(strurl);
            HttpURLConnection conn = null;
            conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(30 * 1000);
            conn.setReadTimeout(30 * 1000);
            conn.setDoInput(true);
            conn.connect();
            InputStream is = conn.getInputStream();
            File file = new File(path);
            file.createNewFile();
            FileOutputStream fos = new FileOutputStream(file);
            byte[] temp = new byte[1024];
            int i = 0;
            while ((i = is.read(temp)) > 0)
                fos.write(temp, 0, i);
            fos.close();
            is.close();
            bRet = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bRet;
    }
 
    public static String RsaEncode(String content, String key) {
        try {
            X509EncodedKeySpec x509 = new X509EncodedKeySpec(Base64.decode(key,
                    Base64.DEFAULT));
            KeyFactory kf = KeyFactory.getInstance("RSA");
            PublicKey pubKey = kf.generatePublic(x509);
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(Cipher.ENCRYPT_MODE, pubKey);
            byte plaintext[] = content.getBytes("UTF-8");
            byte[] output = cipher.doFinal(plaintext);
            return new String(Base64.encode(output, Base64.DEFAULT));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
 
    public static String RsaSign(String content, String privateKey) {
        try {
            PKCS8EncodedKeySpec pkcs8 = new PKCS8EncodedKeySpec(Base64.decode(
                    privateKey, Base64.DEFAULT));
            //KeyFactory kf = KeyFactory.getInstance("RSA", "BC");
            KeyFactory kf = KeyFactory.getInstance("RSA");
            PrivateKey priKey = kf.generatePrivate(pkcs8);
            Signature signature = Signature.getInstance("SHA1WithRSA");
            signature.initSign(priKey);
            signature.update(content.getBytes("UTF-8"));
            byte[] signed = signature.sign();
            return new String(Base64.encode(signed, Base64.DEFAULT));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
 
    public static boolean RsaCheck(String content, String sign, String publicKey) {
        try {
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            byte[] encodedKey = Base64.decode(publicKey, Base64.DEFAULT);
            PublicKey pubKey = keyFactory
                    .generatePublic(new X509EncodedKeySpec(encodedKey));
            Signature signature = Signature.getInstance("SHA1WithRSA");
            signature.initVerify(pubKey);
            signature.update(content.getBytes("UTF-8"));
            boolean bverify = signature.verify(Base64.decode(sign,
                    Base64.DEFAULT));
            return bverify;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
 
    public MobileSecurePayHelper(Context context, Handler handler) {
        mContext = context;
        mHandler = handler;
    }
 
    public boolean detectService() {
        boolean isExist = false;
        List<PackageInfo> pkgList = mContext.getPackageManager()
                .getInstalledPackages(0);
        for (int i = 0; i < pkgList.size(); i++) {
            if (pkgList.get(i).packageName
                    .equalsIgnoreCase("com.alipay.android.app"))
                isExist = true;
        }
        return isExist;
    }
 
    public void downloadAliMSP() {
        JSONObject Resp = null;
        try {
            JSONObject req = new JSONObject();
            req.put("action", "update");
            JSONObject data = new JSONObject();
            data.put("platform", "android");
            data.put("version", "2.2.3");
            data.put("partner", "");
            req.put("data", data);
            Resp = new JSONObject(SendAndWaitResponse(req.toString(),
                    "https://msp.alipay.com/x.htm"));
            mUrl = Resp.getString("updateUrl");
        } catch (JSONException e) {
            e.printStackTrace();
            return;
        }
        new Thread(new Runnable() {
            public void run() {
                mPath = mContext.getCacheDir().getAbsolutePath() + "/temp.apk";
                urlDownloadToFile(mContext, mUrl, mPath);
                Message msg = new Message();
                msg.what = OrderSendActivity.PAY_INSTALL;
                mHandler.sendMessage(msg);
            }
        }).start();
    }
 
    public void installAliMSP() {
        if (mPath == null)
            return;
        try {
            Runtime.getRuntime().exec("chmod 777 " + mPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setDataAndType(Uri.parse("file://" + mPath),
                "application/vnd.android.package-archive");
        mContext.startActivity(intent);
    }
}
