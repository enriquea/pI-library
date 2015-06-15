package uk.ac.ebi.pride.sequence.isoelectricpoint;

/**
 * Created with IntelliJ IDEA.
 * User: enrique
 * Date: 10/06/15
 * Time: 2:57
 * To change this template use File | Settings | File Templates.
 */

/**
 * This class model a protein instance from Isoelectric Point-Protein Data Base.
 * Useful to retrieve information from this data base.
 */

public class ProteinPIDB {

    //field
    private String header = null;
    private String sequence = null;
    private String pIexperimental = null;
    private String pIcomputed = null;

    //constructors
    public ProteinPIDB (String seq, String acc, String pIexp, String pIteor){
        this.sequence = seq;
        this.header = acc;
        this.pIexperimental = pIexp;
        this.pIcomputed = pIteor;
    }

    //methods

    public String getHeaderForEntry(){
        return this.header;
    }

    /**
     * Extract information from the header of a FASTA sequence.
     * The first element is the header ID and the second is the sequence name.
     * Note: This method is very much specialized for headers in a certain
     * format.
     */
    public String getProteinID(){
        // the header is separated by '|'
        String[] fields = header.split("\\|");
        return (fields[0].substring(fields[0].length()-2, fields[0].length()) + "|" + fields[1]);
    }

    public String getpIexperimental(){
        return this.pIexperimental;
    }

    public String getSequenceProtein(){
        return this.sequence;
    }

    public String getPiTheoretical(){
        return this.pIcomputed;
    }
}
