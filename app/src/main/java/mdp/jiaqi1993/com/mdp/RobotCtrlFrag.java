package mdp.jiaqi1993.com.mdp;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class RobotCtrlFrag extends Fragment {
    View inflatedView = null;
    ProgressDialog pd ;

    public RobotCtrlFrag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        inflatedView=inflater.inflate(R.layout.fragment_robot_control, container, false);

        final TextView tvStatus=(TextView)inflatedView.findViewById(R.id.textStatus);

        Button btnF= (Button)inflatedView.findViewById(R.id.btnForward);
        Button btnL= (Button)inflatedView.findViewById(R.id.btnLeft);
        Button btnR= (Button)inflatedView.findViewById(R.id.btnRight);
        pd = new ProgressDialog(getActivity());


        btnF.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                pd.setMessage("Moving Forward");
                pd.show();
                tvStatus.setText("Robot is Moving Forward");
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        pd.dismiss();
                        tvStatus.setText("Robot Ready for Action");
                    }
                }, 1000);
            }
        });
        btnL.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                pd.setMessage("Moving Left");
                pd.show();
                tvStatus.setText("Robot is Moving Left");
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        pd.dismiss();
                        tvStatus.setText("Robot Ready for Action");
                    }
                }, 1000);
            }
        });
        btnR.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                pd.setMessage("Moving Right");
                pd.show();
                tvStatus.setText("Robot is Moving Right");
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        pd.dismiss();
                        tvStatus.setText("Robot Ready for Action");
                    }
                }, 1000);
            }
        });

        return inflatedView;
    }

}
