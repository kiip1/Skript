/**
 *   This file is part of Skript.
 *
 *  Skript is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  Skript is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with Skript.  If not, see <http://www.gnu.org/licenses/>.
 *
 * Copyright Peter Güttinger, SkriptLang team and contributors
 */
package org.skriptlang.skript.bukkit.registration;

import ch.njol.skript.lang.SkriptEvent;
import org.bukkit.event.Event;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Nullable;
import org.skriptlang.skript.registration.SyntaxOrigin;

import java.util.ArrayList;
import java.util.List;

@ApiStatus.Experimental
public final class EventInfoBuilder<E extends SkriptEvent> {
	
	@Contract("_, _, _ -> new")
	public static <E extends SkriptEvent> EventInfoBuilder<E> builder(Class<E> type, String name, String id) {
		return new EventInfoBuilder<>(type, name, id);
	}
	
	private final Class<E> type;
	private final String name;
	private final String id;
	private final List<String> description = new ArrayList<>();
	private final List<String> examples = new ArrayList<>();
	private final List<String> keywords = new ArrayList<>();
	private final List<String> requiredPlugins = new ArrayList<>();
	private final List<String> patterns = new ArrayList<>();
	private final List<Class<? extends Event>> events = new ArrayList<>();
	private SyntaxOrigin origin = SyntaxOrigin.UNKNOWN;
	@Nullable
	private String since;
	@Nullable
	private String documentationId;
	
	private EventInfoBuilder(Class<E> type, String name, String id) {
		this.type = type;
		this.name = name;
		this.id = id;
	}
	
	@Contract("_ -> this")
	public EventInfoBuilder<E> origin(SyntaxOrigin origin) {
		this.origin = origin;
		return this;
	}
	
	@Contract("_ -> this")
	public EventInfoBuilder<E> since(String since) {
		this.since = since;
		return this;
	}
	
	@Contract("_ -> this")
	public EventInfoBuilder<E> documentationId(String documentationId) {
		this.documentationId = documentationId;
		return this;
	}
	
	@Contract("_ -> this")
	public EventInfoBuilder<E> addDescription(String description) {
		this.description.add(description);
		return this;
	}
	
	@Contract("_ -> this")
	public EventInfoBuilder<E> addDescription(String... description) {
		for (String line : description)
			addDescription(line);
		return this;
	}
	
	@Contract("_ -> this")
	public EventInfoBuilder<E> addDescription(List<String> description) {
		for (String line : description)
			addDescription(line);
		return this;
	}
	
	@Contract("_ -> this")
	public EventInfoBuilder<E> addExample(String example) {
		examples.add(example);
		return this;
	}
	
	@Contract("_ -> this")
	public EventInfoBuilder<E> addExamples(String... examples) {
		for (String example : examples)
			addExample(example);
		return this;
	}
	
	@Contract("_ -> this")
	public EventInfoBuilder<E> addExamples(List<String> examples) {
		for (String example : examples)
			addExample(example);
		return this;
	}
	
	@Contract("_ -> this")
	public EventInfoBuilder<E> addKeyword(String keyword) {
		keywords.add(keyword);
		return this;
	}
	
	@Contract("_ -> this")
	public EventInfoBuilder<E> addKeywords(String... keywords) {
		for (String keyword : keywords)
			addKeyword(keyword);
		return this;
	}
	
	@Contract("_ -> this")
	public EventInfoBuilder<E> addKeywords(List<String> keywords) {
		for (String keyword : keywords)
			addKeyword(keyword);
		return this;
	}
	
	@Contract("_ -> this")
	public EventInfoBuilder<E> addRequiredPlugin(String requiredPlugin) {
		requiredPlugins.add(requiredPlugin);
		return this;
	}
	
	@Contract("_ -> this")
	public EventInfoBuilder<E> addRequiredPlugins(String... requiredPlugins) {
		for (String requiredPlugin : requiredPlugins)
			addRequiredPlugin(requiredPlugin);
		return this;
	}
	
	@Contract("_ -> this")
	public EventInfoBuilder<E> addRequiredPlugins(List<String> requiredPlugins) {
		for (String requiredPlugin : requiredPlugins)
			addRequiredPlugin(requiredPlugin);
		return this;
	}
	
	@Contract("_ -> this")
	public EventInfoBuilder<E> addPattern(String pattern) {
		patterns.add(pattern);
		return this;
	}
	
	@Contract("_ -> this")
	public EventInfoBuilder<E> addPatterns(String... patterns) {
		for (String pattern : patterns)
			addPattern(pattern);
		return this;
	}
	
	@Contract("_ -> this")
	public EventInfoBuilder<E> addPatterns(List<String> patterns) {
		for (String pattern : patterns)
			addPattern(pattern);
		return this;
	}
	
	@Contract("_ -> this")
	public EventInfoBuilder<E> addEvent(Class<? extends Event> event) {
		events.add(event);
		return this;
	}
	
	@Contract("_ -> this")
	public EventInfoBuilder<E> addEvents(Class<? extends Event>... events) {
		for (Class<? extends Event> event : events)
			addEvent(event);
		return this;
	}
	
	@Contract("_ -> this")
	public EventInfoBuilder<E> addEvents(List<Class<? extends Event>> events) {
		for (Class<? extends Event> event : events)
			addEvent(event);
		return this;
	}
	
	@Contract("-> new")
	public BukkitSyntaxInfos.Event<E> build() {
		return BukkitSyntaxInfos.Event.of(origin, type, patterns, name, id, since, documentationId,
			description, examples, keywords, requiredPlugins, events);
	}
	
}
