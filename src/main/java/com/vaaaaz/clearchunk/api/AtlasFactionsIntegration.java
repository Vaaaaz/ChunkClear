package com.vaaaaz.clearchunk.api;

import com.atlasplugins.atlasfactionsv2.apiv2.AtlasFactionsAPI;
import com.atlasplugins.atlasfactionsv2.handlers.FactionsHandler;

public class AtlasFactionsIntegration {

    private static final FactionsHandler factionsHandler;

    static {
        factionsHandler = AtlasFactionsAPI.getFactionsHandler();
    }

}
