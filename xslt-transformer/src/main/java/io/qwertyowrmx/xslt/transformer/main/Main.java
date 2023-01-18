package io.qwertyowrmx.xslt.transformer.main;

import com.google.devtools.common.options.OptionsParser;
import io.qwertyowrmx.xslt.transformer.core.XsltTransformer;
import io.qwertyowrmx.xslt.transformer.options.CliOptions;

import java.util.Objects;

public class Main {
    public static void main(String[] args) {

        OptionsParser parser = OptionsParser.newOptionsParser(CliOptions.class);
        parser.parseAndExitUponError(args);
        CliOptions options = parser.getOptions(CliOptions.class);
        CliOptions cliOptions = Objects.requireNonNull(options);

        XsltTransformer transformer = XsltTransformer
                .builder()
                .xslTemplateFileName(cliOptions.xslTemplateFileName)
                .inputXmlFileName(cliOptions.inputXmlFileName)
                .outputXmlFileName(cliOptions.outputXmlFileName)
                .build();

        transformer.transform();
    }
}