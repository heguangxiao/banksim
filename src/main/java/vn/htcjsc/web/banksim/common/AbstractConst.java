/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vn.htcjsc.web.banksim.common;

import java.util.HashMap;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.http.HttpHeaders;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import vn.htcjsc.web.banksim.cache.SelfExpiringHashMap;
import vn.htcjsc.web.banksim.cache.SelfExpiringMap;
import vn.htcjsc.web.banksim.model.ext.ResponResult;
import vn.htcjsc.web.banksim.service.SysAccountService;

/**
 *
 * @author Private
 */
public abstract class AbstractConst {

    public static final HashMap<String, String> LANG = new HashMap<>();
    protected final String TITLE = "title";
    protected final String GUI_INDEX_PAGE = "redirect:/index";
    protected static final String APPLICATION_JSON_UTF8 = "application/json;charset=UTF-8";
    protected static final String TEXT_HTML_UTF8 = "text/html;charset=UTF-8";
    protected static final String APPLICATION_XML_UTF8 = "application/xml;charset=UTF-8";
    protected static final String RESP_NAME = ResponResult.NAME;
    protected static final SelfExpiringMap<String, String> EXPIRE_MAP = new SelfExpiringHashMap<>();
    protected static final int MAX_LIFE_KEY = 5 * 1000;
    protected static final String verifykey = "verifykey";
    //--
    @Autowired
    protected SysAccountService accService;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        StringTrimmerEditor stringtrimmer = new StringTrimmerEditor(true);
        binder.registerCustomEditor(String.class, stringtrimmer);
//        //The date format to parse or output your dates
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
////        simpleDateFormat.setLenient(false);
//        //Create a new CustomDateEditor
//        CustomDateEditor editor = new CustomDateEditor(simpleDateFormat, true);
//        //Register it as custom editor for the Date type
//        binder.registerCustomEditor(Date.class, editor);
//        binder.registerCustomEditor(Timestamp.class, editor);
    }

    public enum ACTION {
        VIEW,
        VIEWFULL,
        GETDATA,
        CREATE,
        UPDATE,
        DEL,
        RECYCLE,
        RESTORE,
        UPLOAD,
        DOWNLOAD,
        RELEASE,
        PRINT,
        IMPORT,
        EXPORT,
        CONFROLE,
        CONFIG,
        LOGIN,
        LOGOUT,//--
        TOPUP, // phong them
        INVOICE, // phong them
//        ADDACC, // phong them
//        VIEWACC, // phong them
        ;

        public static ACTION getTypeByname(String name) {
            ACTION result = null;
            for (ACTION one : ACTION.values()) {
                if (!Tool.checkNull(name) && one.name().equals(name)) {
                    result = one;
                    break;
                }
            }
            return result;
        }
    }

    public enum RESULT {
        FAIL(0),
        SUCCESS(1),
        NO_LOGIN(-1),
        NO_RIGHT(3),;
        public int val;

        private RESULT(int val) {
            this.val = val;
        }

        public static RESULT getResultByname(String name) {
            RESULT result = null;
            for (RESULT one : RESULT.values()) {
                if (!Tool.checkNull(name) && one.name().equals(name)) {
                    result = one;
                    break;
                }
            }
            return result;
        }
    }

    protected String getUniqueKey() {
        String uniqueID = UUID.randomUUID().toString();
        return uniqueID;
    }

    protected String getReferrer(HttpServletRequest request) {
        return request.getHeader(HttpHeaders.REFERER);
    }

    protected void pushKey(HttpServletRequest request, ModelMap model) {
        String key = getUniqueKey();
        EXPIRE_MAP.put(key, HttpUtil.getURI(request), MAX_LIFE_KEY);
        model.put(verifykey, key);
    }
}
