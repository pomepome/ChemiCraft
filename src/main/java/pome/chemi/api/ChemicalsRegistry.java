package pome.chemi.api;

import java.util.ArrayList;
import java.util.List;

public class ChemicalsRegistry
{
	private static List<String> nameMap = new ArrayList<String>();
	private static List<EnumChemicalType> typeMap = new ArrayList<EnumChemicalType>();

	public static int registerChemical(String name,EnumChemicalType type)
	{
		nameMap.add(name);
		typeMap.add(type);
		return nameMap.size() - 1;
	}

	public static int getRegisterdChemicalsCount()
	{
		return nameMap.size();
	}

	public static EnumChemicalType getType(int id)
	{
		if(id >= getRegisterdChemicalsCount())
		{
			return null;
		}
		return typeMap.get(id);
	}

	public static String getName(int id)
	{
		if(id >= getRegisterdChemicalsCount())
		{
			return null;
		}
		return nameMap.get(id);
	}
}
