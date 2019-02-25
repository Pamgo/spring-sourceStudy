package com.okay.ad.dao;

import com.okay.ad.entity.AdUser;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by OKali on 2019/1/19.
 */
public interface AdUserRepository extends JpaRepository<AdUser, Long> {

    /**
     * 根据用户名查找用户记录
     * @param username
     * @return
     */
    AdUser findByUsername(String username);
}
