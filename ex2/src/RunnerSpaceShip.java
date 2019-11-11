public class RunnerSpaceShip extends SpaceShip {

    /**
     * Runner spaceship constructor
     */
    RunnerSpaceShip() {
        super();
    }

    /**
     * String representation of runner space ship
     * @return string
     */
    @Override
    public String toString() {
        return "RunnerSpaceShip";
    }

    /**
     * Runner SpaceShip doAction
     *
     * @param game the game object to which this ship belongs.
     */
    @Override
    public void doAction(SpaceWars game) {
        SpaceShip closestShip = game.getClosestShipTo(this);
        if (this.physics.angleTo(closestShip.physics) <0.23 &&
                this.physics.distanceFrom(closestShip.physics) < 0.25){
            teleport();
        }

        this.GetCloseOrAwayNearestShip(game, game.getClosestShipTo(this), false);

        this.RegenerationAndAddToRoundCount();
    }
}
