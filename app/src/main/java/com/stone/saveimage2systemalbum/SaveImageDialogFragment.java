package com.stone.saveimage2systemalbum;

import android.Manifest;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.stone.saveimage2systemalbum.album.AlbumManager;
import com.stone.saveimage2systemalbum.album.DownloadCallback;

/**
 * @author stone
 * @date 17/3/24
 */
public class SaveImageDialogFragment extends DialogFragment implements View.OnClickListener {

    private FragmentActivity context;

    public static void show(FragmentActivity context, String imageUrl) {
        SaveImageDialogFragment fragment = new SaveImageDialogFragment();
        fragment.context = context;
        Bundle bundle = new Bundle();
        bundle.putString("data", imageUrl);
        fragment.setArguments(bundle);
        fragment.show(fragment.context.getSupportFragmentManager(), "dialog");
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        final Window window = dialog.getWindow();
        final WindowManager.LayoutParams lp = window.getAttributes();
        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                window.setGravity(Gravity.BOTTOM);
                lp.windowAnimations = R.style.SaveImageDialogTheme;

                //要改变dialog的宽度, 必须在dialog.show()之后设置宽度才能生效
                //solution1
                /**
                lp.width = getScreenWidth();
                lp.horizontalMargin = 0.0f;
                lp.verticalMargin = 0.0f;
                window.setAttributes(lp);
                 */

                //solution2
                window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
            }
        });
        return dialog;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.dialog_save_image, container, false);
        rootView.findViewById(R.id.save_btn).setOnClickListener(this);
        rootView.findViewById(R.id.cancel_btn).setOnClickListener(this);
        return rootView;
    }


    private boolean saveButtonClicked = false;

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.save_btn:
                if(saveButtonClicked == false) {
                    saveButtonClicked = true;
                    checkPermission();
                }
                break;
            case R.id.cancel_btn:
                dismiss();
                break;
        }
    }

    private void checkPermission() {
        int result = ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if(result == PackageManager.PERMISSION_GRANTED) {
            downloadImage();
        } else {
            dismiss();
            saveButtonClicked = false;
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0x10);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == 0x10 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            downloadImage();
        } else {
            Toast.makeText(getActivity(), "您拒绝了访问存储设备", Toast.LENGTH_SHORT).show();
        }
    }

    private void downloadImage() {
        AlbumManager.download(getData(), new DownloadCallback() {
            public void onDownloadCompleted(boolean downloadResult) {
                saveButtonClicked = false;
                if(getActivity() == null || !isAdded()) return;
                dismiss();
                Toast.makeText(getActivity(), downloadResult ? "图片保持成功" : "图片保持失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private String getData() {
        return getArguments().getString("data", "");
    }

    public int getScreenWidth() {
        DisplayMetrics dm = getActivity().getResources().getDisplayMetrics();
        return dm.widthPixels;
    }
}
