package me.codeflsuher.msunlimiter.mixin;

import com.mojang.logging.LogUtils;
import me.codeflsuher.msunlimiter.gamerules.ModGamerules;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.vehicle.AbstractMinecart;
import net.minecraft.world.entity.vehicle.MinecartTNT;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecartTNT.class)
public abstract class TntMinecartMixin extends AbstractMinecart {
    @Shadow private float explosionPowerBase;

    @Shadow protected abstract void explode(@Nullable DamageSource damageSource, double d);

    private Logger logger =  LogUtils.getLogger();
        protected TntMinecartMixin(EntityType<?> entityType, Level level) {
        super(entityType, level);
    }

    @Unique
    public double additionalExplosionPower;

    @Unique
    public void addExplosionPower(float additional){
        explosionPowerBase += additional;
    }

    @ModifyArg(method = "explode(Lnet/minecraft/world/damagesource/DamageSource;D)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;explode(Lnet/minecraft/world/entity/Entity;Lnet/minecraft/world/damagesource/DamageSource;Lnet/minecraft/world/level/ExplosionDamageCalculator;DDDFZLnet/minecraft/world/level/Level$ExplosionInteraction;)V"),index = 6)
    public float modify(float original){
        if (!level().getGameRules().getBoolean(ModGamerules.USE_TNT_BUFFING))
            return original;
        return (float) (original+additionalExplosionPower);
    }

    @Inject(method = "explode(Lnet/minecraft/world/damagesource/DamageSource;D)V", at = @At("TAIL"))
    private void test(DamageSource damageSource, double d, CallbackInfo ci){
//        logger.info("TEST {}", d);
    }

    @Inject(method = "readAdditionalSaveData", at = @At("TAIL"))
    private void injectAddtitionalReadData(CompoundTag compoundTag, CallbackInfo callbackInfo){
        additionalExplosionPower = compoundTag.getFloat("additionalExplosionPower");
    }
    @Inject(method = "addAdditionalSaveData", at = @At("TAIL"))
    private void injectAddtitionalSaveData(CompoundTag compoundTag, CallbackInfo callbackInfo){
        compoundTag.putFloat("additionalExplosionPower", (float) additionalExplosionPower);
    }
}
