import java.util.Random;
/**
 * This class represents a ship with a drunk driver inside the Space Wars game
 * It extends the abstract class SpaceShip
 * @author Bar Melinarskiy
 * @version 17/8/20
 */
public class DrunkardShip extends SpaceShip
{
	//Constants
	private static final double ANGLE_TO_FIRE = 0.21;
	private static final double DISTANCE_TO_TELEPORT = 0.25;
	private static final double ANGLE_TO_TELEPORT = 0.23;
	private static final int MAX_RANGE_DIVIDEND = 100;
	private static final int MIN_RANGE_DIVIDEND = 1;
	private static final int MAX_RANGE_DIVISOR = 3;
	private static final int MIN_RANGE_DIVISOR = 1;

	// instance variables
	private final Random randGenerator = new Random();

	/**
	 * implementing abstract method from SpaceShip class
	 * Does the actions of this ship for this round.
	 * This is called once per rounGameGUId by the SpaceWars game driver.
	 * @param game the game object to which this ship belongs.
	 */
	public void doAction(SpaceWars game)
	{
		updateInfoBeforeNewRound();

		SpaceShip closestShip = getClosestShip(game);
		int randomNum = getRandomNumber(MAX_RANGE_DIVIDEND, MIN_RANGE_DIVIDEND);
		int randomNum2 = getRandomNumber(MAX_RANGE_DIVISOR, MIN_RANGE_DIVISOR);
		if (dividedBy(randomNum, randomNum2))
		{
			flyToShip(closestShip);
		}
		else
		{
			flayAwayFromShip(closestShip);
		}

		double angle = getAngle(closestShip);
		double distance = getDistance(closestShip);
		if(angle <= ANGLE_TO_FIRE)
		{
			//Surprisingly he is a better shooter when he is drunk
			fire(game);
		}
		//He thinks objects who are far away are much closer than how they really are
		if(distance >= DISTANCE_TO_TELEPORT || angle >= ANGLE_TO_TELEPORT)
		{
			teleport();
		}

		//he accidentally spilled he's beer on the shield's controls so they don't work anymore :(
		updateInfoAfterRound();
	}

	private int getRandomNumber(int max, int min)
	{
		return randGenerator.nextInt((max - min) + 1) + min;
	}
}
