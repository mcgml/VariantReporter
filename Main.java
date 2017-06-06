package nhs.genetics.cardiff;

import htsjdk.variant.variantcontext.VariantContext;
import htsjdk.variant.vcf.VCFFileReader;
import htsjdk.variant.vcf.VCFHeader;
import nhs.genetics.cardiff.framework.VariantContextStreamFilters;
import org.apache.commons.cli.*;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Program for writing VCF file to text output. VCF should be annotated with VEP.
 *
 * @author  Matt Lyon
 * @since   2016-11-07
 *
 */

public class Main {

    private static final Logger log = Logger.getLogger(Main.class.getName());
    private static final String program = "VCFParse";
    private static final String version = "1.3.0";

    public static void main(String[] args) {

        //parse command line
        CommandLineParser commandLineParser = new DefaultParser();
        CommandLine commandLine = null;
        HelpFormatter formatter = new HelpFormatter();
        Options options = new Options();

        options.addOption("V", "Variant", true, "Path to input VCF file");
        options.addOption("T", "Transcript", true, "Path to preferred transcript list");
        options.addOption("K", "KnownRefSeq", false, "Report only known RefSeq transcripts (NM)");
        options.addOption("O", "Output", true, "Add prefix to output filename");

        try {
            commandLine = commandLineParser.parse(options, args);

            if (!commandLine.hasOption("V")){
                throw new NullPointerException("Incorrect arguments");
            }

        } catch (ParseException | NullPointerException e){
            formatter.printHelp(program + " " + version, options);
            log.log(Level.SEVERE, e.getMessage());
            System.exit(-1);
        }

        //get opts
        boolean onlyReportKnownRefSeq = commandLine.hasOption("K");
        String outputFilenamePrefix = commandLine.getOptionValue("O");

        //parse preferred transcripts list
        HashSet<String> transcripts = null;
        if (commandLine.hasOption("T")){
            try (Stream<String> stream = Files.lines(Paths.get(commandLine.getOptionValue("T")))) {
                transcripts = stream.collect(Collectors.toCollection(HashSet::new));
            } catch (IOException e){
                log.log(Level.SEVERE, e.getMessage());
                System.exit(-1);
            }
        }

        //open VCF
        VCFFileReader vcfFileReader = new VCFFileReader(new File(commandLine.getOptionValue("V")));
        Stream<VariantContext> stream = vcfFileReader.iterator().stream();
        VCFHeader vcfHeader = vcfFileReader.getFileHeader();

        //filter variants
        stream = VariantContextStreamFilters.filterFilteredVariants(stream);
        //TODO

        //parse remaining Variants
        Vcf vcf  = new Vcf(vcfHeader, stream);
        vcf.parseStream();

        //write to text
        try {
            WriteOut.writeToTable(vcf, transcripts, onlyReportKnownRefSeq, outputFilenamePrefix);
        } catch (IOException e){
            log.log(Level.SEVERE, "Could not write to file: " + e.getMessage());
        }

    }

}
