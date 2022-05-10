package io.maker.codegen.core.event;
import java.util.EventListener;

@FunctionalInterface
public interface EventHandler<T extends Event> extends EventListener {
	void handle(T var1);
}