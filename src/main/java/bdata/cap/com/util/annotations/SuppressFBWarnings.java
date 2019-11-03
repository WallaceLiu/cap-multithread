package bdata.cap.com.util.annotations;

/**
 * @program cap-multithread
 * @description:
 * @author: liuning11
 * @create: 2019/11/03
 */

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Used to suppress FindBugs warnings.
 * <p>
 * It should be used instead of
 * {@link edu.umd.cs.findbugs.annotations.SuppressWarnings} to avoid conflicts with
 * {@link java.lang.SuppressWarnings}.
 */
@Retention(RetentionPolicy.CLASS)
public @interface SuppressFBWarnings {
    /**
     * The set of FindBugs warnings that are to be suppressed in
     * annotated element. The value can be a bug category, kind or pattern.
     */
    String[] value() default {};

    /**
     * Optional documentation of the reason why the warning is suppressed
     */
    String justification() default "";
}
