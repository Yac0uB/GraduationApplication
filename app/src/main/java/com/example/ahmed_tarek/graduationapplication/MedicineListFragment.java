package com.example.ahmed_tarek.graduationapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Ahmed_Tarek on 17/11/23.
 */

public class MedicineListFragment extends Fragment {

    private RecyclerView mMedicineListRecyclerView;
    private MedicineAdapter mMedicineAdapter;

    private CartLab mCartLab;

    private EditText mSearchText;
    private Button mSearchSubmitButton;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_fragment, container, false);

        mCartLab = CartLab.get();

        mMedicineListRecyclerView = (RecyclerView) view.findViewById(R.id.medicine_list_recycler_view);
        mMedicineListRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mSearchText = (EditText) view.findViewById(R.id.search_label);
        mSearchText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                ;;;;;;; ///////////alot of code here :D LOL...
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        });

        MedicineLab medicineLab = MedicineLab.get();
        List<Medicine> medicines = medicineLab.getMedicines();

        if (mMedicineAdapter == null) {
            mMedicineAdapter = new MedicineAdapter(medicines);
            mMedicineListRecyclerView.setAdapter(mMedicineAdapter);
        } else {
            mMedicineAdapter.notifyDataSetChanged();
        }

        mSearchSubmitButton = (Button) view.findViewById(R.id.search_submit);
        mSearchSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), CartActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }

    private class MedicineHolder extends RecyclerView.ViewHolder {

        private Medicine mMedicine;

        private CheckBox mSelectedCheckBox;
        private TextView mMedicineNameTextView;
        private Button mDetailsButton;

        public MedicineHolder(View itemView) {
            super(itemView);

            mSelectedCheckBox = (CheckBox) itemView.findViewById(R.id.medicine_selected_check_box);
            mMedicineNameTextView = (TextView) itemView.findViewById(R.id.medicine_name);
            mDetailsButton = (Button) itemView.findViewById(R.id.medicine_details_button);

        }

        public void bindMedicine(Medicine medicine) {
            mMedicine = medicine;

            mMedicineNameTextView.setText(mMedicine.getName());

            mSelectedCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if (compoundButton.isChecked()) {
                        mCartLab.addMedicine(mMedicine);
                    } else {
                        mCartLab.removeMedicine(mMedicine);
                    }
                }
            });

            mDetailsButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    FragmentManager fragmentManager = getFragmentManager();
                    MedicineDetailsFragment medicineDetailsFragment = MedicineDetailsFragment.newInstance(mMedicine.getID());

                    fragmentManager.beginTransaction()
                            .add(R.id.main_fragment_container, medicineDetailsFragment)
                            .addToBackStack(null)
                            .commit();
                }
            });
        }

    }

    private class MedicineAdapter extends RecyclerView.Adapter<MedicineHolder> {

        List<Medicine> mMedicines;

        public MedicineAdapter(List<Medicine> medicines) {
            mMedicines = medicines;
        }

        @Override
        public MedicineHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater.inflate(R.layout.list_item_medicine_search, parent, false);

            return new MedicineHolder(view);
        }

        @Override
        public void onBindViewHolder(MedicineHolder holder, int position) {

            Medicine medicine = mMedicines.get(position);
            holder.bindMedicine(medicine);
        }

        @Override
        public int getItemCount() {
            return mMedicines.size();
        }


    }
}