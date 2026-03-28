package qikahome.anvil_musbox.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.NoteBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import qikahome.anvil_musbox.AnvilMusBoxMod;

@Mixin(NoteBlock.class)
public class NoteBlockMixin {

    @Inject(method = "setInstrument", at = @At("HEAD"), cancellable = true)
    private void onSetInstrument(LevelAccessor level, BlockPos pos, BlockState state,
            CallbackInfoReturnable<BlockState> cir) {
        NoteBlockInstrument noteblockinstrument = level.getBlockState(pos.above()).instrument();
        if (noteblockinstrument.worksAboveNoteBlock()) {
            return;
        }
        BlockState belowState = level.getBlockState(pos.below());

        // 判断下方方块是否在 minecraft:anvil 标签中
        if (belowState.is(AnvilMusBoxMod.ANVILS_TAG)) {
            // 如果是铁砧类方块，返回 AnvilNoteBlock 的 BlockState，保留原始属性
            int note = state.getValue(NoteBlock.NOTE);
            boolean powered = state.getValue(NoteBlock.POWERED);
            BlockState anvilNoteState = AnvilMusBoxMod.ANVIL_NOTE_BLOCK.get().defaultBlockState()
                    .setValue(NoteBlock.NOTE, note)
                    .setValue(NoteBlock.POWERED, powered);
            cir.setReturnValue(anvilNoteState);
            cir.cancel();
        }
    }
}
