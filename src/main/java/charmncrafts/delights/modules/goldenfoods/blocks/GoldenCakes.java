package charmncrafts.delights.modules.goldenfoods.blocks;

import net.minecraft.block.CakeBlock;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class GoldenCakes {
    private static final String MOD_ID = "charmncrafts-culinary-delights";

    // Golden cake - placeable, eatable
    public static final CakeBlock GOLDEN_CAKE = registerCake("golden_cake", new CakeBlock(createSettings()));

    // Enchanted golden cake - placeable, eatable, better effects
    public static final CakeBlock ENCHANTED_GOLDEN_CAKE = registerCake("enchanted_golden_cake", new CakeBlock(createSettings()));

    private static CakeBlock registerCake(String name, CakeBlock block) {
        return Registry.register(
            Registries.BLOCK,
            new Identifier(MOD_ID, name),
            block
        );
    }

    private static net.minecraft.block.AbstractBlock.Settings createSettings() {
        return net.minecraft.block.AbstractBlock.Settings.create()
            .solid()
            .noCollision()
            .strength(0.5f)
            .sounds(net.minecraft.sound.BlockSoundGroup.WOOL);
    }

    public static void initialize() {
        // Static initialization triggers when class loads
    }
}
