package com.okay.ad.sender;

import com.okay.ad.mysql.dto.MySqlRowData;

/**
 * Created by OKali on 2019/2/1.
 */
public interface ISender {

    void sender(MySqlRowData rowData);
}
