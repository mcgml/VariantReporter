package nhs.genetics.cardiff;

import htsjdk.variant.variantcontext.Genotype;
import nhs.genetics.cardiff.framework.GenomeVariant;
import nhs.genetics.cardiff.framework.Pair;
import nhs.genetics.cardiff.framework.vep.VepAnnotationObject;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Writes out desired fields to a tab-delimited text file
 *
 * @author  Sara Rey
 * @since   2016-12-08
 */

public class WriteOut {
    private static final Logger log = Logger.getLogger(WriteOut.class.getName());

    public static void writeToTable(Vcf vcf, HashSet<String> preferredTranscripts, HashMap<GenomeVariant, Integer> classifiedVariants, Boolean onlyReportKnownRefSeq, String outputFilenamePrefix) throws IOException {
        log.log(Level.INFO, "Writing results to table");

        //write header lines & create new file
        for (String sampleId : vcf.getSampleNames()){
            try (PrintWriter writer = new PrintWriter(outputFilenamePrefix != null ? outputFilenamePrefix + "_" + sampleId + "_VariantReport.txt" : sampleId + "_VariantReport.txt")){
                writer.println("SampleID\tVariant\tAltFrequency\tDepth\tGenotype\tQuality\tClassification\tPreferredTranscript\tdbSNP\tCosmic\tHGMD\tExAC_AFR\tExAC_AMR\tExAC_EAS\tExAC_FIN\tExAC_NFE\tExAC_SAS\tExAC_OTH\t1KG_African\t1KG_American\t1KG_EastAsian\t1KG_European\t1KG_SouthAsian\tGene\tTranscript\tHGVSc\tHGVSp\tConsequence\tIntron\tExon\tSIFT\tPolyPhen");
            }
        }

        //loop over all variants
        for (Map.Entry<GenomeVariant, ArrayList<Pair<Genotype, Double>>> variantGenotypeEntry : vcf.getSampleVariants().entrySet()){

            //loop over all genotypes
            for (Pair<Genotype, Double> genotype : variantGenotypeEntry.getValue()){

                //write to variant report & append to header file
                try (FileWriter fileWriter = new FileWriter(outputFilenamePrefix != null ? outputFilenamePrefix + "_" + genotype.getLeft().getSampleName() + "_VariantReport.txt" : genotype.getLeft().getSampleName() + "_VariantReport.txt", true);
                     BufferedWriter bufferedWriter = new BufferedWriter(fileWriter); PrintWriter printWriter = new PrintWriter(bufferedWriter)) {

                    boolean printed = false;

                    //check VEP annotation exists and print
                    if (vcf.getAnnotatedVariants().containsKey(variantGenotypeEntry.getKey())){
                        for (VepAnnotationObject vepAnnotation : vcf.getAnnotatedVariants().get(variantGenotypeEntry.getKey())){

                            //skip non NM_ transcripts
                            if (onlyReportKnownRefSeq && (vepAnnotation.getFeature() == null || !vepAnnotation.getFeature().startsWith("NM_"))){
                                continue;
                            }

                            //print variant details
                            printWriter.print(genotype.getLeft().getSampleName());
                            printWriter.print("\t");
                            printWriter.print(variantGenotypeEntry.getKey());
                            printWriter.print("\t");
                            printWriter.print(genotype.getLeft().getAnyAttribute("VF"));
                            printWriter.print("\t");
                            printWriter.print(genotype.getLeft().hasDP() ? genotype.getLeft().getDP() : null);
                            printWriter.print("\t");
                            printWriter.print(genotype.getLeft().getType());
                            printWriter.print("\t");
                            printWriter.print(String.format("%.2f",genotype.getRight()));
                            printWriter.print("\t");
                            if (classifiedVariants != null && classifiedVariants.containsKey(variantGenotypeEntry.getKey())){
                                printWriter.print(classifiedVariants.get(variantGenotypeEntry.getKey()));
                            }

                            //print annotations
                            printWriter.print("\t");
                            printWriter.print(preferredTranscripts !=null && preferredTranscripts.contains(vepAnnotation.getFeature()));
                            printWriter.print("\t");
                            printWriter.print(vepAnnotation);

                            printWriter.println();

                            printed = true;

                        }
                    }

                    if (!printed){
                        //print variant details
                        printWriter.print(genotype.getLeft().getSampleName());
                        printWriter.print("\t");
                        printWriter.print(variantGenotypeEntry.getKey());
                        printWriter.print("\t");
                        printWriter.print(genotype.getLeft().getAnyAttribute("VF"));
                        printWriter.print("\t");
                        printWriter.print(genotype.getLeft().hasDP() ? genotype.getLeft().getDP() : null);
                        printWriter.print("\t");
                        printWriter.print(genotype.getLeft().getType());
                        printWriter.print("\t");
                        printWriter.print(String.format("%.2f",genotype.getRight()));
                        printWriter.print("\t");

                        if (classifiedVariants != null && classifiedVariants.containsKey(variantGenotypeEntry.getKey())){
                            printWriter.print(classifiedVariants.get(variantGenotypeEntry.getKey()));
                        }

                        printWriter.print("\t");
                        printWriter.println(false);
                    }

                }

            }

        }

    }

}


