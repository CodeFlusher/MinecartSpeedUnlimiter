package me.codeflsuher.msunlimiter.gamerules;

import me.codeflsuher.msunlimiter.mixin.GamerulesMixin;
import net.minecraft.world.level.GameRules;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ModGamerules {
    private static final Logger log = LoggerFactory.getLogger(ModGamerules.class);
    public static GameRules.Key<GameRules.BooleanValue> USE_TNT_BUFFING;
    public static GameRules.Key<GameRules.IntegerValue> ONE_POTION_MINECART_BUFF;

    static {
        try {
            var tntBuffField = GameRules.class.getDeclaredField("RULE_USE_TNT_BUFFING");
            tntBuffField.setAccessible(true);
            USE_TNT_BUFFING = (GameRules.Key<GameRules.BooleanValue>) tntBuffField.get(null);
            var potionBuffField = GameRules.class.getDeclaredField("RULE_ONE_POTION_MINECART_BUFF");
            potionBuffField.setAccessible(true);
            ONE_POTION_MINECART_BUFF = (GameRules.Key<GameRules.IntegerValue>) potionBuffField.get(null);
        } catch (Exception e){
            log.error("Failed to reflect fields",e);
        }
    }
}
