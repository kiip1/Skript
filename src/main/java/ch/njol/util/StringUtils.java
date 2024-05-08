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
package ch.njol.util;

import ch.njol.skript.aliases.AliasesParser;
import ch.njol.skript.effects.EffSendResourcePack;
import org.eclipse.jdt.annotation.Nullable;
import org.jetbrains.annotations.ApiStatus;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Locale;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Deprecated
@ApiStatus.ScheduledForRemoval
// TODO Move methods to usage
public final class StringUtils {

	private StringUtils() {}

	public static void checkIndices(String value, int start, int end) {
		if (start < 0 || end > value.length())
			throw new StringIndexOutOfBoundsException("invalid start/end indices " + start + "," + end + " for string \"" + value + "\" (length " + value.length() + ")");
	}
	
	/**
	 * Appends the english order suffix to the given number.
	 * 
	 * @param i the number
	 * @return 1st, 2nd, 3rd, 4th, etc.
	 */
	public static String fancyOrderNumber(int i) {
		int iModTen = i % 10;
		int iModHundred = i % 100;
		if (iModTen == 1 && iModHundred != 11)
			return i + "st";
		if (iModTen == 2 && iModHundred != 12)
			return i + "nd";
		if (iModTen == 3 && iModHundred != 13)
			return i + "rd";
		return i + "th";
	}
	
	/**
	 * Performs regex replacing using a callback.
	 * 
	 * @param string the String in which should be searched & replaced
	 * @param regex the Regex to match
	 * @param callback the callback will be run for every match of the regex in the string, and should return the replacement string for the given match.
	 *            If the callback returns null for any given match this function will immediately terminate and return null.
	 */
	@Nullable
	public static String replaceAll(CharSequence string, String regex, Function<Matcher, String> callback) {
		return replaceAll(string, Pattern.compile(regex), callback);
	}
	
	/**
	 * Performs regex replacing using a callback.
	 * 
	 * @param string the String in which should be searched & replaced
	 * @param regex the Regex to match
	 * @param callback the callback will be run for every match of the regex in the string, and should return the replacement string for the given match.
	 *            If the callback returns null for any given match this function will immediately terminate and return null.
	 * @return
	 */
	@Nullable
	public static String replaceAll(CharSequence string, Pattern regex, Function<Matcher, String> callback) {
		final Matcher matcher = regex.matcher(string);
		final StringBuffer buffer = new StringBuffer();
		while (matcher.find()) {
			final String value = callback.apply(matcher);
			if (value == null)
				return null;
			matcher.appendReplacement(buffer, value);
		}
		matcher.appendTail(buffer);
		return buffer.toString();
	}
	
	public static int count(String value, char character) {
		return count(value, character, 0, value.length());
	}
	
	public static int count(String value, char character, final int start) {
		return count(value, character, start, value.length());
	}
	
	public static int count(String value, char character, int start, int end) {
		checkIndices(value, start, end);
		int r = 0;
		for (int i = start; i < end; i++) {
			if (value.charAt(i) == character)
				r++;
		}
		return r;
	}
	
	public static boolean contains(String value, char character, int start, int end) {
		checkIndices(value, start, end);
		for (int i = start; i < end; i++) {
			if (value.charAt(i) == character)
				return true;
		}
		return false;
	}
	
	/**
	 * Gets a rounded english (##.##) representation of a number
	 * 
	 * @param number The number to be turned into a string
	 * @param accuracy Maximum number of digits after the period
	 */
	public static String toString(double number, int accuracy) {
		if (accuracy <= 0)
			return "" + Math.round(number);
		final String formatted = String.format(Locale.ENGLISH, "%." + accuracy + "f", number);
		int c = formatted.length() - 1;
		while (formatted.charAt(c) == '0')
			c--;
		if (formatted.charAt(c) == '.')
			c--;
		return formatted.substring(0, c + 1);
	}
	
	public static String firstToUpper(String value) {
		if (value.isEmpty())
			return value;
		if (Character.isUpperCase(value.charAt(0)))
			return value;
		return Character.toUpperCase(value.charAt(0)) + value.substring(1);
	}
	
	/**
	 * Equal to {@link String#substring(int, int)}, but allows negative indices that are counted from the end of the string.
	 * 
	 * @param s
	 * @param start
	 * @param end
	 * @return
	 */
	public static String substring(final String s, int start, int end) {
		if (start < 0)
			start = start + s.length();
		if (end < 0)
			end = end + s.length();
		if (end < start)
			throw new IllegalArgumentException("invalid indices");
		return s.substring(start, end);
	}
	
	/**
	 * Capitalises the first character of the string and all characters that follow periods, exclamation and question marks.
	 */
	public static String fixCapitalization(String value) {
		final char[] chars = value.toCharArray();
		int i = 0;
		while (i != -1) {
			while (i < chars.length && (chars[i] == '.' || chars[i] == '!' || chars[i] == '?' || Character.isWhitespace(chars[i])))
				i++;
			if (i == chars.length)
				return new String(chars);
			if (i == 0 || Character.isWhitespace(chars[i - 1]))
				chars[i] = Character.toUpperCase(chars[i]);
			i = indexOf(chars, i + 1, '.', '!', '?');
		}

		return new String(chars);
	}
	
	private static int indexOf(char[] chars, int start, char... test) {
		for (int i = start; i < chars.length; i++) {
			for (char c : test)
				if (chars[i] == c)
					return i;
		}
		return -1;
	}
	
	/**
	 * Shorthand for <tt>{@link #numberAt(CharSequence, int, boolean) numberAt}(s, index, true)</tt>
	 */
	public static double numberAfter(CharSequence sequence, int index) {
		return numberAt(sequence, index, true);
	}
	
	/**
	 * Shorthand for <tt>{@link #numberAt(CharSequence, int, boolean) numberAt}(s, index, false)</tt>
	 */
	public static double numberBefore(CharSequence sequence, int index) {
		return numberAt(sequence, index, false);
	}
	
	/**
	 * Finds a positive number in the given CharSequence, starting at the given index, and searching in the given direction.
	 * <p>
	 * The number has to start exactly at the given index (ignoring whitespace), and will only count if the other end of the number is either at an end of the string or padded by
	 * whitespace.
	 * 
	 * @param sequence The ChatSequence to search the number in
	 * @param index The index to start searching at (inclusive)
	 * @param forward Whether to search forwards or backwards
	 * @return The number found or -1 if no matching number was found
	 */
	public static double numberAt(CharSequence sequence, int index, boolean forward) {
		assert index >= 0 && index < sequence.length() : index;
		final int direction = forward ? 1 : -1;
		boolean stillWhitespace = true;
		boolean hasDot = false;
		int d1 = -1, d2 = -1;
		for (int i = index; i >= 0 && i < sequence.length(); i += direction) {
			final char c = sequence.charAt(i);
			if ('0' <= c && c <= '9') {
				if (d1 == -1)
					d1 = d2 = i;
				else
					d1 += direction;
				stillWhitespace = false;
			} else if (c == '.') {
				if (hasDot)
					break;
				if (d1 == -1)
					d1 = d2 = i;
				else
					d1 += direction;
				hasDot = true;
				stillWhitespace = false;
			} else if (Character.isWhitespace(c)) {
				if (stillWhitespace)
					continue;
				break;
			} else {
				break;
			}
		}
		if (d1 == -1)
			return -1;
		if (sequence.charAt(Math.min(d1, d2)) == '.')
			return -1;
		if (d1 + direction > 0 && d1 + direction < sequence.length() && !Character.isWhitespace(sequence.charAt(d1 + direction)))
			return -1;
		return Double.parseDouble(sequence.subSequence(Math.min(d1, d2), Math.max(d1, d2) + 1).toString());
	}
	
	public static boolean startsWithIgnoreCase(String value, String start) {
		return startsWithIgnoreCase(value, start, 0);
	}
	
	public static boolean startsWithIgnoreCase(String value, String start, int offset) {
		if (value.length() < offset + start.length())
			return false;
		return value.substring(offset, start.length()).equalsIgnoreCase(start);
	}
	
	public static boolean endsWithIgnoreCase(String value, String end) {
		if (value.length() < end.length())
			return false;
		return value.substring(value.length() - end.length()).equalsIgnoreCase(end);
	}
	
	public static String multiply(@Nullable String value, int amount) {
		assert amount >= 0 : amount;
		if (value == null)
			return "";
		if (amount <= 0)
			return "";
		if (amount == 1)
			return value;
		final char[] input = value.toCharArray();
		final char[] multiplied = new char[input.length * amount];
		for (int i = 0; i < amount; i++)
			System.arraycopy(input, 0, multiplied, i * input.length, input.length);
		return new String(multiplied);
	}
	
	public static String multiply(char c, int amount) {
		if (amount == 0)
			return "";
		final char[] multiplied = new char[amount];
		Arrays.fill(multiplied, c);
		return new String(multiplied);
	}
	
	public static String join(Object @Nullable [] strings) {
		if (strings == null)
			return "";
		return join(strings, "", 0, strings.length);
	}
	
	public static String join(Object @Nullable [] strings, String delimiter) {
		if (strings == null)
			return "";
		return join(strings, delimiter, 0, strings.length);
	}
	
	public static String join(Object @Nullable [] strings, String delimiter, int start, int end) {
		if (strings == null)
			return "";
		assert start >= 0 && start <= end && end <= strings.length : start + ", " + end + ", " + strings.length;
		if (start < 0 || start >= strings.length || start == end)
			return "";
		final StringBuilder b = new StringBuilder("" + strings[start]);
		for (int i = start + 1; i < end; i++) {
			b.append(delimiter);
			b.append(strings[i]);
		}
		return "" + b;
	}
	
	public static String join(@Nullable Iterable<?> strings) {
		if (strings == null)
			return "";
		return join(strings.iterator(), "");
	}
	
	public static String join(@Nullable Iterable<?> strings, String delimiter) {
		if (strings == null)
			return "";
		return join(strings.iterator(), delimiter);
	}
	
	public static String join(@Nullable Iterator<?> strings, String delimiter) {
		if (strings == null || !strings.hasNext())
			return "";
		final StringBuilder b = new StringBuilder("" + strings.next());
		while (strings.hasNext()) {
			b.append(delimiter);
			b.append(strings.next());
		}
		return "" + b;
	}
	
	/**
	 * Scans the string starting at <tt>start</tt> for digits.
	 *
	 * @param start Index of the first digit
	 * @return The index <i>after</i> the last digit or <tt>start</tt> if there are no digits at the given index
	 */
	public static int findLastDigit(String value, int start) {
		int end = start;
		while (end < value.length() && '0' <= value.charAt(end) && value.charAt(end) <= '9')
			end++;
		return end;
	}

	/**
	 * Searches for whether a String contains any of the characters of another string.
	 */
	public static boolean containsAny(String value, String chars) {
		for (int i = 0; i < chars.length(); i++) {
			if (value.indexOf(chars.charAt(i)) != -1)
				return true;
		}
		return false;
	}

	public static boolean equals(String one, String other, boolean caseSensitive) {
		return caseSensitive ? one.equals(other) : one.equalsIgnoreCase(other);
	}

	public static boolean contains(String haystack, String needle, boolean caseSensitive) {
		if (caseSensitive)
			return haystack.contains(needle);
		return haystack.toLowerCase().contains(needle.toLowerCase());
	}

	public static String replace(String haystack, String needle, String replacement, boolean caseSensitive) {
		if (caseSensitive)
			return haystack.replace(needle, replacement);
		return haystack.replaceAll("(?ui)" + Pattern.quote(needle), Matcher.quoteReplacement(replacement));
	}

	public static String replaceFirst(String haystack, String needle, String replacement, boolean caseSensitive) {
		if (caseSensitive)
			return haystack.replaceFirst(needle, replacement);
		return haystack.replaceFirst("(?ui)" + Pattern.quote(needle), replacement);
	}

	public static byte[] hexStringToByteArray(String value) {
		return EffSendResourcePack.hexStringToByteArray(value);
	}

	public static int indexOfOutsideGroup(String string, char find, char groupOpen, char groupClose, int i) {
		return AliasesParser.indexOfOutsideGroup(string, find, groupOpen, groupClose, i);
	}
}
