package io.qwertyowrmx.xslt.transformer.options;

import com.google.devtools.common.options.Option;
import com.google.devtools.common.options.OptionsBase;


public class CliOptions extends OptionsBase {
    @Option(
            name = "xsl-template",
            help = "XSL Template for transformation",
            defaultValue = "template.xsl"
    )
    public String xslTemplateFileName;

    @Option(
            name = "input",
            help = "Input file name for transformation",
            defaultValue = "input.xml"
    )
    public String inputXmlFileName;

    @Option(
            name = "output",
            help = "output file name for transformation",
            defaultValue = "output.xml"
    )
    public String outputXmlFileName;
}
