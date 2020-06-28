package com.docker.design.recycledrg;


/**
 * Created by kelin on 15-8-4.
 */

@FunctionalInterface
public interface ReponseReplayCommandV2<T, R, R2> {
    T exectue(R r, R2 r2);
}
