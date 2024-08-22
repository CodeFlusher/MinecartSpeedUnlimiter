package me.codeflsuher.msunlimiter.mixin;

import me.codeflsuher.msunlimiter.gamerules.ModGamerules;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.GameRules;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;

@Mixin(GameRules.class)
public abstract class GamerulesMixin {
    @Shadow
    private static <T extends GameRules.Value<T>> GameRules.Key<T> register(String string, GameRules.Category category, GameRules.Type<T> type) {
        return null;
    }

    @Unique
    private static GameRules.Key<GameRules.BooleanValue> RULE_USE_TNT_BUFFING;
    @Unique
    private static GameRules.Key<GameRules.IntegerValue> RULE_ONE_POTION_MINECART_BUFF;

    @Inject(method = "<clinit>", at = @At("TAIL"))
    private static void staticInject(CallbackInfo ci){
            ModGamerules.USE_TNT_BUFFING = register("usePotionTNTMinecartBuffing", GameRules.Category.MISC, BooleanRuleMixin.create(false));
            ModGamerules.ONE_POTION_MINECART_BUFF = register("onePotionTntMinecartBuff", GameRules.Category.MISC, IntegerRuleMixin.create(1, 1, 10, FeatureFlagSet.of(), (minecraftServer, integerValue) -> {
            }));
    }

}
