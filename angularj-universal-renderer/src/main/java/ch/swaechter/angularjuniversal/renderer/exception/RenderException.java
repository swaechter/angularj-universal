package ch.swaechter.angularjuniversal.renderer.exception;

import org.jetbrains.annotations.NotNull;

/**
 * An Exception thrown during Rendering either by a remote renderer or locally by the rendering routine
 */
public class RenderException extends IllegalStateException {

    public RenderException(@NotNull String errorMessage) {
        super(errorMessage);
    }

    public RenderException(@NotNull Throwable cause) {
        super(cause);
    }

}
