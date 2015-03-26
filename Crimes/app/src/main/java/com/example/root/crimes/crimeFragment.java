package com.example.root.crimes;


import android.os.Bundle;
import android.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class crimeFragment extends Fragment {
    private crime _crime;


    public crimeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        _crime = new crime();


        View view = inflater.inflate(R.layout.fragment_crime, container, false);

        EditText input = (EditText) view.findViewById(R.id.crime_title);

        input.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle os = new Bundle();

                os.putString("key", "first frag");

                getFragmentManager().beginTransaction().add(R.id.container, fragment_b.newInstance(os)).commit();

            }
        });

        input.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                _crime.setmTitle(s.toString());

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        return view;
    }


}
