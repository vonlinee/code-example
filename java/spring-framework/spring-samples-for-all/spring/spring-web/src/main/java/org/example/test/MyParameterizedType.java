package org.example.test;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class MyParameterizedType implements ParameterizedType {

    private final Class raw;
    private final Type[] args;
    private final Type owner;

    public MyParameterizedType(Class raw, Type[] args, Type owner) {
        this.raw = raw;
        this.args = args != null ? args : new Type[0];
        this.owner = owner;
    }

    @Override
    public Type[] getActualTypeArguments() {
        return args;
    }

    @Override
    public Type getRawType() {
        return raw;
    }

    @Override
    public Type getOwnerType() {
        return owner;
    }
}