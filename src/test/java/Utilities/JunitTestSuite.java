package Utilities;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses({
        MapEditorTest.class,
        PlayersGameplayTest.class
})

public class JUnitTestSuite {

}
