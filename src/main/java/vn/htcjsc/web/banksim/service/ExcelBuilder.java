/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vn.htcjsc.web.banksim.service;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import vn.htcjsc.web.banksim.common.DateProc;
import vn.htcjsc.web.banksim.config.MyConfig;
import vn.htcjsc.web.banksim.model.SysAccount;

/**
 *
 * @author nguoi
 */
public class ExcelBuilder {
    
    @Autowired
    SysAccountService sysAccountService;
    
    public static void sysAccExcelBuilder(ArrayList<SysAccount> listSysAcc, HttpServletResponse response) {
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("System_Account_Available_" + new Date().getTime());
        sheet.setDefaultColumnWidth(10);                //Rộng cột mặc định 12

        ArrayList<Object[]> data = new ArrayList();
        int i = 1;
        String status = "";
        data.add(new Object[]{
            "STT",
            "USERNAME",
            "FULLNAME",
            "ADDRESS",
            "PHONE",
            "EMAIL",
            "STATUS",
            "CREATED BY",
            "CREATED AT",
            "UPDATED BY",
            "UPDATED AT"});
        for (SysAccount oneAcc : listSysAcc) {
            //Dat gia tri cho status
            if (oneAcc.getStatus() == MyConfig.STATUS.ACTIVE.val) {
                status = MyConfig.STATUS.ACTIVE.desc;
            } else if (oneAcc.getStatus() == MyConfig.STATUS.ALL.val) {
                status = MyConfig.STATUS.ALL.desc;
            } else if (oneAcc.getStatus() == MyConfig.STATUS.LOCK.val) {
                status = MyConfig.STATUS.LOCK.desc;
            } else if (oneAcc.getStatus() == MyConfig.STATUS.UNACTIVE.val) {
                status = MyConfig.STATUS.UNACTIVE.desc;
            } else {
                status = "Unknow";
            }
            //Dat gia tri cho tung hang
            data.add(new Object[]{
                i++,
                oneAcc.getUsername(),
                oneAcc.getFullname(),
                oneAcc.getAddress(),
                oneAcc.getPhone(),
                oneAcc.getEmail(),
                status,
                oneAcc.getCreatedBy(),
                new Date(oneAcc.getCreatedAt()).toString(),
                oneAcc.getUpdatedBy(),
                new Date(oneAcc.getUpdatedAt()).toString()});
        }
        int rownum = 0;
        for (Object[] objArr : data) {
            Row row = sheet.createRow(rownum++);
            int cellnum = 0;
            for (Object obj : objArr) {
                Cell cell = row.createCell(cellnum++);
                 if (obj instanceof Timestamp) {
                    cell.setCellValue(DateProc.Timestamp2DDMMYYYYHH24MiSS((Timestamp) obj));
                } else if (obj instanceof Boolean) {
                    cell.setCellValue((Boolean) obj);
                } else if (obj instanceof String) {
                    cell.setCellValue((String) obj);
                } else if (obj instanceof Double) {
                    cell.setCellValue((Double) obj);
                } else if (obj instanceof Integer) {
                    cell.setCellValue((Integer) obj);
                } else {
                    cell.setCellValue((String) obj);
                }
            }
        }

        try {
            ServletOutputStream os = null;
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setHeader("Content-Disposition", "attachment; filename=System_Account_Available_" + new Date().getTime() + ".xlsx");
            os = response.getOutputStream();
            os.flush();
            workbook.write(os);
            System.out.println("Excel written successfully..");
            os.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
