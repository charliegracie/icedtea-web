/* BasicComponentSplashScreenTest.java
Copyright (C) 2012 Red Hat, Inc.

This file is part of IcedTea.

IcedTea is free software; you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation; either version 2, or (at your option)
any later version.

IcedTea is distributed in the hope that it will be useful, but
WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
General Public License for more details.

You should have received a copy of the GNU General Public License
along with IcedTea; see the file COPYING.  If not, write to the
Free Software Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA
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
exception statement from your version. */
package net.sourceforge.jnlp.splashscreen.parts;

import net.sourceforge.jnlp.splashscreen.SplashUtils.SplashReason;
import org.junit.Assert;
import org.junit.Test;

import java.awt.Graphics;

public class BasicComponentSplashScreenTest {

    @Test
    public void createAditionalInfoTest() {
        BasicComponentSplashScreenImpl tested = new BasicComponentSplashScreenImpl();
        String v = "2.118x08";
        tested.setVersion(v);
        tested.setSplashReason(SplashReason.APPLET);
        String s1 = tested.createAditionalInfoTest();
        Assert.assertNotNull("Not null input must result to something", s1);
        Assert.assertTrue("Not null input must have version value", s1.contains(v));
        Assert.assertTrue("Not null input must have version string", s1.contains("version"));
        Assert.assertTrue("Not null input must have version string", s1.contains(SplashReason.APPLET.toString()));
        tested.setVersion(null);
        tested.setSplashReason(null);
        String s2 = tested.createAditionalInfoTest();
        Assert.assertNull("Not null input must result to something", s2);
        tested.setSplashReason(null);
        tested.setVersion(v);
        Exception ex = null;
        try {
            String s3 = tested.createAditionalInfoTest();
        } catch (Exception exx) {
            ex = exx;
        }
        Assert.assertNotNull("Null reason vith set version must causes exception", ex);


    }

    private class BasicComponentSplashScreenImpl extends BasicComponentSplashScreen {

        public BasicComponentSplashScreenImpl() {
        }

        @Override
        public void paintComponent(Graphics g) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public void paintTo(Graphics g) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public void adjustForSize() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public void startAnimation() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public void stopAnimation() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public String createAditionalInfoTest() {
            return super.createAditionalInfo();
        }

        @Override
        public void setPercentage(int done) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public int getPercentage() {
            throw new UnsupportedOperationException("Not supported yet.");
        }
    }
}
