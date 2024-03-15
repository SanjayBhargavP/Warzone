package org.concordia.macs.Utilities;


import org.junit.platform.suite.api.SelectClasses;
@org.junit.platform.suite.api.Suite
@SelectClasses(
        {
                MapEditorTest.class, MapLoaderTest.class, MapValidationTest.class, PlayersGamePlayTest.class
        }
)

public class JUnitTestUtilities {
}