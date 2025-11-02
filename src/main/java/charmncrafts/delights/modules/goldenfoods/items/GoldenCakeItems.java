package charmncrafts.delights.modules.goldenfoods.items;

import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import charmncrafts.delights.modules.goldenfoods.blocks.GoldenCakes;

public class GoldenCakeItems {
    private static final String MOD_ID = "charmncrafts-culinary-delights";

    // Block items for cakes
    public static final Item GOLDEN_CAKE = registerBlockItem("golden_cake", new BlockItem(GoldenCakes.GOLDEN_CAKE, new Item.Settings()));
    public static final Item ENCHANTED_GOLDEN_CAKE = registerBlockItem("enchanted_golden_cake", new BlockItem(GoldenCakes.ENCHANTED_GOLDEN_CAKE, new Item.Settings()));

    private static Item registerBlockItem(String name, BlockItem item) {
        return Registry.register(
            Registries.ITEM,
            new Identifier(MOD_ID, name),
            item
        );
    }

    public static void initialize() {
        // Static initialization triggers when class loads
    }
}
