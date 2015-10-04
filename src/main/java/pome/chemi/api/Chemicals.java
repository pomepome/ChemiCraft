package pome.chemi.api;

public class Chemicals
{
	public String internal_name;
	public String display_name;
	public String formula;
	public EnumChemicalType type;
	public Chemicals(String displayName, String internalName, String chemicalFormula, EnumChemicalType chemicalType)
	{
		display_name = displayName;
		internal_name = internalName;
		formula = chemicalFormula;
		type = chemicalType;
	}
}
