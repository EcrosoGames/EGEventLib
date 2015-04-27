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

import java.lang.reflect.Method;

/**
 * An {@link Event} that has been registered.
 * 
 * @author Michael Musgrove
 */
public class RegisteredEvent {

	private EventListener listener;
	private Method method;
	private Class<? extends Event> eventClass;
	private EventPriority priority;

	/**
	 * Creates a new RegisteredEvent.
	 * 
	 * @param listener
	 *            The {@link EventListener} that is registered for this event.
	 * @param method
	 *            The {@link Method} that was registered to the
	 *            {@link EventListener}.
	 * @param eventClass
	 *            The {@link Event} that the <code>method</code> is from.
	 * @param priority
	 *            The {@link EventPriority} of the RegisteredListener.
	 */
	public RegisteredEvent(EventListener listener, Method method, Class<? extends Event> eventClass, EventPriority priority) {
		this.listener = listener;
		this.method = method;
		this.eventClass = eventClass;
		this.priority = priority;
	}

	/**
	 * Returns the {@link EventListener} for this RegisteredListener.
	 * 
	 * @return
	 */
	public EventListener getListener() {
		return listener;
	}

	/**
	 * Returns the {@link Method} for this RegisteredListener.
	 * 
	 * @return
	 */
	public Method getMethod() {
		return method;
	}

	/**
	 * Returns the {@link Event} class for this RegisteredListener.
	 * 
	 * @return
	 */
	public Class<?> getEventClass() {
		return eventClass;
	}

	/**
	 * Returns the {@link EventPriority} for this RegisteredListener.
	 * 
	 * @return
	 */
	public EventPriority getPriority() {
		return priority;
	}
}
