package ch.njol.skript;

import ch.njol.skript.effects.Delay;
import ch.njol.skript.effects.EffActionBar;
import ch.njol.skript.effects.EffBan;
import ch.njol.skript.effects.EffBreakNaturally;
import ch.njol.skript.effects.EffBroadcast;
import ch.njol.skript.effects.EffCancelCooldown;
import ch.njol.skript.effects.EffCancelDrops;
import ch.njol.skript.effects.EffCancelEvent;
import ch.njol.skript.effects.EffChange;
import ch.njol.skript.effects.EffChargeCreeper;
import ch.njol.skript.effects.EffColorItems;
import ch.njol.skript.effects.EffCommand;
import ch.njol.skript.effects.EffConnect;
import ch.njol.skript.effects.EffContinue;
import ch.njol.skript.effects.EffDoIf;
import ch.njol.skript.effects.EffDrop;
import ch.njol.skript.effects.EffEnchant;
import ch.njol.skript.effects.EffEquip;
import ch.njol.skript.effects.EffExceptionDebug;
import ch.njol.skript.effects.EffExit;
import ch.njol.skript.effects.EffExplodeCreeper;
import ch.njol.skript.effects.EffExplosion;
import ch.njol.skript.effects.EffFeed;
import ch.njol.skript.effects.EffFireworkLaunch;
import ch.njol.skript.effects.EffForceAttack;
import ch.njol.skript.effects.EffHealth;
import ch.njol.skript.effects.EffHidePlayerFromServerList;
import ch.njol.skript.effects.EffIgnite;
import ch.njol.skript.effects.EffIncendiary;
import ch.njol.skript.effects.EffInvisible;
import ch.njol.skript.effects.EffInvulnerability;
import ch.njol.skript.effects.EffKeepInventory;
import ch.njol.skript.effects.EffKick;
import ch.njol.skript.effects.EffKill;
import ch.njol.skript.effects.EffLeash;
import ch.njol.skript.effects.EffLightning;
import ch.njol.skript.effects.EffLoadServerIcon;
import ch.njol.skript.effects.EffLog;
import ch.njol.skript.effects.EffMakeEggHatch;
import ch.njol.skript.effects.EffMakeFly;
import ch.njol.skript.effects.EffMakeSay;
import ch.njol.skript.effects.EffMessage;
import ch.njol.skript.effects.EffOp;
import ch.njol.skript.effects.EffOpenBook;
import ch.njol.skript.effects.EffOpenInventory;
import ch.njol.skript.effects.EffPathfind;
import ch.njol.skript.effects.EffPlaySound;
import ch.njol.skript.effects.EffPlayerInfoVisibility;
import ch.njol.skript.effects.EffPlayerVisibility;
import ch.njol.skript.effects.EffPoison;
import ch.njol.skript.effects.EffPotion;
import ch.njol.skript.effects.EffPush;
import ch.njol.skript.effects.EffPvP;
import ch.njol.skript.effects.EffReplace;
import ch.njol.skript.effects.EffResetTitle;
import ch.njol.skript.effects.EffRespawn;
import ch.njol.skript.effects.EffReturn;
import ch.njol.skript.effects.EffScriptFile;
import ch.njol.skript.effects.EffSendBlockChange;
import ch.njol.skript.effects.EffSendResourcePack;
import ch.njol.skript.effects.EffSendTitle;
import ch.njol.skript.effects.EffShear;
import ch.njol.skript.effects.EffShoot;
import ch.njol.skript.effects.EffSilence;
import ch.njol.skript.effects.EffStopServer;
import ch.njol.skript.effects.EffStopSound;
import ch.njol.skript.effects.EffSuppressWarnings;
import ch.njol.skript.effects.EffSwingHand;
import ch.njol.skript.effects.EffTeleport;
import ch.njol.skript.effects.EffToggle;
import ch.njol.skript.effects.EffToggleFlight;
import ch.njol.skript.effects.EffTree;
import ch.njol.skript.effects.EffVectorRotateAroundAnother;
import ch.njol.skript.effects.EffVectorRotateXYZ;
import ch.njol.skript.effects.EffVehicle;
import ch.njol.skript.effects.EffVisualEffect;
import ch.njol.skript.effects.IndeterminateDelay;

/**
 * This code was automatically generated. DO NOT EDIT!
 */
public enum Effects {
	DELAY(Delay.class),

	EFF_ACTION_BAR(EffActionBar.class),

	EFF_BAN(EffBan.class),

	EFF_BREAK_NATURALLY(EffBreakNaturally.class),

	EFF_BROADCAST(EffBroadcast.class),

	EFF_CANCEL_COOLDOWN(EffCancelCooldown.class),

	EFF_CANCEL_DROPS(EffCancelDrops.class),

	EFF_CANCEL_EVENT(EffCancelEvent.class),

	EFF_CHANGE(EffChange.class),

	EFF_CHARGE_CREEPER(EffChargeCreeper.class),

	EFF_COLOR_ITEMS(EffColorItems.class),

	EFF_COMMAND(EffCommand.class),

	EFF_CONNECT(EffConnect.class),

	EFF_CONTINUE(EffContinue.class),

	EFF_DO_IF(EffDoIf.class),

	EFF_DROP(EffDrop.class),

	EFF_ENCHANT(EffEnchant.class),

	EFF_EQUIP(EffEquip.class),

	EFF_EXCEPTION_DEBUG(EffExceptionDebug.class),

	EFF_EXIT(EffExit.class),

	EFF_EXPLODE_CREEPER(EffExplodeCreeper.class),

	EFF_EXPLOSION(EffExplosion.class),

	EFF_FEED(EffFeed.class),

	EFF_FIREWORK_LAUNCH(EffFireworkLaunch.class),

	EFF_FORCE_ATTACK(EffForceAttack.class),

	EFF_HEALTH(EffHealth.class),

	EFF_HIDE_PLAYER_FROM_SERVER_LIST(EffHidePlayerFromServerList.class),

	EFF_IGNITE(EffIgnite.class),

	EFF_INCENDIARY(EffIncendiary.class),

	EFF_INVISIBLE(EffInvisible.class),

	EFF_INVULNERABILITY(EffInvulnerability.class),

	EFF_KEEP_INVENTORY(EffKeepInventory.class),

	EFF_KICK(EffKick.class),

	EFF_KILL(EffKill.class),

	EFF_LEASH(EffLeash.class),

	EFF_LIGHTNING(EffLightning.class),

	EFF_LOAD_SERVER_ICON(EffLoadServerIcon.class),

	EFF_LOG(EffLog.class),

	EFF_MAKE_EGG_HATCH(EffMakeEggHatch.class),

	EFF_MAKE_FLY(EffMakeFly.class),

	EFF_MAKE_SAY(EffMakeSay.class),

	EFF_MESSAGE(EffMessage.class),

	EFF_OP(EffOp.class),

	EFF_OPEN_BOOK(EffOpenBook.class),

	EFF_OPEN_INVENTORY(EffOpenInventory.class),

	EFF_PATHFIND(EffPathfind.class),

	EFF_PLAYER_INFO_VISIBILITY(EffPlayerInfoVisibility.class),

	EFF_PLAYER_VISIBILITY(EffPlayerVisibility.class),

	EFF_PLAY_SOUND(EffPlaySound.class),

	EFF_POISON(EffPoison.class),

	EFF_POTION(EffPotion.class),

	EFF_PUSH(EffPush.class),

	EFF_PV_P(EffPvP.class),

	EFF_REPLACE(EffReplace.class),

	EFF_RESET_TITLE(EffResetTitle.class),

	EFF_RESPAWN(EffRespawn.class),

	EFF_RETURN(EffReturn.class),

	EFF_SCRIPT_FILE(EffScriptFile.class),

	EFF_SEND_BLOCK_CHANGE(EffSendBlockChange.class),

	EFF_SEND_RESOURCE_PACK(EffSendResourcePack.class),

	EFF_SEND_TITLE(EffSendTitle.class),

	EFF_SHEAR(EffShear.class),

	EFF_SHOOT(EffShoot.class),

	EFF_SILENCE(EffSilence.class),

	EFF_STOP_SERVER(EffStopServer.class),

	EFF_STOP_SOUND(EffStopSound.class),

	EFF_SUPPRESS_WARNINGS(EffSuppressWarnings.class),

	EFF_SWING_HAND(EffSwingHand.class),

	EFF_TELEPORT(EffTeleport.class),

	EFF_TOGGLE(EffToggle.class),

	EFF_TOGGLE_FLIGHT(EffToggleFlight.class),

	EFF_TREE(EffTree.class),

	EFF_VECTOR_ROTATE_AROUND_ANOTHER(EffVectorRotateAroundAnother.class),

	EFF_VECTOR_ROTATE_XYZ(EffVectorRotateXYZ.class),

	EFF_VEHICLE(EffVehicle.class),

	EFF_VISUAL_EFFECT(EffVisualEffect.class),

	INDETERMINATE_DELAY(IndeterminateDelay.class);

	Effects(Class clazz) {
		try {
			Class.forName(clazz.getName());
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
	}

	public static void init() {
	}
}
