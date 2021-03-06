package tech.iosd.benefit.DashboardFragments;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.aigestudio.wheelpicker.WheelPicker;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import tech.iosd.benefit.R;

public class MeasurementData extends Fragment implements View.OnClickListener
{
    public Boolean isHeightFtSelected = true;
    public Boolean isWaistCmSelected = true;
    public Boolean isNeckCmSelected = true;
    public Boolean isHipCmSelected = true;

    Context ctx;
    FragmentManager fm;
    Button heightFt;
    Button heightCm;
    Button waistIn;
    Button waistCm;
    Button neckIn;
    Button neckCm;
    Button hipIn;
    Button hipCm;
    Button saveBtn;
    EditText ageField;
    TextView heightField;
    TextView waistField;
    TextView neckField;
    TextView hipField;
    FloatingActionButton btnMale;
    FloatingActionButton btnFemale;
    FloatingActionButton genderSelector;
    int heightPickerPos = 0;
    int waistPickerPos = 0;
    int neckPickerPos = 0;
    int hipPickerPos = 0;
    List<String> heightsCM;
    List<String> heightsFT;
    List<String> waistIN;
    List<String> waistCM;
    List<String> neckIN;
    List<String> neckCM;
    List<String> hipIN;
    List<String> hipCM;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.dashboard_setup_measurement, container, false);
        ctx = rootView.getContext();
        fm = getFragmentManager();

        heightsCM = new ArrayList<>();
        heightsFT = new ArrayList<>();
        for (int i = 120; i <= 220; i++)
        {
            heightsCM.add(Integer.toString(i));
            double inches = (i / 2.54);
            int feet = (int) inches / 12;
            inches = round(inches - (feet * 12), 1);
            heightsFT.add(feet + "’ " + inches + "”");
        }
        waistCM = new ArrayList<>();
        waistIN = new ArrayList<>();
        for (int i = 50; i <= 100; i++)
        {
            waistCM.add(Integer.toString(i));
            double inches = (i / 2.54);
            int feet = (int) inches / 12;
            inches = round(inches - (feet * 12), 1);
            waistIN.add(feet + "’ " + inches + "”");
        }
        neckCM = new ArrayList<>();
        neckIN = new ArrayList<>();
        for (int i = 20; i <= 60; i++)
        {
            neckCM.add(Integer.toString(i));
            double inches = (i / 2.54);
            int feet = (int) inches / 12;
            inches = round(inches - (feet * 12), 1);
            neckIN.add(feet + "’ " + inches + "”");
        }
        hipCM = new ArrayList<>();
        hipIN = new ArrayList<>();
        for (int i = 80; i <= 130; i++)
        {
            hipCM.add(Integer.toString(i));
            double inches = (i / 2.54);
            int feet = (int) inches / 12;
            inches = round(inches - (feet * 12), 1);
            hipIN.add(feet + "’ " + inches + "”");
        }

        heightFt = rootView.findViewById(R.id.dashboard_measurement_setup_height_ft);
        heightCm = rootView.findViewById(R.id.dashboard_measurement_setup_height_cm);
        waistCm = rootView.findViewById(R.id.dashboard_measurement_setup_waist_cm);
        waistIn = rootView.findViewById(R.id.dashboard_measurement_setup_waist_in);
        neckCm = rootView.findViewById(R.id.dashboard_measurement_setup_neck_cm);
        neckIn = rootView.findViewById(R.id.dashboard_measurement_setup_neck_in);
        hipCm = rootView.findViewById(R.id.dashboard_measurement_setup_hip_cm);
        hipIn = rootView.findViewById(R.id.dashboard_measurement_setup_hip_in);
        saveBtn = rootView.findViewById(R.id.dashboard_measurement_setup_save);
        saveBtn.setAlpha(0.2f);
        saveBtn.setEnabled(false);

        ageField = rootView.findViewById(R.id.dashboard_measurement_setup_age);
        heightField = rootView.findViewById(R.id.dashboard_measurement_setup_height);
        waistField = rootView.findViewById(R.id.dashboard_measurement_setup_waist);
        neckField = rootView.findViewById(R.id.dashboard_measurement_setup_neck);
        hipField = rootView.findViewById(R.id.dashboard_measurement_setup_hip);
        heightFt.setOnClickListener(this);
        heightCm.setOnClickListener(this);
        waistCm.setOnClickListener(this);
        waistIn.setOnClickListener(this);
        neckCm.setOnClickListener(this);
        neckIn.setOnClickListener(this);
        hipCm.setOnClickListener(this);
        hipIn.setOnClickListener(this);
        btnMale = rootView.findViewById(R.id.dashboard_measurement_setup_male);
        btnFemale = rootView.findViewById(R.id.dashboard_measurement_setup_female);
        genderSelector = rootView.findViewById(R.id.dashboard_measurement_setup_gender);
        btnMale.setBackgroundTintList(getResources().getColorStateList(R.color.FABIndicatorBGNotSelected));
        btnFemale.setBackgroundTintList(getResources().getColorStateList(R.color.FABIndicatorBGSelected));
        btnMale.setRippleColor(getResources().getColor(R.color.FABIndicatorBGSelected));
        btnFemale.setRippleColor(getResources().getColor(R.color.FABIndicatorBGNotSelected));
        btnMale.setColorFilter(getResources().getColor(R.color.FABIndicatorSelected));
        btnFemale.setColorFilter(getResources().getColor(R.color.FABIndicatorNotSelected));
        btnMale.setOnClickListener(this);
        btnFemale.setOnClickListener(this);
        saveBtn.setOnClickListener(this);
        heightField.setOnClickListener(this);
        waistField.setOnClickListener(this);
        neckField.setOnClickListener(this);
        hipField.setOnClickListener(this);

        ageField.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {
                checkFields();
            }

            @Override
            public void afterTextChanged(Editable editable) { }
        });
        heightField.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {
                checkFields();
            }

            @Override
            public void afterTextChanged(Editable editable) { }
        });
        waistField.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {
                checkFields();
            }

            @Override
            public void afterTextChanged(Editable editable) { }
        });
        neckField.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {
                checkFields();
            }

            @Override
            public void afterTextChanged(Editable editable) { }
        });
        hipField.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {
                checkFields();
            }

            @Override
            public void afterTextChanged(Editable editable) { }
        });


        return rootView;
    }

    void checkFields()
    {
        String height = heightField.getText().toString();
        String waist = waistField.getText().toString();
        String neck = neckField.getText().toString();
        String hip = hipField.getText().toString();
        String age = ageField.getText().toString();
        if(!age.equals("") && !height.equals("") && !waist.equals("") && !neck.equals("") && !hip.equals(""))
        {
            saveBtn.setAlpha(1.0f);
            saveBtn.setEnabled(true);
        }
    }

    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.dashboard_measurement_setup_save:
            {
                fm.beginTransaction().replace(R.id.dashboard_content, new Measurement())
                        .addToBackStack(null)
                        .commit();
                break;
            }
            case R.id.dashboard_measurement_setup_female:
            {
                btnMale.setBackgroundTintList(getResources().getColorStateList(R.color.FABIndicatorBGNotSelected));
                btnFemale.setBackgroundTintList(getResources().getColorStateList(R.color.FABIndicatorBGSelected));
                btnMale.setRippleColor(getResources().getColor(R.color.FABIndicatorBGSelected));
                btnFemale.setRippleColor(getResources().getColor(R.color.FABIndicatorBGNotSelected));
                btnMale.setColorFilter(getResources().getColor(R.color.FABIndicatorSelected));
                btnFemale.setColorFilter(getResources().getColor(R.color.FABIndicatorNotSelected));
                genderSelector.setImageResource(R.drawable.female_img);
                break;
            }
            case R.id.dashboard_measurement_setup_male:
            {
                btnFemale.setBackgroundTintList(getResources().getColorStateList(R.color.FABIndicatorBGNotSelected));
                btnMale.setBackgroundTintList(getResources().getColorStateList(R.color.FABIndicatorBGSelected));
                btnFemale.setRippleColor(getResources().getColor(R.color.FABIndicatorBGSelected));
                btnMale.setRippleColor(getResources().getColor(R.color.FABIndicatorBGNotSelected));
                btnFemale.setColorFilter(getResources().getColor(R.color.FABIndicatorSelected));
                btnMale.setColorFilter(getResources().getColor(R.color.FABIndicatorNotSelected));
                genderSelector.setImageResource(R.drawable.male_img);
                break;
            }
            case R.id.dashboard_measurement_setup_height_cm:
            {
                isHeightFtSelected = false;
                heightFt.setBackground(getResources().getDrawable(R.drawable.button_style_off));
                heightFt.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                heightCm.setBackground(getResources().getDrawable(R.drawable.button_style_on));
                heightCm.setTextColor(getResources().getColor(R.color.white));
                heightField.setText(heightsCM.get(heightPickerPos));
                break;
            }
            case R.id.dashboard_measurement_setup_height_ft:
            {
                isHeightFtSelected = true;
                heightFt.setBackground(getResources().getDrawable(R.drawable.button_style_on));
                heightFt.setTextColor(getResources().getColor(R.color.white));
                heightCm.setBackground(getResources().getDrawable(R.drawable.button_style_off));
                heightCm.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                heightField.setText(heightsFT.get(heightPickerPos));
                break;
            }
            case R.id.dashboard_measurement_setup_waist_in:
            {
                isWaistCmSelected = false;
                waistCm.setBackground(getResources().getDrawable(R.drawable.button_style_off));
                waistCm.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                waistIn.setBackground(getResources().getDrawable(R.drawable.button_style_on));
                waistIn.setTextColor(getResources().getColor(R.color.white));
                waistField.setText(waistIN.get(waistPickerPos));
                break;
            }
            case R.id.dashboard_measurement_setup_waist_cm:
            {
                isWaistCmSelected = true;
                waistCm.setBackground(getResources().getDrawable(R.drawable.button_style_on));
                waistCm.setTextColor(getResources().getColor(R.color.white));
                waistIn.setBackground(getResources().getDrawable(R.drawable.button_style_off));
                waistIn.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                waistField.setText(waistCM.get(waistPickerPos));
                break;
            }
            case R.id.dashboard_measurement_setup_neck_in:
            {
                isNeckCmSelected = false;
                neckCm.setBackground(getResources().getDrawable(R.drawable.button_style_off));
                neckCm.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                neckIn.setBackground(getResources().getDrawable(R.drawable.button_style_on));
                neckIn.setTextColor(getResources().getColor(R.color.white));
                neckField.setText(neckIN.get(neckPickerPos));
                break;
            }
            case R.id.dashboard_measurement_setup_neck_cm:
            {
                isNeckCmSelected = true;
                neckCm.setBackground(getResources().getDrawable(R.drawable.button_style_on));
                neckCm.setTextColor(getResources().getColor(R.color.white));
                neckIn.setBackground(getResources().getDrawable(R.drawable.button_style_off));
                neckIn.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                neckField.setText(neckCM.get(neckPickerPos));
                break;
            }
            case R.id.dashboard_measurement_setup_hip_in:
            {
                isHipCmSelected = false;
                hipCm.setBackground(getResources().getDrawable(R.drawable.button_style_off));
                hipCm.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                hipIn.setBackground(getResources().getDrawable(R.drawable.button_style_on));
                hipIn.setTextColor(getResources().getColor(R.color.white));
                hipField.setText(hipIN.get(hipPickerPos));
                break;
            }
            case R.id.dashboard_measurement_setup_hip_cm:
            {
                isHipCmSelected = true;
                hipCm.setBackground(getResources().getDrawable(R.drawable.button_style_on));
                hipCm.setTextColor(getResources().getColor(R.color.white));
                hipIn.setBackground(getResources().getDrawable(R.drawable.button_style_off));
                hipIn.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                hipField.setText(hipCM.get(hipPickerPos));
                break;
            }
            case R.id.dashboard_measurement_setup_height:
            {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(getActivity());
                View mView = getActivity().getLayoutInflater().inflate(R.layout.dialog_picker_height, null);
                Button dialogDone = mView.findViewById(R.id.dialog_done);
                final WheelPicker wheelPickerHeight = mView.findViewById(R.id.dialog_picker_height);
                mBuilder.setView(mView);
                final AlertDialog dialog = mBuilder.create();
                dialog.show();
                wheelPickerHeight.setData( isHeightFtSelected ? heightsFT : heightsCM);
                wheelPickerHeight.setSelectedItemPosition(heightPickerPos);

                dialogDone.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View view)
                    {
                        dialog.dismiss();
                        heightPickerPos = wheelPickerHeight.getCurrentItemPosition();
                        heightField.setText(wheelPickerHeight.getData().get(heightPickerPos).toString());
                    }
                });
                break;
            }
            case R.id.dashboard_measurement_setup_waist:
            {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(getActivity());
                View mView = getActivity().getLayoutInflater().inflate(R.layout.dialog_picker_waist, null);
                Button dialogDone = mView.findViewById(R.id.dialog_done);
                final WheelPicker wheelPickerWaist = mView.findViewById(R.id.dialog_picker_waist);
                mBuilder.setView(mView);
                final AlertDialog dialog = mBuilder.create();
                dialog.show();
                wheelPickerWaist.setData( isWaistCmSelected ? waistCM : waistIN);
                wheelPickerWaist.setSelectedItemPosition(waistPickerPos);

                dialogDone.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View view)
                    {
                        dialog.dismiss();
                        waistPickerPos = wheelPickerWaist.getCurrentItemPosition();
                        waistField.setText(wheelPickerWaist.getData().get(waistPickerPos).toString());
                    }
                });
                break;
            }
            case R.id.dashboard_measurement_setup_neck:
            {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(getActivity());
                View mView = getActivity().getLayoutInflater().inflate(R.layout.dialog_picker_neck, null);
                Button dialogDone = mView.findViewById(R.id.dialog_done);
                final WheelPicker wheelPickerNeck = mView.findViewById(R.id.dialog_picker_neck);
                mBuilder.setView(mView);
                final AlertDialog dialog = mBuilder.create();
                dialog.show();
                wheelPickerNeck.setData( isNeckCmSelected ? neckCM : neckIN);
                wheelPickerNeck.setSelectedItemPosition(neckPickerPos);

                dialogDone.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View view)
                    {
                        dialog.dismiss();
                        neckPickerPos = wheelPickerNeck.getCurrentItemPosition();
                        neckField.setText(wheelPickerNeck.getData().get(neckPickerPos).toString());
                    }
                });
                break;
            }
            case R.id.dashboard_measurement_setup_hip:
            {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(getActivity());
                View mView = getActivity().getLayoutInflater().inflate(R.layout.dialog_picker_hip, null);
                Button dialogDone = mView.findViewById(R.id.dialog_done);
                final WheelPicker wheelPickerHip = mView.findViewById(R.id.dialog_picker_hip);
                mBuilder.setView(mView);
                final AlertDialog dialog = mBuilder.create();
                dialog.show();
                wheelPickerHip.setData( isHipCmSelected ? hipCM : hipIN);
                wheelPickerHip.setSelectedItemPosition(hipPickerPos);

                dialogDone.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View view)
                    {
                        dialog.dismiss();
                        hipPickerPos = wheelPickerHip.getCurrentItemPosition();
                        hipField.setText(wheelPickerHip.getData().get(hipPickerPos).toString());
                    }
                });
                break;
            }
        }
    }

    public double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}
