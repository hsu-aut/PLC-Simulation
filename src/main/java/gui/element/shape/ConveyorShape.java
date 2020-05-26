package gui.element.shape;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polyline;
import javafx.scene.shape.Rectangle;

public class ConveyorShape{
	
	private Rectangle rec1, rec2, rec3, rec4, rec5;
	private Polyline left1, left2, left3, left4, right1, right2, right3, right4;
	
	private int length;
	
	public ConveyorShape(Pane pane, double posX, double posY, boolean horizontal) {
		if(horizontal) {
			drawHorizontal(pane, posX, posY);
		}else {
			drawVertical(pane, posX, posY);
		}
	}
	
	private void drawHorizontal(Pane pane, double posX, double posY) {
		Rectangle rectangle = new Rectangle(posX, posY, 160, 50);
		rectangle.setFill(ShapeHelper.INACTIVE);
		rectangle.setStroke(Color.BLACK);
		pane.getChildren().add(rectangle);
		rectangle = new Rectangle(posX, posY+50, 160, 20);
		rectangle.setFill(ShapeHelper.INACTIVE);
		rectangle.setStroke(Color.BLACK);
		pane.getChildren().add(rectangle);
		
		rec1 = new Rectangle(posX+130, posY+10, 20, 30);
		rec1.setStroke(Color.BLACK);
		
		rec2 = new Rectangle(posX+100, posY+10, 20, 30);
		rec2.setStroke(Color.BLACK);
		
		rec3 = new Rectangle(posX+70, posY+10, 20, 30);
		rec3.setStroke(Color.BLACK);
		
		rec4 = new Rectangle(posX+40, posY+10, 20, 30);
		rec4.setStroke(Color.BLACK);
		
		rec5 = new Rectangle(posX+10, posY+10, 20, 30);
		rec5.setStroke(Color.BLACK);
		pane.getChildren().addAll(rec1, rec2, rec3, rec4, rec5);
		resetPosition();
		
		left1 = new Polyline();
		left1.getPoints().addAll(new Double[]{posX+15, posY+55, posX+5, posY+60, posX+15, posY+65, posX+15, posY+55});
		left2 = new Polyline();
		left2.getPoints().addAll(new Double[]{posX+35, posY+55, posX+25, posY+60, posX+35, posY+65, posX+35, posY+55});
		left3 = new Polyline();
		left3.getPoints().addAll(new Double[]{posX+55, posY+55, posX+45, posY+60, posX+55, posY+65, posX+55, posY+55});
		left4 = new Polyline();
		left4.getPoints().addAll(new Double[]{posX+75, posY+55, posX+65, posY+60, posX+75, posY+65, posX+75, posY+55});
		
		right1 = new Polyline();
		right1.getPoints().addAll(new Double[]{posX+85, posY+55, posX+95, posY+60, posX+85, posY+65, posX+85, posY+55});
		right2 = new Polyline();
		right2.getPoints().addAll(new Double[]{posX+105, posY+55, posX+115, posY+60, posX+105, posY+65, posX+105, posY+55});
		right3 = new Polyline();
		right3.getPoints().addAll(new Double[]{posX+125, posY+55, posX+135, posY+60, posX+125, posY+65, posX+125, posY+55});
		right4 = new Polyline();
		right4.getPoints().addAll(new Double[]{posX+145, posY+55, posX+155, posY+60, posX+145, posY+65, posX+145, posY+55});
		pane.getChildren().addAll(left1, left2, left3, left4, right1, right2, right3, right4);
	}
	
	private void drawVertical(Pane pane, double posX, double posY) {
		Rectangle rectangle = new Rectangle(posX+20, posY, 50, 160);
		rectangle.setFill(Color.web("#dcdcdc"));
		rectangle.setStroke(Color.BLACK);
		pane.getChildren().add(rectangle);
		rectangle = new Rectangle(posX, posY, 20, 160);
		rectangle.setFill(Color.web("#dcdcdc"));
		rectangle.setStroke(Color.BLACK);
		pane.getChildren().add(rectangle);
		
		rec1 = new Rectangle(posX+30, posY+130, 30, 20);
		rec1.setStroke(Color.BLACK);
		
		rec2 = new Rectangle(posX+30, posY+100, 30, 20);
		rec2.setStroke(Color.BLACK);
		
		rec3 = new Rectangle(posX+30, posY+70, 30, 20);
		rec3.setStroke(Color.BLACK);
		
		rec4 = new Rectangle(posX+30, posY+40, 30, 20);
		rec4.setStroke(Color.BLACK);
		
		rec5 = new Rectangle(posX+30, posY+10, 30, 20);
		rec5.setStroke(Color.BLACK);
		pane.getChildren().addAll(rec1, rec2, rec3, rec4, rec5);
		resetPosition();
		
		left1 = new Polyline();
		left1.getPoints().addAll(new Double[]{posX+5, posY+15, posX+10, posY+5, posX+15, posY+15, posX+5, posY+15});
		left2 = new Polyline();
		left2.getPoints().addAll(new Double[]{posX+5, posY+35, posX+10, posY+25, posX+15, posY+35, posX+5, posY+35});
		left3 = new Polyline();
		left3.getPoints().addAll(new Double[]{posX+5, posY+55, posX+10, posY+45, posX+15, posY+55, posX+5, posY+55});
		left4 = new Polyline();
		left4.getPoints().addAll(new Double[]{posX+5, posY+75, posX+10, posY+65, posX+15, posY+75, posX+5, posY+75});
		
		right1 = new Polyline();
		right1.getPoints().addAll(new Double[]{posX+5, posY+85, posX+10, posY+95, posX+15, posY+85, posX+5, posY+85});
		right2 = new Polyline();
		right2.getPoints().addAll(new Double[]{posX+5, posY+105, posX+10, posY+115, posX+15, posY+105, posX+5, posY+105});
		right3 = new Polyline();
		right3.getPoints().addAll(new Double[]{posX+5, posY+125, posX+10, posY+135, posX+15, posY+125, posX+5, posY+125});
		right4 = new Polyline();
		right4.getPoints().addAll(new Double[]{posX+5, posY+145, posX+10, posY+155, posX+15, posY+145, posX+5, posY+145});
		pane.getChildren().addAll(left1, left2, left3, left4, right1, right2, right3, right4);
	}
	
	public void activateLeft() {
		left1.setFill(ShapeHelper.ACTIVE);
		left2.setFill(ShapeHelper.ACTIVE);
		left3.setFill(ShapeHelper.ACTIVE);
		left4.setFill(ShapeHelper.ACTIVE);
	}
	
	public void deactivateLeft() {
		left1.setFill(ShapeHelper.INACTIVE);
		left2.setFill(ShapeHelper.INACTIVE);
		left3.setFill(ShapeHelper.INACTIVE);
		left4.setFill(ShapeHelper.INACTIVE);
	}
	
	public void activateRight() {
		right1.setFill(ShapeHelper.ACTIVE);
		right2.setFill(ShapeHelper.ACTIVE);
		right3.setFill(ShapeHelper.ACTIVE);
		right4.setFill(ShapeHelper.ACTIVE);
	}
	
	public void deactivateRight() {
		right1.setFill(ShapeHelper.INACTIVE);
		right2.setFill(ShapeHelper.INACTIVE);
		right3.setFill(ShapeHelper.INACTIVE);
		right4.setFill(ShapeHelper.INACTIVE);
	}
	
	public void resetPosition() {
		rec1.setFill(ShapeHelper.INACTIVE);
		rec2.setFill(ShapeHelper.INACTIVE);
		rec3.setFill(ShapeHelper.INACTIVE);
		rec4.setFill(ShapeHelper.INACTIVE);
		rec5.setFill(ShapeHelper.INACTIVE);
	}
	
	public void setRelativePosition(float relativePosition) {
		resetPosition();
		if(relativePosition > 0 && relativePosition <= 20) {
			rec1.setFill(ShapeHelper.WORKPIECE_COLOR);	
		}else if(relativePosition <= 40) {
			rec2.setFill(ShapeHelper.WORKPIECE_COLOR);
		}else if(relativePosition <= 60) {
			rec3.setFill(ShapeHelper.WORKPIECE_COLOR);
		}else if(relativePosition <= 80) {
			rec4.setFill(ShapeHelper.WORKPIECE_COLOR);
		}else {
			rec5.setFill(ShapeHelper.WORKPIECE_COLOR);
		}
	}
	
	// TODO: Not good, should be set via constructor (resulting changes: pane should be in simulation, passing only coordinates and type to conveyor)
	public void setLength(int length) {
		this.length = length;
	}
	
}