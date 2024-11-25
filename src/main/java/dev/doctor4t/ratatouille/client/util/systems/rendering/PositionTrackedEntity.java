package dev.doctor4t.ratatouille.client.util.systems.rendering;

import net.minecraft.util.math.Vec3d;

import java.util.ArrayList;

/*
    Original class based on the Quilt port (arathain) of the Lodestone library (Sammy; and Lodestar)
 */
public interface PositionTrackedEntity {
    void trackPastPositions();

    ArrayList<Vec3d> getPastPositions();
}
