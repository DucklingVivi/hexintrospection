package io.duckling.hexintrospection.casting.patterns.math

import at.petrak.hexcasting.api.spell.ConstMediaAction
import at.petrak.hexcasting.api.spell.asActionResult
import at.petrak.hexcasting.api.spell.casting.CastingContext
import at.petrak.hexcasting.api.spell.getDouble
import at.petrak.hexcasting.api.spell.iota.Iota
import io.duckling.hexintrospection.DynamicWorld
import io.duckling.hexintrospection.DynamicWorldManager
import io.duckling.hexintrospection.HexIntrospection
import io.duckling.hexintrospection.HexIntrospectionSaveManager
import io.duckling.hexintrospection.network.DimensionalSyncPacket
import io.duckling.hexintrospection.network.MindSpherePacket
import kotlin.math.sign

class OpSignum : ConstMediaAction {
    /**
     * The number of arguments from the stack that this action requires.
     */
    override val argc = 1

    /**
     * The method called when this Action is actually executed. Accepts the [args]
     * that were on the stack (there will be [argc] of them), and the [ctx],
     * which contains things like references to the caster, the ServerLevel,
     * methods to determine whether locations and entities are in ambit, etc.
     * Returns the list of iota that should be added back to the stack as the
     * result of this action executing.
     */
    override fun execute(args: List<Iota>, ctx: CastingContext): List<Iota> {
        val uuid = ctx.caster.uuid;
        val worldhandle = DynamicWorldManager.get(ctx.world.server).getOrOpenDynamicWorld(HexIntrospection.id(uuid.toString()));
        val players = worldhandle.asWorld().players;
        val x = args.getDouble(0);

        val v = HexIntrospectionSaveManager.get(ctx.world.server).getMindSphereData(ctx.caster.uuid)
        v.fogdistance = x.toFloat();

        val packet = MindSpherePacket.syncAll(v)

        packet.sendToPlayers(players);


        return sign(0.0).asActionResult;
    }
}