package org.cloud.crm.entity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class B {

    @Autowired
    A a;
//
//    public B(A a) {
//        this.a = a;
//    }
}
