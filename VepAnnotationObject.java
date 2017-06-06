package nhs.genetics.cardiff.framework.vep;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.util.Arrays;
import java.util.HashMap;
import java.util.stream.Collectors;

/**
 * Custom deserializer for Jackson & vep record
 *
 * @author  Matthew Lyon
 * @since   2017-01-25
 */

public class VepAnnotationObject {

    @JsonProperty("Allele")
    @JsonDeserialize(using = StringDeserializer.class)
    private String allele;

    @JsonProperty("Consequence")
    @JsonDeserialize(using = StringArrayDeserializer.class)
    private String[] consequence;

    @JsonProperty("IMPACT")
    @JsonDeserialize(using = StringDeserializer.class)
    private String impact;

    @JsonProperty("SYMBOL")
    @JsonDeserialize(using = StringDeserializer.class)
    private String symbol;

    @JsonProperty("Gene")
    private Integer gene;

    @JsonProperty("Feature_type")
    @JsonDeserialize(using = StringDeserializer.class)
    private String featureType;

    @JsonProperty("Feature")
    @JsonDeserialize(using = StringDeserializer.class)
    private String feature;

    @JsonProperty("BIOTYPE")
    @JsonDeserialize(using = StringDeserializer.class)
    private String biotype;

    @JsonProperty("EXON")
    @JsonDeserialize(using = ExonIntronDeserializer.class)
    private String exon;

    @JsonProperty("INTRON")
    @JsonDeserialize(using = ExonIntronDeserializer.class)
    private String intron;

    @JsonProperty("HGVSc")
    @JsonDeserialize(using = HgvsDeserializer.class)
    private String hgvsc;

    @JsonProperty("HGVSp")
    @JsonDeserialize(using = HgvsDeserializer.class)
    private String hgvsp;

    @JsonProperty("cDNA_position")
    private String cdnaPosition;

    @JsonProperty("CDS_position")
    private String cdsPosition;

    @JsonProperty("Protein_position")
    private String proteinPosition;

    @JsonProperty("Amino_acids")
    @JsonDeserialize(using = StringDeserializer.class)
    private String aminoAcids;

    @JsonProperty("Codons")
    @JsonDeserialize(using = StringDeserializer.class)
    private String codons;

    @JsonProperty("Existing_variation")
    @JsonDeserialize(using = StringArrayDeserializer.class)
    private String[] existingVariation;

    @JsonProperty("ALLELE_NUM")
    private Integer alleleNum;

    @JsonProperty("DISTANCE")
    private Integer distance;

    @JsonProperty("STRAND")
    private Integer strand;

    @JsonProperty("FLAGS")
    @JsonDeserialize(using = StringDeserializer.class)
    private String flags;

    @JsonProperty("VARIANT_CLASS")
    @JsonDeserialize(using = StringDeserializer.class)
    private String variantClass;

    @JsonProperty("SYMBOL_SOURCE")
    @JsonDeserialize(using = StringDeserializer.class)
    private String symbolSource;

    @JsonProperty("HGNC_ID")
    private Integer hgncId;

    @JsonProperty("CANONICAL")
    @JsonDeserialize(using = YesNoBoolDeserializer.class)
    private Boolean canonical;

    @JsonProperty("TSL")
    private Integer transcriptSupportLevel;

    @JsonProperty("APPRIS")
    private Integer appris;

    @JsonProperty("CCDS")
    @JsonDeserialize(using = StringDeserializer.class)
    private String ccds;

    @JsonProperty("ENSP")
    @JsonDeserialize(using = StringDeserializer.class)
    private String ensp;

    @JsonProperty("SWISSPROT")
    @JsonDeserialize(using = StringDeserializer.class)
    private String swissprot;

    @JsonProperty("TREMBL")
    @JsonDeserialize(using = StringDeserializer.class)
    private String trembl;

    @JsonProperty("UNIPARC")
    @JsonDeserialize(using = StringDeserializer.class)
    private String uniparc;

    @JsonProperty("REFSEQ_MATCH")
    @JsonDeserialize(using = StringDeserializer.class)
    private String refseqMatch;

    @JsonProperty("GENE_PHENO")
    @JsonDeserialize(using = StringDeserializer.class)
    private String genePheno;

    @JsonProperty("SIFT")
    @JsonDeserialize(using = StringDeserializer.class)
    private String sift;

    @JsonProperty("PolyPhen")
    @JsonDeserialize(using = StringDeserializer.class)
    private String polyphen;

    @JsonProperty("DOMAINS")
    @JsonDeserialize(using = StringDeserializer.class)
    private String domains;

    @JsonProperty("HGVS_OFFSET")
    private Integer hgvsOffset;

    @JsonProperty("GMAF")
    @JsonDeserialize(using = MafDeserializer.class)
    private HashMap<String, Double> gmaf;

    @JsonProperty("AFR_MAF")
    @JsonDeserialize(using = MafDeserializer.class)
    private HashMap<String, Double> afrMaf;

    @JsonProperty("AMR_MAF")
    @JsonDeserialize(using = MafDeserializer.class)
    private HashMap<String, Double> amrMaf;

    @JsonProperty("EAS_MAF")
    @JsonDeserialize(using = MafDeserializer.class)
    private HashMap<String, Double> easMaf;

    @JsonProperty("EUR_MAF")
    @JsonDeserialize(using = MafDeserializer.class)
    private HashMap<String, Double> eurMaf;

    @JsonProperty("SAS_MAF")
    @JsonDeserialize(using = MafDeserializer.class)
    private HashMap<String, Double> sasMaf;

    @JsonProperty("AA_MAF")
    @JsonDeserialize(using = MafDeserializer.class)
    private HashMap<String, Double> aaMaf;

    @JsonProperty("EA_MAF")
    @JsonDeserialize(using = MafDeserializer.class)
    private HashMap<String, Double> eaMaf;

    @JsonProperty("ExAC_MAF")
    @JsonDeserialize(using = MafDeserializer.class)
    private HashMap<String, Double> exacMaf;

    @JsonProperty("ExAC_Adj_MAF")
    @JsonDeserialize(using = MafDeserializer.class)
    private HashMap<String, Double> exacAdjMaf;

    @JsonProperty("ExAC_AFR_MAF")
    @JsonDeserialize(using = MafDeserializer.class)
    private HashMap<String, Double> exacAfrMaf;

    @JsonProperty("ExAC_AMR_MAF")
    @JsonDeserialize(using = MafDeserializer.class)
    private HashMap<String, Double> exacAmrMaf;

    @JsonProperty("ExAC_EAS_MAF")
    @JsonDeserialize(using = MafDeserializer.class)
    private HashMap<String, Double> exacEasMaf;

    @JsonProperty("ExAC_FIN_MAF")
    @JsonDeserialize(using = MafDeserializer.class)
    private HashMap<String, Double> exacFinMaf;

    @JsonProperty("ExAC_NFE_MAF")
    @JsonDeserialize(using = MafDeserializer.class)
    private HashMap<String, Double> exacNfeMaf;

    @JsonProperty("ExAC_OTH_MAF")
    @JsonDeserialize(using = MafDeserializer.class)
    private HashMap<String, Double> exacOthMaf;

    @JsonProperty("ExAC_SAS_MAF")
    @JsonDeserialize(using = MafDeserializer.class)
    private HashMap<String, Double> exacSasMaf;

    @JsonProperty("CLIN_SIG")
    @JsonDeserialize(using = StringDeserializer.class)
    private String clinSig;

    @JsonProperty("SOMATIC")
    @JsonDeserialize(using = StringDeserializer.class)
    private String somatic;

    @JsonProperty("PHENO")
    @JsonDeserialize(using = StringDeserializer.class)
    private String pheno;

    @JsonProperty("PUBMED")
    @JsonDeserialize(using = StringArrayDeserializer.class)
    private String[] pubmed;

    @JsonProperty("MOTIF_NAME")
    @JsonDeserialize(using = StringDeserializer.class)
    private String motifName;

    @JsonProperty("MOTIF_POS")
    @JsonDeserialize(using = StringDeserializer.class)
    private String motifPos;

    @JsonProperty("HIGH_INF_POS")
    @JsonDeserialize(using = StringDeserializer.class)
    private String highInfPos;

    @JsonProperty("MOTIF_SCORE_CHANGE")
    @JsonDeserialize(using = StringDeserializer.class)
    private String motifScoreChange;

    public VepAnnotationObject() {

    }

    private static String getMafAsPercentage(HashMap<String, Double> maf, String allele){
        try {
            return String.format("%.2f", maf.get(allele) * 100) + "%";
        } catch (NullPointerException e){
            return "0%";
        }
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();

        //dbSNP, cosmic etc
        stringBuilder.append(getDbSnpIds()); stringBuilder.append("\t");
        stringBuilder.append(getCosmicIds()); stringBuilder.append("\t");
        stringBuilder.append(getHGMDIds()); stringBuilder.append("\t");

        //exac
        stringBuilder.append(getMafAsPercentage(exacAfrMaf, allele)); stringBuilder.append("\t");
        stringBuilder.append(getMafAsPercentage(exacAmrMaf, allele)); stringBuilder.append("\t");
        stringBuilder.append(getMafAsPercentage(exacEasMaf, allele)); stringBuilder.append("\t");
        stringBuilder.append(getMafAsPercentage(exacFinMaf, allele)); stringBuilder.append("\t");
        stringBuilder.append(getMafAsPercentage(exacNfeMaf, allele)); stringBuilder.append("\t");
        stringBuilder.append(getMafAsPercentage(exacSasMaf, allele)); stringBuilder.append("\t");
        stringBuilder.append(getMafAsPercentage(exacOthMaf, allele)); stringBuilder.append("\t");

        //1kg
        stringBuilder.append(getMafAsPercentage(afrMaf, allele)); stringBuilder.append("\t");
        stringBuilder.append(getMafAsPercentage(amrMaf, allele)); stringBuilder.append("\t");
        stringBuilder.append(getMafAsPercentage(easMaf, allele)); stringBuilder.append("\t");
        stringBuilder.append(getMafAsPercentage(eurMaf, allele)); stringBuilder.append("\t");
        stringBuilder.append(getMafAsPercentage(sasMaf, allele)); stringBuilder.append("\t");

        //transcript level annotations
        if (symbol != null) stringBuilder.append(symbol); stringBuilder.append("\t");
        if (feature != null) stringBuilder.append(feature); stringBuilder.append("\t");
        if (hgvsc != null) stringBuilder.append(hgvsc); stringBuilder.append("\t");
        if (hgvsp != null) stringBuilder.append(hgvsp); stringBuilder.append("\t");
        if (consequence != null) stringBuilder.append(Arrays.stream(consequence).collect(Collectors.joining(","))); stringBuilder.append("\t");
        if (intron != null) stringBuilder.append(intron); stringBuilder.append("\t");
        if (exon != null) stringBuilder.append(exon); stringBuilder.append("\t");
        if (sift != null) stringBuilder.append(sift); stringBuilder.append("\t");
        if (polyphen != null) stringBuilder.append(polyphen);

        return stringBuilder.toString();
    }

    public String getDbSnpIds(){
        if (existingVariation == null) return "";
        return Arrays.stream(existingVariation)
                .filter(string -> string.startsWith("rs"))
                .collect(Collectors.joining(","));
    }
    public String getCosmicIds(){
        if (existingVariation == null) return "";
        return Arrays.stream(existingVariation)
                .filter(string -> string.startsWith("COSM"))
                .collect(Collectors.joining(","));
    }
    public String getHGMDIds(){
        if (existingVariation == null) return "";
        return Arrays.stream(existingVariation)
                .filter(string -> string.startsWith("CM"))
                .collect(Collectors.joining(","));
    }

    public String getAllele() {
        return allele;
    }
    public String getCdnaPosition() {
        return cdnaPosition;
    }
    public String[] getConsequence() {
        return consequence;
    }
    public String getImpact() {
        return impact;
    }
    public String getSymbol() {
        return symbol;
    }
    public Integer getGene() {
        return gene;
    }
    public String getFeatureType() {
        return featureType;
    }
    public String getFeature() {
        return feature;
    }
    public String getBiotype() {
        return biotype;
    }
    public String getExon() {
        return exon;
    }
    public String getIntron() {
        return intron;
    }
    public String getHgvsc() {
        return hgvsc;
    }
    public String getHgvsp() {
        return hgvsp;
    }
    public String getCdsPosition() {
        return cdsPosition;
    }
    public String getProteinPosition() {
        return proteinPosition;
    }
    public String getAminoAcids() {
        return aminoAcids;
    }
    public String getCodons() {
        return codons;
    }
    public String[] getExistingVariation() {
        return existingVariation;
    }
    public Integer getAlleleNum() {
        return alleleNum;
    }
    public Integer getDistance() {
        return distance;
    }
    public Integer getStrand() {
        return strand;
    }
    public String getFlags() {
        return flags;
    }
    public String getVariantClass() {
        return variantClass;
    }
    public String getSymbolSource() {
        return symbolSource;
    }
    public Integer getHgncId() {
        return hgncId;
    }
    public Boolean getCanonical() {
        return canonical;
    }
    public Integer getTranscriptSupportLevel() {
        return transcriptSupportLevel;
    }
    public Integer getAppris() {
        return appris;
    }
    public String getCcds() {
        return ccds;
    }
    public String getEnsp() {
        return ensp;
    }
    public String getSwissprot() {
        return swissprot;
    }
    public String getTrembl() {
        return trembl;
    }
    public String getUniparc() {
        return uniparc;
    }
    public String getRefseqMatch() {
        return refseqMatch;
    }
    public String getGenePheno() {
        return genePheno;
    }
    public String getSift() {
        return sift;
    }
    public String getPolyphen() {
        return polyphen;
    }
    public String getDomains() {
        return domains;
    }
    public Integer getHgvsOffset() {
        return hgvsOffset;
    }
    public HashMap<String, Double> getGmaf() {
        return gmaf;
    }
    public HashMap<String, Double> getAfrMaf() {
        return afrMaf;
    }
    public HashMap<String, Double> getAmrMaf() {
        return amrMaf;
    }
    public HashMap<String, Double> getEasMaf() {
        return easMaf;
    }
    public HashMap<String, Double> getEurMaf() {
        return eurMaf;
    }
    public HashMap<String, Double> getSasMaf() {
        return sasMaf;
    }
    public HashMap<String, Double> getAaMaf() {
        return aaMaf;
    }
    public HashMap<String, Double> getEaMaf() {
        return eaMaf;
    }
    public HashMap<String, Double> getExacMaf() {
        return exacMaf;
    }
    public HashMap<String, Double> getExacAdjMaf() {
        return exacAdjMaf;
    }
    public HashMap<String, Double> getExacAfrMaf() {
        return exacAfrMaf;
    }
    public HashMap<String, Double> getExacAmrMaf() {
        return exacAmrMaf;
    }
    public HashMap<String, Double> getExacEasMaf() {
        return exacEasMaf;
    }
    public HashMap<String, Double> getExacFinMaf() {
        return exacFinMaf;
    }
    public HashMap<String, Double> getExacNfeMaf() {
        return exacNfeMaf;
    }
    public HashMap<String, Double> getExacOthMaf() {
        return exacOthMaf;
    }
    public HashMap<String, Double> getExacSasMaf() {
        return exacSasMaf;
    }
    public String getClinSig() {
        return clinSig;
    }
    public String getSomatic() {
        return somatic;
    }
    public String getPheno() {
        return pheno;
    }
    public String[] getPubmed() {
        return pubmed;
    }
    public String getMotifName() {
        return motifName;
    }
    public String getMotifPos() {
        return motifPos;
    }
    public String getHighInfPos() {
        return highInfPos;
    }
    public String getMotifScoreChange() {
        return motifScoreChange;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        VepAnnotationObject that = (VepAnnotationObject) o;

        if (allele != null ? !allele.equals(that.allele) : that.allele != null) return false;
        // Probably incorrect - comparing Object[] arrays with Arrays.equals
        if (!Arrays.equals(consequence, that.consequence)) return false;
        if (impact != null ? !impact.equals(that.impact) : that.impact != null) return false;
        if (symbol != null ? !symbol.equals(that.symbol) : that.symbol != null) return false;
        if (gene != null ? !gene.equals(that.gene) : that.gene != null) return false;
        if (featureType != null ? !featureType.equals(that.featureType) : that.featureType != null) return false;
        if (feature != null ? !feature.equals(that.feature) : that.feature != null) return false;
        if (biotype != null ? !biotype.equals(that.biotype) : that.biotype != null) return false;
        if (exon != null ? !exon.equals(that.exon) : that.exon != null) return false;
        if (intron != null ? !intron.equals(that.intron) : that.intron != null) return false;
        if (hgvsc != null ? !hgvsc.equals(that.hgvsc) : that.hgvsc != null) return false;
        if (hgvsp != null ? !hgvsp.equals(that.hgvsp) : that.hgvsp != null) return false;
        if (cdnaPosition != null ? !cdnaPosition.equals(that.cdnaPosition) : that.cdnaPosition != null) return false;
        if (cdsPosition != null ? !cdsPosition.equals(that.cdsPosition) : that.cdsPosition != null) return false;
        if (proteinPosition != null ? !proteinPosition.equals(that.proteinPosition) : that.proteinPosition != null)
            return false;
        if (aminoAcids != null ? !aminoAcids.equals(that.aminoAcids) : that.aminoAcids != null) return false;
        if (codons != null ? !codons.equals(that.codons) : that.codons != null) return false;
        // Probably incorrect - comparing Object[] arrays with Arrays.equals
        if (!Arrays.equals(existingVariation, that.existingVariation)) return false;
        if (alleleNum != null ? !alleleNum.equals(that.alleleNum) : that.alleleNum != null) return false;
        if (distance != null ? !distance.equals(that.distance) : that.distance != null) return false;
        if (strand != null ? !strand.equals(that.strand) : that.strand != null) return false;
        if (flags != null ? !flags.equals(that.flags) : that.flags != null) return false;
        if (variantClass != null ? !variantClass.equals(that.variantClass) : that.variantClass != null) return false;
        if (symbolSource != null ? !symbolSource.equals(that.symbolSource) : that.symbolSource != null) return false;
        if (hgncId != null ? !hgncId.equals(that.hgncId) : that.hgncId != null) return false;
        if (canonical != null ? !canonical.equals(that.canonical) : that.canonical != null) return false;
        if (transcriptSupportLevel != null ? !transcriptSupportLevel.equals(that.transcriptSupportLevel) : that.transcriptSupportLevel != null)
            return false;
        if (appris != null ? !appris.equals(that.appris) : that.appris != null) return false;
        if (ccds != null ? !ccds.equals(that.ccds) : that.ccds != null) return false;
        if (ensp != null ? !ensp.equals(that.ensp) : that.ensp != null) return false;
        if (swissprot != null ? !swissprot.equals(that.swissprot) : that.swissprot != null) return false;
        if (trembl != null ? !trembl.equals(that.trembl) : that.trembl != null) return false;
        if (uniparc != null ? !uniparc.equals(that.uniparc) : that.uniparc != null) return false;
        if (refseqMatch != null ? !refseqMatch.equals(that.refseqMatch) : that.refseqMatch != null) return false;
        if (genePheno != null ? !genePheno.equals(that.genePheno) : that.genePheno != null) return false;
        if (sift != null ? !sift.equals(that.sift) : that.sift != null) return false;
        if (polyphen != null ? !polyphen.equals(that.polyphen) : that.polyphen != null) return false;
        if (domains != null ? !domains.equals(that.domains) : that.domains != null) return false;
        if (hgvsOffset != null ? !hgvsOffset.equals(that.hgvsOffset) : that.hgvsOffset != null) return false;
        if (gmaf != null ? !gmaf.equals(that.gmaf) : that.gmaf != null) return false;
        if (afrMaf != null ? !afrMaf.equals(that.afrMaf) : that.afrMaf != null) return false;
        if (amrMaf != null ? !amrMaf.equals(that.amrMaf) : that.amrMaf != null) return false;
        if (easMaf != null ? !easMaf.equals(that.easMaf) : that.easMaf != null) return false;
        if (eurMaf != null ? !eurMaf.equals(that.eurMaf) : that.eurMaf != null) return false;
        if (sasMaf != null ? !sasMaf.equals(that.sasMaf) : that.sasMaf != null) return false;
        if (aaMaf != null ? !aaMaf.equals(that.aaMaf) : that.aaMaf != null) return false;
        if (eaMaf != null ? !eaMaf.equals(that.eaMaf) : that.eaMaf != null) return false;
        if (exacMaf != null ? !exacMaf.equals(that.exacMaf) : that.exacMaf != null) return false;
        if (exacAdjMaf != null ? !exacAdjMaf.equals(that.exacAdjMaf) : that.exacAdjMaf != null) return false;
        if (exacAfrMaf != null ? !exacAfrMaf.equals(that.exacAfrMaf) : that.exacAfrMaf != null) return false;
        if (exacAmrMaf != null ? !exacAmrMaf.equals(that.exacAmrMaf) : that.exacAmrMaf != null) return false;
        if (exacEasMaf != null ? !exacEasMaf.equals(that.exacEasMaf) : that.exacEasMaf != null) return false;
        if (exacFinMaf != null ? !exacFinMaf.equals(that.exacFinMaf) : that.exacFinMaf != null) return false;
        if (exacNfeMaf != null ? !exacNfeMaf.equals(that.exacNfeMaf) : that.exacNfeMaf != null) return false;
        if (exacOthMaf != null ? !exacOthMaf.equals(that.exacOthMaf) : that.exacOthMaf != null) return false;
        if (exacSasMaf != null ? !exacSasMaf.equals(that.exacSasMaf) : that.exacSasMaf != null) return false;
        if (clinSig != null ? !clinSig.equals(that.clinSig) : that.clinSig != null) return false;
        if (somatic != null ? !somatic.equals(that.somatic) : that.somatic != null) return false;
        if (pheno != null ? !pheno.equals(that.pheno) : that.pheno != null) return false;
        // Probably incorrect - comparing Object[] arrays with Arrays.equals
        if (!Arrays.equals(pubmed, that.pubmed)) return false;
        if (motifName != null ? !motifName.equals(that.motifName) : that.motifName != null) return false;
        if (motifPos != null ? !motifPos.equals(that.motifPos) : that.motifPos != null) return false;
        if (highInfPos != null ? !highInfPos.equals(that.highInfPos) : that.highInfPos != null) return false;
        return motifScoreChange != null ? motifScoreChange.equals(that.motifScoreChange) : that.motifScoreChange == null;

    }

    @Override
    public int hashCode() {
        int result = allele != null ? allele.hashCode() : 0;
        result = 31 * result + Arrays.hashCode(consequence);
        result = 31 * result + (impact != null ? impact.hashCode() : 0);
        result = 31 * result + (symbol != null ? symbol.hashCode() : 0);
        result = 31 * result + (gene != null ? gene.hashCode() : 0);
        result = 31 * result + (featureType != null ? featureType.hashCode() : 0);
        result = 31 * result + (feature != null ? feature.hashCode() : 0);
        result = 31 * result + (biotype != null ? biotype.hashCode() : 0);
        result = 31 * result + (exon != null ? exon.hashCode() : 0);
        result = 31 * result + (intron != null ? intron.hashCode() : 0);
        result = 31 * result + (hgvsc != null ? hgvsc.hashCode() : 0);
        result = 31 * result + (hgvsp != null ? hgvsp.hashCode() : 0);
        result = 31 * result + (cdnaPosition != null ? cdnaPosition.hashCode() : 0);
        result = 31 * result + (cdsPosition != null ? cdsPosition.hashCode() : 0);
        result = 31 * result + (proteinPosition != null ? proteinPosition.hashCode() : 0);
        result = 31 * result + (aminoAcids != null ? aminoAcids.hashCode() : 0);
        result = 31 * result + (codons != null ? codons.hashCode() : 0);
        result = 31 * result + Arrays.hashCode(existingVariation);
        result = 31 * result + (alleleNum != null ? alleleNum.hashCode() : 0);
        result = 31 * result + (distance != null ? distance.hashCode() : 0);
        result = 31 * result + (strand != null ? strand.hashCode() : 0);
        result = 31 * result + (flags != null ? flags.hashCode() : 0);
        result = 31 * result + (variantClass != null ? variantClass.hashCode() : 0);
        result = 31 * result + (symbolSource != null ? symbolSource.hashCode() : 0);
        result = 31 * result + (hgncId != null ? hgncId.hashCode() : 0);
        result = 31 * result + (canonical != null ? canonical.hashCode() : 0);
        result = 31 * result + (transcriptSupportLevel != null ? transcriptSupportLevel.hashCode() : 0);
        result = 31 * result + (appris != null ? appris.hashCode() : 0);
        result = 31 * result + (ccds != null ? ccds.hashCode() : 0);
        result = 31 * result + (ensp != null ? ensp.hashCode() : 0);
        result = 31 * result + (swissprot != null ? swissprot.hashCode() : 0);
        result = 31 * result + (trembl != null ? trembl.hashCode() : 0);
        result = 31 * result + (uniparc != null ? uniparc.hashCode() : 0);
        result = 31 * result + (refseqMatch != null ? refseqMatch.hashCode() : 0);
        result = 31 * result + (genePheno != null ? genePheno.hashCode() : 0);
        result = 31 * result + (sift != null ? sift.hashCode() : 0);
        result = 31 * result + (polyphen != null ? polyphen.hashCode() : 0);
        result = 31 * result + (domains != null ? domains.hashCode() : 0);
        result = 31 * result + (hgvsOffset != null ? hgvsOffset.hashCode() : 0);
        result = 31 * result + (gmaf != null ? gmaf.hashCode() : 0);
        result = 31 * result + (afrMaf != null ? afrMaf.hashCode() : 0);
        result = 31 * result + (amrMaf != null ? amrMaf.hashCode() : 0);
        result = 31 * result + (easMaf != null ? easMaf.hashCode() : 0);
        result = 31 * result + (eurMaf != null ? eurMaf.hashCode() : 0);
        result = 31 * result + (sasMaf != null ? sasMaf.hashCode() : 0);
        result = 31 * result + (aaMaf != null ? aaMaf.hashCode() : 0);
        result = 31 * result + (eaMaf != null ? eaMaf.hashCode() : 0);
        result = 31 * result + (exacMaf != null ? exacMaf.hashCode() : 0);
        result = 31 * result + (exacAdjMaf != null ? exacAdjMaf.hashCode() : 0);
        result = 31 * result + (exacAfrMaf != null ? exacAfrMaf.hashCode() : 0);
        result = 31 * result + (exacAmrMaf != null ? exacAmrMaf.hashCode() : 0);
        result = 31 * result + (exacEasMaf != null ? exacEasMaf.hashCode() : 0);
        result = 31 * result + (exacFinMaf != null ? exacFinMaf.hashCode() : 0);
        result = 31 * result + (exacNfeMaf != null ? exacNfeMaf.hashCode() : 0);
        result = 31 * result + (exacOthMaf != null ? exacOthMaf.hashCode() : 0);
        result = 31 * result + (exacSasMaf != null ? exacSasMaf.hashCode() : 0);
        result = 31 * result + (clinSig != null ? clinSig.hashCode() : 0);
        result = 31 * result + (somatic != null ? somatic.hashCode() : 0);
        result = 31 * result + (pheno != null ? pheno.hashCode() : 0);
        result = 31 * result + Arrays.hashCode(pubmed);
        result = 31 * result + (motifName != null ? motifName.hashCode() : 0);
        result = 31 * result + (motifPos != null ? motifPos.hashCode() : 0);
        result = 31 * result + (highInfPos != null ? highInfPos.hashCode() : 0);
        result = 31 * result + (motifScoreChange != null ? motifScoreChange.hashCode() : 0);
        return result;
    }
}