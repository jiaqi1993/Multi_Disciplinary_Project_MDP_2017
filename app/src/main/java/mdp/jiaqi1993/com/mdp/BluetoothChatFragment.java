package mdp.jiaqi1993.com.mdp;
/*
 * Copyright (C) 2014 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.MenuItemCompat;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.lang.reflect.Field;
import java.util.ArrayList;

import static android.graphics.Color.LTGRAY;

//package com.example.jiaqi1993.mdpsocket.bluetoothchat;

//import com.example.jiaqi.mdpsocket.bluetoothchat.R;
//import com.example.jiaqi1993.mdpsocket.common.logger.Log;

/**
 * This fragment controls Bluetooth to communicate with other devices.
 */
public class BluetoothChatFragment extends Fragment {

    private static final String TAG = "BluetoothChatFragment";

    // Intent request codes
    private static final int REQUEST_CONNECT_DEVICE_SECURE = 1;
    private static final int REQUEST_CONNECT_DEVICE_INSECURE = 2;
    private static final int REQUEST_ENABLE_BT = 3;

    // Layout Views
    private ListView mConversationView;
    private EditText mOutEditText;
    private Button mSendButton;

    private Button btnCmd1;
    private Button btnCmd2;
    private Button btnF;
    private Button btnL;
    private Button btnR;
    private TextView tvStatus;
    private TextView timerText;

    private int[] start=new int[9];

    private int pos;
    private char startd='E';
    //waypoint position i
    private int waypointPos;
    private boolean waypointInitialize=false;

    private boolean checkManualmode=false;

    /**
     * Name of the connected device
     */
    private String mConnectedDeviceName = null;

    /**
     * Array adapter for the conversation thread
     */
    private ArrayAdapter<String> mConversationArrayAdapter;

    /**
     * String buffer for outgoing messages
     */
    private StringBuffer mOutStringBuffer;

    /**
     * Local Bluetooth adapter
     */
    private BluetoothAdapter mBluetoothAdapter = null;

    /**
     * Member object for the chat services
     */
    private BluetoothChatService mChatService = null;

    //Prepare start function type for alertbuilder
    private CharSequence colors[] = new CharSequence[] {"Exploration", "Shortest Path"};

    //MapGrid
    private GridView gridView;
    private View btnGo;
    private ArrayList<String> selectedWaypoint;
    private ArrayList<String> selectedStartpoint;

private static final String[] numbers= new String[]{"19,0", "19,1", "19,2", "19,3", "19,4", "19,5", "19,6", "19,7", "19,8", "19,9", "19,10", "19,11", "19,12", "19,13", "19,14", "18,0", "18,1", "18,2", "18,3", "18,4", "18,5", "18,6", "18,7", "18,8", "18,9", "18,10", "18,11", "18,12", "18,13", "18,14", "17,0", "17,1", "17,2", "17,3", "17,4", "17,5", "17,6", "17,7", "17,8", "17,9", "17,10", "17,11", "17,12", "17,13", "17,14", "16,0", "16,1", "16,2", "16,3", "16,4", "16,5", "16,6", "16,7", "16,8", "16,9", "16,10", "16,11", "16,12", "16,13", "16,14", "15,0", "15,1", "15,2", "15,3", "15,4", "15,5", "15,6", "15,7", "15,8", "15,9", "15,10", "15,11", "15,12", "15,13", "15,14", "14,0", "14,1", "14,2", "14,3", "14,4", "14,5", "14,6", "14,7", "14,8", "14,9", "14,10", "14,11", "14,12", "14,13", "14,14", "13,0", "13,1", "13,2", "13,3", "13,4", "13,5", "13,6", "13,7", "13,8", "13,9", "13,10", "13,11", "13,12", "13,13", "13,14", "12,0", "12,1", "12,2", "12,3", "12,4", "12,5", "12,6", "12,7", "12,8", "12,9", "12,10", "12,11", "12,12", "12,13", "12,14", "11,0", "11,1", "11,2", "11,3", "11,4", "11,5", "11,6", "11,7", "11,8", "11,9", "11,10", "11,11", "11,12", "11,13", "11,14", "10,0", "10,1", "10,2", "10,3", "10,4", "10,5", "10,6", "10,7", "10,8", "10,9", "10,10", "10,11", "10,12", "10,13", "10,14", "9,0", "9,1", "9,2", "9,3", "9,4", "9,5", "9,6", "9,7", "9,8", "9,9", "9,10", "9,11", "9,12", "9,13", "9,14", "8,0", "8,1", "8,2", "8,3", "8,4", "8,5", "8,6", "8,7", "8,8", "8,9", "8,10", "8,11", "8,12", "8,13", "8,14", "7,0", "7,1", "7,2", "7,3", "7,4", "7,5", "7,6", "7,7", "7,8", "7,9", "7,10", "7,11", "7,12", "7,13", "7,14", "6,0", "6,1", "6,2", "6,3", "6,4", "6,5", "6,6", "6,7", "6,8", "6,9", "6,10", "6,11", "6,12", "6,13", "6,14", "5,0", "5,1", "5,2", "5,3", "5,4", "5,5", "5,6", "5,7", "5,8", "5,9", "5,10", "5,11", "5,12", "5,13", "5,14", "4,0", "4,1", "4,2", "4,3", "4,4", "4,5", "4,6", "4,7", "4,8", "4,9", "4,10", "4,11", "4,12", "4,13", "4,14", "3,0", "3,1", "3,2", "3,3", "3,4", "3,5", "3,6", "3,7", "3,8", "3,9", "3,10", "3,11", "3,12", "3,13", "3,14", "2,0", "2,1", "2,2", "2,3", "2,4", "2,5", "2,6", "2,7", "2,8", "2,9", "2,10", "2,11", "2,12", "2,13", "2,14", "1,0", "1,1", "1,2", "1,3", "1,4", "1,5", "1,6", "1,7", "1,8", "1,9", "1,10", "1,11", "1,12", "1,13", "1,14", "0,0", "0,1", "0,2", "0,3", "0,4", "0,5", "0,6", "0,7", "0,8", "0,9", "0,10", "0,11", "0,12", "0,13", "0,14"};
    //private static  final String[] numbers=new String[]{"0,0", "0,1", "0,2", "0,3", "0,4", "0,5", "0,6", "0,7", "0,8", "0,9", "0,10", "0,11", "0,12", "0,13", "0,14", "1,0", "1,1", "1,2", "1,3", "1,4", "1,5", "1,6", "1,7", "1,8", "1,9", "1,10", "1,11", "1,12", "1,13", "1,14", "2,0", "2,1", "2,2", "2,3", "2,4", "2,5", "2,6", "2,7", "2,8", "2,9", "2,10", "2,11", "2,12", "2,13", "2,14", "3,0", "3,1", "3,2", "3,3", "3,4", "3,5", "3,6", "3,7", "3,8", "3,9", "3,10", "3,11", "3,12", "3,13", "3,14", "4,0", "4,1", "4,2", "4,3", "4,4", "4,5", "4,6", "4,7", "4,8", "4,9", "4,10", "4,11", "4,12", "4,13", "4,14", "5,0", "5,1", "5,2", "5,3", "5,4", "5,5", "5,6", "5,7", "5,8", "5,9", "5,10", "5,11", "5,12", "5,13", "5,14", "6,0", "6,1", "6,2", "6,3", "6,4", "6,5", "6,6", "6,7", "6,8", "6,9", "6,10", "6,11", "6,12", "6,13", "6,14", "7,0", "7,1", "7,2", "7,3", "7,4", "7,5", "7,6", "7,7", "7,8", "7,9", "7,10", "7,11", "7,12", "7,13", "7,14", "8,0", "8,1", "8,2", "8,3", "8,4", "8,5", "8,6", "8,7", "8,8", "8,9", "8,10", "8,11", "8,12", "8,13", "8,14", "9,0", "9,1", "9,2", "9,3", "9,4", "9,5", "9,6", "9,7", "9,8", "9,9", "9,10", "9,11", "9,12", "9,13", "9,14", "10,0", "10,1", "10,2", "10,3", "10,4", "10,5", "10,6", "10,7", "10,8", "10,9", "10,10", "10,11", "10,12", "10,13", "10,14", "11,0", "11,1", "11,2", "11,3", "11,4", "11,5", "11,6", "11,7", "11,8", "11,9", "11,10", "11,11", "11,12", "11,13", "11,14", "12,0", "12,1", "12,2", "12,3", "12,4", "12,5", "12,6", "12,7", "12,8", "12,9", "12,10", "12,11", "12,12", "12,13", "12,14", "13,0", "13,1", "13,2", "13,3", "13,4", "13,5", "13,6", "13,7", "13,8", "13,9", "13,10", "13,11", "13,12", "13,13", "13,14", "14,0", "14,1", "14,2", "14,3", "14,4", "14,5", "14,6", "14,7", "14,8", "14,9", "14,10", "14,11", "14,12", "14,13", "14,14", "15,0", "15,1", "15,2", "15,3", "15,4", "15,5", "15,6", "15,7", "15,8", "15,9", "15,10", "15,11", "15,12", "15,13", "15,14", "16,0", "16,1", "16,2", "16,3", "16,4", "16,5", "16,6", "16,7", "16,8", "16,9", "16,10", "16,11", "16,12", "16,13", "16,14", "17,0", "17,1", "17,2", "17,3", "17,4", "17,5", "17,6", "17,7", "17,8", "17,9", "17,10", "17,11", "17,12", "17,13", "17,14", "18,0", "18,1", "18,2", "18,3", "18,4", "18,5", "18,6", "18,7", "18,8", "18,9", "18,10", "18,11", "18,12", "18,13", "18,14", "19,0", "19,1", "19,2", "19,3", "19,4", "19,5", "19,6", "19,7", "19,8", "19,9", "19,10", "19,11", "19,12", "19,13", "19,14"};

    //CharSequence options[] = new CharSequence[] {"Set as Startpoint", "Set as Waypoint", "Set as obstacle"};


    ProgressDialog pd ;

    //MapGrid end

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        //Get Progressdialog
        pd = new ProgressDialog(getActivity());

        // Get local Bluetooth adapter
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();


        // If the adapter is null, then Bluetooth is not supported
        if (mBluetoothAdapter == null) {
            FragmentActivity activity = getActivity();
            Toast.makeText(activity, "Bluetooth is not available", Toast.LENGTH_LONG).show();
            activity.finish();
        }
    }


    @Override
    public void onStart() {
        super.onStart();
        // If BT is not on, request that it be enabled.
        // setupChat() will then be called during onActivityResult
        if (!mBluetoothAdapter.isEnabled()) {
            Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableIntent, REQUEST_ENABLE_BT);
            // Otherwise, setup the chat session
        } else if (mChatService == null) {
            setupChat();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mChatService != null) {
            mChatService.stop();
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        // Performing this check in onResume() covers the case in which BT was
        // not enabled during onStart(), so we were paused to enable it...
        // onResume() will be called when ACTION_REQUEST_ENABLE activity returns.
        if (mChatService != null) {
            // Only if the state is STATE_NONE, do we know that we haven't started already
            if (mChatService.getState() == BluetoothChatService.STATE_NONE) {
                // Start the Bluetooth chat services
                mChatService.start();
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_bluetooth_chat, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        mConversationView = (ListView) view.findViewById(R.id.in);
        mOutEditText = (EditText) view.findViewById(R.id.edit_text_out);
        mSendButton = (Button) view.findViewById(R.id.button_send);
        //Create 2 buttons for persistence String CMD
        btnCmd1 = (Button) view.findViewById(R.id.btnCmd1);
        btnCmd2 = (Button) view.findViewById(R.id.btnCmd2);
        gridView = (GridView) view.findViewById(R.id.grid);
        btnF= (Button)view.findViewById(R.id.btnForward);
        btnL= (Button)view.findViewById(R.id.btnLeft);
        btnR= (Button)view.findViewById(R.id.btnRight);
        tvStatus=(TextView)view.findViewById(R.id.textStatus);
    }



    public void createMapGrid(){
        selectedWaypoint = new ArrayList<>();
        selectedStartpoint = new ArrayList<>();

        final GridViewAdapter adapter = new GridViewAdapter(numbers, getActivity());
        gridView.setVerticalScrollBarEnabled(false);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(final AdapterView<?> parent, final View v, final int position, long id) {
                int selectedIndex = adapter.selectedPositions.indexOf(position);
                if (selectedIndex > -1) {
                    adapter.selectedPositions.remove(selectedIndex);
                    ((GridItemView) v).display(false);
                    selectedWaypoint.remove((String) parent.getItemAtPosition(position));
                } else {
                    /*adapter.selectedPositions.add(position);
                    ((GridItemView) v).display(true);
                    selectedStrings.add((String) parent.getItemAtPosition(position));*/
                    //selectedStrings.add((String) parent.getItemAtPosition(position));
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
                    alertDialogBuilder.setMessage("You have selected "+"("+(String) parent.getItemAtPosition(position)+")");

                    //Check if the current position is at the border
                    String[] posArr=((String) parent.getItemAtPosition(position)).split(",");

                    if(!checkManualmode) {
                        if (!posArr[1].equals("0") & !posArr[0].equals("19") & !posArr[0].equals("0") & !posArr[1].equals("14")) {
                            alertDialogBuilder.setNegativeButton("Set as Startpoint",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface arg0, int arg1) {
                                            adapter.selectedPositions.add(position);
                                            //((GridItemView) v).display(true);
                                            selectedStartpoint.add((String) parent.getItemAtPosition(position));

                                            pos = position;
                                            start[0] = position;
                                            start[1] = position - 1;
                                            start[2] = position + 1;
                                            start[3] = position - 14;
                                            start[4] = position - 15;
                                            start[5] = position - 16;
                                            start[6] = position + 14;
                                            start[7] = position + 15;
                                            start[8] = position + 16;
                                            //position+1,position-14,position-15,position-16,position+14,position+15,position+16};

                                            for (int i = 0; i < start.length; i++) {
                                                TextView test = (TextView) gridView.getChildAt(start[i]).findViewById(R.id.text);
                                                test.setBackgroundColor(Color.BLUE);
                                            }

                                            TextView startDTv;
                                            switch (startd) {
                                                case 'E':
                                                    startDTv = (TextView) gridView.getChildAt(pos + 1).findViewById(R.id.text);
                                                    startDTv.setBackgroundColor(Color.YELLOW);
                                                    break;
                                                case 'W':
                                                    startDTv = (TextView) gridView.getChildAt(pos - 1).findViewById(R.id.text);
                                                    startDTv.setBackgroundColor(Color.YELLOW);
                                                    break;
                                                case 'N':
                                                    startDTv = (TextView) gridView.getChildAt(pos - 15).findViewById(R.id.text);
                                                    startDTv.setBackgroundColor(Color.YELLOW);
                                                    break;
                                                case 'S':
                                                    startDTv = (TextView) gridView.getChildAt(pos + 15).findViewById(R.id.text);
                                                    startDTv.setBackgroundColor(Color.YELLOW);
                                                    break;


                                            }
                                        }
                                    });

                        }

                        alertDialogBuilder.setNeutralButton("Set as Waypoint", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //Toast.makeText(Map.this,"You clicked yes button",Toast.LENGTH_LONG).show();
                                //adapter.selectedPositions.add(position);
                                //((GridItemView) v).display(true);
                                TextView wptv;
                                if (waypointInitialize) {
                                    wptv = (TextView) gridView.getChildAt(waypointPos).findViewById(R.id.text);
                                    wptv.setBackgroundColor(Color.LTGRAY);
                                }
                                ((GridItemView) v).waypoint(true);
                                waypointPos = position;
                                waypointInitialize = true;

                                //selectedWaypoint.add((String) parent.getItemAtPosition(position));
                                //Toast.makeText(Map.this,"You clicked yes button"+selectedWaypoint.get(0),Toast.LENGTH_LONG).show();


                            }
                        });


                        alertDialogBuilder.setPositiveButton("Back", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                    }else {
                        final TextView tv=(TextView) gridView.getChildAt(position).findViewById(R.id.text);
                        if(getBackgroundColor(tv)==Color.BLACK){
                            alertDialogBuilder.setNegativeButton("Remove Obstacle", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    tv.setBackgroundColor(Color.LTGRAY);
                                    //Send Obstacle info here...
                                }
                            });
                            alertDialogBuilder.setPositiveButton("Back", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            });
                        }else{
                            alertDialogBuilder.setNegativeButton("Set as Obstacle", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    tv.setBackgroundColor(Color.BLACK);
                                    //Send Obstacle info here...
                                }
                            });
                            alertDialogBuilder.setPositiveButton("Back", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            });
                        }

                    }

                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                }

                }
            });
    }
    public void updateArray(int position){
        start[0]=position;
        start[1]=position-1;
        start[2]=position+1;
        start[3]=position-14;
        start[4]=position-15;
        start[5]=position-16;
        start[6]=position+14;
        start[7]=position+15;
        start[8]=position+16;
    }

    //Method to check if the robot is facing any obstacles/border
    public boolean checkArrayMovable(){
        TextView tv;

        for(int i=0;i<start.length;i++){
            Log.d("i index", i + " position " + start[i]);
            if (start[3]%15==0 || start[4]%15==0 || start[i]<0 || start[i]>299){
                return false;
            }
            tv=(TextView) gridView.getChildAt(start[i]).findViewById(R.id.text);
            if(getBackgroundColor(tv)==Color.BLACK) {
                Log.d("checkarray","false");
                return false;

            }

        }
        return true;
    }

    public static int getBackgroundColor(TextView textView) {
        ColorDrawable drawable = (ColorDrawable) textView.getBackground();
        if (Build.VERSION.SDK_INT >= 11) {
            return drawable.getColor();
        }
        try {
            Field field = drawable.getClass().getDeclaredField("mState");
            field.setAccessible(true);
            Object object = field.get(drawable);
            field = object.getClass().getDeclaredField("mUseColor");
            field.setAccessible(true);
            return field.getInt(object);
        } catch (Exception e) {
            // TODO: handle exception
        }
        return 0;
    }







    /**
     * Set up the UI and background operations for chat.
     */
    private void setupChat() {
        Log.d(TAG, "setupChat()");

        // Initialize the array adapter for the conversation thread
        mConversationArrayAdapter = new ArrayAdapter<String>(getActivity(), R.layout.message);

        mConversationView.setAdapter(mConversationArrayAdapter);

        // Initialize the compose field with a listener for the return key
        mOutEditText.setOnEditorActionListener(mWriteListener);

        // Initialize the send button with a listener that for click events
        mSendButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Send a message using content of the edit text widget
                View view = getView();

                TextView textView = (TextView) view.findViewById(R.id.edit_text_out);
                if(textView.getText().toString().startsWith("cmd1")){
                    SharedPreferences.Editor editor = getActivity().getPreferences(Context.MODE_PRIVATE).edit();
                    editor.putString("cmd1", textView.getText().toString().substring(4));
                    editor.apply();
                    Toast.makeText(getActivity(), "Command1 Updated", Toast.LENGTH_SHORT).show();
                }else if(textView.getText().toString().startsWith("cmd2")){
                    SharedPreferences.Editor editor = getActivity().getPreferences(Context.MODE_PRIVATE).edit();
                    editor.putString("cmd2", textView.getText().toString().substring(4));
                    editor.apply();
                    Toast.makeText(getActivity(), "Command2 Updated", Toast.LENGTH_SHORT).show();
                }else{
                    if (null != view) {
                        String message = textView.getText().toString();
                        sendMessage(message);
                    }
                }

            }
        });

        btnCmd1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Send a message using content of the edit text widget
                SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
                String restoredText = sharedPref.getString("cmd1", null);
                if (restoredText != null) {
                    sendMessage(restoredText);
                }
                else{
                    Toast.makeText(getActivity(), "Command Undefined", Toast.LENGTH_SHORT).show();
                }

            }
        });
        btnCmd2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Send a message using content of the edit text widget
                SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
                String restoredText = sharedPref.getString("cmd2", null);
                if (restoredText != null) {
                    sendMessage(restoredText);
                }
                else{
                    Toast.makeText(getActivity(), "Command Undefined", Toast.LENGTH_SHORT).show();
                }

            }
        });

        btnF.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                pd.setMessage("Moving Forward");
                pd.show();
                tvStatus.setText("Robot is Moving Forward");
                final Handler handler = new Handler();
                final int previousPos=pos;
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        for(int i=0;i<start.length;i++) {
                            TextView test = (TextView) gridView.getChildAt(start[i]).findViewById(R.id.text);
                            test.setBackgroundColor(LTGRAY);
                        }

                        switch (startd)
                        {
                            case 'N':
                                pos=pos-15;
                                break;
                            case 'S':
                                pos=pos+15;
                                break;
                            case 'E':
                                pos=pos+1;
                                break;
                            case 'W':
                                pos=pos-1;
                                break;
                        }

                        updateArray(pos);
                        boolean status=checkArrayMovable();
                        if(!status){
                            Toast.makeText(getActivity(), "Invalid Move, Obstacle detected", Toast.LENGTH_SHORT).show();
                            pos=previousPos;
                            updateArray(pos);
                        }

                        for(int i=0;i<start.length;i++) {
                            TextView test = (TextView) gridView.getChildAt(start[i]).findViewById(R.id.text);
                            test.setBackgroundColor(Color.BLUE);
                        }

                        TextView startDTv;
                        switch (startd){
                            case 'E':
                                startDTv = (TextView) gridView.getChildAt(pos+1).findViewById(R.id.text);
                                startDTv.setBackgroundColor(Color.YELLOW);
                                break;
                            case 'W':
                                startDTv = (TextView) gridView.getChildAt(pos-1).findViewById(R.id.text);
                                startDTv.setBackgroundColor(Color.YELLOW);
                                break;
                            case 'N':
                                startDTv = (TextView) gridView.getChildAt(pos-15).findViewById(R.id.text);
                                startDTv.setBackgroundColor(Color.YELLOW);
                                break;
                            case 'S':
                                startDTv = (TextView) gridView.getChildAt(pos+15).findViewById(R.id.text);
                                startDTv.setBackgroundColor(Color.YELLOW);
                                break;
                        }

                        pd.dismiss();
                        tvStatus.setText("Robot Ready for Action");
                    }
                }, 100);
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
                        TextView startDTv;
                        switch (startd){
                            case 'E':
                                startDTv = (TextView) gridView.getChildAt(pos+1).findViewById(R.id.text);
                                startDTv.setBackgroundColor(Color.BLUE);
                                startDTv = (TextView) gridView.getChildAt(pos-15).findViewById(R.id.text);
                                startDTv.setBackgroundColor(Color.YELLOW);
                                startd='N';
                                break;
                            case 'W':
                                startDTv = (TextView) gridView.getChildAt(pos-1).findViewById(R.id.text);
                                startDTv.setBackgroundColor(Color.BLUE);
                                startDTv = (TextView) gridView.getChildAt(pos+15).findViewById(R.id.text);
                                startDTv.setBackgroundColor(Color.YELLOW);
                                startd='S';
                                break;
                            case 'N':
                                startDTv = (TextView) gridView.getChildAt(pos-15).findViewById(R.id.text);
                                startDTv.setBackgroundColor(Color.BLUE);
                                startDTv = (TextView) gridView.getChildAt(pos-1).findViewById(R.id.text);
                                startDTv.setBackgroundColor(Color.YELLOW);
                                startd='W';
                                break;
                            case 'S':
                                startDTv = (TextView) gridView.getChildAt(pos+15).findViewById(R.id.text);
                                startDTv.setBackgroundColor(Color.BLUE);
                                startDTv = (TextView) gridView.getChildAt(pos+1).findViewById(R.id.text);
                                startDTv.setBackgroundColor(Color.YELLOW);
                                startd='E';
                                break;

                        }

                        pd.dismiss();
                        tvStatus.setText("Robot Ready for Action");
                    }
                }, 100);
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
                        TextView startDTv;
                        switch (startd){
                            case 'E':
                                startDTv = (TextView) gridView.getChildAt(pos+1).findViewById(R.id.text);
                                startDTv.setBackgroundColor(Color.BLUE);
                                startDTv = (TextView) gridView.getChildAt(pos+15).findViewById(R.id.text);
                                startDTv.setBackgroundColor(Color.YELLOW);
                                startd='S';
                                break;
                            case 'W':
                                startDTv = (TextView) gridView.getChildAt(pos-1).findViewById(R.id.text);
                                startDTv.setBackgroundColor(Color.BLUE);
                                startDTv = (TextView) gridView.getChildAt(pos-15).findViewById(R.id.text);
                                startDTv.setBackgroundColor(Color.YELLOW);
                                startd='N';
                                break;
                            case 'N':
                                startDTv = (TextView) gridView.getChildAt(pos-15).findViewById(R.id.text);
                                startDTv.setBackgroundColor(Color.BLUE);
                                startDTv = (TextView) gridView.getChildAt(pos+1).findViewById(R.id.text);
                                startDTv.setBackgroundColor(Color.YELLOW);
                                startd='E';
                                break;
                            case 'S':
                                startDTv = (TextView) gridView.getChildAt(pos+15).findViewById(R.id.text);
                                startDTv.setBackgroundColor(Color.BLUE);
                                startDTv = (TextView) gridView.getChildAt(pos-1).findViewById(R.id.text);
                                startDTv.setBackgroundColor(Color.YELLOW);
                                startd='W';
                                break;

                        }
                        pd.dismiss();
                        tvStatus.setText("Robot Ready for Action");
                    }
                }, 100);
            }
        });

        createMapGrid();


        // Initialize the BluetoothChatService to perform bluetooth connections
        mChatService = new BluetoothChatService(getActivity(), mHandler);

        // Initialize the buffer for outgoing messages
        mOutStringBuffer = new StringBuffer("");
    }

    /**
     * Makes this device discoverable for 300 seconds (5 minutes).
     */
    private void ensureDiscoverable() {
        if (mBluetoothAdapter.getScanMode() !=
                BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE) {
            Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
            discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
            startActivity(discoverableIntent);
        }
    }

    /**
     * Sends a message.
     *
     * @param message A string of text to send.
     */
    private void sendMessage(String message) {
        // Check that we're actually connected before trying anything
        if (mChatService.getState() != BluetoothChatService.STATE_CONNECTED) {
            Toast.makeText(getActivity(), R.string.not_connected, Toast.LENGTH_SHORT).show();
            return;
        }

        // Check that there's actually something to send
        if (message.length() > 0) {
            // Get the message bytes and tell the BluetoothChatService to write
            byte[] send = message.getBytes();
            mChatService.write(send);

            // Reset out string buffer to zero and clear the edit text field
            mOutStringBuffer.setLength(0);
            mOutEditText.setText(mOutStringBuffer);
        }
    }


    /**
     * The action listener for the EditText widget, to listen for the return key
     */
    private TextView.OnEditorActionListener mWriteListener
            = new TextView.OnEditorActionListener() {
        public boolean onEditorAction(TextView view, int actionId, KeyEvent event) {
            // If the action is a key-up event on the return key, send the message
            if (actionId == EditorInfo.IME_NULL && event.getAction() == KeyEvent.ACTION_UP) {
                String message = view.getText().toString();
                sendMessage(message);
            }
            return true;
        }
    };

    /**
     * Updates the status on the action bar.
     *
     * @param resId a string resource ID
     */
    private void setStatus(int resId) {
        FragmentActivity activity = getActivity();
        if (null == activity) {
            return;
        }
        final ActionBar actionBar = activity.getActionBar();
        if (null == actionBar) {
            return;
        }
        actionBar.setSubtitle(resId);
    }

    /**
     * Updates the status on the action bar.
     *
     * @param subTitle status
     */
    private void setStatus(CharSequence subTitle) {
        FragmentActivity activity = getActivity();
        if (null == activity) {
            return;
        }
        final ActionBar actionBar = activity.getActionBar();
        if (null == actionBar) {
            return;
        }
        actionBar.setSubtitle(subTitle);
    }

    /**
     * The Handler that gets information back from the BluetoothChatService
     */
    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            FragmentActivity activity = getActivity();
            switch (msg.what) {
                case Constants.MESSAGE_STATE_CHANGE:
                    switch (msg.arg1) {
                        case BluetoothChatService.STATE_CONNECTED:
                            setStatus(getString(R.string.title_connected_to, mConnectedDeviceName));
                            mConversationArrayAdapter.clear();
                            break;
                        case BluetoothChatService.STATE_CONNECTING:
                            setStatus(R.string.title_connecting);
                            break;
                        case BluetoothChatService.STATE_LISTEN:
                        case BluetoothChatService.STATE_NONE:
                            setStatus(R.string.title_not_connected);
                            break;
                    }
                    break;
                case Constants.MESSAGE_WRITE:
                    byte[] writeBuf = (byte[]) msg.obj;
                    // construct a string from the buffer
                    String writeMessage = new String(writeBuf);
                    mConversationArrayAdapter.add("Me:  " + writeMessage);
                    break;
                case Constants.MESSAGE_READ:
                    byte[] readBuf = (byte[]) msg.obj;
                    // construct a string from the valid bytes in the buffer
                    String readMessage = new String(readBuf, 0, msg.arg1);
                    mConversationArrayAdapter.add(mConnectedDeviceName + ":  " + readMessage);
                    break;
                case Constants.MESSAGE_DEVICE_NAME:
                    // save the connected device's name
                    mConnectedDeviceName = msg.getData().getString(Constants.DEVICE_NAME);
                    if (null != activity) {
                        Toast.makeText(activity, "Connected to "
                                + mConnectedDeviceName, Toast.LENGTH_SHORT).show();
                    }
                    break;
                case Constants.MESSAGE_TOAST:
                    if (null != activity) {
                        Toast.makeText(activity, msg.getData().getString(Constants.TOAST),
                                Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
        }
    };

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_CONNECT_DEVICE_SECURE:
                // When DeviceListActivity returns with a device to connect
                if (resultCode == Activity.RESULT_OK) {
                    connectDevice(data, true);
                }
                break;
            case REQUEST_CONNECT_DEVICE_INSECURE:
                // When DeviceListActivity returns with a device to connect
                if (resultCode == Activity.RESULT_OK) {
                    connectDevice(data, false);
                }
                break;
            case REQUEST_ENABLE_BT:
                // When the request to enable Bluetooth returns
                if (resultCode == Activity.RESULT_OK) {
                    // Bluetooth is now enabled, so set up a chat session
                    setupChat();
                } else {
                    // User did not enable Bluetooth or an error occurred
                    Log.d(TAG, "BT not enabled");
                    Toast.makeText(getActivity(), R.string.bt_not_enabled_leaving,
                            Toast.LENGTH_SHORT).show();
                    getActivity().finish();
                }
        }
    }

    /**
     * Establish connection with other device
     *
     * @param data   An {@link Intent} with {@link DeviceListActivity#EXTRA_DEVICE_ADDRESS} extra.
     * @param secure Socket Security type - Secure (true) , Insecure (false)
     */
    private void connectDevice(Intent data, boolean secure) {
        // Get the device MAC address
        String address = data.getExtras()
                .getString(DeviceListActivity.EXTRA_DEVICE_ADDRESS);
        // Get the BluetoothDevice object
        BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(address);
        // Attempt to connect to the device
        mChatService.connect(device, secure);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.bluetooth_chat, menu);
        MenuItem timerItem = menu.findItem(R.id.break_timer);
        timerText = (TextView) MenuItemCompat.getActionView(timerItem);

        timerText.setPadding(10, 0, 10, 0); //Or something like that...
        //startTimer(30000, 1000);

    }
    long timeInMilliseconds = 0L;
    long timeSwapBuff = 0L;
    long updatedTime = 0L;
    private Handler customHandler = new Handler();
    private long startTime = 0L;

    private Runnable updateTimerThread = new Runnable() {

        public void run() {

            timeInMilliseconds = SystemClock.uptimeMillis() - startTime;

            updatedTime = timeSwapBuff + timeInMilliseconds;

            int secs = (int) (updatedTime / 1000);
            int mins = secs / 60;
            secs = secs % 60;
            int milliseconds = (int) (updatedTime % 1000);
            timerText.setText("" + mins + ":"
                    + String.format("%02d", secs) + ":"
                    + String.format("%03d", milliseconds));
            customHandler.postDelayed(this, 0);
        }

    };



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.start_function:{
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Pick One");
                builder.setItems(colors, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch(which){
                            case 0: //sendMessage("Explore");
                                startTime = SystemClock.uptimeMillis();
                                customHandler.postDelayed(updateTimerThread, 0);
                                break;
                            case 1: //sendMessage("Shortest Path");
                                //timeSwapBuff += timeInMilliseconds;
                                customHandler.removeCallbacks(updateTimerThread);

                                break;
                        }
                    }
                });
                builder.show();
                return true;
            }
            case R.id.manual_function:{
                if(!item.isChecked()) {
                    item.setChecked(true);
                    Toast.makeText(getActivity(), "Please select obstacle on map", Toast.LENGTH_LONG).show();
                    checkManualmode=true;

                }else {
                    item.setChecked(true);
                    Toast.makeText(getActivity(), "Exit Edit Mode", Toast.LENGTH_LONG).show();
                    checkManualmode=false;
                }
                break;

            }
            case R.id.secure_connect_scan: {
                // Launch the DeviceListActivity to see devices and do scan
                Intent serverIntent = new Intent(getActivity(), DeviceListActivity.class);
                startActivityForResult(serverIntent, REQUEST_CONNECT_DEVICE_SECURE);
                return true;
            }
            case R.id.insecure_connect_scan: {
                // Launch the DeviceListActivity to see devices and do scan
                Intent serverIntent = new Intent(getActivity(), DeviceListActivity.class);
                startActivityForResult(serverIntent, REQUEST_CONNECT_DEVICE_INSECURE);
                return true;
            }
            case R.id.discoverable: {
                // Ensure this device is discoverable by others
                ensureDiscoverable();
                return true;
            }
        }
        return false;
    }

}
