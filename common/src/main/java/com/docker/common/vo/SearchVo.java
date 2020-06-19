package com.docker.common.vo;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

public class SearchVo implements Serializable {

    HashMap<String, List<String>> resource = new HashMap<>();

    public HashMap<String, List<String>> getResource() {
        return resource;
    }

    public void setResource(HashMap<String, List<String>> resource) {
        this.resource = resource;
    }
}
