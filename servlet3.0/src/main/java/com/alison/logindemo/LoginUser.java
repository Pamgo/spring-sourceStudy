package com.alison.logindemo;

import java.lang.annotation.*;

/**
 * Created by OKali on 2018/9/23.
 */
@Target(value = {
        ElementType.METHOD,
        ElementType.TYPE
})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LoginUser {

    
}
