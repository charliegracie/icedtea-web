/*
 Copyright (C) 2015 Red Hat, Inc.

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
package sopbypasstests;

import net.sourceforge.jnlp.ProcessResult;
import net.sourceforge.jnlp.annotations.NeedsDisplay;
import org.junit.Test;

import java.util.Collections;

import static sopbypasstests.SOPBypassUtil.*;

public class SOPBypassSignedJnlpAppletTestWithHtmlSwitch extends SOPBypassBeforeAndAfterChunks {

    @Test
    @NeedsDisplay
    public void testLocalAbsoluteArchiveLocalPathCodebase_SJHAT() throws Exception {
        TemplatedHtmlDoc templatedDoc = filterHtml("SOPBypassSigned.html", "SOPBypassSigned", server.getUrl("SOPBypassSigned.jar"), server.getUrl("."), getUnrelatedServer());
        ProcessResult pr = performTest(templatedDoc);
        assertCodebaseConnection(pr, serverInstance());
        assertDocumentBaseConnection(pr, serverInstance());
        assertUnrelatedConnection(pr, unrelatedInstance());
        assertResourcesConnection(pr, serverInstance());
    }

    @Test
    @NeedsDisplay
    public void testLocalAbsoluteArchiveUnrelatedRemoteCodebase_SJHAT() throws Exception {
        TemplatedHtmlDoc templatedDoc = filterHtml("SOPBypassSigned.html", "SOPBypassSigned", server.getUrl("SOPBypassSigned.jar"), serverC.getUrl("."), getUnrelatedServer());
        ProcessResult pr = performTest(templatedDoc);
        assertCodebaseConnection(pr, serverC);
        assertDocumentBaseConnection(pr, serverInstance());
        assertUnrelatedConnection(pr, unrelatedInstance());
        assertResourcesConnection(pr, serverInstance());
    }

    @Test
    @NeedsDisplay
    public void testRemoteAbsoluteArchiveSameRemoteCodebase_SJHAT() throws Exception {
        TemplatedHtmlDoc templatedDoc = filterHtml("SOPBypassSigned.html", "SOPBypassSigned", serverC.getUrl("SOPBypassSigned.jar"), serverC.getUrl("."), getUnrelatedServer());
        ProcessResult pr = performTest(templatedDoc);
        assertCodebaseConnection(pr, serverC);
        assertDocumentBaseConnection(pr, serverInstance());
        assertUnrelatedConnection(pr, unrelatedInstance());
        assertResourcesConnection(pr, serverC);
    }

    @Test
    @NeedsDisplay
    public void testRemoteAbsoluteArchiveUnrelatedRemoteCodebase_SJHAT() throws Exception {
        TemplatedHtmlDoc templatedDoc = filterHtml("SOPBypassSigned.html", "SOPBypassSigned", serverB.getUrl("SOPBypassSigned.jar"), serverC.getUrl("."), getUnrelatedServer());
        ProcessResult pr = performTest(templatedDoc);
        assertCodebaseConnection(pr, serverC);
        assertDocumentBaseConnection(pr, serverInstance());
        assertUnrelatedConnection(pr, unrelatedInstance());
        assertResourcesConnection(pr, serverB);
    }

    @Test
    @NeedsDisplay
    public void testRemoteAbsoluteArchiveLocalPathCodebase_SJHAT() throws Exception {
        TemplatedHtmlDoc templatedDoc = filterHtml("SOPBypassSigned.html", "SOPBypassSigned", serverB.getUrl("SOPBypassSigned.jar"), server.getUrl("."), getUnrelatedServer());
        ProcessResult pr = performTest(templatedDoc);
        assertCodebaseConnection(pr, serverInstance());
        assertDocumentBaseConnection(pr, serverInstance());
        assertUnrelatedConnection(pr, unrelatedInstance());
        assertResourcesConnection(pr, serverB);
    }

    @Test
    @NeedsDisplay
    public void testRemoteAbsoluteArchiveLocalDotCodebase_SJHAT() throws Exception {
        TemplatedHtmlDoc templatedDoc = filterHtml("SOPBypassSigned.html", "SOPBypassSigned", serverB.getUrl("SOPBypassSigned.jar"), ".", getUnrelatedServer());
        ProcessResult pr = performTest(templatedDoc);
        assertCodebaseConnection(pr, serverInstance());
        assertDocumentBaseConnection(pr, serverInstance());
        assertUnrelatedConnection(pr, unrelatedInstance());
        assertResourcesConnection(pr, serverB);
    }

    @Test
    @NeedsDisplay
    public void testRemoteAbsoluteArchiveNoCodebase_SJHAT() throws Exception {
        TemplatedHtmlDoc templatedDoc = filterHtml("SOPBypassSigned.html", "SOPBypassSigned", serverB.getUrl("SOPBypassSigned.jar"), (String) null, getUnrelatedServer());
        ProcessResult pr = performTest(templatedDoc);
        assertCodebaseConnection(pr, serverInstance());
        assertDocumentBaseConnection(pr, serverInstance());
        assertUnrelatedConnection(pr, unrelatedInstance());
        assertResourcesConnection(pr, serverB);
    }

    @Test
    @NeedsDisplay
    public void testLocalAbsoluteArchiveNoCodebase_SJHAT() throws Exception {
        TemplatedHtmlDoc templatedDoc = filterHtml("SOPBypassSigned.html", "SOPBypassSigned", server.getUrl("SOPBypassSigned.jar"), (String) null, getUnrelatedServer());
        ProcessResult pr = performTest(templatedDoc);
        assertCodebaseConnection(pr, serverInstance());
        assertDocumentBaseConnection(pr, serverInstance());
        assertUnrelatedConnection(pr, unrelatedInstance());
        assertResourcesConnection(pr, serverInstance());
    }

    @Test
    @NeedsDisplay
    public void testLocalRelativeArchiveNoCodebase_SJHAT() throws Exception {
        TemplatedHtmlDoc templatedDoc = filterHtml("SOPBypassSigned.html", "SOPBypassSigned", "SOPBypassSigned.jar", (String) null, getUnrelatedServer());
        ProcessResult pr = performTest(templatedDoc);
        assertCodebaseConnection(pr, serverInstance());
        assertDocumentBaseConnection(pr, serverInstance());
        assertUnrelatedConnection(pr, unrelatedInstance());
        assertResourcesConnection(pr, serverInstance());
    }

    @Test
    @NeedsDisplay
    public void testLocalRelativeArchiveUnrelatedRemoteCodebase_SJHAT() throws Exception {
        TemplatedHtmlDoc templatedDoc = filterHtml("SOPBypassSigned.html", "SOPBypassSigned", "SOPBypassSigned.jar", serverC.getUrl(), getUnrelatedServer());
        ProcessResult pr = performTest(templatedDoc);
        assertCodebaseConnection(pr, serverC);
        assertDocumentBaseConnection(pr, serverInstance());
        assertUnrelatedConnection(pr, unrelatedInstance());
        assertResourcesConnection(pr, serverC);
    }

    @Test
    @NeedsDisplay
    public void testLocalAbsoluteArchiveLocalDotCodebase_SJHAT() throws Exception {
        TemplatedHtmlDoc templatedDoc = filterHtml("SOPBypassSigned.html", "SOPBypassSigned", server.getUrl("SOPBypassSigned.jar"), ".", getUnrelatedServer());
        ProcessResult pr = performTest(templatedDoc);
        assertCodebaseConnection(pr, serverInstance());
        assertDocumentBaseConnection(pr, serverInstance());
        assertUnrelatedConnection(pr, unrelatedInstance());
        assertResourcesConnection(pr, serverInstance());
    }

    @Test
    @NeedsDisplay
    public void testLocalRelativeArchiveLocalPathCodebase_SJHAT() throws Exception {
        TemplatedHtmlDoc templatedDoc = filterHtml("SOPBypassSigned.html", "SOPBypassSigned", "SOPBypassSigned.jar", server.getUrl("/"), getUnrelatedServer());
        ProcessResult pr = performTest(templatedDoc);
        assertCodebaseConnection(pr, serverInstance());
        assertDocumentBaseConnection(pr, serverInstance());
        assertUnrelatedConnection(pr, unrelatedInstance());
        assertResourcesConnection(pr, serverInstance());
    }

    @Test
    @NeedsDisplay
    public void testLocalRelativeArchiveLocalDotCodebase_SJHAT() throws Exception {
        TemplatedHtmlDoc templatedDoc = filterHtml("SOPBypassSigned.html", "SOPBypassSigned", "SOPBypassSigned.jar", ".", getUnrelatedServer());
        ProcessResult pr = performTest(templatedDoc);
        assertCodebaseConnection(pr, serverInstance());
        assertDocumentBaseConnection(pr, serverInstance());
        assertUnrelatedConnection(pr, unrelatedInstance());
        assertResourcesConnection(pr, serverInstance());
    }

    @Test
    @NeedsDisplay
    public void testRemoteRelativeArchiveSameRemoteCodebase_SJHAT() throws Exception {
        TemplatedHtmlDoc templatedDoc = filterHtml("SOPBypassSigned.html", "SOPBypassSigned", "SOPBypassSigned.jar", serverC.getUrl("/"), getUnrelatedServer());
        ProcessResult pr = performTest(templatedDoc);
        assertCodebaseConnection(pr, serverC);
        assertDocumentBaseConnection(pr, serverInstance());
        assertUnrelatedConnection(pr, unrelatedInstance());
        assertResourcesConnection(pr, serverC);
    }

    public ProcessResult performTest(TemplatedHtmlDoc templatedDoc) throws Exception {
        ProcessResult pr = server.executeJavawsHeadless(Collections.singletonList("-html"), templatedDoc.getFileName(), getClosingListener(), null, null);
        assertStart(pr);
        assertEnd(pr);
        assertPrivileged(pr);
        return pr;
    }

}
