package com.youdao.lib.dialogs.manager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;


import com.youdao.lib.dialogs.activity.AboutDialogActivity;
import com.youdao.lib.dialogs.activity.AlertDialogActivity;
import com.youdao.lib.dialogs.activity.AskForLoginActivity;
import com.youdao.lib.dialogs.activity.CameraTipDialog;
import com.youdao.lib.dialogs.activity.DataSetttingDialogActivity;
import com.youdao.lib.dialogs.activity.ExportDialogActivity;
import com.youdao.lib.dialogs.activity.FeedBackDialog;
import com.youdao.lib.dialogs.activity.GradeChooseDialogActivity;
import com.youdao.lib.dialogs.activity.UserHelpDialogActivity;

import java.util.HashMap;

/*
    CustomDialogManager：
        使用样例，见DebugActivity
        使用步骤：
        1.单例获取CustomDialogManager实例                                        (必须)
        2.调用CustomDialogManager.setType(String dialogType)来设置对话框的类型   （必须，否则showDialog方法会开启上一次使用的对话框）
        3.设置对应对话框的属性，监听等                                            （）
        4.调用CustomDialogManager.showDialog(Context context)来创建并显示对话框
 */
public class CustomDialogManager{


    //对应dialogType,表示弹窗的类型，调用showDialog开启弹窗前设置
    public static final String TYPE_ALTERE_DIALOG = "TYPE_ALTERE_DIALOG";
    public static final String TYPE_EXPORT_DIALOG = "TYPE_EXPORT_DIALOG";
    public static final String TYPE_DATA_SETTING_DIALOG = "TYPE_DATA_SETTING_DIALOG";
    public static final String TYPE_GRADE_CHOOSE_DIALOG = "TYPE_GRADE_CHOOSE_DIALOG";
    public static final String TYPE_CAMERA_TIP_DIALOG = "TYPE_CAMERA_TIP_DIALOG";
    public static final String TYPE_FEED_BACK_DIALOG = "TYPE_FEED_BACK_DIALOG";
    public static final String TYPE_ASK_FOR_LOGIN = "TYPE_ASK_FOR_LOGIN";
    public static final String TYPE_USER_HELP = "TYPE_USER_HELP";
    public static final String TYPE_ABOUT = "TYPE_ABOUT";

    //dialog listener的标签，用于辨别同一界面中的不同按钮
    public static final String TAG_ALERT_DIALOG_POSITIVE = "TAG_ALERT_DIALOG_POSITIVE";
    public static final String TAG_ALERT_DIALOG_NEGATIVE = "TAG_ALERT_DIALOG_NEGATIVE";

    public static final String TAG_EXPORT_DIALOG_WECHAT = "TAG_EXPORT_DIALOG_WECHAT";
    public static final String TAG_EXPORT_DIALOG_QQ = "TAG_EXPORT_DIALOG_QQ";
    public static final String TAG_EXPORT_DIALOG_EMAIL = "TAG_EXPORT_DIALOG_EMAIL";
    public static final String TAG_EXPORT_DIALOG_CANCEL = "TAG_EXPORT_DIALOG_CANCEL";

    public static final String TAG_DATA_SETTING_DIALOG_CONFIRM = "TAG_DATA_SETTING_DIALOG_CONFIRM";

    public static final String TAG_GRADE_CHOOSE_DIALOG_POSITIVE = "TAG_GRADE_CHOOSE_DIALOG_POSITIVE";
    public static final String TAG_GRADE_CHOOSE_DIALOG_NEGATIVE = "TAG_GRADE_CHOOSE_DIALOG_NEGATIVE";

    public static final String TAG_FEED_BACK_DIALOG_GOOD = "TAG_FEED_BACK_DIALOG_GOOD";
    public static final String TAG_FEED_BACK_DIALOG_BAD = "TAG_FEED_BACK_DIALOG_BAD";
    public static final String TAG_FEED_BACK_DIALOG_CANCEL = "TAG_FEED_BACK_DIALOG_CANCEL";

    public static final String TAG_ASK_FOR_LOGIN_DIALOG_LOGIN = "TAG_ASK_FOR_LOGIN_DIALOG_LOGIN";
    public static final String TAG_ASK_FOR_LOGIN_DIALOG_SIGN_UP = "TAG_ASK_FOR_LOGIN_DIALOG_SIGN_UP";
    public static final String TAG_ASK_FOR_LOGIN_DIALOG_CANCEL = " TAG_ASK_FOR_LOGIN_DIALOG_CANCEL";


    //用于数据设置弹窗，表示数据格式的弹窗（如邮箱、电话号码等）,目前只有邮箱格式能用;
    public static final String DATA_FORMAT_EMAIL = "DATA_FORMAT_EMAIL";
    public static final String DATA_FORMAT_PHONE_NUMBER = "DATA_FORMAT_PHONE_NUMBER";
    public static final String DATA_FORMAT_NICKNAME = "ATA_FORMAT_NICKNAME";

    //以下： 各类弹窗中的可定制内容（如标题、内容等等）
    //AlertDialog的属性
    private String mAlertDialogTitle ="未设置";
    private String mAlertDialogContent="未设置";
    private String mAlertDialogPosText="未设置";
    private String mAlertDialogNegText="未设置";

    //题目导出对话框的属性
    private String mOutputFileType ="未设置";
    private String mOutputFileName ="未设置";

    //数据设置对话框的属性（邮箱设置等）
    private String mDataSettingButtonText="未设置"; // 可以是“提交”、“上传”、“发送”等;
    private String mDataSettingHint = "未设置"; //输入框内的提示
    private String mDataFormat = DATA_FORMAT_EMAIL; //默认校验格式为邮箱

    //通用的属性（后续待抽取）
    private String mDialogType = "未设置";//指示对话框类型，以便开启对应的Activity
    private boolean isDialogDismissOnTouchOutside = false;//默认点击窗体外部不消失


    //存放监听器的集合
    private HashMap<String,AlertDialogListener> mAlterDialogListeners = new HashMap<String, AlertDialogListener>();
    private HashMap<String,ExportDialogListener> mExportDialogListeners = new HashMap<String, ExportDialogListener>();
    private HashMap<String,OnDataSettingDialogListener> mDataSettingDialogListeners = new HashMap<String, OnDataSettingDialogListener>();
    private HashMap<String,OnGradeChooseDialogListener> mGradeChooseDialogListeners = new HashMap<String, OnGradeChooseDialogListener>();
    private HashMap<String,OnFeedbackDialogListener> mFeedbackDialogListeners = new HashMap<String, OnFeedbackDialogListener>();
    private HashMap<String,OnAskForLoginDialogListener> mAskForLoginDialogListeners =  new HashMap<String,OnAskForLoginDialogListener>();

    //暂时将Manager类封装单例使用
    @SuppressLint("StaticFieldLeak")
    private static volatile CustomDialogManager instance  = null;

    private CustomDialogManager(){
    }

    //DCL获取单例
    public static CustomDialogManager getInstance(){
        if (instance==null){
            synchronized (CustomDialogManager.class){
                if (instance==null){
                    instance = new CustomDialogManager();
                }
            }
        }
        return instance;
    }

    /**
     * 设置弹窗类型
     * @param dialogType 弹窗类型.
     */
    public void setDialogType(String dialogType){
        mDialogType = dialogType;
    }

    /**
     * 创建并显示对话框
     * @param context 使用者调用该方法时所在的上下文环境
     */
    public void showDialog(Context context){
        Intent dialogActivity = null;
        if (mDialogType!=null){
            switch (mDialogType){
                case TYPE_ALTERE_DIALOG:
                    dialogActivity = new Intent(context,AlertDialogActivity.class);
                    break;
                case TYPE_DATA_SETTING_DIALOG:
                    dialogActivity = new Intent(context,DataSetttingDialogActivity.class);
                    break;
                case TYPE_EXPORT_DIALOG:
                    dialogActivity= new Intent(context,ExportDialogActivity.class);
                    break;
                case TYPE_GRADE_CHOOSE_DIALOG:
                    dialogActivity = new Intent(context,GradeChooseDialogActivity.class);
                    break;
                case TYPE_CAMERA_TIP_DIALOG:
                    dialogActivity = new Intent(context,CameraTipDialog.class);
                    break;
                case TYPE_FEED_BACK_DIALOG:
                    dialogActivity = new Intent(context,FeedBackDialog.class);
                    break;
                case TYPE_ASK_FOR_LOGIN:
                    dialogActivity = new Intent(context, AskForLoginActivity.class);
                    break;
                case TYPE_USER_HELP:
                    dialogActivity = new Intent(context, UserHelpDialogActivity.class);
                    break;
                case TYPE_ABOUT:
                    dialogActivity = new Intent(context, AboutDialogActivity.class);
                    break;
                default:
                    Toast.makeText(context,"使用showDialog()之前，请设置正确的DialogType",Toast.LENGTH_SHORT).show();
                    break;

            }
        }
        context.startActivity(dialogActivity);
    }

    //设置监听
    //格式固定的弹窗（标题，内容，确认取消按钮）
    public void setAlertDialogListener( String tag ,AlertDialogListener alertDialogListener){
        mAlterDialogListeners.put(tag,alertDialogListener);
    }
    //题目导出弹窗
    public void setExportDialogListener(String tag,ExportDialogListener exportDialogListener){
        mExportDialogListeners.put(tag,exportDialogListener);
    }
    //数据设置弹窗（如邮箱设置，昵称设置等）
    public void setOnDataSettingDialogListener(String tag, OnDataSettingDialogListener onDataSettingDialogListener){
        mDataSettingDialogListeners.put(tag, onDataSettingDialogListener);
    }

    //年级设置弹窗
    public void setOnGradeSettingDialogListener(String tag,OnGradeChooseDialogListener onGradeChooseDialogListener){
        mGradeChooseDialogListeners.put(tag,onGradeChooseDialogListener);
    }
    //反馈页面弹窗
    public void setOnFeedBackDialogListener(String tag,OnFeedbackDialogListener onFeedbackDialogListener){
        mFeedbackDialogListeners.put(tag,onFeedbackDialogListener);
    }

    public void setOnAskForLoginDialogListener(String tag ,OnAskForLoginDialogListener onAskForLoginDialogListener){
        mAskForLoginDialogListeners.put(tag,onAskForLoginDialogListener);
    }


    //执行监听
    public void performAlertDialogClick(String tag){
        if (mAlterDialogListeners.get(tag)!=null){
            mAlterDialogListeners.get(tag).onAlertDialogClick();
        }
    }

    public void performExportDialogClick(String tag){
        if (mExportDialogListeners.get(tag)!=null){
            mExportDialogListeners.get(tag).onExportDialogClick();
        }
    }


    /**
     *
     * @param tag  标志监听器的种类 ，用于在hashMap中找到对于的对象
     * @param data  用户输入的数据
     */
    public void performDataSettingDialogClick(String tag,String data){
        if (mDataSettingDialogListeners.get(tag)!=null){
            mDataSettingDialogListeners.get(tag).onDataSettingDialogClick(data);
        }
    }

    public void performGradeSettingDialogClick(String tag,String grade){
        if (mGradeChooseDialogListeners.get(tag)!=null){
            mGradeChooseDialogListeners.get(tag).onGradeChooseDialogClick(grade);
        }
    }

    public void performFeedbackDialogClick(String tag){
        if (mFeedbackDialogListeners.get(tag)!=null){
            mFeedbackDialogListeners.get(tag).onFeedbackDialogClick();
        }
    }

    public void performAskForLoginDialogListener(String tag,String account,String password){
        if (mAskForLoginDialogListeners.get(tag)!=null){
            mAskForLoginDialogListeners.get(tag).onAskForLoginClick(account,password);
        }
    }

    //不同风格弹窗监听
    //AlertDialog类型
    public interface AlertDialogListener {
        void onAlertDialogClick();
    }
    //题目导出弹窗
    public interface ExportDialogListener{
        void onExportDialogClick();
    }
    //数据设置弹窗
    public interface OnDataSettingDialogListener {
        void onDataSettingDialogClick(String data);
    }
    //年级设置弹窗
    public interface OnGradeChooseDialogListener {
        void onGradeChooseDialogClick(String grade);
    }
    //反馈弹窗
    public interface OnFeedbackDialogListener{
        void onFeedbackDialogClick();
    }

    public interface OnAskForLoginDialogListener{
        void onAskForLoginClick(String account,String password);
    }


    //各个弹窗的具体设置（set及get方法，get方法供对应的activity获取并设置）,用于各个弹窗的属性设置
    //AlertDialog 属性设置 (setter和getter)
    public void setAlertDialogTitle(String title){
        mAlertDialogTitle = title;
    }

    public void setAlertDialogContent(String content){
        mAlertDialogContent = content;
    }

    public void setAlertDialogPosText(String posText){
        mAlertDialogPosText = posText;
    }

    public void setAlertDialogNegText(String negText){
        mAlertDialogNegText = negText;
    }

    public String getAlertDialogTitle() {
        return mAlertDialogTitle;
    }

    public String getAlertDialogContent() {
        return mAlertDialogContent;
    }

    public String getAlertDialogPosText() {
        return mAlertDialogPosText;
    }

    public String getAlertDialogNegText() {
        return mAlertDialogNegText;
    }

    //导出题目弹窗 属性设置(setter和getter)
    public void setExportFileName(String fileName){
        mOutputFileName = fileName;
    }

    public void setExportFileType(String fileType){
        mOutputFileType = fileType;
    }

    public String getOutputFileType() {
        return mOutputFileType;
    }

    public String getOutputFileName() {
        return mOutputFileName;
    }


    //数据设置弹窗(邮箱) 属性设置(setter和getter)
    public void setDataSettingButtonText(String dataSettingButtonText){
        mDataSettingButtonText = dataSettingButtonText;
    }

    public void setDataSettingHint(String dataSettingHint){
        mDataSettingHint = dataSettingHint;
    }

    public void setDataFormat(String mDataFormat) {
        this.mDataFormat = mDataFormat;
    }

    public String getDataFormat() {
        return mDataFormat;
    }

    public String getDataSettingButtonText() {
        return mDataSettingButtonText;
    }

    public String getDataSettingHint() {
        return mDataSettingHint;
    }


    //通用属性设置(setter和getter)
    //设置点击窗体外部弹窗是否消失
    public void setDialogDismissOnTouchOutside(boolean dismissOnTouchOutside){
        isDialogDismissOnTouchOutside = dismissOnTouchOutside;
    }
    
    public boolean getDialogDismissSetting(){
        return isDialogDismissOnTouchOutside;
    }

    //回收用完的listener
    public void recycleListener(String dialogType){
        if (dialogType==TYPE_ALTERE_DIALOG){
            mAlterDialogListeners.clear();
        }else if(dialogType == TYPE_DATA_SETTING_DIALOG){
            mDataSettingDialogListeners.clear();
        }else if(dialogType == TYPE_EXPORT_DIALOG){
            mExportDialogListeners.clear();
        }else if (dialogType == TYPE_GRADE_CHOOSE_DIALOG){
            mGradeChooseDialogListeners.clear();
        }else if(dialogType == TYPE_FEED_BACK_DIALOG){
            mFeedbackDialogListeners.clear();
        }else if (dialogType == TYPE_ASK_FOR_LOGIN){
            mAskForLoginDialogListeners.clear();
        }
    }

}
