package com.delta.smt;

import org.junit.Test;

import java.net.InetAddress;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
        String host = "CNBJDRCNB007";
        InetAddress mByName = InetAddress.getByName(host);
        System.out.println(mByName.getHostAddress());
    }

}