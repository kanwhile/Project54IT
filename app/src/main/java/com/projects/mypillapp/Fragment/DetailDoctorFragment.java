package com.projects.mypillapp.Fragment;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.projects.mypillapp.R;
import com.projects.mypillapp.activity.DetailDocter;
import com.projects.mypillapp.activity.ListDoctor;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailDoctorFragment extends Fragment {


    public DetailDoctorFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_detail_doctor, container, false);
        final TextView phone = (TextView)view.findViewById(R.id.txt_phone);
        TextView email = (TextView)view.findViewById(R.id.txt_email);
        TextView address = (TextView)view.findViewById(R.id.txt_address);
        LinearLayout linearLayout = (LinearLayout)view.findViewById(R.id.call_phone);

        phone.setText(DetailDocter.phone);
        email.setText(DetailDocter.email);
        address.setText(DetailDocter.address);

        MyPhoneListener phoneListener = new MyPhoneListener();
        TelephonyManager telephonyManager =
                (TelephonyManager) getActivity().getSystemService(Context.TELEPHONY_SERVICE);
        // receive notifications of telephony state changes
        telephonyManager.listen(phoneListener, PhoneStateListener.LISTEN_CALL_STATE);
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String uri = "tel:"+phone.getText().toString();
                    Intent dialIntent = new Intent(Intent.ACTION_DIAL, Uri.parse(uri));

                    startActivity(dialIntent);
                }catch(Exception e) {
                    Toast.makeText(getActivity(),"Your call has failed...",
                            Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        });
        return view;
    }
    private class MyPhoneListener extends PhoneStateListener {

        private boolean onCall = false;

        @Override
        public void onCallStateChanged(int state, String incomingNumber) {

            switch (state) {
                case TelephonyManager.CALL_STATE_RINGING:
                    // phone ringing...
                    Toast.makeText(getActivity(), incomingNumber + " calls you",
                            Toast.LENGTH_LONG).show();
                    break;

                case TelephonyManager.CALL_STATE_OFFHOOK:
                    // one call exists that is dialing, active, or on hold
                    Toast.makeText(getActivity(), "on call...",
                            Toast.LENGTH_LONG).show();
                    //because user answers the incoming call
                    onCall = true;
                    break;

                case TelephonyManager.CALL_STATE_IDLE:
                    // in initialization of the class and at the end of phone call

                    // detect flag from CALL_STATE_OFFHOOK
                    if (onCall == true) {
                        Toast.makeText(getActivity(), "restart app after call",
                                Toast.LENGTH_LONG).show();

                        // restart our application
                        Intent restart = getActivity().getBaseContext().getPackageManager().
                                getLaunchIntentForPackage(getActivity().getBaseContext().getPackageName());
                        restart.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(restart);

                        onCall = false;
                    }
                    break;
                default:
                    break;
            }

        }
    }
}
