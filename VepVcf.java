package nhs.genetics.cardiff;

import com.fasterxml.jackson.databind.ObjectMapper;
import htsjdk.variant.variantcontext.Genotype;
import htsjdk.variant.variantcontext.VariantContext;
import htsjdk.variant.vcf.VCFHeader;
import nhs.genetics.cardiff.framework.GenomeVariant;
import nhs.genetics.cardiff.framework.Pair;
import nhs.genetics.cardiff.framework.SampleMetaData;
import nhs.genetics.cardiff.framework.vep.VepAnnotationObject;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;

/**
 * Class for extracting VCF genotype and VEP information using htsjdk
 *
 * @author  Sara Rey
 * @since   2016-11-08
 */

public class Vcf {

    private static final Logger log = Logger.getLogger(Vcf.class.getName());
    private VCFHeader vcfHeader;
    private String[] vepHeaders;
    private Integer vepVersion;
    private LinkedHashMap<GenomeVariant, ArrayList<VepAnnotationObject>> annotatedVariants = new LinkedHashMap<>();
    private LinkedHashMap<GenomeVariant, ArrayList<Pair<Genotype, Double>>> sampleVariants = new LinkedHashMap<>();
    private ArrayList<String> sampleNames;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private HashMap<String, SampleMetaData> sampleMetaDataHashMap = new HashMap<>();
    private String sample;

    public Vcf(VCFHeader vcfHeader, String sample) {
        this.vcfHeader = vcfHeader;
        this.sample = sample;
    }

    public void parseHeaders(){
        log.log(Level.INFO, "Parsing VCF headers");

        //get VEP version
        try {
            vepVersion = Integer.parseInt(vcfHeader.getOtherHeaderLine("VEP").getValue().split(" ")[0].split("v")[1]);
        } catch (NullPointerException e){
            log.log(Level.WARNING, "Could not determine VEP version. Continuing without it.");
        }

        //extract VEP headers
        try {
            vepHeaders = vcfHeader.getInfoHeaderLine("CSQ").getDescription().split("Format:")[1].trim().split("\\|");
        } catch (NullPointerException e){
            log.log(Level.WARNING, "Could not find VEP header. Assuming VCF has not been annotated");
        }

        //get sample list
        sampleNames = vcfHeader.getSampleNamesInOrder();
    }

    public void parseVcf(Stream<VariantContext> stream) {

        //read filtered VCF line by line
        stream.forEach(variantContext -> {

            //split site level annotations and pair with headers
            if (variantContext.hasAttribute("CSQ")){
                HashSet<VepAnnotationObject> annotations = new HashSet<>();

                try {
                    annotations.add(deserialiseVepAnnotation((String) variantContext.getAttribute("CSQ")));
                } catch (ClassCastException e) {
                    for (String field : (ArrayList<String>) variantContext.getAttribute("CSQ")) {
                        annotations.add(deserialiseVepAnnotation(field));
                    }
                }

                //loop over alternative alleles, create genomeVariants and associate with allele num
                for (int i=0; i < variantContext.getAlternateAlleles().size(); i++){

                    //create genome variant & convert to minimal representation
                    GenomeVariant genomeVariant = null;

                    if (!variantContext.getAlternateAlleles().get(i).isSymbolic()){
                        genomeVariant = new GenomeVariant(variantContext.getContig(), variantContext.getStart(), variantContext.getReference().getBaseString(), variantContext.getAlternateAlleles().get(i).getBaseString());
                        genomeVariant.convertToMinimalRepresentation();
                    } else {
                        genomeVariant = new GenomeVariant(variantContext.getContig(), variantContext.getStart(), variantContext.getReference().getBaseString(), variantContext.getEnd() + variantContext.getAlternateAlleles().get(i).toString());
                    }

                    //prep annotated variants map
                    annotatedVariants.put(genomeVariant, new ArrayList<>());

                    //find annotation associated with this allele_num
                    for (VepAnnotationObject vepAnnotation : annotations){
                        if (vepAnnotation.getAlleleNum() == (i+1)){
                            annotatedVariants.get(genomeVariant).add(vepAnnotation);
                        }
                    }

                }
            }

            //loop over genotypes
            variantContext.getGenotypes()
                    .stream()
                    .filter(genotype -> !genotype.isNoCall())
                    .filter(genotype -> !genotype.isHomRef())
                    .filter(genotype -> !genotype.isFiltered())
                    .forEach(genotype -> {

                        GenomeVariant genomeVariant = null;

                        //check genotype validity
                        if (genotype.isMixed()) {
                            throw new UnsupportedOperationException(genotype.getSampleName() + ": " + variantContext.getContig() + " " + variantContext.getStart() + " " + variantContext.getReference() + variantContext.getAlternateAlleles().toString() + " has mixed genotype ( " + genotype.getGenotypeString());
                        }

                        if (genotype.getPloidy() != 2 || genotype.getAlleles().size() != 2){
                            throw new UnsupportedOperationException("Allele " + genotype.getAlleles().toString() + " is not diploid.");
                        }

                        if (genotype.getAlleles().get(0).getBaseString().equals("*") || genotype.getAlleles().get(1).getBaseString().equals("*")) {
                            return;
                        }

                        //store genotype
                        if (genotype.isHom()){

                            if (!genotype.getAlleles().get(1).isSymbolic()){
                                genomeVariant = new GenomeVariant(variantContext.getContig(), variantContext.getStart(), variantContext.getReference().getBaseString(), genotype.getAlleles().get(1).getBaseString());
                                genomeVariant.convertToMinimalRepresentation();
                            } else {
                                genomeVariant = new GenomeVariant(variantContext.getContig(), variantContext.getStart(), variantContext.getReference().getBaseString(), variantContext.getEnd() + genotype.getAlleles().get(1).toString());
                            }

                            if (!sampleVariants.containsKey(genomeVariant)){
                                sampleVariants.put(genomeVariant, new ArrayList<>());
                            }

                            sampleVariants.get(genomeVariant).add(new Pair<>(genotype, variantContext.getPhredScaledQual()));

                        } else if (genotype.isHet()){

                            if (!genotype.getAlleles().get(1).isSymbolic()){
                                genomeVariant = new GenomeVariant(variantContext.getContig(), variantContext.getStart(), variantContext.getReference().getBaseString(), genotype.getAlleles().get(1).getBaseString());
                                genomeVariant.convertToMinimalRepresentation();
                            } else {
                                genomeVariant = new GenomeVariant(variantContext.getContig(), variantContext.getStart(), variantContext.getReference().getBaseString(), variantContext.getEnd() + genotype.getAlleles().get(1).toString());
                            }

                            if (!sampleVariants.containsKey(genomeVariant)){
                                sampleVariants.put(genomeVariant, new ArrayList<>());
                            }

                            sampleVariants.get(genomeVariant).add(new Pair<>(genotype, variantContext.getPhredScaledQual()));

                            if (genotype.isHetNonRef()){

                                if (!genotype.getAlleles().get(0).isSymbolic()){
                                    genomeVariant = new GenomeVariant(variantContext.getContig(), variantContext.getStart(), variantContext.getReference().getBaseString(), genotype.getAlleles().get(0).getBaseString());
                                    genomeVariant.convertToMinimalRepresentation();
                                } else {
                                    genomeVariant = new GenomeVariant(variantContext.getContig(), variantContext.getStart(), variantContext.getReference().getBaseString(), variantContext.getEnd() + genotype.getAlleles().get(0).toString());
                                }

                                if (!sampleVariants.containsKey(genomeVariant)){
                                    sampleVariants.put(genomeVariant, new ArrayList<>());
                                }

                                sampleVariants.get(genomeVariant).add(new Pair<>(genotype, variantContext.getPhredScaledQual()));
                            }

                        } else {
                            log.log(Level.WARNING, "Inheritance unknown: " + variantContext.toString() + " and could not be added.");
                        }

                    });

        });
    }

    private VepAnnotationObject deserialiseVepAnnotation(String csqField){

        HashMap<String, String> hashMap = new HashMap<String, String>();

        //split annotation fields
        String[] annotations = csqField.split("\\|");

        //pair headers with fields
        for (int i=0 ; i < annotations.length; i++) {
            hashMap.put(vepHeaders[i].trim(), annotations[i].trim());
        }

        try {
            return objectMapper.convertValue(hashMap, VepAnnotationObject.class);
        } catch (IllegalArgumentException e){
            log.log(Level.WARNING, "Could not find deserialize record: " + Arrays.toString(vepHeaders) + "\n" + Arrays.toString(annotations));
            throw e;
        }

    }
    private void setSampleMetaData(String sampleHeaderLine){
        HashMap<String, String> temp = new HashMap<>();

        for (String keyValue : sampleHeaderLine.substring(1, sampleHeaderLine.length() - 1).split(",")){
            temp.put(keyValue.split("=")[0], keyValue.split("=")[1]);
        }

        sampleMetaDataHashMap.put(temp.get("ID"), objectMapper.convertValue(temp, SampleMetaData.class));
    }

    public Integer getVepVersion() {
        return vepVersion;
    }
    public String[] getVepHeaders() {
        return vepHeaders;
    }
    public LinkedHashMap<GenomeVariant, ArrayList<VepAnnotationObject>> getAnnotatedVariants() {
        return annotatedVariants;
    }
    public LinkedHashMap<GenomeVariant, ArrayList<Pair<Genotype, Double>>> getSampleVariants() {
        return sampleVariants;
    }
    public ArrayList<String> getSampleNames() {
        return sampleNames;
    }
    public HashMap<String, SampleMetaData> getSampleMetaDataHashMap() {
        return sampleMetaDataHashMap;
    }
}
