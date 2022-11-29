package ch.njol.skript;

import ch.njol.skript.events.EvtAtTime;
import ch.njol.skript.events.EvtBlock;
import ch.njol.skript.events.EvtBookEdit;
import ch.njol.skript.events.EvtBookSign;
import ch.njol.skript.events.EvtChat;
import ch.njol.skript.events.EvtClick;
import ch.njol.skript.events.EvtCommand;
import ch.njol.skript.events.EvtDamage;
import ch.njol.skript.events.EvtEntity;
import ch.njol.skript.events.EvtEntityBlockChange;
import ch.njol.skript.events.EvtEntityTarget;
import ch.njol.skript.events.EvtExperienceSpawn;
import ch.njol.skript.events.EvtFirework;
import ch.njol.skript.events.EvtFirstJoin;
import ch.njol.skript.events.EvtGameMode;
import ch.njol.skript.events.EvtGrow;
import ch.njol.skript.events.EvtItem;
import ch.njol.skript.events.EvtLevel;
import ch.njol.skript.events.EvtMove;
import ch.njol.skript.events.EvtMoveOn;
import ch.njol.skript.events.EvtPeriodical;
import ch.njol.skript.events.EvtPlantGrowth;
import ch.njol.skript.events.EvtPressurePlate;
import ch.njol.skript.events.EvtResourcePackResponse;
import ch.njol.skript.events.EvtScript;
import ch.njol.skript.events.EvtSkript;
import ch.njol.skript.events.EvtWeatherChange;
import ch.njol.skript.events.SimpleEvents;
import ch.njol.skript.events.bukkit.ExperienceSpawnEvent;
import ch.njol.skript.events.bukkit.PreScriptLoadEvent;
import ch.njol.skript.events.bukkit.ScheduledEvent;
import ch.njol.skript.events.bukkit.ScheduledNoWorldEvent;
import ch.njol.skript.events.bukkit.ScriptEvent;
import ch.njol.skript.events.bukkit.SkriptParseEvent;
import ch.njol.skript.events.bukkit.SkriptStartEvent;
import ch.njol.skript.events.bukkit.SkriptStopEvent;
import ch.njol.skript.events.util.PlayerChatEventHandler;

/**
 * This code was automatically generated. DO NOT EDIT!
 */
public enum Events {
	EXPERIENCE_SPAWN_EVENT(ExperienceSpawnEvent.class),

	PRE_SCRIPT_LOAD_EVENT(PreScriptLoadEvent.class),

	SCHEDULED_EVENT(ScheduledEvent.class),

	SCHEDULED_NO_WORLD_EVENT(ScheduledNoWorldEvent.class),

	SCRIPT_EVENT(ScriptEvent.class),

	SKRIPT_PARSE_EVENT(SkriptParseEvent.class),

	SKRIPT_START_EVENT(SkriptStartEvent.class),

	SKRIPT_STOP_EVENT(SkriptStopEvent.class),

	EVT_AT_TIME(EvtAtTime.class),

	EVT_BLOCK(EvtBlock.class),

	EVT_BOOK_EDIT(EvtBookEdit.class),

	EVT_BOOK_SIGN(EvtBookSign.class),

	EVT_CHAT(EvtChat.class),

	EVT_CLICK(EvtClick.class),

	EVT_COMMAND(EvtCommand.class),

	EVT_DAMAGE(EvtDamage.class),

	EVT_ENTITY(EvtEntity.class),

	EVT_ENTITY_BLOCK_CHANGE(EvtEntityBlockChange.class),

	EVT_ENTITY_TARGET(EvtEntityTarget.class),

	EVT_EXPERIENCE_SPAWN(EvtExperienceSpawn.class),

	EVT_FIREWORK(EvtFirework.class),

	EVT_FIRST_JOIN(EvtFirstJoin.class),

	EVT_GAME_MODE(EvtGameMode.class),

	EVT_GROW(EvtGrow.class),

	EVT_ITEM(EvtItem.class),

	EVT_LEVEL(EvtLevel.class),

	EVT_MOVE(EvtMove.class),

	EVT_MOVE_ON(EvtMoveOn.class),

	EVT_PERIODICAL(EvtPeriodical.class),

	EVT_PLANT_GROWTH(EvtPlantGrowth.class),

	EVT_PRESSURE_PLATE(EvtPressurePlate.class),

	EVT_RESOURCE_PACK_RESPONSE(EvtResourcePackResponse.class),

	EVT_SCRIPT(EvtScript.class),

	EVT_SKRIPT(EvtSkript.class),

	EVT_WEATHER_CHANGE(EvtWeatherChange.class),

	SIMPLE_EVENTS(SimpleEvents.class),

	PLAYER_CHAT_EVENT_HANDLER(PlayerChatEventHandler.class);

	Events(Class clazz) {
		try {
			Class.forName(clazz.getName());
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
	}

	public static void init() {
	}
}
