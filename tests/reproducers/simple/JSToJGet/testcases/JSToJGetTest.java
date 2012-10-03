/* JSToJGetTest.java
Copyright (C) 2012 Red Hat, Inc.

This file is part of IcedTea.

IcedTea is free software; you can redistribute it and/or
modify it under the terms of the GNU General Public License as published by
the Free Software Foundation, version 2.

IcedTea is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
General Public License for more details.

You should have received a copy of the GNU General Public License
along with IcedTea; see the file COPYING.  If not, write to
the Free Software Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA
02110-1301 USA.

Linking this library statically or dynamically with other modules is
making a combined work based on this library.  Thus, the terms and
conditions of the GNU General Public License cover the whole
combination.

As a special exception, the copyright holders of this library give you
permission to link this library with independent modules to produce an
executable, regardless of the license terms of these independent
modules, and to copy and distribute the resulting executable under
terms of your choice, provided that you also meet, for each linked
independent module, the terms and conditions of the license of that
module.  An independent module is a module which is not derived from
or based on this library.  If you modify this library, you may extend
this exception to your version of the library, but you are not
obligated to do so.  If you do not wish to do so, delete this
exception statement from your version.
 */

import net.sourceforge.jnlp.ProcessResult;
import net.sourceforge.jnlp.ServerAccess;
import net.sourceforge.jnlp.browsertesting.BrowserTest;
import net.sourceforge.jnlp.browsertesting.Browsers;
import net.sourceforge.jnlp.closinglisteners.CountingClosingListener;
import net.sourceforge.jnlp.annotations.NeedsDisplay;
import net.sourceforge.jnlp.annotations.TestInBrowsers;
import org.junit.Assert;

import org.junit.Test;

public class JSToJGetTest extends BrowserTest {

    public String[] testNames = { "int", "double", "float", "long", "boolean",
            "char", "byte", "intArrayElement", "intArrayBeyond",
            "regularString", "specialCharsString", "null", "Integer", "Double",
            "Float", "Long", "Boolean", "Character", "Byte",
            "DoubleArrayElement", "DoubleFullArray" };

    public String[] outputStrings = { "Test no. 1 - (int)",
            "Test no. 2 - (double)", "Test no. 3 - (float)",
            "Test no. 4 - (long)", "Test no. 5 - (boolean)",
            "Test no. 6 - (char)", "Test no. 7 - (byte)",
            "Test no. 8 - (int[] - element access)",
            "Test no. 9 - (int[] - beyond length)",
            "Test no.10 - (regular string)",
            "Test no.11 - (string with special characters)",
            "Test no.12 - (null)", "Test no.13 - (Integer)",
            "Test no.14 - (Double)", "Test no.15 - (Float)",
            "Test no.16 - (Long)", "Test no.17 - (Boolean)",
            "Test no.18 - (Character)", "Test no.19 - (Byte)",
            "Test no.20 - (Double[] - element access)",
            "Test no.21 - (Double[] - full array)" };

    public String passStr = " - passed.";
    public String failValStr = " - failed, value mismatch.";
    public String failTypeStr = " - failed, type mismatch.";
    public String expStr = "expected:[";
    public String foundStr = "] found:[";
    public String endStr = "].";

    private final String exceptionStr = "xception";
    private final String initStr = "JSToJGet applet initialized.";
    private final String setupStr = "JSToJGet applet set up for GET tests.";
    private final String afterStr = "afterTests";

    private class CountingClosingListenerImpl extends CountingClosingListener {

        @Override
        protected boolean isAlowedToFinish(String s) {
            if (s.contains(exceptionStr)) {
                return true;
            }
            return (s.contains(initStr) && s.contains(setupStr) && s
                    .contains(afterStr));
        }
    }

    private void evaluateStdoutContents(int index, ProcessResult pr) {
        // Assert that the applet was initialized.
        Assert.assertTrue("JSToJGetTest stdout should contain \"" + initStr
                + "\" but it didn't.", pr.stdout.contains(initStr));

        // Assert that the applet was set up for the GM tests.
        Assert.assertTrue("JSToJGetTest stdout should contain \"" + setupStr
                + "\" but it didn't.", pr.stdout.contains(setupStr));

        // Assert that the tests have passed.
        String s0 = outputStrings[index] + passStr;
        String s1 = outputStrings[index] + failValStr;
        String s2 = outputStrings[index] + failTypeStr;
        String s3 = "Error on Java side";

        int ind0 = pr.stdout.indexOf(s0);
        int ind1 = pr.stdout.indexOf(s1);
        int ind2 = pr.stdout.indexOf(s2);
        int ind3 = pr.stdout.indexOf(s3);
        int indBegin = pr.stdout.indexOf(setupStr);
        if (indBegin != -1) {
            indBegin += setupStr.length();
        } else {
            indBegin = 0;
        }

        String failStr = "JSToJGet " + outputStrings[index]
                + ": \"passed\" not found in the applet stdout, which is: "
                + pr.stdout.substring(indBegin, pr.stdout.length());

        if (ind1 != -1) {
            // int inde = pr.stdout.indexOf(expStr);
            // int indf = pr.stdout.indexOf(foundStr);
            int indend = pr.stdout.indexOf(endStr);
            failStr = pr.stdout.substring(ind1, indend + endStr.length());
        }

        if (ind2 != -1) {
            // int inde = pr.stdout.indexOf(expStr);
            // int indf = pr.stdout.indexOf(foundStr);
            int indend = pr.stdout.indexOf(endStr);
            failStr = pr.stdout.substring(ind2, indend + endStr.length());
        }

        if (ind3 != -1) {
            failStr = "JSToJGet: " + outputStrings[index]
                    + pr.stdout.substring(ind3, pr.stdout.length());
        }

        Assert.assertTrue(failStr, (ind3 == -1));// no error on Java side
        Assert.assertTrue(failStr, (ind1 == -1));// no value mismatch
        Assert.assertTrue(failStr, (ind2 == -1));// no type mismatch
        Assert.assertTrue(failStr, (ind0 != -1));// test passed

    }

    private void genericJSToJavaGetTestMethod(int index) throws Exception {

        String strURL = "/JSToJGet.html?" + testNames[index];
        ProcessResult pr = server.executeBrowser(strURL,
                new CountingClosingListenerImpl(),
                new CountingClosingListenerImpl());
        evaluateStdoutContents(index, pr);

    }

    @Test
    @TestInBrowsers(testIn = { Browsers.all })
    @NeedsDisplay
    public void AppletJSToJGet_int_Test() throws Exception {
        genericJSToJavaGetTestMethod(0);
    }

    @Test
    @TestInBrowsers(testIn = { Browsers.all })
    @NeedsDisplay
    public void AppletJSToJGet_double_Test() throws Exception {
        genericJSToJavaGetTestMethod(1);
    }

    @Test
    @TestInBrowsers(testIn = { Browsers.all })
    @NeedsDisplay
    public void AppletJSToJGet_float_Test() throws Exception {
        genericJSToJavaGetTestMethod(2);
    }

    @Test
    @TestInBrowsers(testIn = { Browsers.all })
    @NeedsDisplay
    public void AppletJSToJGet_long_Test() throws Exception {
        genericJSToJavaGetTestMethod(3);
    }

    @Test
    @TestInBrowsers(testIn = { Browsers.all })
    @NeedsDisplay
    public void AppletJSToJGet_boolean_Test() throws Exception {
        genericJSToJavaGetTestMethod(4);
    }

    @Test
    @TestInBrowsers(testIn = { Browsers.all })
    @NeedsDisplay
    public void AppletJSToJGet_char_Test() throws Exception {
        genericJSToJavaGetTestMethod(5);
    }

    @Test
    @TestInBrowsers(testIn = { Browsers.all })
    @NeedsDisplay
    public void AppletJSToJGet_byte_Test() throws Exception {
        genericJSToJavaGetTestMethod(6);
    }

    @Test
    @TestInBrowsers(testIn = { Browsers.all })
    @NeedsDisplay
    public void AppletJSToJGet_intArrayElement_Test() throws Exception {
        genericJSToJavaGetTestMethod(7);
    }

    @Test
    @TestInBrowsers(testIn = { Browsers.all })
    @NeedsDisplay
    public void AppletJSToJGet_intArrayBeyond_Test() throws Exception {
        genericJSToJavaGetTestMethod(8);
    }

    @Test
    @TestInBrowsers(testIn = { Browsers.all })
    @NeedsDisplay
    public void AppletJSToJGet_regularString_Test() throws Exception {
        genericJSToJavaGetTestMethod(9);
    }

    @Test
    @TestInBrowsers(testIn = { Browsers.all })
    @NeedsDisplay
    public void AppletJSToJGet_specialCharsString_Test() throws Exception {
        genericJSToJavaGetTestMethod(10);
    }

    @Test
    @TestInBrowsers(testIn = { Browsers.all })
    @NeedsDisplay
    public void AppletJSToJGet_null_Test() throws Exception {
        genericJSToJavaGetTestMethod(11);
    }

    @Test
    @TestInBrowsers(testIn = { Browsers.all })
    @NeedsDisplay
    public void AppletJSToJGet_Integer_Test() throws Exception {
        genericJSToJavaGetTestMethod(12);
    }

    @Test
    @TestInBrowsers(testIn = { Browsers.all })
    @NeedsDisplay
    public void AppletJSToJGet_Double_Test() throws Exception {
        genericJSToJavaGetTestMethod(13);
    }

    @Test
    @TestInBrowsers(testIn = { Browsers.all })
    @NeedsDisplay
    public void AppletJSToJGet_Float_Test() throws Exception {
        genericJSToJavaGetTestMethod(14);
    }

    @Test
    @TestInBrowsers(testIn = { Browsers.all })
    @NeedsDisplay
    public void AppletJSToJGet_Long_Test() throws Exception {
        genericJSToJavaGetTestMethod(15);
    }

    @Test
    @TestInBrowsers(testIn = { Browsers.all })
    @NeedsDisplay
    public void AppletJSToJGet_Boolean_Test() throws Exception {
        genericJSToJavaGetTestMethod(16);
    }

    @Test
    @TestInBrowsers(testIn = { Browsers.all })
    @NeedsDisplay
    public void AppletJSToJGet_Character_Test() throws Exception {
        genericJSToJavaGetTestMethod(17);
    }

    @Test
    @TestInBrowsers(testIn = { Browsers.all })
    @NeedsDisplay
    public void AppletJSToJGet_Byte_Test() throws Exception {
        genericJSToJavaGetTestMethod(18);
    }

    @Test
    @TestInBrowsers(testIn = { Browsers.all })
    @NeedsDisplay
    public void AppletJSToJGet_DoubleArrayElement_Test() throws Exception {
        genericJSToJavaGetTestMethod(19);
    }

    @Test
    @TestInBrowsers(testIn = { Browsers.all })
    @NeedsDisplay
    public void AppletJSToJGet_DoubleFullArray_Test() throws Exception {
        genericJSToJavaGetTestMethod(20);
    }

}
