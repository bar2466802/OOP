import oop.ex2.*;

public class SpaceShipFactory {
        final static char HUMAN_SHIP_TYPE = 'h';
        final static char RUNNER_SHIP_TYPE = 'r';
        final static char DRUNK_SHIP_TYPE = 'd';
        final static char SPECIAL_SHIP_TYPE = 's';
        final static char AGGRESSIVE_SHIP_TYPE = 'a';
        final static char BASHER_SHIP_TYPE = 'b';
    public static SpaceShip[] createSpaceShips(String[] args)
    {
        final int MIN_ARGS_COUNT = 2;
        final int FIRST_CHAR = 0;
        int numberOfShips = args.length;
        if(numberOfShips >= MIN_ARGS_COUNT)
        {
            char shipType;
            SpaceShip[] ships = new SpaceShip[numberOfShips];
            for(int i = 0; i < numberOfShips; i++)
            {
                shipType = args[i].charAt(FIRST_CHAR);
                ships[i] = createShipByType(shipType);
                if(ships[i] == null) //check the creation was successful
                {
                    return null; //something went wrong during the creation
                }
            }
            return ships;
        }
        return null;
    }

    private static SpaceShip createShipByType(char type)
    {
        switch(type)
        {
            case HUMAN_SHIP_TYPE:
                return new HumanShip();
            case RUNNER_SHIP_TYPE:
                return new RunnerShip();
            case DRUNK_SHIP_TYPE:
                return new DrunkardShip();
            case SPECIAL_SHIP_TYPE:
                return new SpecialShip();
            case AGGRESSIVE_SHIP_TYPE:
                return new AggressiveShip();
            case BASHER_SHIP_TYPE:
                return new BasherShip();
            default:
                return null;
        }

    }
}
