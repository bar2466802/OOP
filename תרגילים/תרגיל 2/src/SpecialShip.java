/**
 * This class represents a Special ship which is like a boss inside the Space Wars game
 * It extends the abstract class SpaceShip
 * @author Bar Melinarskiy
 * @version 17/8/20
 */
public class SpecialShip extends SpaceShip
{
	private static final double DISTANCE_TO_ACTIVATE_SHIELD = 0.16;
	private static final double DISTANCE_TO_TELEPORT = 0.12;
	private static final double ANGLE_TO_FIRE = 0.21;
	private static final int DIVISOR = 20;

	/**
	 * implementing abstract method from SpaceShip class
	 * Does the actions of this ship for this round.
	 * This is called once per rounGameGUId by the SpaceWars game driver.
	 * @param game the game object to which this ship belongs.
	 */
	public void doAction(SpaceWars game)
	{
		//This ship is much more strong than the other ships
		updateInfoBeforeNewRound();

		SpaceShip closestShip = getClosestShip(game);
		flyToShip(closestShip);
		double distance = getDistance(closestShip);
		//It is like the aggressive ship
		double angle = getAngle(closestShip);
		if(angle <= ANGLE_TO_FIRE)
		{
			fire(game);
		}
		//And is also protective like the runner ship
		if(dividedBy(getRound(), DIVISOR) || distance <= DISTANCE_TO_ACTIVATE_SHIELD)
		{
			shieldOn();
		}

		if(distance <= DISTANCE_TO_TELEPORT)
		{
			teleport();
		}

		updateInfoAfterRound();
	}
	/** override method from father class
	* This method is called whenever a ship starts a new round
	*/
	@Override
	public void updateInfoBeforeNewRound()
	{
		setRound(getRound() + 1);
		setRoundsSinceLastShoot(FIRING_COOLING_PERIOD);
		setShieldState(false);
		setImage();
	}

	/** override method from father class
	 * This method is called whenever a ship ends a round
	 */
	@Override
	public void updateInfoAfterRound()
	{
		final int EVERY_100_ROUNDS = 100;
		int newEnergy = getEnergy() + 1;
		if(dividedBy(getRound(), EVERY_100_ROUNDS))
		{
			newEnergy = getMaxEnergy();
		}

		setEnergy(newEnergy);
	}



}
