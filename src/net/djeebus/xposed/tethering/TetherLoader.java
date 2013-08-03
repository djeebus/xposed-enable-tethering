package net.djeebus.xposed.tethering;

import static de.robv.android.xposed.XposedHelpers.findAndHookMethod;
import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;

public class TetherLoader implements IXposedHookLoadPackage {

	@Override
	public void handleLoadPackage(LoadPackageParam lpparam) throws Throwable {
		if (!lpparam.packageName.equals("com.android.settings")) {
			return;
		}
		XposedBridge.log("Loaded app: " + lpparam.packageName);
		
		XC_MethodHook handler = new XC_MethodHook() {
    		@Override
    		protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
    		}
    		
    		@Override
    		protected void afterHookedMethod(MethodHookParam param) throws Throwable {
    			XposedBridge.log("Setting isProvisioning return value to false.");
    			param.setResult(false);
    		}
		}; 
		
		findAndHookMethod("com.android.settings.TetherSettings", lpparam.classLoader, "isProvisioningNeeded", handler); 
	}

}
