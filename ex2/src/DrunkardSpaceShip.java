import java.util.Random;

public class DrunkardSpaceShip extends SpaceShip {

    /**
     * Random ship side, get its value at the constructor
     */
    private int shipSide;

    /**
     * Drunkard spaceship constructor
     */
    DrunkardSpaceShip() {
        Random r = new Random();
        this.shipSide = r.nextInt(2) - 1;
    }

    /**
     * String representation of drunkard space ship
     * @return string
     */
    @Override
    public String toString() {
        return "DrunkardSpaceShip";
    }

    /**
     * Do drunkard space ship action
     * @param game the game object to which this ship belongs.
     */
    @Override
    public void doAction(SpaceWars game) {
        this.physics.move(true, shipSide);

        if (this.roundCount % 9 == 0) {
            this.shieldOn();
        }

        if (this.roundCount % 11 == 0) {
            this.fire(game);
        }

        this.RegenerationAndAddToRoundCount();
    }
}
