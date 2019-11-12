public class AggressiveSpaceShip extends SpaceShip {

    /**
     * Aggressive spaceship constructor
     */
    AggressiveSpaceShip() {
        super();
    }

    /**
     * String representation of aggressive space ship
     * @return string
     */
    @Override
    public String toString() {
        return "AggressiveSpaceShip";
    }

    /**
     * Do aggressive spaceship action
     *
     * @param game the game object to which this ship belongs.
     */
    @Override
    public void doAction(SpaceWars game) {
        this.GetCloseOrAwayNearestShip(game.getClosestShipTo(this), true);

        if (this.physics.distanceFrom(game.getClosestShipTo(this).physics) < 0.21) {
            this.fire(game);
        }

        this.RegenerationAndAddToRoundCount();
    }
}
