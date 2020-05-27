package model.elements;

import java.util.EnumSet;

import gui.element.Direction;

/**
 * A Definition of all sensors similar to the global variable list in Codesys
 */
public enum SensorDefinition {
//	B1_S01("ns=4;s=|var|CODESYS Control Win V3 x64.Application.Globale_Variablen.B1_S01", "B1_S01", "Reedkontakt Registerlager", 0, 0, Direction.North),
	B1_S02("ns=4;s=|var|CODESYS Control Win V3 x64.Application.Globale_Variablen.B1_S02", "B1_S02", "Lichtschranke Registerlager", 1040, 310, Direction.North),
	B1_S03("ns=4;s=|var|CODESYS Control Win V3 x64.Application.Globale_Variablen.B1_S03", "B1_S03", "Initiator Förderband 1.1", 840, 230, Direction.South),
	B1_S04("ns=4;s=|var|CODESYS Control Win V3 x64.Application.Globale_Variablen.B1_S04", "B1_S04", "Reedkontakt Farberkennung links", 940, 310 , Direction.North),
	B1_S05("ns=4;s=|var|CODESYS Control Win V3 x64.Application.Globale_Variablen.B1_S05", "B1_S05", "Reedkontakt Farberkennung rechts", 940, 230, Direction.South),
	B1_S06("ns=4;s=|var|CODESYS Control Win V3 x64.Application.Globale_Variablen.B1_S06", "B1_S06", "Taster Registerlager", 1010, 230, Direction.North),
	
	B1_S07("ns=4;s=|var|CODESYS Control Win V3 x64.Application.Globale_Variablen.B1_S07", "B1_S07", "Initiator Förderband 1.2", 680, 310, Direction.North),
	B1_S08("ns=4;s=|var|CODESYS Control Win V3 x64.Application.Globale_Variablen.B1_S08", "B1_S08", "Initiator Förderband 1.3 vorn", 570, 310, Direction.North),
	B1_S10("ns=4;s=|var|CODESYS Control Win V3 x64.Application.Globale_Variablen.B1_S10", "B1_S10", "rechter Flügel offen", 0, 0, Direction.North),
	B1_S11("ns=4;s=|var|CODESYS Control Win V3 x64.Application.Globale_Variablen.B1_S11", "B1_S11", "rechter Flügel geschlossen", 0, 0, Direction.North),
	B1_S12("ns=4;s=|var|CODESYS Control Win V3 x64.Application.Globale_Variablen.B1_S12", "B1_S12", "linker Flügel offen", 0, 0, Direction.North),
	B1_S13("ns=4;s=|var|CODESYS Control Win V3 x64.Application.Globale_Variablen.B1_S13", "B1_S13", "linker Flügel geschlossen", 0, 0, Direction.North),
//	B1_S14("ns=4;s=|var|CODESYS Control Win V3 x64.Application.Globale_Variablen.B1_S14", "B1_S14", "Quetschutz rechter Flügel", 0, 0, Direction.North),
//	B1_S15("ns=4;s=|var|CODESYS Control Win V3 x64.Application.Globale_Variablen.B1_S15", "B1_S15", "Quetschutz linker Flügel", 0, 0, Direction.North),
	B1_S16("ns=4;s=|var|CODESYS Control Win V3 x64.Application.Globale_Variablen.B1_S16", "B1_S16", "Lichtschranke Einfahrt", 480, 230, Direction.South),
	B1_S17("ns=4;s=|var|CODESYS Control Win V3 x64.Application.Globale_Variablen.B1_S17", "B1_S17", "Lichtschranke Ausfahrt", 410, 230, Direction.South),
	
	B1_S09("ns=4;s=|var|CODESYS Control Win V3 x64.Application.Globale_Variablen.B1_S09", "B1_S09", "Initiator Förderband 1.3 hinten", 360, 310, Direction.North),
//	B1_S18("ns=4;s=|var|CODESYS Control Win V3 x64.Application.Globale_Variablen.B1_S18", "B1_S18", "Tor öffnen Einfahrt (Zugseil)", 0, 0, Direction.North),
//	B1_S19("ns=4;s=|var|CODESYS Control Win V3 x64.Application.Globale_Variablen.B1_S19", "B1_S19", "Tor öffnen Ausfahrt (Zugseil)", 0, 0, Direction.North),
	
	B1_S20("ns=4;s=|var|CODESYS Control Win V3 x64.Application.Globale_Variablen.B1_S20", "B1_S20", "Initiator Drehtisch", 0, 0, Direction.North),
	B1_S21("ns=4;s=|var|CODESYS Control Win V3 x64.Application.Globale_Variablen.B1_S21", "B1_S21", "Drehtisch Anschlag vertikal", 310, 190, Direction.East),
	B1_S22("ns=4;s=|var|CODESYS Control Win V3 x64.Application.Globale_Variablen.B1_S22", "B1_S22", "Drehtisch Anschlag horizontal", 190, 310, Direction.North),
	B1_S23("ns=4;s=|var|CODESYS Control Win V3 x64.Application.Globale_Variablen.B1_S23", "B1_S23", "Initiator Förderband ganz links", 80, 230, Direction.South),
	B1_S24("ns=4;s=|var|CODESYS Control Win V3 x64.Application.Globale_Variablen.B1_S24", "B1_S24", "Initiator Förderband oben", 300, 100, Direction.East);
	

	String nodeIdString;
	String tagName;
	String comment;
	int x, y;
	Direction direction;
	
	SensorDefinition(String nodeIdString, String tagName, String comment, int x, int y, Direction dir) {
		this.nodeIdString = nodeIdString;
		this.tagName = tagName;
		this.comment = comment;
		this.x = x;
		this.y = y;
		this.direction = dir;
		
	}
	
	public String getNodeIdString() {
		return this.nodeIdString;
	}
	
	public String getTagName() {
		return this.tagName;
	}
	
	public String getComment() {
		return this.comment;
	}
	
	public double getX() {
		return (double) this.x;
	}
	
	public double getY() {
		return (double) this.y;
	}
	
	public Direction getDirection() {
		return this.direction;
	}
	
	public static EnumSet<SensorDefinition> getConveyorSensors() {
		return EnumSet.of(B1_S02, B1_S03, B1_S04, B1_S05, B1_S07, B1_S08, B1_S09, B1_S16, B1_S17, B1_S23, B1_S24);
	}
}
