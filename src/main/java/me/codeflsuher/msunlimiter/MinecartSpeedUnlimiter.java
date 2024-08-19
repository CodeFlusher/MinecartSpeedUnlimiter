package me.codeflsuher.msunlimiter;

import com.mojang.logging.LogUtils;
import net.fabricmc.api.ModInitializer;
import net.minecraft.world.entity.animal.Pig;
import net.minecraft.world.entity.vehicle.MinecartTNT;
import net.minecraft.world.item.alchemy.Potion;

import java.util.logging.Logger;

public class MinecartSpeedUnlimiter implements ModInitializer {

    @Override
    public void onInitialize() {
        LogUtils.getLogger().info("Initializing Minecart tweaks Mod");
    }
}
