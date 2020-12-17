package vn.htcjsc.web.banksim.service;

import java.util.ArrayList;
import vn.htcjsc.web.banksim.dao.UserActionDaoIF;
import vn.htcjsc.web.banksim.model.UserActionLog;

public interface UserActionService extends UserActionDaoIF {

    public ArrayList<UserActionLog> getSomeInfoUsername();

    public ArrayList<UserActionLog> getSomeInfoTableAction();

    public ArrayList<UserActionLog> getSomeInfoActionType();
}
