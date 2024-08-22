package me.codeflsuher.msunlimiter.mixin;

import net.minecraft.server.MinecraftServer;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.level.GameRules;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

import java.util.function.BiConsumer;

@Mixin(GameRules.IntegerValue.class)
public interface IntegerRuleMixin{

    @Invoker("create")
    static GameRules.Type<GameRules.IntegerValue> create(
            int i, int j, int k, FeatureFlagSet featureFlagSet, BiConsumer<MinecraftServer, GameRules.IntegerValue> biConsumer
    ) {
        return null;
    }
}
