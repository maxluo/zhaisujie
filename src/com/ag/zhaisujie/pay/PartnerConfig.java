package com.ag.zhaisujie.pay;

public class PartnerConfig {
	// 合作商户ID。用签约支付宝账号登录ms.alipay.com后，在账户信息页面获取。
	public static final String PARTNER = "2088701951891021";
	// 商户收款的支付宝账号
	public static final String SELLER = "renwutao@gmail.com";
	// 商户（RSA）私钥
	public static final String RSA_PRIVATE = "MIICXQIBAAKBgQDCFnb5htECrSyDzRSDzQC+8ltBu/y+8ijJmg1MhVogc3uynrBekUNYhofYDsuk+AMCUzPBoqE1G8hFl25t12N6AAuJAtxnRXsjg9WmMcyqkBn3jqtrz+3Fyc59odf7aeCsCCKED87qTF/QzfDH1iZBX4IfZC7tjbK/gC0+eoBzSwIDAQABAoGBAIUNH2x5hkj+bFjCOwELhSphPDv44hAIM/vYVquRczJx2Gefr6p0jKVgPDnaErK5rl5mqUFh+n9M12MVp4c3M6JZugqcRjsQK2+rGD7CSbjGSO3jWzEmrAML2U2fKSAJ6a6f2P6b4mcUz7VsA9tQ3H4jjL5IJRMpJej5/7RUljvBAkEA7QfQmE/E4U8UYhiMqzrwdDtrJnDzIZovEfVx3zJTwRPi35XQACHSmS/yBtFlKsifDukImUib5cdq2NVoLvvRawJBANGe2xZpYMlSj/O/4Zhvk+WrnhkkwMBwoMax9yiw0QE7GTsyMoPPSYpQw6OiDw591wLL3ysE+pfIIHzgcqxB/aECQHFxWg5rryi+dlz0ru6MyOR4Q7IkQNkGOBz6QcO3DIrN/7lOtDw789VJMya8NRUi661xLqJeM+7nYGXizXgfIX0CQQC7J3rykplQhj9yYII6CHzi1Qwt5MuXxbc2T9SdZujkkXBgktvZJWzCaoOSCHIq/R3IKGATV7mpZIvlErwvoiehAkAdGF1f1lccLoUTkAiP3R6gsbPAPnMLX5nsH5aWCwcbCWiLL4qHVxypI6XcpgMVP3793zsF0KKq5yJ0bgxcLy5W";
	// 支付宝（RSA）公钥 用签约支付宝账号登录ms.alipay.com后，在密钥管理页面获取。
	public static final String RSA_ALIPAY_PUBLIC = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCh7YUKWL4M5th/X9ZLZbCu8oISeVEZ522nMFf8 PT7qmfw1XoXuStg9suZOn6Np3C6Hyddatt/8fysSQyWaPwzSoljD1VC6xsVgsTSMWy1JVxrrhxWA ZY+T4fzOTcwbbPkE/nU5/+B+L2jWbu3/aotYWrremMjlH+PUVP5kwFuv8wIDAQAB";
	// 支付宝安全支付服务apk的名称，必须与assets目录下的apk名称一致
	//public static final String ALIPAY_PLUGIN_NAME = "alipay_plugin_20120428msp.apk";

}
