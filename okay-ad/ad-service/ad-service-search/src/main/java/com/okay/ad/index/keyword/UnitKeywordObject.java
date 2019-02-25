package com.okay.ad.index.keyword;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NegativeOrZero;

/**
 * Created by OKali on 2019/1/24.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UnitKeywordObject {

    private Long unitId;
    private String keyword;

}
