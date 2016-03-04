package com.jguix.cordova;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import com.aviary.android.feather.sdk.AviaryIntent;
import com.aviary.android.feather.sdk.internal.Constants;
import com.aviary.android.feather.sdk.internal.filters.ToolLoaderFactory;
import com.aviary.android.feather.sdk.internal.filters.ToolLoaderFactory.Tools;
import com.aviary.android.feather.sdk.internal.headless.utils.MegaPixels;

import org.apache.cordova.CordovaWebView;
import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CordovaInterface;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
* FeatherEditor Plugin
* Based on https://github.com/cmcdonaldca/Cordova-Aviary-Plugin/
*/
public class FeatherEditor extends CordovaPlugin {

	private static final int ACTION_REQUEST_FEATHER = 1;
	private static final String ACTION_SHOW_EVENT = "show";
	private static final String LOG_TAG = "FeatherEditor";

	private CallbackContext callbackContext;

	/**
	* Constructor.
	*/
	public FeatherEditor() {
	}

	/**
	* Sets the context of the Command. This can then be used to do things like
	* get file paths associated with the Activity.
	*
	* @param cordova The context of the main Activity.
	* @param webView The CordovaWebView Cordova is running in.
	*/
	public void initialize(CordovaInterface cordova, CordovaWebView webView) {
		super.initialize(cordova, webView);
		Log.v(LOG_TAG,"Init FeatherEditor");
	}

	public boolean execute(final String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
		if (ACTION_SHOW_EVENT.equals(action)) {

			try {
				Log.i(LOG_TAG, action);
				this.callbackContext = callbackContext;
				// Get source image URI
				final JSONObject options = args.getJSONObject(0);
				final String src = options.getString("src");
				Uri uri = Uri.parse(src);

				// Create the intent needed to start feather
        		Intent imageEditorIntent = new AviaryIntent.Builder(this.cordova.getActivity().getApplicationContext())
                	.setData(uri) // input image source
                	//.withOutput(Uri.parse(getFilesDir() + "/edited.jpg")) // output file destination
                	//.withOutputFormat(Bitmap.CompressFormat.JPEG) // output format
                	.withOutputSize(MegaPixels.Mp5) // output size
                	.withToolList(new Tools[]{Tools.BLEMISH, Tools.BLUR, Tools.COLOR, Tools.DRAW,
							Tools.EFFECTS, Tools.ENHANCE, Tools.FOCUS, Tools.FRAMES, Tools.LIGHTING,
							Tools.MEME, Tools.OVERLAYS, Tools.REDEYE, Tools.SHARPNESS, Tools.SPLASH,
							Tools.STICKERS, Tools.TEXT, Tools.VIGNETTE, Tools.WHITEN}) // tool list
                	.build();


                cordova.getActivity().startActivityForResult(imageEditorIntent, ACTION_REQUEST_FEATHER);
                cordova.setActivityResultCallback(this);
                return true;

			} catch (Exception ex) {
				Log.e(LOG_TAG, ex.toString());
				callbackContext.error("Unknown error occured showing aviary.");
			}

		} else {
			callbackContext.error("FeatherEditor." + action + " is not a supported function. Did you mean '" + ACTION_SHOW_EVENT + "'?");
		}
		return false;

	}

	@Override
	public void onActivityResult( int requestCode, int resultCode, Intent data ) {
	    if( resultCode == Activity.RESULT_OK ) {
	        switch( requestCode ) {
	            case ACTION_REQUEST_FEATHER:
	                Uri mImageUri = data.getData();
	                Log.d(LOG_TAG, "Edited image uri: " + mImageUri.toString());
	                try {
		                JSONObject returnVal = new JSONObject();
		                returnVal.put("src", mImageUri.toString());
		                returnVal.put("name", mImageUri.getLastPathSegment());		                
		                this.callbackContext.success(returnVal);
	                } catch(JSONException ex) {
	    				Log.e(LOG_TAG, ex.toString());
	                	this.callbackContext.error(ex.getMessage());
	                }
	                break;
	       }
	    }
	}

}