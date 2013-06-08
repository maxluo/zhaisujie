package com.ag.zhaisujie;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 *    ValidUtil.java
 *     <p>
 *     ��֤������
 *     Copyright: Copyright(c) 2013 
 *     @author Gavin_Feng
 *     </p>
 * 
 */
public class ValidUtil {

	/**
	 * ��֤�����ַ�Ƿ���ȷ
	 * 
	 * @param email
	 * @return
	 */
	public static boolean isEmail(String email) {
		String regex = "^([a-zA-Z0-9]*[-_]?[a-zA-Z0-9]+)*@([a-zA-Z0-9]*[-_]?[a-zA-Z0-9]+)+[\\.][A-Za-z]{2,3}([\\.][A-Za-z]{2})?$";
		return match(regex, email);
	}

	/**
	 * ��֤�ֻ�����
	 * 
	 * @param mobiles
	 * @return [0-9]{5,9}
	 */
	public static boolean isMobileNO(String mobiles) {
		String regex="^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$";
		 return match(regex, mobiles);
	}

    /**
     * �Ƿ������� ��ʽYYYY-mm-dd
     * 
     * @param value
     * @return ����Ƿ��ϸ�ʽ���ַ���,���� <b>true </b>,����Ϊ <b>false </b>
     */
    public static boolean isDate(String value)
    {
        // û��ʱ����֤��YYYY-MM-DD
        // String regex =
        // "^((((1[6-9]|[2-9]\\d)\\d{2})-(0?[13578]|1[02])-(0?[1-9]|[12]\\d|3[01]))|(((1[6-9]|[2-9]\\d)\\d{2})-(0?[13456789]|1[012])-(0?[1-9]|[12]\\d|30))|(((1[6-9]|[2-9]\\d)\\d{2})-0?2-(0?[1-9]|1\\d|2[0-8]))|(((1[6-9]|[2-9]\\d)(0[48]|[2468][048]|[13579][26])|((16|[2468][048]|[3579][26])00))-0?2-29-))$";
        // ����ʱ����֤��YYYY-MM-DD 00:00:00
        String regex = "^((((1[6-9]|[2-9]\\d)\\d{2})-(0?[13578]|1[02])-(0?[1-9]|[12]\\d|3[01]))|(((1[6-9]|[2-9]\\d)\\d{2})-(0?[13456789]|1[012])-(0?[1-9]|[12]\\d|30))|(((1[6-9]|[2-9]\\d)\\d{2})-0?2-(0?[1-9]|1\\d|2[0-8]))|(((1[6-9]|[2-9]\\d)(0[48]|[2468][048]|[13579][26])|((16|[2468][048]|[3579][26])00))-0?2-29-))$";
        return match(regex, value);

    }
    
    /**
     * @param regex
     *            ������ʽ�ַ���
     * @param str
     *            Ҫƥ����ַ���
     * @return ���str ���� regex��������ʽ��ʽ,����true, ���򷵻� false;
     */
    private static boolean match(String regex, String str)
    {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
    }
    /**
     * �˷���ֻΪJFormattedTextField����Ϊhh:mm:ss��ʽ����֤
     * 
     * @param time
     *            ʱ�䴮
     * @return ���ؽ�� ;
     */
    public static String validTime(String time)
    {

        if (time.equals("__:__"))
        {
            return "��������ȷ��ʱ��,��ʽΪHH:mm";
        } else if (time.contains("_"))
        {
            return "��������ȷ��ʱ��,��ʽΪHH:mm";
        } else
        {
            String[] ays = time.split(":");
            try
            {
                int h = Integer.parseInt(ays[0]);
                if (h >= 24)
                {
                    return "Сʱ������ķ�ΧΪ:0-23";
                }

                int m = Integer.parseInt(ays[1]);
                if (m >= 60)
                {
                    return "���ӿ�����ķ�ΧΪ:0-59";
                }

            } catch (NumberFormatException e)
            {
                return "��������ȷ��ʱ��,��ʽΪHH:mm";
            }
        }
        return null;
    }
    
    /**
     * ������ת���ܼ���ʾ
     *
     */
    public static String convertWeekShow(int num){
		String str = "";
		switch (num) {
		case 1:
			str="һ";
			break;
		case 2:
			str="��";
			break;
		case 3:
			str="��";
			break;
		case 4:
			str="��";
			break;
		case 5:
			str="��";
			break;
		case 6:
			str="��";
			break;
		case 7:
			str="��";
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
