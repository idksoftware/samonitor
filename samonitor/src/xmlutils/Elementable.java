package xmlutils;
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

//package samonitor;

import org.w3c.dom.Element;

/**
 * A simple class that allows access to an XML element.
 *
 * @author <a href="mailto:ben@benlitchfield.com">Ben Litchfield</a>
 * @version $Revision: 1.1 $
 */
public interface Elementable
{
    /**
     * Get the XML element that this object represents.
     *
     * @return The xml element.
     */
    public Element getElement();
}
