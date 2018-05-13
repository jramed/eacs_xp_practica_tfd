package es.upm.eacs.xp.practica.tfd;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;


public class EquationSystemAppTest 
    extends TestCase
{

    public EquationSystemAppTest( String testName )
    {
        super( testName );
    }


    public static Test suite()
    {
        return new TestSuite( EquationSystemAppTest.class );
    }

    public void testApp()
    {
        assertTrue( true );
    }
}
