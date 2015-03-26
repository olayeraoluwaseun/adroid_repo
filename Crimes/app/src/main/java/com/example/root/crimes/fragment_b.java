package com.example.root.crimes;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class fragment_b extends Fragment {


    public fragment_b() {
        // Required empty public constructor
    }



    public static fragment_b newInstance(Bundle os){
        fragment_b frag = new fragment_b();
        frag.setArguments(os);
        return frag;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        TextView textView = new TextView(getActivity());
//        textView.setText(R.string.hello_blank_fragment);

        Bundle re = getArguments();

        String s =re.getString("key");





        Toast.makeText(getActivity(), s, Toast.LENGTH_SHORT).show();

        return textView;
    }


}
