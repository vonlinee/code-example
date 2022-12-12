package io.devpl.codegen.fxui.framework;

import java.lang.ref.WeakReference;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 单例工厂
 */
public class SingletonFactory {
    private static final Map<Class<?>, Object> instances = new ConcurrentHashMap<>();
    private static final Map<Class<?>, WeakReference<Object>> weakReferenceInstances = new ConcurrentHashMap<>();

    /**
     * 创建可不被回收的单例模式,当没有对象引用，单例对象将被gc掉
     * @param className
     * @return
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    @SuppressWarnings("unchecked")
    public static <E> E getInstance(Class<E> className) {
        Object instance = instances.get(className);
        if (instance == null) {
            synchronized (SingletonFactory.class) {
                instance = instances.get(className);
                if (instance == null) {
                    try {
                        instance = className.newInstance();
                    } catch (InstantiationException | IllegalAccessException e) {
                        e.printStackTrace();
                    }
                    instances.put(className, instance);
                }
            }
        }
        return (E) instance;
    }

    /**
     * 创建可回收的单例模式,当没有对象引用，单例对象将被gc掉
     * @param className
     * @return
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    @SuppressWarnings("unchecked")
    public static <E> E getWeakInstace(Class<E> className) {
        WeakReference<Object> reference = weakReferenceInstances.get(className);
        Object instace = reference == null ? null : reference.get();
        if (instace == null) {
            synchronized (SingletonFactory.class) {
                reference = weakReferenceInstances.get(className);
                instace = reference == null ? null : reference.get();
                if (instace == null) {
                    try {
                        instace = className.getDeclaredConstructor().newInstance();
                    } catch (InstantiationException | IllegalAccessException | NoSuchMethodException |
                             InvocationTargetException e) {
                        e.printStackTrace();
                    }
                    weakReferenceInstances.put(className, new WeakReference<Object>(instace));
                }
            }
        }
        return (E) instace;
    }

}