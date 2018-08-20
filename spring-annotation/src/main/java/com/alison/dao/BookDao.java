package com.alison.dao;

import org.springframework.stereotype.Repository;

/**
 * Created by OKali on 2018/8/12.
 */
// 默认id为bookDao
@Repository
public class BookDao {

    private String label = "1";

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return "BookDao{" +
                "label='" + label + '\'' +
                '}';
    }
}
