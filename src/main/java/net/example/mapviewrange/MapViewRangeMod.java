package net.example.mapviewrange;

import com.mojang.brigadier.context.CommandContext;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;

import static com.mojang.brigadier.arguments.DoubleArgumentType.doubleArg;
import static com.mojang.brigadier.arguments.DoubleArgumentType.getDouble;
import static net.minecraft.commands.Commands.argument;
import static net.minecraft.commands.Commands.literal;

/**
 * 地図を持って移動した際に、周辺が塗られる(可視化される)範囲を広げるMOD。
 *
 * vanillaは MapItem#update(Level, Entity, MapItemSavedData) の中で、
 *   int radius = 128 / scale;
 * という式で「1回の更新でプレイヤー周辺を塗る半径」を決めている。
 * scale(拡大するほど大きくなる、1マスあたりのブロック数)が大きいほど
 * radiusが小さくなり、大きい地図ほど歩いても全然埋まらない、という体感になっている。
 *
 * このMODは mixin/MapUpdateRangeMixin.java で「128」の部分を
 * 設定した倍率(1.0〜4.0)倍にすることで、radius自体を広げている。
 * 倍率は config/mapviewrange.json か、/mapviewrange radius コマンドで変更できる。
 */
public class MapViewRangeMod implements ModInitializer {
    public static final String MOD_ID = "mapviewrange";

    private static ModConfig config;

    @Override
    public void onInitialize() {
        config = ModConfig.load();

        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) ->
                dispatcher.register(literal("mapviewrange")
                        .then(literal("radius")
                                .executes(MapViewRangeMod::onGet)
                                .then(argument("value", doubleArg(ModConfig.MIN_MULTIPLIER, ModConfig.MAX_MULTIPLIER))
                                        .executes(MapViewRangeMod::onSet)))));
    }

    private static int onGet(CommandContext<CommandSourceStack> context) {
        context.getSource().sendSuccess(
                () -> Component.translatable("command.mapviewrange.radius.get", config.radiusMultiplier),
                false
        );
        return 1;
    }

    private static int onSet(CommandContext<CommandSourceStack> context) {
        double value = getDouble(context, "value");
        config.radiusMultiplier = ModConfig.clamp(value);
        config.save();
        context.getSource().sendSuccess(
                () -> Component.translatable("command.mapviewrange.radius.set", config.radiusMultiplier),
                true
        );
        return 1;
    }

    /** 設定本体へのアクセサ(Mod Menuの設定画面からも使う)。 */
    public static ModConfig getConfig() {
        if (config == null) {
            config = ModConfig.load();
        }
        return config;
    }

    /** Mixin側から現在の倍率を読むためのアクセサ。 */
    public static double getRadiusMultiplier() {
        return getConfig().radiusMultiplier;
    }
}
