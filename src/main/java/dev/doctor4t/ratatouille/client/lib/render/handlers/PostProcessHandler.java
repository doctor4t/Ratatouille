package dev.doctor4t.ratatouille.client.lib.render.handlers;

import dev.doctor4t.ratatouille.client.lib.render.systems.postprocess.PostProcessor;
import net.minecraft.client.util.math.MatrixStack;

import java.util.ArrayList;
import java.util.List;

/*
    Original class based on the Quilt port (arathain) of the Lodestone library (Sammy; and Lodestar)
 */

/**
 * Handles world-space post-processing.
 * Based on vanilla {@link net.minecraft.client.gl.PostEffectProcessor} system, but allows the shader to access the world depth buffer.
 */
public class PostProcessHandler {
    private static final List<PostProcessor> instances = new ArrayList<>();

    private static boolean didCopyDepth = false;

    /**
     * Add an {@link PostProcessor} for it to be handled automatically.
     * IMPORTANT: processors has to be added in the right order!!!
     * There's no way of getting an instance, so you need to keep the instance yourself.
     */
    public static void addInstance(PostProcessor instance) {
        instances.add(instance);
    }

    public static void copyDepthBuffer() {
        if (didCopyDepth) return;
        instances.forEach(PostProcessor::copyDepthBuffer);
        didCopyDepth = true;
    }

    public static void resize(int width, int height) {
        instances.forEach(i -> i.resize(width, height));
    }

    public static void renderLast(MatrixStack matrices) {
        copyDepthBuffer(); // copy the depth buffer if the mixin didn't trigger

        PostProcessor.viewModelStack = matrices;
        instances.forEach(PostProcessor::applyPostProcess);

        didCopyDepth = false; // reset for next frame
    }
}
