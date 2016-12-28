package com.delta.smt.manager;

import java.util.HashMap;
import java.util.Map;

/**
 * @description :
 * @autHor :  V.Wenju.Tian
 * @date : 2016/12/27 15:00
 */


public class WarningManger {


    private Map<Integer, Class> maps = new HashMap<>();
    private boolean isRecieve = true;

    public void setRecieve(boolean recieve) {
        isRecieve = recieve;
    }

    public boolean isRecieve() {
        return isRecieve;
    }

    public void addWarning(int type, Class mclass) {
        maps.put(type, mclass);
    }

    public Class getWaringCalss(int Type) {
        if (!maps.keySet().contains(Type)) {
            return null;
        }
        return maps.get(Type);
    }

    public Map<Integer, Class> getMaps() {
        return maps;
    }

    public static WarningManger getInstance() {
        return SingletonHolder.sInstance;
    }

    private static class SingletonHolder {
        static WarningManger sInstance = new WarningManger();
    }
}
