package com.delta.smt.entity;

import java.util.List;

/**
 * Created by Zhenyu.Liu on 2017/1/19.
 */

public class MantissaCarResult {


    /**
     * code : 0
     * msg : Success
     * rows : [{"msg":"RP-CAR-A01"}]
     */

    private String code;
    private String msg;
    private List<MantissaCar> rows;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<MantissaCar> getRows() {
        return rows;
    }

    public void setRows(List<MantissaCar> rows) {
        this.rows = rows;
    }

    public static class MantissaCar {
        /**
         * msg : RP-CAR-A01
         */

        private String msg;

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }
    }
}
