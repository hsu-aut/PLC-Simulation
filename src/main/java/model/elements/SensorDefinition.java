package model.elements;

/**
 * A Definition of all sensors similar to the global variable list in Codesys
 */
public enum SensorDefinition {
	B1_S01("ns=4;s=|var|CODESYS Control Win V3 x64.Application.Globale_Variablen.B1_S01", "Reedkontakt Registerlager"),
	B1_S02("ns=4;s=|var|CODESYS Control Win V3 x64.Application.Globale_Variablen.B1_S02", "Lichtschranke Registerlager"),
	B1_S03("ns=4;s=|var|CODESYS Control Win V3 x64.Application.Globale_Variablen.B1_S03", "Initiator F�rderband 1.1"),
	B1_S04("ns=4;s=|var|CODESYS Control Win V3 x64.Application.Globale_Variablen.B1_S04", "Reedkontakt Farberkennung links"),
	B1_S05("ns=4;s=|var|CODESYS Control Win V3 x64.Application.Globale_Variablen.B1_S05", "Reedkontakt Farberkennung rechts"),
	B1_S06("ns=4;s=|var|CODESYS Control Win V3 x64.Application.Globale_Variablen.B1_S06", "Taster Registerlager"),
	
	B1_S07("ns=4;s=|var|CODESYS Control Win V3 x64.Application.Globale_Variablen.B1_S07", "Initiator F�rderband 1.2"),
	B1_S08("ns=4;s=|var|CODESYS Control Win V3 x64.Application.Globale_Variablen.B1_S08", "Initiator F�rderband 1.3 vorn"),
	B1_S10("ns=4;s=|var|CODESYS Control Win V3 x64.Application.Globale_Variablen.B1_S10", "rechter Fl�gel offen"),
	B1_S11("ns=4;s=|var|CODESYS Control Win V3 x64.Application.Globale_Variablen.B1_S11", "rechter Fl�gel geschlossen"),
	B1_S12("ns=4;s=|var|CODESYS Control Win V3 x64.Application.Globale_Variablen.B1_S12", "linker Fl�gel offen"),
	B1_S13("ns=4;s=|var|CODESYS Control Win V3 x64.Application.Globale_Variablen.B1_S13", "linker Fl�gel geschlossen"),
	B1_S14("ns=4;s=|var|CODESYS Control Win V3 x64.Application.Globale_Variablen.B1_S14", "Quetschutz rechter Fl�gel"),
	B1_S15("ns=4;s=|var|CODESYS Control Win V3 x64.Application.Globale_Variablen.B1_S15", "Quetschutz linker Fl�gel"),
	B1_S16("ns=4;s=|var|CODESYS Control Win V3 x64.Application.Globale_Variablen.B1_S16", "Lichtschranke Einfahrt"),
	B1_S17("ns=4;s=|var|CODESYS Control Win V3 x64.Application.Globale_Variablen.B1_S17", "Lichtschranke Ausfahrt"),
	
	B1_S09("ns=4;s=|var|CODESYS Control Win V3 x64.Application.Globale_Variablen.B1_S09", "Initiator F�rderband 1.3 hinten"),
	B1_S18("ns=4;s=|var|CODESYS Control Win V3 x64.Application.Globale_Variablen.B1_S18", "Tor �ffnen Einfahrt (Zugseil)"),
	B1_S19("ns=4;s=|var|CODESYS Control Win V3 x64.Application.Globale_Variablen.B1_S19", "Tor �ffnen Ausfahrt (Zugseil)");

	String nodeIdString;
	String comment;
	
	SensorDefinition(String nodeIdString, String comment) {
		this.nodeIdString = nodeIdString;
		this.comment = comment;
	}
	
	public String getNodeIdString() {
		return this.nodeIdString;
	}
	
	public String getComment() {
		return this.comment;
	}
}
