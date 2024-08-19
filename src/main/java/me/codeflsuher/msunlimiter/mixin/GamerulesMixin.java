package me.codeflsuher.msunlimiter.mixin;

import com.mojang.logging.LogUtils;
import me.codeflsuher.msunlimiter.gamerules.ModGamerules;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.GameRules;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Map;
import java.util.function.BiConsumer;

@Mixin(GameRules.class)
public abstract class GamerulesMixin {
    @Unique
    private static GameRules.Key<GameRules.BooleanValue> RULE_USE_TNT_BUFFING;
    @Unique
    private static GameRules.Key<GameRules.IntegerValue> RULE_ONE_POTION_MINECART_BUFF;

    @Shadow
    protected static <T extends GameRules.Value<T>> GameRules.Key<T> register(String string, GameRules.Category category, GameRules.Type<T> type) {
        return null;
    }

    @Shadow @Final private Map<GameRules.Key<?>, GameRules.Value<?>> rules;

    @Inject(method = "<clinit>", at = @At("TAIL"))
    private static void staticInject(CallbackInfo ci){
        try {
            var createMethod = GameRules.IntegerValue.class.getDeclaredMethod("create", int.class);
            createMethod.setAccessible(true);
            var createBooleanMethod = GameRules.BooleanValue.class.getDeclaredMethod("create", boolean.class);
            createMethod.setAccessible(true);

            ModGamerules.ONE_POTION_MINECART_BUFF = register("onePotionTntMinecartBuff", GameRules.Category.MISC, (GameRules.Type<GameRules.IntegerValue>) createMethod.invoke(null,1));
            ModGamerules.USE_TNT_BUFFING = register("usePotionTNTMinecartBuffing", GameRules.Category.MISC, (GameRules.Type<GameRules.BooleanValue>) createBooleanMethod.invoke(null, false));


        } catch (Exception e){

        }
    }
}
