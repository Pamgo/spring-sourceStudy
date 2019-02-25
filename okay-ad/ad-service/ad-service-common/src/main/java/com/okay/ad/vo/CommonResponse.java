package com.okay.ad.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Created by OKali on 2019/1/19.
 */
@Data   // 设置getter/setter
@NoArgsConstructor   // 设置无惨构造函数
@AllArgsConstructor  // 设置全参构造函数
public class CommonResponse<T> implements Serializable {

    private Integer code;
    private String message;
    private T data;

    public  CommonResponse(Integer code, String message) {
        this.code = code;
        this.message = message;
    }


}
