package io.qwertyowrmx.xslt.transformer.core;

import io.qwertyowrmx.xslt.transformer.exception.TransformationRuntimeException;
import lombok.Builder;

import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.File;

@Builder
public class XsltTransformer {

    private String xslTemplateFileName;
    private String inputXmlFileName;
    private String outputXmlFileName;

    public void transform() {
        try {
            TransformerFactory factory = TransformerFactory.newInstance();
            Source xslt = new StreamSource(new File(xslTemplateFileName));
            Transformer transformer = factory.newTransformer(xslt);

            Source text = new StreamSource(new File(inputXmlFileName));
            transformer.transform(text, new StreamResult(new File(outputXmlFileName)));
        } catch (TransformerException e) {
            String message = String.format("Cannot transform %s to %s with template %s",
                    inputXmlFileName,
                    outputXmlFileName,
                    xslTemplateFileName);

            throw new TransformationRuntimeException(message, e);
        }
    }
}
