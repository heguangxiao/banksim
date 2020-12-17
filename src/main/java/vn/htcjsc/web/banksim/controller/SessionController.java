/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vn.htcjsc.web.banksim.controller;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import vn.htcjsc.web.banksim.common.HttpUtil;
import vn.htcjsc.web.banksim.config.MyConfig;
import vn.htcjsc.web.banksim.config.MyContext;
import vn.htcjsc.web.banksim.model.SysAccount;
import vn.htcjsc.web.banksim.model.UserActionLog;
import vn.htcjsc.web.banksim.model.ext.AngularModel;
import vn.htcjsc.web.banksim.model.ext.ResponResult;

/**
 *
 * @author Private
 */
@Controller
@RequestMapping("/admin")
public class SessionController extends AbstractBackEnConst {

    private final String TABLE = "ACCOUNT";
    
    @InitBinder
    @Override
    public void initBinder(WebDataBinder binder) {
        StringTrimmerEditor stringtrimmer = new StringTrimmerEditor(true);
        binder.registerCustomEditor(String.class, stringtrimmer);
    }

    @GetMapping({"/index", "/"})
    public String index(ModelMap model, HttpServletRequest request) {
        model.put(TITLE, LANG.get("generic.home"));
        return "index";
    }

    @GetMapping("/sessionExpire")
    public ResponseEntity<AngularModel<SysAccount>> sessionExpire(HttpServletRequest request) {
        AngularModel<SysAccount> ngModel = new AngularModel<>();
        ngModel.setResult(new ResponResult(RESULT.NO_LOGIN, "Session Expire..."));
        return new ResponseEntity<>(ngModel, HttpStatus.OK);
    }

    @GetMapping("/login")
    public String login(HttpServletRequest request, ModelMap model) {
        SysAccount account = accService.getSysAccountLogin(request);
        model.put(TITLE, LANG.get("generic.login"));
        if (account != null) {
            return ADMIN_INDEX_PAGE;
        }
        return "login";
    }

    @PostMapping("/login")
    public String loginProcess(ModelMap model, HttpServletRequest request) {
        String username = HttpUtil.getString(request, "username");
        String password = HttpUtil.getString(request, "password");
        HttpSession session = request.getSession(false);
        SysAccount account = accService.checkLoginDB(username, password);
        if (account != null) {
            if (account.getStatus() == MyConfig.STATUS.LOCK.val || account.getStatus() == MyConfig.STATUS.UNACTIVE.val) {
                ResponResult result = new UserActionLog(TABLE, 0, ACTION.LOGIN, RESULT.FAIL, "Tài khoản của bạn bị khóa hoặc chưa active, vui lòng liên hệ quản trị!:user=" + username + "|p=" + password).logAction(request);
                model.addAttribute("error", "Tài khoản của bạn bị khóa hoặc chưa active, vui lòng liên hệ quản trị!");
                return "login";
            }
            session.setAttribute(MyConfig.USER_SESSION_NAME, account);
            session.setAttribute("accService", accService);
            MyContext.putSessionOnline(username, session);
            ResponResult result = new UserActionLog(TABLE, account.getId(), ACTION.LOGIN, RESULT.SUCCESS, "Đăng nhập thành công").logAction(request);
            return ADMIN_INDEX_PAGE;
        } else {
            ResponResult result = new UserActionLog(TABLE, 0, ACTION.LOGIN, RESULT.NO_RIGHT, "Đăng nhập thất bại vui lòng kiểm tra lại!:user=" + username + "|p=" + password).logAction(request);
            model.addAttribute("error", "Đăng nhập thất bại vui lòng kiểm tra lại! ");
            return "login";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        SysAccount acc = accService.getSysAccountLogin(request);
        if (acc != null) {
            new UserActionLog(TABLE, acc.getId(), ACTION.LOGIN, RESULT.SUCCESS, "Đăng xuất thành công").logAction(request);
            MyContext.logOutSession(acc.getUsername());
        }
        if (session != null) {
            session.removeAttribute(MyConfig.USER_SESSION_NAME);
            session.invalidate();
        }
        return "redirect:/admin/login";
    }

    @Override
    public String list(ModelMap modelMap, HttpServletRequest request, RedirectAttributes redrAtt) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ResponseEntity getData(HttpServletRequest request) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String createView(ModelMap model, HttpServletRequest request, RedirectAttributes redrAtt) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String createProcess(ModelMap model, HttpServletRequest request, RedirectAttributes redrAtt) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String editView(ModelMap model, HttpServletRequest request, RedirectAttributes redrAtt) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String editProcess(ModelMap model, HttpServletRequest request, RedirectAttributes redrAtt) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ResponseEntity<AngularModel<ResponResult>> delete(HttpServletRequest request) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
