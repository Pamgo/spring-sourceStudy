package com.okay.ad.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang.StringUtils;

/**
 * Created by OKali on 2019/1/19.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdUnitRequest {

    private Long planId;
    private String unitName;

    private Integer positionType;
    private Long budget;

    public boolean createValidator() {
        return null != planId
                && !StringUtils.isEmpty(unitName)
                && positionType != null
                && budget != null;
    }
}
