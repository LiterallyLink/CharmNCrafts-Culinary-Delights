package charmncrafts.delights.modules.goldenfoods.items;

import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class GoldenFoods {
    private static final String MOD_ID = "charmncrafts-culinary-delights";

    // Different food components with varying saturation levels AND saturation effect
    // Duration in ticks: 20 ticks = 1 second, 1200 ticks = 1 minute, 6000 ticks = 5 minutes

    // Fruits - 1.5 minutes (1800 ticks) saturation effect
    public static final FoodComponent GOLDEN_FRUIT = new FoodComponent.Builder()
        .hunger(6)
        .saturationModifier(0.6f)
        .statusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 60, 0), 1.0f)
        .statusEffect(new StatusEffectInstance(StatusEffects.SATURATION, 1800, 0), 1.0f)
        .alwaysEdible()
        .build();

    // Meats - 4 minutes (4800 ticks) saturation effect
    public static final FoodComponent GOLDEN_MEAT = new FoodComponent.Builder()
        .hunger(9)
        .saturationModifier(1.3f)
        .statusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 100, 1), 1.0f)
        .statusEffect(new StatusEffectInstance(StatusEffects.SATURATION, 4800, 0), 1.0f)
        .alwaysEdible()
        .meat()
        .build();

    // Breads - 2.5 minutes (3000 ticks) saturation effect
    public static final FoodComponent GOLDEN_BREAD_TYPE = new FoodComponent.Builder()
        .hunger(7)
        .saturationModifier(1.0f)
        .statusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 80, 0), 1.0f)
        .statusEffect(new StatusEffectInstance(StatusEffects.SATURATION, 3000, 0), 1.0f)
        .alwaysEdible()
        .build();

    // Soups/Stews - 5 minutes (6000 ticks) saturation effect
    public static final FoodComponent GOLDEN_SOUP = new FoodComponent.Builder()
        .hunger(10)
        .saturationModifier(1.2f)
        .statusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 120, 1), 1.0f)
        .statusEffect(new StatusEffectInstance(StatusEffects.SATURATION, 6000, 0), 1.0f)
        .alwaysEdible()
        .build();

    // Drinks - 1 minute (1200 ticks) saturation effect
    public static final FoodComponent GOLDEN_DRINK = new FoodComponent.Builder()
        .hunger(5)
        .saturationModifier(0.8f)
        .statusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 50, 0), 1.0f)
        .statusEffect(new StatusEffectInstance(StatusEffects.SATURATION, 1200, 0), 1.0f)
        .alwaysEdible()
        .build();

    // Enchanted cake - ultra premium (5 minutes / 6000 ticks saturation effect, Level II)
    public static final FoodComponent ENCHANTED_CAKE_FOOD = new FoodComponent.Builder()
        .hunger(12)
        .saturationModifier(1.8f)
        .statusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 200, 2), 1.0f)
        .statusEffect(new StatusEffectInstance(StatusEffects.SATURATION, 6000, 1), 1.0f)
        .statusEffect(new StatusEffectInstance(StatusEffects.ABSORPTION, 2400, 0), 1.0f)
        .alwaysEdible()
        .build();

    // Regular golden foods with varying saturation

    // Fruits (1.5 minutes saturation effect)
    public static final Item GOLDEN_SWEET_BERRIES = registerFood("golden_sweet_berries", GOLDEN_FRUIT);
    public static final Item GOLDEN_GLOW_BERRIES = registerFood("golden_glow_berries", GOLDEN_FRUIT);
    public static final Item GOLDEN_MELON_SLICE = registerFood("golden_melon_slice", GOLDEN_FRUIT);
    public static final Item GOLDEN_CHORUS_FRUIT = registerFood("golden_chorus_fruit", GOLDEN_FRUIT);
    public static final Item GOLDEN_DRIED_KELP = registerFood("golden_dried_kelp", GOLDEN_FRUIT);

    // Vegetables (2 minutes saturation effect)
    public static final Item GOLDEN_BAKED_POTATO = registerFood("golden_baked_potato",
        new FoodComponent.Builder().hunger(7).saturationModifier(0.7f)
            .statusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 80, 0), 1.0f)
            .statusEffect(new StatusEffectInstance(StatusEffects.SATURATION, 2400, 0), 1.0f)
            .alwaysEdible().build());

    public static final Item GOLDEN_BEETROOT = registerFood("golden_beetroot",
        new FoodComponent.Builder().hunger(7).saturationModifier(0.7f)
            .statusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 80, 0), 1.0f)
            .statusEffect(new StatusEffectInstance(StatusEffects.SATURATION, 2400, 0), 1.0f)
            .alwaysEdible().build());

    // Cooked meats (4 minutes saturation effect)
    public static final Item GOLDEN_COOKED_BEEF = registerFood("golden_cooked_beef", GOLDEN_MEAT);
    public static final Item GOLDEN_COOKED_SALMON = registerFood("golden_cooked_salmon", GOLDEN_MEAT);
    public static final Item GOLDEN_COOKED_CHICKEN = registerFood("golden_cooked_chicken", GOLDEN_MEAT);
    public static final Item GOLDEN_COOKED_RABBIT = registerFood("golden_cooked_rabbit", GOLDEN_MEAT);
    public static final Item GOLDEN_COOKED_PORKCHOP = registerFood("golden_cooked_porkchop", GOLDEN_MEAT);
    public static final Item GOLDEN_COOKED_MUTTON = registerFood("golden_cooked_mutton", GOLDEN_MEAT);
    public static final Item GOLDEN_COOKED_COD = registerFood("golden_cooked_cod", GOLDEN_MEAT);

    // Breads and baked goods (2.5 minutes base saturation effect)
    public static final Item GOLDEN_BREAD = registerFood("golden_bread", GOLDEN_BREAD_TYPE);

    public static final Item GOLDEN_COOKIE = registerFood("golden_cookie",
        new FoodComponent.Builder().hunger(5).saturationModifier(0.9f)
            .statusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 60, 0), 1.0f)
            .statusEffect(new StatusEffectInstance(StatusEffects.SATURATION, 1500, 0), 1.0f)
            .alwaysEdible().build());

    public static final Item GOLDEN_PUMPKIN_PIE = registerFood("golden_pumpkin_pie",
        new FoodComponent.Builder().hunger(8).saturationModifier(1.1f)
            .statusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 100, 1), 1.0f)
            .statusEffect(new StatusEffectInstance(StatusEffects.SATURATION, 3600, 0), 1.0f)
            .alwaysEdible().build());

    // Soups and stews (5 minutes saturation effect)
    public static final Item GOLDEN_MUSHROOM_STEW = registerFood("golden_mushroom_stew", GOLDEN_SOUP);
    public static final Item GOLDEN_BEETROOT_SOUP = registerFood("golden_beetroot_soup", GOLDEN_SOUP);
    public static final Item GOLDEN_RABBIT_STEW = registerFood("golden_rabbit_stew", GOLDEN_SOUP);

    // Drinks (1 minute saturation effect)
    public static final Item GOLDEN_MILK_BUCKET = registerFood("golden_milk_bucket", GOLDEN_DRINK);
    public static final Item GOLDEN_HONEY_BOTTLE = registerFood("golden_honey_bottle", GOLDEN_DRINK);

    private static Item registerFood(String name, FoodComponent food) {
        return Registry.register(
            Registries.ITEM,
            new Identifier(MOD_ID, name),
            new Item(new Item.Settings().food(food))
        );
    }

    public static void initialize() {
        // Static initialization triggers when class loads
    }
}
