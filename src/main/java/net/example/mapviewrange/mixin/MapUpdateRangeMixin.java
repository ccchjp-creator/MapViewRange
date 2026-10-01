package net.example.mapviewrange.mixin;

import net.example.mapviewrange.MapViewRangeMod;
import net.minecraft.world.item.MapItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

/**
 * vanillaの MapItem#update(Level, Entity, MapItemSavedData) 内にある
 *   int radius = 128 / scale;
 * という式の「128」だけを狙い撃ちして拡大する。
 *
 * このメソッド内には他にも128という定数が出てくる(地図のマス数を表す
 * imgX < 128 / imgY < 128 の判定など)が、それらは地図自体のサイズなので
 * 変更してはいけない。ソースコード上、"128 / scale" が最初に出てくる128の
 * リテラルであることを確認済みなので、ordinal = 0 (最初の出現)だけを狙う。
 */
@Mixin(MapItem.class)
public abstract class MapUpdateRangeMixin {

    @ModifyConstant(
            method = "update",
            constant = @Constant(intValue = 128, ordinal = 0)
    )
    private int mapviewrange$expandUpdateRadius(int original) {
        double multiplier = MapViewRangeMod.getRadiusMultiplier();
        return (int) Math.round(original * multiplier);
    }
}
