package com.okay.ad.index.creative;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by Qinyi.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreativeObject {

    private Long adId;  // 广告id、创意id
    private String name; // 创意名称
    private Integer type; // 创意类型，png等
    private Integer materialType; //
    private Integer height;
    private Integer width;
    private Integer auditStatus; // 审核状态
    private String adUrl;  // 广告url

    public void update(CreativeObject newObject) {

        if (null != newObject.getAdId()) {
            this.adId = newObject.getAdId();
        }
        if (null != newObject.getName()) {
            this.name = newObject.getName();
        }
        if (null != newObject.getType()) {
            this.type = newObject.getType();
        }
        if (null != newObject.getMaterialType()) {
            this.materialType = newObject.getMaterialType();
        }
        if (null != newObject.getHeight()) {
            this.height = newObject.getHeight();
        }
        if (null != newObject.getWidth()) {
            this.width = newObject.getWidth();
        }
        if (null != newObject.getAuditStatus()) {
            this.auditStatus = newObject.getAuditStatus();
        }
        if (null != newObject.getAdUrl()) {
            this.adUrl = newObject.getAdUrl();
        }
    }

}
