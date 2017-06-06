package nhs.genetics.cardiff.framework;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Helper class for transcripts
 *
 * @author  Matt Lyon
 * @since   2015-02-10
 */

public class TranscriptListParser {

    private static final Logger log = Logger.getLogger(TranscriptListParser.class.getName());

    private File filePath;
    private HashSet<String> transcripts = new HashSet<>();

    public TranscriptListParser(File filePath){
        this.filePath = filePath;
    }

    public void parseListReader() throws IOException {
        log.log(Level.INFO, "Parsing transcript list");
        String line;

        try (BufferedReader bufferedReader = new BufferedReader (new FileReader(filePath))){
            while ((line = bufferedReader.readLine()) != null) {
                String[] fields = line.split("\t");
                transcripts.add(fields[1]);
            }
        }

    }

    public File getFilePath() {
        return filePath;
    }
    public String getFileName(){
        return filePath.getName();
    }
    public HashSet<String> getTranscripts() {
        return transcripts;
    }
}