package io.devpl.eventbus;

/**
 * This Event is posted by EventBus when an exception occurs inside a subscriber's event handling method.
 * @author Markus
 */
public final class SubscriberExceptionEvent {
    /**
     * The {@link DefaultEventBus} instance to with the original event was posted to.
     */
    public final DefaultEventBus eventBus;

    /**
     * The Throwable thrown by a subscriber.
     */
    public final Throwable throwable;

    /**
     * The original event that could not be delivered to any subscriber.
     */
    public final Object causingEvent;

    /**
     * The subscriber that threw the Throwable.
     */
    public final Object causingSubscriber;

    public SubscriberExceptionEvent(DefaultEventBus eventBus, Throwable throwable, Object causingEvent,
                                    Object causingSubscriber) {
        this.eventBus = eventBus;
        this.throwable = throwable;
        this.causingEvent = causingEvent;
        this.causingSubscriber = causingSubscriber;
    }

}
