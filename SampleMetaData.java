package nhs.genetics.cardiff.framework;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;

/**
 * Class for storing VCF sample metadata
 *
 * @author  Matthew Lyon
 * @since   2017-02-01
 */
@JsonIgnoreProperties({"ID"})
public class SampleMetaData {

    /*Global*/

    @JsonProperty("Tissue")
    private String tissue;

    @JsonProperty("WorklistId")
    private String worklistId;

    @JsonProperty("SeqId")
    private String seqId;

    @JsonProperty("Assay")
    private String assay;

    @JsonProperty("PipelineName")
    private String pipelineName;

    @JsonProperty("PipelineVersion")
    private String pipelineVersion;

    @JsonProperty("RawSequenceQuality")
    private String rawSequenceQuality;

    @JsonProperty("TotalReads")
    private Long totalReads;

    @JsonProperty("PctSelectedBases")
    private Double pctSelectedBases;

    @JsonProperty("MeanOnTargetCoverage")
    private Double meanOnTargetCoverage;

    @JsonProperty("PctTargetBasesCt")
    private Double pctTargetBasesCt;

    @JsonProperty("TotalTargetedUsableBases")
    private Long totalTargetedUsableBases;

    @JsonProperty("RemoteBamFilePath")
    private File remoteBamFilePath;

    @JsonProperty("RemoteVcfFilePath")
    private File remoteVcfFilePath;

    /*Enrichment specific*/

    @JsonProperty("GenotypicGender")
    private Gender genotypicGender;

    @JsonProperty("MeanInsertSize")
    private Double meanInsertSize;

    @JsonProperty("SDInsertSize")
    private Double stdevInsertSize;

    @JsonProperty("DuplicationRate")
    private Double duplicationRate;

    @JsonProperty("EstimatedContamination")
    private Double estimatedContamination;

    public SampleMetaData(){

    }

    @Override
    public String toString() {
        try {
            return new ObjectMapper().writeValueAsString(this);
        } catch (JsonProcessingException e){
            return "{}";
        }
    }

    public String getTissue() {
        return tissue;
    }
    public String getWorklistId() {
        return worklistId;
    }
    public String getSeqId() {
        return seqId;
    }
    public String getAssay() {
        return assay;
    }
    public String getPipelineName() {
        return pipelineName;
    }
    public String getPipelineVersion() {
        return pipelineVersion;
    }
    public String getRawSequenceQuality() {
        return rawSequenceQuality;
    }
    public Long getTotalReads() {
        return totalReads;
    }
    public Double getPctSelectedBases() {
        return pctSelectedBases;
    }
    public Double getMeanOnTargetCoverage() {
        return meanOnTargetCoverage;
    }
    public Double getPctTargetBasesCt() {
        return pctTargetBasesCt;
    }
    public Long getTotalTargetedUsableBases() {
        return totalTargetedUsableBases;
    }
    public File getRemoteBamFilePath() {
        return remoteBamFilePath;
    }
    public File getRemoteVcfFilePath() {
        return remoteVcfFilePath;
    }
    public Gender getGenotypicGender() {
        return genotypicGender;
    }
    public Double getMeanInsertSize() {
        return meanInsertSize;
    }
    public Double getStdevInsertSize() {
        return stdevInsertSize;
    }
    public Double getDuplicationRate() {
        return duplicationRate;
    }
    public Double getEstimatedContamination() {
        return estimatedContamination;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SampleMetaData that = (SampleMetaData) o;

        if (tissue != null ? !tissue.equals(that.tissue) : that.tissue != null) return false;
        if (worklistId != null ? !worklistId.equals(that.worklistId) : that.worklistId != null) return false;
        if (seqId != null ? !seqId.equals(that.seqId) : that.seqId != null) return false;
        if (assay != null ? !assay.equals(that.assay) : that.assay != null) return false;
        if (pipelineName != null ? !pipelineName.equals(that.pipelineName) : that.pipelineName != null) return false;
        if (pipelineVersion != null ? !pipelineVersion.equals(that.pipelineVersion) : that.pipelineVersion != null)
            return false;
        if (rawSequenceQuality != null ? !rawSequenceQuality.equals(that.rawSequenceQuality) : that.rawSequenceQuality != null)
            return false;
        if (totalReads != null ? !totalReads.equals(that.totalReads) : that.totalReads != null) return false;
        if (pctSelectedBases != null ? !pctSelectedBases.equals(that.pctSelectedBases) : that.pctSelectedBases != null)
            return false;
        if (meanOnTargetCoverage != null ? !meanOnTargetCoverage.equals(that.meanOnTargetCoverage) : that.meanOnTargetCoverage != null)
            return false;
        if (pctTargetBasesCt != null ? !pctTargetBasesCt.equals(that.pctTargetBasesCt) : that.pctTargetBasesCt != null)
            return false;
        if (totalTargetedUsableBases != null ? !totalTargetedUsableBases.equals(that.totalTargetedUsableBases) : that.totalTargetedUsableBases != null)
            return false;
        if (remoteBamFilePath != null ? !remoteBamFilePath.equals(that.remoteBamFilePath) : that.remoteBamFilePath != null)
            return false;
        if (remoteVcfFilePath != null ? !remoteVcfFilePath.equals(that.remoteVcfFilePath) : that.remoteVcfFilePath != null)
            return false;
        if (genotypicGender != that.genotypicGender) return false;
        if (meanInsertSize != null ? !meanInsertSize.equals(that.meanInsertSize) : that.meanInsertSize != null)
            return false;
        if (stdevInsertSize != null ? !stdevInsertSize.equals(that.stdevInsertSize) : that.stdevInsertSize != null)
            return false;
        if (duplicationRate != null ? !duplicationRate.equals(that.duplicationRate) : that.duplicationRate != null)
            return false;
        return estimatedContamination != null ? estimatedContamination.equals(that.estimatedContamination) : that.estimatedContamination == null;

    }

    @Override
    public int hashCode() {
        int result = tissue != null ? tissue.hashCode() : 0;
        result = 31 * result + (worklistId != null ? worklistId.hashCode() : 0);
        result = 31 * result + (seqId != null ? seqId.hashCode() : 0);
        result = 31 * result + (assay != null ? assay.hashCode() : 0);
        result = 31 * result + (pipelineName != null ? pipelineName.hashCode() : 0);
        result = 31 * result + (pipelineVersion != null ? pipelineVersion.hashCode() : 0);
        result = 31 * result + (rawSequenceQuality != null ? rawSequenceQuality.hashCode() : 0);
        result = 31 * result + (totalReads != null ? totalReads.hashCode() : 0);
        result = 31 * result + (pctSelectedBases != null ? pctSelectedBases.hashCode() : 0);
        result = 31 * result + (meanOnTargetCoverage != null ? meanOnTargetCoverage.hashCode() : 0);
        result = 31 * result + (pctTargetBasesCt != null ? pctTargetBasesCt.hashCode() : 0);
        result = 31 * result + (totalTargetedUsableBases != null ? totalTargetedUsableBases.hashCode() : 0);
        result = 31 * result + (remoteBamFilePath != null ? remoteBamFilePath.hashCode() : 0);
        result = 31 * result + (remoteVcfFilePath != null ? remoteVcfFilePath.hashCode() : 0);
        result = 31 * result + (genotypicGender != null ? genotypicGender.hashCode() : 0);
        result = 31 * result + (meanInsertSize != null ? meanInsertSize.hashCode() : 0);
        result = 31 * result + (stdevInsertSize != null ? stdevInsertSize.hashCode() : 0);
        result = 31 * result + (duplicationRate != null ? duplicationRate.hashCode() : 0);
        result = 31 * result + (estimatedContamination != null ? estimatedContamination.hashCode() : 0);
        return result;
    }
}
