package qikahome.anvil_musbox;

import com.mojang.logging.LogUtils;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.slf4j.Logger;

@Mod(AnvilMusBoxMod.MOD_ID)
public class AnvilMusBoxMod {
    public static final String MOD_ID = "anvil_musbox";
    private static final Logger LOGGER = LogUtils.getLogger();

    public static final TagKey<Block> ANVILS_TAG = TagKey.create(Registries.BLOCK,
            new ResourceLocation("minecraft", "anvil"));

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MOD_ID);

    public static final RegistryObject<Block> ANVIL_NOTE_BLOCK = BLOCKS.register("anvil_note_block",
            () -> new AnvilNoteBlock(BlockBehaviour.Properties.copy(Blocks.NOTE_BLOCK)));

    public AnvilMusBoxMod() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        BLOCKS.register(modEventBus);
        MinecraftForge.EVENT_BUS.register(this);
    }

}
