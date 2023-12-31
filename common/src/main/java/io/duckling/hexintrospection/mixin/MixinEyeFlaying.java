package io.duckling.hexintrospection.mixin;

import at.petrak.hexcasting.api.spell.ParticleSpray;
import at.petrak.hexcasting.api.spell.RenderedSpell;
import at.petrak.hexcasting.api.spell.casting.CastingContext;
import at.petrak.hexcasting.api.spell.iota.EntityIota;
import at.petrak.hexcasting.api.spell.iota.Iota;
import at.petrak.hexcasting.api.spell.iota.Vec3Iota;
import at.petrak.hexcasting.api.spell.mishaps.MishapBadBrainsweep;
import at.petrak.hexcasting.api.spell.mishaps.MishapLocationInWrongDimension;
import at.petrak.hexcasting.api.spell.mishaps.MishapShameOnYou;
import at.petrak.hexcasting.common.casting.operators.spells.great.OpBrainsweep;
import io.duckling.hexintrospection.FlayPlayer;
import io.duckling.hexintrospection.HexIntrospection;
import kotlin.Triple;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Mixin(OpBrainsweep.class)
public class MixinEyeFlaying {

    @Inject(method = "execute", at = @At("HEAD"), cancellable = true, remap = false)
    private void injectExecute(List<? extends Iota> args, CastingContext ctx, CallbackInfoReturnable<Triple<RenderedSpell, Integer, List<ParticleSpray>>> cir) throws MishapShameOnYou, MishapLocationInWrongDimension {
        Iota sacrifice = args.get(0);
        Iota position = args.get(1);

        if(sacrifice instanceof EntityIota eIota && eIota.getEntity().isPlayer()) {
            ServerPlayerEntity player = (ServerPlayerEntity) eIota.getEntity();
            if(!Objects.equals(ctx.getCaster().getUuid(),player.getUuid())) {
                throw new MishapShameOnYou();
            }
            if(!Objects.equals(ctx.getWorld().getRegistryKey().getValue().getPath(), player.getUuid().toString())){
                throw new MishapLocationInWrongDimension(new Identifier("hexintrospection", "brainsweep"));
            }
            if(position instanceof Vec3Iota vec3Iota) {
                Vec3d pos = vec3Iota.getVec3();
                BlockPos bpos = new BlockPos(pos);
                int cost = 0;
                List<ParticleSpray> particleSprayList = List.of(ParticleSpray.cloud(player.getPos(), 1.0, 100),ParticleSpray.burst(Vec3d.ofCenter(bpos), 0.3, 100));
                cir.setReturnValue(new Triple<>(new FlayPlayer(player, bpos), cost, particleSprayList));

            }else{
                throw new MishapShameOnYou();
            }
        }
    }

}

