package com.a2drunk.alchotest.alchotest;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    final ArrayList<Drink> mainListOfDrinks = new ArrayList<Drink>();

    float BODY_WEIGHT_IN_KG=0;
    int GENDERTYPE=-1;
    float lastDrinkHr=0; //IN HOURS
    float drinkingPeriodHr=0; //HOURS OF DRINKING

    float BODY_WEIGHT_IN_LB=0;
    float GENDER=0; //0.73 for man//ili 0.66 for women
    float BODY_WATER_CONSTANT=0;//0.58 for man//0.49 for women
    float METABOLISM_CONSTANT=0;//0.015 for man//0.017 for women
    //****************************************************************************************
    //BAC%=(A*5.14/W*r)-0.015*H//A=total alchocol consumed(oz)//W=Body weight(lbs)//r=constant(0.73 or 0.66)//H=last drink
    float mainFormula1(){
        return (((float)5.14*calculateOzOfAlchocol())/(BODY_WEIGHT_IN_LB*GENDER))-(float)0.015*lastDrinkHr;
    }
    float calculateOzOfAlchocol(){
        float sumOfOz=0;
        for(Drink temp:mainListOfDrinks){
            sumOfOz+=temp.returnAlchocolOz();
        }
        return sumOfOz;
    }
    //****************************************************************************************
    //EBAC=(((0.806*SD*1.2)/(BW*Wt))-MR*DP)x10//SD=standard drinks//BW=bodtwater constant//Wt=weight in kg//DP=drinking period
    //https://en.wikipedia.org/wiki/Blood_alcohol_content
    float calculateStandardDrinks(){
        float sumOfSD=0;
        for(Drink temp:mainListOfDrinks){
            sumOfSD+=temp.returnStandardDrink();
        }
        return sumOfSD;
    }
    float mainFormula2(){
        return ((((float)0.806*calculateStandardDrinks()*(float)1.2)/(BODY_WATER_CONSTANT*BODY_WEIGHT_IN_KG))-(METABOLISM_CONSTANT*drinkingPeriodHr))*10;
    }
    //****************************************************************************************
    void getConstantsFromDataBase(){
        //SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
        //String ret = settings.getString("statepara1", "0");
        SharedPreferences GENDERTYPEPref= getSharedPreferences("GENDERTYPE", 0);
        GENDERTYPE = GENDERTYPEPref.getInt("GENDERTYPE", 0);

        SharedPreferences BODY_WEIGHT_IN_KGPref= getSharedPreferences("BODY_WEIGHT_IN_KG", 0);
        BODY_WEIGHT_IN_KG = BODY_WEIGHT_IN_KGPref.getInt("BODY_WEIGHT_IN_KG", 0);

        SharedPreferences lastDrinkHrPref= getSharedPreferences("lastDrinkHr", 0);
        lastDrinkHr = lastDrinkHrPref.getFloat("lastDrinkHr", 0);

        SharedPreferences drinkingPeriodHrPref= getSharedPreferences("drinkingPeriodHr", 0);
        drinkingPeriodHr = drinkingPeriodHrPref.getFloat("drinkingPeriodHr", 0);

    }
    //****************************************************************************************
    void storeGENDERTYPE(){
        //SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
        //SharedPreferences.Editor editor = settings.edit();
        //editor.putInt("GENDERTYPE", GENDERTYPE);
        SharedPreferences pref = getApplicationContext().getSharedPreferences("GENDERTYPE", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt("GENDERTYPE", GENDERTYPE);        // Saving integer
        editor.apply();
        //ediotr.commit();
    }
    void storeBODY_WEIGHT_IN_KG(){
        SharedPreferences pref = getApplicationContext().getSharedPreferences("BODY_WEIGHT_IN_KG", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putFloat("BODY_WEIGHT_IN_KG", BODY_WEIGHT_IN_KG);        // Saving integer
        editor.apply();
        //ediotr.commit();
    }
    void storelastDrinkHr(){
        SharedPreferences pref = getApplicationContext().getSharedPreferences("lastDrinkHr", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putFloat("lastDrinkHr", lastDrinkHr);        // Saving integer
        editor.apply();
        //ediotr.commit();
    }
    void storedrinkingPeriodHr(){
        SharedPreferences pref = getApplicationContext().getSharedPreferences("drinkingPeriodHr", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putFloat("drinkingPeriodHr", drinkingPeriodHr);        // Saving integer
        editor.apply();
        //ediotr.commit();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



    }
}
