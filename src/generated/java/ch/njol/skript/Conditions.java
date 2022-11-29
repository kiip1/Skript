package ch.njol.skript;

import ch.njol.skript.conditions.CondAI;
import ch.njol.skript.conditions.CondAlphanumeric;
import ch.njol.skript.conditions.CondCanFly;
import ch.njol.skript.conditions.CondCanHold;
import ch.njol.skript.conditions.CondCanSee;
import ch.njol.skript.conditions.CondCancelled;
import ch.njol.skript.conditions.CondChance;
import ch.njol.skript.conditions.CondCompare;
import ch.njol.skript.conditions.CondContains;
import ch.njol.skript.conditions.CondDamageCause;
import ch.njol.skript.conditions.CondDate;
import ch.njol.skript.conditions.CondEntityIsInLiquid;
import ch.njol.skript.conditions.CondEntityIsWet;
import ch.njol.skript.conditions.CondHasClientWeather;
import ch.njol.skript.conditions.CondHasCustomModelData;
import ch.njol.skript.conditions.CondHasMetadata;
import ch.njol.skript.conditions.CondHasPotion;
import ch.njol.skript.conditions.CondHasResourcePack;
import ch.njol.skript.conditions.CondHasScoreboardTag;
import ch.njol.skript.conditions.CondIgnitionProcess;
import ch.njol.skript.conditions.CondIncendiary;
import ch.njol.skript.conditions.CondIsAlive;
import ch.njol.skript.conditions.CondIsBanned;
import ch.njol.skript.conditions.CondIsBlock;
import ch.njol.skript.conditions.CondIsBlockRedstonePowered;
import ch.njol.skript.conditions.CondIsBlocking;
import ch.njol.skript.conditions.CondIsBurning;
import ch.njol.skript.conditions.CondIsCharged;
import ch.njol.skript.conditions.CondIsEdible;
import ch.njol.skript.conditions.CondIsEmpty;
import ch.njol.skript.conditions.CondIsEnchanted;
import ch.njol.skript.conditions.CondIsFlammable;
import ch.njol.skript.conditions.CondIsFlying;
import ch.njol.skript.conditions.CondIsFrozen;
import ch.njol.skript.conditions.CondIsFuel;
import ch.njol.skript.conditions.CondIsGliding;
import ch.njol.skript.conditions.CondIsInWorld;
import ch.njol.skript.conditions.CondIsInteractable;
import ch.njol.skript.conditions.CondIsInvisible;
import ch.njol.skript.conditions.CondIsInvulnerable;
import ch.njol.skript.conditions.CondIsLoaded;
import ch.njol.skript.conditions.CondIsOccluding;
import ch.njol.skript.conditions.CondIsOfType;
import ch.njol.skript.conditions.CondIsOnGround;
import ch.njol.skript.conditions.CondIsOnline;
import ch.njol.skript.conditions.CondIsPassable;
import ch.njol.skript.conditions.CondIsPluginEnabled;
import ch.njol.skript.conditions.CondIsPoisoned;
import ch.njol.skript.conditions.CondIsRiding;
import ch.njol.skript.conditions.CondIsRiptiding;
import ch.njol.skript.conditions.CondIsSet;
import ch.njol.skript.conditions.CondIsSilent;
import ch.njol.skript.conditions.CondIsSkriptCommand;
import ch.njol.skript.conditions.CondIsSleeping;
import ch.njol.skript.conditions.CondIsSlimeChunk;
import ch.njol.skript.conditions.CondIsSneaking;
import ch.njol.skript.conditions.CondIsSolid;
import ch.njol.skript.conditions.CondIsSprinting;
import ch.njol.skript.conditions.CondIsSwimming;
import ch.njol.skript.conditions.CondIsTameable;
import ch.njol.skript.conditions.CondIsTransparent;
import ch.njol.skript.conditions.CondIsUnbreakable;
import ch.njol.skript.conditions.CondIsVectorNormalized;
import ch.njol.skript.conditions.CondIsWearing;
import ch.njol.skript.conditions.CondIsWhitelisted;
import ch.njol.skript.conditions.CondIsWithinLocation;
import ch.njol.skript.conditions.CondItemInHand;
import ch.njol.skript.conditions.CondLeashed;
import ch.njol.skript.conditions.CondMatches;
import ch.njol.skript.conditions.CondPermission;
import ch.njol.skript.conditions.CondPlayedBefore;
import ch.njol.skript.conditions.CondProjectileCanBounce;
import ch.njol.skript.conditions.CondPvP;
import ch.njol.skript.conditions.CondResourcePack;
import ch.njol.skript.conditions.CondScriptLoaded;
import ch.njol.skript.conditions.CondStartsEndsWith;
import ch.njol.skript.conditions.CondWillHatch;
import ch.njol.skript.conditions.CondWithinRadius;
import ch.njol.skript.conditions.base.PropertyCondition;

/**
 * This code was automatically generated. DO NOT EDIT!
 */
public enum Conditions {
	PROPERTY_CONDITION(PropertyCondition.class),

	COND_AI(CondAI.class),

	COND_ALPHANUMERIC(CondAlphanumeric.class),

	COND_CANCELLED(CondCancelled.class),

	COND_CAN_FLY(CondCanFly.class),

	COND_CAN_HOLD(CondCanHold.class),

	COND_CAN_SEE(CondCanSee.class),

	COND_CHANCE(CondChance.class),

	COND_COMPARE(CondCompare.class),

	COND_CONTAINS(CondContains.class),

	COND_DAMAGE_CAUSE(CondDamageCause.class),

	COND_DATE(CondDate.class),

	COND_ENTITY_IS_IN_LIQUID(CondEntityIsInLiquid.class),

	COND_ENTITY_IS_WET(CondEntityIsWet.class),

	COND_HAS_CLIENT_WEATHER(CondHasClientWeather.class),

	COND_HAS_CUSTOM_MODEL_DATA(CondHasCustomModelData.class),

	COND_HAS_METADATA(CondHasMetadata.class),

	COND_HAS_POTION(CondHasPotion.class),

	COND_HAS_RESOURCE_PACK(CondHasResourcePack.class),

	COND_HAS_SCOREBOARD_TAG(CondHasScoreboardTag.class),

	COND_IGNITION_PROCESS(CondIgnitionProcess.class),

	COND_INCENDIARY(CondIncendiary.class),

	COND_IS_ALIVE(CondIsAlive.class),

	COND_IS_BANNED(CondIsBanned.class),

	COND_IS_BLOCK(CondIsBlock.class),

	COND_IS_BLOCKING(CondIsBlocking.class),

	COND_IS_BLOCK_REDSTONE_POWERED(CondIsBlockRedstonePowered.class),

	COND_IS_BURNING(CondIsBurning.class),

	COND_IS_CHARGED(CondIsCharged.class),

	COND_IS_EDIBLE(CondIsEdible.class),

	COND_IS_EMPTY(CondIsEmpty.class),

	COND_IS_ENCHANTED(CondIsEnchanted.class),

	COND_IS_FLAMMABLE(CondIsFlammable.class),

	COND_IS_FLYING(CondIsFlying.class),

	COND_IS_FROZEN(CondIsFrozen.class),

	COND_IS_FUEL(CondIsFuel.class),

	COND_IS_GLIDING(CondIsGliding.class),

	COND_IS_INTERACTABLE(CondIsInteractable.class),

	COND_IS_INVISIBLE(CondIsInvisible.class),

	COND_IS_INVULNERABLE(CondIsInvulnerable.class),

	COND_IS_IN_WORLD(CondIsInWorld.class),

	COND_IS_LOADED(CondIsLoaded.class),

	COND_IS_OCCLUDING(CondIsOccluding.class),

	COND_IS_OF_TYPE(CondIsOfType.class),

	COND_IS_ON_GROUND(CondIsOnGround.class),

	COND_IS_ONLINE(CondIsOnline.class),

	COND_IS_PASSABLE(CondIsPassable.class),

	COND_IS_PLUGIN_ENABLED(CondIsPluginEnabled.class),

	COND_IS_POISONED(CondIsPoisoned.class),

	COND_IS_RIDING(CondIsRiding.class),

	COND_IS_RIPTIDING(CondIsRiptiding.class),

	COND_IS_SET(CondIsSet.class),

	COND_IS_SILENT(CondIsSilent.class),

	COND_IS_SKRIPT_COMMAND(CondIsSkriptCommand.class),

	COND_IS_SLEEPING(CondIsSleeping.class),

	COND_IS_SLIME_CHUNK(CondIsSlimeChunk.class),

	COND_IS_SNEAKING(CondIsSneaking.class),

	COND_IS_SOLID(CondIsSolid.class),

	COND_IS_SPRINTING(CondIsSprinting.class),

	COND_IS_SWIMMING(CondIsSwimming.class),

	COND_IS_TAMEABLE(CondIsTameable.class),

	COND_IS_TRANSPARENT(CondIsTransparent.class),

	COND_IS_UNBREAKABLE(CondIsUnbreakable.class),

	COND_IS_VECTOR_NORMALIZED(CondIsVectorNormalized.class),

	COND_IS_WEARING(CondIsWearing.class),

	COND_IS_WHITELISTED(CondIsWhitelisted.class),

	COND_IS_WITHIN_LOCATION(CondIsWithinLocation.class),

	COND_ITEM_IN_HAND(CondItemInHand.class),

	COND_LEASHED(CondLeashed.class),

	COND_MATCHES(CondMatches.class),

	COND_PERMISSION(CondPermission.class),

	COND_PLAYED_BEFORE(CondPlayedBefore.class),

	COND_PROJECTILE_CAN_BOUNCE(CondProjectileCanBounce.class),

	COND_PV_P(CondPvP.class),

	COND_RESOURCE_PACK(CondResourcePack.class),

	COND_SCRIPT_LOADED(CondScriptLoaded.class),

	COND_STARTS_ENDS_WITH(CondStartsEndsWith.class),

	COND_WILL_HATCH(CondWillHatch.class),

	COND_WITHIN_RADIUS(CondWithinRadius.class);

	Conditions(Class clazz) {
		try {
			Class.forName(clazz.getName());
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
	}

	public static void init() {
	}
}
