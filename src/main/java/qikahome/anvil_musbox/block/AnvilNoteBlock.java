package qikahome.anvil_musbox.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class AnvilNoteBlock extends ExtendNoteBlock {
    public AnvilNoteBlock(Properties properties) {
        super(properties);
    }

    public static final TagKey<Block> ANVILS_TAG = TagKey.create(Registries.BLOCK,
            ResourceLocation.fromNamespaceAndPath("minecraft", "anvil"));

    @Override
    protected void playSound(Level level, BlockPos pos, float pitch) {
        level.playSound(null, pos, SoundEvents.ANVIL_PLACE, SoundSource.RECORDS, 1.0F, pitch);
    }

    @Override
    public Boolean blockMatches(BlockState below) {
        return below.is(ANVILS_TAG);
    }

    @Override
    public String getInstrumentName() {
        return "block.minecraft.anvil";
    }
}
