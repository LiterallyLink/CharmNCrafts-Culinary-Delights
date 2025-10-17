#!/bin/bash

BASE_DIR="/mnt/c/Users/Umi/Desktop/Programming/Java/CharmNCrafts-Culinary-Delights/src/main/resources/data/charmncraft/recipes"

# Move Golden Foods recipes
echo "Moving Golden Foods recipes..."
cd "$BASE_DIR" || exit 1
for file in golden_*.json; do
    if [ -f "$file" ]; then
        mv "$file" "golden_foods/"
        echo "Moved $file"
    fi
done

# Move Ender's Delight recipes
echo "Moving Ender's Delight recipes..."
for file in chorus_* crawling_sandwich.json crispy_skewer.json shulker_bowl.json strange_eclair.json twisted_cereal*.json uncanny_cookies.json; do
    if [ -f "$file" ]; then
        mv "$file" "enders_delight/"
        echo "Moved $file"
    fi
done

# Move block recipes
echo "Moving block recipes..."
if [ -f "endstone_stove.json" ]; then
    mv "endstone_stove.json" "blocks/"
    echo "Moved endstone_stove.json"
fi

# Move cooking recipes into enders_delight subfolder
echo "Organizing cooking recipes..."
if [ -d "cooking" ]; then
    mv "cooking" "enders_delight/"
    echo "Moved cooking folder to enders_delight"
fi

# Move cutting recipes into enders_delight subfolder
echo "Organizing cutting recipes..."
if [ -d "cutting" ]; then
    mv "cutting" "enders_delight/"
    echo "Moved cutting folder to enders_delight"
fi

echo "Recipe organization complete!"
