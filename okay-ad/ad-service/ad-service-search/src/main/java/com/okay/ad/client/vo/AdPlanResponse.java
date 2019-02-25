package com.okay.ad.client.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * Created by OKali on 2019/1/20.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdPlanResponse {

    private Long id;
    private Long userId;
    private String planName;
    private Integer planStatus;
    private Date startDate;
    private Date endDate;
    private Date createTime;
    private Date updateTime;

}
