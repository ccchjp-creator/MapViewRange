package net.example.mapviewrange.client;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;

/**
 * Mod Menu から「設定」ボタンを押したときに開く画面を登録する。
 * Mod Menu が入っていない環境ではこのクラスは読み込まれない。
 */
public class ModMenuIntegration implements ModMenuApi {

    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return MapViewRangeConfigScreen::new;
    }
}
