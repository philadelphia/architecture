package com.example.app.entity;

import com.example.libs.adapter.TimeEntity;

import java.util.List;

/**
 * @description :
 * @autHor :  V.Wenju.Tian
 * @date : 2017/1/17 15:18
 */


public class FaultMessage {


    /**
     * code : 0
     * msg : Success
     * rows : {"failures":[{"exception_name":"贴片机故障-贴片","line":"ST01","duration_time":61662,"process":"ICT","child_exception_name":"贴片","exception_code":"T-0001"}],"filters":[{"main_exception_name":"贴片机故障"},{"main_exception_name":"回焊炉故障"},{"main_exception_name":"AOI故障"},{"main_exception_name":"ICT故障"}]}
     */

    private String code;
    private String msg;
    private RowsBean rows;

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

    public RowsBean getRows() {
        return rows;
    }

    public void setRows(RowsBean rows) {
        this.rows = rows;
    }

    public static class RowsBean {
        private List<FailuresBean> failures;
        private List<FiltersBean> filters;

        public List<FailuresBean> getFailures() {
            return failures;
        }

        public void setFailures(List<FailuresBean> failures) {
            this.failures = failures;
        }

        public List<FiltersBean> getFilters() {
            return filters;
        }

        public void setFilters(List<FiltersBean> filters) {
            this.filters = filters;
        }

        public static class FailuresBean extends TimeEntity {
            /**
             * exception_name : 贴片机故障-贴片
             * line : ST01
             * duration_time : 61662
             * process : ICT
             * child_exception_name : 贴片
             * exception_code : T-0001
             */

            private String exception_name;
            private String line;
            private double duration_time;
            private String process;
            private String child_exception_name;
            private String exception_code;

            public String getException_name() {
                return exception_name;
            }

            public void setException_name(String exception_name) {
                this.exception_name = exception_name;
            }

            public String getLine() {
                return line;
            }

            public void setLine(String line) {
                this.line = line;
            }

            public double getDuration_time() {
                return duration_time;
            }

            public void setDuration_time(int duration_time) {
                this.duration_time = duration_time;
            }

            public String getProcess() {
                return process;
            }

            public void setProcess(String process) {
                this.process = process;
            }

            public String getChild_exception_name() {
                return child_exception_name;
            }

            public void setChild_exception_name(String child_exception_name) {
                this.child_exception_name = child_exception_name;
            }

            public String getException_code() {
                return exception_code;
            }

            public void setException_code(String exception_code) {
                this.exception_code = exception_code;
            }
        }

        public static class FiltersBean {
            /**
             * main_exception_name : 贴片机故障
             */

            private String main_exception_name;

            public String getMain_exception_name() {
                return main_exception_name;
            }

            public void setMain_exception_name(String main_exception_name) {
                this.main_exception_name = main_exception_name;
            }
        }
    }
}
