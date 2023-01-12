package io.devpl.sdk.util;

import com.google.common.base.Strings;
import io.devpl.sdk.validation.Validator;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * 异常工具类
 */
public class ExceptionUtils {

    /**
     * Assemble the detail message for the throwable with all of its cause included (at most 10 causes).
     *
     * @param ex the exception
     * @return the message along with its causes
     */
    public static String getDetailMessage(Throwable ex) {
        if (ex == null || Validator.isNullOrEmpty(ex.getMessage())) {
            return "";
        }
        StringBuilder builder = new StringBuilder(ex.getMessage());
        List<Throwable> causes = new LinkedList<>();
        int counter = 0;
        Throwable current = ex;
        //retrieve up to 10 causes
        while (current.getCause() != null && counter < 10) {
            Throwable next = current.getCause();
            causes.add(next);
            current = next;
            counter++;
        }
        for (Throwable cause : causes) {
            if (Validator.isNullOrEmpty(cause.getMessage())) {
                counter--;
                continue;
            }
            builder.append(" [Cause: ").append(cause.getMessage());
        }
        builder.append(Strings.repeat("]", counter));
        return builder.toString();
    }

    /**
     * 过滤栈帧
     * @param throwable
     * @param ignorePackagePrefix
     * @return
     */
    public static StackTraceElement[] getStackTrace(Throwable throwable, String ignorePackagePrefix) {
        List<StackTraceElement> stackTraceElementList = new ArrayList<>();
        StackTraceElement[] stackTraceElements = throwable.getStackTrace();
        for (int i = 0; i < stackTraceElements.length; i++) {
            if (stackTraceElements[i].getClassName().startsWith(ignorePackagePrefix)) {
                continue;
            }
            stackTraceElementList.add(stackTraceElements[i]);
        }
        return stackTraceElementList.toArray(new StackTraceElement[0]);
    }
}