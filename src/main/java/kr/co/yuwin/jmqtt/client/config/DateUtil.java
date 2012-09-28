package kr.co.yuwin.jmqtt.client.config;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class DateUtil
{
    
    private static boolean isLeapYear(int year)
    {
        return year % 4 == 0 && (year % 100 != 0 || year % 400 == 0);
    }

    private DateUtil(){}

    public static String getCurrentTimestampString()
    {
        return getTimestampString(System.currentTimeMillis());
    }

    public static String getCurrentTimeString()
    {
        return getTimeString(System.currentTimeMillis());
    }

    public static String getCurrentDateString()
    {
        return getDateString(System.currentTimeMillis());
    }

    public static String getTimestampString(long l)
    {
        return getTimestampString(l, true);
    }

    public static String getTimestampString(long l, boolean timezone)
    {
        return getTimestampString(l, timezone, false);
    }
    
    private static SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
	public static String convertBeforeYYYYMMDD(String time, int beforeDay)
    {  	
    	if(time!=null && time.length()==14)
    	{
            int year = Integer.parseInt(time.substring(0, 4));
            int month = Integer.parseInt(time.substring(4, 6)) - 1;
            int day = Integer.parseInt(time.substring(6, 8));
            int hour = Integer.parseInt(time.substring(8, 10));
            int min = Integer.parseInt(time.substring(10, 12));
            int sec = Integer.parseInt(time.substring(12, 14));

            Calendar calendar = Calendar.getInstance();
            calendar.set(year, month, day, hour, min, sec);

            long start = calendar.getTimeInMillis();
            for(int i=0 ; i<beforeDay ; i++) {
            	start -= 86400000; //(60000*60*24) // 1��
            }
            String result = format.format(new Date(start));
        	return result;
    	}
    	else
    		return "";
    }
	
	public static String convertBeforeNMinute(String time, int beforeMinute)
    {  	
    	if(time!=null && time.length()==14)
    	{
            int year = Integer.parseInt(time.substring(0, 4));
            int month = Integer.parseInt(time.substring(4, 6)) - 1;
            int day = Integer.parseInt(time.substring(6, 8));
            int hour = Integer.parseInt(time.substring(8, 10));
            int min = Integer.parseInt(time.substring(10, 12));
            int sec = Integer.parseInt(time.substring(12, 14));

            Calendar calendar = Calendar.getInstance();
            calendar.set(year, month, day, hour, min, sec);

            long start = calendar.getTimeInMillis();
            start -= (60000*beforeMinute); 
            
            String result = format.format(new Date(start));
        	return result;
    	}
    	else
    		return "";
    }
	
	public static long convertYYYYMMDDhhmmssToLong(String time)
    {  	
    	if(time!=null && time.length()==14)
    	{
            int year = Integer.parseInt(time.substring(0, 4));
            int month = Integer.parseInt(time.substring(4, 6)) - 1;
            int day = Integer.parseInt(time.substring(6, 8));
            int hour = Integer.parseInt(time.substring(8, 10));
            int min = Integer.parseInt(time.substring(10, 12));
            int sec = Integer.parseInt(time.substring(12, 14));

            Calendar calendar = Calendar.getInstance();
            calendar.set(year, month, day, hour, min, sec);

            return calendar.getTimeInMillis();
    	} else {
    		return 0L;
    	}
    }

    public static String getTimestampString(long l, boolean timezone, boolean format)
    {
        if(timezone)
            l += timeZoneOffset;
        if(l < 0L)
            return format ? "1970-01-01/00:00:00" : "19700101000000"; //"19700101/000000";
        if(l > accmulated_timemillis_year[229])
            return format ? "9999-12-31/00:00:00" : "99991231000000";
        int year = 9999;
        int mon = 0;
        boolean leap_year = false;
        if(l > accmulated_timemillis_2002)
        {
            for(int y = 2003; y < 2199; y++)
            {
                if(l >= accmulated_timemillis_year[y - 1970])
                    continue;
                year = y;
                break;
            }

        } else
        {
            for(int y = 1970; y < 2200; y++)
            {
                if(l >= accmulated_timemillis_year[y - 1970])
                    continue;
                year = y;
                break;
            }

        }
        if(year > 1970)
            l -= accmulated_timemillis_year[year - 1970 - 1];
        if(isLeapYear(year))
        {
            for(int m = 0; m < 12; m++)
            {
                if(l >= accmulated_timemillis_month_leapyear[m])
                    continue;
                mon = m;
                if(m > 0)
                    l -= accmulated_timemillis_month_leapyear[m - 1];
                break;
            }

        } else
        {
            for(int m = 0; m < 12; m++)
            {
                if(l >= accmulated_timemillis_month[m])
                    continue;
                mon = m;
                if(m > 0)
                    l -= accmulated_timemillis_month[m - 1];
                break;
            }

        }
        int day = (int)(l * 0x31b5d43bL >>> 56);
        l -= (long)day * 0x5265c00L;
        int r = (int)l;
        int hour = 0;
        hour = (int)((long)r * 0x25485f3L >>> 47);
        r -= hour * 0x36ee80;
        int min = 0;
        min = (int)((long)r * 0x45e7b3L >>> 38);
        r -= min * 60000;
        int sec = 0;
        sec = r * 0x10625 >>> 26;
        char buf[] = format ? new char[19] : new char[14];
        if(format)
        {
            int century = year * 0x147af >>> 23;
            buf[0] = DigitTens[century];
            buf[1] = DigitOnes[century];
            int year_r = year - century * 100;
            buf[2] = DigitTens[year_r];
            buf[3] = DigitOnes[year_r];
            buf[4] = '-';
            buf[5] = DigitTens[++mon];
            buf[6] = DigitOnes[mon];
            buf[7] = '-';
            buf[8] = DigitTens[++day];
            buf[9] = DigitOnes[day];
            buf[10] = '/';
            buf[11] = DigitTens[hour];
            buf[12] = DigitOnes[hour];
            buf[13] = ':';
            buf[14] = DigitTens[min];
            buf[15] = DigitOnes[min];
            buf[16] = ':';
            buf[17] = DigitTens[sec];
            buf[18] = DigitOnes[sec];
        } else
        {
            int century = year * 0x147af >>> 23;
            buf[0] = DigitTens[century];
            buf[1] = DigitOnes[century];
            int year_r = year - century * 100;
            buf[2] = DigitTens[year_r];
            buf[3] = DigitOnes[year_r];
            buf[4] = DigitTens[++mon];
            buf[5] = DigitOnes[mon];
            buf[6] = DigitTens[++day];
            buf[7] = DigitOnes[day];
            //buf[8] = '/';
            buf[8] = DigitTens[hour];
            buf[9] = DigitOnes[hour];
            buf[10] = DigitTens[min];
            buf[11] = DigitOnes[min];
            buf[12] = DigitTens[sec];
            buf[13] = DigitOnes[sec];
        }
        return new String(buf);
    }

    public static String getDateString(long l)
    {
        return getDateString(l, true, false);
    }

    public static String getFormatedDateString(long l)
    {
        return getDateString(l, true, true);
    }

    public static String getFormatedDateString(long l, boolean timezone)
    {
        return getDateString(l, timezone, true);
    }

    public static String getDateString(long l, boolean timezone)
    {
        return getDateString(l, timezone, true);
    }

    public static String getDateString(long l, boolean timezone, boolean formated)
    {
        if(timezone)
            l += timeZoneOffset;
        if(l < 0L)
            return formated ? "1970-01-01" : "19700101";
        if(l > accmulated_timemillis_year[229])
            return formated ? "9999-12-31" : "99991231";
        int year = 9999;
        int mon = 0;
        boolean leap_year = false;
        if(l > accmulated_timemillis_2002)
        {
            for(int y = 2003; y < 2199; y++)
            {
                if(l >= accmulated_timemillis_year[y - 1970])
                    continue;
                year = y;
                break;
            }

        } else
        {
            for(int y = 1970; y < 2200; y++)
            {
                if(l >= accmulated_timemillis_year[y - 1970])
                    continue;
                year = y;
                break;
            }

        }
        if(year > 1970)
            l -= accmulated_timemillis_year[year - 1970 - 1];
        if(isLeapYear(year))
        {
            for(int m = 0; m < 12; m++)
            {
                if(l >= accmulated_timemillis_month_leapyear[m])
                    continue;
                mon = m;
                if(m > 0)
                    l -= accmulated_timemillis_month_leapyear[m - 1];
                break;
            }

        } else
        {
            for(int m = 0; m < 12; m++)
            {
                if(l >= accmulated_timemillis_month[m])
                    continue;
                mon = m;
                if(m > 0)
                    l -= accmulated_timemillis_month[m - 1];
                break;
            }

        }
        int day = (int)(l * 0x31b5d43bL >>> 56);
        l -= (long)day * 0x5265c00L;
        char buf[] = formated ? new char[10] : new char[8];
        if(formated)
        {
            int century = year * 0x147af >>> 23;
            buf[0] = DigitTens[century];
            buf[1] = DigitOnes[century];
            int year_r = year - century * 100;
            buf[2] = DigitTens[year_r];
            buf[3] = DigitOnes[year_r];
            buf[4] = '-';
            buf[5] = DigitTens[++mon];
            buf[6] = DigitOnes[mon];
            buf[7] = '-';
            buf[8] = DigitTens[++day];
            buf[9] = DigitOnes[day];
        } else
        {
            int century = year * 0x147af >>> 23;
            buf[0] = DigitTens[century];
            buf[1] = DigitOnes[century];
            int year_r = year - century * 100;
            buf[2] = DigitTens[year_r];
            buf[3] = DigitOnes[year_r];
            buf[4] = DigitTens[++mon];
            buf[5] = DigitOnes[mon];
            buf[6] = DigitTens[++day];
            buf[7] = DigitOnes[day];
        }
        return new String(buf);
    }

    public static String getMonthDayString(long l, boolean timezone, boolean formated)
    {
        if(timezone)
            l += timeZoneOffset;
        if(l < 0L)
            return formated ? "01-01" : "0101";
        if(l > accmulated_timemillis_year[229])
            return formated ? "12-31" : "1231";
        int year = 9999;
        int mon = 0;
        boolean leap_year = false;
        if(l > accmulated_timemillis_2002)
        {
            for(int y = 2003; y < 2199; y++)
            {
                if(l >= accmulated_timemillis_year[y - 1970])
                    continue;
                year = y;
                break;
            }

        } else
        {
            for(int y = 1970; y < 2200; y++)
            {
                if(l >= accmulated_timemillis_year[y - 1970])
                    continue;
                year = y;
                break;
            }

        }
        if(year > 1970)
            l -= accmulated_timemillis_year[year - 1970 - 1];
        if(isLeapYear(year))
        {
            for(int m = 0; m < 12; m++)
            {
                if(l >= accmulated_timemillis_month_leapyear[m])
                    continue;
                mon = m;
                if(m > 0)
                    l -= accmulated_timemillis_month_leapyear[m - 1];
                break;
            }

        } else
        {
            for(int m = 0; m < 12; m++)
            {
                if(l >= accmulated_timemillis_month[m])
                    continue;
                mon = m;
                if(m > 0)
                    l -= accmulated_timemillis_month[m - 1];
                break;
            }

        }
        int day = (int)(l * 0x31b5d43bL >>> 56);
        l -= (long)day * 0x5265c00L;
        char buf[] = formated ? new char[5] : new char[4];
        if(formated)
        {
            buf[0] = DigitTens[++mon];
            buf[1] = DigitOnes[mon];
            buf[2] = '-';
            buf[3] = DigitTens[++day];
            buf[4] = DigitOnes[day];
        } else
        {
            buf[0] = DigitTens[++mon];
            buf[1] = DigitOnes[mon];
            buf[2] = DigitTens[++day];
            buf[3] = DigitOnes[day];
        }
        return new String(buf);
    }

    public static String getTimeString(long l)
    {
        return getTimeString(l, true);
    }

    public static String getTimeString(long l, boolean timezone)
    {
        if(timezone)
            l += timeZoneOffset;
        if(l < 0L)
            return "000000";
        if(l > accmulated_timemillis_year[229])
            return "000000";
        int year = 9999;
        int mon = 0;
        boolean leap_year = false;
        if(l > accmulated_timemillis_2002)
        {
            for(int y = 2003; y < 2199; y++)
            {
                if(l >= accmulated_timemillis_year[y - 1970])
                    continue;
                year = y;
                break;
            }

        } else
        {
            for(int y = 1970; y < 2200; y++)
            {
                if(l >= accmulated_timemillis_year[y - 1970])
                    continue;
                year = y;
                break;
            }

        }
        if(year > 1970)
            l -= accmulated_timemillis_year[year - 1970 - 1];
        if(isLeapYear(year))
        {
            for(int m = 0; m < 12; m++)
            {
                if(l >= accmulated_timemillis_month_leapyear[m])
                    continue;
                mon = m;
                if(m > 0)
                    l -= accmulated_timemillis_month_leapyear[m - 1];
                break;
            }

        } else
        {
            for(int m = 0; m < 12; m++)
            {
                if(l >= accmulated_timemillis_month[m])
                    continue;
                mon = m;
                if(m > 0)
                    l -= accmulated_timemillis_month[m - 1];
                break;
            }

        }
        int day = (int)(l * 0x31b5d43bL >>> 56);
        l -= (long)day * 0x5265c00L;
        int r = (int)l;
        int hour = 0;
        hour = (int)((long)r * 0x25485f3L >>> 47);
        r -= hour * 0x36ee80;
        int min = 0;
        min = (int)((long)r * 0x45e7b3L >>> 38);
        r -= min * 60000;
        int sec = 0;
        sec = r * 0x10625 >>> 26;
        char buf[] = new char[6];
        int century = year * 0x147af >>> 23;
        buf[0] = DigitTens[hour];
        buf[1] = DigitOnes[hour];
        buf[2] = DigitTens[min];
        buf[3] = DigitOnes[min];
        buf[4] = DigitTens[sec];
        buf[5] = DigitOnes[sec];
        return new String(buf);
    }

    public static String getTimeStringHHMM(long l, boolean timezone)
    {
        if(timezone)
            l += timeZoneOffset;
        if(l < 0L)
            return "00:00";
        if(l > accmulated_timemillis_year[229])
            return "00:00";
        int year = 9999;
        int mon = 0;
        boolean leap_year = false;
        if(l > accmulated_timemillis_2002)
        {
            for(int y = 2003; y < 2199; y++)
            {
                if(l >= accmulated_timemillis_year[y - 1970])
                    continue;
                year = y;
                break;
            }

        } else
        {
            for(int y = 1970; y < 2200; y++)
            {
                if(l >= accmulated_timemillis_year[y - 1970])
                    continue;
                year = y;
                break;
            }

        }
        if(year > 1970)
            l -= accmulated_timemillis_year[year - 1970 - 1];
        if(isLeapYear(year))
        {
            for(int m = 0; m < 12; m++)
            {
                if(l >= accmulated_timemillis_month_leapyear[m])
                    continue;
                mon = m;
                if(m > 0)
                    l -= accmulated_timemillis_month_leapyear[m - 1];
                break;
            }

        } else
        {
            for(int m = 0; m < 12; m++)
            {
                if(l >= accmulated_timemillis_month[m])
                    continue;
                mon = m;
                if(m > 0)
                    l -= accmulated_timemillis_month[m - 1];
                break;
            }

        }
        int day = (int)(l * 0x31b5d43bL >>> 56);
        l -= (long)day * 0x5265c00L;
        int r = (int)l;
        int hour = 0;
        hour = (int)((long)r * 0x25485f3L >>> 47);
        r -= hour * 0x36ee80;
        int min = 0;
        min = (int)((long)r * 0x45e7b3L >>> 38);
        r -= min * 60000;
        int sec = 0;
        sec = r * 0x10625 >>> 26;
        char buf[] = new char[5];
        int century = year * 0x147af >>> 23;
        buf[0] = DigitTens[hour];
        buf[1] = DigitOnes[hour];
        buf[2] = ':';
        buf[3] = DigitTens[min];
        buf[4] = DigitOnes[min];
        return new String(buf);
    }

    public static long currentTimeMillis(boolean timezone)
    {
        if(timezone)
            return System.currentTimeMillis() + (long)timeZoneOffset;
        else
            return System.currentTimeMillis();
    }

    public static long currentTimeMillis()
    {
        return System.currentTimeMillis() + (long)timeZoneOffset;
    }

    public static long currentTimeMillis(long time)
    {
        return time + (long)timeZoneOffset;
    }

    public static long currentTimeSeconds()
    {
        return currentTimeMillis() / 1000L;
    }

    public static long currentTimeMinutes()
    {
        return currentTimeSeconds() / 60L;
    }

    public static long currentTimeHours()
    {
        return currentTimeMinutes() / 60L;
    }

    public static long currentTimeDays()
    {
        return currentTimeHours() / 24L;
    }
    
    /*
     * JVM �����ð��� ���� �� �ִ�.
     * �����Ͽ��� ����� System.currentTimeMillis()�� �Ķ���� ������ ���ָ�ȴ�. 
     */
    public static String getIntervalDateString(long start)
    {
    	StringBuffer sb = new StringBuffer();
    	long interval = System.currentTimeMillis() - start;
    	
	    long day =  interval / (1000L*60L*60L*24L);
	    sb.append(day).append("-");

	    long hour = interval / (1000L*60L*60L);
	    hour = (hour-(day*24)); 
	    sb.append(hour).append("-");	
	    
	    long min = interval / (1000L*60L);
	    min = (min-((day*24*60)+(hour*60)));
	    sb.append(min).append("-");	
	    
	    long sec = interval / 1000L;
	    sec = (sec - ((day*24*60*60)+(hour*60*60)+(min*60)));
	    sb.append(sec);
	    
	    return sb.toString();
    }
    
    /*public static long convertYYYYMMDDtoNumberTime(String time)
    {
    	if(time!=null && time.length()>=12)
    	{
	    	Calendar calendar = Calendar.getInstance();
	    	int year = Integer.parseInt(time.substring(0,4));
	    	int month = Integer.parseInt(time.substring(4,6))-1;
	    	int day = Integer.parseInt(time.substring(6,8));
	    	int hour = Integer.parseInt(time.substring(8,10));
	    	int minute = Integer.parseInt(time.substring(10,12));   	
	        calendar.set(year,month,day,hour,minute,0);
	        // calendar.set(year, month, day, hour, minute, second) month�� �Ѵ��� �۴�.
	        //time = time.substring(0,time.length()-3);
	        return calendar.getTimeInMillis();
    	}
    	else
    		return 0L;
    }*/
    
   /* public static String convertYYYYMMDDtoNumberTime(String time, long workingTime)
    {
    	if(time!=null && time.length()==14)
    	{
            int year = Integer.parseInt(time.substring(0, 4));
            int month = Integer.parseInt(time.substring(4, 6)) - 1;
            int day = Integer.parseInt(time.substring(6, 8));
            int hour = Integer.parseInt(time.substring(8, 10));
            int min = Integer.parseInt(time.substring(10, 12));
            int sec = Integer.parseInt(time.substring(12, 14));

            Calendar calendar = Calendar.getInstance();
            calendar.set(year, month, day, hour, min, sec);

            long start = calendar.getTimeInMillis();
            long end = start + workingTime;
            return format.format(new Date(end));
    	}
    	else
    		return "";
    }*/
   
   
    public static void main(String args[]) throws Exception
    {
        int MAX = 0x186a0;
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd/HHmmss");
        new Date();
        getCurrentTimestampString();
        long s = System.currentTimeMillis();
        for(int i = 0; i < MAX; i++)
            df.format(new Date());

        long e = System.currentTimeMillis();
        System.out.println("1 : " + (e - s));
        s = System.currentTimeMillis();
        for(int i = 0; i < MAX; i++)
            getCurrentTimestampString();

        e = System.currentTimeMillis();
        System.out.println("2 : " + (e - s));
        System.out.println(getCurrentTimestampString());
    }

    private static final int SUPPORTED_YEARS = 230;
    private static final int days_month[] = {
        31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 
        30, 31
    };
    private static long accmulated_timemillis_year[];
    private static long accmulated_timemillis_month[];
    private static long accmulated_timemillis_month_leapyear[];
    private static long accmulated_timemillis_2002 = 0L;
    private static int timeZoneOffset = 0;
    static final char DigitTens[] = {
        '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', 
        '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', 
        '2', '2', '2', '2', '2', '2', '2', '2', '2', '2', 
        '3', '3', '3', '3', '3', '3', '3', '3', '3', '3', 
        '4', '4', '4', '4', '4', '4', '4', '4', '4', '4', 
        '5', '5', '5', '5', '5', '5', '5', '5', '5', '5', 
        '6', '6', '6', '6', '6', '6', '6', '6', '6', '6', 
        '7', '7', '7', '7', '7', '7', '7', '7', '7', '7', 
        '8', '8', '8', '8', '8', '8', '8', '8', '8', '8', 
        '9', '9', '9', '9', '9', '9', '9', '9', '9', '9'
    };
    static final char DigitOnes[] = {
        '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 
        '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 
        '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 
        '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 
        '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 
        '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 
        '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 
        '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 
        '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 
        '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'
    };

    static 
    {
        accmulated_timemillis_year = new long[230];
        accmulated_timemillis_month = new long[12];
        accmulated_timemillis_month_leapyear = new long[12];
        long acc_timemillis = 0L;
        for(int i = 0; i < 230; i++)
        {
            long timemillis_this_year = 0L;
            if(isLeapYear(1970 + i))
                timemillis_this_year = 0x75cd78800L;
            else
                timemillis_this_year = 0x757b12c00L;
            acc_timemillis += timemillis_this_year;
            accmulated_timemillis_year[i] = acc_timemillis;
            if(1970 + i == 2002)
                accmulated_timemillis_2002 = accmulated_timemillis_year[i];
        }

        acc_timemillis = 0L;
        for(int i = 0; i < 12; i++)
        {
            acc_timemillis += (long)days_month[i] * 0x5265c00L;
            accmulated_timemillis_month[i] = acc_timemillis;
            if(i < 1)
                accmulated_timemillis_month_leapyear[i] = accmulated_timemillis_month[i];
            else
                accmulated_timemillis_month_leapyear[i] = accmulated_timemillis_month[i] + 0x5265c00L;
        }

        timeZoneOffset = TimeZone.getDefault().getRawOffset();
    }
}
