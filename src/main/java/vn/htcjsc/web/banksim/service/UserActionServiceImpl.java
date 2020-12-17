package vn.htcjsc.web.banksim.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.htcjsc.web.banksim.common.Tool;
import vn.htcjsc.web.banksim.dao.UserActionDaoIF;
import vn.htcjsc.web.banksim.db.DBPool;
import vn.htcjsc.web.banksim.model.UserActionLog;
import static vn.htcjsc.web.banksim.service.SysAccountServiceImpl.logger;

@Service("userActionSV")
public class UserActionServiceImpl implements UserActionService {

    @Autowired
    UserActionDaoIF userAction;

    @Override
    public ArrayList<UserActionLog> view(int page, int maxRow, String user_name, String table_action, String action_type, String result_status) {
        return userAction.view(page, maxRow, user_name, table_action, action_type, result_status);
    }

    @Override
    public int count(String user_name, String table_action, String action_type, String result_status) {
        return userAction.count(user_name, table_action, action_type, result_status);
    }

    @Override
    public UserActionLog findById(int id) {
        return userAction.findById(id);
    }
    
    @Override
    public ArrayList<UserActionLog> getSomeInfoUsername() {
        ArrayList<UserActionLog> result = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        String sql = "SELECT DISTINCT USER_NAME FROM LOG_USER_ACTION";
        try {
            conn = DBPool.getConnection();
            pstm = conn.prepareStatement(sql);
            int i = 1;
            rs = pstm.executeQuery();
            while (rs.next()) {
                UserActionLog one = new UserActionLog();
                one.setUserName(rs.getString("USER_NAME"));
                result.add(one);
            }
        } catch (SQLException ex) {
            logger.error(Tool.getLogMessage(ex));
        } finally {
            DBPool.freeConn(rs, pstm, conn);
        }
        return result;
    }
    
    @Override
    public ArrayList<UserActionLog> getSomeInfoTableAction() {
        ArrayList<UserActionLog> result = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        String sql = "SELECT DISTINCT TABLE_ACTION FROM LOG_USER_ACTION";
        try {
            conn = DBPool.getConnection();
            pstm = conn.prepareStatement(sql);
            int i = 1;
            rs = pstm.executeQuery();
            while (rs.next()) {
                UserActionLog one = new UserActionLog();
                one.setTableAction(rs.getString("TABLE_ACTION"));
                result.add(one);
            }
        } catch (SQLException ex) {
            logger.error(Tool.getLogMessage(ex));
        } finally {
            DBPool.freeConn(rs, pstm, conn);
        }
        return result;
    }
    
    @Override
    public ArrayList<UserActionLog> getSomeInfoActionType() {
        ArrayList<UserActionLog> result = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        String sql = "SELECT DISTINCT ACTION_TYPE FROM LOG_USER_ACTION";
        try {
            conn = DBPool.getConnection();
            pstm = conn.prepareStatement(sql);
            int i = 1;
            rs = pstm.executeQuery();
            while (rs.next()) {
                UserActionLog one = new UserActionLog();
                one.setActionType(rs.getString("ACTION_TYPE"));
                result.add(one);
            }
        } catch (SQLException ex) {
            logger.error(Tool.getLogMessage(ex));
        } finally {
            DBPool.freeConn(rs, pstm, conn);
        }
        return result;
    }

    @Override
    public ArrayList<String> getListUser() {
        return userAction.getListUser();
    }

    @Override
    public ArrayList<String> getListTable() {
        return userAction.getListTable();
    }

    @Override
    public ArrayList<String> getListAction() {
        return userAction.getListAction();
    }
}
