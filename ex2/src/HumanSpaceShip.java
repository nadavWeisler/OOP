import oop.ex2.GameGUI;
import oop.ex2.SpaceShipPhysics;

import java.awt.*;

public class HumanSpaceShip extends SpaceShip {

    /**
     * SpaceShip Physics object
     */
    private SpaceShipPhysics physics;

    /**
     * SpaceShip health count
     */
    private int health = 22;

    /**
     * Boolean represent if shield is on
     */
    private boolean isShieldOn = false;

    /**
     * Max of energy
     */
    private int maxEnergy = 210;

    /**
     * Current amount of energy
     */
    private int currentEnergy = 190;

    /**
     * Count of shot rounds
     */
    private int lastShotRound = Integer.MIN_VALUE;

    /**
     * Round count
     */
    private int roundCount = 0;

    /**
     * Contractor
     */
    public HumanSpaceShip() {

    }

    private boolean ReduceEnergy(int energyCount) {
        if (this.currentEnergy > energyCount) {
            this.currentEnergy -= energyCount;
            return true;
        } else {
            return false;
        }
    }

    /**
     * Do human spaceship action
     */
    @Override
    public void doAction(SpaceWars game) {
        //Teleport
        if (game.getGUI().isTeleportPressed()) {
            this.teleport();
        }

        //Move + Accelerate
        boolean acceleration = false;
        int turn = 0;

        if (game.getGUI().isUpPressed()) {
            acceleration = true;
        }

        if (game.getGUI().isLeftPressed()) {
            if (!game.getGUI().isRightPressed()) {
                turn = 1;
            }
        } else if (game.getGUI().isRightPressed()) {
            turn = -1;
        }

        this.physics.move(acceleration, turn);

        //Shield
        if (game.getGUI().isShieldsPressed()) {
            this.shieldOn();
        } else {
            this.isShieldOn = false;
        }

        //Shot
        if (game.getGUI().isShotPressed()) {
            if (this.roundCount - this.lastShotRound > 7) {
                lastShotRound = this.roundCount;
                this.fire(game);
            }
        }

        //Regeneration
        if (this.currentEnergy < this.maxEnergy) {
            this.currentEnergy++;
        }

        this.currentEnergy++;
    }

    /**
     *
     */
    @Override
    public void collidedWithAnotherShip() {
        this.gotHit();
    }

    /**
     * Set all start values for properties
     */
    @Override
    public void reset() {
        this.health = 22;
        this.physics = new SpaceShipPhysics();
        this.maxEnergy = 210;
        this.currentEnergy = 190;
    }

    /**
     * Check if SpaceShip is Dead
     */
    @Override
    public boolean isDead() {
        return health == 0;
    }

    /**
     * Get physics object
     *
     * @return SpaceShipPhysics
     */
    @Override
    public SpaceShipPhysics getPhysics() {
        return this.physics;
    }

    /**
     * Deal with hit
     */
    @Override
    public void gotHit() {
        if (!isShieldOn) {
            this.health--;
            this.maxEnergy -= 10;
            if (this.maxEnergy < this.currentEnergy) {
                this.currentEnergy = this.maxEnergy;
            }
        }
    }

    /**
     * Return spaceship image
     *
     * @return Image
     */
    @Override
    public Image getImage() {
        if (this.isShieldOn) {
            return GameGUI.SPACESHIP_IMAGE_SHIELD;
        } else {
            return GameGUI.SPACESHIP_IMAGE;
        }
    }

    /**
     * Make a shot
     *
     * @param game the game object.
     */
    @Override
    public void fire(SpaceWars game) {
        game.addShot(this.physics);
        this.ReduceEnergy(19);
    }

    /**
     * Turn on shield if possible
     */
    @Override
    public void shieldOn() {
        this.isShieldOn = this.ReduceEnergy(3);
    }

    /**
     * Teleport current spaceship
     */
    @Override
    public void teleport() {
        if (this.ReduceEnergy(140)) {
            this.physics = new SpaceShipPhysics();
        }
    }
}
