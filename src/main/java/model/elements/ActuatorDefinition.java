package model.elements;

import java.util.List;

public enum ActuatorDefinition {
	
	B1_A01("ns=4;s=|var|CODESYS Control Win V3 x64.Application.Globale_Variablen.B1_A01", "Förderband 1.0"),
	B1_A02("ns=4;s=|var|CODESYS Control Win V3 x64.Application.Globale_Variablen.B1_A02", "Förderband 1.1"),
	B1_A03("ns=4;s=|var|CODESYS Control Win V3 x64.Application.Globale_Variablen.B1_A03", "LED"),
	B1_A04("ns=4;s=|var|CODESYS Control Win V3 x64.Application.Globale_Variablen.B1_A04", "Summer"),
	B1_A05("ns=4;s=|var|CODESYS Control Win V3 x64.Application.Globale_Variablen.B1_A05", "Farbcode Bit 0"),
	B1_A06("ns=4;s=|var|CODESYS Control Win V3 x64.Application.Globale_Variablen.B1_A06", "Farbcode Bit 1"),
	B1_A07("ns=4;s=|var|CODESYS Control Win V3 x64.Application.Globale_Variablen.B1_A07", "Förderband 1.2"),
	B1_A08("ns=4;s=|var|CODESYS Control Win V3 x64.Application.Globale_Variablen.B1_A08", "Förderband 1.3"),
	B1_A09("ns=4;s=|var|CODESYS Control Win V3 x64.Application.Globale_Variablen.B1_A09", "Ampel Einfahrt rot"),
	B1_A10("ns=4;s=|var|CODESYS Control Win V3 x64.Application.Globale_Variablen.B1_A10", "Ampel Ausfahrt rot"),
	B1_A11("ns=4;s=|var|CODESYS Control Win V3 x64.Application.Globale_Variablen.B1_A11", "Ampel Einfahrt grün"),
	B1_A12("ns=4;s=|var|CODESYS Control Win V3 x64.Application.Globale_Variablen.B1_A12", "Ampel Ausfahrt grün"),
	B1_A13("ns=4;s=|var|CODESYS Control Win V3 x64.Application.Globale_Variablen.B1_A13", "rechten Flügel schließen"),
	B1_A14("ns=4;s=|var|CODESYS Control Win V3 x64.Application.Globale_Variablen.B1_A14", "rechten Flügel öffnen"),
	B1_A15("ns=4;s=|var|CODESYS Control Win V3 x64.Application.Globale_Variablen.B1_A15", "linken Flügel schließen"),
	B1_A16("ns=4;s=|var|CODESYS Control Win V3 x64.Application.Globale_Variablen.B1_A16", "linken Flügel öffnen");
	
	String nodeIdString;
	String comment;
	
	private ActuatorDefinition(String nodeIdString, String comment) {
		this.nodeIdString = nodeIdString;
		this.comment = comment;
	}
	
	public String getNodeIdString() {
		return this.nodeIdString;
	}
	
	public String getComment() {
		return this.comment;
	}
	
	public static List<ActuatorDefinition> getDoors() {
		return List.of(B1_A13, B1_A14, B1_A15, B1_A16);
	}
	
	public static List<ActuatorDefinition> getConveyors() {
		return List.of(B1_A01, B1_A02, B1_A07, B1_A08);
	}
}
