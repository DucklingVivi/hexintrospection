package io.duckling.hexintrospection.network;

import dev.architectury.networking.NetworkManager;
import io.duckling.hexintrospection.HexIntrospection;
import net.minecraft.util.Identifier;

public class Packets {


    public static final Identifier DIMENSIONALSYNC = new Identifier(HexIntrospection.MOD_ID, "dimensionalsync");
    public static final Identifier MINDSPHERE = new Identifier(HexIntrospection.MOD_ID, "mindsphere");


    public static void init(){
        NetworkManager.registerReceiver(NetworkManager.Side.S2C, DIMENSIONALSYNC, DimensionalSyncPacket::recieve);
        NetworkManager.registerReceiver(NetworkManager.Side.S2C, MINDSPHERE, MindSpherePacket::recieve);
    }









}
