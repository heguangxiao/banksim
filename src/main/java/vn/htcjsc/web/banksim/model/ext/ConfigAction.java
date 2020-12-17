/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vn.htcjsc.web.banksim.model.ext;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.util.ArrayList;
import java.util.List;
import vn.htcjsc.web.banksim.config.MyConfig;

/**
 *
 * @author tuanp
 */
public class ConfigAction {

    public ConfigAction() {
        this.code = "";
        this.desc = "Unknow";
        this.status = 0;
    }

    public ConfigAction(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static ArrayList<ConfigAction> toListModel() {
        ArrayList<ConfigAction> _myAction = new ArrayList<>();
        for (ACTION one : ACTION.values()) {
            ConfigAction _status = new ConfigAction(one.code, one.desc);
            _myAction.add(_status);
        }
        return _myAction;
    }

    public static ArrayList<ConfigAction> toListConfig() {
        ArrayList<ConfigAction> list = new ArrayList<>();
        for (ACTION one : ACTION.values()) {
            ConfigAction oneRole = new ConfigAction();
            oneRole.setCode(one.code);
            oneRole.setDesc(one.desc);
            oneRole.setStatus(0);   // Default Disable
            list.add(oneRole);
        }
        return list;
    }

    public static ArrayList<ConfigAction> reBuildAction(ArrayList<ConfigAction> moduleConf) {
        ArrayList<ConfigAction> rootAct = toListConfig();
        for (ConfigAction one : moduleConf) {
            if (one.getStatus() == MyConfig.STATUS.ACTIVE.val) {
                updateRootAct(rootAct, one);
            }
        }
        return rootAct;
    }

    private static void updateRootAct(ArrayList<ConfigAction> rootAct, ConfigAction one) {
        for (ConfigAction oneRoot : rootAct) {
            if (one.getCode().equals(oneRoot.getCode())) {
                oneRoot.setStatus(MyConfig.STATUS.ACTIVE.val);
                break;
            }
        }
    }

    public static enum ACTION {
        viewShort("Xem ngắn gọn"),
        viewFull("Xem đầy đủ"),
        add("Thêm mới"),
        edit("Cập nhật"),
        del("Xóa"),
        delEver("Xóa vĩnh viễn"),
        recycle("Truy cập dữ liệu xóa"),
        restore("Khôi phục dữ liệu xóa"),
        upload("Tải lên"),
        download("Tải xuống"),
        release("Phát hành"),
        reject("Từ chối"),
        print("In ấn"),
        importD("Nhập dữ liệu"),
        export("Xuất dữ liệu"),
        confRole("Cấp quyền"),
        config("Cấu hình"),
        topup("Nạp tiền"),
        topupPhut("Nạp phút"),
        ;
        public String code;
        public String desc;

        private ACTION(String desc) {
            this.desc = desc;
            this.code = this.name();
        }
    }

    public static ArrayList<String> listNameAction() {
        ArrayList<String> result = new ArrayList<>();
        for (ACTION one : ACTION.values()) {
            result.add(one.name());
        }
        return result;
    }

    public static String findDesc(String code) {
        String _desc = "";
        try {
            for (ACTION one : ACTION.values()) {
                if (one.code.equals(code)) {
                    _desc = one.desc;
                    break;
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return _desc;
    }
    
    private String code;
    private String desc;
    private int status;
    private int actId;
    private int checkUser;

    public int getCheckUser() {
        return checkUser;
    }

    public void setCheckUser(int checkUser) {
        this.checkUser = checkUser;
    }

    public int getActId() {
        return actId;
    }

    public void setActId(int actId) {
        this.actId = actId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public static ArrayList<ConfigAction> toObjec(String data) {
        ArrayList<ConfigAction> result = new ArrayList<>();
        try {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            result = gson.fromJson(data, new TypeToken<List<ConfigAction>>() {
            }.getType());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
