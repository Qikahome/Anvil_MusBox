package qikahome.anvil_musbox;

import com.mojang.logging.LogUtils;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;

import qikahome.anvil_musbox.block.AnvilNoteBlock;
import qikahome.anvil_musbox.block.ExtendNoteBlock;

@Mod(AnvilMusBoxMod.MOD_ID)
public class AnvilMusBoxMod {
    public static final String MOD_ID = "anvil_musbox";
    private static final Logger LOGGER = LogUtils.getLogger();

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(Registries.BLOCK, MOD_ID);

    public static final DeferredHolder<Block, Block> ANVIL_NOTE_BLOCK = BLOCKS.register("anvil_note_block",
            () -> new AnvilNoteBlock(BlockBehaviour.Properties.ofLegacyCopy(Blocks.NOTE_BLOCK)));
    
    public static final List<ExtendNoteBlock> INSTRUMENTS = new ArrayList<>();
    
    public AnvilMusBoxMod() {
        IEventBus modEventBus = ModLoadingContext.get().getActiveContainer().getEventBus();
        BLOCKS.register(modEventBus);
        modEventBus.addListener(this::commonSetup);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        // Perform setup actions here
        LOGGER.info("Common setup started");

        INSTRUMENTS.add((ExtendNoteBlock) ANVIL_NOTE_BLOCK.get());
        // Initialize instruments or other setup tasks
        for (ExtendNoteBlock instrument : INSTRUMENTS) {
            LOGGER.info("Registered instrument: {}", instrument.getDescriptionId());
        }
        
        LOGGER.info("Common setup completed");
    }
}
