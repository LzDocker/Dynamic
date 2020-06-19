package com.docker.commonapi.card;

import android.util.Log;

import com.docker.commonapi.service.CardProviderService;

import java.util.ServiceLoader;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class CardFactory {

    @Inject
    public CardFactory() {
    }

    public void getCardList() {
        ServiceLoader<CardProviderService> cardProviderServices = ServiceLoader.load(CardProviderService.class);
        for (CardProviderService cardProviderService : cardProviderServices) {
            Log.d("TAG", "getCardList: ============" + cardProviderService.getInitLevel());
        }
    }

}
