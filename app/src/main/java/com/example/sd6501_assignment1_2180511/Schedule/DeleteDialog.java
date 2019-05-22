//package com.example.sd6501_assignment1_2180511.Schedule;
//
//import android.content.Context;
//import android.os.Bundle;
//import android.support.annotation.NonNull;
//import android.support.annotation.Nullable;
//import android.support.v4.app.DialogFragment;
//import android.support.v4.app.FragmentActivity;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.TextView;
//
//import com.example.sd6501_assignment1_2180511.Home.HomeFragment;
//import com.example.sd6501_assignment1_2180511.R;
//
//public class DeleteDialog extends DialogFragment {
//    private static final String TAG = "DeleteDialog";
//
//    public interface OnInputListener{
//        void deleteSchedule(int i);
//    }
//
//    public OnInputListener onInputListener;
//    private TextView actionOK, actionCancel;
//
//    @Nullable
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        View v = inflater.inflate(R.layout.layout_dialog_delete, container, false);
//        actionOK = v.findViewById(R.id.dialog_tv_ok);
//        actionCancel = v.findViewById(R.id.dialog_tv_cancel);
//        setCancelable(false);
//
//        actionCancel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Log.d(TAG, "onClick: you pressed cancel, close dialog");
//                getDialog().dismiss();
//            }
//        });
//
//        actionOK.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Log.d(TAG, "onClick: you pressed delete, deleting item");
//                onInputListener.deleteSchedule();
//                getDialog().dismiss();
//            }
//        });
//
//        return v;
//    }
//
//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        try { onInputListener = (OnInputListener) getActivity();
//        } catch(ClassCastException e) {
//            Log.e(TAG, "onAttach: You have class not found exception" + e.getMessage() );
//        }
//    }
//}
