package ak.foodinfo;

import java.text.DecimalFormat;
import java.util.List;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod(FoodInfo.MOD_ID)
public class FoodInfo {
    public static final String MOD_ID = "foodinfo";
    private static final String KEY_FOOD_POINT = "foodinfo.info.0";
    private static final String KEY_HIDDEN_FOOD_POINT = "foodinfo.info.1";
    private static final String KEY_WOLF_LIKE = "foodinfo.info.2";
    private static final DecimalFormat DECIMALFORMAT = new DecimalFormat("#.#");

    public FoodInfo() {
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void addFoodInfo(ItemTooltipEvent event) {
        ItemStack itemStack = event.getItemStack();
        if (itemStack.getItem() instanceof ItemFood) {
            List<ITextComponent> list = event.getToolTip();
            int foodPoint = ((ItemFood) itemStack.getItem()).getHealAmount(itemStack);
            float foodSaturationModifier = ((ItemFood) itemStack.getItem()).getSaturationModifier(itemStack);
            float hiddenFoodPoint = foodPoint * foodSaturationModifier;
            String string = I18n
                .format(KEY_FOOD_POINT, DECIMALFORMAT.format((float) foodPoint / 2));
            String stringHidden = I18n.format(KEY_HIDDEN_FOOD_POINT, DECIMALFORMAT.format(hiddenFoodPoint));
            list.add(new TextComponentString(string));
            list.add(new TextComponentString(stringHidden));
            if (((ItemFood) itemStack.getItem()).isMeat()) {
                String stringWolfLike = I18n.format(KEY_WOLF_LIKE);
                list.add(new TextComponentString(stringWolfLike));
            }
        }
    }
}