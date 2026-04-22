package qikahome.anvil_musbox.mixin;

import net.minecraft.world.level.block.state.BlockState;
import qikahome.anvil_musbox.block.ExtendNoteBlock;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import snownee.jade.addon.vanilla.NoteBlockProvider;
import snownee.jade.api.BlockAccessor;
import snownee.jade.api.ITooltip;
import snownee.jade.api.config.IPluginConfig;

@Mixin(NoteBlockProvider.class)
public class NoteBlockProviderMixin {
    
    @ModifyVariable(
        method = "appendTooltip",
        at = @At(value = "STORE"),
        ordinal = 0,
        remap = false
    )
    public String modifyKey(String key, ITooltip tooltip, BlockAccessor accessor, IPluginConfig config) {
        BlockState state = accessor.getBlockState();
        if (state.getBlock() instanceof ExtendNoteBlock extend) {
            return extend.getInstrumentName();
        }
        return key;
    }
}
