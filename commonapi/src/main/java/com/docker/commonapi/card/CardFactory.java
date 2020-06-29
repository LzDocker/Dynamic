package com.docker.commonapi.card;

import com.docker.common.model.card.CardMarkService;

import java.util.ServiceLoader;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class CardFactory {

    @Inject
    public CardFactory() {
    }

    public void getCardList() {
        ServiceLoader<CardMarkService> cardMarkServices = ServiceLoader.load(CardMarkService.class);
        for (CardMarkService cardMarkService : cardMarkServices) {

        }
    }

}
