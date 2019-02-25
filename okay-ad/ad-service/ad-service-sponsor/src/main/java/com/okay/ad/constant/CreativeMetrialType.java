package com.okay.ad.constant;

import lombok.Getter;

/**
 *
 * Created by OKali on 2019/1/19.
 */
@Getter
public enum CreativeMetrialType {

    JPG(1, "jpg"),
    BMP(2, "bmp"),

    MP4(3, "mp3"),
    AVI(4, "avi"),

    TXT(5, "txt");

    private int type;
    private String desc;

    CreativeMetrialType(int type, String desc) {
        this.type = type;
        this.desc = desc;
    }

}
