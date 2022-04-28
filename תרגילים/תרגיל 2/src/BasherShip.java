/**
 * This class represents a Basher ship inside the Space Wars game
 * It extends the abstract class SpaceShip
 * @author Bar Melinarskiy
 * @version 17/8/20
 */
public class BasherShip extends SpaceShip
{
	private final double DISTANCE_TO_ACTIVATE_SHIELD = 0.19;

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
		flyToShip(closestShip);
		double distance = getDistance(closestShip);
		if(distance <= DISTANCE_TO_ACTIVATE_SHIELD)
		{
			shieldOn();
		}

		updateInfoAfterRound();
	}

}
