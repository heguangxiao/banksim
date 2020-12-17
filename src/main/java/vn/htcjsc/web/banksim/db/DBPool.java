package vn.htcjsc.web.banksim.db;

import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.Enumeration;
import javax.annotation.PreDestroy;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import snaq.db.ConnectionPool;
import snaq.db.ConnectionPoolManager;
import vn.htcjsc.web.banksim.common.DateProc;
import vn.htcjsc.web.banksim.common.Tool;
import vn.htcjsc.web.banksim.config.WebInitializer;

@Component
public class DBPool {

    static Logger logger = Logger.getLogger(DBPool.class);
    //=============================Connection Pool------------------------------------------
    private static final String DB_POOL_NAME = "banksim";
    private static ConnectionPoolManager connPoolMng;
    private static ConnectionPool dbpool;

    public static void init() {
        initConnPoolMng();
        initDBPool();
    }

    public static Connection getConnection() {
        Connection conn = null;
        try {
            conn = dbpool.getConnection();
        } catch (SQLException e) {
            Tool.out("---------------->>> get connection error !  <<<------------- ");
            logger.error(e);
        }
        return conn;
    }

    @PreDestroy
    public void releaseDB() {
        dbpool.release();
        connPoolMng.release();
        Enumeration<Driver> drivers = DriverManager.getDrivers();
        while (drivers.hasMoreElements()) {
            Driver driver = drivers.nextElement();
            try {
                DriverManager.deregisterDriver(driver);
                Tool.out(" Deregis Driver:" + driver.toString());
                logger.log(Level.INFO, String.format("deregistering jdbc driver: %s", driver));
            } catch (SQLException e) {
                logger.log(Level.ERROR, String.format("Error deregistering driver %s", driver), e);
            }
        }
        Tool.out("releaseDB Finish............");
    }

    public static void freeConn(ResultSet rs, PreparedStatement pstm, Connection conn) {
        try {
            if (rs != null) {
                rs.close();
            }
            if (pstm != null) {
                pstm.close();
            }
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            logger.error(Tool.getLogMessage(e));
        }
    }

    public static void rollback(Connection conn, Object result) {
        try {
            if (result != null) {
                result = null;
            }
            if (conn != null) {
                conn.rollback();
            }
        } catch (SQLException e) {
            logger.error(Tool.getLogMessage(e));
        }
    }

    public static void freeConn(PreparedStatement pstm, Connection conn) {
        try {

            if (pstm != null) {
                pstm.close();
            }
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            logger.error(Tool.getLogMessage(e));
        }
    }

    public static void releadRsPstm(ResultSet rs, PreparedStatement pstm) {
        try {
            if (rs != null) {
                rs.close();
            }
            if (pstm != null) {
                pstm.close();
            }
        } catch (SQLException e) {
            logger.error(Tool.getLogMessage(e));
        }
    }

    public static int size() {
        return dbpool.getSize();
    }

    private static void initDBPool() {
        System.out.println("Vao DBPool Luc: " + DateProc.createTimestamp());
        if (connPoolMng != null) {
            dbpool = connPoolMng.getPool(DB_POOL_NAME);
            if (dbpool == null) {
                Tool.out("======F=======> [" + DB_POOL_NAME + "] IS NULL ?????");
            } else {
                Tool.out("======D=======> [" + DB_POOL_NAME + "] Init Done...");
            }
        } else {
            System.out.println("---> ConnectionPoolManager is null ???");
        }
    }

    private static void initConnPoolMng() {
        System.out.println("Vao initConnPoolMng Luc: " + DateProc.createTimestamp());
        try {
            File fcf = new File(WebInitializer.configDir + "dbpool.properties");
            connPoolMng = ConnectionPoolManager.getInstance(fcf, "UTF-8");
            connPoolMng.registerShutdownHook();
        } catch (IOException e) {
            logger.error(Tool.getLogMessage(e));
        }
    }
}
