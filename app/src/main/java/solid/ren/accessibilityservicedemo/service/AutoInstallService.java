package solid.ren.accessibilityservicedemo.service;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import java.util.List;

import solid.ren.accessibilityservicedemo.PrintUtils;

/**
 * Created by _SOLID
 * Date:2016/7/20
 * Time:17:43
 */
public class AutoInstallService extends AccessibilityService {

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        PrintUtils.printEvent(event);
        findAndPerformActionButton("继续");
        findAndPerformActionTextView("下一步");
        findAndPerformActionTextView("安装");
    }


    private void findAndPerformActionButton(String text) {
        if (getRootInActiveWindow() == null)//取得当前激活窗体的根节点
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

    private void findAndPerformActionTextView(String text) {
        if (getRootInActiveWindow() == null)
            return;
        //通过文字找到当前的节点
        List<AccessibilityNodeInfo> nodes = getRootInActiveWindow().findAccessibilityNodeInfosByText(text);
        for (int i = 0; i < nodes.size(); i++) {
            AccessibilityNodeInfo node = nodes.get(i);
            // 执行按钮点击行为
            if (node.getClassName().equals("android.widget.TextView") && node.isEnabled()) {
                node.performAction(AccessibilityNodeInfo.ACTION_CLICK);
            }
        }
    }

    @Override
    protected void onServiceConnected() {
        super.onServiceConnected();
        PrintUtils.log("onServiceConnected");
    }

    @Override
    public void onInterrupt() {
        PrintUtils.log("onInterrupt");
    }


}
