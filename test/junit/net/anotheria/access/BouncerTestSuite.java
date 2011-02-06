package net.anotheria.access;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(value=Suite.class)
@SuiteClasses(value={SOAttributeTest.class, SecurityObjectTest.class})
public class BouncerTestSuite {

}
