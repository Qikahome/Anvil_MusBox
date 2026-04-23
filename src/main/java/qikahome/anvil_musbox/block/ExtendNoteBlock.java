package qikahome.anvil_musbox.block;

import javax.annotation.Nullable;

import org.checkerframework.checker.nullness.qual.NonNull;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.NoteBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;

public abstract class ExtendNoteBlock extends NoteBlock {
    public ExtendNoteBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected void playNote(@Nullable Entity entity, BlockState state, Level level, BlockPos pos) {
        if (level.getBlockState(pos.above()).isAir()) {
            level.blockEvent(pos, this, 0, 0);
            level.gameEvent(entity, GameEvent.NOTE_BLOCK_PLAY, pos);
        }
    }

    @Override
    public boolean triggerEvent(BlockState state, Level level, BlockPos pos, int id, int data) {
        int note = state.getValue(NOTE);
        float pitch = (float) Math.pow(2.0, (note - 12) / 12.0);

        // Add note particles
        level.addParticle(ParticleTypes.NOTE, pos.getX() + 0.5D, pos.getY() + 1.2D, pos.getZ() + 0.5D, note / 24.0D,
                0.0D, 0.0D);

        // Play custom sound
        playSound(level, pos, pitch);

        return true;
    }

    @Override
    public BlockState setInstrument(LevelReader level, BlockPos pos, BlockState state) {
        int note = state.getValue(NoteBlock.NOTE);
        boolean powered = state.getValue(NoteBlock.POWERED);
        BlockState newState = Blocks.NOTE_BLOCK.defaultBlockState().setValue(NoteBlock.NOTE, note)
                .setValue(NoteBlock.POWERED, powered);
        return super.setInstrument(level, pos, newState);
    }

    @Override
    public ItemStack getCloneItemStack(LevelReader p_304395_, BlockPos p_49824_, BlockState p_49825_) {
        return new ItemStack(Blocks.NOTE_BLOCK);
    }

    @Override
    public MutableComponent getName() {
        return Component.translatableWithFallback(super.getDescriptionId(),
                "{\"translate\":\"block.minecraft.note_block\"}");
    }

    /**
     * Play custom sound
     * 
     * @param pos   The position of the note block
     * @param pitch The pitch of the note
     */
    abstract protected void playSound(@NonNull Level level, @NonNull BlockPos pos, float pitch);

    /**
     * Check if the block matches the instrument
     * 
     * @param below The block below the note block
     * @return True if the block matches the instrument, False otherwise
     */
    abstract public Boolean blockMatches(@NonNull BlockState below);

    /**
     * Get the name of the instrument
     * 
     * @return The name of the instrument
     */
    @NonNull
    abstract public String getInstrumentName();
}
