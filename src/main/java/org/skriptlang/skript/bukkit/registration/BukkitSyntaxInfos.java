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
import ch.njol.skript.lang.SkriptEventInfo;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Nullable;
import org.skriptlang.skript.registration.SyntaxInfo;
import org.skriptlang.skript.registration.SyntaxOrigin;

import java.util.List;

@ApiStatus.Internal
public interface BukkitSyntaxInfos {
	
	@ApiStatus.NonExtendable
	interface Event<E extends SkriptEvent> extends SyntaxInfo.Structure<E> {
		
		@Contract("_, _, _, _, _, _, _, _, _, _, _, _ -> new")
		static <E extends SkriptEvent> BukkitSyntaxInfos.Event<E> of(
			SyntaxOrigin origin, Class<E> type, List<String> patterns, String name, String id, @Nullable String since,
			@Nullable String documentationId, List<String> description, List<String> examples, List<String> keywords,
			List<String> requiredPlugins, List<Class<? extends org.bukkit.event.Event>> events
		) {
			return new BukkitSyntaxInfosImpl.EventImpl<>(origin, type, patterns, name, id, since, documentationId,
				description, examples, keywords, requiredPlugins, events);
		}
		
		@Contract("_ -> new")
		static <E extends SkriptEvent> BukkitSyntaxInfos.Event<E> legacy(SkriptEventInfo<E> info) {
			return new BukkitSyntaxInfosImpl.LegacyEventImpl<>(info);
		}
		
		String name();
		
		String id();
		
		@Nullable
		String since();
		
		@Nullable
		String documentationId();
		
		List<String> description();
		
		List<String> examples();
		
		List<String> keywords();
		
		List<String> requiredPlugins();
		
		List<Class<? extends org.bukkit.event.Event>> events();
		
	}
	
	static String pattern(String pattern) {
		return "[on] " + SkriptEvent.fixPattern(pattern) + " [with priority (lowest|low|normal|high|highest|monitor)]";
	}
	
}
