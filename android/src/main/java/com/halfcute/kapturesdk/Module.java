package com.halfcute.kapturesdk;

import android.widget.Toast;
import android.app.Activity;

import com.adjetter.kapchatsdk.structure.KapchatTemplateMessage;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

import java.util.HashMap;
import java.util.Map;

import com.adjetter.kapchatsdk.KapchatHelper;
import com.adjetter.kapchatsdk.interfaces.CallBackResponse;

public class Module extends ReactContextBaseJavaModule {

  private static final String DURATION_SHORT_KEY = "SHORT";
  private static final String DURATION_LONG_KEY = "LONG";

  public Module(ReactApplicationContext reactContext) {
    super(reactContext);
  }

  @Override
  public String getName() {
    return "kapturesdk";
  }

	private static final String ERROR_NO_ACTIVITY = "E_NO_ACTIVITY";
	private static final String ERROR_NO_ACTIVITY_MESSAGE = "Tried to do the something while not attached to an Activity";

	@Override
  public Map<String, Object> getConstants() {
    final Map<String, Object> constants = new HashMap<>();
    constants.put(DURATION_SHORT_KEY, Toast.LENGTH_SHORT);
    constants.put(DURATION_LONG_KEY, Toast.LENGTH_LONG);
    return constants;
  }

  @ReactMethod
  public void show(String message, int duration) {
    Toast.makeText(getReactApplicationContext(), message, duration).show();
  }


	@ReactMethod
	public String initialise(String customerCode, String partnerSupportKey, String encryptionKey){
		final Activity activity = getCurrentActivity();

		if (activity == null) {
			return ERROR_NO_ACTIVITY;
		}

		String finalResponse = "true";

		KapchatHelper.initialise(activity, customerCode, partnerSupportKey, encryptionKey, new CallBackResponse() {
				@Override
				public void intialiseResponse(String s) {
					final String finalResponse = s;
				}
			}
		);
		return finalResponse;
	}

	@ReactMethod
	public void openChatScreen(){
		final Activity activity = getCurrentActivity();

		if (activity == null) {
			return;
		}
		KapchatHelper.startChatScreen(activity);
	}

	@ReactMethod
	public void openChatScreenwithMessage(String msg){
		final Activity activity = getCurrentActivity();

		if (activity == null) {
			return;
		}
		KapchatTemplateMessage templateMessage = new KapchatTemplateMessage();
		templateMessage.setMessage(msg);

		KapchatHelper.startChatScreenWithTemplate(activity,templateMessage);
	}




}
