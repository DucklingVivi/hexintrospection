package io.duckling.hexintrospection.casting.iota;

import at.petrak.hexcasting.api.spell.iota.Iota;
import at.petrak.hexcasting.api.spell.iota.IotaType;
import at.petrak.hexcasting.api.utils.HexUtils;
import io.duckling.hexintrospection.HexIntrospection;
import io.duckling.hexintrospection.registry.HexIntrospectionIotaTypeRegistry;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.text.TextColor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class EyeIota extends Iota {

    private record Payload(UUID dimension, int index) { }

    public EyeIota(Payload payload) {
        super(HexIntrospectionIotaTypeRegistry.EYE_TYPE, payload);
    }

    public EyeIota(UUID dimension, int index) {
        this(new Payload(dimension, index));
    }

    @Override
    public boolean isTruthy() {
        return true;
    }

    @Override
    protected boolean toleratesOther(Iota that) {
        return typesMatch(this, that) && that instanceof EyeIota gthat && gthat.getDimension().equals(getDimension()) && gthat.getIndex() == getIndex();
    }

    public @Nullable UUID getDimension() {
        return ((Payload)this.payload).dimension;
    }

    public @Nullable int getIndex() {
        return ((Payload)this.payload).index;
    }



    @Override
    public @NotNull NbtElement serialize() {
        NbtCompound compound = new NbtCompound();
        compound.putUuid("dimension", getDimension());
        compound.putInt("index", getIndex());

        return compound;
    }



    public static final IotaType<EyeIota> TYPE = new IotaType<>() {
        @Nullable
        @Override
        public EyeIota deserialize(NbtElement tag, ServerWorld world) throws IllegalArgumentException {

            NbtCompound compound = HexUtils.downcast(tag, NbtCompound.TYPE);
            UUID dimension = compound.getUuid("dimension");
            int index = compound.getInt("index");


            return new EyeIota(new Payload(dimension, index));
        }

        @Override
        public Text display(NbtElement tag) {
            return Text.of("Inner Eye").getWithStyle(Style.EMPTY.withColor(color())).get(0);
        }


        @Override
        public int color() {
            return 0xff_5555FF;
        }
    };
}
