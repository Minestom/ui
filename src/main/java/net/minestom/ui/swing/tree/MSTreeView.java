package net.minestom.ui.swing.tree;

import com.formdev.flatlaf.icons.*;
import net.minestom.ui.swing.MSContainer;
import net.minestom.ui.swing.util.MSIcon;
import net.minestom.ui.swing.util.SwingHelper;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MSTreeView extends MSContainer {
    private static final Logger logger = LoggerFactory.getLogger(MSTreeView.class);

    // Flags
    public static final int NONE = 0;
    public static final int ROOT_NODE_HIDDEN = 0x1;

    private final int opts;
    private final MSTreeNode root;

    public MSTreeView() {
        this(NONE);
    }

    public MSTreeView(int opts) {
        this.opts = opts;

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        root = new MSTreeNode(SwingHelper.alignLeft(new JLabel("<root>")));
        add(root);

//        var box = Box.createHorizontalBox();
//        box.add(new JLabel("Testing 123"));
//        box.add(Box.createHorizontalGlue());
//        box.add(new JLabel("Testing 123"));
//        add(box);
//
//
//        box = Box.createHorizontalBox();
//        var collapsed = true;
//        var collapsedIcon = new FlatTreeCollapsedIcon();
//        var expandedIcon = new FlatTreeExpandedIcon();
//
//        var testicon = new FlatClearIcon();
//
//        var changableLabel = new JLabel(testicon);
//        changableLabel.addMouseListener(new MouseAdapter() {
//            @Override
//            public void mouseClicked(MouseEvent e) {
//                if (changableLabel.getIcon() == collapsedIcon) {
//                    changableLabel.setIcon(expandedIcon);
//                } else {
//                    changableLabel.setIcon(collapsedIcon);
//                }
//            }
//        });
//        box.add(changableLabel);
//        var icon = new MSIcon(":/img/nbt/nbt_compound.png", 20, 20);
//        box.add(new JLabel("Image and text", icon, JLabel.LEFT));
//        box.add(Box.createHorizontalGlue());
//        add(box);



//        var tn = new MSTreeNode();
//        tn.__addChild(new MSTreeNode());
//        tn.__addChild(new MSTreeNode());
//        var tn2 = new MSTreeNode();
//        tn2.__addChild(new MSTreeNode());
//        tn2.__addChild(new MSTreeNode());
//        tn.__addChild(tn2);
//        tn.__addChild(new MSTreeNode());
//        tn.__addChild(new MSTreeNode());
//        add(tn);
//
//        tn = new MSTreeNode();
//        tn.__addChild(new MSTreeNode());
//        tn.__addChild(new MSTreeNode());
//        add(tn);
    }




    @NotNull
    public MSTreeNode getRoot() {
        return root;
    }

}
