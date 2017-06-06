package nhs.genetics.cardiff;

import nhs.genetics.cardiff.framework.GenomeVariant;
import nhs.genetics.cardiff.framework.TranscriptListParser;
import org.apache.commons.cli.*;

import java.io.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Program for writing VCF file to text output. VCF should be annotated with VEP.
 *
 * @author  Sara Rey
 * @since   2016-11-07
 *
 */

public class Main {

    private static final Logger log = Logger.getLogger(Main.class.getName());
    private static final String program = "VCFParse";
    private static final String version = "1.2.5";

    public static void main(String[] args) {

        //parse command line
        CommandLineParser commandLineParser = new DefaultParser();
        CommandLine commandLine = null;
        HelpFormatter formatter = new HelpFormatter();
        Options options = new Options();

        options.addOption("V", "Variant", true, "Path to input VCF file");
        options.addOption("T", "Transcript", true, "Path to preferred transcript list");
        options.addOption("C", "Classification", true, "Path to VCF with classifications");
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
            try {
                TranscriptListParser transcriptListParser = new TranscriptListParser(new File(commandLine.getOptionValue("T")));
                transcriptListParser.parseListReader();

                transcripts = transcriptListParser.getTranscripts();
            } catch (IOException e){
                log.log(Level.SEVERE, e.getMessage());
                System.exit(-1);
            }
        }

        //parse classification VCF
        HashMap<GenomeVariant, Integer> classifiedVariants = null;
        if (commandLine.hasOption("C")){
            classifiedVariants = Vcf.getClassifications(new File(commandLine.getOptionValue("C")));
        }

        //parse VEP annotated VCF file
        Vcf vcf = new Vcf(new File(commandLine.getOptionValue("V")));
        vcf.parseAnnotatedVepVcf();

        //write to file
        try {
            WriteOut.writeToTable(vcf, transcripts, classifiedVariants, onlyReportKnownRefSeq, outputFilenamePrefix);
        } catch (IOException e){
            log.log(Level.SEVERE, "Could not write to file:" + e.getMessage());
            System.exit(-1);
        }

    }

}
