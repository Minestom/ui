package net.minestom.ui.extension;

import net.minestom.server.MinecraftServer;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.entity.Entity;
import net.minestom.server.entity.EntityType;
import net.minestom.server.entity.LivingEntity;
import net.minestom.server.extensions.Extension;
import net.minestom.ui.Main;

public class MinestomUIExtension extends Extension {
    @Override
    public void initialize() {
        getLogger().info("Hello from Minestom UI");

        var instance = MinecraftServer.getInstanceManager().getInstances().stream().findFirst().get();

        Entity testEntity1 = new Entity(EntityType.ZOMBIE);
        testEntity1.setInstance(instance, new Pos(-1, 42, 0));

        Entity testEntity2 = new LivingEntity(EntityType.WOLF);
        testEntity2.setInstance(instance, new Pos(1, 42, 0));



        Main.main(new String[]{});
    }

    @Override
    public void terminate() {

    }
}
