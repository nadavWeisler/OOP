import oop.ex2.GameGUI;
import oop.ex2.SpaceShipPhysics;

import java.awt.*;

/**
 * The API spaceships need to implement for the SpaceWars game.
 * It is your decision whether SpaceShip.java will be an interface, an abstract class,
 * a base class for the other spaceships or any other option you will choose.
 *
 * @author oop
 */
public abstract class SpaceShip {
    private final int START_HEALTH = 22;
    private final int START_MAX_ENERGY = 210;
    private final int START_CURRENT_ENERGY = 190;

    /**
     * SpaceShip Physics object
     */
    SpaceShipPhysics physics;

    /**
     * SpaceShip health count
     */
    int health;

    /**
     * Boolean represent if shield is on
     */
    boolean isShieldOn;

    /**
     * Max of energy
     */
    private int maxEnergy;

    /**
     * Current amount of energy
     */
    private int currentEnergy;

    /**
     * Count of shot rounds
     */
    int lastShotRound = -7;

    /**
     * Round count
     */
    int roundCount = 0;

    SpaceShip() {
        this.physics = new SpaceShipPhysics();
        this.health = START_HEALTH;
        this.maxEnergy = START_MAX_ENERGY;
        this.currentEnergy = START_CURRENT_ENERGY;
        this.isShieldOn = false;
    }

    /**
     * String representation of space ship - abstract
     *
     * @return string
     */
    public abstract String toString();

    /**
     * Move closer, away or strait from closest spaceship
     *
     * @param closestShip Closest space ship, SpaceShip object
     * @param near        Boolean represent if going forward or backward
     */
    void GetCloseOrAwayNearestShip(SpaceShip closestShip, boolean near) {
        int turn = 0;

        if ((this.physics.angleTo(closestShip.physics) < 0)) {
            if (near) {
                turn = -1;
            } else {
                turn = 1;
            }
        } else if ((this.physics.angleTo(closestShip.physics) > 0)) {
            if (near) {
                turn = 1;
            } else {
                turn = -1;
            }
        }
        this.physics.move(true, turn);
    }

    /**
     * Does the actions of this ship for this round.
     * This is called once per round by the SpaceWars game driver.
     *
     * @param game the game object to which this ship belongs.
     */
    public abstract void doAction(SpaceWars game);

    /**
     * This method is called every time a collision with this ship occurs
     */
    public void collidedWithAnotherShip() {
        //Bashing
        if(this.isShieldOn) {
            this.maxEnergy += 18;
            this.currentEnergy += 18;
        }

        this.gotHit();
    }

    /**
     * This method is called whenever a ship has died. It resets the ship's
     * attributes, and starts it at a new random position.
     */
    public void reset() {
        this.health = START_HEALTH;
        this.physics = new SpaceShipPhysics();
        this.maxEnergy = START_MAX_ENERGY;
        this.currentEnergy = START_CURRENT_ENERGY;
    }

    /**
     * Process That happen in every action
     */
    protected void RegenerationAndAddToRoundCount() {
        //Regeneration
        if (this.currentEnergy < this.maxEnergy) {
            this.currentEnergy++;
        }

        this.roundCount++;
    }

    /**
     * Checks if this ship is dead.
     *
     * @return true if the ship is dead. false otherwise.
     */
    public boolean isDead() {
        return this.health == 0;
    }

    /**
     * Gets the physics object that controls this ship.
     *
     * @return the physics object that controls the ship.
     */
    public SpaceShipPhysics getPhysics() {
        return this.physics;
    }

    /**
     * This method is called by the SpaceWars game object when ever this ship
     * gets hit by a shot.
     */
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
     * Gets the image of this ship. This method should return the image of the
     * ship with or without the shield. This will be displayed on the GUI at
     * the end of the round.
     *
     * @return the image of this ship.
     */
    public Image getImage() {
        if (this.toString().equals("HumanSpaceShip")) {
            if (this.isShieldOn) {
                return GameGUI.SPACESHIP_IMAGE_SHIELD;
            } else {
                return GameGUI.SPACESHIP_IMAGE;
            }
        } else {
            if (this.isShieldOn) {
                return GameGUI.ENEMY_SPACESHIP_IMAGE_SHIELD;
            } else {
                return GameGUI.ENEMY_SPACESHIP_IMAGE;
            }
        }
    }

    /**
     * Attempts to fire a shot.
     *
     * @param game the game object.
     */
    public void fire(SpaceWars game) {
        if (this.ReduceEnergy(19)) {
            game.addShot(this.physics);
        }
    }

    /**
     * Reduce energy amount if has it, else do nothing
     *
     * @param energyCount Energy amount to reduce
     * @return If reduces
     */
    private boolean ReduceEnergy(int energyCount) {
        if (this.currentEnergy > energyCount) {
            this.currentEnergy -= energyCount;
            return true;
        } else {
            return false;
        }
    }

    /**
     * Attempts to turn on the shield.
     */
    public void shieldOn() {
        this.isShieldOn = this.ReduceEnergy(3);
    }

    /**
     * Attempts to teleport.
     */
    public void teleport() {
        if (this.ReduceEnergy(140)) {
            this.physics = new SpaceShipPhysics();
        }
    }

}
