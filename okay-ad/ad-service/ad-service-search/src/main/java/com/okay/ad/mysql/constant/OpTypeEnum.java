package com.okay.ad.mysql.constant;

import com.github.shyiko.mysql.binlog.event.EventType;

/**
 * Created by OKali on 2019/1/29.
 */
public enum OpTypeEnum {

    ADD,
    UPDATE,
    DELETE,
    OTHRE;

    public static OpTypeEnum to(EventType eventType) {
        switch (eventType) {
            case EXT_WRITE_ROWS:
                return ADD;
            case EXT_UPDATE_ROWS:
                return UPDATE;
            case EXT_DELETE_ROWS:
                return DELETE;
            default:
                return OTHRE;
        }
    }
}
