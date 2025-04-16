package it.unibo.michelito.model.player.components.api;

import it.unibo.michelito.model.blanckspace.api.BlankSpace;
import it.unibo.michelito.model.maze.api.Maze;
import it.unibo.michelito.model.util.hitbox.api.HitBox;
import it.unibo.michelito.model.powerups.api.PowerUp;
import it.unibo.michelito.util.Position;
import it.unibo.michelito.model.wall.api.Wall;
import it.unibo.michelito.model.box.api.Box;

import java.util.Optional;

/**
 * Interface that models a {@link HitBox} manager wrapping a {@link HitBox}.
 */
public interface HitBoxComponent {
    /**
     * Gets the {@link HitBox} that is managed.
     * @return the {@link HitBox}
     */
    HitBox getHitBox();

    /**
     * Updates the contained {@link HitBox} taking a new {@link Position} that represents the new center of the {@link HitBox}.
     * @param position the new center of the {@link HitBox}
     */
    void update(Position position);

    /**
     * Method that checks if the {@link HitBox} is colliding with a {@link Wall} or {@link Box} in the {@link Maze}.
     * @param maze the {@link Maze} that contains {@link Wall}s and {@link Box}es
     * @return boolean representing if it is colliding
     */
    boolean checkCollisionWallsBoxes(Maze maze);

    /**
     * Method that checks if the {@link HitBox} is colliding with a {@link PowerUp}.
     * @param maze the {@link Maze} that contains {@link PowerUp}s
     * @return boolean representing if it is colliding
     */
    Optional<PowerUp> checkCollisionPowerUp(Maze maze);

    /**
     * Method that finds the closest {@link BlankSpace} colliding with the {@link HitBox}.
     * @param maze maze the {@link Maze} that contains {@link BlankSpace}s
     * @return an {@link Optional} containing the closest colliding {@link BlankSpace} or {@link Optional}.empty if there is none
     */
    Optional<BlankSpace> closestBlankSpace(Maze maze);
}
