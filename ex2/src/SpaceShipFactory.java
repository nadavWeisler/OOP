public class SpaceShipFactory {
    public static SpaceShip[] createSpaceShips(String[] args) {
        SpaceShip[] array = new SpaceShip[args.length];
        for (int i = 0; i < args.length; i++) {
            switch (args[i]) {
                case "h":
                    array[i] = new HumanSpaceShip();
                    break;
                case "r":
                    array[i] = new RunnerSpaceShip();
                    break;
                case "b":
                    array[i] = new BasherSpaceShip();
                    break;
                case "a":
                    array[i] = new AggressiveSpaceShip();
                    break;
                case "d":
                    array[i] = new DrunkardSpaceShip();
                    break;
                case "s":
                    array[i] = new SpecialSpaceShip();
                    break;
            }
        }
        return array;
    }
}
