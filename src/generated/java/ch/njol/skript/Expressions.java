package ch.njol.skript;

import ch.njol.skript.expressions.ExprAI;
import ch.njol.skript.expressions.ExprAbsorbedBlocks;
import ch.njol.skript.expressions.ExprAffectedEntities;
import ch.njol.skript.expressions.ExprAge;
import ch.njol.skript.expressions.ExprAllBannedEntries;
import ch.njol.skript.expressions.ExprAllCommands;
import ch.njol.skript.expressions.ExprAlphabetList;
import ch.njol.skript.expressions.ExprAltitude;
import ch.njol.skript.expressions.ExprAmount;
import ch.njol.skript.expressions.ExprAmountOfItems;
import ch.njol.skript.expressions.ExprAppliedEnchantments;
import ch.njol.skript.expressions.ExprArgument;
import ch.njol.skript.expressions.ExprArmorSlot;
import ch.njol.skript.expressions.ExprArrowKnockbackStrength;
import ch.njol.skript.expressions.ExprArrowPierceLevel;
import ch.njol.skript.expressions.ExprArrowsStuck;
import ch.njol.skript.expressions.ExprAttackCooldown;
import ch.njol.skript.expressions.ExprAttacked;
import ch.njol.skript.expressions.ExprAttacker;
import ch.njol.skript.expressions.ExprBed;
import ch.njol.skript.expressions.ExprBiome;
import ch.njol.skript.expressions.ExprBlock;
import ch.njol.skript.expressions.ExprBlockData;
import ch.njol.skript.expressions.ExprBlockHardness;
import ch.njol.skript.expressions.ExprBlockSphere;
import ch.njol.skript.expressions.ExprBlocks;
import ch.njol.skript.expressions.ExprBookAuthor;
import ch.njol.skript.expressions.ExprBookPages;
import ch.njol.skript.expressions.ExprBookTitle;
import ch.njol.skript.expressions.ExprBurnCookTime;
import ch.njol.skript.expressions.ExprChatFormat;
import ch.njol.skript.expressions.ExprChatRecipients;
import ch.njol.skript.expressions.ExprChestInventory;
import ch.njol.skript.expressions.ExprChunk;
import ch.njol.skript.expressions.ExprClicked;
import ch.njol.skript.expressions.ExprClientViewDistance;
import ch.njol.skript.expressions.ExprCmdCooldownInfo;
import ch.njol.skript.expressions.ExprColorOf;
import ch.njol.skript.expressions.ExprColoured;
import ch.njol.skript.expressions.ExprCommand;
import ch.njol.skript.expressions.ExprCommandInfo;
import ch.njol.skript.expressions.ExprCommandSender;
import ch.njol.skript.expressions.ExprCompassTarget;
import ch.njol.skript.expressions.ExprCoordinate;
import ch.njol.skript.expressions.ExprCreeperMaxFuseTicks;
import ch.njol.skript.expressions.ExprCursorSlot;
import ch.njol.skript.expressions.ExprCustomModelData;
import ch.njol.skript.expressions.ExprDamage;
import ch.njol.skript.expressions.ExprDamageCause;
import ch.njol.skript.expressions.ExprDamagedItem;
import ch.njol.skript.expressions.ExprDateAgoLater;
import ch.njol.skript.expressions.ExprDefaultValue;
import ch.njol.skript.expressions.ExprDifference;
import ch.njol.skript.expressions.ExprDifficulty;
import ch.njol.skript.expressions.ExprDirection;
import ch.njol.skript.expressions.ExprDistance;
import ch.njol.skript.expressions.ExprDrops;
import ch.njol.skript.expressions.ExprDropsOfBlock;
import ch.njol.skript.expressions.ExprDurability;
import ch.njol.skript.expressions.ExprEgg;
import ch.njol.skript.expressions.ExprElement;
import ch.njol.skript.expressions.ExprEnchantItem;
import ch.njol.skript.expressions.ExprEnchantingExpCost;
import ch.njol.skript.expressions.ExprEnchantmentBonus;
import ch.njol.skript.expressions.ExprEnchantmentLevel;
import ch.njol.skript.expressions.ExprEnchantmentOffer;
import ch.njol.skript.expressions.ExprEnchantmentOfferCost;
import ch.njol.skript.expressions.ExprEnchantments;
import ch.njol.skript.expressions.ExprEnderChest;
import ch.njol.skript.expressions.ExprEntities;
import ch.njol.skript.expressions.ExprEntity;
import ch.njol.skript.expressions.ExprEntityAttribute;
import ch.njol.skript.expressions.ExprEntityTamer;
import ch.njol.skript.expressions.ExprEventCancelled;
import ch.njol.skript.expressions.ExprEventExpression;
import ch.njol.skript.expressions.ExprExhaustion;
import ch.njol.skript.expressions.ExprExperience;
import ch.njol.skript.expressions.ExprExplodedBlocks;
import ch.njol.skript.expressions.ExprExplosionBlockYield;
import ch.njol.skript.expressions.ExprExplosionYield;
import ch.njol.skript.expressions.ExprExplosiveYield;
import ch.njol.skript.expressions.ExprEyeLocation;
import ch.njol.skript.expressions.ExprFacing;
import ch.njol.skript.expressions.ExprFallDistance;
import ch.njol.skript.expressions.ExprFertilizedBlocks;
import ch.njol.skript.expressions.ExprFilter;
import ch.njol.skript.expressions.ExprFinalDamage;
import ch.njol.skript.expressions.ExprFireTicks;
import ch.njol.skript.expressions.ExprFireworkEffect;
import ch.njol.skript.expressions.ExprFlightMode;
import ch.njol.skript.expressions.ExprFoodLevel;
import ch.njol.skript.expressions.ExprFormatDate;
import ch.njol.skript.expressions.ExprFreezeTicks;
import ch.njol.skript.expressions.ExprFurnaceSlot;
import ch.njol.skript.expressions.ExprGameMode;
import ch.njol.skript.expressions.ExprGameRule;
import ch.njol.skript.expressions.ExprGlidingState;
import ch.njol.skript.expressions.ExprGlowing;
import ch.njol.skript.expressions.ExprGravity;
import ch.njol.skript.expressions.ExprHanging;
import ch.njol.skript.expressions.ExprHash;
import ch.njol.skript.expressions.ExprHatchingNumber;
import ch.njol.skript.expressions.ExprHatchingType;
import ch.njol.skript.expressions.ExprHealAmount;
import ch.njol.skript.expressions.ExprHealReason;
import ch.njol.skript.expressions.ExprHealth;
import ch.njol.skript.expressions.ExprHiddenPlayers;
import ch.njol.skript.expressions.ExprHighestSolidBlock;
import ch.njol.skript.expressions.ExprHostname;
import ch.njol.skript.expressions.ExprHotbarButton;
import ch.njol.skript.expressions.ExprHotbarSlot;
import ch.njol.skript.expressions.ExprHoverList;
import ch.njol.skript.expressions.ExprHumidity;
import ch.njol.skript.expressions.ExprIP;
import ch.njol.skript.expressions.ExprIdOf;
import ch.njol.skript.expressions.ExprIndexOf;
import ch.njol.skript.expressions.ExprIndices;
import ch.njol.skript.expressions.ExprInventory;
import ch.njol.skript.expressions.ExprInventoryAction;
import ch.njol.skript.expressions.ExprInventoryInfo;
import ch.njol.skript.expressions.ExprInventorySlot;
import ch.njol.skript.expressions.ExprItem;
import ch.njol.skript.expressions.ExprItemAmount;
import ch.njol.skript.expressions.ExprItemFrameSlot;
import ch.njol.skript.expressions.ExprItemWithCustomModelData;
import ch.njol.skript.expressions.ExprItemWithLore;
import ch.njol.skript.expressions.ExprItemsIn;
import ch.njol.skript.expressions.ExprJoinSplit;
import ch.njol.skript.expressions.ExprLanguage;
import ch.njol.skript.expressions.ExprLastAttacker;
import ch.njol.skript.expressions.ExprLastColor;
import ch.njol.skript.expressions.ExprLastDamage;
import ch.njol.skript.expressions.ExprLastDamageCause;
import ch.njol.skript.expressions.ExprLastLoadedServerIcon;
import ch.njol.skript.expressions.ExprLastLoginTime;
import ch.njol.skript.expressions.ExprLastResourcePackResponse;
import ch.njol.skript.expressions.ExprLastSpawnedEntity;
import ch.njol.skript.expressions.ExprLeashHolder;
import ch.njol.skript.expressions.ExprLength;
import ch.njol.skript.expressions.ExprLevel;
import ch.njol.skript.expressions.ExprLevelProgress;
import ch.njol.skript.expressions.ExprLightLevel;
import ch.njol.skript.expressions.ExprLocation;
import ch.njol.skript.expressions.ExprLocationAt;
import ch.njol.skript.expressions.ExprLocationFromVector;
import ch.njol.skript.expressions.ExprLocationOf;
import ch.njol.skript.expressions.ExprLocationVectorOffset;
import ch.njol.skript.expressions.ExprLoopValue;
import ch.njol.skript.expressions.ExprLore;
import ch.njol.skript.expressions.ExprMOTD;
import ch.njol.skript.expressions.ExprMaxDurability;
import ch.njol.skript.expressions.ExprMaxFreezeTicks;
import ch.njol.skript.expressions.ExprMaxHealth;
import ch.njol.skript.expressions.ExprMaxMinecartSpeed;
import ch.njol.skript.expressions.ExprMaxPlayers;
import ch.njol.skript.expressions.ExprMaxStack;
import ch.njol.skript.expressions.ExprMe;
import ch.njol.skript.expressions.ExprMendingRepairAmount;
import ch.njol.skript.expressions.ExprMessage;
import ch.njol.skript.expressions.ExprMetadata;
import ch.njol.skript.expressions.ExprMiddleOfLocation;
import ch.njol.skript.expressions.ExprMinecartDerailedFlyingVelocity;
import ch.njol.skript.expressions.ExprMoonPhase;
import ch.njol.skript.expressions.ExprName;
import ch.njol.skript.expressions.ExprNamed;
import ch.njol.skript.expressions.ExprNearestEntity;
import ch.njol.skript.expressions.ExprNoDamageTicks;
import ch.njol.skript.expressions.ExprNow;
import ch.njol.skript.expressions.ExprNumberOfCharacters;
import ch.njol.skript.expressions.ExprNumbers;
import ch.njol.skript.expressions.ExprOfflinePlayers;
import ch.njol.skript.expressions.ExprOnlinePlayersCount;
import ch.njol.skript.expressions.ExprOpenedInventory;
import ch.njol.skript.expressions.ExprParse;
import ch.njol.skript.expressions.ExprParseError;
import ch.njol.skript.expressions.ExprPassenger;
import ch.njol.skript.expressions.ExprPermissions;
import ch.njol.skript.expressions.ExprPickupDelay;
import ch.njol.skript.expressions.ExprPing;
import ch.njol.skript.expressions.ExprPlain;
import ch.njol.skript.expressions.ExprPlayerProtocolVersion;
import ch.njol.skript.expressions.ExprPlayerViewDistance;
import ch.njol.skript.expressions.ExprPlayerWeather;
import ch.njol.skript.expressions.ExprPlayerlistHeaderFooter;
import ch.njol.skript.expressions.ExprPlugins;
import ch.njol.skript.expressions.ExprPortal;
import ch.njol.skript.expressions.ExprPotionEffect;
import ch.njol.skript.expressions.ExprPotionEffectTier;
import ch.njol.skript.expressions.ExprPotionEffects;
import ch.njol.skript.expressions.ExprProjectileBounceState;
import ch.njol.skript.expressions.ExprProjectileCriticalState;
import ch.njol.skript.expressions.ExprProtocolVersion;
import ch.njol.skript.expressions.ExprPushedBlocks;
import ch.njol.skript.expressions.ExprRandom;
import ch.njol.skript.expressions.ExprRandomNumber;
import ch.njol.skript.expressions.ExprRandomUUID;
import ch.njol.skript.expressions.ExprRawName;
import ch.njol.skript.expressions.ExprRawString;
import ch.njol.skript.expressions.ExprRedstoneBlockPower;
import ch.njol.skript.expressions.ExprRemainingAir;
import ch.njol.skript.expressions.ExprRespawnLocation;
import ch.njol.skript.expressions.ExprReversedList;
import ch.njol.skript.expressions.ExprRound;
import ch.njol.skript.expressions.ExprSaturation;
import ch.njol.skript.expressions.ExprScoreboardTags;
import ch.njol.skript.expressions.ExprScript;
import ch.njol.skript.expressions.ExprScripts;
import ch.njol.skript.expressions.ExprSeaLevel;
import ch.njol.skript.expressions.ExprSeed;
import ch.njol.skript.expressions.ExprServerIcon;
import ch.njol.skript.expressions.ExprSets;
import ch.njol.skript.expressions.ExprShooter;
import ch.njol.skript.expressions.ExprShuffledList;
import ch.njol.skript.expressions.ExprSignText;
import ch.njol.skript.expressions.ExprSkull;
import ch.njol.skript.expressions.ExprSlotIndex;
import ch.njol.skript.expressions.ExprSortedList;
import ch.njol.skript.expressions.ExprSpawn;
import ch.njol.skript.expressions.ExprSpawnReason;
import ch.njol.skript.expressions.ExprSpawnerType;
import ch.njol.skript.expressions.ExprSpecialNumber;
import ch.njol.skript.expressions.ExprSpectatorTarget;
import ch.njol.skript.expressions.ExprSpeed;
import ch.njol.skript.expressions.ExprStringCase;
import ch.njol.skript.expressions.ExprSubstring;
import ch.njol.skript.expressions.ExprTPS;
import ch.njol.skript.expressions.ExprTamer;
import ch.njol.skript.expressions.ExprTarget;
import ch.njol.skript.expressions.ExprTargetedBlock;
import ch.njol.skript.expressions.ExprTeleportCause;
import ch.njol.skript.expressions.ExprTemperature;
import ch.njol.skript.expressions.ExprTernary;
import ch.njol.skript.expressions.ExprTime;
import ch.njol.skript.expressions.ExprTimePlayed;
import ch.njol.skript.expressions.ExprTimeSince;
import ch.njol.skript.expressions.ExprTimeState;
import ch.njol.skript.expressions.ExprTimes;
import ch.njol.skript.expressions.ExprTool;
import ch.njol.skript.expressions.ExprTypeOf;
import ch.njol.skript.expressions.ExprUUID;
import ch.njol.skript.expressions.ExprUnbreakable;
import ch.njol.skript.expressions.ExprUnixDate;
import ch.njol.skript.expressions.ExprUnixTicks;
import ch.njol.skript.expressions.ExprValue;
import ch.njol.skript.expressions.ExprVectorAngleBetween;
import ch.njol.skript.expressions.ExprVectorArithmetic;
import ch.njol.skript.expressions.ExprVectorBetweenLocations;
import ch.njol.skript.expressions.ExprVectorCrossProduct;
import ch.njol.skript.expressions.ExprVectorCylindrical;
import ch.njol.skript.expressions.ExprVectorDotProduct;
import ch.njol.skript.expressions.ExprVectorFromXYZ;
import ch.njol.skript.expressions.ExprVectorFromYawAndPitch;
import ch.njol.skript.expressions.ExprVectorLength;
import ch.njol.skript.expressions.ExprVectorNormalize;
import ch.njol.skript.expressions.ExprVectorOfLocation;
import ch.njol.skript.expressions.ExprVectorRandom;
import ch.njol.skript.expressions.ExprVectorSpherical;
import ch.njol.skript.expressions.ExprVectorSquaredLength;
import ch.njol.skript.expressions.ExprVectorXYZ;
import ch.njol.skript.expressions.ExprVehicle;
import ch.njol.skript.expressions.ExprVelocity;
import ch.njol.skript.expressions.ExprVersion;
import ch.njol.skript.expressions.ExprVersionString;
import ch.njol.skript.expressions.ExprWeather;
import ch.njol.skript.expressions.ExprWhitelist;
import ch.njol.skript.expressions.ExprWorld;
import ch.njol.skript.expressions.ExprWorldEnvironment;
import ch.njol.skript.expressions.ExprWorldFromName;
import ch.njol.skript.expressions.ExprWorlds;
import ch.njol.skript.expressions.ExprXOf;
import ch.njol.skript.expressions.ExprYawPitch;
import ch.njol.skript.expressions.LitAt;
import ch.njol.skript.expressions.LitConsole;
import ch.njol.skript.expressions.LitNewLine;
import ch.njol.skript.expressions.arithmetic.ArithmeticChain;
import ch.njol.skript.expressions.arithmetic.ArithmeticGettable;
import ch.njol.skript.expressions.arithmetic.ExprArithmetic;
import ch.njol.skript.expressions.arithmetic.NumberExpressionInfo;
import ch.njol.skript.expressions.arithmetic.Operator;
import ch.njol.skript.expressions.base.EventValueExpression;
import ch.njol.skript.expressions.base.PropertyExpression;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import ch.njol.skript.expressions.base.WrapperExpression;

/**
 * This code was automatically generated. DO NOT EDIT!
 */
public enum Expressions {
	ARITHMETIC_CHAIN(ArithmeticChain.class),

	ARITHMETIC_GETTABLE(ArithmeticGettable.class),

	EXPR_ARITHMETIC(ExprArithmetic.class),

	NUMBER_EXPRESSION_INFO(NumberExpressionInfo.class),

	OPERATOR(Operator.class),

	EVENT_VALUE_EXPRESSION(EventValueExpression.class),

	PROPERTY_EXPRESSION(PropertyExpression.class),

	SIMPLE_PROPERTY_EXPRESSION(SimplePropertyExpression.class),

	WRAPPER_EXPRESSION(WrapperExpression.class),

	EXPR_ABSORBED_BLOCKS(ExprAbsorbedBlocks.class),

	EXPR_AFFECTED_ENTITIES(ExprAffectedEntities.class),

	EXPR_AGE(ExprAge.class),

	EXPR_AI(ExprAI.class),

	EXPR_ALL_BANNED_ENTRIES(ExprAllBannedEntries.class),

	EXPR_ALL_COMMANDS(ExprAllCommands.class),

	EXPR_ALPHABET_LIST(ExprAlphabetList.class),

	EXPR_ALTITUDE(ExprAltitude.class),

	EXPR_AMOUNT(ExprAmount.class),

	EXPR_AMOUNT_OF_ITEMS(ExprAmountOfItems.class),

	EXPR_APPLIED_ENCHANTMENTS(ExprAppliedEnchantments.class),

	EXPR_ARGUMENT(ExprArgument.class),

	EXPR_ARMOR_SLOT(ExprArmorSlot.class),

	EXPR_ARROW_KNOCKBACK_STRENGTH(ExprArrowKnockbackStrength.class),

	EXPR_ARROW_PIERCE_LEVEL(ExprArrowPierceLevel.class),

	EXPR_ARROWS_STUCK(ExprArrowsStuck.class),

	EXPR_ATTACK_COOLDOWN(ExprAttackCooldown.class),

	EXPR_ATTACKED(ExprAttacked.class),

	EXPR_ATTACKER(ExprAttacker.class),

	EXPR_BED(ExprBed.class),

	EXPR_BIOME(ExprBiome.class),

	EXPR_BLOCK(ExprBlock.class),

	EXPR_BLOCK_DATA(ExprBlockData.class),

	EXPR_BLOCK_HARDNESS(ExprBlockHardness.class),

	EXPR_BLOCKS(ExprBlocks.class),

	EXPR_BLOCK_SPHERE(ExprBlockSphere.class),

	EXPR_BOOK_AUTHOR(ExprBookAuthor.class),

	EXPR_BOOK_PAGES(ExprBookPages.class),

	EXPR_BOOK_TITLE(ExprBookTitle.class),

	EXPR_BURN_COOK_TIME(ExprBurnCookTime.class),

	EXPR_CHAT_FORMAT(ExprChatFormat.class),

	EXPR_CHAT_RECIPIENTS(ExprChatRecipients.class),

	EXPR_CHEST_INVENTORY(ExprChestInventory.class),

	EXPR_CHUNK(ExprChunk.class),

	EXPR_CLICKED(ExprClicked.class),

	EXPR_CLIENT_VIEW_DISTANCE(ExprClientViewDistance.class),

	EXPR_CMD_COOLDOWN_INFO(ExprCmdCooldownInfo.class),

	EXPR_COLOR_OF(ExprColorOf.class),

	EXPR_COLOURED(ExprColoured.class),

	EXPR_COMMAND(ExprCommand.class),

	EXPR_COMMAND_INFO(ExprCommandInfo.class),

	EXPR_COMMAND_SENDER(ExprCommandSender.class),

	EXPR_COMPASS_TARGET(ExprCompassTarget.class),

	EXPR_COORDINATE(ExprCoordinate.class),

	EXPR_CREEPER_MAX_FUSE_TICKS(ExprCreeperMaxFuseTicks.class),

	EXPR_CURSOR_SLOT(ExprCursorSlot.class),

	EXPR_CUSTOM_MODEL_DATA(ExprCustomModelData.class),

	EXPR_DAMAGE(ExprDamage.class),

	EXPR_DAMAGE_CAUSE(ExprDamageCause.class),

	EXPR_DAMAGED_ITEM(ExprDamagedItem.class),

	EXPR_DATE_AGO_LATER(ExprDateAgoLater.class),

	EXPR_DEFAULT_VALUE(ExprDefaultValue.class),

	EXPR_DIFFERENCE(ExprDifference.class),

	EXPR_DIFFICULTY(ExprDifficulty.class),

	EXPR_DIRECTION(ExprDirection.class),

	EXPR_DISTANCE(ExprDistance.class),

	EXPR_DROPS(ExprDrops.class),

	EXPR_DROPS_OF_BLOCK(ExprDropsOfBlock.class),

	EXPR_DURABILITY(ExprDurability.class),

	EXPR_EGG(ExprEgg.class),

	EXPR_ELEMENT(ExprElement.class),

	EXPR_ENCHANTING_EXP_COST(ExprEnchantingExpCost.class),

	EXPR_ENCHANT_ITEM(ExprEnchantItem.class),

	EXPR_ENCHANTMENT_BONUS(ExprEnchantmentBonus.class),

	EXPR_ENCHANTMENT_LEVEL(ExprEnchantmentLevel.class),

	EXPR_ENCHANTMENT_OFFER(ExprEnchantmentOffer.class),

	EXPR_ENCHANTMENT_OFFER_COST(ExprEnchantmentOfferCost.class),

	EXPR_ENCHANTMENTS(ExprEnchantments.class),

	EXPR_ENDER_CHEST(ExprEnderChest.class),

	EXPR_ENTITIES(ExprEntities.class),

	EXPR_ENTITY(ExprEntity.class),

	EXPR_ENTITY_ATTRIBUTE(ExprEntityAttribute.class),

	EXPR_ENTITY_TAMER(ExprEntityTamer.class),

	EXPR_EVENT_CANCELLED(ExprEventCancelled.class),

	EXPR_EVENT_EXPRESSION(ExprEventExpression.class),

	EXPR_EXHAUSTION(ExprExhaustion.class),

	EXPR_EXPERIENCE(ExprExperience.class),

	EXPR_EXPLODED_BLOCKS(ExprExplodedBlocks.class),

	EXPR_EXPLOSION_BLOCK_YIELD(ExprExplosionBlockYield.class),

	EXPR_EXPLOSION_YIELD(ExprExplosionYield.class),

	EXPR_EXPLOSIVE_YIELD(ExprExplosiveYield.class),

	EXPR_EYE_LOCATION(ExprEyeLocation.class),

	EXPR_FACING(ExprFacing.class),

	EXPR_FALL_DISTANCE(ExprFallDistance.class),

	EXPR_FERTILIZED_BLOCKS(ExprFertilizedBlocks.class),

	EXPR_FILTER(ExprFilter.class),

	EXPR_FINAL_DAMAGE(ExprFinalDamage.class),

	EXPR_FIRE_TICKS(ExprFireTicks.class),

	EXPR_FIREWORK_EFFECT(ExprFireworkEffect.class),

	EXPR_FLIGHT_MODE(ExprFlightMode.class),

	EXPR_FOOD_LEVEL(ExprFoodLevel.class),

	EXPR_FORMAT_DATE(ExprFormatDate.class),

	EXPR_FREEZE_TICKS(ExprFreezeTicks.class),

	EXPR_FURNACE_SLOT(ExprFurnaceSlot.class),

	EXPR_GAME_MODE(ExprGameMode.class),

	EXPR_GAME_RULE(ExprGameRule.class),

	EXPR_GLIDING_STATE(ExprGlidingState.class),

	EXPR_GLOWING(ExprGlowing.class),

	EXPR_GRAVITY(ExprGravity.class),

	EXPR_HANGING(ExprHanging.class),

	EXPR_HASH(ExprHash.class),

	EXPR_HATCHING_NUMBER(ExprHatchingNumber.class),

	EXPR_HATCHING_TYPE(ExprHatchingType.class),

	EXPR_HEAL_AMOUNT(ExprHealAmount.class),

	EXPR_HEAL_REASON(ExprHealReason.class),

	EXPR_HEALTH(ExprHealth.class),

	EXPR_HIDDEN_PLAYERS(ExprHiddenPlayers.class),

	EXPR_HIGHEST_SOLID_BLOCK(ExprHighestSolidBlock.class),

	EXPR_HOSTNAME(ExprHostname.class),

	EXPR_HOTBAR_BUTTON(ExprHotbarButton.class),

	EXPR_HOTBAR_SLOT(ExprHotbarSlot.class),

	EXPR_HOVER_LIST(ExprHoverList.class),

	EXPR_HUMIDITY(ExprHumidity.class),

	EXPR_ID_OF(ExprIdOf.class),

	EXPR_INDEX_OF(ExprIndexOf.class),

	EXPR_INDICES(ExprIndices.class),

	EXPR_INVENTORY(ExprInventory.class),

	EXPR_INVENTORY_ACTION(ExprInventoryAction.class),

	EXPR_INVENTORY_INFO(ExprInventoryInfo.class),

	EXPR_INVENTORY_SLOT(ExprInventorySlot.class),

	EXPR_IP(ExprIP.class),

	EXPR_ITEM(ExprItem.class),

	EXPR_ITEM_AMOUNT(ExprItemAmount.class),

	EXPR_ITEM_FRAME_SLOT(ExprItemFrameSlot.class),

	EXPR_ITEMS_IN(ExprItemsIn.class),

	EXPR_ITEM_WITH_CUSTOM_MODEL_DATA(ExprItemWithCustomModelData.class),

	EXPR_ITEM_WITH_LORE(ExprItemWithLore.class),

	EXPR_JOIN_SPLIT(ExprJoinSplit.class),

	EXPR_LANGUAGE(ExprLanguage.class),

	EXPR_LAST_ATTACKER(ExprLastAttacker.class),

	EXPR_LAST_COLOR(ExprLastColor.class),

	EXPR_LAST_DAMAGE(ExprLastDamage.class),

	EXPR_LAST_DAMAGE_CAUSE(ExprLastDamageCause.class),

	EXPR_LAST_LOADED_SERVER_ICON(ExprLastLoadedServerIcon.class),

	EXPR_LAST_LOGIN_TIME(ExprLastLoginTime.class),

	EXPR_LAST_RESOURCE_PACK_RESPONSE(ExprLastResourcePackResponse.class),

	EXPR_LAST_SPAWNED_ENTITY(ExprLastSpawnedEntity.class),

	EXPR_LEASH_HOLDER(ExprLeashHolder.class),

	EXPR_LENGTH(ExprLength.class),

	EXPR_LEVEL(ExprLevel.class),

	EXPR_LEVEL_PROGRESS(ExprLevelProgress.class),

	EXPR_LIGHT_LEVEL(ExprLightLevel.class),

	EXPR_LOCATION(ExprLocation.class),

	EXPR_LOCATION_AT(ExprLocationAt.class),

	EXPR_LOCATION_FROM_VECTOR(ExprLocationFromVector.class),

	EXPR_LOCATION_OF(ExprLocationOf.class),

	EXPR_LOCATION_VECTOR_OFFSET(ExprLocationVectorOffset.class),

	EXPR_LOOP_VALUE(ExprLoopValue.class),

	EXPR_LORE(ExprLore.class),

	EXPR_MAX_DURABILITY(ExprMaxDurability.class),

	EXPR_MAX_FREEZE_TICKS(ExprMaxFreezeTicks.class),

	EXPR_MAX_HEALTH(ExprMaxHealth.class),

	EXPR_MAX_MINECART_SPEED(ExprMaxMinecartSpeed.class),

	EXPR_MAX_PLAYERS(ExprMaxPlayers.class),

	EXPR_MAX_STACK(ExprMaxStack.class),

	EXPR_ME(ExprMe.class),

	EXPR_MENDING_REPAIR_AMOUNT(ExprMendingRepairAmount.class),

	EXPR_MESSAGE(ExprMessage.class),

	EXPR_METADATA(ExprMetadata.class),

	EXPR_MIDDLE_OF_LOCATION(ExprMiddleOfLocation.class),

	EXPR_MINECART_DERAILED_FLYING_VELOCITY(ExprMinecartDerailedFlyingVelocity.class),

	EXPR_MOON_PHASE(ExprMoonPhase.class),

	EXPR_MOTD(ExprMOTD.class),

	EXPR_NAME(ExprName.class),

	EXPR_NAMED(ExprNamed.class),

	EXPR_NEAREST_ENTITY(ExprNearestEntity.class),

	EXPR_NO_DAMAGE_TICKS(ExprNoDamageTicks.class),

	EXPR_NOW(ExprNow.class),

	EXPR_NUMBER_OF_CHARACTERS(ExprNumberOfCharacters.class),

	EXPR_NUMBERS(ExprNumbers.class),

	EXPR_OFFLINE_PLAYERS(ExprOfflinePlayers.class),

	EXPR_ONLINE_PLAYERS_COUNT(ExprOnlinePlayersCount.class),

	EXPR_OPENED_INVENTORY(ExprOpenedInventory.class),

	EXPR_PARSE(ExprParse.class),

	EXPR_PARSE_ERROR(ExprParseError.class),

	EXPR_PASSENGER(ExprPassenger.class),

	EXPR_PERMISSIONS(ExprPermissions.class),

	EXPR_PICKUP_DELAY(ExprPickupDelay.class),

	EXPR_PING(ExprPing.class),

	EXPR_PLAIN(ExprPlain.class),

	EXPR_PLAYERLIST_HEADER_FOOTER(ExprPlayerlistHeaderFooter.class),

	EXPR_PLAYER_PROTOCOL_VERSION(ExprPlayerProtocolVersion.class),

	EXPR_PLAYER_VIEW_DISTANCE(ExprPlayerViewDistance.class),

	EXPR_PLAYER_WEATHER(ExprPlayerWeather.class),

	EXPR_PLUGINS(ExprPlugins.class),

	EXPR_PORTAL(ExprPortal.class),

	EXPR_POTION_EFFECT(ExprPotionEffect.class),

	EXPR_POTION_EFFECTS(ExprPotionEffects.class),

	EXPR_POTION_EFFECT_TIER(ExprPotionEffectTier.class),

	EXPR_PROJECTILE_BOUNCE_STATE(ExprProjectileBounceState.class),

	EXPR_PROJECTILE_CRITICAL_STATE(ExprProjectileCriticalState.class),

	EXPR_PROTOCOL_VERSION(ExprProtocolVersion.class),

	EXPR_PUSHED_BLOCKS(ExprPushedBlocks.class),

	EXPR_RANDOM(ExprRandom.class),

	EXPR_RANDOM_NUMBER(ExprRandomNumber.class),

	EXPR_RANDOM_UUID(ExprRandomUUID.class),

	EXPR_RAW_NAME(ExprRawName.class),

	EXPR_RAW_STRING(ExprRawString.class),

	EXPR_REDSTONE_BLOCK_POWER(ExprRedstoneBlockPower.class),

	EXPR_REMAINING_AIR(ExprRemainingAir.class),

	EXPR_RESPAWN_LOCATION(ExprRespawnLocation.class),

	EXPR_REVERSED_LIST(ExprReversedList.class),

	EXPR_ROUND(ExprRound.class),

	EXPR_SATURATION(ExprSaturation.class),

	EXPR_SCOREBOARD_TAGS(ExprScoreboardTags.class),

	EXPR_SCRIPT(ExprScript.class),

	EXPR_SCRIPTS(ExprScripts.class),

	EXPR_SEA_LEVEL(ExprSeaLevel.class),

	EXPR_SEED(ExprSeed.class),

	EXPR_SERVER_ICON(ExprServerIcon.class),

	EXPR_SETS(ExprSets.class),

	EXPR_SHOOTER(ExprShooter.class),

	EXPR_SHUFFLED_LIST(ExprShuffledList.class),

	EXPR_SIGN_TEXT(ExprSignText.class),

	EXPR_SKULL(ExprSkull.class),

	EXPR_SLOT_INDEX(ExprSlotIndex.class),

	EXPR_SORTED_LIST(ExprSortedList.class),

	EXPR_SPAWN(ExprSpawn.class),

	EXPR_SPAWNER_TYPE(ExprSpawnerType.class),

	EXPR_SPAWN_REASON(ExprSpawnReason.class),

	EXPR_SPECIAL_NUMBER(ExprSpecialNumber.class),

	EXPR_SPECTATOR_TARGET(ExprSpectatorTarget.class),

	EXPR_SPEED(ExprSpeed.class),

	EXPR_STRING_CASE(ExprStringCase.class),

	EXPR_SUBSTRING(ExprSubstring.class),

	EXPR_TAMER(ExprTamer.class),

	EXPR_TARGET(ExprTarget.class),

	EXPR_TARGETED_BLOCK(ExprTargetedBlock.class),

	EXPR_TELEPORT_CAUSE(ExprTeleportCause.class),

	EXPR_TEMPERATURE(ExprTemperature.class),

	EXPR_TERNARY(ExprTernary.class),

	EXPR_TIME(ExprTime.class),

	EXPR_TIME_PLAYED(ExprTimePlayed.class),

	EXPR_TIMES(ExprTimes.class),

	EXPR_TIME_SINCE(ExprTimeSince.class),

	EXPR_TIME_STATE(ExprTimeState.class),

	EXPR_TOOL(ExprTool.class),

	EXPR_TPS(ExprTPS.class),

	EXPR_TYPE_OF(ExprTypeOf.class),

	EXPR_UNBREAKABLE(ExprUnbreakable.class),

	EXPR_UNIX_DATE(ExprUnixDate.class),

	EXPR_UNIX_TICKS(ExprUnixTicks.class),

	EXPR_UUID(ExprUUID.class),

	EXPR_VALUE(ExprValue.class),

	EXPR_VECTOR_ANGLE_BETWEEN(ExprVectorAngleBetween.class),

	EXPR_VECTOR_ARITHMETIC(ExprVectorArithmetic.class),

	EXPR_VECTOR_BETWEEN_LOCATIONS(ExprVectorBetweenLocations.class),

	EXPR_VECTOR_CROSS_PRODUCT(ExprVectorCrossProduct.class),

	EXPR_VECTOR_CYLINDRICAL(ExprVectorCylindrical.class),

	EXPR_VECTOR_DOT_PRODUCT(ExprVectorDotProduct.class),

	EXPR_VECTOR_FROM_XYZ(ExprVectorFromXYZ.class),

	EXPR_VECTOR_FROM_YAW_AND_PITCH(ExprVectorFromYawAndPitch.class),

	EXPR_VECTOR_LENGTH(ExprVectorLength.class),

	EXPR_VECTOR_NORMALIZE(ExprVectorNormalize.class),

	EXPR_VECTOR_OF_LOCATION(ExprVectorOfLocation.class),

	EXPR_VECTOR_RANDOM(ExprVectorRandom.class),

	EXPR_VECTOR_SPHERICAL(ExprVectorSpherical.class),

	EXPR_VECTOR_SQUARED_LENGTH(ExprVectorSquaredLength.class),

	EXPR_VECTOR_XYZ(ExprVectorXYZ.class),

	EXPR_VEHICLE(ExprVehicle.class),

	EXPR_VELOCITY(ExprVelocity.class),

	EXPR_VERSION(ExprVersion.class),

	EXPR_VERSION_STRING(ExprVersionString.class),

	EXPR_WEATHER(ExprWeather.class),

	EXPR_WHITELIST(ExprWhitelist.class),

	EXPR_WORLD(ExprWorld.class),

	EXPR_WORLD_ENVIRONMENT(ExprWorldEnvironment.class),

	EXPR_WORLD_FROM_NAME(ExprWorldFromName.class),

	EXPR_WORLDS(ExprWorlds.class),

	EXPR_XOF(ExprXOf.class),

	EXPR_YAW_PITCH(ExprYawPitch.class),

	LIT_AT(LitAt.class),

	LIT_CONSOLE(LitConsole.class),

	LIT_NEW_LINE(LitNewLine.class);

	Expressions(Class clazz) {
		try {
			Class.forName(clazz.getName());
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
	}

	public static void init() {
	}
}
