public class HumanSpaceShip extends SpaceShip {

    /**
     * Human spaceship contractor
     */
    HumanSpaceShip() {
        super();
    }

    /**
     * String representation of human space ship
     * @return string
     */
    @Override
    public String toString() {
        return "HumanSpaceShip";
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
                this.lastShotRound = this.roundCount;
                this.fire(game);
            }
        }

        this.RegenerationAndAddToRoundCount();
    }

}
