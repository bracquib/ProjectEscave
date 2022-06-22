#!/usr/bin/env bash

declare -A hue
hue[vert]=50,-20
hue[bleu]=180,-50,2
hue[rouge]=-80,-20,5
hue[violet]=-110,-20

for color in "${!hue[@]}"; do
    if [ ! -d $color ]; then
        mkdir $color 
    fi

    for img in jaune/*.png; do
        echo Generation de $(basename $img .png) pour $color
        dest=$color/$(basename $img)
        hue-changer $img "${hue[$color]}" $dest
    done
done
