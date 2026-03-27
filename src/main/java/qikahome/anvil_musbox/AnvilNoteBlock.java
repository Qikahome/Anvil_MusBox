package qikahome.anvil_musbox;

import javax.annotation.Nullable;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.NoteBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;

public class AnvilNoteBlock extends NoteBlock {
    public AnvilNoteBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected void playNote(@Nullable Entity entity, BlockState state, Level level, BlockPos pos) {
        if (level.getBlockState(pos.above()).isAir()) {
            int note = state.getValue(NOTE);
            float pitch = (float) Math.pow(2.0, (note - 12) / 12.0);

            level.playSound(null, pos, SoundEvents.ANVIL_PLACE, SoundSource.BLOCKS, 1.0F, pitch);
            level.gameEvent(entity, GameEvent.NOTE_BLOCK_PLAY, pos);
        }
    }

    @Override
    public BlockState setInstrument(LevelAccessor level, BlockPos pos, BlockState state) {
        int note = state.getValue(NoteBlock.NOTE);
        boolean powered = state.getValue(NoteBlock.POWERED);
        BlockState newState = Blocks.NOTE_BLOCK.defaultBlockState().setValue(NoteBlock.NOTE, note)
                .setValue(NoteBlock.POWERED, powered);
        return super.setInstrument(level, pos, newState);
    }

    @Override
    public ItemStack getCloneItemStack(BlockGetter level, BlockPos pos, BlockState state) {
        return new ItemStack(Blocks.NOTE_BLOCK);
    }

    @Override
    public MutableComponent getName() {
        return Component.translatableWithFallback(super.getDescriptionId(),
                "{\"translate\":\"block.minecraft.note_block\"}");
    }
}
