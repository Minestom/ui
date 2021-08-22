package net.minestom.ui.panel.nbt;

import org.jetbrains.annotations.NotNull;
import org.jglrxavpok.hephaistos.nbt.*;

import java.io.PrintStream;
import java.util.Arrays;
import java.util.stream.Collectors;

public interface NBTCompoundVisitor {
    default void visitByte(String key, NBTByte nbt) {}
    default void visitByteArray(String key, NBTByteArray nbt) {}
    default void visitString(String key, NBTString nbt) {}
    default void visitInt(String key, NBTInt nbt) {}
    default void visitIntArray(String key, NBTIntArray nbt) {}
    default void visitLong(String key, NBTLong nbt) {}
    default void visitLongArray(String key, NBTLongArray nbt) {}
    default void visitShort(String key, NBTShort nbt) {}
    default void visitFloat(String key, NBTFloat nbt) {}
    default void visitDouble(String key, NBTDouble nbt) {}
    default void visitList(String key, NBTList<?> nbt) {}
    default void visitCompound(String key, NBTCompound compound) {
        walk(compound);
    }

    default void walk(@NotNull NBTCompound compound) {
        for (String k : compound.getKeys()) {
            var nbt = compound.get(k);
            if (nbt instanceof NBTByte b) {
                visitByte(k, b);
            } else if (nbt instanceof NBTByteArray ba) {
                visitByteArray(k, ba);
            } else if (nbt instanceof NBTString s) {
                visitString(k, s);
            } else if (nbt instanceof NBTInt i) {
                visitInt(k, i);
            } else if (nbt instanceof NBTIntArray ia) {
                visitIntArray(k, ia);
            } else if (nbt instanceof NBTLong l) {
                visitLong(k, l);
            } else if (nbt instanceof NBTLongArray la) {
                visitLongArray(k, la);
            } else if (nbt instanceof NBTShort s) {
                visitShort(k, s);
            } else if (nbt instanceof NBTFloat f) {
                visitFloat(k, f);
            } else if (nbt instanceof NBTDouble d) {
                visitDouble(k, d);
            } else if (nbt instanceof NBTList<?> l) {
                visitList(k, l);
            } else if (nbt instanceof NBTCompound c) {
                visitCompound(k, c);
            }
        }
    }

    class Printer implements NBTCompoundVisitor {
        private final PrintStream printer;
        private int indent = 0;

        public Printer(@NotNull PrintStream printer) {
            this.printer = printer;
        }

        private void print(String key, String value) {
            printer.print(" ".repeat(indent * 2));
            printer.print(key);
            printer.print(" : ");
            printer.println(value);
        }

        @Override
        public void visitByte(String key, NBTByte nbt) {
            print(key, nbt.getValue() + "b");

        }

        @Override
        public void visitByteArray(String key, NBTByteArray nbt) {
            String[] elements = new String[nbt.getValue().length];
            for (int i = 0; i < elements.length; i++) {
                elements[i] = nbt.getValue()[i] + "b";
            }
            print(key, "[ " + String.join(",", elements) + " ]");
        }

        @Override
        public void visitString(String key, NBTString nbt) {
            print(key, "\"" + nbt.getValue() + "\"");
        }

        @Override
        public void visitInt(String key, NBTInt nbt) {
            print(key, nbt.getValue() + "");
        }

        @Override
        public void visitIntArray(String key, NBTIntArray nbt) {
            String stringified = Arrays.stream(nbt.getValue()).mapToObj(i -> i + "").collect(Collectors.joining(", "));
            print(key, "[ " + stringified + " ]");
        }

        @Override
        public void visitLong(String key, NBTLong nbt) {
            print(key, nbt.getValue() + "l");
        }

        @Override
        public void visitLongArray(String key, NBTLongArray nbt) {
            String stringified = Arrays.stream(nbt.getValue()).mapToObj(i -> i + "l").collect(Collectors.joining(", "));
            print(key, "[ " + stringified + " ]");
        }

        @Override
        public void visitShort(String key, NBTShort nbt) {
            print(key, nbt.getValue() + "s");
        }

        @Override
        public void visitFloat(String key, NBTFloat nbt) {
            print(key, nbt.getValue() + "f");
        }

        @Override
        public void visitDouble(String key, NBTDouble nbt) {
            print(key, nbt.getValue() + "");
        }

        @Override
        public void visitList(String key, NBTList<?> nbt) {
            print(key, "<list>");
        }

        @Override
        public void visitCompound(String key, NBTCompound compound) {
            print(key, "<compound>");

            indent++;
            NBTCompoundVisitor.super.visitCompound(key, compound);
            indent--;
        }
    }
}
