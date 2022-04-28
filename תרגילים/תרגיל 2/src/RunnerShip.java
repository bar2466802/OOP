/**
 * This class represents a Runner ship inside the Space Wars game
 * He was hopping this would be just a normal field trip
 * It extends the abstract class SpaceShip
 * @author Bar Melinarskiy
 * @version 17/8/20
 */
public class RunnerShip extends SpaceShip
{
	private static double DISTANCE_TO_TELEPORT = 0.25;
	private static final double ANGLE_TO_TELEPORT = 0.23;

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
		double distance = getDistance(closestShip);
		double angle = getAngle(closestShip);
		if(distance <= DISTANCE_TO_TELEPORT || angle <= ANGLE_TO_TELEPORT)
		{
			teleport();
		}

		closestShip = getClosestShip(game);
		flayAwayFromShip(closestShip);

		updateInfoAfterRound();
	}

}
