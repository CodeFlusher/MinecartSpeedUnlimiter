package me.codeflsuher.msunlimiter.mixin;

import com.mojang.logging.LogUtils;
import net.minecraft.world.entity.vehicle.AbstractMinecart;
import net.minecraft.world.entity.vehicle.Minecart;
import net.minecraft.world.entity.vehicle.MinecartBehavior;
import net.minecraft.world.entity.vehicle.NewMinecartBehavior;
import net.minecraft.world.level.GameRules;
import org.slf4j.Logger;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(NewMinecartBehavior.class)
public abstract class NewMinecartBehaviourMixin extends MinecartBehavior {

    private Logger logger = LogUtils.getLogger();

    protected NewMinecartBehaviourMixin(AbstractMinecart abstractMinecart) {
        super(abstractMinecart);
    }

    @Inject(method = "getSlowdownFactor", at = @At("HEAD"), cancellable = true)
    private void slowdownFactror(CallbackInfoReturnable<Double> ci){
        if(level().getGameRules().getInt(GameRules.RULE_MINECART_MAX_SPEED) >= 1000){
            ci.setReturnValue(1.0);
            ci.cancel();
        }
    }


    @Inject(method = "getMaxSpeed", at=@At("HEAD"), cancellable = true)
    private void overrideMaxSpeed(CallbackInfoReturnable<Double> ci){
        if(level().getGameRules().getInt(GameRules.RULE_MINECART_MAX_SPEED) >= 1000){
            ci.setReturnValue(Double.MAX_VALUE / (this.minecart.isInWater() ? 0.5 : 1.0) / 20.0);
            ci.cancel();
        }
    }
}
