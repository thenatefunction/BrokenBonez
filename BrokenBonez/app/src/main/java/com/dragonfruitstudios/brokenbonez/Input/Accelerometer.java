package com.dragonfruitstudios.brokenbonez.Input;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.util.Log;

public class Accelerometer {
    long lastUpdate = System.currentTimeMillis();
    static float returnValue = 0;
    enum phoneSide {LEFT, RIGHT}
    static phoneSide mPhoneSide = null;

    public void onSensorChanged(SensorEvent event) {
        if(event.sensor.getType() == Sensor.TYPE_ACCELEROMETER){
            float currX, currY, currZ;

            currX = event.values[0];
            currY = event.values[1];
            currZ = event.values[2];

            long currentTime = System.currentTimeMillis();
            if((currentTime - lastUpdate) > 100){
                long extraTime = (currentTime - lastUpdate);
                lastUpdate = currentTime;

                //Log.d("Z", "" + currX);
                //Log.d("Y", "" + currY);
                //Log.d("Z", "" + currZ);

                if((currX < 9.50 && currY > 0.4)){
                    mPhoneSide = phoneSide.LEFT;
                    Log.d("tilted on", "" + mPhoneSide);
                    switch ((int) currX){
                        case 1: returnValue = (float) 0.9;
                            break;
                        case 2: returnValue = (float) 0.8;
                            break;
                        case 3: returnValue = (float) 0.7;
                            break;
                        case 4: returnValue = (float) 0.6;
                            break;
                        case 5: returnValue = (float) 0.5;
                            break;
                        case 6: returnValue = (float) 0.4;
                            break;
                        case 7: returnValue = (float) 0.3;
                            break;
                        case 8: returnValue = (float) 0.2;
                            break;
                        case 9: returnValue = (float) 0.1;
                            break;
                        default:break;
                    }
                }

                else if((currX < 9.50 && currY < 0.0)){
                    mPhoneSide = phoneSide.RIGHT;
                    Log.d("tilted on", "" + mPhoneSide);
                    switch ((int) currX){
                        case 1: returnValue = (float) 0.9;
                            break;
                        case 2: returnValue = (float) 0.8;
                            break;
                        case 3: returnValue = (float) 0.7;
                            break;
                        case 4: returnValue = (float) 0.6;
                            break;
                        case 5: returnValue = (float) 0.5;
                            break;
                        case 6: returnValue = (float) 0.4;
                            break;
                        case 7: returnValue = (float) 0.3;
                            break;
                        case 8: returnValue = (float) 0.2;
                            break;
                        case 9: returnValue = (float) 0.1;
                            break;
                        default:break;
                    }
                }

                else if(currZ < -1.0){
                    Log.d("PHONEISFACEDOWN","");
                }
                else if(currY < 1.0){
                    Log.d("PHONEISFACEUP","");
                }
            }
        }
    }

    public static float getReturnValue(){
        if (Accelerometer.mPhoneSide != null) {
            return returnValue;
        } else {
            return returnValue;
        }
    }
}