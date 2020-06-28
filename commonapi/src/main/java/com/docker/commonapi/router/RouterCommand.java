package com.docker.commonapi.router;

import com.docker.design.recycledrg.ReponseReplayCommandV2;

public class RouterCommand {

    // 出发地址_到达地址
    public String commandkey;

    public ReponseReplayCommandV2 reponseReplayCommand;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RouterCommand)) return false;

        RouterCommand that = (RouterCommand) o;

        return commandkey.equals(that.commandkey);
    }

    @Override
    public int hashCode() {
        return commandkey.hashCode();
    }
}
