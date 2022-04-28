SpaceShipFactoryimport java.awt.Image;
import oop.ex2.*;

/**
 * The API spaceships need to implement for the SpaceWars game. 
 * SpaceShip.java is an abstract class,
 *  a base class for the other spaceships or any other option you will choose.
 *  
 * @author oop & Bar Melinarskiy
 */
public abstract class SpaceShip{
    //Constants
    protected static final int DEAD_HEALTH_LEVEL = 0;
    protected static final int INITIAL_ROUND = 0;
    protected static final int INITIAL_HEALTH_LEVEL = 22;
    protected static final int INITIAL_ENERGY_LEVEL = 190;
    protected static final int INITIAL_MAX_ENERGY_LEVEL = 210;
    protected static final int MIN_ENERGY_LEVEL = 0;
    protected static final int ADDITION_TO_ENERGY_BASH = 18;
    protected static final int ADDITION_TO_ENERGY_END_ROUND = 1;
    protected static final int REDUCE_FROM_ENERGY_SHOT = 10;
    protected static final int REDUCE_FROM_HEALTH_SHOT = 1;
    protected static final int REDUCE_FROM_HEALTH_BASH = 1;
    protected static final int FIRING_COST = 19;
    protected static final int TELEPORTING_COST = 140;
    protected static final int SHIELDS_COST = 3;
    protected static final int FIRING_COOLING_PERIOD = 7;

    protected static final int SAME_LOCATION = 0;
    protected static final int NO_TURN = 0;
    protected static final int RIGHT_TURN = -1;
    protected static final int LEFT_TURN = 1;

    // instance variables
    /** The ships current image. */
    Image image;

    /** The current physical state of this ship. */
    SpaceShipPhysics shipPhysics;

    /** The current round level of this ship. */
    private int round = INITIAL_ROUND;

    /** The current health strength of this ship. */
    private int healthLevel = INITIAL_HEALTH_LEVEL;

    /** The current energy power of this ship. */
    private int energyLevel = INITIAL_ENERGY_LEVEL;

    /** The current maximum energy power of this ship. */
    private int maxEnergyLevel = INITIAL_MAX_ENERGY_LEVEL;

    /** Flag to indicate whether the ship's shield is up/down. */
    private boolean isShieldUp = false;

    /** Flag to indicate whether the ship's shield is up/down. */
    private int roundsSinceLastShoot = FIRING_COOLING_PERIOD;

    // Contractor
    /**
     * Default contractor to initialize each spaceship
     */
    public SpaceShip()
    {
        reset();
    }

    // instance methods
   
    /**
     * Does the actions of this ship for this round. 
     * This is called once per rounGameGUId by the SpaceWars game driver.
     * This method is implemented according to the relevant type of the spaceship
     * @param game the game object to which this ship belongs.
     */
    public abstract void doAction(SpaceWars game);

    /**
     * This method is called every time a collision with this ship occurs 
     */
    public void collidedWithAnotherShip()
    {
        /**
         * first lets check whether the ship's shield are up
         * I know you can write without the == true, but I think it clearer like that
         */
        if(getShieldState() == true)
        {
            //The shields are up so increase energy parameters
            setEnergy(getEnergy() + ADDITION_TO_ENERGY_BASH);
            setMaxEnergy(getMaxEnergy() + ADDITION_TO_ENERGY_BASH);
        }
        else
        {
            //The shields are down so decrease ship's health
            setHealth(getHealth() - REDUCE_FROM_HEALTH_BASH);
        }
    }

    /** 
     * This method is called whenever a ship has died. It resets the ship's 
     * attributes, and starts it at a new random position.
     */
    public void reset()
    {
        setImage();
        initializePhysics();
        setRound(INITIAL_ROUND);
        setRoundsSinceLastShoot(FIRING_COOLING_PERIOD);
        setHealth(INITIAL_HEALTH_LEVEL);
        setEnergy(INITIAL_ENERGY_LEVEL);
        setMaxEnergy(INITIAL_MAX_ENERGY_LEVEL);
        setShieldState(false);
    }

    /**
     * This method is called whenever a ship starts a new round
     */
    public void updateInfoBeforeNewRound()
    {
        setRound(getRound() + 1);
        setRoundsSinceLastShoot(getRoundsSinceLastShoot() + 1);
        setShieldState(false);
        setImage();
    }

    /**
     * This method is called whenever a ship ends a round
     */
    public void updateInfoAfterRound()
    {
        setEnergy(getEnergy() + ADDITION_TO_ENERGY_END_ROUND);
    }

    /**
     * Checks if this ship is dead.
     * 
     * @return true if the ship is dead. false otherwise.
     */
    public boolean isDead()
    {
        return getHealth() <= DEAD_HEALTH_LEVEL;
    }

    /**
     * This method is called by the SpaceWars game object when ever this ship
     * gets hit by a shot.
     */
    public void gotHit()
    {
        if(getShieldState() == false)
        {
            setHealth(getHealth() - REDUCE_FROM_HEALTH_SHOT);
            setMaxEnergy(getMaxEnergy() - REDUCE_FROM_ENERGY_SHOT);
        }
    }

    /**
     * Attempts to fire a shot.
     *
     * @param game the game object.
     */
    public void fire(SpaceWars game)
    {
        //We need to check we have enough energy and that enough time has passed since we last fired
        if(getEnergy() >= FIRING_COST && getRoundsSinceLastShoot() >= FIRING_COOLING_PERIOD)
        {
            game.addShot(getPhysics()); //add the shoot to game GUI
            setEnergy(getEnergy() - FIRING_COST); //update the energy level
            setRoundsSinceLastShoot(INITIAL_ROUND); //update rounds since last firing
        }
    }

    /**
     * Attempts to turn on the shield.
     */
    public void shieldOn()
    {
        //We need to check we have enough energy and that we didn't already activated the shield
        if(getEnergy() >= SHIELDS_COST && getShieldState() == false)
        {
            setEnergy(getEnergy() - SHIELDS_COST); //update the energy level
            setShieldState(true);
            setImage(); //update the ship's image
        }
    }

    /**
     * Attempts to teleport.
     */
    public void teleport()
    {
        //We need to check we have enough energy
        if(getEnergy() >= TELEPORTING_COST)
        {
            setEnergy(getEnergy() - TELEPORTING_COST); //update the energy level
            initializePhysics(); //update ship's location
        }
    }
    /**
     * Check if dividend % divisor == 0
     * @param dividend number to check, a number to be divided by another number.
     * @param divisor a number by which another number is to be divided.
     * @return true if the divisor divides into dividend without a remainder, false otherwise.
     */
    protected boolean dividedBy(int dividend , int divisor)
    {
        return dividend % divisor == 0;
    }


    // getters methods

    /**
     * Gets the physics object that controls this ship.
     * 
     * @return the physics object that controls the ship.
     */
    public SpaceShipPhysics getPhysics() {
        return shipPhysics;
    }

    /**
     * Gets the current ship's health strength.
     *
     * @return the current ship's health strength.
     */
    public int getHealth()
    {
        return healthLevel;
    }

    /**
     * Gets the current ship's energy power.
     *
     * @return the current ship's energy power.
     */
    public int getEnergy()
    {
        return energyLevel;
    }

    /**
     * Gets the current ship's max energy power.
     *
     * @return the current ship's max energy power.
     */
    public int getMaxEnergy()
    {
        return maxEnergyLevel;
    }

    /**
     * Gets the current ship's round number.
     *
     * @return the current ship's round number.
     */
    public int getRound()
    {
        return round;
    }

    /**
     * Gets the current rounds numbers since the ship fired it's last shoot.
     *
     * @return the current rounds numbers since the ship fired it's last shoot.
     */
    public int getRoundsSinceLastShoot()
    {
        return roundsSinceLastShoot;
    }

    /**
     * Gets the current state of the ship's shield.
     *
     * @return the current state of the ship's shield - true if the shields are up,
     * false otherwise.
     */
    public boolean getShieldState()
    {
        return isShieldUp;
    }

    /**
     * Gets the image of this ship. This method should return the image of the
     * ship with or without the shield. This will be displayed on the GUI at
     * the end of the round.
     * 
     * @return the image of this ship.
     */
    public Image getImage()
    {
        return image;
    }

    /**
     * Gets the ship closest to our ship
     * @param game the game object.
     * @return ship closest to our ship.
     */
    protected SpaceShip getClosestShip(SpaceWars game)
    {
        return game.getClosestShipTo(this);
    }

    /**
     * Gets the distance from given ship
     * @param ship the ship to get distance from.
     * @return the distance from given ship.
     */
    protected double getDistance(SpaceShip ship)
    {
        return getPhysics().distanceFrom(ship.getPhysics());
    }

    /**
     * Gets the angle to given ship
     * @param ship the ship to get angle to.
     * @return the angle to given ship.
     */
    protected double getAngle(SpaceShip ship)
    {
        return getPhysics().angleTo(ship.getPhysics());
    }

    // setters methods
    /**
     * Sets the physics object that controls this ship.
     *
     */
    public void initializePhysics() {
        shipPhysics = new SpaceShipPhysics();
    }

    /**
     * Sets the current ship's health strength.
     * @param newHealth the new value to set
     */
    public void setHealth(int newHealth)
    {
        if(newHealth < DEAD_HEALTH_LEVEL)
        {
            newHealth = DEAD_HEALTH_LEVEL;
        }
        healthLevel = newHealth;
    }

    /**
     * Sets the current ship's energy power.
     * @param newEnergy the new value to set
     */
    public void setEnergy(int newEnergy)
    {
        if(newEnergy < MIN_ENERGY_LEVEL)
        {
            newEnergy = MIN_ENERGY_LEVEL;
        }
        else if(newEnergy > getMaxEnergy())
        {
            newEnergy = getMaxEnergy();
        }
        energyLevel = newEnergy;
    }

    /**
     * Sets the current ship's max energy power.
     * @param newMaxEnergy the new value to set
     */
    public void setMaxEnergy(int newMaxEnergy)
    {
        if(newMaxEnergy < MIN_ENERGY_LEVEL)
        {
            newMaxEnergy = MIN_ENERGY_LEVEL;
        }
        maxEnergyLevel = newMaxEnergy;
        //Check if we need to adjust the current energy after max energy change
        if(getEnergy() > maxEnergyLevel)
        {
            setEnergy(maxEnergyLevel);
        }
    }

    /**
     * Sets the current ship's round number.
     * @param roundNumber the new value to set
     */
    public void setRound(int roundNumber)
    {
        round = roundNumber;
    }

    /**
     * Sets the current rounds numbers since the ship fired it's last shoot.
     * @param numberOfRounds the new value to set
     */
    public void setRoundsSinceLastShoot(int numberOfRounds)
    {
        roundsSinceLastShoot = numberOfRounds;
    }

    /**
     * Sets the current state of the ship's shield.
     * @param state the new value to set
     */
    public void setShieldState(boolean state)
    {
        isShieldUp = state;
    }

    /**
     * Sets the image of this ship.
     */
    public void setImage()
    {
         //first lets check whether the ship's shield are up
        if(getShieldState() == true)
        {
           image = GameGUI.ENEMY_SPACESHIP_IMAGE_SHIELD;
        }
        else
        {
            image = GameGUI.ENEMY_SPACESHIP_IMAGE;
        }
    }

    /**
     * Move ship in the direction of the given ship
     * @param  ship the ship to fly to
     */
    public void flyToShip(SpaceShip ship)
    {
        double angleToShip = getAngle(ship);
        if(angleToShip > SAME_LOCATION)
        {
            getPhysics().move(true, LEFT_TURN);
        }
        else if(angleToShip < SAME_LOCATION)
        {
            getPhysics().move(true, RIGHT_TURN);
        }
        else
        {
            getPhysics().move(true, NO_TURN);
        }
    }

    /**
     * Move ship in the direction away from the given ship
     * @param  ship the ship to run away
     */
    public void flayAwayFromShip(SpaceShip ship)
    {
        double angleToShip = getAngle(ship);
        if(angleToShip > SAME_LOCATION)
        {
            getPhysics().move(true, RIGHT_TURN);
        }
        else
        {
            getPhysics().move(true, LEFT_TURN);
        }
    }
}
