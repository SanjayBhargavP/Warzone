package org.concordia.macs.JSuite;

import org.concordia.macs.Utilities.MapValidationTest;
import org.junit.platform.suite.api.SelectClasses;

import org.concordia.macs.Models.CountryTest;
import org.concordia.macs.Utilities.MapEditorTest;
import org.concordia.macs.Utilities.MapLoaderTest;
import org.concordia.macs.Utilities.PlayersGamePlayTest;
@org.junit.platform.suite.api.Suite
@SelectClasses(
        {MapEditorTest.class, MapLoaderTest.class, PlayersGamePlayTest.class,CountryTest.class, MapValidationTest.class
        })

public class JUnitTestSuite {

}
