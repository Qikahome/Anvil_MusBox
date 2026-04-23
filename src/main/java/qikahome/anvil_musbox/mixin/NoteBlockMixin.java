package qikahome.anvil_musbox.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.NoteBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import qikahome.anvil_musbox.AnvilMusBoxMod;
import qikahome.anvil_musbox.block.ExtendNoteBlock;

@Mixin(NoteBlock.class)
public class NoteBlockMixin {

    @Inject(method = "setInstrument", at = @At("HEAD"), cancellable = true, remap=true)
    private void onSetInstrument(LevelReader level, BlockPos pos, BlockState state,
            CallbackInfoReturnable<BlockState> cir) {
        NoteBlockInstrument noteblockinstrument = level.getBlockState(pos.above()).instrument();
        if (noteblockinstrument.worksAboveNoteBlock()) {
            return;
        }
        BlockState belowState = level.getBlockState(pos.below());

        for (ExtendNoteBlock extend : AnvilMusBoxMod.INSTRUMENTS) {
            if (extend.blockMatches(belowState)) {
                int note = state.getValue(NoteBlock.NOTE);
                boolean powered = state.getValue(NoteBlock.POWERED);
                BlockState anvilNoteState = extend.defaultBlockState()
                        .setValue(NoteBlock.NOTE, note)
                        .setValue(NoteBlock.POWERED, powered);
                cir.setReturnValue(anvilNoteState);
                cir.cancel();
            }
        }
    }
}
