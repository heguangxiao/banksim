/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vn.htcjsc.web.banksim.model.ext;

/**
 *
 * @author Private
 */
public class ModelEnum {

    public ModelEnum(int val, String desc) {
        this.desc = desc;
        this.val = val;
    }

    public ModelEnum(String name, String desc) {
        this.desc = desc;
        this.name = name;
    }
    private String name;
    private String desc;
    private int val;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getVal() {
        return val;
    }

    public void setVal(int val) {
        this.val = val;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

}
