package nhs.genetics.cardiff.framework;

/**
 * A class for chromosomal changes.
 *
 * @author  Matt Lyon
 * @version 1.0
 * @since   2016-05-09
 */
public class GenomeVariant {

    private String contig, ref, alt;
    private int pos;

    public GenomeVariant(String contig, int pos, String ref, String alt){
        this.contig = contig;
        this.pos = pos;
        this.ref = ref;
        this.alt = alt;
    }

    public GenomeVariant(String s){ //use 1:111000A>T

        String[] split1 = s.split(":"); //get contig
        String[] split2 = split1[1].split("\\D"); //get pos
        String[] split3 = split1[1].split("\\d"); //get ref>alt
        String[] split4 = split3[split3.length - 1].split(">");

        this.contig = split1[0];
        this.pos = Integer.parseInt(split2[0]);
        this.ref = split4[0];
        this.alt = split4[1];
    }

    public void convertToMinimalRepresentation(){

        if (ref.length() == 1 && alt.length() == 1){
            return;
        } else {
            int i = ref.length(), j = alt.length();

            //trim shared suffix sequence
            while (i > 1 && j > 1){

                if (ref.charAt(i - 1) != alt.charAt(j - 1)){
                    break;
                }

                --i; --j;
            }

            ref = ref.substring(0, i);
            alt = alt.substring(0, j);
            
            //trim shared prefix sequence
            i = 1; j = 1;

            while (i < ref.length() && j < alt.length()){

                if (ref.charAt(i - 1) != alt.charAt(j - 1)){
                    break;
                }

                ++i; ++j; ++pos;
            }

            ref = ref.substring(i - 1);
            alt = alt.substring(i - 1);

        }

    }

    public String getContig(){
        return contig;
    }
    public String getRef(){
        return ref;
    }
    public String getAlt(){
        return alt;
    }
    public int getPos(){
        return pos;
    }
    public boolean isSnp(){
        return (ref.length() == 1 && alt.length() == 1);
    }
    public boolean isIndel(){
        return (ref.length() != 1 || alt.length() != 1);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GenomeVariant that = (GenomeVariant) o;

        if (pos != that.pos) return false;
        if (contig != null ? !contig.equals(that.contig) : that.contig != null) return false;
        if (ref != null ? !ref.equals(that.ref) : that.ref != null) return false;
        return alt != null ? alt.equals(that.alt) : that.alt == null;

    }

    @Override
    public int hashCode() {
        int result = contig != null ? contig.hashCode() : 0;
        result = 31 * result + (ref != null ? ref.hashCode() : 0);
        result = 31 * result + (alt != null ? alt.hashCode() : 0);
        result = 31 * result + pos;
        return result;
    }

    @Override
    public String toString() {
        return contig + ":" + Integer.toString(pos) + ref + ">" + alt;
    }
}
