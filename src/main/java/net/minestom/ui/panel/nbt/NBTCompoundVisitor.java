package net.minestom.ui.panel.nbt;

import org.jetbrains.annotations.NotNull;
import org.jglrxavpok.hephaistos.nbt.*;

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
}
