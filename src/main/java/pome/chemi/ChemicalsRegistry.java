package pome.chemi;

import java.util.ArrayList;
import java.util.List;

import pome.chemi.api.Chemicals;
import pome.chemi.api.EnumChemicalType;

public class ChemicalsRegistry
{
	private static List<Chemicals> chemicalsList = new ArrayList<Chemicals>();

	public static int registerChemical(String display_name, String internal_name, String formula, EnumChemicalType type)
	{
		chemicalsList.add(new Chemicals(display_name, internal_name, formula, type));
		return chemicalsList.size() - 1;
	}

	public static int getRegisterdChemicalsCount()
	{
		return chemicalsList.size();
	}
	public static EnumChemicalType getType(int id)
	{
		if(id >= getRegisterdChemicalsCount() || id < 0)
		{
			return null;
		}
		return chemicalsList.get(id).type;
	}
	public static String getFormula(int id)
	{
		if(id >= getRegisterdChemicalsCount() || id < 0)
		{
			return "Unknown chemicals";
		}
		return chemicalsList.get(id).formula;
	}
	public static String getName(int id)
	{
		if(id >= getRegisterdChemicalsCount() || id < 0)
		{
			return "Unknown chemicals";
		}
		return chemicalsList.get(id).display_name;
	}
	public static int getChemicalId(String internalName)
	{
		int id = -1;
		for(int i = 0;i < chemicalsList.size();i++)
		{
			Chemicals ch = chemicalsList.get(i);
			if(ch != null && ch.internal_name == internalName)
			{
				id = i;
			}
		}
		return id;
	}
}
