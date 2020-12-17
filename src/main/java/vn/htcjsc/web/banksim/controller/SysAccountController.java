/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vn.htcjsc.web.banksim.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import vn.htcjsc.web.banksim.common.HttpUtil;
import vn.htcjsc.web.banksim.common.Tool;
import vn.htcjsc.web.banksim.config.MyConfig;
import vn.htcjsc.web.banksim.model.SysAccount;
import vn.htcjsc.web.banksim.model.UserActionLog;
import vn.htcjsc.web.banksim.model.ext.AngularModel;
import vn.htcjsc.web.banksim.model.ext.ResponResult;
import vn.htcjsc.web.banksim.service.ExcelBuilder;

/**
 *
 * @author Private
 */
@Controller
@RequestMapping("/admin/sys-account")
public class SysAccountController extends AbstractBackEnConst {

    private final String REDIRECT_VIEW = "redirect:/admin/sys-account/list";
    private final String TABLE = "ACCOUNTS";
    private final String MODEL_NAME = "sysAcc";

    @GetMapping("/list")
    @Override
    public String list(ModelMap model, HttpServletRequest request, RedirectAttributes redrAtt) {
        if (accService.checkAccess(request)) {
            model.put(TITLE, LANG.get("sysAccount.title.list"));
            SysAccount acc = accService.getSysAccountLogin(request);
            model.addAttribute(MODEL_NAME, acc);
            return "sys-account";
        } else {
            ResponResult result = new UserActionLog(TABLE, 0, ACTION.VIEW, RESULT.NO_RIGHT, "Bạn không có quyền truy cập Quản lý tài khoản hệ thống").logAction(request);
            redrAtt.addFlashAttribute(RESP_NAME, result);
            return ADMIN_INDEX_PAGE;
        }

    }

    @PostMapping("/rest/data")
    @Override
    public ResponseEntity<AngularModel<SysAccount>> getData(HttpServletRequest request) {
        AngularModel<SysAccount> ngModel = new AngularModel<>();
        if (accService.checkLogin(request)) {
            String key = HttpUtil.getString(request, "key");
            String phone = HttpUtil.getString(request, "phone");
            String email = HttpUtil.getString(request, "email");
            int maxRow = HttpUtil.getInt(request, "maxRow", MyConfig.ADMIN_MAX_ROW);
            int crPage = HttpUtil.getInt(request, "crPage", 1);
            int status = HttpUtil.getInt(request, "status", MyConfig.STATUS.ALL.val);
            
            ArrayList<SysAccount> listData = accService.view(crPage, maxRow, key, phone, email, status);
            int totalRow = accService.count(key, phone, email, status);
            
            ngModel.setDatas(listData);
            ngModel.setTotalRow(totalRow);
            HashMap<String, Boolean> roles = accService.checkRight(request);
            ngModel.setRoles(roles);
            if (listData == null || listData.isEmpty()) {
                ngModel.setResult(new ResponResult(RESULT.FAIL, "Danh sách tài khoản trống"));
            } else {
                ngModel.setResult(new ResponResult(RESULT.SUCCESS, ""));
            }
        } else {
            ResponResult result = new UserActionLog(TABLE, 0, ACTION.VIEW, RESULT.NO_LOGIN, "Bạn chưa đăng nhập").logAction(request);
            ngModel.setResult(result);
        }
        return new ResponseEntity<>(ngModel, HttpStatus.OK);
    }

    @PostMapping("/rest/data2")
    public ResponseEntity<AngularModel<SysAccount>> getData2(HttpServletRequest request) {
        AngularModel<SysAccount> ngModel = new AngularModel<>();
        if (accService.checkLogin(request)) {
            ArrayList<SysAccount> listData = accService.findAll();            
            ngModel.setDatas(listData);
            HashMap<String, Boolean> roles = accService.checkRight(request);
            ngModel.setRoles(roles);
            if (listData == null || listData.isEmpty()) {
                ngModel.setResult(new ResponResult(RESULT.FAIL, "Danh sách tài khoản trống"));
            } else {
                ngModel.setResult(new ResponResult(RESULT.SUCCESS, ""));
            }
        } else {
            ResponResult result = new UserActionLog(TABLE, 0, ACTION.VIEW, RESULT.NO_LOGIN, "Bạn chưa đăng nhập").logAction(request);
            ngModel.setResult(result);
        }
        return new ResponseEntity<>(ngModel, HttpStatus.OK);
    }
    
    @GetMapping("/recycle")
    public String recycleView(ModelMap model, HttpServletRequest request, RedirectAttributes redrAtt) {
        if (accService.checkAccess(request)) {
            model.put(TITLE, LANG.get("sysAccount.title.recycle"));
            return "sys-account-recycle";
        } else {
            ResponResult result = new UserActionLog(TABLE, 0, ACTION.VIEW, RESULT.NO_RIGHT, "Bạn không được quyền truy cập dữ liệu đã xóa của tài khoản hệ thống").logAction(request);
            redrAtt.addFlashAttribute(RESP_NAME, result);
            return ADMIN_INDEX_PAGE;
        }
    }
    
    @GetMapping("/create")
    @Override
    public String createView(ModelMap modelMap, HttpServletRequest request, RedirectAttributes redrAtt) {
        if (accService.checkAccess(request)) {
            SysAccount account = new SysAccount();
            modelMap.put(MODEL_NAME, account);
            modelMap.put(TITLE, LANG.get("sysAccount.title.add"));
            return "sys-account-add";
        } else {
            ResponResult result = new UserActionLog(TABLE, 0, ACTION.CREATE, RESULT.NO_RIGHT, "Bạn không có quyền thêm mới tài khoản hệ thống").logAction(request);
            redrAtt.addFlashAttribute(RESP_NAME, result);
            return ADMIN_INDEX_PAGE;
        }
    }

    @PostMapping("/create")
    @Override
    public String createProcess(ModelMap model, HttpServletRequest request, RedirectAttributes redrAtt) {
        if (accService.checkAccess(request)) {
            String username = HttpUtil.getString(request, "username");            
            String password = HttpUtil.getString(request, "password");
            String fullname = HttpUtil.getString(request, "fullname");
            String phone = HttpUtil.getString(request, "phone");
            String email = HttpUtil.getString(request, "email");
            String address = HttpUtil.getString(request, "address");
            int status = HttpUtil.getInt(request, "status", MyConfig.STATUS.LOCK.val);
            
            SysAccount account = new SysAccount();
            account.setUsername(username);
            account.setPassword(password);
            account.setFullname(fullname);
            account.setPhone(phone);
            account.setEmail(email);
            account.setAddress(address);
            account.setStatus(status);
            account.setType(1);
            
            model.addAttribute("sysAcc", account);
            
            if (accService.existsByUsername(username)) {
                ResponResult result = new UserActionLog(TABLE, 0, ACTION.CREATE, RESULT.FAIL, "Username đã tồn tại, không tạo mới được!").logAction(request);
                model.put(RESP_NAME, result);
                model.put(MODEL_NAME, account);
                return "sys-account-add";
            }
            
            SysAccount userLogin = accService.getSysAccountLogin(request);
            account.setCreatedBy(userLogin.getId());
            Date date = new Date();
            account.setCreatedAt(date.getTime());
            int id = accService.create(account);
            if (id > 0) {
                ResponResult result = new UserActionLog(TABLE, id, ACTION.CREATE, RESULT.SUCCESS, "Thêm mới thành công").logAction(request);
                redrAtt.addFlashAttribute(RESP_NAME, result);
                return REDIRECT_VIEW;
            } else {
                ResponResult result = new UserActionLog(TABLE, id, ACTION.CREATE, RESULT.FAIL, "Thêm mới tài khoản thất bại").logAction(request);
                model.put(RESP_NAME, result);
                model.put(MODEL_NAME, account);
                return "sys-account-add";
            }
        } else {
            ResponResult result = new UserActionLog(TABLE, 0, ACTION.CREATE, RESULT.NO_RIGHT, "Bạn không có quyền thêm mới tài khoản hệ thống").logAction(request);
            redrAtt.addFlashAttribute(RESP_NAME, result);
            return ADMIN_INDEX_PAGE;
        }
    }    
    
    @GetMapping("/edit")
    @Override
    public String editView(ModelMap model, HttpServletRequest request, RedirectAttributes redrAtt) {
        int id = HttpUtil.getInt(request, "id");
        if (accService.checkAccess(request)) {
            SysAccount acc = accService.findById(id);
            model.put(TITLE, LANG.get("sysAccount.title.edit"));
            if (acc != null) {
                model.addAttribute(MODEL_NAME, acc);
                return "sys-account-edit";
            } else {
                ResponResult result = new UserActionLog(TABLE, id, ACTION.UPDATE, RESULT.FAIL, "Không tìm thấy tài khoản cần sửa").logAction(request);
                redrAtt.addFlashAttribute(RESP_NAME, result);
                return REDIRECT_VIEW;
            }
        } else {
            ResponResult result = new UserActionLog(TABLE, id, ACTION.UPDATE, RESULT.NO_RIGHT, "Bạn không có quyền cập nhật tài khoản hệ thống id=" + id).logAction(request);
            redrAtt.addFlashAttribute(RESP_NAME, result);
            return ADMIN_INDEX_PAGE;
        }
    }

    @PostMapping("/edit")
    @Override
    public String editProcess(ModelMap model, HttpServletRequest request, RedirectAttributes redrAtt) {
        int accId = HttpUtil.getInt(request, "accId");
        if (accService.checkAccess(request)) {
            SysAccount account = accService.findById(accId);
            SysAccount acc = accService.getSysAccountLogin(request);
            if (account != null) {
                String username = HttpUtil.getString(request, "username");
                String password = HttpUtil.getString(request, "password");
                String fullname = HttpUtil.getString(request, "fullname");
                String phone = HttpUtil.getString(request, "phone");
                String email = HttpUtil.getString(request, "email");
                String address = HttpUtil.getString(request, "address");
                int status = HttpUtil.getInt(request, "status", MyConfig.STATUS.LOCK.val);
                
                account.setUsername(username);
                account.setPassword(password);
                account.setFullname(fullname);
                account.setPhone(phone);
                account.setEmail(email);
                account.setAddress(address);
                account.setStatus(status);
                account.setUpdatedBy(acc.getId());
                Date date = new Date();
                account.setUpdatedAt(date.getTime());
                
                model.addAttribute(MODEL_NAME, account);
                if (!Tool.checkNull(password)){
                    if (Tool.stringIsSpace(password) || !Tool.Password_Validation(password)) {
                        new UserActionLog(TABLE, account.getId(), ACTION.UPDATE, RESULT.FAIL, "Password phải có ít nhất 1 ký tự là số \"0123456789\" và 1 ký tự đặc biệt như \"!@#$%&*()_+=|<>?{}[]~-\"!").logAction(request);
                        model.put(RESP_NAME, new ResponResult(RESULT.FAIL, "Password phải có ít nhất 1 ký tự là số \"0123456789\" và 1 ký tự đặc biệt như \"!@#$%&*()_+=|<>?{}[]~-\"!"));
                        model.put(MODEL_NAME, account);
                        return "sys-account-edit";
                    }
                }
                SysAccount oldVal = accService.update(account);
                if (oldVal != null) {
                    new UserActionLog(TABLE, account.getId(), ACTION.UPDATE, RESULT.SUCCESS, "Cập nhật tài khoản thành công", oldVal).logAction(request);
                    redrAtt.addFlashAttribute(RESP_NAME, new ResponResult(RESULT.SUCCESS, "Cập nhật tài khoản thành công"));
                } else {
                    new UserActionLog(TABLE, account.getId(), ACTION.UPDATE, RESULT.FAIL, "Cập nhật tài khoản thất bại").logAction(request);
                    model.put(RESP_NAME, new ResponResult(RESULT.FAIL, "Cập nhật tài khoản thất bại"));
                    model.put(MODEL_NAME, account);
                    return "sys-account-edit";
                }
            } else {
                new UserActionLog(TABLE, 0, ACTION.CREATE, RESULT.FAIL, "Không tìm thấy tài khoản cần sửa").logAction(request);
                redrAtt.addFlashAttribute(RESP_NAME, new ResponResult(RESULT.FAIL, "Không tìm thấy tài khoản cần sửa"));
            }
            return REDIRECT_VIEW;
        } else {
            redrAtt.addFlashAttribute(RESP_NAME, new ResponResult(RESULT.NO_RIGHT, "Bạn không có quyền cập nhật tài khoản hệ thống"));
            new UserActionLog(TABLE, 0, ACTION.CREATE, RESULT.NO_RIGHT, "Bạn không có quyền cập nhật tài khoản hệ thống").logAction(request);
            return ADMIN_INDEX_PAGE;
        }
    }
    
    @PostMapping("/rest/deleteForEver")
    public ResponseEntity<AngularModel<ResponResult>> deleteForEver(HttpServletRequest request) {
        int id = HttpUtil.getInt(request, "id");
        AngularModel<ResponResult> ngmodel = new AngularModel<>();
        if (accService.checkAccess(request)) {
            SysAccount oldVal = accService.deleteForEver(id);
            if (oldVal != null) {
                ResponResult result = new UserActionLog(TABLE, id, ACTION.DEL, RESULT.SUCCESS, "Xóa tải khoản --> " + oldVal.getUsername() + " <-- thành công", oldVal).logAction(request);
                ngmodel.setResult(result);
                return new ResponseEntity<>(ngmodel, HttpStatus.OK);
            } else {
                ResponResult result = new UserActionLog(TABLE, id, ACTION.DEL, RESULT.FAIL, "Xóa tài khoản --> " + oldVal.getUsername() + " <-- thất bại").logAction(request);
                ngmodel.setResult(result);
                return new ResponseEntity<>(ngmodel, HttpStatus.OK);
            }
        } else {
            ResponResult result = new UserActionLog(TABLE, id, ACTION.DEL, RESULT.NO_RIGHT, "Bạn không có quyền xóa tài khoản hệ thống").logAction(request);
            ngmodel.setResult(result);
            return new ResponseEntity<>(ngmodel, HttpStatus.OK);
        }
    }
        
    @RequestMapping("/exportSysAcc")
    public String exportSysAcc(ModelMap modelMap, HttpServletResponse response, HttpServletRequest request, RedirectAttributes redrAtt) {
        if (accService.checkLogin(request)) {
            String key = HttpUtil.getString(request, "key");
            String phone = HttpUtil.getString(request, "phone");
            String email = HttpUtil.getString(request, "email");
            int status = HttpUtil.getInt(request, "status", MyConfig.STATUS.ALL.val);
            ArrayList<SysAccount> listSysAcc = accService.view(key, phone, email, status);            
            ExcelBuilder.sysAccExcelBuilder(listSysAcc, response);
            return "sys-account";
        } else {
            ResponResult result = new UserActionLog(TABLE, 0, ACTION.UPDATE, RESULT.NO_LOGIN, "Bạn chưa đăng nhập").logAction(request);
            redrAtt.addFlashAttribute(RESP_NAME, result);
            return ADMIN_INDEX_PAGE;
        }
    }

    @Override
    public ResponseEntity delete(HttpServletRequest request) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
