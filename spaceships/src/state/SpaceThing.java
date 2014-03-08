package state;

import gameLogic.Constants.*;

public class SpaceThing {
	
	protected int uniqueId;
	protected PlayerNumber owner;
	protected SpaceThingType type;
	protected int x;
	protected int y;
	
	public SpaceThing(int id, SpaceThingType type, PlayerNumber owner) {
		this.uniqueId = id;
		this.type = type;
		this.owner = owner;
	}
	
	
	public int getUniqueId()
	{
		return uniqueId;
	}

	public SpaceThingType getType()
	{
		return type;
	}

	public void setOwner(PlayerNumber owner) {
		this.owner = owner;
	}
	
	public PlayerNumber getOwner() {
		return this.owner;
	}
	
	public int getX() {
		return this.x;
	}
	public int getY() {
		return this.y;
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public void setY(int y) {
		this.y = y;
	}


	@Override
	public String toString()
	{
		return "SpaceThing [uniqueId=" + uniqueId + ", owner=" + owner + ", type=" + type + ", x=" + x + ", y=" + y
				+ "]";
	}


	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((owner == null) ? 0 : owner.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		result = prime * result + uniqueId;
		result = prime * result + x;
		result = prime * result + y;
		return result;
	}


	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SpaceThing other = (SpaceThing) obj;
		if (owner != other.owner)
			return false;
		if (type != other.type)
			return false;
		if (uniqueId != other.uniqueId)
			return false;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		return true;
	}
	
	

	
	
	

}
