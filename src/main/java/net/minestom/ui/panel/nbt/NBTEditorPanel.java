package net.minestom.ui.panel.nbt;

import net.minestom.server.utils.NamespaceID;
import net.minestom.ui.swing.control.MSControl;
import net.minestom.ui.swing.panel.MSPanel;
import net.minestom.ui.swing.tree.MSTreeNode;
import net.minestom.ui.swing.tree.MSTreeView;
import net.minestom.ui.swing.util.icon.MSIcon;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jglrxavpok.hephaistos.nbt.*;

import javax.swing.*;
import java.awt.*;
import java.util.Stack;
import java.util.function.Supplier;

public class NBTEditorPanel extends MSPanel {
    private NBTCompound nbt;
    private Supplier<NBTCompound> nbtProvider;



    public NBTEditorPanel() {
        super(NamespaceID.from("minestom", "nbt_editor"));

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JLabel test = new JLabel("AWOITGHAWIUGHIUOAW");
        add(test);





        var icon = new MSIcon(":/img/nbt/nbt_int.png");
        add(new JLabel("Image and text", icon, JLabel.LEFT));

        MSTreeView demoTree = new MSTreeView();
        add(demoTree);

        msResize();

        NBTCompound nbt = new NBTCompound();
        nbt.setByte("byte", (byte) 1);
        nbt.setByteArray("byte array", new byte[]{1, 2, 3});
        nbt.setString("string", "I am a test string wow");
        nbt.setInt("int", 123);
        nbt.setIntArray("int array", new int[]{1, 2, 3, 4, 5});
        nbt.setLong("long", 123456);
        nbt.setLongArray("long array", new long[]{123, 456, 789});
        nbt.setShort("short", (short) 25565);
        nbt.setFloat("float", 3.14f);
        nbt.setDouble("double", 3.1415926);
        var list = new NBTList<>(NBTTypes.TAG_Int);
        list.add(new NBTInt(1));
        list.add(new NBTInt(2));
        list.add(new NBTInt(3));
        nbt.set("int list", list);
        var compound = new NBTCompound();
        compound.setInt("one", 1);
        compound.setInt("two", 2);
        compound.setString("3", "three");
        nbt.set("child", compound);

        new NBTCompoundVisitor.Printer(System.out).walk(nbt);
        new Visitor(demoTree).walk(nbt);
    }

    public void setNBT(@Nullable NBTCompound nbt) {

    }

    public void setNBTProvider(@NotNull Supplier<NBTCompound> provider) {

    }

    private static class Visitor implements NBTCompoundVisitor {
        private final Stack<MSTreeNode> stack = new Stack<>();

        public Visitor(MSTreeView treeView) {
            stack.push(treeView.getRoot());
        }

        private Box createEntry(String key, MSIcon icon, @Nullable Container control) {
            Box container = Box.createHorizontalBox();

            var label = new JLabel(key, icon, JLabel.LEFT);
            label.setPreferredSize(new Dimension(200, label.getPreferredSize().height));
            container.add(label);

            if (control != null) {
                container.add(control);
            }

            container.add(Box.createHorizontalGlue());

            if (control != null) {
                container.add(Box.createRigidArea(new Dimension(10, 10)));
            }
            return container;
        }

        @Override
        public void visitByte(String key, NBTByte nbt) {
            stack.peek().getChildNodeOrCreate(key, () -> {
                return createEntry(key, NBTIcon.Byte, MSControl.Dynamic(byte.class, nbt.getValue()));
            });
        }

        @Override
        public void visitByteArray(String key, NBTByteArray nbt) {
            stack.peek().getChildNodeOrCreate(key, () -> {
                return createEntry(key, NBTIcon.ByteArray, null);
            });
        }

        @Override
        public void visitString(String key, NBTString nbt) {
            stack.peek().getChildNodeOrCreate(key, () -> {
                return createEntry(key, NBTIcon.String, MSControl.Dynamic(String.class, nbt.getValue()));
            });
        }

        @Override
        public void visitInt(String key, NBTInt nbt) {
            stack.peek().getChildNodeOrCreate(key, () -> {
                return createEntry(key, NBTIcon.Int, MSControl.Dynamic(int.class, nbt.getValue()));
            });
        }

        @Override
        public void visitIntArray(String key, NBTIntArray nbt) {
            stack.peek().getChildNodeOrCreate(key, () -> {
                return createEntry(key, NBTIcon.IntArray, null);
            });
        }

        @Override
        public void visitLong(String key, NBTLong nbt) {
            stack.peek().getChildNodeOrCreate(key, () -> {
                return createEntry(key, NBTIcon.Long, MSControl.Dynamic(long.class, nbt.getValue()));
            });
        }

        @Override
        public void visitLongArray(String key, NBTLongArray nbt) {
            stack.peek().getChildNodeOrCreate(key, () -> {
                return createEntry(key, NBTIcon.LongArray, null);
            });
        }

        @Override
        public void visitShort(String key, NBTShort nbt) {
            stack.peek().getChildNodeOrCreate(key, () -> {
                return createEntry(key, NBTIcon.Short, MSControl.Dynamic(short.class, nbt.getValue()));
            });
        }

        @Override
        public void visitFloat(String key, NBTFloat nbt) {
            stack.peek().getChildNodeOrCreate(key, () -> {
                return createEntry(key, NBTIcon.Float, MSControl.Dynamic(float.class, nbt.getValue()));
            });
        }

        @Override
        public void visitDouble(String key, NBTDouble nbt) {
            stack.peek().getChildNodeOrCreate(key, () -> {
                return createEntry(key, NBTIcon.Double, MSControl.Dynamic(double.class, nbt.getValue()));
            });
        }

        @Override
        public void visitList(String key, NBTList<?> nbt) {
            stack.peek().getChildNodeOrCreate(key, () -> {
                return createEntry(key, NBTIcon.List, null);
            });
        }

        @Override
        public void visitCompound(String key, NBTCompound compound) {
            //todo this system doesnt handle removing nodes which are no longer present
            MSTreeNode next = stack.peek().getChildNodeOrCreate(key, () -> {
                return createEntry(key, NBTIcon.Compound, null);
            });

            stack.push(next);

            NBTCompoundVisitor.super.visitCompound(key, compound);

            stack.pop();
        }
    }
}
