package io.duckling.hexintrospection.client;

import net.minecraft.network.PacketByteBuf;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MindSphereClientData {
    private static MindSphereClientData _currentData = new MindSphereClientData();
    public static MindSphereClientData get_currentData(){
        return _currentData;
    }
    public static void set_currentData(MindSphereClientData currentData){
        _currentData = currentData;
    }


    private float _fogdistance = 20f;

    public float get_fogdistance() {
        return _fogdistance;
    }
    public void set_fogdistance(float _fogdistance) {
        this._fogdistance = _fogdistance;
    }

    public Map<Integer, MindSphereClientEyeData> eyeData = new HashMap<>();


}
