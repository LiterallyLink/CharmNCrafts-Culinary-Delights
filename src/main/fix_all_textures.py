#!/usr/bin/env python3
"""
Fix all texture references from casualness_delight to charmncraft
Run this from your mod root directory
"""

import os
import json
from pathlib import Path

def fix_models():
    model_dir = Path("src/main/resources/assets/charmncraft/models/block")
    
    if not model_dir.exists():
        print(f"ERROR: Directory not found: {model_dir}")
        return False
    
    fixed_count = 0
    for json_file in model_dir.glob("*.json"):
        with open(json_file, 'r', encoding='utf-8') as f:
            content = f.read()
        
        if 'casualness_delight' in content:
            # Replace in the content
            new_content = content.replace('casualness_delight:block', 'charmncraft:block')
            
            # Write back
            with open(json_file, 'w', encoding='utf-8') as f:
                f.write(new_content)
            
            print(f"✓ Fixed: {json_file.name}")
            fixed_count += 1
    
    print(f"\nDone! Fixed {fixed_count} model files.")
    return True

if __name__ == "__main__":
    fix_models()
