/**
 * This class represents an Aggressive ship inside the Space Wars game
 * It extends the abstract class SpaceShip
 * @author Bar Melinarskiy
 * @version 17/8/20
 */
public class AggressiveShip extends SpaceShip
{
	//Constants
	private static final double ANGLE_TO_FIRE = 0.21;

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
		double angle = getAngle(closestShip);
		if(angle <= ANGLE_TO_FIRE)
		{
			fire(game);
		}
		
		updateInfoAfterRound();
	}
}
