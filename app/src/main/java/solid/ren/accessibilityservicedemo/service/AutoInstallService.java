package solid.ren.accessibilityservicedemo.service;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import java.util.List;

/**
 * Created by _SOLID
 * Date:2016/7/20
 * Time:17:43
 */
public class AutoInstallService extends AccessibilityService {

    String packages[] = {"com.android.packageinstaller"};

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        Log.i("test","event type:"+event.getEventType());
        if (event.getSource() != null) {
            findAndPerformAction("安装");
            findAndPerformAction("下一步");
            findAndPerformAction("完成");
        }
    }

    @Override
    protected void onServiceConnected() {
        super.onServiceConnected();
        //可用代码配置当前Service的信息
        AccessibilityServiceInfo info = new AccessibilityServiceInfo();
        info.packageNames = packages; //监听过滤的包名
        info.eventTypes = AccessibilityEvent.TYPES_ALL_MASK; //监听哪些行为
        info.feedbackType = AccessibilityServiceInfo.FEEDBACK_SPOKEN; //反馈
        info.notificationTimeout = 100; //通知的时间

        setServiceInfo(info);
    }

    @Override
    public void onInterrupt() {

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
