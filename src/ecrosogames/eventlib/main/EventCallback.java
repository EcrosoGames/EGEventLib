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
 * When an {@link Event} isn't cancelled, this is the code that should run.
 * 
 * @author Michael Musgrove
 */
@FunctionalInterface
public interface EventCallback<T extends Event> {

	/**
	 * Executes the code that should run when an {@link Event} isn't cancelled.
	 * 
	 * @param event
	 *            The event that is to be executed if the {@link Event} wasn't
	 *            cancelled.
	 */
	public abstract void execute(T event);
}
