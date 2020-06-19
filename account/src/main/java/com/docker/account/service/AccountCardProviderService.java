package com.docker.account.service;

import com.docker.commonapi.service.CardProviderService;
import com.google.auto.service.AutoService;

@AutoService(CardProviderService.class)
public class AccountCardProviderService implements CardProviderService {

    @Override
    public int getInitLevel() {
        return 1;
    }
}
