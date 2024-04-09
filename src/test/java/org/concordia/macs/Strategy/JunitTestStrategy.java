package org.concordia.macs.Strategy;

import org.junit.platform.suite.api.SelectClasses;

import Tools.MapEditorTest;
import Tools.MapLoaderTest;
import Tools.PlayersGameplayTest;
@org.junit.platform.suite.api.Suite
@SelectClasses(
        {AggressivePlayerStrategyTest.class, BenevolentPlayerStrategyTest.class
        })

public class JunitTestStrategy {

}
