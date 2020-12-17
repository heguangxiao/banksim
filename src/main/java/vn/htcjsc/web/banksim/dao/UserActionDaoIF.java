package vn.htcjsc.web.banksim.dao;

import java.util.ArrayList;
import vn.htcjsc.web.banksim.model.UserActionLog;

public interface UserActionDaoIF {

    public ArrayList<UserActionLog> view(int page, int maxRow, String user_name, String table_action, String action_type, String result_status);

    public int count(String user_name, String table_action, String action_type, String result_status);

    public UserActionLog findById(int id);
    
    public ArrayList<String> getListUser();
    
    public ArrayList<String> getListTable();
    
    public ArrayList<String> getListAction();
}
