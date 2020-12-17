/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vn.htcjsc.web.banksim.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import vn.htcjsc.web.banksim.common.Md5;
import vn.htcjsc.web.banksim.common.Tool;
import vn.htcjsc.web.banksim.config.MyConfig;
import vn.htcjsc.web.banksim.db.DBPool;
import vn.htcjsc.web.banksim.model.SysAccount;

/**
 *
 * @author Private
 */
@Repository("accDao")
public class SysAccountDaoImpl implements SysAccountDaoIF {

    static Logger logger = Logger.getLogger(SysAccountDaoImpl.class);

    @Override
    public ArrayList<String> getAllAccount() {
        ArrayList<String> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "SELECT USERNAME FROM account WHERE 1 = 1";
        try {
            conn = DBPool.getConnection();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                list.add(rs.getString("USERNAME"));
            }
        } catch (SQLException e) {
            logger.error(Tool.getLogMessage(e));
        } finally {
            DBPool.freeConn(rs, ps, conn);
        }
        return list;
    }
    
    @Override
    public ArrayList<SysAccount> view(int page, int maxRow, String key, String phone, String email, int status) {
        ArrayList<SysAccount> result = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        String sql = "SELECT A.* FROM account A WHERE 1 = 1 ";
        if (!Tool.checkNull(key)) {
            sql += " AND (USERNAME like ?  OR FULLNAME like ?)";
        }
        if (!Tool.checkNull(phone)) {
            sql += " AND PHONE like ? ";
        }
        if (!Tool.checkNull(email)) {
            sql += " AND EMAIL like ? ";
        }
        if (status != MyConfig.STATUS.ALL.val) {
            sql += " AND STATUS = ? ";
        }
        sql += " ORDER BY A.ID DESC LIMIT ?,?";
        try {
            conn = DBPool.getConnection();
            pstm = conn.prepareStatement(sql);
            int i = 1;
            
            if (!Tool.checkNull(key)) {
                pstm.setString(i++, "%" + key + "%");
                pstm.setString(i++, "%" + key + "%");
            }
            if (!Tool.checkNull(phone)) {
                pstm.setString(i++, "%" + phone + "%");
            }
            if (!Tool.checkNull(email)) {
                pstm.setString(i++, "%" + email + "%");
            }
            if (status != MyConfig.STATUS.ALL.val) {
                pstm.setInt(i++, status);
            }
            pstm.setInt(i++, (page - 1) * maxRow);
            pstm.setInt(i++, maxRow);
            rs = pstm.executeQuery();
            while (rs.next()) {
                SysAccount acc = new SysAccount();
                acc.setId(rs.getInt("ID"));
                acc.setUsername(rs.getString("USERNAME"));
                acc.setPassword(rs.getString("PASSWORD"));
                acc.setFullname(rs.getString("FULLNAME"));
                acc.setAddress(rs.getString("ADDRESS"));
                acc.setPhone(rs.getString("PHONE"));
                acc.setEmail(rs.getString("EMAIL"));
                acc.setCreatedAt(rs.getLong("CREATEDAT"));
                acc.setCreatedBy(rs.getInt("CREATEDBY"));
                acc.setUpdatedAt(rs.getLong("UPDATEDAT"));
                acc.setUpdatedBy(rs.getInt("UPDATEDBY"));
                acc.setStatus(rs.getInt("STATUS"));
                acc.setType(rs.getInt("TYPE"));
                result.add(acc);
            }
        } catch (SQLException ex) {
            logger.error(Tool.getLogMessage(ex));
        } finally {
            DBPool.freeConn(rs, pstm, conn);
        }
        return result;
    }

    @Override
    public int count(String key, String phone, String email, int status) {
        int result = 0;
        Connection conn = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        String sql = "SELECT count(*) FROM account A WHERE 1 = 1 ";
        if (!Tool.checkNull(key)) {
            sql += " AND (USERNAME like ?  OR FULLNAME like ?)";
        }
        if (!Tool.checkNull(phone)) {
            sql += " AND PHONE like ? ";
        }
        if (!Tool.checkNull(email)) {
            sql += " AND EMAIL like ? ";
        }
        if (status != MyConfig.STATUS.ALL.val) {
            sql += " AND STATUS = ? ";
        }
        try {
            conn = DBPool.getConnection();
            pstm = conn.prepareStatement(sql);
            int i = 1;
            
            if (!Tool.checkNull(key)) {
                pstm.setString(i++, "%" + key + "%");
                pstm.setString(i++, "%" + key + "%");
            }
            if (!Tool.checkNull(phone)) {
                pstm.setString(i++, "%" + phone + "%");
            }
            if (!Tool.checkNull(email)) {
                pstm.setString(i++, "%" + email + "%");
            }
            if (status != MyConfig.STATUS.ALL.val) {
                pstm.setInt(i++, status);
            }
            rs = pstm.executeQuery();
            if (rs.next()) {
                result = rs.getInt(1);
            }
        } catch (SQLException ex) {
            logger.error(Tool.getLogMessage(ex));
        } finally {
            DBPool.freeConn(rs, pstm, conn);
        }
        return result;
    }

    @Override
    public ArrayList<SysAccount> findAllSysAccountActive(String key) {
        ArrayList<SysAccount> result = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        String sql = "SELECT * FROM account WHERE STATUS = ? AND 1 = 1";
        if (!Tool.checkNull(key)) {
            sql += " AND( USERNAME like ? or FULLNAME like ?) ";
        }
        sql += " ORDER BY ID DESC,STATUS DESC";
        try {
            conn = DBPool.getConnection();
            pstm = conn.prepareStatement(sql);
            int i = 1;
            pstm.setInt(i++, MyConfig.STATUS.ACTIVE.val);
            pstm.setBoolean(i++, MyConfig.ISDEL);
            if (!Tool.checkNull(key)) {
                pstm.setString(i++, "%" + key + "%");
                pstm.setString(i++, "%" + key + "%");
            }
            rs = pstm.executeQuery();
            while (rs.next()) {
                SysAccount acc = new SysAccount();
                acc.setId(rs.getInt("ID"));
                acc.setUsername(rs.getString("USERNAME"));
                acc.setPassword(rs.getString("PASSWORD"));
                acc.setFullname(rs.getString("FULLNAME"));
                acc.setAddress(rs.getString("ADDRESS"));
                acc.setPhone(rs.getString("PHONE"));
                acc.setEmail(rs.getString("EMAIL"));
                acc.setCreatedAt(rs.getLong("CREATEDAT"));
                acc.setCreatedBy(rs.getInt("CREATEDBY"));
                acc.setUpdatedAt(rs.getLong("UPDATEDAT"));
                acc.setUpdatedBy(rs.getInt("UPDATEDBY"));
                acc.setStatus(rs.getInt("STATUS"));
                acc.setType(rs.getInt("TYPE"));
                result.add(acc);
            }
        } catch (SQLException ex) {
            logger.error(Tool.getLogMessage(ex));
        } finally {
            DBPool.freeConn(rs, pstm, conn);
        }
        return result;
    }

    @Override
    public SysAccount findById(int accID) {
        SysAccount acc = null;
        Connection conn = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        String sql = "SELECT * FROM account WHERE 1 = 1 AND ID = ?";
        try {
            conn = DBPool.getConnection();
            pstm = conn.prepareStatement(sql);
            pstm.setInt(1, accID);
            rs = pstm.executeQuery();
            if (rs.next()) {
                acc = new SysAccount();
                acc.setId(rs.getInt("ID"));
                acc.setUsername(rs.getString("USERNAME"));
                acc.setPassword(rs.getString("PASSWORD"));
                acc.setFullname(rs.getString("FULLNAME"));
                acc.setAddress(rs.getString("ADDRESS"));
                acc.setPhone(rs.getString("PHONE"));
                acc.setEmail(rs.getString("EMAIL"));
                acc.setCreatedAt(rs.getLong("CREATEDAT"));
                acc.setCreatedBy(rs.getInt("CREATEDBY"));
                acc.setUpdatedAt(rs.getLong("UPDATEDAT"));
                acc.setUpdatedBy(rs.getInt("UPDATEDBY"));
                acc.setStatus(rs.getInt("STATUS"));
                acc.setType(rs.getInt("TYPE"));
            }
        } catch (SQLException ex) {
            logger.error(Tool.getLogMessage(ex));
        } finally {
            DBPool.freeConn(rs, pstm, conn);
        }
        return acc;
    }

    @Override
    public SysAccount checkLoginDB(String userName, String password) {
        SysAccount acc = null;
        Connection conn = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        String sql = "SELECT * FROM account WHERE upper(USERNAME) = upper(?) AND PASSWORD = ?";
        try {
            conn = DBPool.getConnection();
            pstm = conn.prepareStatement(sql);
            pstm.setString(1, userName);
            pstm.setString(2, Md5.encryptMD5(password));
            rs = pstm.executeQuery();
            if (rs.next()) {
                acc = new SysAccount();
                acc.setId(rs.getInt("ID"));
                acc.setUsername(rs.getString("USERNAME"));
                acc.setPassword(rs.getString("PASSWORD"));
                acc.setFullname(rs.getString("FULLNAME"));
                acc.setAddress(rs.getString("ADDRESS"));
                acc.setPhone(rs.getString("PHONE"));
                acc.setEmail(rs.getString("EMAIL"));
                acc.setCreatedAt(rs.getLong("CREATEDAT"));
                acc.setCreatedBy(rs.getInt("CREATEDBY"));
                acc.setUpdatedAt(rs.getLong("UPDATEDAT"));
                acc.setUpdatedBy(rs.getInt("UPDATEDBY"));
                acc.setStatus(rs.getInt("STATUS"));
                acc.setType(rs.getInt("TYPE"));
            }
        } catch (SQLException ex) {
            logger.error(Tool.getLogMessage(ex));
        } finally {
            DBPool.freeConn(rs, pstm, conn);
        }
        return acc;
    }

    @Override
    public int create(SysAccount oneAcc) {
        int result = 0;
        Connection conn = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        String sql = "INSERT INTO account(USERNAME,PASSWORD,FULLNAME,ADDRESS,PHONE,EMAIL,CREATEDAT,CREATEDBY,STATUS,TYPE) "
                   + "             VALUES(    ?   ,    ?   ,    ?   ,   ?   ,  ?  ,  ?  ,   ?     ,   ?     ,  ?   ,  ? ) ";
        try {
            conn = DBPool.getConnection();
            pstm = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            int i = 1;
            pstm.setString(i++, oneAcc.getUsername());
            pstm.setString(i++, Md5.encryptMD5(oneAcc.getPassword()));
            pstm.setString(i++, oneAcc.getFullname());
            pstm.setString(i++, oneAcc.getAddress());
            pstm.setString(i++, oneAcc.getPhone());
            pstm.setString(i++, oneAcc.getEmail());
            pstm.setLong(i++, oneAcc.getCreatedAt());
            pstm.setInt(i++, oneAcc.getCreatedBy());
            pstm.setInt(i++, oneAcc.getStatus());
            pstm.setInt(i++, oneAcc.getType());
            
            if (pstm.executeUpdate() == 1) {
                try (ResultSet generatedKeys = pstm.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        result = generatedKeys.getInt(1);
                    } else {
                        throw new SQLException("Creating SysAccount failed, no ID obtained.");
                    }
                }
            }
        } catch (SQLException ex) {
            logger.error(Tool.getLogMessage(ex));
        } finally {
            DBPool.freeConn(rs, pstm, conn);
        }
        return result;
    }

    @Override
    public SysAccount update(SysAccount edit) {
        SysAccount result = findById(edit.getId());
        if (result != null) {
            Connection conn = null;
            PreparedStatement pstm = null;
            String sql = "UPDATE account SET ";
            if (!Tool.checkNull(edit.getPassword())) {
                sql += "PASSWORD = ?,";
            }
            sql += " FULLNAME = ?, ADDRESS = ?, PHONE = ?, EMAIL = ?,"
                +  " UPDATEDAT = ?, UPDATEDBY = ?, STATUS=?, TYPE = ? "
                +  " WHERE ID = ?";
            try {
                conn = DBPool.getConnection();
                conn.setAutoCommit(false);
                pstm = conn.prepareStatement(sql);
                int i = 1;
                
                pstm.setString(i++, edit.getUsername());
                if (!Tool.checkNull(edit.getPassword())) {
                    pstm.setString(i++, Md5.encryptMD5(edit.getPassword()));
                }
                pstm.setString(i++, edit.getFullname());
                pstm.setString(i++, edit.getAddress());
                pstm.setString(i++, edit.getPhone());
                pstm.setString(i++, edit.getEmail());
                pstm.setInt(i++, edit.getCreatedBy());
                pstm.setLong(i++, edit.getCreatedAt());
                pstm.setInt(i++, edit.getStatus());
                pstm.setInt(i++, edit.getType());
                pstm.setInt(i++, edit.getId());
                
                if (pstm.executeUpdate() == 1) {
                    conn.commit();
                    return result;
                } else {
                    conn.rollback();
                    result = null;
                }
            } catch (SQLException ex) {
                DBPool.rollback(conn, result);
                logger.error(Tool.getLogMessage(ex));
            } finally {
                DBPool.freeConn(null, pstm, conn);
            }
        }
        return result;
    }

    @Override
    public SysAccount deleteForEver(int accId) {
        System.out.println("id : " + accId);
        SysAccount result = findById(accId);
        if (result != null) {
            Connection conn = null;
            PreparedStatement pstm = null;
            String sql = "DELETE FROM account WHERE ID = ?";
            try {
                conn = DBPool.getConnection();
                pstm = conn.prepareStatement(sql);
                pstm.setInt(1, accId);
                if (pstm.executeUpdate() != 1) {
                    result = null;
                }
            } catch (SQLException ex) {
                DBPool.rollback(conn, result);
                logger.error(Tool.getLogMessage(ex));
            } finally {
                DBPool.freeConn(null, pstm, conn);
            }
        }
        return result;
    }

    @Override
    public SysAccount delete(int accID) {
        SysAccount result = findById(accID);
        return result;
    }

    @Override
    public SysAccount undoDelete(int accID) {
        SysAccount result = findById(accID);
        return result;
    }

    @Override
    public ArrayList<SysAccount> findSysAccountOnRole(int page, int maxrow, String key) {
        ArrayList<SysAccount> result = new ArrayList<>();
        return result;
    }

    @Override
    public int countSysAccountOnRole(String key) {
        int result = 0;
        return result;
    }

    @Override
    public ArrayList<SysAccount> findAll() {
        ArrayList<SysAccount> result = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        String sql = "SELECT A.* FROM account A WHERE 1 = 1 ";
        try {
            conn = DBPool.getConnection();
            pstm = conn.prepareStatement(sql);
            int i = 1;
            
            rs = pstm.executeQuery();
            while (rs.next()) {
                SysAccount acc = new SysAccount();
                acc.setUsername(rs.getString("USERNAME"));
                acc.setFullname(rs.getString("FULLNAME"));
                result.add(acc);
            }
        } catch (SQLException ex) {
            logger.error(Tool.getLogMessage(ex));
        } finally {
            DBPool.freeConn(rs, pstm, conn);
        }
        return result;
    }

    @Override
    public boolean existsByUsername(String username) {
        boolean result = false;
        Connection conn = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        String sql = "SELECT * FROM account WHERE 1 = 1 AND USERNAME = ?";
        try {
            conn = DBPool.getConnection();
            pstm = conn.prepareStatement(sql);
            pstm.setString(1, username);
            rs = pstm.executeQuery();
            if (rs.next()) {
                result = true;
            }
        } catch (SQLException ex) {
            logger.error(Tool.getLogMessage(ex));
        } finally {
            DBPool.freeConn(rs, pstm, conn);
        }
        return result;
    }

    @Override
    public ArrayList<SysAccount> view(String key, String phone, String email, int status) {
        ArrayList<SysAccount> result = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        String sql = "SELECT A.* FROM account A WHERE 1 = 1 ";
        if (!Tool.checkNull(key)) {
            sql += " AND (USERNAME like ?  OR FULLNAME like ?)";
        }
        if (!Tool.checkNull(phone)) {
            sql += " AND PHONE like ? ";
        }
        if (!Tool.checkNull(email)) {
            sql += " AND EMAIL like ? ";
        }
        if (status != MyConfig.STATUS.ALL.val) {
            sql += " AND STATUS = ? ";
        }
        sql += " ORDER BY A.ID DESC";
        try {
            conn = DBPool.getConnection();
            pstm = conn.prepareStatement(sql);
            int i = 1;            
            if (!Tool.checkNull(key)) {
                pstm.setString(i++, "%" + key + "%");
                pstm.setString(i++, "%" + key + "%");
            }
            if (!Tool.checkNull(phone)) {
                pstm.setString(i++, "%" + phone + "%");
            }
            if (!Tool.checkNull(email)) {
                pstm.setString(i++, "%" + email + "%");
            }
            if (status != MyConfig.STATUS.ALL.val) {
                pstm.setInt(i++, status);
            }
            rs = pstm.executeQuery();
            while (rs.next()) {
                SysAccount acc = new SysAccount();
                acc.setId(rs.getInt("ID"));
                acc.setUsername(rs.getString("USERNAME"));
                acc.setPassword(rs.getString("PASSWORD"));
                acc.setFullname(rs.getString("FULLNAME"));
                acc.setAddress(rs.getString("ADDRESS"));
                acc.setPhone(rs.getString("PHONE"));
                acc.setEmail(rs.getString("EMAIL"));
                acc.setCreatedAt(rs.getLong("CREATEDAT"));
                acc.setCreatedBy(rs.getInt("CREATEDBY"));
                acc.setUpdatedAt(rs.getLong("UPDATEDAT"));
                acc.setUpdatedBy(rs.getInt("UPDATEDBY"));
                acc.setStatus(rs.getInt("STATUS"));
                acc.setType(rs.getInt("TYPE"));
                result.add(acc);
            }
        } catch (SQLException ex) {
            logger.error(Tool.getLogMessage(ex));
        } finally {
            DBPool.freeConn(rs, pstm, conn);
        }
        return result;
    }

}
