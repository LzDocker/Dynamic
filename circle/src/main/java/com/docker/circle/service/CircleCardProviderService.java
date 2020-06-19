package com.docker.circle.service;

import com.docker.commonapi.service.CardProviderService;
import com.google.auto.service.AutoService;

@AutoService(CardProviderService.class)
public class CircleCardProviderService implements CardProviderService {

    @Override
    public int getInitLevel() {
        return 0;
    }


}
