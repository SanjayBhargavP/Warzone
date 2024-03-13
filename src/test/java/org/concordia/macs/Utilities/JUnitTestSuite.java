package org.concordia.macs.Utilities;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses({
        MapEditorTest.class, PlayersGamePlayTest.class, MapValidationTest.class
})

public class JUnitTestSuite {

}