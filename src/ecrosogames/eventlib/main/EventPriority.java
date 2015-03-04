/*
 * EGEventLib - A lightweight Java Event Managing System for handling your
 * program's events using Annotations. Copyright (C) 2015 Michael Musgrove
 * 
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * this program. If not, see <http://www.gnu.org/licenses/>.
 * 
 */
package ecrosogames.eventlib.main;

/**
 * A helper to distinguish the priorities for an {@link Event}. In essence, it
 * helps to establish what order each event method is called in.
 * 
 * @author Michael Musgrove
 */
public enum EventPriority {

	/**
	 * The Lowest priority. Any method marked with this will be called first, so
	 * it won't have much of a "last say" for the event.
	 */
	Low,

	/**
	 * The Normal priority. It's the default priority. Its opinion isn't the
	 * least important, but it's also not the most important.
	 */
	Normal,

	/**
	 * The High priority. Any events marked with this will have a near to last
	 * say for what should happen in the event.
	 */
	High;
}
