package bot.batandwa;

import robocode.*;
// import java.awt.Color;
import robocode.util.Utils;

// API help : https://robocode.sourceforge.io/docs/robocode/robocode/Robot.html


/**
 * EightSignals - a robot by (Batandwa Mgutsi)
 */
public class EightSignals extends AdvancedRobot {

	protected XYTuple[] corners = new XYTuple[4];
	protected int targetCornerIndex = 0;

	/**
	 * run: EightSignals's default behavior
	 */
	public void run() {
		computeCorners();

		// Robot main loop
		while (true) {
			// We have arrived at a corner, scan, fire and move to the next corner.
			turnGunRight(360);
			incrementAndWrapTargetCornerIndex();
			final XYTuple targetCorner = getTargetCorner();
			goTo(targetCorner.x, targetCorner.y);
			out.println("Going to new corner");
		}
	}

	/**
	 * onScannedRobot: What to do when you see another robot
	 */
	public void onScannedRobot(ScannedRobotEvent other) {
		fire(enemyIsCloseEnopughToUseMaxPower(other) ? 3 : 1);
	}

	public boolean enemyIsCloseEnopughToUseMaxPower(ScannedRobotEvent enemy) {
		// For bots close enough that it's impossible to miss, we want to use
		// max power.
		return enemy.getDistance() <= 3 * getWidth();
	}

	/**
	 * Copied from Robocode docs: https://robowiki.net/wiki/GoTo
	 */
	protected void goTo(double x, double y) {
		/* Transform our coordinates into a vector */
		x -= getX();
		y -= getY();

		/* Calculate the angle to the target position */
		double angleToTarget = Math.atan2(x, y);

		/* Calculate the turn required get there */
		double targetAngle = Utils.normalRelativeAngle(angleToTarget - getHeadingRadians());

		/*
		 * The Java Hypot method is a quick way of getting the length of a vector. Which in this
		 * case is also the distance between our robot and the target location.
		 */
		double distance = Math.hypot(x, y);

		/* This is a simple method of performing set front as back */
		double turnAngle = Math.atan(Math.tan(targetAngle));
		setTurnRightRadians(turnAngle);
		if (targetAngle == turnAngle) {
			ahead(distance);
		} else {
			back(distance);
		}
	}

	protected void computeCorners() {
		// We are leaving double a bot width between the wall and our bot
		double botDoubleWidth = getWidth() * 2;
		corners[0] = new XYTuple(0 + botDoubleWidth, 0 + botDoubleWidth);
		corners[1] = new XYTuple(getBattleFieldWidth() - botDoubleWidth, 0 + botDoubleWidth);
		corners[2] = new XYTuple(getBattleFieldWidth() - botDoubleWidth,
				getBattleFieldHeight() - botDoubleWidth);
		corners[3] = new XYTuple(0 + botDoubleWidth, getBattleFieldHeight() - botDoubleWidth);
	}

	protected void incrementAndWrapTargetCornerIndex() {
		targetCornerIndex++;
		if (targetCornerIndex == corners.length)
			targetCornerIndex = 0;
	}

	protected XYTuple getTargetCorner() {
		return corners[targetCornerIndex];
	}
}


class XYTuple {
	final double x;
	final double y;

	public XYTuple(double x, double y) {
		this.x = x;
		this.y = y;
	}
}

