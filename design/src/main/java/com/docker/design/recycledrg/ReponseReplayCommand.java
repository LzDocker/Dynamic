package com.docker.design.recycledrg;


/**
 * Created by kelin on 15-8-4.
 */

@FunctionalInterface
public interface ReponseReplayCommand<T, R> {
    T exectue(R r);
}
