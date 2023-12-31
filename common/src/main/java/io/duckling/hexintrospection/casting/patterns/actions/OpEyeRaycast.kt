package io.duckling.hexintrospection.casting.patterns.actions

import at.petrak.hexcasting.api.spell.Action
import at.petrak.hexcasting.api.spell.ConstMediaAction
import at.petrak.hexcasting.api.spell.casting.CastingContext
import at.petrak.hexcasting.api.spell.getVec3
import at.petrak.hexcasting.api.spell.iota.Iota
import at.petrak.hexcasting.api.spell.iota.NullIota
import io.duckling.hexintrospection.HexIntrospection
import io.duckling.hexintrospection.HexIntrospectionSaveManager
import io.duckling.hexintrospection.casting.iota.EyeIota
import net.minecraft.util.math.Vec3d
import java.util.*

class OpEyeRaycast : ConstMediaAction {
    override val argc = 2;


    override fun execute(args: List<Iota>, ctx: CastingContext): List<Iota> {
        val origin = args.getVec3(0,argc)
        val look = args.getVec3(1,argc)
        val planeOrigin = Vec3d(0.0, 50.0, 0.0)
        val planeNormal = Vec3d(0.0, -1.0, 0.0).normalize()

        val intersection = linePlaneIntersection(origin, look, planeOrigin, planeNormal)

        val uuid = UUID.fromString(ctx.world.registryKey.value.path)

        val data = HexIntrospectionSaveManager.get(ctx.world.server).getMindSphereData(uuid)


        for (datamember in data.eyeData.entries) {
            if(datamember.value.position.getSquaredDistance(intersection) < 10.0) {
                return listOf(EyeIota(uuid, datamember.key))
            }
        }

        return listOf(NullIota());
    }

    private fun linePlaneIntersection(lineorigin: Vec3d, linedir: Vec3d, planeorigin: Vec3d, planenormal: Vec3d): Vec3d? {
        val epsilon = -1e-3f

        val flag: Double = planenormal.dotProduct(linedir.normalize())
        if (flag > epsilon) {
            return null
        }
        val t: Double =
            (planenormal.dotProduct(planeorigin) - planenormal.dotProduct(lineorigin)) / planenormal.dotProduct(linedir.normalize())
        return lineorigin.add(linedir.normalize().multiply(t))
    }

}