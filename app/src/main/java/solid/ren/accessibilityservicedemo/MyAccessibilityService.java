package solid.ren.accessibilityservicedemo;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import java.util.List;

/**
 * Created by _SOLID
 * Date:2016/7/20
 * Time:16:19
 */
public class MyAccessibilityService extends AccessibilityService {

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        log("-------------------------------------------------------------");
        log("onAccessibilityEvent");
        int eventType = event.getEventType();
        log("packageName:" + event.getPackageName() + "");
        log("source class:" + event.getClassName() + "");
        log("event type(int):" + eventType + "");


        switch (eventType) {
            case AccessibilityEvent.TYPE_NOTIFICATION_STATE_CHANGED:// 通知栏事件
                log("event type:TYPE_NOTIFICATION_STATE_CHANGED");
                break;
            case AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED://窗体状态改变
                log("event type:TYPE_WINDOW_STATE_CHANGED");
                break;
            case AccessibilityEvent.TYPE_VIEW_ACCESSIBILITY_FOCUSED://View获取到焦点
                log("event type:TYPE_VIEW_ACCESSIBILITY_FOCUSED");
                break;
            case AccessibilityEvent.TYPE_GESTURE_DETECTION_START:
                log("event type:TYPE_VIEW_ACCESSIBILITY_FOCUSED");
                break;
            case AccessibilityEvent.TYPE_GESTURE_DETECTION_END:
                log("event type:TYPE_GESTURE_DETECTION_END");
                break;
            case AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED:
                log("event type:TYPE_WINDOW_CONTENT_CHANGED");
                break;
            case AccessibilityEvent.TYPE_VIEW_CLICKED:
                log("event type:TYPE_VIEW_CLICKED");
                break;
            case AccessibilityEvent.TYPE_VIEW_TEXT_CHANGED:
                log("event type:TYPE_VIEW_TEXT_CHANGED");
                break;
            case AccessibilityEvent.TYPE_VIEW_SCROLLED:
                log("event type:TYPE_VIEW_SCROLLED");
                break;
            case AccessibilityEvent.TYPE_VIEW_TEXT_SELECTION_CHANGED:
                log("event type:TYPE_VIEW_TEXT_SELECTION_CHANGED");
                break;
        }

        for (CharSequence txt : event.getText()) {
            log("text:" + txt);
        }

        log("-------------------------------------------------------------");
    }

    @Override
    public void onInterrupt() {
        log("onInterrupt");
    }

    @Override
    protected void onServiceConnected() {
        super.onServiceConnected();
        log("onServiceConnected");
        //可用代码配置当前Service的信息
        AccessibilityServiceInfo info = new AccessibilityServiceInfo();
        info.packageNames = new String[]{"com.android.packageinstaller", "com.tencent.mobileqq", "com.trs.gygdapp"}; //监听过滤的包名
        info.eventTypes = AccessibilityEvent.TYPES_ALL_MASK; //监听哪些行为
        info.feedbackType = AccessibilityServiceInfo.FEEDBACK_SPOKEN; //反馈
        info.notificationTimeout = 100; //通知的时间

        setServiceInfo(info);
    }

    private void log(CharSequence log) {
        Log.i("test", log + "");
    }


    private void findAndPerformAction(String text) {
        // 查找当前窗口中包含“安装”文字的按钮
        if (getRootInActiveWindow() == null)
            return;
        //通过文字找到当前的节点
        List<AccessibilityNodeInfo> nodes = getRootInActiveWindow().findAccessibilityNodeInfosByText(text);
        for (int i = 0; i < nodes.size(); i++) {
            AccessibilityNodeInfo node = nodes.get(i);
            // 执行按钮点击行为
            if (node.getClassName().equals("android.widget.Button") && node.isEnabled()) {
                node.performAction(AccessibilityNodeInfo.ACTION_CLICK);
            }
        }
    }
}
