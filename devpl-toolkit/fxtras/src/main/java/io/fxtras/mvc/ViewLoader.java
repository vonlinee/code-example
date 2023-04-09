package io.fxtras.mvc;

import io.fxtras.utils.WeakValueHashMap;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;
import java.net.URL;

public class ViewLoader {

    private static final WeakValueHashMap<Class<?>, ViewLoader>
            loaderCache = new WeakValueHashMap<>();

    private final Parent rootNode;
    private final Object viewController;

    public ViewLoader(Parent rootNode, Object viewController) {
        this.rootNode = rootNode;
        this.viewController = viewController;
    }

    public static ViewLoader load(Class<?> clazz) {
        if (!View.class.isAssignableFrom(clazz)) {
            throw new IllegalArgumentException(String.format("cannot load class View [%s]", clazz));
        }
        ViewLoader loader = loaderCache.get(clazz);
        if (loader == null) {
            // 重新加载
            FxmlLocation fxmlInfo = clazz.getAnnotation(FxmlLocation.class);
            if (fxmlInfo != null) {
                String fxmlLocation = fxmlInfo.location();
                if (fxmlLocation.isEmpty()) {
                    String packageName = clazz.getPackageName();
                    fxmlLocation = packageName.replace(".", "/") + "/" + clazz.getSimpleName() + ".fxml";
                }
                // JDK17 通过此方式获取不到资源
                URL resource = Thread.currentThread().getContextClassLoader().getResource(fxmlLocation);
                FXMLLoader fxmlLoader = new FXMLLoader(resource);
                try {
                    loaderCache.put(clazz, loader = new ViewLoader(fxmlLoader.load(), fxmlLoader.getController()));
                } catch (IOException e) {
                    throw new RuntimeException("failed to load fxml [" + fxmlLocation + "]", e);
                }
            } else {
                throw new RuntimeException("暂不支持非FXML类型的视图加载");
            }
        }
        return loader;
    }

    @SuppressWarnings("unchecked")
    public <T extends Parent> T getRoot() {
        return (T) rootNode;
    }

    @SuppressWarnings("unchecked")
    public <T extends View> T getViewController() {
        return (T) viewController;
    }
}