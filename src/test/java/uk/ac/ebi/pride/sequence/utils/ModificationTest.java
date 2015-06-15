package uk.ac.ebi.pride.sequence.utils;

import org.junit.Test;

/**
 * Created with IntelliJ IDEA.
 * User: enrique
 * Date: 10/11/13
 * Time: 4:57
 * To change this template use File | Settings | File Templates.
 */
public class ModificationTest {

    @Test
    public void testGetFullname() throws Exception {
          System.out.println(Modification.a.getFullname());
    }

    @Test
    public void testGetRecordId() throws Exception {
        System.out.println(Modification.a.getRecordId());
    }

    @Test
    public void testGetCodeName() throws Exception {
        System.out.println(Modification.a.getCodeName());
    }

    @Test
    public void testGetMonoMass() throws Exception {
        System.out.println(Modification.a.getMonoMass());
    }

    @Test
    public void testGetAvgMass() throws Exception {
        System.out.println(Modification.a.getAvgMass());
    }

    @Test
    public void testGetModification() throws Exception {
        System.out.println(Modification.getModification('a'));
    }
}
