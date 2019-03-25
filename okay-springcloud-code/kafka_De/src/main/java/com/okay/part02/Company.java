package com.okay.part02;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by OKali on 2019/3/25.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Company {

    private String name;

    private String address;
}
