package edu.unicen.surfforecaster.client;

import com.google.gwt.user.client.ui.impl.ClippedImagePrototype;
import com.google.gwt.user.client.ui.AbstractImagePrototype;
import com.google.gwt.core.client.GWT;

public class SampleImageBundle_generatedBundle implements edu.unicen.surfforecaster.client.SampleImageBundle {
  private static final String IMAGE_BUNDLE_URL = GWT.getModuleBaseURL() + "D16EF370D9380728C575E014FB4F7ACF.cache.png";
    private static final ClippedImagePrototype getGWTLogo_SINGLETON = new ClippedImagePrototype(IMAGE_BUNDLE_URL, 0, 0, 100, 75);
    public com.google.gwt.user.client.ui.AbstractImagePrototype getGWTLogo() {
      return getGWTLogo_SINGLETON;
    }
}
