package model.simulation;

/**
 * Certain states that a workpiece can generally be in
 */
public enum WorkpieceState {
	AtStorage, OnConveyor1, OnConveyor2, OnConveyor3, BeginConveyor4, FrontOfGate, BehindGate, EndConveyor4;
}
