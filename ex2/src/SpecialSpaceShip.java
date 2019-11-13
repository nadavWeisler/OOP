public class SpecialSpaceShip extends SpaceShip {

    /**
     * Special spaceship constructor
     */
    SpecialSpaceShip() {
        super();
    }

    /**
     * String representation of special space ship
     * @return string
     */
    @Override
    public String toString() {
        return "SpecialSpaceShip";
    }

    /**
     * Do special spaceship action
     *
     * @param game the game object to which this ship belongs.
     */
    @Override
    public void doAction(SpaceWars game) {
        SpaceShip closestSpaceShip = game.getClosestShipTo(this);

        if (closestSpaceShip.toString().equals("BasherSpaceShip") ||
                closestSpaceShip.toString().equals("AggressiveSpaceShip")) {
            //Move
            this.GetCloseOrAwayNearestShip(closestSpaceShip, true);

            //Fire
            if (this.physics.angleTo(closestSpaceShip.physics) < 0.1) {
                for (int i = 0; i < 5; i++) {
                    this.lastShotRound = -7;
                    this.fire(game);
                }
            }
        } else {
            this.GetCloseOrAwayNearestShip(closestSpaceShip, false);
        }

        if(this.health < 22 && this.roundCount % 5 == 0){
            health += 1;
        }

        this.RegenerationAndAddToRoundCount();
    }
}
