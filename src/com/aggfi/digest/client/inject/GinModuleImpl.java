

package com.aggfi.digest.client.inject;
import org.cobogw.gwt.waveapi.gadget.client.WaveFeature;

import com.aggfi.digest.client.ReadOnlyGadget;
import com.aggfi.digest.client.service.NewPostGadgetService;
import com.aggfi.digest.client.service.NewPostGadgetServiceImpl;
import com.google.gwt.gadgets.client.DynamicHeightFeature;
import com.google.gwt.gadgets.client.GoogleAnalyticsFeature;
import com.google.gwt.inject.client.AbstractGinModule;
import com.google.inject.Singleton;
import com.vegalabs.features.client.feature.minimessages.MiniMessagesFeature;
import com.vegalabs.features.client.feature.views.ViewsFeature;
import com.vegalabs.features.client.request.WaveRequestServiceImpl;
import com.vegalabs.features.client.utils.WaveVegaUtilsImpl;
import com.vegalabs.general.client.objects.GoogleAnalyticsId;
import com.vegalabs.general.client.request.RequestService;
import com.vegalabs.general.client.utils.VegaUtils;

/**
 * This gin module binds an implementation for the
 * {@link com.google.gwt.inject.example.simple.client.SimpleService} used in
 * this example application. Note that we don't have to bind implementations
 * for {@link com.google.gwt.inject.DigestConstants.simple.client.SimpleConstants} and
 * {@link com.google.gwt.inject.DigestMessages.simple.client.SimpleMessages} - they
 * are constructed by Gin through GWT.create.
 */
public class GinModuleImpl extends AbstractGinModule {

	protected void configure() {

		bind(NewPostGadgetService.class).to(NewPostGadgetServiceImpl.class);
		bind(VegaUtils.class).to(WaveVegaUtilsImpl.class);
		bind(RequestService.class).to(WaveRequestServiceImpl.class).in(Singleton.class);
		
		bind(WaveFeature.class).toProvider(ReadOnlyGadget.WaveFeatureProvider.class).in(Singleton.class);
		bind(GoogleAnalyticsFeature.class).toProvider(ReadOnlyGadget.AnalyticsFeatureProvider.class).in(Singleton.class);
		bind(MiniMessagesFeature.class).toProvider(ReadOnlyGadget.MiniMessagesFeatureProvider.class).in(Singleton.class);
		bind(DynamicHeightFeature.class).toProvider(ReadOnlyGadget.DynamicHeightFeatureProvider.class).in(Singleton.class);
		bind(ViewsFeature.class).toProvider(ReadOnlyGadget.ViewsFeatureProvider.class).in(Singleton.class);
		bind(GoogleAnalyticsId.class).toProvider(ReadOnlyGadget.AnalyticsIdFeatureProvider.class).in(Singleton.class);

	}
}
