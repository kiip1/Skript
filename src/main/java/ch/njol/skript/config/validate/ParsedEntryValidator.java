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
package ch.njol.skript.config.validate;

import ch.njol.skript.classes.Parser;
import ch.njol.skript.config.EntryNode;
import ch.njol.skript.config.Node;
import ch.njol.skript.lang.ParseContext;
import ch.njol.util.Setter;
import org.jetbrains.annotations.ApiStatus;

import java.util.function.Consumer;

/**
 * @author Peter Güttinger
 */
public class ParsedEntryValidator<T> extends EntryValidator {
	
	private final Parser<? extends T> parser;
	private final Consumer<T> setter;

	// TODO Remove when Setter gets removed
	@Deprecated
	@ApiStatus.ScheduledForRemoval
	public ParsedEntryValidator(Parser<? extends T> parser, Setter<T> setter) {
		this(parser, (Consumer<T>) setter::set);
	}

	public ParsedEntryValidator(Parser<? extends T> parser, Consumer<T> setter) {
		assert parser != null;
		assert setter != null;
		this.parser = parser;
		this.setter = setter;
	}

	@Override
	public boolean validate(final Node node) {
		if (!super.validate(node))
			return false;
		final T t = parser.parse(((EntryNode) node).getValue(), ParseContext.CONFIG);
		if (t == null)
			return false;
		setter.accept(t);
		return true;
	}
	
}
