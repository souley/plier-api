//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.0 in JDK 1.6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2011.05.11 at 11:17:38 AM CEST 
//


package nl.biggrid.plier.opm;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlIDREF;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Annotation complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Annotation">
 *   &lt;complexContent>
 *     &lt;extension base="{http://openprovenance.org/model/v1.1.a}EmbeddedAnnotation">
 *       &lt;sequence>
 *         &lt;choice minOccurs="0">
 *           &lt;element name="externalSubject" type="{http://www.w3.org/2001/XMLSchema}anyURI"/>
 *           &lt;element name="localSubject" type="{http://www.w3.org/2001/XMLSchema}IDREF"/>
 *         &lt;/choice>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Annotation", propOrder = {
    "externalSubject",
    "localSubject"
})
public class Annotation
    extends EmbeddedAnnotation
{

    protected String externalSubject;
    @XmlIDREF
    protected String localSubject;

    /**
     * Gets the value of the externalSubject property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getExternalSubject() {
        return externalSubject;
    }

    /**
     * Sets the value of the externalSubject property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setExternalSubject(String value) {
        this.externalSubject = value;
    }

    /**
     * Gets the value of the localSubject property.
     * 
     * @return
     *     possible object is
     *     {@link Object }
     *     
     */
    public String getLocalSubject() {
        return localSubject;
    }

    /**
     * Sets the value of the localSubject property.
     * 
     * @param value
     *     allowed object is
     *     {@link Object }
     *     
     */
    public void setLocalSubject(String value) {
        this.localSubject = value;
    }

}
