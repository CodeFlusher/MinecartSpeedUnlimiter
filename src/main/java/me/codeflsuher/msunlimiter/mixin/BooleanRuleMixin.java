package me.codeflsuher.msunlimiter.mixin;

import net.minecraft.server.MinecraftServer;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.level.GameRules;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

import java.util.function.BiConsumer;

@Mixin(GameRules.BooleanValue.class)
public interface BooleanRuleMixin{

    @Invoker("create")
    static GameRules.Type<GameRules.BooleanValue> create(
            boolean state
    ) {
        return null;
    }
}
