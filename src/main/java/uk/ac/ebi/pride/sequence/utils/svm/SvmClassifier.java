package uk.ac.ebi.pride.sequence.utils.svm;

/**
 * Created with IntelliJ IDEA.
 * User: enriquea
 * Date: 1/08/13
 * Time: 22:23
 * To change this template use File | Settings | File Templates.
 */

import weka.classifiers.Evaluation;
import weka.classifiers.functions.SMOreg;
import weka.classifiers.functions.supportVector.Kernel;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.SerializationHelper;

import java.io.Serializable;


public class SvmClassifier implements Serializable{

    private SMOreg svm;
    /*An SVM instance*/

    private Evaluation eval;

    private Instances trainData;

    private double rmsd;

    //constructor
    public SvmClassifier(Instances trainData, String[] options, Kernel kernel) throws Exception{
        this.trainData = trainData;
        this.svm = new SMOreg();
        this.svm.setOptions(options);       //set the options
        this.svm.setKernel(kernel);
        this.svm.buildClassifier(trainData);    // build classifier
    }

    /*
     * This method return standard deviation obtained in the stage test the classifier
     * it is used like selection criteria.
     */
    //
    public double evaluateRMSE(Instances test) throws Exception{
        this.eval = new Evaluation(trainData);
        eval.evaluateModel(svm, test);
        double rmsd = eval.errorRate();
        return rmsd;
    }


    public Instances classifingInstance(Instances dataset) throws Exception{
        dataset.setClassIndex(-1); //data set without class attribute
        dataset.setClassIndex(dataset.numAttributes() - 1);
        Instances labeled = new Instances(dataset);
        for (int i = 0; i < dataset.numInstances(); i++) {
            double clsLabel = svm.classifyInstance(dataset.instance(i));
            labeled.instance(i).setClassValue(clsLabel);
        }
        return labeled;
    }

    public double[] getCoefficients(){
        return svm.getRegOptimizer().m_alpha;
    }


    public double classifyInstance(Instance inst) throws Exception{
        return this.eval.evaluateModelOnce(svm,inst);
    }

    public static void saveClassifier(String fileSaveName, SvmClassifier svmClassifier) throws Exception {
        SerializationHelper.write(fileSaveName, svmClassifier);
    }

    public static SvmClassifier loadClassifier(String idModel) throws Exception {
        return (SvmClassifier) SerializationHelper.read(idModel);
    }

}
