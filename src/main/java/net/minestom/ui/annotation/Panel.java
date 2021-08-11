package net.minestom.ui.annotation;

/**
 * An experimental Panel settings method.
 *
 * It highlights a few potential behaviors:
 *   - Some panels should always be open if hidden (eg Entity Inspector. Other panels will still rely on it for their selection)
 *   - Some panels could be opened twice (eg some kind of text editor or file browser)
 *   - Note: allowDuplicates=true,alwaysOpen=true is not an acceptable behavior I dont think.
 */
public @interface Panel {
    String name();

    boolean allowDuplicates() default false;
    boolean alwaysOpen() default false;
}
