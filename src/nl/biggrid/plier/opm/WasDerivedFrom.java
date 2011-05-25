//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.0 in JDK 1.6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2011.05.11 at 11:17:38 AM CEST 
//


package nl.biggrid.plier.opm;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for WasDerivedFrom complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="WasDerivedFrom">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="effect" type="{http://openprovenance.org/model/v1.1.a}ArtifactRef"/>
 *         &lt;element name="cause" type="{http://openprovenance.org/model/v1.1.a}ArtifactRef"/>
 *         &lt;element name="account" type="{http://openprovenance.org/model/v1.1.a}AccountRef" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="time" type="{http://openprovenance.org/model/v1.1.a}OTime" minOccurs="0"/>
 *         &lt;element ref="{http://openprovenance.org/model/v1.1.a}annotation" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="id" type="{http://www.w3.org/2001/XMLSchema}ID" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "WasDerivedFrom", propOrder = {
    "effect",
    "cause",
    "account",
    "time",
    "annotation"
})
public class WasDerivedFrom extends CausalDependency {

    @XmlElement(required = true)
    protected Artifact effect;
    @XmlElement(required = true)
    protected Artifact cause;

    /**
     * Gets the value of the effect property.
     * 
     * @return
     *     possible object is
     *     {@link ArtifactRef }
     *     
     */
    public Artifact getEffect() {
        return effect;
    }

    /**
     * Sets the value of the effect property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArtifactRef }
     *     
     */
    public void setEffect(Artifact value) {
        this.effect = value;
    }

    /**
     * Gets the value of the cause property.
     * 
     * @return
     *     possible object is
     *     {@link ArtifactRef }
     *     
     */
    public Artifact getCause() {
        return cause;
    }

    /**
     * Sets the value of the cause property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArtifactRef }
     *     
     */
    public void setCause(Artifact value) {
        this.cause = value;
    }

}
