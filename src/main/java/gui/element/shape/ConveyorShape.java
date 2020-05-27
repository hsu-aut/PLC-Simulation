package gui.element.shape;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polyline;
import javafx.scene.shape.Rectangle;

public class ConveyorShape{
	
	private double workPieceWidth = 20;
	private final int workPieceHeight = 30;
	private final int wpMargin = 8;
	private final int arrowWidth = 14; 
	private double numberOfWorkpieceRectangles;
	
	private List<Rectangle> wpRectangles = new ArrayList<>();
	private List<Polyline> leftArrows = new ArrayList<>();
	private List<Polyline> rightArrows = new ArrayList<>();
	private int activeWpRectangle = 1;
	
	
	public ConveyorShape(Pane pane, double posX, double posY, int length, boolean horizontal) {
		numberOfWorkpieceRectangles = (length - this.wpMargin) / (this.workPieceWidth + this.wpMargin);
		// correct width in case a width of 20 doesn't properly fit
		this.workPieceWidth = (length - this.wpMargin) / Math.ceil(numberOfWorkpieceRectangles) - this.wpMargin;
		if(horizontal) {
			drawHorizontal(pane, posX, posY, length);
		}else {
			drawVertical(pane, posX, posY, length);
		}
	}
	
	private void drawHorizontal(Pane pane, double posX, double posY, int length) {
		Rectangle baseRectangle = new Rectangle(posX, posY, length, 50);
		baseRectangle.setFill(ShapeHelper.INACTIVE);
		baseRectangle.setStroke(Color.BLACK);
		pane.getChildren().add(baseRectangle);
		
		Rectangle lowerRectangle = new Rectangle(posX, posY+50, length, 20);
		lowerRectangle.setFill(ShapeHelper.INACTIVE);
		lowerRectangle.setStroke(Color.BLACK);
		pane.getChildren().add(lowerRectangle);
		
		for (int i = 0; i < this.numberOfWorkpieceRectangles; i++) {
			double wpX = posX + (i+1) * wpMargin + (i * workPieceWidth);
			double wpY = posY + wpMargin;
			Rectangle wpRectangle = new Rectangle(wpX, wpY, workPieceWidth, workPieceHeight);
			wpRectangle.setStroke(Color.BLACK);
			this.wpRectangles.add(wpRectangle);
		}
		pane.getChildren().addAll(wpRectangles);
		resetPosition();
		
		
		for (int i = 0; i < (this.numberOfWorkpieceRectangles / 2); i++) {
			Polyline leftArrow = new Polyline();
			double xLeft = (posX + wpMargin) + i * (arrowWidth + wpMargin);
			double xRight = (posX + wpMargin + arrowWidth) + i * (arrowWidth + wpMargin);
			leftArrow.getPoints().addAll(new Double[]{xLeft, posY+60, xRight, posY+55, xRight, posY+65, xLeft, posY+60});
			this.leftArrows.add(leftArrow);
			pane.getChildren().add(leftArrow);
		}
		
		for (int i = 0; i < (this.numberOfWorkpieceRectangles / 2); i++) {
			Polyline rightArrow = new Polyline();
			double xLeft = (posX + length - wpMargin - arrowWidth) - i * (arrowWidth + wpMargin);
			double xRight = (posX + length - wpMargin) - i * (arrowWidth + wpMargin);
			rightArrow.getPoints().addAll(new Double[]{xRight, posY+60, xLeft, posY+55, xLeft, posY+65, xRight, posY+60});
			this.rightArrows.add(rightArrow);
			pane.getChildren().add(rightArrow);
		}
		
	}
	
	
	private void drawVertical(Pane pane, double posX, double posY, int length) {
		Rectangle baseRectangle = new Rectangle(posX, posY, 50, length);
		baseRectangle.setFill(ShapeHelper.INACTIVE);
		baseRectangle.setStroke(Color.BLACK);
		pane.getChildren().add(baseRectangle);
		
		Rectangle lowerRectangle = new Rectangle(posX-20, posY, 20, length);
		lowerRectangle.setFill(ShapeHelper.INACTIVE);
		lowerRectangle.setStroke(Color.BLACK);
		pane.getChildren().add(lowerRectangle);
		
		for (int i = 0; i < this.numberOfWorkpieceRectangles; i++) {
			double wpX = posX + wpMargin;
			double wpY = posY + (i+1) * wpMargin + (i * workPieceWidth);
			Rectangle wpRectangle = new Rectangle(wpX, wpY, workPieceHeight, workPieceWidth);
			wpRectangle.setStroke(Color.BLACK);
			this.wpRectangles.add(wpRectangle);
		}
		
		pane.getChildren().addAll(wpRectangles);
		resetPosition();
		
		
		for (int i = 0; i < (this.numberOfWorkpieceRectangles / 2); i++) {
			Polyline leftArrow = new Polyline();
			double yTop = (posY + wpMargin) + i * (arrowWidth + wpMargin);
			double yBottom = (posY + wpMargin + arrowWidth) + i * (arrowWidth + wpMargin);
			leftArrow.getPoints().addAll(new Double[]{posX - 10, yTop, posX -15, yBottom, posX - 5, yBottom, posX - 10, yTop});
			this.leftArrows.add(leftArrow);
			pane.getChildren().add(leftArrow);
		}
		
		for (int i = 0; i < (this.numberOfWorkpieceRectangles / 2); i++) {
			Polyline leftArrow = new Polyline();
			double yTop = (posY + length - wpMargin - arrowWidth) - i * (arrowWidth + wpMargin);
			double yBottom = (posY + length - wpMargin) - i * (arrowWidth + wpMargin);
			leftArrow.getPoints().addAll(new Double[]{posX - 10, yBottom, posX -15, yTop, posX - 5, yTop, posX - 10, yBottom});
			this.leftArrows.add(leftArrow);
			pane.getChildren().add(leftArrow);
		}
		
		
	}
	
	public void activateLeft() {
		for (Polyline arrow : leftArrows) {
			arrow.setFill(ShapeHelper.ACTIVE);
		}
	}
	
	public void deactivateLeft() {
		for (Polyline arrow : leftArrows) {
			arrow.setFill(ShapeHelper.INACTIVE);
		}
	}
	
	public void activateRight() {
		for (Polyline arrow : rightArrows) {
			arrow.setFill(ShapeHelper.ACTIVE);
		}
	}
	
	public void deactivateRight() {
		for (Polyline arrow : rightArrows) {
			arrow.setFill(ShapeHelper.INACTIVE);
		}
	}
	
	public void resetPosition() {
		for (Rectangle rect : this.wpRectangles) {
			rect.setFill(ShapeHelper.INACTIVE);
		}
	}
	
	public void addWorkpiece(boolean onRight) {
		if(onRight) {
			this.activeWpRectangle = this.wpRectangles.size() -1;
		} else {
			this.activeWpRectangle = 0;
		}
		this.wpRectangles.get(activeWpRectangle).setFill(ShapeHelper.WORKPIECE_COLOR);
	}
	
	public void moveLeft(float relativePosition) {
		float relWpSize = 100f / this.wpRectangles.size();
		if (relativePosition < this.activeWpRectangle * relWpSize) {
			this.wpRectangles.get(activeWpRectangle).setFill(ShapeHelper.INACTIVE);
			Math.max(0, this.activeWpRectangle--);
			this.wpRectangles.get(activeWpRectangle).setFill(ShapeHelper.WORKPIECE_COLOR);
		}
	}
	
	public void moveRight(float relativePosition) {
		resetPosition();
		float relWpSize = 100f / this.wpRectangles.size();
		if (relativePosition > this.activeWpRectangle * relWpSize) {
			this.wpRectangles.get(activeWpRectangle).setFill(ShapeHelper.INACTIVE);
			Math.min(this.wpRectangles.size(), this.activeWpRectangle++);
			this.wpRectangles.get(activeWpRectangle).setFill(ShapeHelper.WORKPIECE_COLOR);
		}
	}

	
}