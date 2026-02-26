package network.oxalis.peppol.schematron;

import net.sf.saxon.lib.ResourceRequest;
import net.sf.saxon.lib.ResourceResolver;
import net.sf.saxon.trans.XPathException;

import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import java.io.InputStream;

public class ClasspathResourceResolver implements ResourceResolver {

    private final String resourceBasePath;

    public ClasspathResourceResolver(String resourceBasePath) {
        this.resourceBasePath = resourceBasePath;
    }

    /**
     * Resolves a resource request by loading the resource from the classpath.
     * @param resourceRequest The resource request containing the relative URI of the resource to resolve.
     * @return A stream source for the requested resource.
     * @throws XPathException if the resource cannot be found or loaded from the classpath.
     */
    @Override
    public Source resolve(ResourceRequest resourceRequest) throws XPathException {
        String path = String.format("%s/%s", resourceBasePath, resourceRequest.relativeUri);
        InputStream stream = getClass().getResourceAsStream(path);
        if (stream == null)
            throw new XPathException("Resource not found: " + path);
        return new StreamSource(stream);
    }
}
