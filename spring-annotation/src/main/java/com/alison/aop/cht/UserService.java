package com.alison.aop.cht;

/**
 * Created by OKali on 2019/1/5.
 */
public interface UserService {

    User createUser(String firstName, String lastName, int age);

    User queryUser();
}
