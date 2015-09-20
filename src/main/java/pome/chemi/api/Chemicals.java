package pome.chemi.api;

public class Chemicals
{
	public String name;
	public String formula;
	public EnumChemicalType type;
	public Chemicals(String chemicalName, String chemicalFormula, EnumChemicalType chemicalType)
	{
		name = chemicalName;
		formula = chemicalFormula;
		type = chemicalType;
	}
}
