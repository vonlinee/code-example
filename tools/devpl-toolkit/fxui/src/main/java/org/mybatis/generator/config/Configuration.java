package org.mybatis.generator.config;

import static org.mybatis.generator.internal.util.StringUtils.hasLength;
import static org.mybatis.generator.internal.util.messages.Messages.getString;

import java.util.ArrayList;
import java.util.List;

import org.mybatis.generator.exception.InvalidConfigurationException;

public class Configuration {

    private List<Context> contexts;

    private final List<String> classPathEntries;

    public Configuration() {
        super();
        contexts = new ArrayList<>();
        classPathEntries = new ArrayList<>();
    }

    public void addClasspathEntry(String entry) {
        classPathEntries.add(entry);
    }

    public List<String> getClassPathEntries() {
        return classPathEntries;
    }

    /**
     * This method does a simple validate, it makes sure that all required fields have been filled in and that all
     * implementation classes exist and are of the proper type. It does not do any more complex operations such as:
     * validating that database tables exist or validating that named columns exist
     * @throws InvalidConfigurationException the invalid configuration exception
     */
    public void validate() throws InvalidConfigurationException {
        List<String> errors = new ArrayList<>();

        for (String classPathEntry : classPathEntries) {
            if (!hasLength(classPathEntry)) {
                errors.add(getString("ValidationError.19")); //$NON-NLS-1$
                // only need to state this error once
                break;
            }
        }

        if (contexts.isEmpty()) {
            errors.add(getString("ValidationError.11")); //$NON-NLS-1$
        } else {
            for (Context context : contexts) {
                context.validate(errors);
            }
        }

        if (!errors.isEmpty()) {
            throw new InvalidConfigurationException(errors);
        }
    }

    public List<Context> getContexts() {
        return contexts;
    }

    public void addContext(Context context) {
        contexts.add(context);
    }

    public void removeContext(Context context) {
        contexts.remove(context);
    }

    public void setContexts(List<Context> contexts) {
        this.contexts = contexts;
    }
}
