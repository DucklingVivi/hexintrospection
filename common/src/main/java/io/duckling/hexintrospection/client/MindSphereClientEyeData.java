package io.duckling.hexintrospection.client;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;

public class MindSphereClientEyeData {


    public BlockPos position = new BlockPos(0,0,0);

    public EyeRenderInfo eyeRenderInfo = new EyeRenderInfo(0,0);


    private int _tier = 0;
    public int get_tier(){
        return _tier;
    }
    public void set_tier(int tier){
        this._tier = tier;
        eyeRenderInfo.reCalculate(tier);
    }




    public static MindSphereClientEyeData fromNBT(NbtCompound compound){
        MindSphereClientEyeData data = new MindSphereClientEyeData();
        data.set_tier(compound.getInt("tier"));
        data.position = BlockPos.fromLong(compound.getLong("position"));
        return data;
    }


    public static class EyeRenderInfo {
        public int irisIndex = 0;
        public int eyeIndex = 0;

        public boolean showPupil = true;
        public boolean showIris = true;
        public float pupilBound = 1.5f;

        public EyeRenderInfo(int irisIndex, int eyeIndex) {
            this.irisIndex = irisIndex;
            this.eyeIndex = eyeIndex;
        }


        //tier goes from 0-17, there are 13 iris indexes and 13 pupil indexes
        public void reCalculate(int tier){
            showIris = true;
            showPupil = true;
            pupilBound = 1.5f;
           switch(tier){
               case 17: {
                     irisIndex = 0;
                     eyeIndex = 0;
                     break;
               }
                case 16: {
                     irisIndex = 1;
                     eyeIndex = 0;
                     break;
                }
                case 15: {
                     irisIndex = 2;
                     eyeIndex = 0;
                     pupilBound = 0.7f;
                     break;
                }
                case 14: {
                     irisIndex = 3;
                     eyeIndex = 1;
                    pupilBound = 0.45f;
                     break;
                }
                case 13: {
                     irisIndex = 4;
                     eyeIndex = 2;
                    pupilBound = 0.3f;
                     break;
                }
                case 12: {
                     irisIndex = 5;
                     eyeIndex = 3;
                    pupilBound = 0.15f;
                     break;
                }
                case 11: {
                     irisIndex = 6;
                     eyeIndex = 4;
                     pupilBound = 0f;
                     break;
                }
                case 10: {
                     irisIndex = 7;
                     eyeIndex = 4;
                     pupilBound = 0f;
                     break;
                }
                case 9: {
                     irisIndex = 8;
                     eyeIndex = 4;
                     pupilBound = 0f;
                     break;
                }
                case 8: {
                     irisIndex = 9;
                     eyeIndex = 4;
                    showPupil = false;
                     break;
                }
                case 7: {
                     irisIndex = 10;
                     eyeIndex = 5;
                    showPupil = false;
                     break;
                }
                case 6: {
                     irisIndex = 11;
                     eyeIndex = 6;
                    showPupil = false;
                     break;
                }
                case 5: {
                     irisIndex = 12;
                    showPupil = false;
                     eyeIndex = 7;
                     break;
                }
                case 4: {
                     showIris = false;
                    showPupil = false;
                     eyeIndex = 8;
                     break;
                }
                case 3: {
                     showIris = false;
                    showPupil = false;
                     eyeIndex = 9;
                     break;
                }
                case 2: {
                     showIris = false;
                    showPupil = false;
                     eyeIndex = 10;
                     break;
                }
                case 1: {
                     showIris = false;
                     showPupil = false;
                     eyeIndex = 11;
                     break;
                }
                case 0: {
                     showIris = false;
                     showPupil = false;
                     eyeIndex = 12;
                     break;
                }

           }
        }
    }

}
