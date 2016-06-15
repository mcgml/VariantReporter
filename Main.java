package nhs.genetics.cardiff;

import nhs.genetics.cardiff.framework.VCFFile;
import nhs.genetics.cardiff.framework.VEPAnnotationFile;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;

/**
 * Program for reporting variants in VCF format and annotations using varaint effect predictor
 *
 * @author  Matt Lyon
 * @version 1.0
 * @since   2016-01-13
 */
public class Main {

    private static final Logger log = Logger.getLogger(Main.class.getName());
    private static final String version = "1.0.0";

    public static void main(String[] args) {

        if (args.length < 3 || args.length > 7){
            System.err.println("VariantReporter v" + version);
            System.err.println("Usage: <VCF> <VEP> <TranscriptList>");
            System.err.println("-d: Print depth");
            System.err.println("-a: Print allele frequency");
            System.err.println("-f: Print strand bias");
            System.err.println("-q: Set minimum genotype quality [30]");
            return;
        }

        String line;

        //open files
        VCFFile vcfFile = new VCFFile(new File(args[0]));
        VEPAnnotationFile vepFile = new VEPAnnotationFile(new File(args[1]));

        //parse files
        vcfFile.parseVCF();
        vepFile.parseVEP();

        //read transcript list
        HashSet<String> preferredTranscripts = new HashSet<String>();
        try (BufferedReader transcriptReader = new BufferedReader(new FileReader(new File(args[2])))){
            while ((line = transcriptReader.readLine()) != null) {
                if (!line.equals("")) {
                    String[] fields = line.split("\t");
                    preferredTranscripts.add(fields[1]);
                }
            }
            transcriptReader.close();

        } catch (IOException e) {
            log.log(Level.SEVERE, "Could not read transcript file: " + e.getMessage());
        }

        //initalise report
        Report2 report = new Report2(vcfFile, vepFile, preferredTranscripts);

        //configure report
        if (args.length > 3){

            for (int n= 3 ; n < args.length; ++n){
                if (args[n].equals("-d")){
                    report.setPrintDepth(true);
                } else if (args[n].equals("-a")){
                    report.setPrintAF(true);
                } else if (args[n].equals("-f")){
                    report.setPrintFilter(true);
                } else if (Pattern.matches("^-q.*", args[n])){
                    String[] split = args[n].split("q");
                    report.setMinGQ(Integer.parseInt(split[1]));
                }
            }

        }

        //write report
        report.writeReport();
    }
}