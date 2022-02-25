package com.orionemu.server.game.catalog.purchase.handlers;

import com.orionemu.server.game.achievements.types.AchievementType;
import com.orionemu.server.game.catalog.purchase.PurchaseHandler;
import com.orionemu.server.game.catalog.purchase.PurchaseResult;
import com.orionemu.server.game.catalog.types.CatalogItem;
import com.orionemu.server.game.pets.data.PetData;
import com.orionemu.server.game.pets.data.StaticPetProperties;
import com.orionemu.server.network.messages.outgoing.catalog.UnseenItemsMessageComposer;
import com.orionemu.server.network.messages.outgoing.user.inventory.PetInventoryMessageComposer;
import com.orionemu.server.network.sessions.Session;
import com.orionemu.server.storage.queries.pets.PetDao;
import com.google.common.collect.Lists;

import java.util.HashMap;
import java.util.List;

public class PetPurchaseHandler implements PurchaseHandler {
    @Override
    public PurchaseResult handlePurchaseData(Session session, String purchaseData, CatalogItem catalogItem, int amount) {
        String petRace = catalogItem.getDisplayName().replace("a0 pet", "");
        String[] petData = purchaseData.split("\n"); // [0:name, 1:race, 2:colour]

        if (petData.length != 3) {
            return null;
        }

        int petId = PetDao.createPet(session.getPlayer().getId(), petData[0], Integer.parseInt(petRace), Integer.parseInt(petData[1]), petData[2]);

        session.getPlayer().getAchievements().progressAchievement(AchievementType.PET_LOVER, 1);
        session.getPlayer().getPets().addPet(new PetData(petId, petData[0], 0, StaticPetProperties.DEFAULT_LEVEL, StaticPetProperties.DEFAULT_HAPPINESS, StaticPetProperties.DEFAULT_EXPERIENCE, StaticPetProperties.DEFAULT_ENERGY, session.getPlayer().getId(), session.getPlayer().getData().getUsername(), petData[2], Integer.parseInt(petData[1]), Integer.parseInt(petRace)));
        session.send(new PetInventoryMessageComposer(session.getPlayer().getPets().getPets()));

        session.send(new UnseenItemsMessageComposer(new HashMap<Integer, List<Integer>>() {{
            put(3, Lists.newArrayList(petId));
        }}));

        return null;
    }
}
