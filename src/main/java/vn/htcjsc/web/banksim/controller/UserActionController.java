package vn.htcjsc.web.banksim.controller;

import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import vn.htcjsc.web.banksim.common.HttpUtil;
import vn.htcjsc.web.banksim.config.MyConfig;
import vn.htcjsc.web.banksim.model.UserActionLog;
import vn.htcjsc.web.banksim.model.ext.AngularModel;
import vn.htcjsc.web.banksim.model.ext.ResponResult;
import vn.htcjsc.web.banksim.service.UserActionService;

@Controller
@RequestMapping("/admin/user-action")
public class UserActionController extends AbstractBackEnConst {

    private final String TABLE = "LOG_USER_ACTION";
    @Autowired
    UserActionService userActionSV;

    @GetMapping("/list")
    @Override
    public String list(ModelMap model, HttpServletRequest request, RedirectAttributes redrAtt) {
        if (accService.checkAccess(request)) {
            model.put("username", userActionSV.getSomeInfoUsername());
            model.put("tableaction", userActionSV.getSomeInfoTableAction());
            model.put("actiontype", userActionSV.getSomeInfoActionType());
            model.put(TITLE, LANG.get("userAction.title.list"));
            return "admin-user-action";
        } else {
            ResponResult result = new UserActionLog(TABLE, 0, ACTION.VIEW, RESULT.NO_RIGHT, "Bạn không có quyền truy cập lịch sử người dùng thao tác").logAction(request);
            redrAtt.addFlashAttribute(RESP_NAME, result);
            return ADMIN_INDEX_PAGE;
        }
    }

    @GetMapping("/rest/data")
    @Override
    public ResponseEntity<AngularModel<UserActionLog>> getData(HttpServletRequest request) {
        AngularModel<UserActionLog> ngModel = new AngularModel<>();
        if (accService.checkLogin(request)) {
            int crPage = HttpUtil.getInt(request, "crPage", 1);
            int maxRow = HttpUtil.getInt(request, "maxRow", MyConfig.ADMIN_MAX_ROW);
            String user_name = HttpUtil.getString(request, "user_name");
            String table_action = HttpUtil.getString(request, "table_action");
            String action_type = HttpUtil.getString(request, "action_type");
            String result_status = HttpUtil.getString(request, "result");
            
            ArrayList<UserActionLog> listData = userActionSV.view(crPage, maxRow, user_name, table_action, action_type, result_status);
            
            int totalRow = userActionSV.count(user_name, table_action, action_type, result_status);
                        
            ngModel.setDatas(listData);
            ngModel.setTotalRow(totalRow);
            
            if (listData == null || listData.isEmpty()) {
                ngModel.setResult(new ResponResult(RESULT.FAIL, "Danh sách lịch sử người dùng thao tác trống"));
            } else {
                ngModel.setResult(new ResponResult(RESULT.SUCCESS, ""));
            }
        } else {
            ResponResult result = new UserActionLog(TABLE, 0, ACTION.VIEW, RESULT.NO_LOGIN, "Bạn chưa đăng nhập").logAction(request);
            ngModel.setResult(result);
        }
        return new ResponseEntity<>(ngModel, HttpStatus.OK);
    }

    @GetMapping("/rest/data2")
    public ResponseEntity<AngularModel<String>> getData2(HttpServletRequest request) {
        AngularModel<String> ngModel = new AngularModel<>();
        if (accService.checkLogin(request)) {
            ArrayList<String> listData = userActionSV.getListUser();            
            ngModel.setDatas(listData);
            if (listData == null || listData.isEmpty()) {
                ngModel.setResult(new ResponResult(RESULT.FAIL, "Danh sách tên người dùng thao tác trống"));
            } else {
                ngModel.setResult(new ResponResult(RESULT.SUCCESS, ""));
            }
        } else {
            ResponResult result = new UserActionLog(TABLE, 0, ACTION.VIEW, RESULT.NO_LOGIN, "Bạn chưa đăng nhập").logAction(request);
            ngModel.setResult(result);
        }
        return new ResponseEntity<>(ngModel, HttpStatus.OK);
    }

    @GetMapping("/rest/data3")
    public ResponseEntity<AngularModel<String>> getData3(HttpServletRequest request) {
        AngularModel<String> ngModel = new AngularModel<>();
        if (accService.checkLogin(request)) {
            ArrayList<String> listData = userActionSV.getListTable();
            ngModel.setDatas(listData);
            if (listData == null || listData.isEmpty()) {
                ngModel.setResult(new ResponResult(RESULT.FAIL, "Danh sách bảng người dùng thao tác trống"));
            } else {
                ngModel.setResult(new ResponResult(RESULT.SUCCESS, ""));
            }
        } else {
            ResponResult result = new UserActionLog(TABLE, 0, ACTION.VIEW, RESULT.NO_LOGIN, "Bạn chưa đăng nhập").logAction(request);
            ngModel.setResult(result);
        }
        return new ResponseEntity<>(ngModel, HttpStatus.OK);
    }

    @GetMapping("/rest/data4")
    public ResponseEntity<AngularModel<String>> getData4(HttpServletRequest request) {
        AngularModel<String> ngModel = new AngularModel<>();
        if (accService.checkLogin(request)) {
            ArrayList<String> listData = userActionSV.getListAction();
            ngModel.setDatas(listData);
            if (listData == null || listData.isEmpty()) {
                ngModel.setResult(new ResponResult(RESULT.FAIL, "Danh sách kiểu hành động người dùng thao tác trống"));
            } else {
                ngModel.setResult(new ResponResult(RESULT.SUCCESS, ""));
            }
        } else {
            ResponResult result = new UserActionLog(TABLE, 0, ACTION.VIEW, RESULT.NO_RIGHT, "Bạn chưa đăng nhập").logAction(request);
            ngModel.setResult(result);
        }
        return new ResponseEntity<>(ngModel, HttpStatus.OK);
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
