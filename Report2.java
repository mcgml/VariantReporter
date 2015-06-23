package nhs.genetics.cardiff;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by ml on 16/04/15.
 */

//TODO: re-write using htsjdk
public class Report2 {

    private static final Logger log = Logger.getLogger(Report2.class.getName());

    private VCFFile vcfFile;
    private VEPAnnotationFile vepFile;
    private HashSet<String> preferredTranscripts;
    private int minGQ = 30;
    private boolean printDepth = false;
    private boolean printAF = false;
    private boolean printFilter = false;

    public Report2(VCFFile vcfFile, VEPAnnotationFile vepFile, HashSet<String> preferredTranscripts){
        this.vcfFile = vcfFile;
        this.vepFile = vepFile;
        this.preferredTranscripts = preferredTranscripts;
    }

    public void writeReport() {

        log.log(Level.INFO, "Writing report ...");
        log.log(Level.INFO, "Minimum genotype quality: " + minGQ);
        log.log(Level.INFO, "Print depth: " + printDepth);
        log.log(Level.INFO, "Print allele frequency: " + printAF);
        log.log(Level.INFO, "Print variant filter: " + printFilter);

        for (Map.Entry<String, Integer> sampleID : vcfFile.getSampleIDs().entrySet()) {

            try (PrintWriter writer = new PrintWriter(new File(sampleID.getKey() + "_VariantReport.txt"))) {

                //print headers
                writer.print("SampleID\t");
                writer.print("Variant\t");
                if (printAF) writer.print("AlleleFrequency\t"); else writer.print("Zygosity\t");
                if (printDepth) writer.print("Depth\t");
                if (printFilter) writer.print("Filter\t");
                writer.print("Quality\t");
                writer.print("ID\t");
                writer.print("Classification\t");
                writer.print("ESP_AfricanAmerican\t");
                writer.print("ESP_EuropeanAmerican\t");
                writer.print("1KG_African\t");
                writer.print("1KG_American\t");
                writer.print("1KG_Asian\t");
                writer.print("1KG_European\t");
                writer.print("Transcript\t");
                writer.print("PreferredTranscript\t");
                writer.print("Gene\t");
                writer.print("HGVSc\t");
                writer.print("HGVSp\t");
                writer.print("Consequence\t");
                writer.print("Exon/Intron\t");
                writer.print("SIFTResult\t");
                writer.print("SIFTScore\t");
                writer.print("PolyPhenResult\t");
                writer.print("PolyPhenScore");
                writer.println();

                //loop over VCF records
                for (VCFRecord vcfRecord : vcfFile.getVCFRecords()){

                    //skip wildtype bases
                    if (vcfRecord.getGenomeVariant().getAlt().equals("\\.")) continue;

                    //skip wildtype genotypes & low qualtiy calls & filtered calls
                    if (skipWildtypeGenotype(vcfRecord.getFormatSubFields().get(sampleID.getValue()).get("GT"))) continue;
                    if (isVariantLowQuality(vcfRecord.getFormatSubFields().get(sampleID.getValue()).get("GQ"))) continue;
                    if (!vcfRecord.getFilter().equals("PASS")) continue;

                    //print variants with annotations
                    if (vepFile.getTranscriptLevelRecords().containsKey(vcfRecord.getGenomeVariant().getConcatenatedVariant())) {

                        //loop over annotations for this variants
                        for (VEPTranscriptAnnotation transcriptAnnotation : vepFile.getTranscriptLevelRecords().get(vcfRecord.getGenomeVariant().getConcatenatedVariant())) {

                            writer.print(sampleID.getKey() + "\t");
                            writer.print(vcfRecord.getGenomeVariant().getConcatenatedVariant() + "\t");

                            if (printAF){
                                writer.print(vcfRecord.getFormatSubFields().get(sampleID.getValue()).get("VF") + "\t");
                            } else {
                                printGenotype(vcfRecord.getFormatSubFields().get(sampleID.getValue()).get("GT"), writer);
                            }

                            if (printDepth) printCombinedAlleleDepth(vcfRecord.getFormatSubFields().get(sampleID.getValue()).get("AD"), writer);
                            if (printFilter) writer.print(vcfRecord.getFilter() + "\t");

                            writer.print(vcfRecord.getFormatSubFields().get(sampleID.getValue()).get("GQ") + "\t");
                            printVariantId(vcfRecord.getId(), writer);

                            //print known variants classification
                            printClassification(vcfRecord, writer);

                            //print population frequencies
                            printGenomicAnnotation(vcfRecord, writer);

                            //print transcript annotations
                            writer.print(transcriptAnnotation.getFeature() + "\t");
                            if (preferredTranscripts.contains(transcriptAnnotation.getFeature())) writer.print("TRUE\t"); else writer.print("FALSE\t");
                            writer.print(transcriptAnnotation.getSymbol() + "\t");
                            writer.print(transcriptAnnotation.getHgvsCoding() + "\t");
                            writer.print(transcriptAnnotation.getHgvsProtein());

                            //print consequences
                            printConsequence(transcriptAnnotation, writer);

                            //print exon/intron
                            printExonIntron(transcriptAnnotation, writer);

                            //print in-silico
                            printSift(transcriptAnnotation, writer);
                            printPolyphen(transcriptAnnotation, writer);

                            writer.println();

                        }

                    } else {

                        //print variants without transcript annotations
                        writer.print(sampleID.getKey() + "\t");
                        writer.print(vcfRecord.getGenomeVariant().getConcatenatedVariant() + "\t");

                        if (printAF){
                            writer.print(vcfRecord.getFormatSubFields().get(sampleID.getValue()).get("VF"));
                        } else {
                            printGenotype(vcfRecord.getFormatSubFields().get(sampleID.getValue()).get("GT"), writer);
                        }

                        if (printDepth) writer.print(vcfRecord.getFormatSubFields().get(sampleID.getValue()).get("DP") + "\t");
                        if (printFilter) writer.print(vcfRecord.getFilter() + "\t");

                        writer.print(vcfRecord.getFormatSubFields().get(sampleID.getValue()).get("GQ") + "\t");
                        printVariantId(vcfRecord.getId(), writer);

                        //print known variants classification
                        printClassification(vcfRecord, writer);

                        //print population frequencies
                        printGenomicAnnotation(vcfRecord, writer);

                        writer.println();

                    }

                }

                writer.close();
            } catch (IOException e){
                log.log(Level.SEVERE, "Problem writing variant report: " + e.getMessage());
            }

        }
    }

    private boolean skipWildtypeGenotype(String genotype){

        try {
            if (genotype.equals("0/0") || genotype.equals("0|0") || genotype.equals("./.") || genotype.equals(".|.")) {
                return true;
            } else {
                return false;
            }
        } catch (NullPointerException e) {
            return true;
        }

    }
    private boolean isVariantLowQuality(String quality){

        try {
            if (Integer.parseInt(quality) < minGQ) {
                return true;
            } else {
                return false;
            }
        } catch (NullPointerException e) {
            return true;
        }

    }
    private void printGenotype(String genotype, PrintWriter writer){

        if (genotype.equals("0/1") || genotype.equals("0|1")) {
            writer.print("HET\t");
        } else if (genotype.equals("1|1")) {
            writer.print("HOM\t");
        } else {
            writer.print("\t");
        }

    }
    private void printVariantId(String field, PrintWriter writer){

        String[] idFields = field.split(";");

        for (int n = 1; n < idFields.length; ++n) {
            if (n == 1) {
                writer.print(idFields[n]);
            } else {
                writer.print(";" + idFields[n]);
            }
        }
        writer.print("\t");

    }
    private void printClassification(VCFRecord vcfRecord, PrintWriter writer){

        if (vcfRecord.getInfoSubFields().containsKey("known_variants.Classification")){
            writer.print(vcfRecord.getInfoSubFields().get("known_variants.Classification") + "\t");
        } else {
            writer.print("\t");
        }

    }
    private void printGenomicAnnotation(VCFRecord vcfRecord, PrintWriter writer){

        if (vepFile.getGenomeLevelRecords().containsKey(vcfRecord.getGenomeVariant().getConcatenatedVariant())) {

            if (vepFile.getGenomeLevelRecords().get(vcfRecord.getGenomeVariant().getConcatenatedVariant()).isHasAaMAF()) {
                writer.print(vepFile.getGenomeLevelRecords().get(vcfRecord.getGenomeVariant().getConcatenatedVariant()).getAaMAF() + "\t");
            } else {
                writer.print("\t");
            }

            if (vepFile.getGenomeLevelRecords().get(vcfRecord.getGenomeVariant().getConcatenatedVariant()).isHasEaMAF()) {
                writer.print(vepFile.getGenomeLevelRecords().get(vcfRecord.getGenomeVariant().getConcatenatedVariant()).getEaMAF() + "\t");
            } else {
                writer.print("\t");
            }

            if (vepFile.getGenomeLevelRecords().get(vcfRecord.getGenomeVariant().getConcatenatedVariant()).isHasAfrMAF()) {
                writer.print(vepFile.getGenomeLevelRecords().get(vcfRecord.getGenomeVariant().getConcatenatedVariant()).getAfrMAF() + "\t");
            } else {
                writer.print("\t");
            }

            if (vepFile.getGenomeLevelRecords().get(vcfRecord.getGenomeVariant().getConcatenatedVariant()).isHasAmrMAF()) {
                writer.print(vepFile.getGenomeLevelRecords().get(vcfRecord.getGenomeVariant().getConcatenatedVariant()).getAmrMAF() + "\t");
            } else {
                writer.print("\t");
            }

            if (vepFile.getGenomeLevelRecords().get(vcfRecord.getGenomeVariant().getConcatenatedVariant()).isHasAsnMAF()) {
                writer.print(vepFile.getGenomeLevelRecords().get(vcfRecord.getGenomeVariant().getConcatenatedVariant()).getAsnMAF() + "\t");
            } else {
                writer.print("\t");
            }

            if (vepFile.getGenomeLevelRecords().get(vcfRecord.getGenomeVariant().getConcatenatedVariant()).isHasEurMAF()) {
                writer.print(vepFile.getGenomeLevelRecords().get(vcfRecord.getGenomeVariant().getConcatenatedVariant()).getEurMAF() + "\t");
            } else {
                writer.print("\t");
            }
        } else {
            writer.print("\t\t\t\t\t\t");
        }

    }
    private void printConsequence(VEPTranscriptAnnotation transcriptAnnotation, PrintWriter writer){

        boolean firstCon = true;

        for (String con : transcriptAnnotation.getConsequences()) {
            if (firstCon) {
                writer.print("\t" + con);
                firstCon = false;
            } else {
                writer.print("," + con);
            }
        }
    }
    private void printCombinedAlleleDepth(String alleleDepthField, PrintWriter writer){

        int combinedDepth = 0;
        String[] fields = alleleDepthField.split(",");

        for (int n= 0; n < fields.length; ++n){
            combinedDepth += Integer.parseInt(fields[n]);
        }

        writer.print(combinedDepth + "\t");
    }
    private void printExonIntron(VEPTranscriptAnnotation transcriptAnnotation, PrintWriter writer){

        if (!transcriptAnnotation.getExonNo().equals("")){
            writer.print("\t" + transcriptAnnotation.getExonNo() + "\t");
        } else if (!transcriptAnnotation.getIntronNo().equals("")){
            writer.print("\t" + transcriptAnnotation.getIntronNo() + "\t");
        } else {
            writer.print("\t\t");
        }

    }
    private void printSift(VEPTranscriptAnnotation transcriptAnnotation, PrintWriter writer){

        if (!transcriptAnnotation.getSiftResult().equals("")) {
            writer.print(transcriptAnnotation.getSiftResult() + "\t" + transcriptAnnotation.getSiftScore() + "\t");
        } else {
            writer.print("\t\t");
        }

    }
    private void printPolyphen(VEPTranscriptAnnotation transcriptAnnotation, PrintWriter writer){
        if (!transcriptAnnotation.getPolyPhenResult().equals("")) {
            writer.print(transcriptAnnotation.getPolyPhenResult() + "\t" + transcriptAnnotation.getPolyphenScore());
        }
    }

    public void setMinGQ(int minGQ) {
        this.minGQ = minGQ;
    }
    public void setPrintDepth(boolean printDepth) {
        this.printDepth = printDepth;
    }
    public void setPrintAF(boolean printAF) {
        this.printAF = printAF;
    }
    public void setPrintFilter(boolean printFilter) {
        this.printFilter = printFilter;
    }

}