/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vn.htcjsc.web.banksim.service;

import java.util.ArrayList;
import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.htcjsc.web.banksim.common.HttpUtil;
import vn.htcjsc.web.banksim.common.Tool;
import vn.htcjsc.web.banksim.config.MyConfig;
import vn.htcjsc.web.banksim.dao.SysAccountDaoIF;
import vn.htcjsc.web.banksim.model.SysAccount;

/**
 *
 * @author Private
 */
@Service("accService")
@Transactional
public class SysAccountServiceImpl implements SysAccountService {

    static final Logger logger = Logger.getLogger(SysAccountServiceImpl.class);

    @Autowired
    SysAccountDaoIF accDao;
    
    static String[] USER_BLACK_LIST = {"admin",
        "administrator",
        "moderator",
        "mode",
        "quantri",};

    public static boolean isBlackList(String user) {
        boolean result = false;
        for (String one : USER_BLACK_LIST) {
            if (user.startsWith(one)) {
                result = true;
                break;
            }
        }
        return result;
    }

    public static void outerReload() {

    }

    @Override
    public ArrayList<SysAccount> view(int page, int maxRow, String key, String phone, String email, int status) {
        return accDao.view(page, maxRow, key, phone, email, status);
    }

    @Override
    public int count(String key, String phone, String email, int status) {
        return accDao.count(key, phone, email, status);
    }

    @Override
    public SysAccount getSysAccountLogin(HttpSession session) {
        SysAccount acc = null;
        try {
            acc = (SysAccount) session.getAttribute(MyConfig.USER_SESSION_NAME);
        } catch (Exception e) {
            logger.error("SysAccount.getSysAccount:" + Tool.getLogMessage(e));
        }
        return acc;
    }

    @Override
    public String getUserName(HttpServletRequest request) {
        SysAccount acc = getSysAccountLogin(request);
        if (acc != null) {
            return acc.getUsername();
        } else {
            return "Unknow";
        }
    }

    @Override
    public SysAccount getSysAccountLogin(HttpServletRequest request) {
        SysAccount acc = null;
        try {
            HttpSession session = request.getSession(false);
            acc = (SysAccount) session.getAttribute(MyConfig.USER_SESSION_NAME);
        } catch (Exception e) {
            logger.error("SysAccount.getSysAccount:" + Tool.getLogMessage(e));
        }
        return acc;
    }

    @Override
    public SysAccount findById(int accID) {
        return accDao.findById(accID);
    }

    @Override
    public SysAccount checkLoginDB(String userName, String password) {
        SysAccount account = accDao.checkLoginDB(userName, password);
        return account;
    }

    @Override
    public int create(SysAccount oneAcc) {
        if (isBlackList(oneAcc.getUsername())) {
            return -1;
        }
        return accDao.create(oneAcc);
    }

    @Override
    public SysAccount update(SysAccount accUpdate) {
        return accDao.update(accUpdate);
    }

    @Override
    public SysAccount delete(int accID) {
        return accDao.delete(accID);
    }

    @Override
    public SysAccount deleteForEver(int accID) {
        return accDao.deleteForEver(accID);
    }

    @Override
    public SysAccount undoDelete(int accID) {
        return accDao.undoDelete(accID);
    }

    @Override
    public boolean updateIsdelete(int accID) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated 
    }
    
    @Override
    public ArrayList<SysAccount> findSysAccountOnRole(int page, int maxrow, String key) {
        return accDao.findSysAccountOnRole(page, maxrow, key);
    }

    @Override
    public int countSysAccountOnRole(String key) {
        return accDao.countSysAccountOnRole(key);
    }

    @Override
    public HashMap<String, Boolean> checkRight(HttpServletRequest request) {
        HashMap<String, Boolean> hasRole = new HashMap<>();
        SysAccount account = getSysAccountLogin(request);
        if (account != null) {
            // Lay ra modul dang truy xuat
            String uri = HttpUtil.getURI(request);
            uri = Tool.removeEndCharactor(uri, "/");
        }
        return hasRole;
    }

    /**
     *
     * @param request
     * @return
     */
    @Override
    public boolean checkAccess(HttpServletRequest request) {
        boolean right = false;
        SysAccount account = getSysAccountLogin(request);
        if (account != null) {
            right = true;  
        }        
        return right;
    }

    public boolean accessModule(String modulePath, HttpServletRequest request) {
        boolean right = false;
        SysAccount account = getSysAccountLogin(request);
        right = account != null;
        return right;
    }

    @Override
    public ArrayList<SysAccount> findAllSysAccountActive(String key) {
        return accDao.findAllSysAccountActive(key);
    }

    @Override
    public ArrayList<String> getAllAccount() {
        return accDao.getAllAccount();
    }

    @Override
    public ArrayList<SysAccount> findAll() {
        return accDao.findAll();
    }

    @Override
    public boolean existsByUsername(String username) {
        return accDao.existsByUsername(username);
    }

    @Override
    public ArrayList<SysAccount> view(String key, String phone, String email, int status) {
        return accDao.view(key, phone, email, status);
    }

    @Override
    public boolean checkLogin(HttpServletRequest request) {
        boolean right = false;
        SysAccount account = getSysAccountLogin(request);
        if (account != null) {
            right = true;
        }        
        return right;
    }

}
