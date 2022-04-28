import oop.ex2.GameGUI;

/**
 * This class represents a user controlled ship inside the Space Wars game
 * It extends the abstract class SpaceShip
 * @author Bar Melinarskiy
 * @version 17/8/20
 */
public class HumanShip extends SpaceShip
{
	/**
	 * implementing abstract method from SpaceShip class
	 * Does the actions of this ship for this round.
	 * Check what action the user wants to do according to the following order
	 * 1. Teleport
	 * 2. Accelerate and turn (happen at the same time)
	 * 3. Shield activation
	 * 4. Firing a shot
	 * 5. Regeneration of the 1 unit of energy of this round
	 * This is called once per rounGameGUId by the SpaceWars game driver.
	 * @param game the game object to which this ship belongs.
	 */
	public void doAction(SpaceWars game)
	{
		updateInfoBeforeNewRound();

		//1. Teleport
		if(game.getGUI().isTeleportPressed() == true)
		{
			teleport();
		}
		//2. Accelerate and turn (happen at the same time)
		boolean wishToAccelerate = game.getGUI().isUpPressed();
		int directionToMove = NO_TURN;
		if(game.getGUI().isLeftPressed() == true)
		{
			directionToMove = LEFT_TURN;
		}
		else if(game.getGUI().isRightPressed() == true)
		{
			directionToMove = RIGHT_TURN;
		}
		getPhysics().move(wishToAccelerate, directionToMove);
		//3. Shield activation
		if(game.getGUI().isShieldsPressed() == true)
		{
			shieldOn();
		}
		//4. Firing a shot
		if(game.getGUI().isShotPressed() == true)
		{
			fire(game);
		}
		//5. Regeneration of the 1 unit of energy of this round
		updateInfoAfterRound();
	}

	/** overriding this method from the father class
	 * Sets the image of this ship.
	 */
	@Override
	public void setImage()
	{
		//first lets check whether the ship's shield are up
		if(getShieldState() == true)
		{
			image = GameGUI.SPACESHIP_IMAGE_SHIELD;
		}
		else
		{
			image = GameGUI.SPACESHIP_IMAGE;
		}
	}

}
