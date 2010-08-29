

package com.aggfi.digest.client.inject;

import com.aggfi.digest.client.ui.MainPanel;
import com.google.gwt.inject.client.GinModules;
import com.google.gwt.inject.client.Ginjector;

@GinModules(GinModuleImpl.class)
public interface GinjectorImpl extends Ginjector {

	MainPanel getMainPanel();
}
