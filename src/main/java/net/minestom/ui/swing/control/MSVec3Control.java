package net.minestom.ui.swing.control;

import net.minestom.server.coordinate.Point;
import net.minestom.server.coordinate.Vec;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;

public class MSVec3Control extends Container {
    private final SpinnerNumberModel xModel;
    private final SpinnerNumberModel yModel;
    private final SpinnerNumberModel zModel;

    public MSVec3Control() {
        final var layout = new BoxLayout(this, BoxLayout.X_AXIS);
        setLayout(layout);

        xModel = new SpinnerNumberModel(0.0, 0, Double.MAX_VALUE, 0.1);
        createInput("X", xModel);

        add(Box.createHorizontalStrut(10));

        yModel = new SpinnerNumberModel(0.0, 0, Double.MAX_VALUE, 0.1);
        createInput("Y", yModel);

        add(Box.createHorizontalStrut(10));

        zModel = new SpinnerNumberModel(0.0, 0, Double.MAX_VALUE, 0.1);
        createInput("Z", zModel);
    }

    public double x() {
        return xModel.getNumber().doubleValue();
    }

    public double y() {
        return yModel.getNumber().doubleValue();
    }

    public double z() {
        return zModel.getNumber().doubleValue();
    }

    public @NotNull Point get() {
        return new Vec(x(), y(), z());
    }

    public void setX(double value) {
        xModel.setValue(value);
    }

    public void setY(double value) {
        yModel.setValue(value);
    }

    public void setZ(double value) {
        zModel.setValue(value);
    }

    public void set(Point value) {
        xModel.setValue(value.x());
        yModel.setValue(value.y());
        zModel.setValue(value.z());
    }

    private void createInput(String resetLabel, SpinnerModel model) {
        JButton resetButton = new JButton(resetLabel);
//        resetButton.putClientProperty("JButton.buttonType", "square");
        resetButton.setToolTipText("Reset " + resetLabel + " value");

        resetButton.addActionListener(e -> model.setValue(0));
        add(resetButton);

        JSpinner spinner = new JSpinner(model);
        // Stop spinner from vertically expanding to fill available area.
        spinner.setMaximumSize(new Dimension(Short.MAX_VALUE, spinner.getPreferredSize().height));
        spinner.setMinimumSize(new Dimension(50, 0));

        setMinimumSize(new Dimension(0, spinner.getPreferredSize().height));
        setMaximumSize(new Dimension(Short.MAX_VALUE, spinner.getPreferredSize().height));

        add(spinner);
    }
}
