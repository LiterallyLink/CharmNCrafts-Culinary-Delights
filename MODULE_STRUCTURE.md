# CharmNCrafts Culinary Delights - Module Structure

This mod is organized as an **amalgamation mod** - a collection of multiple merged mods organized into modular components for easy maintenance and expansion.

## Directory Structure

```
src/main/java/charmncrafts/delights/
├── CharmNCraftsCulinaryDelights.java    # Main mod entry point
├── mixin/                                # Global mixins
└── modules/                              # Individual mod modules
    └── goldenfoods/                      # Golden Foods module
        ├── GoldenFoodsModule.java        # Module initializer
        ├── items/                        # Food items
        │   ├── GoldenFoods.java          # 21 golden food items
        │   └── GoldenCakeItems.java      # Cake block items
        ├── blocks/                       # Blocks
        │   └── GoldenCakes.java          # Golden cake blocks
        └── groups/                       # Creative tabs
            └── GoldenFoodsItemGroup.java # Golden Foods creative tab
```

## How Modules Work

### 1. Module Structure
Each module is a self-contained package under `modules/[modulename]/`:
- `[ModuleName]Module.java` - Initializes all components of this module
- `items/` - Item definitions
- `blocks/` - Block definitions
- `groups/` - Creative tab definitions
- Additional subpackages as needed (entities, recipes, etc.)

### 2. Module Initialization
The main mod class (`CharmNCraftsCulinaryDelights.java`) calls each module's `initialize()` method:

```java
@Override
public void onInitialize() {
    GoldenFoodsModule.initialize();  // Initialize Golden Foods module
    // Future modules added here:
    // AnotherModule.initialize();
}
```

### 3. Module Initializer Pattern
Each module has an initializer that registers all its components:

```java
public class GoldenFoodsModule {
    public static void initialize() {
        GoldenFoods.initialize();          // Register items
        GoldenCakes.initialize();          // Register blocks
        GoldenCakeItems.initialize();      // Register block items
        GoldenFoodsItemGroup.registerItemGroups();  // Register creative tabs
    }
}
```

## Current Modules

### Golden Foods Module (`modules/goldenfoods/`)

**Features:**
- 21 golden food items with enhanced effects
  - Fruits (5): sweet berries, glow berries, melon slice, chorus fruit, dried kelp
  - Vegetables (2): baked potato, beetroot
  - Meats (7): cooked beef, salmon, chicken, rabbit, porkchop, mutton, cod
  - Breads (3): bread, cookie, pumpkin pie
  - Soups (3): mushroom stew, beetroot soup, rabbit stew
  - Drinks (2): milk bucket, honey bottle
- 2 golden cake blocks (regular and enchanted)
- Custom "Golden Foods!" creative tab

**Effects:**
- All foods are always edible
- Regeneration effects (varying durations)
- Long-lasting Saturation effects (1-5 minutes)
- Enchanted golden cake grants Absorption

**Assets:**
- All assets remain in standard Minecraft structure
- Located under: `assets/charmncrafts-culinary-delights/`
- Items use standard naming (e.g., `golden_sweet_berries`)

## Adding New Modules

When merging a new mod into this amalgamation:

### Step 1: Create Module Package
```
src/main/java/charmncrafts/delights/modules/[newmodname]/
```

### Step 2: Create Module Structure
```
modules/[newmodname]/
├── [NewModName]Module.java   # Module initializer
├── items/                     # Item classes
├── blocks/                    # Block classes
├── groups/                    # Creative tab classes
└── [other]/                   # Other components as needed
```

### Step 3: Create Module Initializer
```java
package charmncrafts.delights.modules.[newmodname];

public class [NewModName]Module {
    public static void initialize() {
        CharmNCraftsCulinaryDelights.LOGGER.info("Initializing [NewModName] module...");

        // Register all module components
        [ModItems].initialize();
        [ModBlocks].initialize();
        // etc...

        CharmNCraftsCulinaryDelights.LOGGER.info("[NewModName] module loaded!");
    }
}
```

### Step 4: Add to Main Initializer
In `CharmNCraftsCulinaryDelights.java`:
```java
@Override
public void onInitialize() {
    GoldenFoodsModule.initialize();
    [NewModName]Module.initialize();  // Add this line
}
```

### Step 5: Asset Organization
- Keep assets in standard Minecraft structure
- Items naturally have unique names (e.g., `magic_corn`, `ender_berry`)
- If name collisions occur, add descriptive prefixes

## Benefits of This Structure

1. **Modularity**: Each merged mod is self-contained and easy to maintain
2. **Scalability**: Adding new mods is straightforward
3. **Clarity**: Easy to see which code belongs to which original mod
4. **Maintainability**: Updates to one module don't affect others
5. **Debugging**: Issues can be traced to specific modules
6. **Collaboration**: Multiple developers can work on different modules

## Asset Organization

Assets follow standard Minecraft conventions:
- `assets/charmncrafts-culinary-delights/` - Textures, models, lang files
- `data/charmncrafts-culinary-delights/` - Recipes, loot tables, tags

Assets are NOT separated by module because:
1. Minecraft's resource loader expects a flat structure
2. The mod namespace provides sufficient separation
3. Item names are naturally unique between modules
4. Reduces complexity and maintains Minecraft conventions

## Notes

- The build system remains unchanged (standard Fabric Gradle setup)
- Mixins can be global or module-specific as needed
- Each module should be documented with its features and effects
- When possible, preserve attribution to original mod authors in comments
