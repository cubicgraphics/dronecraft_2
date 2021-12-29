package net.cubic.dronecraft_2.dronecraft_2.screen;
import com.google.common.eventbus.Subscribe;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.cubic.dronecraft_2.dronecraft_2.ModSettings;
import net.cubic.dronecraft_2.dronecraft_2.dronecraft_2Main;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.gui.widget.list.OptionsRowList;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.SliderPercentageOption;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;



public final class ConfigScreen extends Screen {
    /** Distance from top of the screen to this GUI's title */
    private static final int TITLE_HEIGHT = 8;

    /** Distance from top of the screen to the options row list's top */
    private static final int OPTIONS_LIST_TOP_HEIGHT = 24;
    /** Distance from bottom of the screen to the options row list's bottom */
    private static final int OPTIONS_LIST_BOTTOM_OFFSET = 32;
    /** Height of each item in the options row list */
    private static final int OPTIONS_LIST_ITEM_HEIGHT = 25;

    /** Width of a button */
    private static final int BUTTON_WIDTH = 200;
    /** Height of a button */
    private static final int BUTTON_HEIGHT = 20;
    /** Distance from bottom of the screen to the "Done" button's top */
    private static final int DONE_BUTTON_TOP_OFFSET = 26;

    /** List of options rows shown on the screen */
    // Not a final field because this cannot be initialized in the constructor,
    // as explained below
    private OptionsRowList optionsRowList;
    private final Screen parentScreen;


    public ConfigScreen(Screen parentScreen) {
        // Use the super class' constructor to set the screen's title
        super(ITextComponent.getTextComponentOrEmpty("Dronecraft 2 mod config"));
        this.parentScreen = parentScreen;
    }



    @Override
    protected void init() {
        // Create the options row list
        // It must be created in this method instead of in the constructor,
        // or it will not be displayed properly
        this.optionsRowList = new OptionsRowList(
                this.minecraft, this.width, this.height,
                OPTIONS_LIST_TOP_HEIGHT,
                this.height - OPTIONS_LIST_BOTTOM_OFFSET,
                OPTIONS_LIST_ITEM_HEIGHT
        );

        // Add the options row list as this screen's child
        // If this is not done, users cannot click on items in the list
        this.children.add(this.optionsRowList);

        // Add the "Done" button
        this.addButton(new Button(
                (this.width - BUTTON_WIDTH) / 2,
                this.height - DONE_BUTTON_TOP_OFFSET,
                BUTTON_WIDTH, BUTTON_HEIGHT,
                // Text shown on the button
                new TranslationTextComponent("gui.done"),
                // Action performed when the button is pressed
                button -> this.onClose()

        ));

        this.optionsRowList.addOption(new SliderPercentageOption(
                "screen.dronecraft_2.background.red",
                0.0,
                255,
                1,
                unused -> (double) ModSettings.BackgroundR.get(), //gets var
                (unused, newValue) -> ModSettings.BackgroundR.set(newValue.intValue()), //sets var
                // BiFunction that returns a string text component
                // in format "<name>: <value>"
                (gs, option) -> new StringTextComponent(
                        // Use I18n.get(String) to get a translation key's value
                        I18n.format("screen.dronecraft_2.background.red")
                                + ": "
                                + (int) option.get(gs)
                )
        ));
        this.optionsRowList.addOption(new SliderPercentageOption(
                "screen.dronecraft_2.background.green",
                0.0,
                255,
                1,
                unused -> (double) ModSettings.BackgroundG.get(), //gets var
                (unused, newValue) -> ModSettings.BackgroundG.set(newValue.intValue()), //sets var
                // BiFunction that returns a string text component
                // in format "<name>: <value>"
                (gs, option) -> new StringTextComponent(
                        // Use I18n.get(String) to get a translation key's value
                        I18n.format("screen.dronecraft_2.background.green")
                                + ": "
                                + (int) option.get(gs)
                )
        ));
        this.optionsRowList.addOption(new SliderPercentageOption(
                "screen.dronecraft_2.background.blue",
                0.0,
                255,
                1,
                unused -> (double) ModSettings.BackgroundB.get(), //gets var
                (unused, newValue) -> ModSettings.BackgroundB.set(newValue.intValue()), //sets var
                // BiFunction that returns a string text component
                // in format "<name>: <value>"
                (gs, option) -> new StringTextComponent(
                        // Use I18n.get(String) to get a translation key's value
                        I18n.format("screen.dronecraft_2.background.blue")
                                + ": "
                                + (int) option.get(gs)
                )
        ));

        this.optionsRowList.addOption(new SliderPercentageOption(
                "screen.dronecraft_2.background.red",
                0.0,
                255,
                1,
                unused -> (double) ModSettings.ForegroundR.get(), //gets var
                (unused, newValue) -> ModSettings.ForegroundR.set(newValue.intValue()), //sets var
                // BiFunction that returns a string text component
                // in format "<name>: <value>"
                (gs, option) -> new StringTextComponent(
                        // Use I18n.get(String) to get a translation key's value
                        I18n.format("screen.dronecraft_2.foreground.red")
                                + ": "
                                + (int) option.get(gs)
                )
        ));
        this.optionsRowList.addOption(new SliderPercentageOption(
                "screen.dronecraft_2.foreground.green",
                0.0,
                255,
                1,
                unused -> (double) ModSettings.ForegroundG.get(), //gets var
                (unused, newValue) -> ModSettings.ForegroundG.set(newValue.intValue()), //sets var
                // BiFunction that returns a string text component
                // in format "<name>: <value>"
                (gs, option) -> new StringTextComponent(
                        // Use I18n.get(String) to get a translation key's value
                        I18n.format("screen.dronecraft_2.foreground.green")
                                + ": "
                                + (int) option.get(gs)
                )
        ));
        this.optionsRowList.addOption(new SliderPercentageOption(
                "screen.dronecraft_2.foreground.blue",
                0.0,
                255,
                1,
                unused -> (double) ModSettings.ForegroundB.get(), //gets var
                (unused, newValue) -> ModSettings.ForegroundB.set(newValue.intValue()), //sets var
                // BiFunction that returns a string text component
                // in format "<name>: <value>"
                (gs, option) -> new StringTextComponent(
                        // Use I18n.get(String) to get a translation key's value
                        I18n.format("screen.dronecraft_2.foreground.blue")
                                + ": "
                                + (int) option.get(gs)
                )
        ));
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {

        // First draw the background of the screen
        this.renderBackground(matrixStack);
        // Draw the title
        drawCenteredString(matrixStack, this.font, this.title.getString(),
                this.width / 2, TITLE_HEIGHT, 0xFFFFFF);

        this.optionsRowList.render(matrixStack, mouseX, mouseY, partialTicks);
        drawCenteredString(matrixStack, this.font, this.title.getString(),
                this.width / 2, TITLE_HEIGHT, 0xFFFFFF);

        RenderSystem.color4f(ModSettings.FBackgroundR(),ModSettings.FBackgroundG(),ModSettings.FBackgroundB(),ModSettings.FBackgroundA());
        this.minecraft.getTextureManager().bindTexture(new ResourceLocation("minecraft", "textures/gui/accessibility.png"));
        this.blit(matrixStack,0,0,0,0,20,40);
        RenderSystem.color4f(ModSettings.FForegroundR(),ModSettings.FForegroundG(),ModSettings.FForegroundB(),ModSettings.FForegroundA());
        this.blit(matrixStack,0,50,0,0,20,40);

        // Call the super class' method to complete rendering
        super.render(matrixStack, mouseX, mouseY, partialTicks);
    }


    @Override
    public void onClose(){
        // Save mod configuration
        //modSettings.save();
        this.minecraft.currentScreen = parentScreen;  //setScreen(parentScreen);
    }
}
