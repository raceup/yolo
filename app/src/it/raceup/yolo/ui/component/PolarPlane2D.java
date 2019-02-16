package it.raceup.yolo.ui.component;

import javax.swing.*;
import java.awt.*;
import java.text.DecimalFormat;

/**
 * aPolar plane 2D
 */
public class PolarPlane2D extends JPanel {
    private static final DecimalFormat SHORT_DEC_FORMAT = new DecimalFormat("0.00");  // decimal places
    private static final int distanceFromAxisToBorder = 60;  // distance from axis to borders
    private static final int distanceFromAxisToLabels = 40;  // distance from axis to labels
    private static final Color axisLabelsColor = Color.black;
    private final PolarPlane2DAxis[] axis;  // list of axis to display

    public PolarPlane2D(Dimension d) {
        super();

        axis = new PolarPlane2DAxis[]{
                new PolarPlane2DAxis(Color.RED, "x"),
                new PolarPlane2DAxis(Color.BLUE, "y")
        };
    }

    /**
     * Draws a point in the plane based on value
     *
     * @param value value of axis
     * @param axis  which axis value refers to
     */
    public void setValue(double value, int axis) {
        if (axis >= 0 && axis < 2) {
            this.axis[axis].setValue(value);
        }

        repaint();
    }

    public void setXYValue(double x, double y) {
        setValue(x, 0);
        setValue(y, 1);
    }

    /*
     * Paint 3D plane
     */

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        setupAxis();  // re-compute size and position of axis
        paintPlane(g);
        paintCurrentPoint(g);
    }

    /**
     * Paint 3D axis
     *
     * @param g graphics with which to paint
     */
    private void paintPlane(Graphics g) {
        for (PolarPlane2DAxis axisLine : axis) {  // add to panel
            paintAxis(axisLine, g);
        }

        paintCircleLines(g);
    }

    /**
     * Paints 3D axis
     *
     * @param axis axis to paint
     * @param g    graphics with which to paint
     */
    private void paintAxis(PolarPlane2DAxis axis, Graphics g) {
        paintAxisLabels(axis, g);
        paintAxisLines(axis, g);
    }

    /**
     * Paints 3D axis labels
     *
     * @param axis axis to paint
     * @param g    graphics with which to paint
     */
    private void paintAxisLabels(PolarPlane2DAxis axis, Graphics g) {
        g.setColor(axisLabelsColor);
        g.drawString(
                axis.label,
                (int) axis.endPoint.getX() + distanceFromAxisToLabels / 2,
                (int) axis.endPoint.getY()
        );  // draw label
        g.drawString(
                axis.valueLabel,
                (int) axis.endPoint.getX() - distanceFromAxisToLabels / 2,
                (int) axis.endPoint.getY() - (int) (distanceFromAxisToBorder * 0.4)
        );  // draw valueLabel
    }

    /**
     * Paints 3D axis lines
     *
     * @param axis axis to paint
     * @param g    graphics with which to paint
     */
    private void paintAxisLines(PolarPlane2DAxis axis, Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // paint value label
        g2.setStroke(new BasicStroke(3));
        g2.setColor(axis.color);  // color line
        g2.drawLine(
                (int) axis.origin.getX(),
                (int) axis.origin.getY(),
                (int) axis.endPoint.getX(),
                (int) axis.endPoint.getY()
        );  // draw real value of axis

        // draw origin point
        Point center = new Point(this.getWidth() / 2, this.getHeight() / 2);
        g2.setStroke(new BasicStroke(10));
        g2.setColor(Color.gray);
        g2.fillOval(
                (int) center.getX() - 3,
                (int) center.getY() - 3,
                6,
                6
        );  // draw origin point
    }

    /**
     * Paints concentric circles around center point
     *
     * @param g graphics with which to paint
     */
    private void paintCircleLines(Graphics g) {
        // largest circle positions
        Point center = new Point(this.getWidth() / 2, this.getHeight() / 2);  // middle point
        Point[] circleCenters = new Point[]{
                center,
                center,
                center
        };
        int[] circleRadii = new int[]{
                20,
                40,
                60
        };

        Rectangle[] circleRectangles = new Rectangle[3];
        for (int i = 0; i < circleCenters.length; i++) {
            Point startPoint = new Point(
                    (int) circleCenters[i].getX() - circleRadii[i],
                    (int) circleCenters[i].getY() - circleRadii[i]
            );
            Point endPoint = new Point(
                    (int) circleCenters[i].getX() + circleRadii[i],
                    (int) circleCenters[i].getY() + circleRadii[i]
            );

            circleRectangles[i] = new Rectangle((int) startPoint.getX(), (int) startPoint.getY(), (int) endPoint.getX(), (int) endPoint.getY());
        }

        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setStroke(new BasicStroke(1));
        g2.setColor(Color.black);  // color line

        for (int i = 0; i < circleRectangles.length; i++) {
            g2.drawOval((int) circleRectangles[i].getX(), (int) circleRectangles[i].getY(), circleRadii[i] * 2, circleRadii[i] * 2);
        }
    }

    /**
     * Gets current values and paints on chart
     *
     * @param g graphics with which to paint
     */
    private void paintCurrentPoint(Graphics g) {
        double currentValueX = axis[1].getCurrentValuePoint().getX();
        double currentValueY = axis[0].getCurrentValuePoint().getY();
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setStroke(new BasicStroke(10));
        g2.setColor(Color.black);
        g2.fillOval(
                (int) currentValueX - 5,
                (int) currentValueY - 5,
                10,
                10
        );  // draw origin point
    }

    /**
     * Sets up axis ad origin
     */
    private void setupAxis() {
        Point[] originPoints = new Point[]{
                new Point(this.getWidth() / 2, this.getHeight() - (int) (distanceFromAxisToBorder * 0.8)),  // center down
                new Point((int) (distanceFromAxisToBorder * 0.8), this.getHeight() / 2)  // center left
        };
        Point[] endPoints = new Point[]{
                new Point(this.getWidth() / 2, (int) (distanceFromAxisToBorder * 0.8)),  // center up
                new Point(this.getWidth() - (int) (distanceFromAxisToBorder * 0.8), this.getHeight() / 2)  // center right
        };

        for (int i = 0; i < axis.length; i++) {  // sets origin/end points of all axis
            axis[i].setOrigin(originPoints[i]);
            axis[i].setEndPoint(endPoints[i]);
        }
    }

    /*
     * Setup gui
     */

    /**
     * Line of 3D plane
     */
    private class PolarPlane2DAxis {
        private final Color color;  // gui
        private final String label;
        private double maxValue = 5.0;  // min, max valueLabel of axis
        private double minValue = -5.0;
        private double value = 0.0;  // current value
        private String valueLabel;
        private Point origin = new Point(0, 0);  // axis
        private Point endPoint = origin;  // where axis stops

        /**
         * Constructs new line axis
         *
         * @param color color of line
         * @param label axis label
         */
        PolarPlane2DAxis(Color color, String label) {
            super();
            this.color = color;
            this.label = label;

            setValue(0.0);
        }

        /**
         * Sets new valueLabel to label
         *
         * @param value new value
         */
        public void setValue(double value) {
            this.value = value;
            valueLabel = SHORT_DEC_FORMAT.format(value);

            if (this.value > maxValue) {  // re-compute max, min
                maxValue = this.value;
            }

            if (this.value < minValue) {
                minValue = this.value;
            }
        }

        /**
         * Calculates point based con current value
         *
         * @return point of value
         */
        public Point getCurrentValuePoint() {
            double valueRatio = (value - minValue) / (maxValue - minValue);
            int valuePointX = (int) (origin.getX() + (endPoint.getX() - origin.getX()) * valueRatio);
            int valuePointY = (int) (origin.getY() + (endPoint.getY() - origin.getY()) * valueRatio);
            return new Point(valuePointX, valuePointY);
        }

        /**
         * Sets origin of axis
         *
         * @param origin point where axis starts
         */
        public void setOrigin(Point origin) {
            this.origin = origin;
        }

        /**
         * Sets where axis stops
         *
         * @param endPoint point where axis stops
         */
        public void setEndPoint(Point endPoint) {
            this.endPoint = endPoint;
        }
    }
}
