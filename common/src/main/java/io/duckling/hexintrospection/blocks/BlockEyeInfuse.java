package io.duckling.hexintrospection.blocks;

import io.duckling.hexintrospection.HexIntrospection;
import io.duckling.hexintrospection.registry.HexIntrospectionBlockEntityRegistry;
import io.duckling.hexintrospection.registry.HexIntrospectionBlockRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.event.listener.GameEventListener;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class BlockEyeInfuse extends Block implements BlockEntityProvider {
    public BlockEyeInfuse(Settings settings) {
        super(settings);
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new BlockEntityEyeInfuse(pos, state);
    }


    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
        NbtCompound compound = itemStack.getNbt();
        Optional<BlockEntityEyeInfuse> BEopt = world.getBlockEntity(pos, HexIntrospectionBlockEntityRegistry.EYE_INFUSE.get());
        if(BEopt.isPresent() && compound != null){
            BlockEntityEyeInfuse BE = BEopt.get();
            NbtCompound datacompound = compound.getCompound("infusionData");

            BE.onPlaced(datacompound);
        }

        super.onPlaced(world, pos, state, placer, itemStack);
    }


    @Override
    public ItemStack getPickStack(BlockView world, BlockPos pos, BlockState state) {

        ItemStack stack = super.getPickStack(world, pos, state);
        Optional<BlockEntityEyeInfuse> BEopt = world.getBlockEntity(pos, HexIntrospectionBlockEntityRegistry.EYE_INFUSE.get());
        if(BEopt.isPresent()) {
            NbtCompound infusionData = new NbtCompound();
            infusionData.put("infusion", BEopt.get().getInfusion().toNBT());
            stack.setSubNbt("infusionData", infusionData);
        }



        return stack;
    }


    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {

        return BlockEntityProvider.super.getTicker(world, state, type);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> GameEventListener getGameEventListener(ServerWorld world, T blockEntity) {
        return BlockEntityProvider.super.getGameEventListener(world, blockEntity);
    }
}
