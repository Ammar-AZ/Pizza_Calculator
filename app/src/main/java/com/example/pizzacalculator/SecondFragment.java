package com.example.pizzacalculator;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import java.text.DecimalFormat;

public class SecondFragment extends Fragment implements View.OnClickListener{

    View view;
    //These booleans track the measurement mode and the shape mode
    boolean grams = false;
    boolean rect = false;
    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_second, container, false);
        //Give the buttons onClick listeners in the fragment
        Button cring = (Button) view.findViewById(R.id.mode);
        Button cringe = (Button) view.findViewById(R.id.calculator);
        cring.setOnClickListener(this);
        cringe.setOnClickListener(this);
        //get the textfields and edittexts I need to work with
        final TextView measureO = (TextView) view.findViewById(R.id.measureTO);
        final TextView measureT = (TextView) view.findViewById(R.id.measureTT);
        final EditText measureOne = (EditText) view.findViewById(R.id.measureOne);
        final EditText measureTwo = (EditText) view.findViewById(R.id.measureTwo);

        //Find the radiogroup
        RadioGroup rGroup = (RadioGroup)view.findViewById(R.id.shapeGroup);
        //Find the checked button
        RadioButton checkedRadioButton = (RadioButton)rGroup.findViewById(rGroup.getCheckedRadioButtonId());
        //This is the oncheck listener now
        rGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId)
            {
                // This will get the radiobutton that has changed in its check state
                RadioButton checkedRadioButton = (RadioButton)group.findViewById(checkedId);
                //Get whether or not it is checked
                boolean isChecked = checkedRadioButton.isChecked();
                //If the radio button that was changed is now active
                if (isChecked)
                {
                    //Make irrelevant fields disappear and refresh the shared field
                    if(rect){
                        measureO.setText("Diameter:");
                        measureT.setVisibility(View.INVISIBLE);
                        measureOne.setText("");
                        measureTwo.setVisibility(View.INVISIBLE);
                        rect = false;
                    }else{
                        measureO.setText("Length:");
                        measureT.setVisibility(View.VISIBLE);
                        measureOne.setText("");
                        measureTwo.setVisibility(View.VISIBLE);
                        rect = true;
                    }
                }
            }
        });
        return view;
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        /*view.findViewById(R.id.button_second).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(SecondFragment.this)
                        .navigate(R.id.action_SecondFragment_to_FirstFragment);
            }
        });*/
    }

    public void onClick(View v) {
        switch(v.getId()){
            case R.id.mode:
                //Find all the elements that need to be worked with and make it easy to refer to them
                RadioGroup shape = view.findViewById(R.id.shapeGroup);
                TextView gramlbl = view.findViewById(R.id.gramlbl);
                TextView tflbl = view.findViewById(R.id.tflbl);
                TextView measureO = (TextView) view.findViewById(R.id.measureTO);
                TextView measureT = (TextView) view.findViewById(R.id.measureTT);
                EditText measureOne = (EditText) view.findViewById(R.id.measureOne);
                EditText measureTwo = (EditText) view.findViewById(R.id.measureTwo);
                EditText bigImp = (EditText) view.findViewById(R.id.wOtf);

                //Turn the elements irrelevant to the mode invisible or to a relevant value
                if(!grams){
                    shape.setVisibility(View.VISIBLE);
                    gramlbl.setVisibility(View.VISIBLE);
                    tflbl.setVisibility(View.INVISIBLE);
                    shape.setVisibility(View.INVISIBLE);
                    measureO.setVisibility(View.INVISIBLE);
                    measureT.setVisibility(View.INVISIBLE);
                    measureOne.setVisibility(View.INVISIBLE);
                    bigImp.setText("");
                    measureTwo.setVisibility(View.INVISIBLE);
                    grams = true;
                }else{
                    shape.setVisibility(View.INVISIBLE);
                    gramlbl.setVisibility(View.INVISIBLE);
                    tflbl.setVisibility(View.VISIBLE);
                    shape.setVisibility(View.VISIBLE);
                    measureO.setVisibility(View.VISIBLE);
                    measureOne.setVisibility(View.VISIBLE);
                    if(rect) {
                        measureT.setVisibility(View.VISIBLE);
                        measureTwo.setVisibility(View.VISIBLE);
                    }
                    bigImp.setText("0.");
                    grams = false;
                }
                break;

                case R.id.calculator:
                //finding all relevant elements
                EditText mainBox = view.findViewById(R.id.wOtf);
                EditText balls = view.findViewById(R.id.doughBs);
                EditText extra = view.findViewById(R.id.extra);
                EditText dimension = view.findViewById(R.id.measureOne);
                EditText dimensionT = view.findViewById(R.id.measureTwo);

                EditText water = view.findViewById(R.id.waterField);
                EditText  oil = view.findViewById(R.id.oilField);
                EditText sugar = view.findViewById(R.id.sugarField);
                EditText  yeast = view.findViewById(R.id.yeastField);
                EditText  salt = view.findViewById(R.id.saltField);
                EditText honey = view.findViewById(R.id.honeyField);
                EditText other = view.findViewById(R.id.otherField);

                //initializing output string and output box
                String recipe = "Dough Ingredients:\n\n";
                TextView output = view.findViewById(R.id.outputBox);
                //if the main box is empty or has an unusable value this boolean will let me use an if statement to make the button do nothing
                boolean exist;
                    try {
                        double test = Double.valueOf(mainBox.getText().toString());
                        if(test==0)
                            exist = false;
                        else
                            exist = true;
                    } catch (NumberFormatException e) {
                        exist = false;
                    }

                if(!exist){
                    output.setText(recipe);
                    break;
                }else{
                    //variable declaration
                    DecimalFormat df = new DecimalFormat("0.##");
                    double amount, waterP, oilP, sugarP, yeastP, saltP, honeyP, otherP, waterM, oilM, sugarM, yeastM, saltM, honeyM, otherM, flourM, mass, totalPercent, res;

                    double thick = Double.valueOf(mainBox.getText().toString());

                    //If the fields do not hold a usable double assign one and catch the error
                    try {
                        amount = Double.valueOf(balls.getText().toString());
                        //If they input 0 just throw an exception to get to the catch and give them 1 instead
                        if(amount==0)
                            throw new NumberFormatException("");
                    } catch (NumberFormatException e) {
                        amount = 1;
                        balls.setText("1");
                    }

                    try {
                        waterP = Double.valueOf(water.getText().toString());
                    } catch (NumberFormatException e) {
                        waterP = 0;
                    }

                    try {
                        oilP = Double.valueOf(oil.getText().toString());
                    } catch (NumberFormatException e) {
                        oilP = 0;
                    }

                    try {
                        sugarP = Double.valueOf(sugar.getText().toString());
                    } catch (NumberFormatException e) {
                        sugarP = 0;
                    }

                    try {
                        yeastP = Double.valueOf(yeast.getText().toString());
                    } catch (NumberFormatException e) {
                        yeastP = 0;
                    }

                    try {
                        saltP = Double.valueOf(salt.getText().toString());
                    } catch (NumberFormatException e) {
                        saltP = 0;
                    }

                    try {
                        honeyP = Double.valueOf(honey.getText().toString());
                    } catch (NumberFormatException e) {
                        honeyP = 0;
                    }

                    try {
                        otherP = Double.valueOf(other.getText().toString());
                    } catch (NumberFormatException e) {
                        otherP = 0;
                    }

                    try {
                        res = 1+(Double.valueOf(extra.getText().toString())/100);
                    } catch (NumberFormatException e) {
                        res = 1.00;
                    }
                    //Calculate the mass of each doughball based off the mode
                    if (grams) {
                        mass = Double.valueOf(mainBox.getText().toString());
                    } else if (rect) {
                        double length,width;
                        //try to get the two dimensions, if either fails break
                        try{
                            length=Double.valueOf(dimension.getText().toString());
                            try{
                                width = Double.valueOf(dimensionT.getText().toString());
                                //if a dimension is 0 just throw an exception to get to the catch
                                if(length==0||width==0)
                                    throw new NumberFormatException("");
                            }catch (NumberFormatException e) {
                                output.setText(recipe);
                                break;
                            }
                        }catch (NumberFormatException e) {
                            output.setText(recipe);
                            break;
                        }
                        mass = length*width*(thick)*28.3495;
                    } else {
                        double rad;
                        //try to get the radius, if the
                        try {
                           rad  = Double.valueOf(dimension.getText().toString()) / 2;;
                            //if a dimension is 0 just throw an exception to get to the catch
                            if(rad==0)
                                throw new NumberFormatException("");
                        } catch (NumberFormatException e) {
                            output.setText(recipe);
                            break;
                        }
                        mass = ((3.141592653589793 * rad * rad) * (thick)) * 28.3495;
                    }

                    //These are normal pizza calculator values for calculation
                    totalPercent = 100 + waterP + oilP + sugarP + yeastP + saltP + honeyP + otherP;
                    flourM = 100 / totalPercent * mass * res;
                    waterM = ((waterP / 100) * flourM * amount);
                    yeastM = (yeastP / 100 * flourM * amount);
                    saltM = (saltP / 100 * flourM * amount);
                    oilM = (oilP / 100 * flourM * amount);
                    sugarM = (sugarP / 100 * flourM * amount);
                    honeyM = (honeyP / 100 * flourM * amount);
                    otherM = (otherP / 100 * flourM * amount);

                    //Add in a line for every ingredient being used, these mass to volume calculations are correct (not determined by me though)

                    recipe+="Flour (100%): " + df.format(flourM*amount)+" g\n\n";
                    if(waterM!=0)
                        recipe+= "Water ("+ df.format(waterP) +"%): " + df.format(waterM)+" g\n\n";
                    if(yeastM!=0)
                        recipe+= "Yeast ("+ df.format(yeastP) +"%): " + df.format(yeastM)+"g | " + df.format(yeastM/3) + " tsp | "+ df.format(yeastM/3/3)+" tbsp\n\n";
                    if(saltM!=0)
                        recipe+= "Salt ("+ df.format(saltP) +"%): " + df.format(saltM)+" g | "+ df.format(saltM/5.56666666666666) + " tsp | "+df.format(saltM/5.56666666666666/3)+" tbsp\n\n";
                    if(oilM!=0)
                        recipe+= "Oil ("+ df.format(oilP) +"%): " + df.format(oilM)+" g | "+ df.format(oilM/4.5046210721) + " tsp | "+ df.format(oilM/4.5046210721/3)+" tbsp\n\n";
                    if(sugarM!=0)
                        recipe+= "Sugar ("+ df.format(sugarP) +"%): " + df.format(sugarM)+" g | "+ df.format(sugarM/3.9832935561) + " tsp | " + df.format(sugarM/3.9832935561/3)+" tbsp\n\n";
                    if(honeyM!=0)
                        recipe+= "Honey ("+ df.format(honeyP) +"%): " + df.format(honeyM)+" g | "+ df.format(honeyM/6.96) + " tsp | " + df.format(honeyM/3.9832935561/3)+" tbsp\n\n";
                    if(otherM!=0)
                        recipe+= "Other ("+ df.format(otherP) +"%): " + df.format(otherM)+" g\n\n";

                    recipe+="Total ("+ df.format(totalPercent) +"%): " + df.format((mass*amount*res)+.005)+" g\n\n";
                    //Mass of each doughball must be known (if relevant)
                    if(amount>1)
                        recipe+="Single Ball: " + df.format((mass*res)+.005)+" g\n";
                    output.setText(recipe);
                    break;
                }


        }
    }



    }



