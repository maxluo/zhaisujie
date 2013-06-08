package com.ag.zhaisujie;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 *    ValidUtil.java
 *     <p>
 *     验证工具类
 *     Copyright: Copyright(c) 2013 
 *     @author Gavin_Feng
 *     </p>
 * 
 */
public class ValidUtil {

	/**
	 * 验证邮箱地址是否正确
	 * 
	 * @param email
	 * @return
	 */
	public static boolean isEmail(String email) {
		String regex = "^([a-zA-Z0-9]*[-_]?[a-zA-Z0-9]+)*@([a-zA-Z0-9]*[-_]?[a-zA-Z0-9]+)+[\\.][A-Za-z]{2,3}([\\.][A-Za-z]{2})?$";
		return match(regex, email);
	}

	/**
	 * 验证手机号码
	 * 
	 * @param mobiles
	 * @return [0-9]{5,9}
	 */
	public static boolean isMobileNO(String mobiles) {
		String regex="^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$";
		 return match(regex, mobiles);
	}

    /**
     * 是否是日期 格式YYYY-mm-dd
     * 
     * @param value
     * @return 如果是符合格式的字符串,返回 <b>true </b>,否则为 <b>false </b>
     */
    public static boolean isDate(String value)
    {
        // 没加时间验证的YYYY-MM-DD
        // String regex =
        // "^((((1[6-9]|[2-9]\\d)\\d{2})-(0?[13578]|1[02])-(0?[1-9]|[12]\\d|3[01]))|(((1[6-9]|[2-9]\\d)\\d{2})-(0?[13456789]|1[012])-(0?[1-9]|[12]\\d|30))|(((1[6-9]|[2-9]\\d)\\d{2})-0?2-(0?[1-9]|1\\d|2[0-8]))|(((1[6-9]|[2-9]\\d)(0[48]|[2468][048]|[13579][26])|((16|[2468][048]|[3579][26])00))-0?2-29-))$";
        // 加了时间验证的YYYY-MM-DD 00:00:00
        String regex = "^((((1[6-9]|[2-9]\\d)\\d{2})-(0?[13578]|1[02])-(0?[1-9]|[12]\\d|3[01]))|(((1[6-9]|[2-9]\\d)\\d{2})-(0?[13456789]|1[012])-(0?[1-9]|[12]\\d|30))|(((1[6-9]|[2-9]\\d)\\d{2})-0?2-(0?[1-9]|1\\d|2[0-8]))|(((1[6-9]|[2-9]\\d)(0[48]|[2468][048]|[13579][26])|((16|[2468][048]|[3579][26])00))-0?2-29-))$";
        return match(regex, value);

    }
    
    /**
     * @param regex
     *            正则表达式字符串
     * @param str
     *            要匹配的字符串
     * @return 如果str 符合 regex的正则表达式格式,返回true, 否则返回 false;
     */
    private static boolean match(String regex, String str)
    {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
    }
    /**
     * 此方法只为JFormattedTextField设置为hh:mm:ss格式的验证
     * 
     * @param time
     *            时间串
     * @return 返回结果 ;
     */
    public static String validTime(String time)
    {

        if (time.equals("__:__"))
        {
            return "请输入正确的时间,格式为HH:mm";
        } else if (time.contains("_"))
        {
            return "请输入正确的时间,格式为HH:mm";
        } else
        {
            String[] ays = time.split(":");
            try
            {
                int h = Integer.parseInt(ays[0]);
                if (h >= 24)
                {
                    return "小时可输入的范围为:0-23";
                }

                int m = Integer.parseInt(ays[1]);
                if (m >= 60)
                {
                    return "分钟可输入的范围为:0-59";
                }

            } catch (NumberFormatException e)
            {
                return "请输入正确的时间,格式为HH:mm";
            }
        }
        return null;
    }
    
    /**
     * 把数字转成周几显示
     *
     */
    public static String convertWeekShow(int num){
		String str = "";
		switch (num) {
		case 1:
			str="一";
			break;
		case 2:
			str="二";
			break;
		case 3:
			str="三";
			break;
		case 4:
			str="四";
			break;
		case 5:
			str="五";
			break;
		case 6:
			str="六";
			break;
		case 7:
			str="日";
			break;
		default:
			break;
		}
		return str;
    }
    
	public static void main(String[] args) {
		String phone = "13611716465";
		System.out.print(isMobileNO(phone));
	}
}
