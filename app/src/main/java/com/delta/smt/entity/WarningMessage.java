package com.delta.smt.entity;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * @description :
 * @autHor :  V.Wenju.Tian
 * @date : 2016/12/26 14:19
 */


public class WarningMessage<T extends Parcelable> implements Parcelable {

    private static final String DATA_KEY = "message";
    /**
     * type : 9
     * message : [{"work_order_id":0,"line_name":"T07","work_order":"2311701218/2316","side":"B","product_name_main":"DPS-495BB A","product_name":"DPS-495BB A","online_plan_start_time":"","status":204}]
     */

    private int type;
    private List<T> message;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public List<T> getMessage() {
        return message;
    }

    public void setMessage(List<T> message) {
        this.message = message;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public String toString() {
        return "WarningMessage{" +
                "type=" + type +
                ", message=" + message +
                '}';
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.type);
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(DATA_KEY, (ArrayList<? extends Parcelable>) message);
        dest.writeBundle(bundle);
        // dest.writeTypedList(this.message);
    }

    public WarningMessage() {
    }

    protected WarningMessage(Parcel in) {
        this.type = in.readInt();

        this.message = in.readBundle().getParcelableArrayList(DATA_KEY);
    }

    public static final Creator<WarningMessage> CREATOR = new Creator<WarningMessage>() {
        @Override
        public WarningMessage createFromParcel(Parcel source) {
            return new WarningMessage(source);
        }

        @Override
        public WarningMessage[] newArray(int size) {
            return new WarningMessage[size];
        }
    };


}
