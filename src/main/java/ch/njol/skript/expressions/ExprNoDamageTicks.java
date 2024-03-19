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
package ch.njol.skript.expressions;

import ch.njol.skript.Skript;
import ch.njol.skript.classes.Changer.ChangeMode;
import ch.njol.skript.doc.NoDoc;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.util.MarkedForRemoval;
import ch.njol.util.Kleenean;
import ch.njol.util.coll.CollectionUtils;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.Event;
import org.eclipse.jdt.annotation.Nullable;

@NoDoc
@Deprecated
@MarkedForRemoval(version = "2.8")
public class ExprNoDamageTicks extends SimplePropertyExpression<LivingEntity, Long> {
	
	static {
		register(ExprNoDamageTicks.class, Long.class, "(invulnerability|invincibility|no damage) tick[s]", "livingentities");
	}
	
	@Override
	public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, ParseResult parseResult) {
		if (!super.init(exprs, matchedPattern, isDelayed, parseResult))
			return false;
		
		Skript.warning("Switch from deprecated 'no damage ticks' to 'no damage duration', as the deprecated expression will be removed in 2.8.");
		return true;
	}
	
	@Override
	public Long convert(LivingEntity entity) {
		return (long) Math.max(entity.getNoDamageTicks(), 0);
	}
	
	@Override
	@Nullable
	public Class<?>[] acceptChange(ChangeMode mode) {
		return (mode != ChangeMode.REMOVE_ALL) ? CollectionUtils.array(Number.class) : null;
	}
	
	@Override
	public void change(Event event, @Nullable Object[] delta, ChangeMode mode) {
		LivingEntity[] entities = getExpr().getArray(event);
		int amount = delta == null ? 0 : ((Number) delta[0]).intValue();
		for (LivingEntity entity : entities) {
			switch (mode) {
				case REMOVE:
					amount = -amount;
				case ADD:
					entity.setNoDamageTicks(Math.max(entity.getNoDamageTicks() + amount, 0));
					break;
				case RESET:
					entity.setNoDamageTicks(10); // Default value from https://minecraft.fandom.com/wiki/Damage
					break;
				case DELETE:
				case SET:
					entity.setNoDamageTicks(Math.max(amount, 0));
					break;
				default:
					assert false;
			}
		}
	}
	
	@Override
	public Class<? extends Long> getReturnType() {
		return Long.class;
	}
	
	@Override
	protected String getPropertyName() {
		return "no damage ticks";
	}
	
}
