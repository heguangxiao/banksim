/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vn.htcjsc.web.banksim.dao;

import java.util.ArrayList;
import vn.htcjsc.web.banksim.model.SysAccount;

/**
 *
 * @author Private
 */
public interface SysAccountDaoIF extends BasicDaoIF<SysAccount> {

    public ArrayList<SysAccount> view(int page, int maxRow, String key, String phone, String email, int status);
    
    public ArrayList<SysAccount> view(String key, String phone, String email, int status);

    public int count(String key, String phone, String email, int status);

    public ArrayList<SysAccount> findSysAccountOnRole(int page, int maxrow, String key);

    public int countSysAccountOnRole(String key);

    public ArrayList<SysAccount> findAllSysAccountActive(String key);

    public SysAccount checkLoginDB(String user, String pass);

    public SysAccount undoDelete(int accID);

    public SysAccount deleteForEver(int accID);
    
    public ArrayList<String> getAllAccount();
    
    public ArrayList<SysAccount> findAll();
    
    boolean existsByUsername(String username);

}
