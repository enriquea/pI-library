package uk.ac.ebi.pride.sequence.retentiontime;

import uk.ac.ebi.pride.mol.AminoAcid;
import uk.ac.ebi.pride.sequence.utils.SequenceUtils;

import java.util.*;

/*******************************************************************************
 Copyright 2006-2010 Lukas Käll <lukas.kall@scilifelab.se>

 Licensed under the Apache License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at

 http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.

 *******************************************************************************/
/*
 * @ Created by Luminita Moruz
 * Sep, 2010
 */
/*
 * The file includes definitions of variables and methods in the class RetentionFeatures
 */

public class RetentionTimeFeature{

    public static String INDEX_CTERM                            = "Index C-terminus";

    public static String INDEX_NTERM                            = "Index N-terminus";

    public static String INDEX_NEAREST_NEIGHBOUR                = "Nearest Neighbour";

    public static String INDEX_MAX_PARTIAL_SUM_FIVE             = "Index Max Partial Sum of Five";

    public static String INDEX_MAX_PARTIAL_SUM_TWO              = "Index Max Partial Sum of Two";

    public static String INDEX_MIN_PARTIAL_SUM_FIVE             = "Index Min Partial Sum of Five";

    public static String INDEX_MIN_PARTIAL_SUM_TWO              = "Index Min Partial Sum of TWO";

    public static String INDEX_MAX_HYDROPHOBIC_SIDE_HELIX       = "Index Max Hydrophobic Side Helix";

    public static String INDEX_MIN_HYDROPHOBIC_SIDE_HELIX       = "Index Min Hydrophobic Side Helix";

    public static String INDEX_MAX_HYDROPHOBIC_MOMENT_100_11          = "Index Max Hydrophobic Moment 100-11";

    public static String INDEX_MAX_HYDROPHOBIC_MOMENT_180_11          = "Index Max Hydrophobic Moment 180-11";

    public static String INDEX_MIN_HYDROPHOBIC_MOMENT_100_11          = "Index Min Hydrophobic Moment 100-11";

    public static String INDEX_MIN_HYDROPHOBIC_MOMENT_180_11          = "Index Min Hydrophobic Moment 180-11";

    public static String INDEX_SUM_SQUARED_DIFF                       = "Index Sum Squared Diff";

    public static String INDEX_NUMBER_TYPE_POLAR_AA                   = "Number Type Polar AA";

    public static String INDEX_NUMBER_CONSECUTIVE_TYPE_POLAR_AA       = "Number Consecutive Type Polar AA";

    public static String INDEX_NUMBER_TYPE_HYDROPHOBIC_AA             = "Number Type Hydrophobic AA";

    public static String INDEX_NUMBER_CONSECUTIVE_TYPE_HYDROPHOBIC_AA = "Number Consecutive Type Hydrophobic AA";

    public static String INDEX_SUM                                    = "Index Sum";

    private static final String INDEX_AVG                             = "Index Avg";

    private static final String KYTEDOOLITTLE                         = "KyteDoolittle";

    private static final String BULKINESS                             = "Bulkiness";

    public static String INDEX_SEQUENCE_LENGTH                        = "Sequence Length";

    public static String INDEX_RETENTION_TIME_AA                      = "Retention Time AA";

    private static final String INDEX_NUMBER_OF_AA                    = "Number of Ocurrences of AA ";

    public static int kMaxNumberFeatures = 100;
    /* maximum number of features */

    public static Map<AminoAcid, Double> kKyteDoolittle = new HashMap<AminoAcid, Double>(){{
        put(AminoAcid.A,  1.8); put(AminoAcid.C,  2.5); put(AminoAcid.D, -3.5); put(AminoAcid.E, -3.5);
        put(AminoAcid.F,  2.8); put(AminoAcid.G, -0.4); put(AminoAcid.H, -3.2); put(AminoAcid.I,  4.5);
        put(AminoAcid.K, -3.9); put(AminoAcid.L,  3.8); put(AminoAcid.M,  1.9); put(AminoAcid.N, -3.5);
        put(AminoAcid.P, -1.6); put(AminoAcid.Q, -3.5); put(AminoAcid.R, -4.5); put(AminoAcid.S, -0.8);
        put(AminoAcid.T, -0.7); put(AminoAcid.V,  4.2); put(AminoAcid.W, -0.9); put(AminoAcid.Y, -1.3);
    }};
    /* define the Kyte Doolittle index */

    public static Map<AminoAcid, Double> kBulkiness = new HashMap<AminoAcid, Double>(){{
        put(AminoAcid.A,   11.5); put(AminoAcid.C,  13.46); put(AminoAcid.D, 11.68); put(AminoAcid.E,  13.57);
        put(AminoAcid.F,  19.80); put(AminoAcid.G,   3.40); put(AminoAcid.H, 13.69); put(AminoAcid.I,  21.40);
        put(AminoAcid.K,  15.71); put(AminoAcid.L,  21.40); put(AminoAcid.M, 16.25); put(AminoAcid.N,  12.82);
        put(AminoAcid.P,  17.43); put(AminoAcid.Q,  14.45); put(AminoAcid.R, 14.28); put(AminoAcid.S,   9.47);
        put(AminoAcid.T,  15.77); put(AminoAcid.V,  21.57); put(AminoAcid.W, 21.67); put(AminoAcid.Y,  18.03);
    }};

    public static List<AminoAcid> AAList = new ArrayList<AminoAcid>(){{
        add(AminoAcid.A); add(AminoAcid.C); add(AminoAcid.D); add(AminoAcid.E); add(AminoAcid.F); add(AminoAcid.G);
        add(AminoAcid.H); add(AminoAcid.I); add(AminoAcid.K); add(AminoAcid.L); add(AminoAcid.M); add(AminoAcid.N);
        add(AminoAcid.P); add(AminoAcid.Q); add(AminoAcid.R); add(AminoAcid.S); add(AminoAcid.T); add(AminoAcid.V);
        add(AminoAcid.W); add(AminoAcid.Y);
    }};

    public static Double kPercentageAA = 0.25;
    /* percentage of the amino acids from an index that are polar or hydrophobic */


    public static String kGroupNames[] = {"No posttranslationally modified peptides", "Phosphorylations"};
    /* name of each feature group */

    public boolean ignorePtms = true;
    /* whenever a modified peptide is not identified, use the unmodified instead? */

    private static volatile RetentionTimeFeature instance = null;



    public static RetentionTimeFeature getInstance(){
        if (instance == null) {
            synchronized (RetentionTimeFeature .class){
                if (instance == null) {
                    instance = new RetentionTimeFeature();
                }
            }
        }
        return instance;
    }

    private RetentionTimeFeature(){}

    /*
    * Get the total number of features
    */
    public int getTotalNumberFeatures(){
        int number_index_features = AAList.size();
        int number_length_features = 1;
        int number_aa_features = AAList.size();
        int total_number_features = 0;

        if (ignorePtms) {
            int number_bulkiness_features = 1;
            total_number_features += 2 * number_index_features +  number_length_features + number_bulkiness_features +  number_aa_features;
        }
        if (!ignorePtms){
            total_number_features += number_index_features +  number_length_features +  number_aa_features;
        }else{
            total_number_features += number_aa_features;
        }
        return total_number_features;
    }


    /* Calculate the sum of hydrophobicities of all AA in the peptide; if a modified AA is not in the index
     * then the value of the unmodified AA is used; is an AA is not found at all in the index its contribution is
     * not considered
     * */

     public Double indexSum(List<AminoAcid> sequence, Map<AminoAcid, Double> index) {
        Double sum = 0.0;
        for(AminoAcid aminoAcid: sequence){
            sum   += ((index.containsKey(aminoAcid)) ? index.get(aminoAcid):0.0);
        }
        return sum;
    }

    /* Calculate the average of hydrophobicities of all aa in the peptide; if a modified AA is not in the index
     * then the value of the unmodified aa is used; is an aa is not found at all in the index its contribution
     * is not considered
     */

    public Double indexAvg(List<AminoAcid> sequence, Map<AminoAcid, Double> index) {
        Double sum = 0.0;
        int count  = 0;
        for(AminoAcid aminoAcid: sequence){
            sum   += ((index.containsKey(aminoAcid))?index.get(aminoAcid):0.0);
            count += ((index.containsKey(aminoAcid))?1:0);
        }
        return sum / (double) count;
    }

    /*
     * Calculate the average hydrophobicity of an index
     */
    public double avgHydrophobicityIndex(Map<AminoAcid, Double> index) {
        double sum = 0.0;
        for(Double indexValues: index.values())  sum += indexValues;
        return sum / (double) index.size();
    }

    /*
    * Calculate the hydrophobicity of the N-terminus
    */
    public Double indexNTerm(List<AminoAcid> sequence, Map<AminoAcid, Double> index) {
        return getIndexValue(sequence.get(0),index);
    }

    /*
     * Calculate the hydrophobicity of the C-terminus
     */
    public Double indexCTerm(List<AminoAcid> sequence, Map<AminoAcid, Double> index) {
        return getIndexValue(sequence.get(sequence.size()-1), index);
    }

    private Double getIndexValue(AminoAcid aminoAcid, Map<AminoAcid, Double> index) {
        return (index.containsKey(aminoAcid) ? index.get(aminoAcid) : null);
    }

    /* 
     * Calculate the sum of squared differences in hydrophobicities between neighbours
     */
    public Double indexSumSquaredDiff(List<AminoAcid> sequence, Map<AminoAcid, Double> index) {
        Double squared_diff_sum = 0.0;
        Double diff = 0.0;
        for(int i = 1; i < sequence.size(); i++){
            diff = getIndexValue(sequence.get(i),index) - getIndexValue(sequence.get(i-1),index);
            squared_diff_sum += diff * diff;
        }
        return squared_diff_sum;
    }

    /*
     * Calculate the number of a consecutive AA of a certain type. The set gives the type of these such amino acids
     * (for example, the type could be the list "A", "I", "L", "M", "F", "W", "Y", "V", "C" for hydrophobic aa)
     */
    public Double numberConsecTypeAA(List<AminoAcid> sequence, List<AminoAcid> aminoAcidTypes) {
        Double occurences = 0.0;
        for(int i = 1; i < sequence.size(); i++) {
            if(aminoAcidTypes.contains(sequence.get(i-1)) && aminoAcidTypes.contains(sequence.get(i))) occurences++;
        }
        return occurences;
    }

    /*
     * Calculate the sum of hydrophobicities of neighbours of polar amino acids
     */
    public Double indexNearestNeigbour(List<AminoAcid> sequence, Map<AminoAcid, Double> index, List<AminoAcid> aaPolar) {
        Double sum = 0.0;

        for(int i = 0; i < sequence.size(); i++){
            AminoAcid aa = sequence.get(i);
            if(aaPolar.contains(aa)){
                if(i > 0)                    sum += Math.max(0.0, getIndexValue(sequence.get(i - 1), index));
                if (i < sequence.size() - 1) sum += Math.max(0.0, getIndexValue(sequence.get(i + 1), index));
            }
        }
        return sum;
    }

    /*
     * Get the most hydrophobic window
     */

    public Double indexMaxPartialSum(List<AminoAcid> sequence, Map<AminoAcid, Double> index, int gap) {
        gap = Math.min(gap, sequence.size()-1);
        Double max_sum, sum = 0.0;

        for(int i = 0; i < gap; i++) sum += getIndexValue(sequence.get(i), index);

        max_sum = sum;
        for(int i = gap ; i < sequence.size(); i++){
            sum -= getIndexValue(sequence.get(i - gap), index);
            sum += getIndexValue(sequence.get(i), index);
            max_sum = Math.max(max_sum, sum);
        }
        return max_sum;
    }

    public Double indexMinPartialSum(List<AminoAcid> sequence, Map<AminoAcid, Double> index, int gap) {
        gap = Math.min(gap, sequence.size() - 1);
        Double min_sum, sum = 0.0;

        for(int i = 0; i < gap; i++)  sum += getIndexValue(sequence.get(i), index);

        min_sum = sum;
        for( int i = gap ; i < sequence.size(); i++){
            sum -= getIndexValue(sequence.get(i - gap), index);
            sum += getIndexValue(sequence.get(i), index);
            min_sum = Math.min(min_sum, sum);
        }
        return min_sum;
    }


    public Double indexMaxHydrophobicSideHelix(List<AminoAcid> sequence,  Map<AminoAcid, Double> index) {

        Double cos300 = Math.cos(300 * Math.PI / 180);
        Double cos400 = Math.cos(400 * Math.PI / 180);

        if (sequence.size() < 9) {
            Double avg_hydrophobicity_index = avgHydrophobicityIndex(index);
            return avg_hydrophobicity_index * (1 + 2 * cos300 + 2 * cos400);
        } else {
            Double hydrophobicity_side = 0.0;
            Double max_hydrophobicity_side = getIndexValue(sequence.get(4), index) +
                    (cos300 * (getIndexValue(sequence.get(1), index) + getIndexValue(sequence.get(7), index))) +
                    (cos400 * (getIndexValue(sequence.get(0), index) + getIndexValue(sequence.get(8), index)));

            for(int i = 5; i <= sequence.size() - 5; ++i) {
                hydrophobicity_side = getIndexValue(sequence.get(i), index) +
                                      cos300 * (getIndexValue(sequence.get(i - 3), index) + getIndexValue(sequence.get(i + 3), index)) +
                                      cos400 * (getIndexValue(sequence.get(i - 4), index) + getIndexValue(sequence.get(i + 4), index));
                max_hydrophobicity_side = Math.max(max_hydrophobicity_side, hydrophobicity_side);
            }
            return max_hydrophobicity_side;
        }
    }

    /* calculate the least hydrophobic sides for alpha helices */
    public Double indexMinHydrophobicSideHelix(List<AminoAcid> sequence,  Map<AminoAcid, Double> index) {
        Double cos300 = Math.cos(300 * Math.PI / 180);
        Double cos400 = Math.cos(400 * Math.PI / 180);

        if (sequence.size() < 9) {
            Double avg_hydrophobicity_index = avgHydrophobicityIndex(index);
            return avg_hydrophobicity_index * (1 + 2 * cos300 + 2 * cos400);
        } else {
            Double hydrophobicity_side = 0.0;
            Double min_hydrophobicity_side = getIndexValue(sequence.get(4), index) +
                    cos300 * (getIndexValue(sequence.get(1), index) + getIndexValue(sequence.get(7), index)) +
                    cos400 * (getIndexValue(sequence.get(0), index) + getIndexValue(sequence.get(8), index));

            for(int i = 5; i <= sequence.size() - 5; ++i) {
                hydrophobicity_side = getIndexValue(sequence.get(i), index) +
                        cos300 * (getIndexValue(sequence.get(i - 3), index) + getIndexValue(sequence.get(i + 3), index)) +
                        cos400 * (getIndexValue(sequence.get(i - 4), index) + getIndexValue(sequence.get(i + 4), index));
                min_hydrophobicity_side = Math.min(min_hydrophobicity_side, hydrophobicity_side);
            }
            return min_hydrophobicity_side;
        }
    }

    /*
     * Compute the sum of bulkiness
     */
    public Map<String, Double> bulkinessFeatures(List<AminoAcid> sequence, Map<AminoAcid, Double> bulkinessIndex, Map<String, Double> features) {
        features = (features == null)? new HashMap<String, Double>(): features;
        features.put(INDEX_SUM + " " + BULKINESS, indexSum(sequence,bulkinessIndex));
        return features;
    }

    /*
     * Calculate the maximum value of the hydrophobic moment
     */

    public Double indexMaxHydrophobicMoment(List<AminoAcid> sequence, Map<AminoAcid, Double> index, Double angle_degrees, int gap) {
        Double sin_sum = 0.0, cos_sum = 0.0;
        Double angle_radians = angle_degrees * Math.PI / 180;

        if (sequence.size() < gap) {
            Double avg_hydrophobicity_index = avgHydrophobicityIndex(index);
            for(int i = 1; i <= gap; ++i) {
                cos_sum += Math.cos(i * angle_radians);
                sin_sum += Math.sin(i * angle_radians);
            }
            cos_sum *= avg_hydrophobicity_index;
            sin_sum *= avg_hydrophobicity_index;
            return Math.sqrt((cos_sum * cos_sum) + (sin_sum * sin_sum));
        } else {
            Double window_hmoment = 0.0;
            for( int i = 0 ; i < gap; i++) {
                cos_sum += getIndexValue(sequence.get(i), index) * Math.cos(i * angle_radians);
                sin_sum += getIndexValue(sequence.get(i), index) * Math.sin(i * angle_radians);
            }
            Double max_hmoment = Math.sqrt((cos_sum * cos_sum) + (sin_sum * sin_sum));
            for(int i = gap ; i < sequence.size(); i++) {
                cos_sum += getIndexValue(sequence.get(i), index) * Math.cos(i * angle_radians);
                cos_sum -= getIndexValue(sequence.get(i - gap), index) * Math.cos((i-gap) * angle_radians);
                sin_sum += getIndexValue(sequence.get(i), index) * Math.sin(i * angle_radians);
                sin_sum -= getIndexValue(sequence.get(i - gap), index) * Math.sin((i-gap) * angle_radians);
                window_hmoment = Math.sqrt((cos_sum * cos_sum) + (sin_sum * sin_sum));
                max_hmoment = Math.max(max_hmoment, window_hmoment);
            }
            return max_hmoment;
        }
    }

    /* calculate the minimum value of the hydrophobic moment */
    public Double indexMinHydrophobicMoment(List<AminoAcid> sequence, Map<AminoAcid, Double> index, Double angle_degrees, int gap) {

        int len = sequence.size();
        double sin_sum = 0.0, cos_sum = 0.0;
        double angle_radians = angle_degrees * Math.PI / 180;

        if (len < gap) {
            double avg_hydrophobicity_index = avgHydrophobicityIndex(index);
            for(int i = 1; i <= gap; ++i) {
                cos_sum += Math.cos(i * angle_radians);
                sin_sum += Math.sin(i * angle_radians);
            }
            cos_sum *= avg_hydrophobicity_index;
            sin_sum *= avg_hydrophobicity_index;
            return Math.sqrt((cos_sum * cos_sum) + (sin_sum * sin_sum));
        } else {
            double window_hmoment = 0.0;
            for(int i = 0  ; i < gap; i++) {
                cos_sum += getIndexValue(sequence.get(i), index) * Math.cos((i + 1) * angle_radians);
                sin_sum += getIndexValue(sequence.get(i), index) * Math.sin((i+1) * angle_radians);
            }
            double min_hmoment = Math.sqrt((cos_sum * cos_sum) + (sin_sum * sin_sum));
            for( int i = gap; i < sequence.size(); i++) {
                cos_sum += getIndexValue(sequence.get(i), index) * Math.cos((i + 1) * angle_radians);
                cos_sum -= getIndexValue(sequence.get(i-gap), index) * Math.cos(((i + 1) - gap) * angle_radians);
                sin_sum += getIndexValue(sequence.get(i), index) * Math.sin((i + 1) * angle_radians);
                sin_sum -= getIndexValue(sequence.get(i-gap), index) * Math.sin(((i + 1) - gap) * angle_radians);
                window_hmoment = Math.sqrt((cos_sum * cos_sum) + (sin_sum * sin_sum));
                min_hmoment = Math.min(min_hmoment, window_hmoment);
            }
            return min_hmoment;
        }
    }



    /**************************** INDEX FUNCTIONS **************************************/
    /*
     * Fill all the features of an index; return a pointer to the next element in the feature table
     */

    Map<String, Double> indexFeatures(List<AminoAcid> sequence, Map<AminoAcid, Double> index, List<AminoAcid> aaPolarList,
                                      List<AminoAcid> aaHidrophobicList, Map<String, Double> features) {

        features = (features == null)? new HashMap<String, Double>():features;
        features.put(INDEX_SUM, indexSum(sequence, index));
        features.put(INDEX_AVG, indexAvg(sequence, index));
        features.put(INDEX_NTERM, indexNTerm(sequence, index));
        features.put(INDEX_CTERM, indexCTerm(sequence, index));
        features.put(INDEX_NEAREST_NEIGHBOUR, indexNearestNeigbour(sequence,index,aaPolarList));
        features.put(INDEX_MAX_PARTIAL_SUM_FIVE, indexMaxPartialSum(sequence, index, 5));
        features.put(INDEX_MAX_PARTIAL_SUM_TWO, indexMaxPartialSum(sequence,index,2));
        features.put(INDEX_MIN_PARTIAL_SUM_FIVE,indexMinPartialSum(sequence, index, 5));
        features.put(INDEX_MIN_PARTIAL_SUM_TWO,indexMinPartialSum(sequence, index, 2));
        features.put(INDEX_MAX_HYDROPHOBIC_SIDE_HELIX,indexMaxHydrophobicSideHelix(sequence, index));
        features.put(INDEX_MIN_HYDROPHOBIC_SIDE_HELIX,indexMinHydrophobicSideHelix(sequence, index));
        features.put(INDEX_MAX_HYDROPHOBIC_MOMENT_100_11,indexMaxHydrophobicMoment(sequence, index, 100.0, 11));
        features.put(INDEX_MAX_HYDROPHOBIC_MOMENT_180_11, indexMaxHydrophobicMoment(sequence, index, 180.0, 11));
        features.put(INDEX_MIN_HYDROPHOBIC_MOMENT_100_11, indexMinHydrophobicMoment(sequence, index, 100.0, 11));
        features.put(INDEX_MIN_HYDROPHOBIC_MOMENT_180_11, indexMinHydrophobicMoment(sequence, index, 180.0, 11));
        features.put(INDEX_SUM_SQUARED_DIFF, indexSumSquaredDiff(sequence, index));
        features.put(INDEX_NUMBER_TYPE_POLAR_AA, (double)SequenceUtils.numberTypeAA(sequence, aaPolarList));
        features.put(INDEX_NUMBER_CONSECUTIVE_TYPE_POLAR_AA, numberConsecTypeAA(sequence, aaPolarList));
        features.put(INDEX_NUMBER_TYPE_HYDROPHOBIC_AA, (double) SequenceUtils.numberTypeAA(sequence, aaHidrophobicList));
        features.put(INDEX_NUMBER_CONSECUTIVE_TYPE_HYDROPHOBIC_AA, numberConsecTypeAA(sequence, aaHidrophobicList));

        return features;
    }

    /* get the total number of features */
    public int getkMaxNumberFeatures(){
        int number_index_features = 20;
        int number_length_features = 1;
        int number_aa_features = AAList.size();
        int total_number_features = 0;
        int number_bulkiness_features = 1;
        total_number_features += 2 * number_index_features +  number_length_features + number_bulkiness_features +  number_aa_features;
        total_number_features += number_aa_features;
        return total_number_features;
    }


    public Map<String, Double> computeBulkinessFeatures(List<AminoAcid> sequence, Map<AminoAcid, Double> bulkinessIndex, Map<String,Double> features) {
        features = (features == null)? new HashMap<String, Double>():features;
        features.put(INDEX_SUM + " " + BULKINESS,indexSum(sequence, bulkinessIndex));
        return features;
    }


    public Vector<List<AminoAcid>> getExtremeRetentionAA(Map<AminoAcid, Double> index) {
        Vector<List<AminoAcid>> result = new Vector<List<AminoAcid>>();
        Vector<AminoAcid> aaList = new Vector<AminoAcid>();
        Vector<Double>    retentions = new Vector<Double>();
        int len = index.size();
        int num_aa = (int) Math.ceil(kPercentageAA * len);

        for(AminoAcid aminoAcid: index.keySet()) {
            aaList.add(aminoAcid);
            retentions.add(index.get(aminoAcid));
        }
        int i;
        boolean switched = true;
        double temp_ret;
        AminoAcid temp_aa;
        while (switched) {
            switched = false;
            for(i = 0; i < index.size()-1; ++i) {
                    if (retentions.get(i) > retentions.get(i+1)) {
                        switched = true;
                        temp_ret = retentions.get(i);
                        retentions.set(i,retentions.get(i+1));
                        retentions.set(i+1,temp_ret);
                        temp_aa = aaList.get(i);
                        aaList.set(i,aaList.get(i + 1));
                        aaList.set(i + 1,temp_aa);
                    }
            }
        }
        // if there are more aa with equal retentions, take all of them
        double val = retentions.get(num_aa - 1);
        i = num_aa;
        while (i < len && val == retentions.get(i)) {
            ++i;
        }
        List<AminoAcid> lowest_set = aaList.subList(0, i);
        result.add(lowest_set);
        val = retentions.get(len - num_aa);
        i = len - num_aa - 1;
        while (i >= 0 && i < len && val == retentions.get(i)) {
            --i;
        }
        List<AminoAcid> highest_set = aaList.subList(i + 1, aaList.size());
        result.add(highest_set);
        return result;
    }

    /* adds a feature giving the number of each of the symbols in the alphabet found in the peptide */
    public Map<String, Double> fillAAFeatures(List<AminoAcid> sequence, Map<String, Double> features) {
        features = (features == null)? new HashMap<String, Double>(): features;
        Map<AminoAcid, Double> featureValues = new HashMap<AminoAcid, Double>();
        for(AminoAcid aminoAcid: AAList){
            featureValues.put(aminoAcid, 0.0);
        }
        for(int i = 0 ; i < sequence.size(); i++) {
            if(AAList.contains(sequence.get(i))){
                double value = featureValues.get(sequence.get(i));
                value++;
                featureValues.remove(sequence.get(i));
                featureValues.put(sequence.get(i), value);
                break;
            }
        }
        for(AminoAcid aminoAcid: featureValues.keySet()){
            features.put(INDEX_NUMBER_OF_AA + aminoAcid.getOneLetterCode(),featureValues.get(aminoAcid));

        }
        return features;
    }

    /* compute the features related to length; */
    public Map<String, Double> computeLengthFeatures(List<AminoAcid> sequence, Map<String, Double> features) {
        features = (features == null)? new HashMap<String, Double>(): features;
        features.put(INDEX_SEQUENCE_LENGTH, (double) sequence.size());
        return features;
    }

    public Map<String, Double> computeRetentionTimeFeatures(List<AminoAcid> sequence){
        Map<String, Double> features = new HashMap<String, Double>();

        features.put(INDEX_SUM +" "+ BULKINESS,indexSum(sequence,kBulkiness));
        /* Bulkisness sum (1) */
        features.put(INDEX_SUM +" "+ KYTEDOOLITTLE,indexSum(sequence,kKyteDoolittle));
        /* KyteDoolittle sum (2) */

        features.put(INDEX_AVG +" "+ BULKINESS, indexAvg(sequence,kBulkiness));
        /* Bulkisness Avg (3) */
        features.put(INDEX_AVG +" "+ KYTEDOOLITTLE, indexAvg(sequence,kKyteDoolittle));
        /* KyteDoolittle Avg (4) */

        features.put(INDEX_CTERM +" "+ BULKINESS,indexCTerm(sequence,kBulkiness));
        /* Bulkisness CTerm (5) */
        features.put(INDEX_CTERM +" "+ KYTEDOOLITTLE, indexCTerm(sequence,kKyteDoolittle));
        /* KyteDoolittle CTerm (6) */

        features.put(INDEX_NTERM +" "+ BULKINESS,indexNTerm(sequence,kBulkiness));
        /* Bulkisness NTerm (7) */
        features.put(INDEX_NTERM +" "+ KYTEDOOLITTLE, indexNTerm(sequence,kKyteDoolittle));
        /* KyteDoolittle NTerm (8) */

        features.put(INDEX_SUM_SQUARED_DIFF +" "+ BULKINESS,indexSumSquaredDiff(sequence,kBulkiness));
        /* Bulkisness Sum SQUARE (9) */
        features.put(INDEX_SUM_SQUARED_DIFF +" "+ KYTEDOOLITTLE, indexSumSquaredDiff(sequence,kKyteDoolittle));
        /* KyteDoolittle SUM SQUARE (10) */

        features = computeLengthFeatures(sequence,features);
        /*Number of occurrences of each amino acid (11-20)*/

        features = computeLengthFeatures(sequence,features);
        /*Sequence Length (21)*/

        return features;
    }
}


