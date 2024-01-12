package io.duckling.hexintrospection.blocks;

import io.duckling.hexintrospection.EyeInfusion;
import io.duckling.hexintrospection.registry.HexIntrospectionBlockEntityRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class BlockEntityEyeInfuse extends BlockEntity {
    public BlockEntityEyeInfuse(BlockPos pos, BlockState state) {
        //TODO ADD TYPE
        super(HexIntrospectionBlockEntityRegistry.EYE_INFUSE.get(), pos, state);
    }

    private EyeInfusion infusion = EyeInfusion.NONE;

    @Override
    public void markRemoved() {
        super.markRemoved();
    }

    public EyeInfusion getInfusion(){
        return infusion;
    }
    public void setInfusion(EyeInfusion infusion) {
        this.infusion = infusion;
        markDirty();
    }

    public void onPlaced(NbtCompound datacompound) {
        NbtCompound infusioncompound = datacompound.getCompound("infusion");
        if(infusioncompound != null){
            EyeInfusion infusion = EyeInfusion.fromNBT(infusioncompound);
            setInfusion(infusion);
        }else{
            setInfusion(EyeInfusion.NONE);
        }
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        NbtCompound infusioncompound = nbt.getCompound("infusion");
        if(infusioncompound!=null){
            EyeInfusion infusion = EyeInfusion.fromNBT(infusioncompound);
            setInfusion(infusion);
        }
        super.readNbt(nbt);
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        if(!infusion.isNone()){
            //nbt.put("infusion", infusion.toNBT());
        }
        nbt.put("infusion", infusion.toNBT());
        super.writeNbt(nbt);
    }



    @Nullable
    @Override
    public Packet<ClientPlayPacketListener> toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    @Override
    public NbtCompound toInitialChunkDataNbt() {
        return createNbt();
    }
}
