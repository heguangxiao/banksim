/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vn.htcjsc.web.banksim.service;

import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import vn.htcjsc.web.banksim.dao.SysAccountDaoIF;
import vn.htcjsc.web.banksim.model.SysAccount;
/**
 *
 * @author Private
 */
public interface SysAccountService extends SysAccountDaoIF {

    public SysAccount getSysAccountLogin(HttpSession session);

    public String getUserName(HttpServletRequest request);

    public SysAccount getSysAccountLogin(HttpServletRequest request);

    public boolean updateIsdelete(int accID);

    //--Check Right Methol
    public HashMap<String, Boolean> checkRight(HttpServletRequest request);

    public boolean checkAccess(HttpServletRequest request);
    
    public boolean checkLogin(HttpServletRequest request);
}
