package org.concordia.macs.Strategy;

import org.junit.platform.suite.api.SelectClasses;

import org.concordia.macs.Utilities.MapEditorTest;
import org.concordia.macs.Utilities.MapLoaderTest;
@org.junit.platform.suite.api.Suite
@SelectClasses(
        {AggressivePlayerStrategyTest.class, BenevolentPlayerStrategyTest.class
        })

public class JunitTestStrategy {

}
