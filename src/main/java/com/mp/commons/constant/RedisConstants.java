package com.mp.commons.constant;

import org.apache.commons.lang3.EnumUtils;


/**
 * redis常量，尽量采用“先定义，后使用”的原则。
 * 
 */
public class RedisConstants {
	/**
	 * Key的类型
	 */
	public enum KeyType {
		string, list, set, zset, hash;
		
		public static KeyType getKeyType(String type){
			return EnumUtils.getEnum(KeyType.class, type);
		}
	}
	
	/**
	 * 以秒为单位的时间常量。先定义，再使用，提高代码可读性
	 */
	public enum TimeInSecond {
		/** 5天 */
		_5_DAYS(5*24*3600),
		
		/** 24小时 */
		_24_HOURS(24*3600),
		
		/** 8小时 */
		_8_HOURS(8*3600),
		
		/** 120分钟 */
		_120_MINS(120*60),
		
		/** 10分钟 */
		_10_MINS(10*60),

		/** 5分钟 */
		_5_MINS(5*60),
		
		/** 无穷大 ≈68年 */
		INFINITE(Integer.MAX_VALUE),
		
		/** 不指定有效期，比如根据不同情况使用不同的有效期的情况、或其他不适用的情况 */
		NA(Integer.MIN_VALUE);
		
		TimeInSecond(int seconds){
			this.seconds = seconds;
		}
		
		public int val() {
			return seconds;
		}

		private int seconds;
	}
	
	/**
	 * Key的前缀：前缀String、类型、有效期（秒） 
	 * 类型如果不指定则默认为string
	 * 有效期如果不指定则默认为5天
	 */
	public enum Prefix {
		/** APP登录的token前缀，value=userId */
		APP_TOKEN("token.to.userid:"),

		/** WEB登录的token前缀，value=userId */
		WEB_TOKEN("web.token.to.userid:", TimeInSecond._5_DAYS),

		/** admin用户登录的token前缀 */
		ADMIN_TOKEN("admin.token.to.userid:", TimeInSecond._5_DAYS),

		/** admin用户登录的token前缀 */
		WWW_TOKEN("www.token.to.userid:", TimeInSecond._5_DAYS),

		/** UserInfo信息 */
		USER_INFO("user.info:", KeyType.hash),

		/** 用户登录相关的信息，APP登录和WEB登录共用 */
		USER_LOGIN_INFO("userLoginInfo:", KeyType.hash, TimeInSecond.INFINITE),

		/** 验证码，Hash类型， 后面跟着cookieId */
		CAPTCHA("photovoltaic.captcha:", KeyType.hash, TimeInSecond._5_MINS),
		
		/** 手机验证码 */
		PHONE_AUTHCODE("identifyCode:", TimeInSecond._10_MINS),

		/** 用户访问记录，防止频繁请求 industrynet.req:<userId>.<method> */
		REQUEST("photovoltaic.req:", TimeInSecond.NA);
		

		/**
		 * 定义了前缀，缺省类型为string、有效期5天
		 */
		Prefix(String id){
			this.id = id;
			this.type = KeyType.string;
			this.ttl = TimeInSecond._5_DAYS;
		}

		/**
		 * 定义了前缀和有效期，缺省类型为string
		 */
		Prefix(String id, TimeInSecond ttl){
			this.id = id;
			this.type = KeyType.string;
			this.ttl = ttl;
		}
		
		/**
		 * 定义了前缀和类型，缺省有效期5天
		 */
		Prefix(String id, KeyType type){
			this.id = id;
			this.type = type;
			this.ttl = TimeInSecond._5_DAYS;
		}
		
		/**
		 * 定义了前缀、类型、和有效期
		 */
		Prefix(String id, KeyType type, TimeInSecond ttl){
			this.id = id;
			this.type = type;
			this.ttl = ttl;
		}

		public String id() {
			return id;
		}
		
		public KeyType type() {
			return type;
		}
		
		public int ttl() {
			return ttl.val();
		}

		@Override
		public String toString() {
			return id;
		}

		private String id;	//前缀字符串
		private KeyType type;	//类型
		private TimeInSecond ttl;	//过期时间（秒）
		
		
		public static Prefix findPrefixById(String prefixId){
			for(Prefix p : Prefix.values()){
				if(p.id().equals(prefixId)){
					return p;
				}
			}
			return null;
		}
	}
	
	/**
	 * 验证码hash中相关的field 
	 */
	public enum CapchaInfo {
		CODE,
		CHECKED;
	}
	

	public enum UserLoginInfo {
		/** APP登录后的token */
		APP_TOKEN("appToken"),
		/** APP最后登录时间  */
		APP_LOGIN_TIME("appLoginTime"),
		/** APP最后登录版本  */
		APP_VERSION("appVersion"),
		/** APP最后登录平台  */
		APP_PLATFORM("appPlatform"),

		/** Web登录后token */
		WEB_TOKEN("webToken"),
		/** WEB最后登录时间  */
		WEB_LOGIN_TIME("webLoginTime"),
		/** admin登录后token**/
		ADMIN_TOKEN("adminToken"),
		/** WEB最后登录时间  */
		ADMIN_LOGIN_TIME("adminLoginTime"),
		/** admin登录后token**/
		WWW_TOKEN("wwwToken"),
		/** WEB最后登录时间  */
		WWW_LOGIN_TIME("wwwLoginTime"),
		/** WEB最后登录版本  */
		WEB_VERSION("webVersion"),
		/** WEB最后登录平台  */
		WEB_PLATFORM("webPlatform");
		
		UserLoginInfo(String id){
			this.id = id;
		}
		
		public String id() {
			return id;
		}

		@Override
		public String toString() {
			return id;
		}
		
		private String id;	//field String
	}
	


	public enum Verifytype {
		/** 注册用户 */
		Regist(0),
		/** 发送验证码到旧绑定手机 */
		SendToOldPhone(1);

		private int index;

		private Verifytype(int index) {
			this.index = index;
		}
		
		public int getIndex() {
			return index;
		}
		
		@Override
		public String toString() {
			return String.valueOf(index);
		}
	}


}
