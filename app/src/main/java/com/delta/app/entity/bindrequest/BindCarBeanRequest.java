package com.delta.app.entity.bindrequest;

/**
 * @copyright : 台达电子企业管理(上海)有限公司 (c) 2017
 * @creator : YangS
 * @create-time : 2017/7/11 15:48
 * @description :绑定料车的Bean
 */

public class BindCarBeanRequest {
    private String carName;

    public String getCarName() {
        return carName;
    }

    public void setCarName(String carName) {
        this.carName = carName;
    }

    public BindCarBeanRequest(String carName) {

        this.carName = carName;
    }
}
