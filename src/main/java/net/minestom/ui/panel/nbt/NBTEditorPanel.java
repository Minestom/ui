package net.minestom.ui.panel.nbt;

import net.minestom.server.entity.Entity;
import net.minestom.server.utils.NamespaceID;
import net.minestom.ui.swing.panel.MSPanel;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jglrxavpok.hephaistos.nbt.NBTCompound;

import java.util.function.Supplier;

public class NBTEditorPanel extends MSPanel {
    private NBTCompound nbt;
    private Supplier<NBTCompound> nbtProvider;

    public NBTEditorPanel() {
        super(NamespaceID.from("minestom", "nbt_editor"));


    }

    public void setNBT(@Nullable NBTCompound nbt) {

    }

    public void setNBTProvider(@NotNull Supplier<NBTCompound> provider) {

    }
}
