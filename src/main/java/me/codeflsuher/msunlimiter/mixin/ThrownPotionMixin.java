package me.codeflsuher.msunlimiter.mixin;

import com.mojang.logging.LogUtils;
import me.codeflsuher.msunlimiter.gamerules.ModGamerules;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.entity.projectile.ThrownPotion;
import net.minecraft.world.entity.vehicle.MinecartTNT;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

@Mixin(ThrownPotion.class)
public abstract class ThrownPotionMixin extends ThrowableItemProjectile {

    public Logger logger = LogUtils.getLogger();

    public ThrownPotionMixin(EntityType<? extends ThrowableItemProjectile> entityType, Level level) {
        super(entityType, level);
    }

    @Inject(method = "applySplash", at = @At("HEAD"))
    private void injectSplash(Iterable<MobEffectInstance> iterable, @Nullable Entity entity, CallbackInfo info){

        if (level().isClientSide)
            return;
        if (!level().getGameRules().getBoolean(ModGamerules.USE_TNT_BUFFING))
            return;

        AtomicBoolean hasEffect = new AtomicBoolean(false);
        AtomicReference<MobEffectInstance> instance = new AtomicReference<>();
        iterable.iterator().forEachRemaining(it->{
            if (hasEffect.get()) {
                return;
            }
            if(it.getEffect().equals(MobEffects.DAMAGE_BOOST))
            {
                hasEffect.set(true);
                instance.set(it);
            }
        });
        if (!hasEffect.get())
            return;
        var list = level().getEntitiesOfClass(MinecartTNT.class, this.getBoundingBox().inflate(4,2,4));
//        logger.info("Applying Splash Potion");
        list.forEach(cart ->{
            try {
                var field = cart.getClass().getDeclaredField("additionalExplosionPower");
//                logger.info(field.getName());
                var currentValue = (Double) field.get(cart);
//                logger.info(currentValue.toString());
                field.setAccessible(true);
                field.set(cart,currentValue + Math.clamp(level().getGameRules().getInt(ModGamerules.ONE_POTION_MINECART_BUFF), 1, Integer.MAX_VALUE)*instance.get().getAmplifier());
//                logger.info("We did something");
//                logger.info(((Double) field.get(cart)).toString());
            } catch (Exception e) {
                logger.info("Something went wrong", e);
            }
        });
    }


}
