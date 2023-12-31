package io.duckling.hexintrospection;

import at.petrak.hexcasting.api.spell.RenderedSpell;
import at.petrak.hexcasting.api.spell.casting.CastingContext;
import io.duckling.hexintrospection.network.MindSpherePacket;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.NotNull;

public class FlayPlayer implements RenderedSpell {

    private ServerPlayerEntity player;
    private BlockPos bpos;
    public FlayPlayer(ServerPlayerEntity player, BlockPos bpos) {
        this.player = player;
        this.bpos = bpos;
    }
    @Override
    public void cast(@NotNull CastingContext castingContext) {

        MindSphereEyeData eyeData = new MindSphereEyeData();
        MindSphereData data = HexIntrospectionSaveManager.get(castingContext.getCaster().server).getMindSphereData(player.getUuid());
        eyeData.position = bpos.withY(30);
        MindSpherePacket packet = data.addEyeData(eyeData);
        packet.sendToPlayers(castingContext.getWorld().getPlayers());

    }
}