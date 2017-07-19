package org.opensrp.view.activity;

import org.opensrp.event.CapturedPhotoInformation;
import org.opensrp.event.Event;
import org.opensrp.event.Listener;
import org.opensrp.view.controller.PNCDetailController;

public class PNCDetailActivity extends SecuredWebActivity {
    private Listener<CapturedPhotoInformation> photoCaptureListener;

    @Override
    protected void onInitialization() {
        String caseId = (String) getIntent().getExtras().get("caseId");

        webView.addJavascriptInterface(new PNCDetailController(this, caseId,
                context().allEligibleCouples(), context().allBeneficiaries(),
                context().allTimelineEvents()), "context");
        webView.loadUrl("file:///android_asset/www/pnc_detail.html");

        photoCaptureListener = new Listener<CapturedPhotoInformation>() {
            @Override
            public void onEvent(CapturedPhotoInformation data) {
                if (webView != null) {
                    webView.loadUrl("javascript:pageView.reloadPhoto('" + data.entityId() + "', '" + data.photoPath() + "')");
                }
            }
        };
        Event.ON_PHOTO_CAPTURED.addListener(photoCaptureListener);
    }
}