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
package ch.njol.skript.lang;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

public class ExpressionInfo<E extends Expression<T>, T> extends SyntaxElementInfo<E> {
	
	public Class<T> returnType;
	@Nullable
	public ExpressionType expressionType;
	
	public ExpressionInfo(String[] patterns, Class<T> returnType, Class<E> clazz,
	                      String originClassPath, @Nullable ExpressionType expressionType) throws IllegalArgumentException {
		
		// Null can be passed for now, but whenever these methods get removed
		// null won't be supported anymore
		//noinspection DataFlowIssue
		this(patterns, returnType, clazz, null, originClassPath, expressionType);
	}
	
	public ExpressionInfo(String[] patterns, Class<T> returnType, Class<E> clazz, Supplier<E> supplier,
	                      String originClassPath, @Nullable ExpressionType expressionType) throws IllegalArgumentException {
		
		super(patterns, clazz, supplier, originClassPath);
		this.returnType = returnType;
		this.expressionType = expressionType;
	}
	
	/**
	 * Get the return type of this expression.
	 * @return The return type of this Expression
	 */
	public Class<T> getReturnType() {
		return returnType;
	}
	
	/**
	 * Get the type of this expression.
	 * @return The type of this Expression
	 */
	@Nullable
	public ExpressionType getExpressionType() {
		return expressionType;
	}
}
