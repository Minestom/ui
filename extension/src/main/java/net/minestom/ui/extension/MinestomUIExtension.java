package net.minestom.ui.extension;

import net.minestom.server.extensions.Extension;
import net.minestom.ui.Main;

public class MinestomUIExtension extends Extension {
    @Override
    public void initialize() {
        getLogger().info("Hello from Minestom UI");
        Main.main(new String[]{});
    }

    @Override
    public void terminate() {

    }
}
