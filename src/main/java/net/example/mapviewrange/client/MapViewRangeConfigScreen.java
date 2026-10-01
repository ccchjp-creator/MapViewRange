package net.example.mapviewrange.client;

import net.example.mapviewrange.MapViewRangeMod;
import net.example.mapviewrange.ModConfig;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.AbstractSliderButton;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

/**
 * Mod Menu から開く設定画面。倍率(1.0〜4.0)をスライダーで0.1刻みに変更できる。
 * 変更は即時に反映され、画面を閉じるときに config/mapviewrange.json へ保存する。
 */
public class MapViewRangeConfigScreen extends Screen {
    private static final int WIDGET_WIDTH = 200;
    private static final int WIDGET_HEIGHT = 20;

    private final Screen parent;
    private MultiplierSlider slider;

    public MapViewRangeConfigScreen(Screen parent) {
        super(Component.translatable("config.mapviewrange.title"));
        this.parent = parent;
    }

    @Override
    protected void init() {
        int centerX = this.width / 2;
        int x = centerX - WIDGET_WIDTH / 2;
        int y = this.height / 4;

        this.slider = new MultiplierSlider(x, y, WIDGET_WIDTH, WIDGET_HEIGHT,
                MapViewRangeMod.getConfig().radiusMultiplier);
        this.addRenderableWidget(this.slider);

        this.addRenderableWidget(Button.builder(
                        Component.translatable("config.mapviewrange.reset", ModConfig.DEFAULT_MULTIPLIER),
                        button -> this.slider.setMultiplier(ModConfig.DEFAULT_MULTIPLIER))
                .bounds(x, y + 28, WIDGET_WIDTH, WIDGET_HEIGHT)
                .build());

        this.addRenderableWidget(Button.builder(
                        Component.translatable("gui.done"),
                        button -> this.onClose())
                .bounds(x, this.height - 40, WIDGET_WIDTH, WIDGET_HEIGHT)
                .build());
    }

    @Override
    public void extractRenderState(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float delta) {
        super.extractRenderState(graphics, mouseX, mouseY, delta);
        graphics.centeredText(this.font, this.title, this.width / 2, this.height / 4 - 24, 0xFFFFFFFF);
    }

    @Override
    public void onClose() {
        MapViewRangeMod.getConfig().save();
        this.minecraft.gui.setScreen(this.parent);
    }

    /** 1.0〜4.0を0.0〜1.0に対応づけた、0.1刻みのスライダー。 */
    private static class MultiplierSlider extends AbstractSliderButton {
        private static final double RANGE = ModConfig.MAX_MULTIPLIER - ModConfig.MIN_MULTIPLIER;

        MultiplierSlider(int x, int y, int width, int height, double multiplier) {
            super(x, y, width, height, Component.empty(), toSlider(multiplier));
            this.updateMessage();
        }

        private static double toSlider(double multiplier) {
            return (ModConfig.clamp(multiplier) - ModConfig.MIN_MULTIPLIER) / RANGE;
        }

        private static double snap(double multiplier) {
            return ModConfig.clamp(Math.round(multiplier * 10.0) / 10.0);
        }

        private double currentMultiplier() {
            return snap(ModConfig.MIN_MULTIPLIER + this.value * RANGE);
        }

        void setMultiplier(double multiplier) {
            this.value = toSlider(snap(multiplier));
            this.applyValue();
            this.updateMessage();
        }

        @Override
        protected void updateMessage() {
            this.setMessage(Component.translatable(
                    "config.mapviewrange.radius", String.format("%.1f", currentMultiplier())));
        }

        @Override
        protected void applyValue() {
            MapViewRangeMod.getConfig().radiusMultiplier = currentMultiplier();
        }
    }
}
