package network.oxalis.peppol.schematron;

import net.sf.saxon.lib.ResourceRequest;
import org.testng.annotations.Test;

import javax.xml.transform.Source;

import static org.testng.Assert.assertNotNull;

public class ClasspathResourceResolverTest {

    // Positive tests
    @Test
    public void resolveExistingResource() throws Exception {

        // Arrange
        ClasspathResourceResolver resolver = new ClasspathResourceResolver("/iso-schematron-xslt2");

        ResourceRequest request = new ResourceRequest();
        request.relativeUri = "iso_dsdl_include.xsl";

        // Act
        Source source = resolver.resolve(request);

        // Assert
        assertNotNull(source, "Resolved source should not be null");
    }

    @Test
    public void resolveAllPipelineSteps() throws Exception {
        // Arrange
        ClasspathResourceResolver resolver = new ClasspathResourceResolver("/iso-schematron-xslt2");

        for (String xsl : new String[]{
                "iso_dsdl_include.xsl",
                "iso_abstract_expand.xsl",
                "iso_svrl_for_xslt2-original.xsl",
                "iso_svrl_for_xslt2-peppol.xsl"
        }) {

            ResourceRequest request = new ResourceRequest();
            request.relativeUri = xsl;

            // Act
            Source source = resolver.resolve(request);

            // Assert
            assertNotNull(source, "Failed to resolve: " + xsl);
        }
    }

    @Test(expectedExceptions = net.sf.saxon.trans.XPathException.class)
    public void resolveNonExistentResourceThrows() throws Exception {
        ClasspathResourceResolver resolver = new ClasspathResourceResolver("/iso-schematron-xslt2");

        ResourceRequest request = new ResourceRequest();
        request.relativeUri = "does_not_exist.xsl";

        resolver.resolve(request); // should throw
    }
}
