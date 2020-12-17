/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vn.htcjsc.web.banksim.common;

import java.util.Enumeration;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author TUANPLA
 */
public class HttpUtil {

    public static void debugParam(HttpServletRequest request) {
        Tool.out("--------debugParam--------");
        Enumeration<String> allParam = request.getParameterNames();
        while (allParam.hasMoreElements()) {
            String oneParam = allParam.nextElement();
            Tool.out(oneParam + ":" + request.getParameter(oneParam));
        }
        Tool.out("--------End debugParam--------");
    }

    public static int getInt(HttpServletRequest request, String param) {
        int tem;
        try {
            tem = Integer.parseInt(request.getParameter(param).trim());
        } catch (Exception e) {
            tem = 0;
            Tool.out("getInt  [" + param + "] " + e.getMessage() + " | URI" + getURI(request));
        }
        return tem;
    }

    public static boolean getBoolean(HttpServletRequest request, String param) {
        boolean tem;
        try {
            String str = request.getParameter(param).trim();
            tem = str != null && (str.equals("1") || str.equals("true"));
        } catch (Exception e) {
            tem = false;
            Tool.out("getBoolean " + e.getMessage() + " | URI" + getURI(request));
        }
        return tem;
    }

    public static int getInt(HttpServletRequest request, String param, int defaultVal) {
        int tem;
        try {
            tem = Integer.parseInt(request.getParameter(param).trim());
        } catch (NumberFormatException e) {
            Tool.out("getInt  [" + param + "]- defaultVal:" + e.getMessage() + " | URI" + getURI(request));
            tem = defaultVal;
        }
        if( param == null || param.equals("")) {
            tem = defaultVal;
        } else {
        }
        return tem;
    }

    public static long getLong(HttpServletRequest request, String param) {
        long tem;
        try {
            tem = Long.parseLong(request.getParameter(param).trim());
        } catch (Exception e) {
            Tool.out("getLong  [" + param + "]:" + e.getMessage() + " | URI " + getURI(request));
            tem = 0;
        }
        return tem;
    }

    public static double getDouble(HttpServletRequest request, String param) {
        double tem;
        try {
            tem = Double.parseDouble(request.getParameter(param).trim());
        } catch (Exception e) {
            Tool.out("getDouble [" + param + "]:" + e.getMessage());
            tem = 0;
        }
        return tem;
    }

    public static String getString(HttpServletRequest request, String param) {
        String str;
        try {
            str = request.getParameter(param).trim();
        } catch (Exception e) {
            Tool.out("getstring [" + param + "]:" + e.getMessage());
            str = "";
        }
        return str;
    }

    public static String getString(HttpServletRequest request, String param, String defaultVal) {
        String str;
        try {
            str = request.getParameter(param).trim();
        } catch (Exception e) {
            Tool.out("getstring [" + param + "]:" + e.getMessage());
            str = defaultVal;
        }
        return str;
    }

    public static Float getFloat(HttpServletRequest request, String param) {
        float tem;
        try {
            tem = Float.parseFloat(request.getParameter(param).trim());
        } catch (Exception e) {
            Tool.out("getFloat [" + param + "]:" + e.getMessage());
            tem = 0;
        }
        return tem;
    }

    //--
    public String getCookie(HttpServletRequest request, String c_name) {
        String value = "";
        try {
            Cookie[] cookies = request.getCookies();

            for (Cookie cookie : cookies) {
                String name = cookie.getName();
                if (name.equals(c_name)) {
                    value = cookie.getValue();
                    break;
                }
            }
        } catch (Exception e) {
            Tool.out("Unable to get cookie using");
            e.printStackTrace();
        }
        return value;
    }

    public static String getClientIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    public static String getURI(HttpServletRequest request) {
        String currentURL = null;
        if (request.getAttribute("javax.servlet.forward.request_uri") != null) {
            currentURL = (String) request.getAttribute("javax.servlet.forward.request_uri");
        } else {
            currentURL = request.getRequestURI();
        }
        if (currentURL != null && request.getAttribute("javax.servlet.include.query_string") != null) {
            currentURL += "?" + request.getQueryString();
        }
        return currentURL;
    }

    public static String getFullURL(HttpServletRequest request) {
        String currentURL = null;
        String domain = request.getScheme() + "://" + request.getHeader("host");
        if (request.getAttribute("javax.servlet.forward.request_uri") != null) {
            currentURL = (String) request.getAttribute("javax.servlet.forward.request_uri");
        } else {
            currentURL = request.getRequestURI();
        }
        if (currentURL != null && request.getAttribute("javax.servlet.include.query_string") != null) {
            currentURL += "?" + request.getQueryString();
        }
        return domain + currentURL;
    }

    public static String getPageUrl(HttpServletRequest request) {
        String pageURL = getFullURL(request) + "?";
        Enumeration paraList = request.getParameterNames();
        String data = "";
        while (paraList.hasMoreElements()) {
            String paraName = String.valueOf(paraList.nextElement());
            if (!paraName.equalsIgnoreCase("page") && !paraName.equalsIgnoreCase("submit")) {
                Tool.out("paraName:" + paraName + "=" + request.getParameter(paraName));
                data += paraName + "=" + request.getParameter(paraName) + "&amp;";
            }
        }
//        debug("data before Strim: " + data);
        if (data.endsWith("&amp;")) {
            data = data.substring(0, data.length() - 5);
        }
        if (data.startsWith("&amp;")) {
            data = data.substring(5);
        }
        if (data.startsWith("&")) {
            data = data.substring(1);
        }
//        debug("data affter Trim: " + data);
        return pageURL + data + "&";
    }

}
