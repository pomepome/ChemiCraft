package pome.chemi;

import java.util.ArrayList;
import java.util.List;

import pome.chemi.api.Chemicals;
import pome.chemi.api.EnumChemicalType;

public class ChemicalsRegistry
{
	private static List<Chemicals> chemicalsList = new ArrayList<Chemicals>();

	public static int registerChemical(String name, String formula, EnumChemicalType type)
	{
		chemicalsList.add(new Chemicals(name, formula, type));
		return chemicalsList.size() - 1;
	}

	public static int getRegisterdChemicalsCount()
	{
		return chemicalsList.size();
	}
	public static EnumChemicalType getType(int id)
	{
		if(id >= getRegisterdChemicalsCount())
		{
			return null;
		}
		return chemicalsList.get(id).type;
	}
	public static String getFormula(int id)
	{
		if(id >= getRegisterdChemicalsCount())
		{
			return "Unknown chemicals";
		}
		return chemicalsList.get(id).formula;
	}
	public static String getName(int id)
	{
		if(id >= getRegisterdChemicalsCount())
		{
			return "Unknown chemicals";
		}
		return chemicalsList.get(id).name;
	}
}
