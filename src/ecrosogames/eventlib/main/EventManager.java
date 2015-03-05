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

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * A manager for handling each and every established {@link Event}.
 * 
 * @author Michael Musgrove
 */
public class EventManager {

	private static final Logger logger = Logger.getLogger("EGEventManager");

	private static final List<Class<? extends Event>> eventClasses = new ArrayList<>();
	private static final List<Class<? extends EventListener>> registeredListeners = new ArrayList<>();

	private EventManager() {
	}

	/**
	 * Registers the specified {@link Event} class for handling its events.
	 * 
	 * @param event
	 *            The {@link Event} that should be registered.
	 * @return <code>true</code> if the {@link Event} successfully registered,
	 *         <code>false</code> if not.
	 */
	public static boolean registerEventClass(Class<? extends Event> event) {
		if (!eventClasses.contains(event)) {
			eventClasses.add(event);
			return true;
		}
		return false;
	}

	/**
	 * Unregisters the specified {@link Event} class so it is no longer handled.
	 * Any methods that handled this type of event aren't removed, in case the
	 * class is registered again.
	 * 
	 * @param event
	 *            The {@link Event} that should be unregistered.
	 * @return <code>true</code> if the {@link Event} successfully unregistered,
	 *         <code>false</code> if not.
	 */
	public static boolean unregisterEventClass(Class<? extends Event> event) {
		if (eventClasses.contains(event)) {
			eventClasses.remove(event);
			return true;
		}
		return false;
	}

	/**
	 * Checks if the specified {@link Event} is registered.
	 * 
	 * @param eventClass
	 *            The {@link Event} that you want to see is registered.
	 * @return <code>true</code> if the {@link Event} is registered,
	 *         </code>false</code> if not.
	 */
	public static boolean isEventClassRegistered(Class<? extends Event> eventClass) {
		return eventClasses.contains(eventClass);
	}

	/**
	 * Registers a new {@link EventListener}. If the class for the
	 * {@link EventListener} has already been registered, it will not register
	 * and it will return null.
	 * 
	 * @param listener
	 *            The {@link EventListener} to register.
	 * @return The list of {@link RegisteredEvent} objects that were
	 *         successfully created in the {@link EventListener}.
	 */
	public static List<RegisteredEvent> registerEventListener(EventListener listener) {
		List<RegisteredEvent> newlyRegistered = null;
		if (!registeredListeners.contains(listener)) {
			newlyRegistered = registerEventHandlers(listener);
			registeredListeners.add(listener.getClass());
		}
		return newlyRegistered;
	}

	/**
	 * Registers the {@link EventHandler} annotations from the
	 * {@link EventListener}.
	 * 
	 * @param listener
	 *            The {@link EventListener} to register the {@link EventHandler}
	 *            <code>s</code> from.
	 * @return The List of {@link RegisteredEvent}<code>s</code> from the
	 *         registered {@link EventHandler} annotations.
	 */
	@SuppressWarnings("unchecked")
	private static List<RegisteredEvent> registerEventHandlers(EventListener listener) {
		List<RegisteredEvent> newlyRegistered = new ArrayList<>();

		try {
			Class<? extends EventListener> eventListenerClass = listener.getClass();
			Method[] classMethods = eventListenerClass.getDeclaredMethods();
			for (int i = 0; i < classMethods.length; i++) {
				Method method = classMethods[i];
				if (method.getParameterCount() != 1) continue;
				EventHandler[] methodAnnotations = method.getDeclaredAnnotationsByType(EventHandler.class);
				if (methodAnnotations.length == 0) continue;
				EventHandler eventHandlerAnnotation = methodAnnotations[0];
				EventPriority priority = eventHandlerAnnotation.value();
				Class<? extends Event> eventClass = (Class<? extends Event>) method.getParameterTypes()[0];
				PrioritizedEvents.addRegisteredEvent(new RegisteredEvent(listener, method, eventClass, priority));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return newlyRegistered;
	}

	/**
	 * Unregisters the specified {@link EventListener}.
	 * 
	 * @param listener
	 *            The {@link EventListener} to unregister.
	 * @return <code>true</code> if the {@link EventListener} was successfully
	 *         unregistered, or <code>false</code> if not. It may return
	 *         <code>false</code> because the {@link EventListener} was never
	 *         registered.
	 */
	public static boolean unregisterEventListener(EventListener listener) {
		if (registeredListeners.contains(listener)) {
			registeredListeners.remove(listener.getClass());
			return true;
		}
		return false;
	}

	/**
	 * Returns whether or not the specified {@link EventListener} class is
	 * registered.
	 * 
	 * @param listenerClass
	 *            The {@link EventListener} class that you want to check is
	 *            registered or not.
	 * @return
	 */
	public static boolean isEventListenerRegistered(Class<? extends EventListener> listenerClass) {
		return registeredListeners.contains(listenerClass);
	}

	/**
	 * Calls a check to {@link #isEventClassRegistered(Class)}. If not, it will
	 * log a warning, and the {@link Event} won't be called.
	 * 
	 * @param eventClass
	 *            The {@link Event} class to check is registered.
	 * @return <code>true</code> if it is, <code>false</code> if not after it
	 *         logs a warning.
	 */
	private static boolean checkIsEventClassRegistered(Class<? extends Event> eventClass) {
		boolean registered = isEventClassRegistered(eventClass);
		if (registered) return true;
		logger.warning("EventManager.call(Class<? extends Event>) cancelled: event is not contained in the registered Event classes!");
		return false;
	}

	/**
	 * Calls the specified {@link Event} class. The Object arguments must match
	 * a constructor, or an exception will be thrown when searching for the
	 * Constructor. The {@link Event} will not run if the specified class is not
	 * registered.
	 * 
	 * @see #call(Class, Object...)
	 * 
	 * @param eventExecutor
	 *            What should execute if the {@link Event} is not cancelled.
	 * @param eventClass
	 *            The {@link Event} that should be called.
	 * @param eventArgs
	 *            The Constructor arguments for the wanted Constructor.
	 * @return
	 */
	public static final <T extends Event> void call(EventExecutor<T> eventExecutor, Class<T> eventClass, Object... eventArgs) {
		if (!checkIsEventClassRegistered(eventClass)) return;
		try {
			Class<?>[] constructorParameters = EventUtilities.getArrayOfClasses(eventArgs);
			Constructor<?> constructor = eventClass.getDeclaredConstructor(constructorParameters);

			T event = (T) eventClass.cast(constructor.newInstance(eventArgs));
			if (!callAllRegisteredMethods(event)) eventExecutor.execute(event);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Calls the specified {@link Event} class. The Object arguments must match
	 * a constructor, or an exception will be thrown when searching for the
	 * Constructor. This method WILL NOT run any <code>execute</code> method
	 * after all of the Methods for the {@link Event} have ran. If you'd like a
	 * method that would run the wanted {@link EventExecutor}, look in the
	 * <strong>See Also</strong> section. Also, the {@link Event} won't run if
	 * the specified class isn't registered.
	 * 
	 * @see #call(EventExecutor, Class, Object...)
	 * 
	 * @param eventClass
	 *            The {@link Event} that should be called.
	 * @param eventArgs
	 *            The Constructor arguments for the wanted Constructor.
	 */
	public static void call(Class<? extends Event> eventClass, Object... eventArgs) {
		try {
			Event event = null;

			Class<?>[] constructorParameters = EventUtilities.getArrayOfClasses(eventArgs);
			Constructor<?> constructor = eventClass.getDeclaredConstructor(constructorParameters);

			event = (Event) constructor.newInstance(eventArgs);

			callAllRegisteredMethods(event);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * A method that calls all of the Registered Methods.
	 * 
	 * @param eventClass
	 *            The {@link Event} class that is being called.
	 * @param eventArgs
	 *            The {@link Event} arguments.
	 * @return Whether or not the {@link Event} has been cancelled.
	 */
	private static boolean callAllRegisteredMethods(Event event) {
		try {
			List<RegisteredEvent> lowPriority = PrioritizedEvents.getRegisteredEvents(EventPriority.Low);
			List<RegisteredEvent> normalPriority = PrioritizedEvents.getRegisteredEvents(EventPriority.Normal);
			List<RegisteredEvent> highPriority = PrioritizedEvents.getRegisteredEvents(EventPriority.High);

			for (int i = 0; i < lowPriority.size(); i++) {
				RegisteredEvent registeredEvent = lowPriority.get(i);
				EventListener listener = registeredEvent.getListener();
				Method method = registeredEvent.getMethod();

				if (registeredEvent.getEventClass() != event.getClass() || !registeredListeners.contains(listener.getClass())) continue;
				method.invoke(listener, event);
			}
			for (int i = 0; i < normalPriority.size(); i++) {
				RegisteredEvent registeredEvent = normalPriority.get(i);
				EventListener listener = registeredEvent.getListener();
				Method method = registeredEvent.getMethod();

				if (registeredEvent.getEventClass() != event.getClass() || !registeredListeners.contains(listener.getClass())) {
					continue;
				}
				method.invoke(listener, event);
			}
			for (int i = 0; i < highPriority.size(); i++) {
				RegisteredEvent registeredEvent = highPriority.get(i);
				EventListener listener = registeredEvent.getListener();
				Method method = registeredEvent.getMethod();

				if (registeredEvent.getEventClass() != event.getClass() || !registeredListeners.contains(listener.getClass())) continue;
				method.invoke(listener, event);
			}
			return event.isCancelled();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * A helper to help organize the {@link EventPriority} of each
	 * {@link RegisteredEvent}.
	 * 
	 * @author Michael Musgrove
	 */
	static class PrioritizedEvents {

		private static final Map<EventPriority, List<RegisteredEvent>> prioritized = new HashMap<>();

		static {
			EventPriority[] values = EventPriority.values();
			for (int i = 0; i < values.length; i++) {
				EventPriority priority = values[i];
				prioritized.put(priority, new ArrayList<>());
			}
		}

		/**
		 * Returns a {@link List} of Objects from the {@link RegisteredEvent}
		 * class, based on the type of {@link EventPriority} that the list is
		 * taking.
		 * 
		 * @param priority
		 *            The {@link EventPriority} of lists to grab.
		 * @return The returned {@link} List of {@link RegisteredEvent} Objects.
		 */
		public static List<RegisteredEvent> getRegisteredEvents(EventPriority priority) {
			return prioritized.get(priority);
		}

		/**
		 * Adds a {@link RegisteredEvent} to a {@link List} based on the
		 * {@link EventPriority}.
		 * 
		 * @param priority
		 *            The {@link EventPriority} of the
		 *            {@link RegisteredListener}.
		 * @param registeredEvent
		 *            The {@link RegisteredEvent} to add to the {@link List}
		 *            based on the {@link EventPriority}.
		 * @return <code>true</code> if the {@link RegisteredEvent} was
		 *         successfully added, <code>false</code> if not.
		 */
		public static boolean addRegisteredEvent(RegisteredEvent registeredEvent) {
			getRegisteredEvents(registeredEvent.getPriority()).add(registeredEvent);
			return true;
		}
	}
}
