public class BasherSpaceShip extends SpaceShip {

    /**
     * Basher space ship constructor
     */
    BasherSpaceShip() {
        super();
    }

    /**
     * String representation of basher space ship
     * @return string
     */
    @Override
    public String toString() {
        return "BasherSpaceShip";
    }

    /**
     * Do basher spaceship action
     * @param game the game object to which this ship belongs.
     */
    @Override
    public void doAction(SpaceWars game) {
        this.GetCloseOrAwayNearestShip(game.getClosestShipTo(this),true);

        if (this.physics.distanceFrom(game.getClosestShipTo(this).physics) <= 0.19) {
            this.shieldOn();
        }

        this.RegenerationAndAddToRoundCount();
    }
}
