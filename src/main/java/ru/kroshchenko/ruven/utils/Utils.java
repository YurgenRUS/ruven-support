package ru.kroshchenko.ruven.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {

    static final Logger logger = LoggerFactory.getLogger(ClassNameUtil.getCurrentClassName());

    private static final DecimalFormat df = new DecimalFormat();
    private static SimpleDateFormat exportDateFormatterWithoutTime = new SimpleDateFormat("MM/dd/yyyy");
    private static SimpleDateFormat exportDateFormatterWithTime = new SimpleDateFormat("MM/dd/yyyy HH:mm");
    private static SimpleDateFormat exportDateFormatterTimeOnly = new SimpleDateFormat("HH:mm");

    static {
        df.setMaximumFractionDigits(2);
        df.setMinimumFractionDigits(0);
        df.setGroupingUsed(false);
    }

    public static String decimalToString(BigDecimal value) {
        return df.format(value);
    }

    public enum TimeFormat {
        WITH_TIME,
        WITHOUT_TIME,
        TIME_ONLY
    }

    public static String formatDateForExport(Date date, TimeFormat format){
        switch (format) {
            case WITH_TIME:
                return exportDateFormatterWithTime.format(date);
            case WITHOUT_TIME:
                return exportDateFormatterWithoutTime.format(date);
            case TIME_ONLY:
                return exportDateFormatterTimeOnly.format(date);
        }
        return null;
    }

    public static String formatDate(Date date) {
        String dateText = "NA";
        if (date == null)
            return dateText;

        SimpleDateFormat formatter = new SimpleDateFormat("dd MMMM, yyyy", Locale.US);
        dateText = formatter.format(date);
        return dateText;
    }

    public static String formatDbDate(Date date) {
        String dateText = "NA";
        if (date == null)
            return dateText;

        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy", Locale.US);
        dateText = formatter.format(date);
        return dateText;
    }

    public static Date parseDate(String sDate) {
        if (sDate == null || sDate.isEmpty())
            return null;
        else {
            DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT, new Locale(System.getProperty("user.language")));
            try {
                return df.parse(sDate);
            } catch (ParseException e) {
                // TODO Auto-generated catch block
                logger.error(e.getMessage(), e);
                return null;
            }
        }
    }

    public static String formatBoolean(boolean val) {
        return (val) ? "yes" : "no";
    }

    public static String formatDouble(Double value) {
        String defaultValue = "0.00";
        if (value == null)
            return defaultValue;
        else
            return String.format(Locale.US, "%1$,.2f", value);
    }

    public static String Unnull(String txt) {
        if (txt == null)
            return "";
        else
            return txt.trim();
    }

    public static Long UnnullLng(String txt) {
        if (txt == null)
            return null;
        else {
            if (txt.trim().equals(""))
                return null;
            else {
                try {
                    return new Long(txt.trim());
                } catch (Exception exc) {
                    return 0L;
                }
            }
        }
    }

    public static Boolean UnnullBool(String txt) {
        if (txt == null)
            return false;
        else {
            if (txt.trim().equals(""))
                return false;
            else {
                try {
                    return new Boolean(txt.trim());
                } catch (Exception exc) {
                    return false;
                }
            }
        }
    }

    public static ArrayList<String> tokenizeList(String metaname, String delim) {
        StringTokenizer tok = new StringTokenizer(Unnull(metaname), delim);
        ArrayList<String> list = new ArrayList<String>();

        while (tok.hasMoreTokens()) {
            String token = tok.nextToken().trim();
            list.add(token);
        }

        return list;
    }

    public static String xmlEncode(String in) {
        if (in == null)
            return null;

        java.lang.StringBuffer sb = new java.lang.StringBuffer(1000);

        for (int i = 0; i < in.length(); i++) {
            char currChar = in.charAt(i);
            switch (currChar) {
                case '&':
                    sb.append("&amp;");
                    break;

                case '\'':
                    sb.append("&apos;");
                    break;

                case '"':
                    sb.append("&quot;");
                    break;

                case '<':
                    sb.append("&lt;");
                    break;

                case '>':
                    sb.append("&gt;");
                    break;

                case (char) 11:
                    sb.append((char) 13 + (char) 10);
                    break;

                default:
                    sb.append(currChar);
                    break;
            }
        }

        return sb.toString();
    }

    public static String arrayToString(ArrayList<String> array, String delim) {
        String arrayString = "";

        if (array == null) return "";

        for (int i = 0; i < (array.size() - 1); i++)
            arrayString = arrayString + array.get(i) + delim;

        if (array.size() > 0)
            arrayString = arrayString + array.get(array.size() - 1);

        return arrayString;
    }

    public static String arrayToInClause(ArrayList<String> array, String delim) {
        String arrayString = "";

        if (array == null)
            return "";

        for (int i = 0; i < (array.size() - 1); i++)
            arrayString = arrayString + "'" + array.get(i) + "'" + delim;

        if (array.size() > 0)
            arrayString = arrayString + "'" + array.get(array.size() - 1) + "'";

        return arrayString;
    }


    public static boolean columnExists(ResultSet rs, String columnName) {
        try {
            ResultSetMetaData meta = rs.getMetaData();
            int colCount = meta.getColumnCount();
            for (int i = 1; i < colCount + 1; i++) {
                if (meta.getColumnName(i).equals(columnName))
                    return true;
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }

        return false;
    }

    public static ArrayList<String> getResultsetColumns(ResultSet rs) {
        ArrayList<String> columns = new ArrayList<String>();

        try {
            ResultSetMetaData meta = rs.getMetaData();
            int colCount = meta.getColumnCount();
            for (int i = 1; i < colCount + 1; i++) {
                columns.add(meta.getColumnName(i));
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }

        return columns;
    }

    public static String getContents(String url, String encodeType) throws IOException {
        StringBuilder builder = new StringBuilder();
        URL u = new URL(url);
        BufferedReader theHTML = new BufferedReader(new InputStreamReader(u.openStream(), encodeType));
        String thisLine;
        while ((thisLine = theHTML.readLine()) != null) {
            builder.append(thisLine).append("\n");
        }
        return builder.toString();
    }

    public static Calendar stringToCalendar(String dateTime) {
        if (dateTime == null || dateTime.trim().length() == 0) {
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        try {
            calendar.setTime(sdf.parse(dateTime));
            return calendar;
        } catch (ParseException e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    public static Date stringToDate(String dateTime) {
        if (dateTime == null || dateTime.trim().length() == 0) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        try {
            return sdf.parse(dateTime);
        } catch (ParseException e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    public static ArrayList<String> enumNamesAsList(Enum[] values) {
        ArrayList<String> result = new ArrayList<>();
        for (Enum value : values) {
            result.add(value.name());
        }
        return result;
    }

    public static Calendar timestampToCalendar(Timestamp lastUpdateAsString) {
        Calendar instance = Calendar.getInstance();
        instance.setTimeInMillis(lastUpdateAsString.getTime());
        return instance;
    }

    /**
     * @return default packaging time for a MFFile object
     */
    public static Date defaultPackingDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 11);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        return calendar.getTime();
    }

    public static String generateUUID() {
        return UUID.randomUUID().toString();
    }

    public static Integer getParameterAsInteger(HttpServletRequest req, String name) {
        return getParameterAsInteger(req, name, 0);
    }

    public static Integer getParameterAsInteger(HttpServletRequest req, String name, Integer defaultValue) {
        try {
            return Integer.parseInt(req.getParameter(name));
        } catch (Exception e) {
            return defaultValue;
        }
    }

    public static Double getParameterAsDouble(HttpServletRequest req, String name) {
        return getParameterAsDouble(req, name, 0d);
    }

    public static Double getParameterAsDouble(HttpServletRequest req, String name, Double defaultValue) {
        try {
            return Double.parseDouble(req.getParameter(name));
        } catch (Exception e) {
            return defaultValue;
        }
    }

    public static BigDecimal getParameterAsBigDecimal(HttpServletRequest req, String name) {
        return getParameterAsBigDecimal(req, name, new BigDecimal(0));
    }

    public static BigDecimal getParameterAsBigDecimal(HttpServletRequest req, String volume, BigDecimal defaultValue) {
        try {
            return new BigDecimal(req.getParameter(volume));
        } catch (Exception e) {
            return defaultValue;
        }
    }

    public static String getString(Object source) {
        return getString(source, null);
    }

    public static String getString(Object source, String defaultValue) {
        if (source == null) {
            if (defaultValue == null) {
                return "";
            }
            return defaultValue;
        }
        return source.toString();
    }

    public static Integer asInteger(Object u) {
        try {
            if (u != null) {
                String result = u.toString().replaceAll("[^0-9]", "");
                return Integer.parseInt(result);
            }
        } catch (Exception ignored) {
        }
        return null;
    }

    public static String prepareTemplate(String template) {
//        Change $...$ syntax to <...> and replace dots
        if (template == null) {
            return "";
        }
        Pattern pattern = Pattern.compile("\\$(.*?)\\$");
        Matcher matcher = pattern.matcher(template);
        while (matcher.find()) {
            String match = matcher.group();
            template = template.replace(match, match.replace(".", "_"));
        }
        return template.replaceAll("\\$(.*?)\\$", "<$1>");
    }

    public static String getMd5(String msg) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(msg.getBytes());
            byte byteData[] = md.digest();
            StringBuilder sb = new StringBuilder();
            for (byte aByteData : byteData) {
                sb.append(Integer.toString((aByteData & 0xff) + 0x100, 16).substring(1));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
    }
}