package charmncrafts.delights.item;

import charmncrafts.delights.item.GoldenCakeItems;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ModItemGroups {
    
    public static final RegistryKey<ItemGroup> GOLDEN_FOODS_TAB = RegistryKey.of(
        RegistryKeys.ITEM_GROUP,
        new Identifier("charmncrafts-culinary-delights", "golden_foods")
    );
    
    public static void registerItemGroups() {
        Registry.register(
            Registries.ITEM_GROUP,
            GOLDEN_FOODS_TAB,
            FabricItemGroup.builder()
                .icon(() -> new ItemStack(GoldenFoods.GOLDEN_SWEET_BERRIES))
                .displayName(Text.translatable("itemGroup.charmncrafts-culinary-delights.golden_foods"))
                .entries((displayContext, entries) -> {
                    // Fruits
                    entries.add(GoldenFoods.GOLDEN_SWEET_BERRIES);
                    entries.add(GoldenFoods.GOLDEN_BAKED_POTATO);
                    entries.add(GoldenFoods.GOLDEN_BEETROOT);
                    entries.add(GoldenFoods.GOLDEN_DRIED_KELP);
                    entries.add(GoldenFoods.GOLDEN_GLOW_BERRIES);
                    entries.add(GoldenFoods.GOLDEN_MELON_SLICE);
                    entries.add(GoldenFoods.GOLDEN_CHORUS_FRUIT);
                    
                    // Cooked meats
                    entries.add(GoldenFoods.GOLDEN_COOKED_BEEF);
                    entries.add(GoldenFoods.GOLDEN_COOKED_SALMON);
                    entries.add(GoldenFoods.GOLDEN_COOKED_CHICKEN);
                    entries.add(GoldenFoods.GOLDEN_COOKED_RABBIT);
                    entries.add(GoldenFoods.GOLDEN_COOKED_PORKCHOP);
                    entries.add(GoldenFoods.GOLDEN_COOKED_MUTTON);
                    entries.add(GoldenFoods.GOLDEN_COOKED_COD);
                    
                    // Breads and baked goods
                    entries.add(GoldenFoods.GOLDEN_BREAD);
                    entries.add(GoldenFoods.GOLDEN_COOKIE);
                    entries.add(GoldenFoods.GOLDEN_PUMPKIN_PIE);
                    
                    // Soups and stews
                    entries.add(GoldenFoods.GOLDEN_MUSHROOM_STEW);
                    entries.add(GoldenFoods.GOLDEN_BEETROOT_SOUP);
                    entries.add(GoldenFoods.GOLDEN_RABBIT_STEW);
                    
                    // Drinks
                    entries.add(GoldenFoods.GOLDEN_MILK_BUCKET);
                    entries.add(GoldenFoods.GOLDEN_HONEY_BOTTLE);
                    
                    // Cakes
                    entries.add(GoldenCakeItems.GOLDEN_CAKE);
                    entries.add(GoldenCakeItems.ENCHANTED_GOLDEN_CAKE);
                })
                .build()
        );
    }
}
