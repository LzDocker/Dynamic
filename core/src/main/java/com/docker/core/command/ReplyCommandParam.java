package com.docker.core.command;


import java.io.Serializable;

/**
 * Created by kelin on 15-8-4.
 */

@FunctionalInterface
public interface ReplyCommandParam<T> extends Serializable {

    void exectue(T t);


}
