package it.unibo.michelito.model.enemy.api.ai;

/**
 * A factory for the various mood that an Enemy can have.
 */
public interface MoodAIFactory {
    /**
     * @return a mood where the enemy spend most of the time doing nothing.
     */
    MovementAI chilling();

    /**
     * @return a mood where the enemy don't move as if he were sleeping.
     */
    MovementAI sleeping();

    /**
     * @return a mood where the enemy is more active and don't waste time doing nothing.
     */
    MovementAI searching();
}
