package com.example.app.entity;

/**
 * Created by Shufeng.Wu on 2017/1/4.
 */

public class ModuleUpBindingItem {

    /**
     * work_order : 2311701657
     * side : A
     * material_no : 1511542032
     * serial_no : D201702241521453001
     * feeder_id : NULL
     * slot : 01T06
     * bind_time : NULL
     * qty : 0
     * id : 0
     * t_material_id : 0
     * is_feeder_buffer : 0
     * status : 0
     * first : 0
     */

    private String work_order;
    private String side;
    private String material_no;
    private String serial_no;
    private String feeder_id;
    private String slot;
    private String bind_time;
    private int qty;
    private int id;
    private int t_material_id;
    private int is_feeder_buffer;
    private int status;
    private int first;

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

    public String getMaterial_no() {
        return material_no;
    }

    public void setMaterial_no(String material_no) {
        this.material_no = material_no;
    }

    public String getSerial_no() {
        return serial_no;
    }

    public void setSerial_no(String serial_no) {
        this.serial_no = serial_no;
    }

    public String getFeeder_id() {
        return feeder_id;
    }

    public void setFeeder_id(String feeder_id) {
        this.feeder_id = feeder_id;
    }

    public String getSlot() {
        return slot;
    }

    public void setSlot(String slot) {
        this.slot = slot;
    }

    public String getBind_time() {
        return bind_time;
    }

    public void setBind_time(String bind_time) {
        this.bind_time = bind_time;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getT_material_id() {
        return t_material_id;
    }

    public void setT_material_id(int t_material_id) {
        this.t_material_id = t_material_id;
    }

    public int getIs_feeder_buffer() {
        return is_feeder_buffer;
    }

    public void setIs_feeder_buffer(int is_feeder_buffer) {
        this.is_feeder_buffer = is_feeder_buffer;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getFirst() {
        return first;
    }

    public void setFirst(int first) {
        this.first = first;
    }

    @Override
    public String toString() {
        return "ModuleUpBindingItem{" +
                "work_order='" + work_order + '\'' +
                ", side='" + side + '\'' +
                ", material_no='" + material_no + '\'' +
                ", serial_no='" + serial_no + '\'' +
                ", feeder_id='" + feeder_id + '\'' +
                ", slot='" + slot + '\'' +
                ", bind_time='" + bind_time + '\'' +
                ", qty=" + qty +
                ", id=" + id +
                ", t_material_id=" + t_material_id +
                ", is_feeder_buffer=" + is_feeder_buffer +
                ", status=" + status +
                ", first=" + first +
                '}';
    }
}
