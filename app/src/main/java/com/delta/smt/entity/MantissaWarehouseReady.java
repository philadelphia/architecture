package com.delta.smt.entity;

import com.delta.libs.adapter.TimeEntity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Zhenyu.Liu on 2016/12/22.
 */

public class MantissaWarehouseReady implements Serializable {

    /**
     * code : 0
     * msg : Success
     * rows : [{"line_name":"T15","work_order":"2311700546","side":"A","status":1,"remain_time":129357}]
     */

    private String code;
    private String msg;
    /**
     * line_name : T15
     * work_order : 2311700546
     * side : A
     * status : 1
     * remain_time : 129357
     */

    private List<RowsBean> rows;

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

    public List<RowsBean> getRows() {
        return rows;
    }

    public void setRows(List<RowsBean> rows) {
        this.rows = rows;
    }

    public static class RowsBean extends TimeEntity implements Serializable {
        private String line_name;
        private String work_order;
        private String side;
        private int status;
        private int remain_time;

        public String getLine_name() {
            return line_name;
        }

        public void setLine_name(String line_name) {
            this.line_name = line_name;
        }

        public String getWork_order() {
            return work_order;
        }

        public void setWork_order(String work_order) {
            this.work_order = work_order;
        }

        public String getSide() {
            return side;
        }

        public void setSide(String side) {
            this.side = side;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getRemain_time() {
            return remain_time;
        }

        public void setRemain_time(int remain_time) {
            this.remain_time = remain_time;
        }
    }
}
