package ak.foodinfo;

import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.translation.I18n;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.text.DecimalFormat;
import java.util.List;

@Mod(modid = FoodInfo.MOD_ID,
        name = FoodInfo.MOD_NAME,
        version = FoodInfo.MOD_VERSION,
        dependencies = FoodInfo.MOD_DEPENDENCIES,
        useMetadata = true,
        acceptedMinecraftVersions = FoodInfo.MOD_MC_VERSION)
public class FoodInfo {
    public static final String MOD_ID = "foodinfo";
    public static final String MOD_NAME = "FoodInfo";
    public static final String MOD_VERSION = "@VERSION@";
    public static final String MOD_DEPENDENCIES = "required-after:forge";
    public static final String MOD_MC_VERSION = "[1.12,1.99.99]";
    private static final String KEY_FOOD_POINT = "foodinfo.info.0";
    private static final String KEY_HIDDEN_FOOD_POINT = "foodinfo.info.1";
    private static final String KEY_WOLF_LIKE = "foodinfo.info.2";
    private static final DecimalFormat DECIMALFORMAT = new DecimalFormat("#.#");

    @Mod.EventHandler
    public void load(FMLInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void addFoodInfo(ItemTooltipEvent event) {
        ItemStack itemStack = event.getItemStack();
        if (itemStack.getItem() instanceof ItemFood) {
            List<String> list = event.getToolTip();
            int foodPoint = ((ItemFood) itemStack.getItem()).getHealAmount(itemStack);
            float foodSaturationModifier = ((ItemFood) itemStack.getItem()).getSaturationModifier(itemStack);
            float hiddenFoodPoint = foodPoint * foodSaturationModifier;
            String string = I18n.translateToLocalFormatted(KEY_FOOD_POINT, DECIMALFORMAT.format((float) foodPoint / 2));
            String stringHidden = I18n.translateToLocalFormatted(KEY_HIDDEN_FOOD_POINT, DECIMALFORMAT.format(hiddenFoodPoint));
            list.add(string);
            list.add(stringHidden);
            if (((ItemFood) itemStack.getItem()).isWolfsFavoriteMeat()) {
                String stringWolfLike = I18n.translateToLocal(KEY_WOLF_LIKE);
                list.add(stringWolfLike);
            }
        }
    }
}