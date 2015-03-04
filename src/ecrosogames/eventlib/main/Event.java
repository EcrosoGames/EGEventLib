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
 * An Event is something that should be handled by the {@link EventManager}.
 * 
 * @author Michael Musgrove
 */
public class Event {

	private boolean cancelled;

	/**
	 * Returns whether or not this event is cancelled.
	 * 
	 * @return
	 */
	public boolean isCancelled() {
		return cancelled;
	}

	/**
	 * Sets whether or not the event should be cancelled.
	 * 
	 * @param cancelled
	 *            If true, the event will be cancelled; if false, the event
	 *            won't be cancelled.
	 */
	public void setCancelled(boolean cancelled) {
		this.cancelled = cancelled;
	}
}
