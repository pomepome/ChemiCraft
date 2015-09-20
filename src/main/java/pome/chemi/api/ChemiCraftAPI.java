package pome.chemi.api;

public class ChemiCraftAPI
{
	public static IChemiCraft getAPI()
	{
		try
		{
			Class c = Class.forName("pome.chemi.ChemiCraft");
			return (IChemiCraft)c.getField("instance").get(null);
		}
		catch(Exception e)
		{
			return null;
		}
	}
}
