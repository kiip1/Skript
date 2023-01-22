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
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.google.common.collect.ImmutableList;
import org.jetbrains.annotations.Nullable;
import org.skriptlang.skript.bukkit.registration.BukkitSyntaxInfos.Event;
import org.skriptlang.skript.registration.SyntaxInfoImpl;
import org.skriptlang.skript.registration.SyntaxOrigin;

import java.util.List;
import java.util.stream.Collectors;

final class BukkitSyntaxInfosImpl {
	
	static final class EventImpl<E extends SkriptEvent> extends SyntaxInfoImpl.StructureImpl<E> implements Event<E> {
		
		private final String name;
		@Nullable
		private final String since;
		@Nullable
		private final String documentationId;
		private final List<String> description;
		private final List<String> examples;
		private final List<String> keywords;
		private final List<String> requiredPlugins;
		private final List<Class<? extends org.bukkit.event.Event>> events;
		
		EventImpl(
			SyntaxOrigin origin, Class<E> type, List<String> patterns, String name, @Nullable String since,
			@Nullable String documentationId, List<String> description, List<String> examples, List<String> keywords,
			List<String> requiredPlugins, List<Class<? extends org.bukkit.event.Event>> events
		) {
			super(origin, type, patterns.stream().map(EventImpl::pattern)
				.collect(Collectors.toList()), null);
			this.name = name;
			this.since = since;
			this.documentationId = documentationId;
			this.description = ImmutableList.copyOf(description);
			this.examples = ImmutableList.copyOf(examples);
			this.keywords = ImmutableList.copyOf(keywords);
			this.requiredPlugins = ImmutableList.copyOf(requiredPlugins);
			this.events = ImmutableList.copyOf(events);
		}
		
		@Override
		public String name() {
			return name;
		}
		
		@Override
		public String since() {
			return since;
		}
		
		@Override
		public String documentationId() {
			return documentationId;
		}
		
		@Override
		public List<String> description() {
			return description;
		}
		
		@Override
		public List<String> examples() {
			return examples;
		}
		
		@Override
		public List<String> keywords() {
			return keywords;
		}
		
		@Override
		public List<String> requiredPlugins() {
			return requiredPlugins;
		}
		
		@Override
		public List<Class<? extends org.bukkit.event.Event>> events() {
			return events;
		}
		
		@Override
		public boolean equals(Object other) {
			if (this == other)
				return true;
			if (!(other instanceof Event))
				return false;
			Event<?> event = (Event<?>) other;
			return origin().equals(event.origin()) && type().equals(event.type()) &&
				patterns().equals(event.patterns()) && name().equals(event.name());
		}
		
		@Override
		public int hashCode() {
			return Objects.hashCode(origin(), type(), patterns(), name(), events());
		}
		
		@Override
		public String toString() {
			return MoreObjects.toStringHelper(this)
				.add("origin", origin())
				.add("type", type())
				.add("patterns", patterns())
				.add("name", name())
				.add("events", events())
				.toString();
		}
		
		private static String pattern(String pattern) {
			return "[on] " + SkriptEvent.fixPattern(pattern) + " [with priority (lowest|low|normal|high|highest|monitor)]";
		}
		
	}
	
}
