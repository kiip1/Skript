package ch.njol.skript;

import ch.njol.skript.entity.AxolotlData;
import ch.njol.skript.entity.BeeData;
import ch.njol.skript.entity.BoatChestData;
import ch.njol.skript.entity.BoatData;
import ch.njol.skript.entity.CatData;
import ch.njol.skript.entity.CreeperData;
import ch.njol.skript.entity.DroppedItemData;
import ch.njol.skript.entity.EndermanData;
import ch.njol.skript.entity.EntityData;
import ch.njol.skript.entity.EntityType;
import ch.njol.skript.entity.FallingBlockData;
import ch.njol.skript.entity.FishData;
import ch.njol.skript.entity.FoxData;
import ch.njol.skript.entity.FrogData;
import ch.njol.skript.entity.GoatData;
import ch.njol.skript.entity.LlamaData;
import ch.njol.skript.entity.MinecartData;
import ch.njol.skript.entity.MooshroomData;
import ch.njol.skript.entity.OcelotData;
import ch.njol.skript.entity.PandaData;
import ch.njol.skript.entity.ParrotData;
import ch.njol.skript.entity.PigData;
import ch.njol.skript.entity.PlayerData;
import ch.njol.skript.entity.RabbitData;
import ch.njol.skript.entity.SheepData;
import ch.njol.skript.entity.SimpleEntityData;
import ch.njol.skript.entity.ThrownPotionData;
import ch.njol.skript.entity.TropicalFishData;
import ch.njol.skript.entity.VillagerData;
import ch.njol.skript.entity.WolfData;
import ch.njol.skript.entity.XpOrbData;
import ch.njol.skript.entity.ZombieVillagerData;

/**
 * This code was automatically generated. DO NOT EDIT!
 */
public enum Entities {
	AXOLOTL_DATA(AxolotlData.class),

	BEE_DATA(BeeData.class),

	BOAT_CHEST_DATA(BoatChestData.class),

	BOAT_DATA(BoatData.class),

	CAT_DATA(CatData.class),

	CREEPER_DATA(CreeperData.class),

	DROPPED_ITEM_DATA(DroppedItemData.class),

	ENDERMAN_DATA(EndermanData.class),

	ENTITY_DATA(EntityData.class),

	ENTITY_TYPE(EntityType.class),

	FALLING_BLOCK_DATA(FallingBlockData.class),

	FISH_DATA(FishData.class),

	FOX_DATA(FoxData.class),

	FROG_DATA(FrogData.class),

	GOAT_DATA(GoatData.class),

	LLAMA_DATA(LlamaData.class),

	MINECART_DATA(MinecartData.class),

	MOOSHROOM_DATA(MooshroomData.class),

	OCELOT_DATA(OcelotData.class),

	PANDA_DATA(PandaData.class),

	PARROT_DATA(ParrotData.class),

	PIG_DATA(PigData.class),

	PLAYER_DATA(PlayerData.class),

	RABBIT_DATA(RabbitData.class),

	SHEEP_DATA(SheepData.class),

	SIMPLE_ENTITY_DATA(SimpleEntityData.class),

	THROWN_POTION_DATA(ThrownPotionData.class),

	TROPICAL_FISH_DATA(TropicalFishData.class),

	VILLAGER_DATA(VillagerData.class),

	WOLF_DATA(WolfData.class),

	XP_ORB_DATA(XpOrbData.class),

	ZOMBIE_VILLAGER_DATA(ZombieVillagerData.class);

	Entities(Class clazz) {
		try {
			Class.forName(clazz.getName());
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
	}

	public static void init() {
	}
}
