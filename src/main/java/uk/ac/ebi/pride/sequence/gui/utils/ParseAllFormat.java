package uk.ac.ebi.pride.sequence.gui.utils;

import java.text.AttributedCharacterIterator;
import java.text.FieldPosition;
import java.text.Format;
import java.text.ParseException;
import java.text.ParsePosition;


/**
 * Created with IntelliJ IDEA.
 * User: Enrique
 * Date: 20/02/15
 * Time: 11:41
 * To change this template use File | Settings | File Templates.
 */


public class ParseAllFormat extends Format{

    private final Format fDelegate;

    /**
     * Decorate <code>aDelegate</code> to make sure if parser everything or nothing
     *
     * @param aDelegate The delegate format
     */
    public ParseAllFormat(Format aDelegate) {
        fDelegate = aDelegate;
    }

    @Override
    public StringBuffer format( Object obj, StringBuffer toAppendTo, FieldPosition pos ) {
        return fDelegate.format( obj, toAppendTo, pos );
    }

    @Override
    public AttributedCharacterIterator formatToCharacterIterator( Object obj ) {
        return fDelegate.formatToCharacterIterator( obj );
    }

    @Override
    public Object parseObject( String source, ParsePosition pos ) {
        int initialIndex = pos.getIndex();
        Object result = fDelegate.parseObject( source, pos );
        if ( result != null && pos.getIndex() < source.length() ) {
            int errorIndex = pos.getIndex();
            pos.setIndex( initialIndex );
            pos.setErrorIndex( errorIndex );
            return null;
        }
        return result;
    }

    @Override
    public Object parseObject( String source ) throws ParseException {
        //no need to delegate the call, super will call the parseObject( source, pos ) method
        return super.parseObject( source );
    }

}
