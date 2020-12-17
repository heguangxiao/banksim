/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vn.htcjsc.web.banksim.model.ext;

/**
 *
 * @author nguoi
 */
public class InvoiceCallStatistic {

    private int duration;
    private int billsec;
    private String telco;
    private String status;
    private int year;
    private int month;

    public InvoiceCallStatistic() {
    }

    public InvoiceCallStatistic(int duration, int billsec, String telco, String status, int year, int month) {
        this.duration = duration;
        this.billsec = billsec;
        this.telco = telco;
        this.status = status;
        this.year = year;
        this.month = month;
    }

    @Override
    public String toString() {
        return "InvoiceCallStatistic{" + "duration=" + duration + ", billsec=" + billsec + ", telco=" + telco + ", status=" + status + ", year=" + year + ", month=" + month + '}';
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getBillsec() {
        return billsec;
    }

    public void setBillsec(int billsec) {
        this.billsec = billsec;
    }

    public String getTelco() {
        return telco;
    }

    public void setTelco(String telco) {
        this.telco = telco;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
